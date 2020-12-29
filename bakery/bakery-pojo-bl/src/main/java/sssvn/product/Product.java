package sssvn.product;

import sssvn.product.validators.ProductNameValidator;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.Unique;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Represents a product.
 *
 * @author Generated
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Product")
@DescTitle(value = "Description", desc = "Product's description")
@MapEntityTo
@CompanionObject(ProductCo.class)
@DisplayDescription
public class Product extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Product.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Name", desc = "A name that represents the product uniquely.")
    @CompositeKeyMember(1)
    @BeforeChange(@Handler(ProductNameValidator.class))
    private String name;

    @IsProperty
    @MapTo
    @Title("Price")
    @Required
    private Money price;

    @IsProperty
    @MapTo
    @Title("Recipe")
    private String recipe;

    @Override
    @Observable
    public Product setDesc(final String desc) {
        super.setDesc(desc);
        return this;
    }
    
    @Observable
    public Product setName(String name) {
        this.name = name;
        return this;
    }
    
    @Observable
    public Product setRecipe(final String recipe) {
        this.recipe = recipe;
        return this;
    }
    
    @Observable
    public Product setPrice(final Money price) {
        this.price = price;
        return this;
    }
    
    @Override
    @Observable
    public Product setActive(boolean active) {

        super.setActive(active);
        return this;
    }

    
    public String getName() {
        return name;
    }

    public String getRecipe() {
        return recipe;
    }
    
    public Money getPrice() {
        return price;
    }

}