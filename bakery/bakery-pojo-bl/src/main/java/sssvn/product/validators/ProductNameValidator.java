package sssvn.product.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class ProductNameValidator extends AbstractBeforeChangeEventHandler<String> {
    
    public static final String ERR_NUMBERS_NOT_PERMITTED = "Numbers are not permitted in the product`s name.";
    
    @Override
    public Result handle(MetaProperty<String> property, String name, Set<Annotation> mutatorAnnotations) {
        if(name.matches(".*\\d+.*")) {
            return Result.failure(ERR_NUMBERS_NOT_PERMITTED);
        }
        return Result.successful(name);
    }

}
