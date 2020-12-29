package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class GenerateEmployeeNoValidator extends AbstractBeforeChangeEventHandler<Boolean> {
	

	
	private static final String WARN_GENERATING_EMPLOYEE_NO = "Once employee no has been set, it cannot be removed.";

	@Override
    public Result handle(MetaProperty<Boolean> property, Boolean generateEmployeeNo, Set<Annotation> mutatorAnnotations) {
        
    	Person person = property.getEntity();
    	
    	if (!person.isEmployee() && generateEmployeeNo) {
    		return Result.warning(WARN_GENERATING_EMPLOYEE_NO);
    	}
    	
        return Result.successful(generateEmployeeNo);
    }

}
