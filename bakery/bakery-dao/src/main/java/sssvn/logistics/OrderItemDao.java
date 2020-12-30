package sssvn.logistics;

import com.google.inject.Inject;

import sssvn.security.tokens.persistent.OrderItem_CanSave_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.security.Authorise;
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
         return FETCH_PROVIDER;
    }
    
    @Override
    @SessionRequired
    @Authorise(OrderItem_CanSave_Token.class)
	public OrderItem save(OrderItem entity) {
		return super.save(entity);
	}
}