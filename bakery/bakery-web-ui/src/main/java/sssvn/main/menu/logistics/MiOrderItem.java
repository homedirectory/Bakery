package sssvn.main.menu.logistics;

import sssvn.logistics.OrderItem;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.ui.menu.MiWithConfigurationSupport;
/**
 * Main menu item representing an entity centre for {@link OrderItem}.
 *
 * @author Developers
 *
 */
@EntityType(OrderItem.class)
public class MiOrderItem extends MiWithConfigurationSupport<OrderItem> {

}
