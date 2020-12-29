package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class EmploymentEmployeeValidator extends AbstractBeforeChangeEventHandler<Person> {
	
	private static final String ERR_EMPLOYMENT_OF_NON_EMPLOYEE = "This person is not an employee.";

	@Override
	public Result handle(final MetaProperty<Person> property, final Person employee, final Set<Annotation> mutatorAnnotations) {
		
		if (!employee.isEmployee()) {
			return Result.failure(ERR_EMPLOYMENT_OF_NON_EMPLOYEE);
		}
		
		return Result.successful(employee);
	}

}
