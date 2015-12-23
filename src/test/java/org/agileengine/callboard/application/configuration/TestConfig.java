package org.agileengine.callboard.application.configuration;

import org.agileengine.callboard.application.ApplicationData;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan({ApplicationData.PACKAGE_DAO, ApplicationData.PACKAGE_SERVICE})
public class TestConfig {

    public static final String PROPERTY_JDBC_DRIVER = "jdbc.driverClassName";
    public static final String PROPERTY_USERNAME = "jdbc.username";
    public static final String PROPERTY_PASSWORD = "jdbc.password";
    public static final String TEST_DB_URL = "jdbc:h2:mem:callboard;DB_CLOSE_DELAY=-1";

    @Bean
    public DataSource dataSource() {
        try {
            Properties props = PropertiesLoaderUtils.loadAllProperties("persistence.properties");
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(props.getProperty(PROPERTY_JDBC_DRIVER));
            dataSource.setUrl(TEST_DB_URL);
            dataSource.setUsername(props.getProperty(PROPERTY_USERNAME));
            dataSource.setPassword(props.getProperty(PROPERTY_PASSWORD));
            return dataSource;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find properties file!");
        }
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}