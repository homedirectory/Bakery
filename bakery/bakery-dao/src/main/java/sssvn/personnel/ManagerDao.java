package sssvn.personnel;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link ManagerCo}.
 *
 * @author Developers
 *
 */
@EntityType(Manager.class)
public class ManagerDao extends CommonEntityDao<Manager> implements ManagerCo {

    @Inject
    public ManagerDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Manager> createFetchProvider() {
         return FETCH_PROVIDER;
    }
}
