package sssvn.config.location;


import static sssvn.common.LayoutComposer.*;
import static sssvn.common.StandardActionsStyles.MASTER_CANCEL_ACTION_LONG_DESC;
import static sssvn.common.StandardActionsStyles.MASTER_CANCEL_ACTION_SHORT_DESC;
import static sssvn.common.StandardActionsStyles.MASTER_SAVE_ACTION_LONG_DESC;
import static sssvn.common.StandardActionsStyles.MASTER_SAVE_ACTION_SHORT_DESC;

import static ua.com.fielden.platform.web.centre.api.context.impl.EntityCentreContextSelector.context;
import static ua.com.fielden.platform.web.layout.api.impl.LayoutBuilder.cell;

import java.util.Optional;

import com.google.inject.Injector;

import sssvn.common.StandardActions;
import sssvn.config.location.LocationWebUiConfig;
import sssvn.location.Location;
import sssvn.main.menu.location.MiLocation;
import sssvn.location.Location;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.compound.Compound;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;


public class LocationWebUiConfig {

	private final Injector injector;
    
    public final EntityCentre<Location> centre;
    public final EntityMaster<Location> master;
    
    public static LocationWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new LocationWebUiConfig(injector, builder);
    }
    
    private LocationWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        this.injector = injector;

        centre = createLocationCentre(builder);
        builder.register(centre);

        master = createLocationMaster();
        builder.register(master);
    }
    
    /**
     * Creates entity centre for {@link Location}.
     *
     * @return
     */
    private EntityCentre<Location> createLocationCentre(final IWebUiBuilder builder) {
        final String layout = cell(
                cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))  // row 1 -> 1, 2
                .cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))
               .cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))
               .cell(cell().layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN)), 
               PADDING_LAYOUT).toString();

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Location.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Location.class);
        builder.registerOpenMasterAction(Location.class, standardEditAction);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Location.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Location.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<Location> ecc = EntityCentreBuilder.centreFor(Location.class)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Location.class).also()
                .addCrit("desc").asMulti().text().also()
                .addCrit("country").asMulti().text().also()
                .addCrit("city").asMulti().text().also()
                .addCrit("address").asMulti().text().also()  // street
                .addCrit("phone").asMulti().text().also()
                .addCrit("employeesAmount").asRange().integer()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .addProp("this").order(1).asc().minWidth(50)
                    .withSummary("total_count_", "COUNT(SELF)", "Count:The total number of matching Location.")
                    .withAction(standardEditAction).also()
                .addProp("desc").minWidth(200).also()
                .addProp("country").minWidth(70).also()
                .addProp("city").minWidth(70).also()
                .addProp("address").minWidth(70).also()
                .addProp("phone").minWidth(70).also()
                .addProp("workingHours").minWidth(70).also()
                .addProp("employeesAmount").minWidth(70)
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiLocation.class, MiLocation.class.getSimpleName(), ecc, injector, null);
    }

    private EntityMaster<Location> createLocationMaster() {
        final String layout = cell(
                cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))
               .cell(cell().layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))
               .cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))
               .cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN)),
               PADDING_LAYOUT).toString();

        final IMaster<Location> masterConfig = new SimpleMasterBuilder<Location>().forEntity(Location.class)
                // row 1
                .addProp("country").asSinglelineText().also()
                .addProp("city").asSinglelineText().also()
                // row 2
                .addProp("address").asMultilineText().also()
                // row 3
                .addProp("phone").asSinglelineText().also()
                .addProp("workingHours").asSinglelineText().also()
                // row 4
                .addProp("employeesAmount").asDecimal().also()
                .addProp("desc").asSinglelineText().also()
                .addAction(MasterActions.REFRESH).shortDesc(MASTER_CANCEL_ACTION_SHORT_DESC).longDesc(MASTER_CANCEL_ACTION_LONG_DESC)
                .addAction(MasterActions.SAVE).shortDesc(MASTER_SAVE_ACTION_SHORT_DESC).longDesc(MASTER_SAVE_ACTION_LONG_DESC)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .done();

        return new EntityMaster<>(Location.class, masterConfig, injector);
    }
    
}
