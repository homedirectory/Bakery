package sssvn.order;

import sssvn.location.Location;
import sssvn.order.validators.DifferentLocationsValidator;
import sssvn.personnel.Carrier;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.Dependent;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Readonly;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle(value="Order", desc = "Order of goods within the network of bakeries")
@CompanionObject(OrderCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription

public class Order extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Order.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @CompositeKeyMember(1)
    @Readonly
    @Title(value = "Order Number", desc = "number of the order")
    private String orderNo;
    
    @IsProperty
    @MapTo
    @Title(value = "Departure", desc = "Location where the Carrier would take the goods")
    @BeforeChange(@Handler(DifferentLocationsValidator.class))
    private Location locationFrom;
    
    @IsProperty
    @MapTo
    @Title(value = "Destination", desc = "Location where the Carrier bring the goods")
    @Dependent({"locationFrom"})
    private Location locationTo;
    
    
    @IsProperty
    @MapTo
    @Title(value = "Carrier", desc = "The carrier, that is assigned to make a delivery")
    @Required
    private Carrier carrier;
    
    @Override
    @Observable
    protected Order setActive(boolean active) {
        
        super.setActive(active);
        return this;
    }
    

    @Observable
    public Order setCarrier(final Carrier carrier) {
        this.carrier = carrier;
        return this;
    }

    public Carrier getCarrier() {
        return carrier;
    }
    

    @Observable
    public Order setLocationFrom(final Location locationFrom) {
        this.locationFrom = locationFrom;
        return this;
    }

    public Location getLocationFrom() {
        return locationFrom;
    }
    
    @Observable
    public Order setLocationTo(final Location locationTo) {
        this.locationTo = locationTo;
        return this;
    }

    public Location getLocationTo() {
        return locationTo;
    }

    

    @Observable
    public Order setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    

}
