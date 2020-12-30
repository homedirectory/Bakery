package sssvn.dev_mod.util;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;

import sssvn.config.ApplicationDomain;
import sssvn.logistics.Location;
import sssvn.logistics.Order;
import sssvn.logistics.OrderItem;
import sssvn.personnel.Carrier;
import sssvn.personnel.Employment;
import sssvn.personnel.Manager;
import sssvn.personnel.ManagerCo;
import sssvn.personnel.Person;
import sssvn.personnel.PersonCo;
import sssvn.production.Product;
import ua.com.fielden.platform.devdb_support.DomainDrivenDataPopulation;
import ua.com.fielden.platform.entity.AbstractEntity;
import ua.com.fielden.platform.persistence.HibernateUtil;
import ua.com.fielden.platform.security.user.User;
import ua.com.fielden.platform.test.IDomainDrivenTestCaseConfiguration;
import ua.com.fielden.platform.types.Hyperlink;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.DbUtils;

/**
 * This is a convenience class for (re-)creation of the development database and its population.
 * 
 * It contains the <code>main</code> method and can be executed whenever the target database needs to be (re-)set.
 * <p>
 * 
 * <b>IMPORTANT: </b><i>One should be careful not to run this code against the deployment or production databases, which would lead to the loss of all data.</i>
 * 
 * <p>
 * 
 * @author TG Team
 * 
 */
public class PopulateDb extends DomainDrivenDataPopulation {
	private static final Logger LOGGER = getLogger(PopulateDb.class);

    private final ApplicationDomain applicationDomainProvider = new ApplicationDomain();

    private PopulateDb(final IDomainDrivenTestCaseConfiguration config, final Properties props) {
        super(config, props);
    }

    public static void main(final String[] args) throws Exception {
        LOGGER.info("Initialising...");
        final String configFileName = args.length == 1 ? args[0] : "application.properties";
        final Properties props = new Properties();
        try (final FileInputStream in = new FileInputStream(configFileName)) {
            props.load(in);
        }

        LOGGER.info("Obtaining Hibernate dialect...");
        final Class<?> dialectType = Class.forName(props.getProperty("hibernate.dialect"));
        final Dialect dialect = (Dialect) dialectType.newInstance();
        LOGGER.info(format("Running with dialect %s...", dialect));
        final DataPopulationConfig config = new DataPopulationConfig(props);
        LOGGER.info("Generating DDL and running it against the target DB...");

        // use TG DDL generation or
        // Hibernate DDL generation final List<String> createDdl = DbUtils.generateSchemaByHibernate()
        final List<String> createDdl = config.getDomainMetadata().generateDatabaseDdl(dialect);
        final List<String> ddl = dialect instanceof H2Dialect ?
                                 DbUtils.prependDropDdlForH2(createDdl) :
                                 DbUtils.prependDropDdlForSqlServer(createDdl);
        DbUtils.execSql(ddl, config.getInstance(HibernateUtil.class).getSessionFactory().getCurrentSession());

        final PopulateDb popDb = new PopulateDb(config, props);
        popDb.populateDomain();
    }

    @Override
    protected void populateDomain() {
        LOGGER.info("Creating and populating the development database...");
        
        setupUser(User.system_users.SU, "sssvn");
        setupPerson(User.system_users.SU, "sssvn");
        
        setupNonUserPerson("MR", "manager", "Manager Role", false, "+380994532562", "mr@gmail.com");
        final Person p1 = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "MR");
        final Person person1 = p1.setGenerateEmployeeNo(true);
        setupManager(person1);

        final Manager mg = co(Manager.class).findByKeyAndFetch(ManagerCo.FETCH_PROVIDER.fetchModel(), "MR");
        setupCarrier(save(new_(Person.class).setInitials("CR").setTitle("carrier").setDesc("Carrier Role").setEmployeeNo("123").setPhone("+380994562542").setAManager(mg).setEmail("cr@gmail.com").setActive(true)));
        
        final Person p2 = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "CR");
        final Date startDate = dateTime("2021-01-28 00:00:00").toDate();
        final Date finishDate = dateTime("2021-01-30 00:00:00").toDate();
        
        final Hyperlink contractDocument = new Hyperlink("https://legaltemplates.net/wp-content/uploads/2016/01/employment-contract-template.png");
		
        setupEmployment(p2, startDate, finishDate, contractDocument, Money.of("1500.00"));
        
        setupLocation("Ukraine", "Lviv", "Kozelnytska 2a", 33L, "+380987654321", "8:00 - 22:00", "Morning croissant bakery point.");
        setupLocation("Ukraine", "Lviv", "Kulparkivska 200a", 24L, "+380984321456", "9:00 - 22:00", "Croissant takeaway.");
        
        setupProduct("Croissant with chocolate", Money.of("12.00"), "Chocolate bar, sugar powder, eggs, flour, sugar, vanilla");
        setupProduct("Napoleon", Money.of("23.00"), "sugar powder, eggs, flour, sugar, vanilla, sour cream");
 
        final Location l1 = co(Location.class).findByKey("Kozelnytska 2a");
        final Location l2 = co(Location.class).findByKey("Kulparkivska 200a");
        final Carrier cr = co(Carrier.class).findByKey("CR");

        final Date orderDate = dateTime("2021-01-28 00:00:00").toDate();
        final Order order = setupOrder(l1, l2, cr, orderDate);
        
        final Product product1 = co(Product.class).findByKey("Croissant with chocolate");
               
        setupOrderItem(order, product1, 150L);
		
        LOGGER.info("Completed database creation and population.");
	}

    private void setupPerson(final User.system_users defaultUser, final String emailDomain) {
        final User su = co(User.class).findByKey(defaultUser.name());
        save(new_(Person.class).setInitials(defaultUser.name()).setActive(true).setUser(su).setDesc("Person who is a user").setEmail(defaultUser + "@" + emailDomain));
    }
    
    private void setupNonUserPerson(final String initials, final String title, final String desc, final boolean generateEmployeeNo, final String phone, final String email) {
        save(new_(Person.class).setInitials(initials).setTitle(title).setDesc(desc).setGenerateEmployeeNo(generateEmployeeNo).setPhone(phone).setEmail(email).setActive(true));
    }
    
    
    private void setupLocation(final String country, final String city, final String address, final Long employeeAmount, final String phone, final String workingHours, final String desc) {
        save(new_(Location.class).setCountry(country).setCity(city).setAddress(address).setEmployeesAmount(employeeAmount).setPhone(phone).setWorkingHours(workingHours).setDesc(desc));
    }
    
    private Order setupOrder(final Location locationFrom, final Location locationTo, final Carrier carrier, final Date orderDate) {
        return save(new_(Order.class).setLocationFrom(locationFrom).setLocationTo(locationTo).setCarrier(carrier).setOrderDate(orderDate));
    }

    private void setupOrderItem(final Order order, final Product product, final Long quantity) {
        save(new_(OrderItem.class).setOrder(order).setProduct(product).setQuantity(quantity));
    }
    
    private void setupProduct(final String name, final Money price, final String recipe) {
        save(new_(Product.class).setName(name).setPrice(price).setRecipe(recipe));
    }
    
    private void setupCarrier(final Person person) {
        save(person.setCarrier(true));
    }
    
    private void setupManager(final Person person) {
    	save(person.setManager(true));
    }
    
    private void setupEmployment(final Person employee, final Date startDate, final Date finishDate, final Hyperlink contractDocument, Money salary) {
        save(new_(Employment.class).setEmployee(employee).setStartDate(startDate).setFinishDate(finishDate).setContractDocument(contractDocument).setSalary(salary));
    }

    @Override
    protected List<Class<? extends AbstractEntity<?>>> domainEntityTypes() {
        return applicationDomainProvider.entityTypes();
    }

}

