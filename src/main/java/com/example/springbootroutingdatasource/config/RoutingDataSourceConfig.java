package com.example.springbootroutingdatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSourceConfig extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        //트랜젝션 상태에 따라 결정
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            return "slave";
        } else {
            return "master";
        }
    }
}
