package sssvn.personnel;

import java.util.Date;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;
import ua.com.fielden.platform.utils.EntityUtils;
/**
 * DAO implementation for companion object {@link EmploymentCo}.
 *
 * @author Developers
 *
 */
@EntityType(Employment.class)
public class EmploymentDao extends CommonEntityDao<Employment> implements EmploymentCo {
	
	private static final String CONTRACT_NUMBER_KEY = "CONTRACT NUMBER";
	public static final String DEFAULT_CONTRACT_NUMBER = "CONTRACT NUMBER WILL BE AUTO-GENERATED";

    @Inject
    public EmploymentDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Employment> createFetchProvider() {
         return FETCH_PROVIDER;
    }
    
    @Override
	public Employment new_() {
		return super.new_().setContractNo(DEFAULT_CONTRACT_NUMBER);
	}
    
    @Override
    @SessionRequired
	public Employment save(final Employment employment) {
		employment.isValid().ifFailure(Result::throwRuntime);
		
		boolean wasPersisted = employment.isPersisted();
		try {
			if (!wasPersisted && EntityUtils.equalsEx(employment.getContractNo(), DEFAULT_CONTRACT_NUMBER)) {
				final IKeyNumber coKeyNumber = co(KeyNumber.class);
				var next = coKeyNumber.nextNumber(CONTRACT_NUMBER_KEY);
				final String keyPattern = "CO-%06d";
				employment.setContractNo(String.format(keyPattern, next));
			}
			
			return super.save(employment);
		} catch (final Exception ex) {
			if (!wasPersisted) {
				employment.beginInitialising();
				employment.setContractNo(DEFAULT_CONTRACT_NUMBER);
				employment.endInitialising();
			}
			throw ex;
		}
	}
}