package sssvn.personnel.definers;

import org.apache.commons.lang3.StringUtils;

import sssvn.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;

public class GenerateEmployeeNoDefiner extends AbstractAfterChangeEventHandler<Boolean> {
	
	private static final String EMPLOYEE_NUMBER_KEY = "EMPLOYEE NUMBER";
	
	@Override
    public void handle(final MetaProperty<Boolean> mp, final Boolean generateEmployeeNo) {
		
        final Person person = mp.getEntity();
        
    	// generate employee number if needed
        if (generateEmployeeNo && !person.isEmployee()) { 
        	final IKeyNumber coKeyNumber = co(KeyNumber.class);
			var next = coKeyNumber.nextNumber(EMPLOYEE_NUMBER_KEY);
			final String keyPattern = "EMP-%06d";
			person.setEmployeeNo(String.format(keyPattern, next));
        }
        
        else if (!generateEmployeeNo) {
        	person.setEmployeeNo("");
        }
        
    }

}
