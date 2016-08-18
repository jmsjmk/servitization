package com.servitization.commons.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ManyDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return ManyDataSourceSwitch.getDataSourceType();
    }
}
