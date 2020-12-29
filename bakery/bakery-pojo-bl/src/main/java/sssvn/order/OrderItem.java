package sssvn.order;

import java.lang.annotation.Target;

import sssvn.order.Order;
import sssvn.order.validators.ProductAmountForOrderValidator;
import sssvn.product.Product;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * One-2-Many entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Order")
@CompanionObject(OrderItemCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
//@DescRequired
public class OrderItem extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(OrderItem.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    @IsProperty
    @MapTo
    @Required
    @Title(value = "Order", desc = "An order associated with the current order item.")
    @CompositeKeyMember(1)
    private Order order;

    @IsProperty
    @MapTo
    @Required
    @Title(value = "Product", desc = "A product which is included into Order.")
    @CompositeKeyMember(2)
    @BeforeChange(@Handler(ProductAmountForOrderValidator.class))
    private Product product;
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Quantity", desc = "A quantity of a product which is included into Order.")
    private int quantity;
    
    @Observable
    public OrderItem setOrder(final Order order) {
        this.order = order;
        return this;
    }

    public Order getOrder() {
        return order;
    }
    
    @Observable
    public OrderItem setProduct(final Product product) {
        this.product = product;
        return this;
    }

    public Product getProduct() {
        return product;
    }
    
    
    @Observable
    public OrderItem setQuantity(final int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }
    
}