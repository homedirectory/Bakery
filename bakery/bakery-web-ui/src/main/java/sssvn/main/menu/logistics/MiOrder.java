package sssvn.main.menu.logistics;

import sssvn.logistics.Order;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.ui.menu.MiWithConfigurationSupport;
/**
 * Main menu item representing an entity centre for {@link Order}.
 *
 * @author Developers
 *
 */
@EntityType(Order.class)
public class MiOrder extends MiWithConfigurationSupport<Order> {

}
