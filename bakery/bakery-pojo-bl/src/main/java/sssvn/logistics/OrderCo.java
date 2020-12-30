package sssvn.logistics;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Order}.
 *
 * @author Developers
 *
 */
public interface OrderCo extends IEntityDao<Order> {

    static final IFetchProvider<Order> FETCH_PROVIDER = EntityUtils.fetch(Order.class).with(
        // TODO: uncomment the following line and specify the properties, which are required for the UI. Then remove the line after.
         "orderNo", "desc", "locationFrom", "locationTo", "carrier");
        

}
