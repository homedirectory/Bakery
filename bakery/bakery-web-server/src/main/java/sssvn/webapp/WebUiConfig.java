package sssvn.webapp;

import static ua.com.fielden.platform.reflection.TitlesDescsGetter.getEntityTitleAndDesc;

import org.apache.commons.lang3.StringUtils;

import sssvn.logistics.Location;
import sssvn.logistics.Order;
import sssvn.logistics.OrderItem;
import sssvn.personnel.Carrier;
import sssvn.personnel.Employment;
import sssvn.personnel.Manager;
import sssvn.personnel.Person;
import sssvn.production.Product;
import sssvn.config.Modules;
import sssvn.config.personnel.PersonWebUiConfig;
import sssvn.webapp.config.logistics.LocationWebUiConfig;
import sssvn.webapp.config.logistics.OrderItemWebUiConfig;
import sssvn.webapp.config.logistics.OrderWebUiConfig;
import sssvn.webapp.config.personnel.CarrierWebUiConfig;
import sssvn.webapp.config.personnel.EmploymentWebUiConfig;
import sssvn.webapp.config.personnel.ManagerWebUiConfig;
import sssvn.webapp.config.production.ProductWebUiConfig;
import ua.com.fielden.platform.basic.config.Workflows;
import ua.com.fielden.platform.entity.AbstractEntity;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.resources.webui.AbstractWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.SecurityMatrixWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.UserRoleWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.UserWebUiConfig;

/**
 * App-specific {@link IWebApp} implementation.
 *
 * @author Generated
 *
 */
public class WebUiConfig extends AbstractWebUiConfig {

    public static final String WEB_TIME_WITH_MILLIS_FORMAT = "HH:mm:ss.SSS";
    public static final String WEB_TIME_FORMAT = "HH:mm";
    public static final String WEB_DATE_FORMAT_JS = "DD/MM/YYYY";
    public static final String WEB_DATE_FORMAT_JAVA = fromJsToJavaDateFormat(WEB_DATE_FORMAT_JS);

    private final String domainName;
    private final String path;
    private final int port;

    public WebUiConfig(final String domainName, final int port, final Workflows workflow, final String path) {
        super("Bakery Management System", workflow, new String[] { "sssvn/" });
        if (StringUtils.isEmpty(domainName) || StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Both the domain name and application binding path should be specified.");
        }
        this.domainName = domainName;
        this.port = port;
        this.path = path;
    }


    @Override
    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int getPort() {
        return port;
    }

    /**
     * Configures the {@link WebUiConfig} with custom centres and masters.
     */
    @Override
    public void initConfiguration() {
        super.initConfiguration();

        final IWebUiBuilder builder = configApp();
        builder.setDateFormat(WEB_DATE_FORMAT_JS).setTimeFormat(WEB_TIME_FORMAT).setTimeWithMillisFormat(WEB_TIME_WITH_MILLIS_FORMAT)
        .setMinTabletWidth(600);

        // Users and Personnel Module
        final EmploymentWebUiConfig employmentWebUiConfig = EmploymentWebUiConfig.register(injector(), builder);
        final CarrierWebUiConfig carrierWebUiConfig = CarrierWebUiConfig.register(injector(), builder);
        final ManagerWebUiConfig managerWebUiConfig = ManagerWebUiConfig.register(injector(), builder);
        final PersonWebUiConfig personWebUiConfig = PersonWebUiConfig.register(injector(), builder);
        final UserWebUiConfig userWebUiConfig = new UserWebUiConfig(injector());
        final UserRoleWebUiConfig userRoleWebUiConfig = new UserRoleWebUiConfig(injector());
        final SecurityMatrixWebUiConfig securityConfig = SecurityMatrixWebUiConfig.register(injector(), configApp());
        
        // Logistics Module
        final LocationWebUiConfig locationWebUiConfig = LocationWebUiConfig.register(injector(), builder);
        final OrderWebUiConfig orderWebUiConfig = OrderWebUiConfig.register(injector(), builder);
        final OrderItemWebUiConfig orderItemWebUiConfig = OrderItemWebUiConfig.register(injector(), builder);
        
        // Production Module
        final ProductWebUiConfig productWebUiConfig = ProductWebUiConfig.register(injector(), builder);

        // Add user-rated masters and centres to the configuration 
        configApp()
        .addMaster(userWebUiConfig.master)
        .addMaster(userWebUiConfig.rolesUpdater)
        .addMaster(userRoleWebUiConfig.master)
        .addMaster(userRoleWebUiConfig.tokensUpdater)
        .addCentre(userWebUiConfig.centre)
        .addCentre(userRoleWebUiConfig.centre);

        // Configure application menu
        configDesktopMainMenu()
        .addModule(Modules.USERS_AND_PERSONNEL.title)
            .description(Modules.USERS_AND_PERSONNEL.desc)
            .icon(Modules.USERS_AND_PERSONNEL.icon)
            .detailIcon(Modules.USERS_AND_PERSONNEL.icon)
            .bgColor(Modules.USERS_AND_PERSONNEL.bgColour)
            .captionBgColor(Modules.USERS_AND_PERSONNEL.captionBgColour)
            .menu()
                .addMenuItem(mkMenuItemTitle(Person.class)).description(mkMenuItemDesc(Person.class)).centre(personWebUiConfig.centre).done()                
                .addMenuItem(mkMenuItemTitle(Manager.class)).description(mkMenuItemDesc(Manager.class)).centre(managerWebUiConfig.centre).done()
                .addMenuItem(mkMenuItemTitle(Carrier.class)).description(mkMenuItemDesc(Carrier.class)).centre(carrierWebUiConfig.centre).done()
                .addMenuItem(mkMenuItemTitle(Employment.class)).description(mkMenuItemDesc(Employment.class)).centre(employmentWebUiConfig.centre).done()
                .addMenuItem("System Users").description("Functionality for managing system users, athorisation, etc.")
                    .addMenuItem("Users").description("User centre").centre(userWebUiConfig.centre).done()
                    .addMenuItem("User Roles").description("User roles centre").centre(userRoleWebUiConfig.centre).done()
                    .addMenuItem("Security Matrix").description("Security Matrix is used to manage application authorisations for User Roles.").master(securityConfig.master).done()
                .done()
            .done().done()
        .addModule(Modules.LOGISTICS.title)
	        .description(Modules.LOGISTICS.desc)
	        .icon(Modules.LOGISTICS.icon)
	        .detailIcon(Modules.LOGISTICS.icon)
	        .bgColor(Modules.LOGISTICS.bgColour)
	        .captionBgColor(Modules.LOGISTICS.captionBgColour)
	        .menu()
	        	.addMenuItem(mkMenuItemTitle(Location.class)).description(mkMenuItemDesc(Location.class)).centre(locationWebUiConfig.centre).done()
	        	.addMenuItem(mkMenuItemTitle(Order.class)).description(mkMenuItemDesc(Order.class)).centre(orderWebUiConfig.centre).done()
	        	.addMenuItem(mkMenuItemTitle(OrderItem.class)).description(mkMenuItemDesc(OrderItem.class)).centre(orderItemWebUiConfig.centre).done()
	        .done().done()
        .addModule(Modules.PRODUCTION.title)
	        .description(Modules.PRODUCTION.desc)
	        .icon(Modules.PRODUCTION.icon)
	        .detailIcon(Modules.PRODUCTION.icon)
	        .bgColor(Modules.PRODUCTION.bgColour)
	        .captionBgColor(Modules.PRODUCTION.captionBgColour)
	        .menu()
	        	.addMenuItem(mkMenuItemTitle(Product.class)).description(mkMenuItemDesc(Product.class)).centre(productWebUiConfig.centre).done()
	        .done().done()
        .setLayoutFor(Device.DESKTOP, null, "[ [ [], [], [] ] ]")
        .setLayoutFor(Device.TABLET, null, "[[[]]]")
        .setLayoutFor(Device.MOBILE, null, "[[[]]]")
        .minCellWidth(100).minCellHeight(148).done();
    }

    private static String fromJsToJavaDateFormat(final String dateFormatJs) {
        return dateFormatJs.replace("DD", "dd").replace("YYYY", "yyyy"); // UPPERCASE "Y" is "week year" in Java, therefore we prefer lowercase "y"
    }

    public static String mkMenuItemTitle(final Class<? extends AbstractEntity<?>> entityType) {
        return getEntityTitleAndDesc(entityType).getKey();
    }

    public static final String CENTRE_SUFFIX = " Centre";
    public static String mkMenuItemDesc(final Class<? extends AbstractEntity<?>> entityType) {
        final Pair<String, String> titleDesc = TitlesDescsGetter.getEntityTitleAndDesc(entityType);
        // Some @EntityTitle desc are not specified, while the others are worded as whole sentence ending with "." - use value in both cases
        return titleDesc.getValue().isEmpty() || titleDesc.getValue().endsWith(".") ? titleDesc.getKey() + CENTRE_SUFFIX : titleDesc.getValue() + CENTRE_SUFFIX;
    }

}