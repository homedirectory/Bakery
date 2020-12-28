package sssvn.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;

import sssvn.location.Location;
import sssvn.order.Order;
import sssvn.order.OrderCo;
import sssvn.order.validators.DifferentLocationsValidator;
import sssvn.personnel.Carrier;
import sssvn.personnel.CarrierCo;
import sssvn.personnel.Person;
import sssvn.test_config.AbstractDaoTestCase;
import sssvn.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class OrderTest extends AbstractDaoTestCase {

	@Test
    public void order_is_present() {
    	final Order order = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "123");
    	assertNotNull(order);
    }
	
	@Test
    public void order_does_not_permit_same_locations() {

        final Order order = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "123");
        assertNotNull(order);
        
        assertEquals(order.getOrderNo(), "123");
    	
        
//        order.setLocationTo(new_(Location.class).setCountry("Ukraine").setCity("Lviv").setAddress("Shevchenka street, 14").setEmployeesAmount(33L).setPhone("+380998876330").setWorkingHours("9:00 - 22:00"));
        
//        final MetaProperty<Location> mp = order.getProperty("locationFrom"); 
       
//        System.out.println(order.getProperty("orderNo"));
//        assertFalse(mp.isDirty());
//        
//        
//        assertEquals(DifferentLocationsValidator.SAME_LOC_NOT_PERMITTED, mp.getFirstFailure().getMessage());
//        
        
	}
	
	@Override
    public boolean saveDataPopulationScriptToFile() {
        return true;
    }
	
	@Override
    public boolean useSavedDataPopulationScript() {
        return false;
    }
	
	@Override
    protected void populateDomain() {
    	super.populateDomain();
    	final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
    	constants.setNow(dateTime("2019-10-01 11:30:00"));
    	
        if (useSavedDataPopulationScript()) {
            return;
         }
        
         Location locationFrom = save(new_(Location.class).setCountry("Ukraine").setCity("Lviv").setAddress("Shevchenka street, 14").setEmployeesAmount(33L).setPhone("+380998876330").setWorkingHours("9:00 - 22:00").setActive(true));
        
         Location locationTo = save(new_(Location.class).setCountry("Ukraine").setCity("Lviv").setAddress("Kozelnytska, 2a").setEmployeesAmount(12L).setPhone("+380987654321").setWorkingHours("8:00 - 22:00").setActive(true));
         
         Person p = save(new_(Person.class).setInitials("ALO").setActive(true).setDesc("pes").setEmployeeNo("123").setTitle("boss").setCarrier(true).setManager(true));
         
         final Optional<Carrier> carrier = co$(Carrier.class).findByKeyAndFetchOptional(CarrierCo.FETCH_PROVIDER.fetchModel(), p);
         
         
        
         save(new_(Order.class).setOrderNo("123").setLocationFrom(locationFrom).setLocationTo(locationTo).setCarrier(carrier.get().setActive(true)));
                 
         
     }
	
}
