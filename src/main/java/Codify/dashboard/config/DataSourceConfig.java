package Codify.dashboard.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean("writeHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public HikariConfig writeHikariConfig() {
        return new HikariConfig();
    }

    @Bean("readHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public HikariConfig readHikariConfig() {
        return new HikariConfig();
    }

    @Bean("writeDataSource")
    public HikariDataSource writeDataSource(@Qualifier("writeHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean("readDataSource")
    public HikariDataSource readDataSource(@Qualifier("readHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Primary
    @Bean
    public DataSource dataSource
            (@Qualifier("writeDataSource") DataSource writeDataSource,
             @Qualifier("readDataSource") DataSource readDataSource) {
        LazyConnectionDataSourceProxy proxy = new LazyConnectionDataSourceProxy(writeDataSource);
        proxy.setReadOnlyDataSource(readDataSource);
        return proxy;
    }


}
