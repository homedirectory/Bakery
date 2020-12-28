package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Set;

import sssvn.personnel.Employment;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class EmploymentStartDateValidator extends AbstractBeforeChangeEventHandler<Date> {
    
    public static final String ERR_INVALID_DATE = "Start Date can't occur after Finish Date";

    @Override
    public Result handle(MetaProperty<Date> property, Date startDate, Set<Annotation> mutatorAnnotations) {
        final Employment employment = property.getEntity();
        final Date finishDate = employment.getFinishDate();

        if (finishDate != null && startDate != null && startDate.compareTo(finishDate) > 0) {
        	return Result.failure(ERR_INVALID_DATE);
        }
        

        return Result.successful(startDate);
    }
   

}