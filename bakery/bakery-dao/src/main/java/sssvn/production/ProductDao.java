package sssvn.production;

import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import sssvn.security.tokens.persistent.Product_CanDelete_Token;
import sssvn.security.tokens.persistent.Product_CanSave_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.security.Authorise;
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
        return FETCH_PROVIDER;
    }
    
    @Override
    @SessionRequired
    @Authorise(Product_CanSave_Token.class)
	public Product save(Product entity) {
		return super.save(entity);
	}
    
    @Override
    @SessionRequired
    @Authorise(Product_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(Product_CanDelete_Token.class)
    public int batchDelete(final List<Product> entities) {
        return defaultBatchDelete(entities);
    }
    
}
