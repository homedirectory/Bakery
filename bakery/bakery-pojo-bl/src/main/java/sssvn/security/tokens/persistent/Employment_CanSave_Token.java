package sssvn.security.tokens.persistent;

import static java.lang.String.format;
import static ua.com.fielden.platform.reflection.TitlesDescsGetter.getEntityTitleAndDesc;

import sssvn.personnel.Employment;
import sssvn.personnel.Person;
import sssvn.security.tokens.UsersAndPersonnelModuleToken;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link Employment} to guard SAVE. 
 * 
 * @author Generated
 *
 */
public class Employment_CanSave_Token extends UsersAndPersonnelModuleToken {
    private final static String ENTITY_TITLE = getEntityTitleAndDesc(Employment.class).getKey();
    public final static String TITLE = format(Template.SAVE.forTitle(), ENTITY_TITLE);
    public final static String DESC = format(Template.SAVE.forDesc(), ENTITY_TITLE);
}