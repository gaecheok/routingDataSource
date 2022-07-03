package com.example.springbootroutingdatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean("dataSource")
    public DataSource createRouterDatasource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSourceConfig();
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource master = createDataSource("jdbc:mysql://localhost:3306/test", "root", "mousee");
        DataSource slave = createDataSource("jdbc:mysql://localhost:3307/test", "root", "mousee");
        targetDataSources.put("slave",slave);
        targetDataSources.put("master",master);

        routingDataSource.setDefaultTargetDataSource(master);
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    private DataSource createDataSource(String url, String user, String password) {
        com.zaxxer.hikari.HikariDataSource dataSource =
                new com.zaxxer.hikari.HikariDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        return dataSource;
    }

    @Bean
    public DataSource routingLazyDataSource(@Qualifier("dataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("routingLazyDataSource") DataSource routingDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(routingDataSource);
        return transactionManager;
    }
}
