package sssvn.security.tokens.persistent;

import static java.lang.String.format;
import static ua.com.fielden.platform.reflection.TitlesDescsGetter.getEntityTitleAndDesc;

import sssvn.production.Product;
import sssvn.security.tokens.ProductionModuleToken;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link Product} to guard DELETE. 
 * 
 * @author Generated
 *
 */
public class Product_CanDelete_Token extends ProductionModuleToken {
    private final static String ENTITY_TITLE = getEntityTitleAndDesc(Product.class).getKey();
    public final static String TITLE = format(Template.DELETE.forTitle(), ENTITY_TITLE);
    public final static String DESC = format(Template.DELETE.forDesc(), ENTITY_TITLE);
}