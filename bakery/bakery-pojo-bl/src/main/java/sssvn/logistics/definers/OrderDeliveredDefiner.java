package sssvn.logistics.definers;

import java.util.Date;

import sssvn.logistics.Order;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class OrderDeliveredDefiner extends AbstractAfterChangeEventHandler<Boolean> {

	@Override
	public void handle(MetaProperty<Boolean> property, Boolean delivered) {
		
		Order order = property.getEntity();
		Date deliveryDate = order.getDeliveryDate();
		
		if (delivered && deliveryDate == null) {
			order.getProperty("deliveryDate").setValue(new Date());
		}

	}

}
