package sssvn.product;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link ProductCo}.
 *
 * @author Developers
 *
 */
@EntityType(Product.class)
public class ProductDao extends CommonEntityDao<Product> implements ProductCo {

    @Inject
    public ProductDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
    public Product new_() {
        return super.new_().setActive(true);
    }

    @Override
    protected IFetchProvider<Product> createFetchProvider() {
        // TODO: uncomment the following line and specify the properties, which are required for the UI in IProduct.FETCH_PROVIDER. Then remove the line after.
        // return FETCH_PROVIDER;
        return FETCH_PROVIDER;
    }
}
