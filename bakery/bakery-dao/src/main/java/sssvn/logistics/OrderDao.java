package sssvn.logistics;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;
import ua.com.fielden.platform.utils.EntityUtils;

/**
 * DAO implementation for companion object {@link IOrder}.
 *
 * @author Developers
 *
 */
@EntityType(Order.class)
public class OrderDao extends CommonEntityDao<Order> implements OrderCo {

    private static final String ORDER_NUMBER_KEY = "qq";
    public static final String DEFAULT_ORDER_NUMBER_KEY = "jj";

    @Inject
    public OrderDao(final IFilter filter) {
        super(filter);
    }

    @Override
    public Order new_() {
        
        return super.new_().setOrderNo(DEFAULT_ORDER_NUMBER_KEY).setActive(true);
    }

    @Override
    @SessionRequired
    public Order save(final Order order) {
        order.isValid().ifFailure(Result::throwRuntime);
        boolean orderIsPersisted = order.isPersisted();
        try {
            if (!orderIsPersisted && EntityUtils.equalsEx(order.getOrderNo(), DEFAULT_ORDER_NUMBER_KEY)) {
                
                IKeyNumber key = co(KeyNumber.class);
                Integer newKey = key.nextNumber(ORDER_NUMBER_KEY);
                order.setOrderNo(String.format("ORD-%06d", newKey));
            }
            return super.save(order);
        } catch (Exception ex) {
            if (!orderIsPersisted) {
                order.beginInitialising();
                order.setOrderNo(DEFAULT_ORDER_NUMBER_KEY);
                order.endInitialising();
            } throw ex;
        }
        
    }

    @Override
    protected IFetchProvider<Order> createFetchProvider() {

        return FETCH_PROVIDER;

    }
}
