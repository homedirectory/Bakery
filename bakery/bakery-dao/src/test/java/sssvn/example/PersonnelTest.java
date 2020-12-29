package sssvn.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import sssvn.personnel.Carrier;
import sssvn.personnel.CarrierCo;
import sssvn.personnel.Manager;
import sssvn.personnel.ManagerCo;
import sssvn.personnel.Person;
import sssvn.personnel.PersonCo;
import sssvn.personnel.validators.PersonInitialsValidator;
import sssvn.test_config.AbstractDaoTestCase;
import sssvn.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.utils.IUniversalConstants;

/**
 * This is an example unit test, which can be used as a starting point for creating application unit tests.
 * 
 * @author Generated
 *
 */
public class PersonnelTest extends AbstractDaoTestCase {

    /**
     * The names of the test method should be informative. It is recommended to make the method name sound like a sentence stating the expected behaviour. In this case, the test
     * method name indicates that it is expected to find person with initials RDM and that it has an active status.
     * <p>
     * Each test method should be related to exactly one concern, which facilitates creation of unit tests that address a single concern.
     */

    @Test
    public void user_RMD_is_present_and_active() {
        final Person person = co(Person.class).findByKey("RMD");
        assertNotNull(person);
        assertTrue(person.isActive());
    }

    @Test
    public void user_JC_is_present_but_not_active() {
        final Person person = co(Person.class).findByKey("JC");
        assertNotNull(person);
        assertFalse(person.isActive());
    }

    @Test
    public void initials_do_not_permit_spaces() {
        final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
        assertNotNull(person);
        assertTrue(person.isValid().isSuccessful());

        person.setInitials("N T");

        final MetaProperty<String> mp = person.getProperty("initials");
        assertFalse(mp.isDirty());
        assertEquals(PersonInitialsValidator.ERR_SPACES_NOT_PERMITTED, mp.getFirstFailure().getMessage());
        person.setInitials("N TA");

        assertFalse(mp.isValid());
        assertEquals(mp.getValue(), "RMD");
        assertEquals(mp.getLastAttemptedValue(), "N TA");

        person.setInitials("A K");
        assertEquals(mp.getLastInvalidValue(), "A K");

        assertFalse(mp.isDirty());
        person.setInitials("NT");
        assertTrue(mp.isDirty());
        assertTrue(person.isValid().isSuccessful());

    }
    
    @Test
    public void position_must_be_present_for_employee() {
        final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
        assertNotNull(person);
        assertTrue(person.isValid().isSuccessful());

        final MetaProperty<String> mp = person.getProperty("title");
        
        assertTrue(mp.isValid());
        
        person.setEmployeeNo("123");
        assertTrue(mp.isRequired());
        
        assertFalse(person.isValid().isSuccessful());
        
        person.setEmployeeNo(null);
        assertFalse(mp.isRequired());
        
        person.setEmployeeNo("228");
        assertTrue(mp.isRequired());
        person.setTitle("manager");
        person.setManager(true);
        assertTrue(person.isValid().isSuccessful());

    }
    
    @Test
    public void employees_must_have_manager_assigned() {
      	final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
    	final MetaProperty<Person> mpAManager = person.getProperty("aManager");
    	assertFalse(mpAManager.isRequired());
    	
    	person.setEmployeeNo("SOME NUMBER");
    	assertTrue(mpAManager.isRequired());
    	
    	person.setEmployeeNo(null);
    	assertFalse(mpAManager.isRequired());
    	
    	person.setEmployeeNo("SOME NUMBER");
    	assertTrue(mpAManager.isRequired());
    	person.setManager(true);
    	assertFalse(mpAManager.isRequired());
    }
    
    @Test
    public void only_employees_can_be_manager_and_asigning_employee_after_failure_recovers_automatically() {
    	final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
    	
    	person.setEmployeeNo(null);
    	person.setManager(true);
    	assertFalse(person.isManager());
    	
    	person.setEmployeeNo("SOME NUMBER");
    	person.setManager(true);
//    	System.out.println(person.isManager());
    	assertTrue(person.isManager());
    }
    
    @Test
    public void no_one_can_manage_themselves() {
        final Person person1 = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
        final MetaProperty<Person> mpAManager = person1.getProperty("aManager");
    
        
        person1.setEmployeeNo("SOME NUMBER");
        person1.setManager(true);
        assertTrue(person1.isManager());
        
        final Manager manager1 = co$(Manager.class).findByKeyAndFetch(ManagerCo.FETCH_PROVIDER.fetchModel(), person1);
        person1.setAManager(manager1);
        assertFalse(person1.getAManager() != null);
        
    }
    
    @Test
    public void manager_cannot_manage_nonemployee() {
        final Person person1 = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
        final MetaProperty<Person> mpAManager = person1.getProperty("aManager");
    
        
        person1.setEmployeeNo("SOME NUMBER");
        person1.setManager(true);
        assertTrue(person1.isManager());
        
        final Manager manager1 = co$(Manager.class).findByKeyAndFetch(ManagerCo.FETCH_PROVIDER.fetchModel(), person1);
        
        final Person person2 = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
        assertFalse(person2.isEmployee());
        
        person2.setAManager(manager1);
        assertFalse(person2.getAManager() != null);
        
    }
    
    @Test
    public void carrier_is_created_after_person_is_given_carrier_role() {
    	final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
    	
    	person.setEmployeeNo("12312312");
    	person.setTitle("sss");
    	person.setManager(true);
    	person.setCarrier(true);
    	
    	final Person savedPerson = save(person);
    	
    	final Optional<Carrier> carrier = co$(Carrier.class).findByKeyAndFetchOptional(CarrierCo.FETCH_PROVIDER.fetchModel(), savedPerson);
    	
    	assertTrue(carrier.isPresent());
    	assertEquals(savedPerson, carrier.get().getPerson());
    }
    
    @Test
    public void carrier_does_not_require_manager() {
        final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
        
        person.setEmployeeNo("12312312");
        person.setTitle("carrier");
        person.setCarrier(true);
        
        final MetaProperty<Person> mpManager = person.getProperty("aManager");
        
        
        assertFalse(mpManager.isRequired());
        
        person.setCarrier(false);
        assertTrue(mpManager.isRequired());
        
    }


    /**
     * In case of a complex data population it is possible to store the data into a script by changing this method to return <code>true</code>.
     * <p>
     * This way it is possible to reuse it later in place of re-running the data population logic, which is a lot faster. Please also refer method
     * {@link #useSavedDataPopulationScript()} below -- the values returned by this and that method cannot be <code>true</code> simultaneously.
     */
    @Override
    public boolean saveDataPopulationScriptToFile() {
        return true;
    }

    /**
     * If the test data was populated and saved as a script file (hinted in method {@link #saveDataPopulationScriptToFile()} above), then this method can be changed to return
     * <code>true</code> in order to avoid execution of the data population logic and simply execute the saved script. This makes the population of the test data a lot faster. It
     * is very convenient when there is a need to run the same test case multiple times interactively.
     * <p>
     * However, this method should never return <code>true</code> when running multiple test cases. Therefore, it is important to change this method to return <code>false</code>
     * before committing changes into your VCS such as Git.
     */
    @Override
    public boolean useSavedDataPopulationScript() {
        return false;
    }

    /**
     * Domain state population method.
     * <p>
     * <b>IMPORTANT:
     * </p>
     * this method executes only once for a Test Case. At the same time, new instances of a Test Case are created for each test method. Thus, this method should not be used for
     * initialisation of the Test Case state other than the persisted domain state.
     */
    @Override
    protected void populateDomain() {
        // Need to invoke super to create a test user that is responsible for data population 
        super.populateDomain();

        // Here is how the Test Case universal constants can be set.
        // In this case the notion of now is overridden, which makes it possible to have an invariant system-time.
        // However, the now value should be after AbstractDaoTestCase.prePopulateNow in order not to introduce any date-related conflicts.
        final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
        constants.setNow(dateTime("2019-10-01 11:30:00"));

        // If the use of saved data population script is indicated then there is no need to proceed with any further data population logic.
        if (useSavedDataPopulationScript()) {
            return;
        }

        // Here the three Person entities are persisted using the the inherited from TG testing framework methods.
        save(new_(Person.class).setInitials("RMD").setDesc("Ronald McDonald").setActive(true));
        save(new_(Person.class).setInitials("JC").setDesc("John Carmack").setActive(false));
    }

}
