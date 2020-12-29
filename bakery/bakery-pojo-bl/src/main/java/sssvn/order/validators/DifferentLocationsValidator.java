package sssvn.order.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import sssvn.location.Location;
import sssvn.order.Order;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.utils.EntityUtils;

public class DifferentLocationsValidator extends AbstractBeforeChangeEventHandler<Location> {
	
    public static final String SAME_LOC_NOT_PERMITTED = "Order can not have same locationFrom and locationTo";
    
    @Override
    public Result handle(MetaProperty<Location> property, Location locationFrom, Set<Annotation> mutatorAnnotations) {
        
        final Order order = property.getEntity();
        
        if (order.getLocationFrom() != null && order.getLocationTo() != null) {
       
            if (EntityUtils.equalsEx(order.getLocationFrom().getAddress(), order.getLocationTo().getAddress())) {
                return Result.failure(SAME_LOC_NOT_PERMITTED);
            }
        }
        
        
        return Result.successful(order);
    }

    

}
