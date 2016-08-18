package com.servitization.yoke.dao;

import com.servitization.yoke.entity.EMVersionKey;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBAccess {

    private DataSource dataSource;

    public DBAccess(DataSource d) {
        this.dataSource = d;
    }

    private static String sqlString = "select version,param_key from em_version_key";

    public List<EMVersionKey> selectEmVersionKeys() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<EMVersionKey> emVersionKeys = new ArrayList<EMVersionKey>(64);
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sqlString);
            if (rs != null) {
                while (rs.next()) {
                    EMVersionKey e = new EMVersionKey();
                    e.setClientKey(rs.getString(1));
                    e.setParamKey(rs.getString(2));
                    emVersionKeys.add(e);
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return emVersionKeys;
    }
}
