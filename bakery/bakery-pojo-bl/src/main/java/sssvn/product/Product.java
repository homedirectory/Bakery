package sssvn.product;

import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DescRequired;
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
@KeyTitle(value = "Name", desc = "Product`s name must represent the product uniquely")
@DescTitle(value = "Price", desc = "Product's price - e.g. integer value")
@MapEntityTo
//@CompanionObject(PersonCo.class)
@DescRequired
@DisplayDescription
public class Product extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Product.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

//    @IsProperty
//    @Unique
//    @MapTo
//    @Title(value = "User", desc = "An application user associated with the current person.")
//    @SkipEntityExistsValidation(skipActiveOnly = true)
//    private User user;
    
    
    @IsProperty
    @MapTo
    @Required
    @Unique
    @Title(value = "Name", desc = "Desc")
    @CompositeKeyMember(1)
//    @BeforeChange(@Handler(PersonInitialsValidator.class))
    private String name;

    @IsProperty
    @MapTo
    @Title(value = "Description", desc = "Product`s description")
    private String description;

    @IsProperty
    @MapTo
    @Title("Price")
    @Required
//    @AfterChange(PositionRequirednsessForEmployeeDefiner.class)
//    @Dependent({"manager"})
    private Money price;

    @IsProperty
    @MapTo
    @Title("Recipe")
    private String recipe;


    @Override
    @Observable
    public Product setDesc(final String desc) {
        return (Product) super.setDesc(desc);
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
    

    public String getDesc() {
        return description;
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