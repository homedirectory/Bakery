package sssvn.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import sssvn.personnel.Employment;
import sssvn.personnel.Person;
import sssvn.personnel.PersonCo;
import sssvn.personnel.validators.EmploymentStartDateValidator;
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
public class EmploymentTest extends AbstractDaoTestCase {

    /**
     * The names of the test method should be informative. It is recommended to make the method name sound like a sentence stating the expected behaviour. In this case, the test
     * method name indicates that it is expected to find person with initials RDM and that it has an active status.
     * <p>
     * Each test method should be related to exactly one concern, which facilitates creation of unit tests that address a single concern.
     */
	
	@Test
	public void employment_does_not_permit_intersecting_periods_for_the_same_person() {
		final Date finish = dateTime("2021-01-30 00:00:00").toDate();
		
		Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
		person = save(person.setEmployeeNo("123").setTitle("title").setManager(true));
		Employment employment = save(new_(Employment.class).setEmployee(person).setStartDate(dateTime("2020-12-01 00:00:00").toDate()).setFinishDate(finish));
		
		// employmentStart ........ employment1Start ......... employmentFinish
		Employment employment1 = new_(Employment.class).setEmployee(person).setStartDate(dateTime("2020-12-02 00:00:00").toDate());
		
        final MetaProperty<Date> mp = employment1.getProperty("startDate");
        assertFalse(mp.isValid());
        assertEquals(EmploymentStartDateValidator.ERR_EMPLOYEE_CURR_EMPLOYMENT_INTERSECTION, mp.getFirstFailure().getMessage());

		// employment2Start .............. employmentStart ................. employmentFinish ................ employment2Finish
        Employment employment2 = new_(Employment.class).setEmployee(person).setStartDate(dateTime("2019-12-01 00:00:00").toDate())
        		.setFinishDate(dateTime("2021-12-01 00:00:00").toDate());
        
        final MetaProperty<Date> mp2 = employment2.getProperty("finishDate");
        assertFalse(mp2.isValid());
        assertEquals(EmploymentStartDateValidator.ERR_EMPLOYEE_CURR_EMPLOYMENT_INTERSECTION, mp2.getFirstFailure().getMessage());
	}


    /**
     * In case of a complex data population it is possible to store the data into a script by changing this method to return <code>true</code>.
     * <p>
     * This way it is possible to reuse it later in place of re-running the data population logic, which is a lot faster. Please also refer method
     * {@link #useSavedDataPopulationScript()} below -- the values returned by this and that method cannot be <code>true</code> simultaneously.
     */
    @Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
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
