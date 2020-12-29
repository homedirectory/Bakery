package sssvn.webapp.config.product;

import static java.lang.String.format;
import static sssvn.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;

import java.util.Optional;

import com.google.inject.Injector;

import sssvn.product.Product;
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
import sssvn.main.menu.product.MiProduct;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import static ua.com.fielden.platform.web.PrefDim.mkDim;
import ua.com.fielden.platform.web.PrefDim.Unit;
/**
 * {@link Product} Web UI configuration.
 *
 * @author Developers
 *
 */
public class ProductWebUiConfig {

    public final EntityCentre<Product> centre;
    public final EntityMaster<Product> master;

    public static ProductWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new ProductWebUiConfig(injector, builder);
    }

    private ProductWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Product}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Product> createCentre(final Injector injector, final IWebUiBuilder builder) {
            final String layout = LayoutComposer.mkVarGridForCentre(2, 1);


            final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Product.class);
            final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Product.class);
            final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Product.class);
            final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Product.class);
            final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
            builder.registerOpenMasterAction(Product.class, standardEditAction);
            
            final EntityCentreConfig<Product> ecc = EntityCentreBuilder.centreFor(Product.class)
                    //.runAutomatically()
                    .addFrontAction(standardNewAction)
                    .addTopAction(standardNewAction).also()
                    .addTopAction(standardDeleteAction).also()
                    .addTopAction(standardSortAction).also()
                    .addTopAction(standardExportAction)
                    .addCrit("this").asMulti().autocompleter(Product.class).also()
                    .addCrit("desc").asMulti().text().also()
                    .addCrit("price").asRange().decimal()
                    
                    .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                    .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                    .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                    .withScrollingConfig(standardStandaloneScrollingConfig(0))
                    .addProp("this").order(1).asc().minWidth(100)
                        .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Product.ENTITY_TITLE))
                        .withAction(standardEditAction).also()
                    .addProp("desc").minWidth(100).also()
                    .addProp("price").minWidth(100).also()
                    .addProp("recipe").minWidth(100)
                    //.addProp("prop").minWidth(100).withActionSupplier(builder.getOpenMasterAction(Entity.class)).also()
                    .addPrimaryAction(standardEditAction).build();
            
            return new EntityCentre<>(MiProduct.class, MiProduct.class.getSimpleName(), ecc, injector, null);

            
            

    }

    /**
     * Creates entity master for {@link Product}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Product> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(2, 1);

        final IMaster<Product> masterConfig = new SimpleMasterBuilder<Product>().forEntity(Product.class)
                .addProp("name").asSinglelineText().also()
                .addProp("desc").asMultilineText().also()
                .addProp("price").asMoney().also()
                .addProp("recipe").asMultilineText().also()
                
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(Product.class, masterConfig, injector);

    }
}