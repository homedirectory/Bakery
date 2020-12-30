package sssvn.security.tokens;

import sssvn.config.Modules;
import ua.com.fielden.platform.security.ISecurityToken;

/**
 * Top level security token for all security tokens that belong to module {@link Modules#PRODUCTION};
 *
 * @author Generated
 */

public class ProductionModuleToken implements ISecurityToken {
    public static final String TITLE = Modules.PRODUCTION.title;
    public static final String DESC = Modules.PRODUCTION.desc;
}
