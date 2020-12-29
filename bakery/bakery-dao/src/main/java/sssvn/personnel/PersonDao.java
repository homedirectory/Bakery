package sssvn.personnel;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetch;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAll;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

import sssvn.security.tokens.persistent.Person_CanDelete_Token;
import sssvn.security.tokens.persistent.Person_CanModify_user_Token;
import sssvn.security.tokens.persistent.Person_CanSave_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.query.fluent.fetch;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.security.user.IUser;
import ua.com.fielden.platform.security.user.User;

/**
 * DAO implementation for companion object {@link PersonCo}.
 *
 * @author Generated
 *
 */
@EntityType(Person.class)
public class PersonDao extends CommonEntityDao<Person> implements PersonCo {
	
    private static final Logger LOGGER = getLogger(PersonDao.class);

    @Inject
    protected PersonDao(final IFilter filter) {
        super(filter);
    }

    @Override
    public Person new_() {
        return super.new_().setActive(true).setGenerateEmployeeNo(false);
    }

    @Override
    @SessionRequired
    @Authorise(Person_CanSave_Token.class)
    public Person save(final Person person) {
        person.isValid().ifFailure(Result::throwRuntime);
        
        // check if manager/carrier property was changed
        final boolean wasCarrierChanged = person.getProperty("carrier").isDirty();
        final boolean wasManagerChanged = person.getProperty("manager").isDirty();
        final Person savedPerson = super.save(person);
        
        // if it was changed, then a new Manager object needs to be created or fetched if it already exists
        if (wasManagerChanged) {
        	final ManagerCo co$ = co$(Manager.class);
        	final Optional<Manager> maybeManager = co$.findByKeyAndFetchOptional(co$.getFetchProvider().fetchModel(), savedPerson);
        	
        	/* if a Person was assigned the manager role -> get the fetched Manager object or create a new one if it does not exist yet 
        		and sync its active state with Person */
        	if (savedPerson.isManager()) {
        		final Manager manager = maybeManager.orElseGet(() -> co$.new_().setPerson(savedPerson));
        		co$.save(manager.setActive(savedPerson.isActive()));
        	}
        	// if a Person was was removed from the manager role -> set fetched Manager object active status to false
        	else {
        		maybeManager.ifPresent(manager -> co$.save(manager.setActive(false)));
        	}
        }
        
        if (wasCarrierChanged) {
        	final CarrierCo co$ = co$(Carrier.class);
        	final Optional<Carrier> maybeCarrier = co$.findByKeyAndFetchOptional(co$.getFetchProvider().fetchModel(), savedPerson);
        	
        	/* if a Person was assigned the carrier role -> get the fetched Carrier object or create a new one if it does not exist yet 
        		and sync its active state with Person */
        	if (savedPerson.isCarrier()) {
        		final Carrier carrier = maybeCarrier.orElseGet(() -> co$.new_().setPerson(savedPerson));
        		co$.save(carrier.setActive(savedPerson.isActive()));
        	}
        	// if a Person was was removed from the carrier role -> set fetched Carrier object active status to false
        	else {
        		maybeCarrier.ifPresent(carrier -> co$.save(carrier.setActive(false)));
        	}
        }
        
        return savedPerson;
    }

    @Override
    @SessionRequired
    @Authorise(Person_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(Person_CanDelete_Token.class)
    public int batchDelete(final List<Person> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    public Optional<Person> currentPerson(final fetch<Person> fetchPerson) {
        final EntityResultQueryModel<Person> qExecModel = select(Person.class).where().prop("user").eq().val(getUser()).model();
        return getEntityOptional(from(qExecModel).with(fetchPerson).model());
    }

    @Override
    public Optional<Person> currentPerson() {
        return currentPerson(fetch(Person.class));
    }

    @Override
    protected IFetchProvider<Person> createFetchProvider() {
        return PersonCo.FETCH_PROVIDER;
    }

    @Override
    @SessionRequired
    @Authorise(Person_CanModify_user_Token.class)
    public Person makeUser(final Person person) {
        if (person.isAUser()) {
            throw Result.failure(format("Person [%s] is already an application user.", person.getKey()));
        }

        final String email = person.getEmail();
        final boolean active = email != null; // i.e. only make a user active if the email is not null
        final IUser co$User = co$(User.class);
        final User user = co$User.new_().setKey(person.getInitials()).setEmail(email).setActive(active);
        user.setDesc(format("User for person [%s].", person.getDesc()));
        final User su = co$User.findByKeyAndFetch(fetchAll(User.class), User.system_users.SU.name());
        if (su != null) {
            user.setBasedOnUser(su);
        } else {
            user.setBase(true);
        }
        
        final User savedUser = co$User.resetPasswd(user, user.getKey()).getKey();
        return save(co$(Person.class).findById(person.getId(), PersonCo.FETCH_PROVIDER.fetchModel())
                    .setUser(savedUser));
    }

}