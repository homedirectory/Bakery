package sssvn.logistics;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Location}.
 *
 * @author Developers
 *
 */
public interface LocationCo extends IEntityDao<Location> {

    static final IFetchProvider<Location> FETCH_PROVIDER = EntityUtils.fetch(Location.class).with("desc", "country", "city", "address", "phone",
    																							"workingHours", "employeesAmount");

}