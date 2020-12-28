package sssvn.location;

import static java.lang.String.format;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetch;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAll;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import static org.apache.logging.log4j.LogManager.getLogger;
import org.apache.logging.log4j.Logger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import com.google.inject.Inject;

import sssvn.security.tokens.persistent.Person_CanModify_user_Token;
import sssvn.security.tokens.persistent.Person_CanSave_Token;
import sssvn.security.tokens.persistent.Person_CanDelete_Token;

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
@EntityType(Location.class)
public class LocationDao extends CommonEntityDao<Location> implements LocationCo {

    private static final Logger LOGGER = getLogger(LocationDao.class);

    @Inject
    protected LocationDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Location> createFetchProvider() {
        return FETCH_PROVIDER;
    }

}