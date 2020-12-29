package sssvn.product;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import sssvn.personnel.Person;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Product}.
 *
 * @author Developers
 *
 */
public interface ProductCo extends IEntityDao<Product> {

    static final IFetchProvider<Product> FETCH_PROVIDER = EntityUtils.fetch(Product.class)
            .with("name", "desc", "price", "recipe");
    

}
