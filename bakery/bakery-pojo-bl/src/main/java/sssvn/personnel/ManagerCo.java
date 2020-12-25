package sssvn.personnel;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Manager}.
 *
 * @author Developers
 *
 */
public interface ManagerCo extends IEntityDao<Manager> {

    static final IFetchProvider<Manager> FETCH_PROVIDER = EntityUtils.fetch(Manager.class).with("person", "desc");

}
