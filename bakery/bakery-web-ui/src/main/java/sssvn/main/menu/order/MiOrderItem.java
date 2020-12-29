package sssvn.main.menu.order;

import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.ui.menu.MiWithConfigurationSupport;
import sssvn.order.OrderItem;
/**
 * Main menu item representing an entity centre for {@link OrderItem}.
 *
 * @author Developers
 *
 */
@EntityType(OrderItem.class)
public class MiOrderItem extends MiWithConfigurationSupport<OrderItem> {

}
