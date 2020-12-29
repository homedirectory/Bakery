package sssvn.webapp.config.order;

import static java.lang.String.format;
import static sssvn.common.LayoutComposer.CELL_LAYOUT;
import static sssvn.common.LayoutComposer.MARGIN;
import static sssvn.common.LayoutComposer.PADDING_LAYOUT;
import static sssvn.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;

import java.util.Optional;

import com.google.inject.Injector;

import sssvn.order.Order;
import sssvn.order.OrderItem;
import sssvn.personnel.Carrier;
import sssvn.product.Product;
import sssvn.common.LayoutComposer;
import sssvn.common.StandardActions;
import sssvn.location.Location;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import sssvn.main.menu.order.MiOrderItem;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import static ua.com.fielden.platform.web.PrefDim.mkDim;
import static ua.com.fielden.platform.web.layout.api.impl.LayoutBuilder.cell;

import ua.com.fielden.platform.web.PrefDim.Unit;
/**
 * {@link OrderItem} Web UI configuration.
 *
 * @author Developers
 *
 */
public class OrderItemWebUiConfig {

    public final EntityCentre<OrderItem> centre;
    public final EntityMaster<OrderItem> master;

    public static OrderItemWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new OrderItemWebUiConfig(injector, builder);
    }

    private OrderItemWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link OrderItem}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<OrderItem> createCentre(final Injector injector, final IWebUiBuilder builder) {
    	 final String layout = cell(
                 cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN)) 
                .cell(cell().layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN)), 
                PADDING_LAYOUT).toString();
    	 
        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(OrderItem.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(OrderItem.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(OrderItem.class);
        builder.registerOpenMasterAction(OrderItem.class, standardEditAction);

        final EntityCentreConfig<OrderItem> ecc = EntityCentreBuilder.centreFor(OrderItem.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardExportAction)
                .addCrit("order").asMulti().autocompleter(Order.class).also()
                .addCrit("product").asMulti().autocompleter(Product.class).also()
                .addCrit("quantity").asRange().integer()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("order").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", OrderItem.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                .addProp("product").minWidth(100).also()
                .addProp("quantity").minWidth(100)
                //.addProp("prop").minWidth(100).withActionSupplier(builder.getOpenMasterAction(Entity.class)).also()
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiOrderItem.class, MiOrderItem.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link OrderItem}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<OrderItem> createMaster(final Injector injector) {
    	final String layout = cell(
                cell(cell().repeat(2).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN))
               .cell(cell().repeat(1).layoutForEach(CELL_LAYOUT).withGapBetweenCells(MARGIN)), PADDING_LAYOUT).toString();

        final IMaster<OrderItem> masterConfig = new SimpleMasterBuilder<OrderItem>().forEntity(OrderItem.class)
        		.addProp("order").asAutocompleter().also()
                .addProp("product").asAutocompleter().also()
                .addProp("quantity").asDecimal().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(OrderItem.class, masterConfig, injector);
    }
}