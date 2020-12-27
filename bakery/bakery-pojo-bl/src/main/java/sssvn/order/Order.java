package sssvn.order;

import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(String.class)
@KeyTitle("Key")
@CompanionObject(OrderCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
public class Order extends AbstractPersistentEntity<String> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Order.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "orderNo", desc = "number of the order")
    private String orderNo;
    
    @IsProperty
    @MapTo
    @Title(value = "locationFrom", desc = "Location where the Carrier would take the goods")
    private String locationFrom;
    
    @IsProperty
    @MapTo
    @Title(value = "locationTo", desc = "Location where the Carrier bring the goods")
    private String locationTo;
    
    
    @IsProperty
    @MapTo
    @Title(value = "carrier", desc = "Desc")
    private String carrier;

    @Observable
    public Order setCarrier(final String carrier) {
        this.carrier = carrier;
        return this;
    }

    public String getCarrier() {
        return carrier;
    }
    

    @Observable
    public Order setLocationFrom(final String locationFrom) {
        this.locationFrom = locationFrom;
        return this;
    }

    public String getLocationFrom() {
        return locationFrom;
    }
    
    @Observable
    public Order setLocationTo(final String locationTo) {
        this.locationTo = locationTo;
        return this;
    }

    public String getLocationTo() {
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
