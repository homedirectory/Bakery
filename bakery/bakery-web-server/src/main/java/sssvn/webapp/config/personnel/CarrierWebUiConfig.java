package sssvn.webapp.config.personnel;

import static java.lang.String.format;
import static sssvn.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static ua.com.fielden.platform.web.PrefDim.mkDim;

import java.util.Optional;

import com.google.inject.Injector;

import sssvn.common.LayoutComposer;
import sssvn.common.StandardActions;
import sssvn.main.menu.personnel.MiCarrier;
import sssvn.personnel.Carrier;
import sssvn.personnel.Person;
import ua.com.fielden.platform.web.PrefDim.Unit;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
/**
 * {@link Carrier} Web UI configuration.
 *
 * @author Developers
 *
 */
public class CarrierWebUiConfig {

    public final EntityCentre<Carrier> centre;

    public static CarrierWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new CarrierWebUiConfig(injector, builder);
    }

    private CarrierWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
    }

    /**
     * Creates entity centre for {@link Carrier}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Carrier> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(1, 2);

        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Carrier.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<Carrier> ecc = EntityCentreBuilder.centreFor(Carrier.class)
                .runAutomatically()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Carrier.class).also()
                .addCrit("desc").asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("person").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Carrier.ENTITY_TITLE))
                    .withActionSupplier(builder.getOpenMasterAction(Person.class)).also()
                .addProp("desc").minWidth(100).also()
                .addProp("active").minWidth(50).also()
                .addProp("person.aManager").minWidth(100).also()
                .addProp("person.employeeNo").minWidth(100).also()
                .addProp("person.title").minWidth(100).also()
                .addProp("person.phone").minWidth(100).also()
                .addProp("person.email").minWidth(100)
                .build();

        return new EntityCentre<>(MiCarrier.class, MiCarrier.class.getSimpleName(), ecc, injector, null);
    }

//    /**
//     * Creates entity master for {@link Carrier}.
//     *
//     * @param injector
//     * @return created entity master
//     */
//    private EntityMaster<Carrier> createMaster(final Injector injector) {
//        final String layout = LayoutComposer.mkGridForMasterFitWidth(1, 2);
//
//        final IMaster<Carrier> masterConfig = new SimpleMasterBuilder<Carrier>().forEntity(Carrier.class)
//                .addProp("key").asSinglelineText().also()
//                .addProp("desc").asMultilineText().also()
//                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
//                .addAction(MasterActions.SAVE)
//                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
//                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
//                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
//                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
//                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
//                .done();
//
//        return new EntityMaster<>(Carrier.class, masterConfig, injector);
//    }
}