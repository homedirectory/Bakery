package sssvn.personnel.definers;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class PositionRequirednsessForEmployeeDefiner extends AbstractAfterChangeEventHandler<Object>{
    @Override
    public void handle(final MetaProperty<Object> mp, final Object value) {
        final Person p = mp.getEntity();
        p.getProperty("title").setRequired(!StringUtils.isEmpty(p.getEmployeeNo()));
        p.getProperty("aManager").setRequired(!StringUtils.isEmpty(p.getEmployeeNo()) && !p.isManager() && !p.isCarrier());
    }

}
