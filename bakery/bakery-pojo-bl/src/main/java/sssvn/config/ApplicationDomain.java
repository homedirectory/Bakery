package sssvn.config;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import sssvn.personnel.Person;
import sssvn.production.Product;
import ua.com.fielden.platform.basic.config.IApplicationDomainProvider;
import ua.com.fielden.platform.domain.PlatformDomainTypes;
import ua.com.fielden.platform.entity.AbstractEntity;
import sssvn.logistics.Location;
import sssvn.logistics.Order;
import sssvn.logistics.OrderItem;
import sssvn.personnel.Manager;
import sssvn.personnel.Carrier;
import sssvn.personnel.Employment;

/**
 * A class to register domain entities.
 * 
 * @author Generated
 * 
 */
public class ApplicationDomain implements IApplicationDomainProvider {
	private static final Set<Class<? extends AbstractEntity<?>>> entityTypes = new LinkedHashSet<>();
	private static final Set<Class<? extends AbstractEntity<?>>> domainTypes = new LinkedHashSet<>();

	static {
		entityTypes.addAll(PlatformDomainTypes.types);
		add(Person.class);
		add(Manager.class);
		add(Product.class);
		add(Location.class);
		add(Carrier.class);
		add(Order.class);
		add(Employment.class);
		add(OrderItem.class);
	}

	private static void add(final Class<? extends AbstractEntity<?>> domainType) {
		entityTypes.add(domainType);
		domainTypes.add(domainType);
	}

	@Override
	public List<Class<? extends AbstractEntity<?>>> entityTypes() {
		return Collections.unmodifiableList(entityTypes.stream().collect(Collectors.toList()));
	}

	public List<Class<? extends AbstractEntity<?>>> domainTypes() {
		return Collections.unmodifiableList(domainTypes.stream().collect(Collectors.toList()));
	}
}
