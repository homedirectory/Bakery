package sssvn.webapp.config.personnel;

import static java.lang.String.format;
import static sssvn.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;

import java.util.Optional;

import com.google.inject.Injector;

import sssvn.personnel.Manager;
import sssvn.personnel.Person;
import sssvn.common.LayoutComposer;
import sssvn.common.StandardActions;

import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import sssvn.main.menu.personnel.MiManager;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import static ua.com.fielden.platform.web.PrefDim.mkDim;
import ua.com.fielden.platform.web.PrefDim.Unit;
/**
 * {@link Manager} Web UI configuration.
 *
 * @author Developers
 *
 */
public class ManagerWebUiConfig {

    public final EntityCentre<Manager> centre;

    public static ManagerWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new ManagerWebUiConfig(injector, builder);
    }

    private ManagerWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
    }

    /**
     * Creates entity centre for {@link Manager}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Manager> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(1, 2);

        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Manager.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<Manager> ecc = EntityCentreBuilder.centreFor(Manager.class)
                .runAutomatically()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Manager.class).also()
                .addCrit("desc").asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("person").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Manager.ENTITY_TITLE))
                    .withActionSupplier(builder.getOpenMasterAction(Person.class)).also()
                .addProp("desc").minWidth(100).also()
                .addProp("active").minWidth(50).also()
                .addProp("person.aManager").minWidth(100).also()
                .addProp("person.employeeNo").minWidth(100).also()
                .addProp("person.title").minWidth(100).also()
                .addProp("person.phone").minWidth(100).also()
                .addProp("person.email").minWidth(100)
                .build();

        return new EntityCentre<>(MiManager.class, MiManager.class.getSimpleName(), ecc, injector, null);
    }

//    /**
//     * Creates entity master for {@link Manager}.
//     *
//     * @param injector
//     * @return created entity master
//     */
//    private EntityMaster<Manager> createMaster(final Injector injector) {
//        final String layout = LayoutComposer.mkGridForMasterFitWidth(1, 2);
//
//        final IMaster<Manager> masterConfig = new SimpleMasterBuilder<Manager>().forEntity(Manager.class)
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
//        return new EntityMaster<>(Manager.class, masterConfig, injector);
//    }
}