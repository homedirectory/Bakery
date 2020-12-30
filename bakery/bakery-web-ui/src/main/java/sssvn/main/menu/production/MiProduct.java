package sssvn.main.menu.production;

import sssvn.production.Product;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.ui.menu.MiWithConfigurationSupport;
/**
 * Main menu item representing an entity centre for {@link Product}.
 *
 * @author Developers
 *
 */
@EntityType(Product.class)
public class MiProduct extends MiWithConfigurationSupport<Product> {

}
