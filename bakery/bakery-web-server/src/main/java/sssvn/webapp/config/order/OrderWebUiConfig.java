package sssvn.webapp.config.order;

import static java.lang.String.format;
import static sssvn.common.LayoutComposer.CELL_LAYOUT;
import static sssvn.common.LayoutComposer.MARGIN;
import static sssvn.common.LayoutComposer.PADDING_LAYOUT;
import static sssvn.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;

import java.util.Optional;

import com.google.inject.Injector;

import sssvn.order.Order;
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
import sssvn.main.menu.order.MiOrder;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import static ua.com.fielden.platform.web.PrefDim.mkDim;
import static ua.com.fielden.platform.web.layout.api.impl.LayoutBuilder.cell;

import ua.com.fielden.platform.web.PrefDim.Unit;
/**
 * {@link Order} Web UI configuration.
 *
 * @author Developers
 *
 */
public class OrderWebUiConfig {

    public final EntityCentre<Order> centre;
    public final EntityMaster<Order> master;

    public static OrderWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new OrderWebUiConfig(injector, builder);
    }

    private OrderWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Order}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Order> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(1, 2);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Order.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Order.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Order.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Order.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Order.class, standardEditAction);

        final EntityCentreConfig<Order> ecc = EntityCentreBuilder.centreFor(Order.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("orderNo").asMulti().text().also()
                .addCrit("carrier").asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("orderNo").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Order.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                .addProp("locationFrom").minWidth(100).also()
                .addProp("locationTo").minWidth(100).also()
                .addProp("carrier").minWidth(100)
                
                .build();

        return new EntityCentre<>(MiOrder.class, MiOrder.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link Order}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Order> createMaster(final Injector injector) {
        final String layout = cell(
                cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))
               .cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN)), PADDING_LAYOUT).toString();

        final IMaster<Order> masterConfig = new SimpleMasterBuilder<Order>().forEntity(Order.class)
                .addProp("locationFrom").asSinglelineText().also()
                .addProp("locationTo").asSinglelineText().also()
                .addProp("carrier").asSinglelineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(Order.class, masterConfig, injector);
    }
}