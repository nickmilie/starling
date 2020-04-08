package com.nick.springsecurity.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.nick.springsecurity")
@PropertySource({"classpath:persistence-mysql.properties", "classpath:security-persistence-mysql.properties"})
public class AppConfig implements WebMvcConfigurer {


    // set up variable to hold a properties

    @Autowired
    private Environment env;

    // set up logger for diagnostic

    private Logger logger = Logger.getLogger(getClass().getName());

    @Bean
    public ViewResolver viewResolver(){

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    // define a bean for our security datasource


    @Bean
    public DataSource DataSource() {

        // create connection pool
        ComboPooledDataSource DataSource = new ComboPooledDataSource();

        // set the jdbc driver
        try {
            DataSource.setDriverClass("com.mysql.jdbc.Driver");
        }
        catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // for sanity's sake, let's log url and user ... just to make sure we are reading the data
        logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
        logger.info("jdbc.user=" + env.getProperty("jdbc.user"));

        // set database connection props
        DataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        DataSource.setUser(env.getProperty("jdbc.user"));
        DataSource.setPassword(env.getProperty("jdbc.password"));

        // set connection pool props
        DataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        DataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        DataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        DataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return DataSource;
    }

    @Bean
    public DataSource securityDataSource(){

        // create connection pool

        ComboPooledDataSource securityDataSource
                = new ComboPooledDataSource();

        // set the jdbc driver class

        try {
            securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // log the connection properties
        // for sanity's sake

        logger.info(">>>>> jdbc.url = " + env.getProperty("security.jdbc.url"));
        logger.info(">>>>> jdbc.user = " + env.getProperty("security.jdbc.user"));

        // set database connection properties

        securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
        securityDataSource.setUser(env.getProperty("security.jdbc.user"));
        securityDataSource.setPassword(env.getProperty("security.jdbc.password"));

        // set connection pool properties

        securityDataSource.setInitialPoolSize(
                getIntProperty("security.connection.pool.initialPoolSize")
        );
        securityDataSource.setMinPoolSize(
                getIntProperty("security.connection.pool.minPoolSize")
        );
        securityDataSource.setMaxPoolSize(
                getIntProperty("security.connection.pool.maxPoolSize")
        );
        securityDataSource.setMaxIdleTime(
                getIntProperty("security.connection.pool.maxIdleTime")
        );

        return securityDataSource;
    }

    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        return props;
    }

    private int getIntProperty(String propertyName){

        String propertyValue = env.getProperty(propertyName);

        int intPropertyValue = Integer.parseInt(propertyValue);

        return intPropertyValue;

    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        // create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(DataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    public LocalSessionFactoryBean securitySessionFactory(){

        // create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(securityDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }


    @Bean
    @Autowired
    @Qualifier(value = "sessionFactory")
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    @Autowired
    @Qualifier(value = "securitySessionFactory")
    public HibernateTransactionManager securityTransactionManager(SessionFactory securitySessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(securitySessionFactory);

        return txManager;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
