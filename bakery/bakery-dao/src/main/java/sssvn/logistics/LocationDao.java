package sssvn.logistics;

import static org.apache.logging.log4j.LogManager.getLogger;

import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

import sssvn.personnel.PersonCo;
import sssvn.security.tokens.persistent.Location_CanSave_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.security.Authorise;

/**
 * DAO implementation for companion object {@link LocationCo}.
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
    public Location new_() {
        return super.new_().setActive(true);
    }

    @Override
    protected IFetchProvider<Location> createFetchProvider() {
        return FETCH_PROVIDER;
    }
    
    @Override
    @SessionRequired
    @Authorise(Location_CanSave_Token.class)
    public Location save(Location entity) {
    	return super.save(entity);
    }

}