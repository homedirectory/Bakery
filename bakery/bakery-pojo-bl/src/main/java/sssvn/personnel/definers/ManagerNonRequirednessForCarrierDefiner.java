package sssvn.personnel.definers;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class ManagerNonRequirednessForCarrierDefiner extends AbstractAfterChangeEventHandler<Boolean>{
	
    @Override
    public void handle(final MetaProperty<Boolean> mp, final Boolean carrier) {
        final Person person = mp.getEntity();
        
        person.getProperty("aManager").setRequired(!carrier && !person.isManager() && person.isEmployee());
        
    }

}
