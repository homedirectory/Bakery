package sssvn.personnel;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link CarrierCo}.
 *
 * @author Developers
 *
 */
@EntityType(Carrier.class)
public class CarrierDao extends CommonEntityDao<Carrier> implements CarrierCo {

    @Inject
    public CarrierDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Carrier> createFetchProvider() {
         return FETCH_PROVIDER;
    }
}
