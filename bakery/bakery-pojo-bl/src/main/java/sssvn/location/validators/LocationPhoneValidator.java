package sssvn.location.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class LocationPhoneValidator extends AbstractBeforeChangeEventHandler<String> {
	
    public static final String ERR_LETTERS_NOT_PERMITTED = "Letters are not permitted in the phone number";
    
    @Override
    public Result handle(MetaProperty<String> property, String phone, Set<Annotation> mutatorAnnotations) {
        for (char letter= 65;letter<122;letter++) {
        	if(phone.contains(""+letter)) {
                return Result.failure(ERR_LETTERS_NOT_PERMITTED);
            }
        }
    	
        return Result.successful(phone);
    }

}