package sssvn.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import sssvn.location.Location;
import sssvn.order.Order;
import sssvn.order.OrderCo;
import sssvn.order.OrderItem;
import sssvn.order.OrderItemCo;
import sssvn.order.validators.DifferentLocationsValidator;
import sssvn.order.validators.ProductAmountForOrderValidator;
import sssvn.personnel.Carrier;
import sssvn.personnel.CarrierCo;
import sssvn.personnel.Person;
import sssvn.personnel.validators.PersonInitialsValidator;
import sssvn.product.Product;
import sssvn.product.ProductCo;
import sssvn.test_config.AbstractDaoTestCase;
import sssvn.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class OrderTest extends AbstractDaoTestCase {

    @Test
    public void order_is_present() {
        final Order order = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "123");
        assertNotNull(order);
    }

    @Test
    public void order_does_not_permit_same_locations() {

        final Order order = co$(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "123");
        assertNotNull(order);

        assertEquals(order.getOrderNo(), "123");

        Location loc = (Location) order.getProperty("locationTo").getValue();

        order.setLocationFrom(loc);

        final MetaProperty<Location> mp = order.getProperty("locationFrom");

        assertFalse(mp.isDirty());

        assertEquals(DifferentLocationsValidator.ERR_SAME_LOC_NOT_PERMITTED, mp.getFirstFailure().getMessage());

    }
    
    @Test
    public void order_item_is_present() {
        final Order order = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "123");
        assertNotNull(order);
        
        final Product product = co(Product.class).findByKeyAndFetch(ProductCo.FETCH_PROVIDER.fetchModel(), "Cake");
		final OrderItem oi = co(OrderItem.class).findByKeyAndFetch(OrderItemCo.FETCH_PROVIDER.fetchModel(), order, product);
        assertNotNull(oi);
    }

    @Test
    public void the_product_that_was_already_added_to_order_cannot_be_added_again() {
    	final Order order1 = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "123");
		final Product product1 = co(Product.class).findByKeyAndFetch(ProductCo.FETCH_PROVIDER.fetchModel(), "Croissant");
		
		final Order order2 = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "124");
		final Product product2 = co(Product.class).findByKeyAndFetch(ProductCo.FETCH_PROVIDER.fetchModel(), "Cake"); 
		
        save(new_(OrderItem.class).setOrder(order1).setProduct(product1).setQuantity(11L));
		OrderItem oi = co$(OrderItem.class).findByKeyAndFetch(OrderItemCo.FETCH_PROVIDER.fetchModel(), order1, product1);
		
		assertTrue(oi.isValid().isSuccessful()); 
		String ERR_THE_SAME_PRODUCT = "";
		
		try {
			save(new_(OrderItem.class).setOrder(order1).setProduct(product2).setQuantity(11L));
			OrderItem oi2 = co$(OrderItem.class).findByKeyAndFetch(OrderItemCo.FETCH_PROVIDER.fetchModel(), order1, product2);
			assertTrue(oi.isValid().isSuccessful()); 
			ERR_THE_SAME_PRODUCT = "No error";
		} 
		catch (final Exception ex) {
			ERR_THE_SAME_PRODUCT = "An error occured. The product that was already added cannot be added to order again.";
		}
		assertTrue(ERR_THE_SAME_PRODUCT.equals("An error occured. The product that was already added cannot be added to order again."));
    }
    
    @Test
    public void the_order_cannot_contain_more_than_thrashold_amount_of_products() {
    	final Order order1 = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "123");
		final Product product1 = co(Product.class).findByKeyAndFetch(ProductCo.FETCH_PROVIDER.fetchModel(), "Croissant");
		
		final Order order2 = co(Order.class).findByKeyAndFetch(OrderCo.FETCH_PROVIDER.fetchModel(), "124");
		final Product product2 = co(Product.class).findByKeyAndFetch(ProductCo.FETCH_PROVIDER.fetchModel(), "Cake");
        
        final Product product3 = save(new_(Product.class).setName("Croissant with chocolate").setPrice(Money.of("15.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product4 = save(new_(Product.class).setName("Croissant with condensed milk").setPrice(Money.of("17.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product5 = save(new_(Product.class).setName("Napoleon").setPrice(Money.of("34.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product6 = save(new_(Product.class).setName("Ecler").setPrice(Money.of("5.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product7 = save(new_(Product.class).setName("Ecler sweet").setPrice(Money.of("6.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product8 = save(new_(Product.class).setName("Chocolate Mousse").setPrice(Money.of("17.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product9 = save(new_(Product.class).setName("Ice cream").setPrice(Money.of("23.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product10 = save(new_(Product.class).setName("Apple pie").setPrice(Money.of("12.00")).setRecipe("Flour, sugar").setActive(true));
        final Product product11 = save(new_(Product.class).setName("Spartak").setPrice(Money.of("70.00")).setRecipe("Flour, sugar").setActive(true));

        
        save(new_(OrderItem.class).setOrder(order1).setProduct(product1).setQuantity(11L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product3).setQuantity(2L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product4).setQuantity(17L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product5).setQuantity(7L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product6).setQuantity(21L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product7).setQuantity(6L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product8).setQuantity(18L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product9).setQuantity(56L));
        save(new_(OrderItem.class).setOrder(order1).setProduct(product10).setQuantity(13L));
        
        OrderItem oi1 = new_(OrderItem.class).setOrder(order1).setProduct(product11).setQuantity(13L);
        
        final MetaProperty<String> mp = oi1.getProperty("product");
        assertEquals(ProductAmountForOrderValidator.ERR_PRODUCT_AMOUNT_EXCEEDS_THRESHOLD, mp.getFirstFailure().getMessage());
		
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
        
        Location locationFrom1 = save(new_(Location.class).setCountry("Ukraine").setCity("Lviv").setAddress("Lesya Kurbasa street, 3").setEmployeesAmount(24L).setPhone("+380998876567").setWorkingHours("9:00 - 22:00").setActive(true));
        Location locationTo1 = save(new_(Location.class).setCountry("Ukraine").setCity("Lviv").setAddress("Kulparkivska, 200a").setEmployeesAmount(8L).setPhone("+380987052321").setWorkingHours("8:00 - 22:00").setActive(true));

        Person p = save(new_(Person.class).setInitials("ALO").setActive(true).setDesc("pes").setEmployeeNo("123").setTitle("boss").setCarrier(true).setManager(true));

        final Optional<Carrier> carrier = co$(Carrier.class).findByKeyAndFetchOptional(CarrierCo.FETCH_PROVIDER.fetchModel(), p);

        Order order1 = save(new_(Order.class).setOrderNo("123").setLocationFrom(locationFrom).setLocationTo(locationTo).setCarrier(carrier.get().setActive(true)));
        Order order2 = save(new_(Order.class).setOrderNo("124").setLocationFrom(locationFrom1).setLocationTo(locationTo1).setCarrier(carrier.get().setActive(true)));
        
        Product product1 = save(new_(Product.class).setName("Croissant").setPrice(Money.of("22.00")).setRecipe("Flour, sugar").setActive(true));
        Product product2 = save(new_(Product.class).setName("Cake").setPrice(Money.of("27.00")).setRecipe("Flour, sugar, eggs").setActive(true));
        
        save(new_(OrderItem.class).setOrder(order1).setProduct(product2).setQuantity(11L));
        save(new_(OrderItem.class).setOrder(order2).setProduct(product2).setQuantity(12L));
    }

}
