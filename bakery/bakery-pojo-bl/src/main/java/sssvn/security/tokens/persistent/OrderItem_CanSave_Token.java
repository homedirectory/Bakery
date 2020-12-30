package sssvn.security.tokens.persistent;

import static java.lang.String.format;
import static ua.com.fielden.platform.reflection.TitlesDescsGetter.getEntityTitleAndDesc;

import sssvn.logistics.Order;
import sssvn.logistics.OrderItem;
import sssvn.security.tokens.LogisticsModuleToken;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link OrderItem} to guard SAVE. 
 * 
 * @author Generated
 *
 */
public class OrderItem_CanSave_Token extends LogisticsModuleToken {
    private final static String ENTITY_TITLE = getEntityTitleAndDesc(OrderItem.class).getKey();
    public final static String TITLE = format(Template.SAVE.forTitle(), ENTITY_TITLE);
    public final static String DESC = format(Template.SAVE.forDesc(), ENTITY_TITLE);
}