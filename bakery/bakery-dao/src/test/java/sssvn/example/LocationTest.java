package sssvn.example;

import static org.junit.Assert.*;

import org.junit.Test;

import sssvn.location.Location;
import sssvn.location.LocationCo;
import sssvn.location.validators.LocationPhoneValidator;
import sssvn.personnel.Person;
import sssvn.test_config.AbstractDaoTestCase;
import sssvn.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;
import ua.com.fielden.platform.entity.meta.MetaProperty;

public class LocationTest extends AbstractDaoTestCase {

	@Test
    public void location_is_present() {
    	final Location location = co(Location.class).findByKeyAndFetch(LocationCo.FETCH_PROVIDER.fetchModel(), "Kozelnytska, 2a");
    	assertNotNull(location);
    }
	
	@Test
    public void phone_does_not_permit_letters() {
    	final Location location = co$(Location.class).findByKeyAndFetch(LocationCo.FETCH_PROVIDER.fetchModel(), "Kozelnytska, 2a");
    	assertNotNull(location);
    	
    	System.out.println("Current phone : "+ location.getProperty("phone").toString());
    	
        location.setPhone("ojd2930");
        
        final MetaProperty<String> mp = location.getProperty("phone");
        assertFalse(mp.isDirty());
        assertEquals(LocationPhoneValidator.ERR_LETTERS_NOT_PERMITTED, mp.getFirstFailure().getMessage());
        
        location.setPhone("0998765h7");
        assertFalse(mp.isValid());
        assertEquals(mp.getValue(), "+380987654321");
        assertEquals(mp.getLastAttemptedValue(), "0998765h7");
        
        location.setPhone("099kdsoh7");
        assertEquals(mp.getLastInvalidValue(), "099kdsoh7");
        
        assertFalse(mp.isDirty());
        location.setPhone("+380993894400");
        assertTrue(mp.isDirty());
        assertTrue(location.isValid().isSuccessful());
	}
	
	@Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
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
        
         save(new_(Location.class).setCountry("Ukraine").setCity("Lviv").setAddress("Kozelnytska, 2a").setEmployeesAmount(12L).setPhone("+380987654321").setWorkingHours("8:00 - 22:00"));
         save(new_(Location.class).setCountry("Ukraine").setCity("Lviv").setAddress("Shevchenka street, 14").setEmployeesAmount(33L).setPhone("+380998876330").setWorkingHours("9:00 - 22:00"));
         
     }
	
}
