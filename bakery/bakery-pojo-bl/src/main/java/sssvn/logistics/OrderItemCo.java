package sssvn.logistics;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link OrderItem}.
 *
 * @author Developers
 *
 */
public interface OrderItemCo extends IEntityDao<OrderItem> {

    static final IFetchProvider<OrderItem> FETCH_PROVIDER = EntityUtils.fetch(OrderItem.class).with(
        // TODO: uncomment the following line and specify the properties, which are required for the UI. Then remove the line after.
         "order", "product","quantity");
//        "Please specify the properties, which are required for the UI");

}