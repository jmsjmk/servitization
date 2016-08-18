package com.servitization.commons.db;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 解决直接使用BasicDataSource在tomcat下出现warn日志的问题
 */
public class FixedBasicDataSource extends BasicDataSource {

    @Override
    public synchronized void close() throws SQLException {
        DriverManager.deregisterDriver(DriverManager.getDriver(url));
        super.close();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }


    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
