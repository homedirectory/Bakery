package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Employment;
import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class EmploymentFinishDateValidator extends AbstractBeforeChangeEventHandler<Date> {
	
	@Override
	public Result handle(final MetaProperty<Date> property, final Date finishDate, final Set<Annotation> mutatorAnnotations) {
		
		final Employment employment = property.getEntity();
		final Date startDate = employment.getStartDate();
		
		final Employment employeeCurrEmployment = employment.getEmployee().getCurrEmployment();
		
		if (employeeCurrEmployment != null) {
			final Date currStartDate = employeeCurrEmployment.getStartDate();
			final Date currFinishDate = employeeCurrEmployment.getFinishDate();
			
			if (finishDate.compareTo(currStartDate) >= 0 && (currFinishDate == null || finishDate.compareTo(currFinishDate) <= 0)) {
				return Result.failure(EmploymentStartDateValidator.ERR_EMPLOYEE_CURR_EMPLOYMENT_INTERSECTION);
			}
			
		}
		
		return Result.successful(startDate);
	}

}
