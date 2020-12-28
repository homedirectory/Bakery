package sssvn.webapp.config.personnel;

import static java.lang.String.format;
import static sssvn.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static ua.com.fielden.platform.web.PrefDim.mkDim;

import java.util.Optional;

import com.google.inject.Injector;

import sssvn.common.LayoutComposer;
import sssvn.common.StandardActions;
import sssvn.main.menu.personnel.MiEmployment;
import sssvn.personnel.Employment;
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
 * {@link Employment} Web UI configuration.
 *
 * @author Developers
 *
 */
public class EmploymentWebUiConfig {

    public final EntityCentre<Employment> centre;
    public final EntityMaster<Employment> master;

    public static EmploymentWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new EmploymentWebUiConfig(injector, builder);
    }

    private EmploymentWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Employment}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Employment> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 2, 1);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Employment.class);
//        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Employment.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Employment.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Employment.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Employment.class, standardEditAction);

        final EntityCentreConfig<Employment> ecc = EntityCentreBuilder.centreFor(Employment.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
//                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Employment.class).also()
                .addCrit("employee").asMulti().autocompleter(Person.class).also()
                .addCrit("startDate").asRange().date().also()
                .addCrit("finishDate").asRange().date().also()
                .addCrit("salary").asRange().decimal()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("this").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Employment.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                .addProp("contractNo").width(70).also()
                .addProp("employee").minWidth(100).also()
                .addProp("startDate").width(70).also()
                .addProp("finishDate").width(70).also()
                .addProp("salary").minWidth(70).also()
                .addProp("contractDocument").width(70)
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiEmployment.class, MiEmployment.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link Employment}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Employment> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 2, 2);

        final IMaster<Employment> masterConfig = new SimpleMasterBuilder<Employment>().forEntity(Employment.class)
                .addProp("contractNo").asSinglelineText().also()
                .addProp("employee").asAutocompleter().also()
                .addProp("startDate").asDatePicker().also()
                .addProp("finishDate").asDatePicker().also()
                .addProp("salary").asMoney().also()
                .addProp("contractDocument").asHyperlink().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_TWO_COLUMN_MASTER_DIM_WIDTH, 440, Unit.PX))
                .done();

        return new EntityMaster<>(Employment.class, masterConfig, injector);
    }
}