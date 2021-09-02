package com.bloethner.termproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.bloethner.termproject.repository"})
@ComponentScan(basePackages  = {"com.bloethner.termproject"} )
public class DataServiceConfig {

    // configure database connection
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:8889/LIBRARY");
        dataSource.setUsername("e57");
        dataSource.setPassword("password");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(1800000);
        dataSource.setNumTestsPerEvictionRun(3);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        return dataSource;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProp.put("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.max_fetch_depth", 3);
        hibernateProp.put("hibernate.jdbc.batch_size", 10);
        hibernateProp.put("hibernate.jdbc.fetch_size", 50);
        return hibernateProp;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    // configure transaction manager using entity factory
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    // configure entity manager for Spring repositories
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("com.bloethner.termproject.entities");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }
}





















//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import org.apache.commons.dbcp2.BasicDataSource;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@EnableJpaRepositories(basePackages = "com.bloethner.termproject", entityManagerFactoryRef="emf")
//@ComponentScan(basePackages  = {"com.bloethner.termproject"} )
//public class DataServiceConfig {
//
//    private static Logger logger = LoggerFactory.getLogger(DataServiceConfig.class);
//
//    @Bean(destroyMethod = "close")
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:8889/LIBRARY");
//        dataSource.setUsername("e57");
//        dataSource.setPassword("password");
//        //dataSource.setValidationQuery(databaseValidationQuery);
//        dataSource.setTestOnBorrow(true);
//        dataSource.setTestOnReturn(true);
//        dataSource.setTestWhileIdle(true);
//        dataSource.setTimeBetweenEvictionRunsMillis(1800000);
//        dataSource.setNumTestsPerEvictionRun(3);
//        dataSource.setMinEvictableIdleTimeMillis(1800000);
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
//        return new NamedParameterJdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean theEntityManager = new LocalContainerEntityManagerFactoryBean();
//        theEntityManager.setDataSource(dataSource());
//        theEntityManager.setPersistenceUnitName(configrationProperties
//                .getString("db.jpa.persistanceUnit"));
//        theEntityManager.setJpaVendorAdapter(jpaVendorAdapter());
//        theEntityManager.setJpaProperties(jpaProperties());
//        EntityManagerFactory emf = theEntityManager
//                .getNativeEntityManagerFactory();
//        return emf <- this is null;
//    }
//
//
//
////    @Bean
////    public Properties hibernateProperties() {
////        Properties hibernateProp = new Properties();
////        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
////        hibernateProp.put("hibernate.hbm2ddl.auto", "create-drop");
////        hibernateProp.put("hibernate.show_sql", true);
////        hibernateProp.put("hibernate.max_fetch_depth", 3);
////        hibernateProp.put("hibernate.jdbc.batch_size", 10);
////        hibernateProp.put("hibernate.jdbc.fetch_size", 50);
////        return hibernateProp;
////    }
////
////    @Bean
////    public PlatformTransactionManager transactionManager() {
////        return new JpaTransactionManager(entityManagerFactory());
////    }
////
////    @Bean
////    public JpaVendorAdapter jpaVendorAdapter() {
////        return new HibernateJpaVendorAdapter();
////    }
////
////
////    @Bean
////    public EntityManagerFactory entityManagerFactory() {
////        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
////        factoryBean.setPackagesToScan("com.apress.prospring5.ch16.entities");
////        factoryBean.setDataSource(dataSource());
////        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
////        factoryBean.setJpaProperties(hibernateProperties());
////        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
////        factoryBean.afterPropertiesSet();
////        return factoryBean.getNativeEntityManagerFactory();
////    }
//}
