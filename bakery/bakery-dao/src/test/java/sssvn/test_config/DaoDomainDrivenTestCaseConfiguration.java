package sssvn.test_config;

import java.util.Properties;

import ua.com.fielden.platform.entity.factory.EntityFactory;
import ua.com.fielden.platform.entity.query.IdOnlyProxiedEntityTypeCache;
import ua.com.fielden.platform.entity.query.metadata.DomainMetadata;
import ua.com.fielden.platform.ioc.ApplicationInjectorFactory;
import ua.com.fielden.platform.ioc.NewUserNotifierMockBindingModule;
import ua.com.fielden.platform.security.NoAuthorisation;
import ua.com.fielden.platform.test.DbDrivenTestCase;
import ua.com.fielden.platform.test.IDomainDrivenTestCaseConfiguration;

import com.google.inject.Injector;

import sssvn.config.ApplicationDomain;
import sssvn.dbsetup.HibernateSetup;
import sssvn.filter.NoDataFilter;
import sssvn.ioc.ApplicationServerModule;
import sssvn.serialisation.SerialisationClassProvider;

/**
 * Provides implementation of {@link IDomainDrivenTestCaseConfiguration} for testing purposes, which is mainly related to construction of appropriate IoC modules.
 *
 * @author Generated
 *
 */
public final class DaoDomainDrivenTestCaseConfiguration implements IDomainDrivenTestCaseConfiguration {
    private final EntityFactory entityFactory;
    private final Injector injector;
    private final ApplicationServerModule iocModule;

    /**
     * Required for dynamic instantiation by {@link DbDrivenTestCase}
     */
    public DaoDomainDrivenTestCaseConfiguration(final Properties props) {
    	try {
    	    // application properties
    	    props.setProperty("app.name", "Bakery Management System");
    	    props.setProperty("email.smtp", "non-existing-server");
    	    props.setProperty("email.fromAddress", "todoshchuk@ucu.edu.ua");
    	    props.setProperty("reports.path", "");
    	    props.setProperty("domain.path", "../bakery-pojo-bl/target/classes");
    	    props.setProperty("domain.package", "sssvn");
    	    props.setProperty("tokens.path", "../bakery-pojo-bl/target/classes");
    	    props.setProperty("tokens.package", "sssvn.security.tokens");
            props.setProperty("attachments.location", "../bakery-web-server/src/test/resources/attachments");
    	    props.setProperty("workflow", "development");
    	    // custom Hibernate configuration properties
    	    props.setProperty("hibernate.show_sql", "false");
    	    props.setProperty("hibernate.format_sql", "true");
    	    props.setProperty("cacheDefaults", "false");
    	    
    	    final ApplicationDomain applicationDomainProvider = new ApplicationDomain();
    	    
            iocModule = new ApplicationServerModule(
                    HibernateSetup.getHibernateTypes(),
                    applicationDomainProvider,
                    applicationDomainProvider.domainTypes(),
                    SerialisationClassProvider.class,
                    NoDataFilter.class,
                    NoAuthorisation.class,
                    UniversalConstantsForTesting.class,
                    props);
    
    	    injector = new ApplicationInjectorFactory()
                    .add(iocModule)
                    .add(new NewUserNotifierMockBindingModule())
                    .getInjector();
    
            entityFactory = injector.getInstance(EntityFactory.class);
    	} catch (final Exception e) {
    	    e.printStackTrace();
    	    throw new RuntimeException(e);
    	}
    }

    @Override
    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    @Override
    public <T> T getInstance(final Class<T> type) {
        return injector.getInstance(type);
    }

    @Override
    public DomainMetadata getDomainMetadata() {
        return iocModule.getDomainMetadata();
    }

    @Override
    public IdOnlyProxiedEntityTypeCache getIdOnlyProxiedEntityTypeCache() {
        return iocModule.getIdOnlyProxiedEntityTypeCache();
    }
}
