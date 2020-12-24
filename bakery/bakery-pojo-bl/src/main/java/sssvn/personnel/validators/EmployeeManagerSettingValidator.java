package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class EmployeeManagerSettingValidator extends AbstractBeforeChangeEventHandler<Boolean> {
	public static final String ERR_NON_EMPLOYEE_BECOMING_MANAGER = "Only employees can become managers.";

	@Override
	public Result handle(final MetaProperty<Boolean> property, final Boolean isManager, final Set<Annotation> mutatorAnnotations) {
		// TODO Auto-generated method stub
		final Person person = property.getEntity();
		
		if (StringUtils.isEmpty(person.getEmployeeNo()) && isManager) {
			return Result.failure(ERR_NON_EMPLOYEE_BECOMING_MANAGER);
		}
		return Result.successful(isManager);
	}
}
