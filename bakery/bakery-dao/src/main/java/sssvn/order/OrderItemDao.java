package sssvn.order;

import static org.apache.logging.log4j.LogManager.getLogger;

import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

import sssvn.location.LocationDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link OrderItemCo}.
 *
 * @author Developers
 *
 */
@EntityType(OrderItem.class)
public class OrderItemDao extends CommonEntityDao<OrderItem> implements OrderItemCo {
	
    @Inject
    public OrderItemDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<OrderItem> createFetchProvider() {
        // TODO: uncomment the following line and specify the properties, which are required for the UI in IOrderItem.FETCH_PROVIDER. Then remove the line after.
         return FETCH_PROVIDER;
//        throw new UnsupportedOperationException("Please specify the properties, which are required for the UI in IOrderItem.FETCH_PROVIDER");
    }
}