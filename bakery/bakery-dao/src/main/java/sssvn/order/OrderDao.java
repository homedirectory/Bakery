package sssvn.order;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link IOrder}.
 *
 * @author Developers
 *
 */
@EntityType(Order.class)
public class OrderDao extends CommonEntityDao<Order> implements OrderCo {

    @Inject
    public OrderDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Order> createFetchProvider() {
        // TODO: uncomment the following line and specify the properties, which are required for the UI in IOrder.FETCH_PROVIDER. Then remove the line after.
        // return FETCH_PROVIDER;
        throw new UnsupportedOperationException("Please specify the properties, which are required for the UI in IOrder.FETCH_PROVIDER");
    }
}
