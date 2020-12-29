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

public class EmploymentStartDateValidator extends AbstractBeforeChangeEventHandler<Date> {
	
	private static final String ERR_EMPLOYEE_CURR_EMPLOYMENT_INTERSECTION = "This date intersects with the employee's current employment";

	@Override
	public Result handle(final MetaProperty<Date> property, final Date startDate, final Set<Annotation> mutatorAnnotations) {
		
		final Employment employment = property.getEntity();
		
		final Employment employeeCurrEmployment = employment.getEmployee().getCurrEmployment();
		final Date currStartDate = employeeCurrEmployment.getStartDate();
		final Date currFinishDate = employeeCurrEmployment.getFinishDate();
		
		if (employeeCurrEmployment != null) {
			if (startDate.compareTo(currStartDate) >= 0 && (currFinishDate == null || startDate.compareTo(currFinishDate) <= 0)) {
				return Result.failure(ERR_EMPLOYEE_CURR_EMPLOYMENT_INTERSECTION);
			}
		}
		
		return Result.successful(startDate);
	}

}
