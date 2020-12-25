package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class PersonInitialsValidator extends AbstractBeforeChangeEventHandler<String> {
	
    public static final String ERR_SPACES_NOT_PERMITTED = "Spaces are not permitted in the initials";
    
    @Override
    public Result handle(MetaProperty<String> property, String initials, Set<Annotation> mutatorAnnotations) {
        if(initials.contains(" ")) {
            return Result.failure(ERR_SPACES_NOT_PERMITTED);
        }
        return Result.successful(initials);
    }

}
