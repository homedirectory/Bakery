package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class EmployeeCarrierSettingValidator extends AbstractBeforeChangeEventHandler<Boolean> {
	public static final String ERR_NON_EMPLOYEE_BECOMING_CARRIER = "Only employees can become carriers.";

	@Override
	public Result handle(final MetaProperty<Boolean> property, final Boolean isCarrier, final Set<Annotation> mutatorAnnotations) {
		
		final Person person = property.getEntity();
		
		if (StringUtils.isEmpty(person.getEmployeeNo()) && isCarrier) {
			return Result.failure(ERR_NON_EMPLOYEE_BECOMING_CARRIER);
		}
		return Result.successful(isCarrier);
	}
}