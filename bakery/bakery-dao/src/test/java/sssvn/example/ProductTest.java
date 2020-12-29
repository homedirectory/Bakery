package sssvn.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import sssvn.product.Product;
import sssvn.product.ProductCo;
import sssvn.test_config.AbstractDaoTestCase;
import sssvn.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.IUniversalConstants;

/**
 * This is an example unit test, which can be used as a starting point for creating application unit tests.
 * 
 * @author Generated
 *
 */
public class ProductTest extends AbstractDaoTestCase {

    /**
     * The names of the test method should be informative. It is recommended to make the method name sound like a sentence stating the expected behaviour. In this case, the test
     * method name indicates that it is expected to find person with initials RDM and that it has an active status.
     * <p>
     * Each test method should be related to exactly one concern, which facilitates creation of unit tests that address a single concern.
     */

    @Test
    public void product_is_present() {
        final Product product = co(Product.class).findByKey("Croissant");
        assertNotNull(product);     
        
    }
    
    
    @Test
    public void price_must_be_present_for_product() {
        final Product product2 = new_(Product.class).setName("Toast");
        assertNotNull(product2);

        final MetaProperty<String> mp = product2.getProperty("price");
        
        assertTrue(mp.isValid());
        
        product2.setName("Croissant Classic");
        assertTrue(mp.isRequired());
        assertFalse(product2.isValid().isSuccessful());
        
        product2.setPrice(Money.of("30.00"));
        assertTrue(product2.isValid().isSuccessful());
        

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
        save(new_(Product.class).setName("Croissant").setDesc("Croissant Classic").setPrice(Money.of("34.00")).setRecipe("Butter, flour"));
        
    }

}
