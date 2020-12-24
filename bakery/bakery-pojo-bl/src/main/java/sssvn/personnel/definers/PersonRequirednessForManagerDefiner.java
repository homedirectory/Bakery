package sssvn.personnel.definers;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class PersonRequirednessForManagerDefiner extends AbstractAfterChangeEventHandler<Object> {

	@Override
	public void handle(MetaProperty<Object> property, Object entityPropertyValue) {
		// TODO Auto-generated method stub
		final Person p = property.getEntity();
		p.getProperty("aManager").setRequired(!StringUtils.isEmpty(p.getEmployeeNo()) && !p.isManager());
	}

}
