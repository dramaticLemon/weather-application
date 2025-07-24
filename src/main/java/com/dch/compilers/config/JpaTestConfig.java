package com.dch.compilers.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("TEST")
public class JpaTestConfig extends BaseJpaConfig{
	@Override
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }
	
	@Override
    protected Properties jpaProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        return props;
    }
}
