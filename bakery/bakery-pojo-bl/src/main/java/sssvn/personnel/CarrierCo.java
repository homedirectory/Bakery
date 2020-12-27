package sssvn.personnel;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Carrier}.
 *
 * @author Developers
 *
 */
public interface CarrierCo extends IEntityDao<Carrier> {

    static final IFetchProvider<Carrier> FETCH_PROVIDER = EntityUtils.fetch(Carrier.class).with("person", "desc");

}
