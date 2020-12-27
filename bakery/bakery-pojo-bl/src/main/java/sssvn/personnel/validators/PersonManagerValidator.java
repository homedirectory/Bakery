package sssvn.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import sssvn.personnel.Manager;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class PersonManagerValidator extends AbstractBeforeChangeEventHandler<Manager> {
    
    public static final String ERR_SELF_MANAGEMENT = "No one should be able to manage themselves.";
    public static final String ERR_MANAGEMENT_OF_NON_EMPLOYEE = "Manager cannot manage a non-employee.";

    @Override
    public Result handle(MetaProperty<Manager> property, Manager manager, Set<Annotation> mutatorAnnotations) {
        final Person person = property.getEntity();

        if (manager != null && StringUtils.isEmpty(person.getEmployeeNo())) {
            return Result.failure(ERR_MANAGEMENT_OF_NON_EMPLOYEE);
        }

        if (manager != null && person.equals(manager.getPerson())) {
            return Result.failure(ERR_SELF_MANAGEMENT);
        }
        

        return Result.successful(manager);
    }
   

}