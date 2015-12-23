package org.agileengine.callboard.application.configuration.persistence;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class PersistenceConfig {

    public static final String PROPERTIES_FILE = "persistence.properties";
    public static final String PROPERTY_JDBC_DRIVER = "jdbc.driverClassName";
    public static final String PROPERTY_URL = "jdbc.url";
    public static final String PROPERTY_USERNAME = "jdbc.username";
    public static final String PROPERTY_PASSWORD = "jdbc.password";

    @Bean
    public DataSource dataSource() {
        try {
            Properties props = PropertiesLoaderUtils.loadAllProperties(PROPERTIES_FILE);
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(props.getProperty(PROPERTY_JDBC_DRIVER));
            dataSource.setUrl(props.getProperty(PROPERTY_URL));
            dataSource.setUsername(props.getProperty(PROPERTY_USERNAME));
            dataSource.setPassword(props.getProperty(PROPERTY_PASSWORD));
            return dataSource;
        } catch(IOException e) {
            throw new RuntimeException("Couldn't find properties file!");
        }
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}