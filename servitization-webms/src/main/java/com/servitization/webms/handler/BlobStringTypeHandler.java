package com.servitization.webms.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;

/**
 * 在MyBatis中处理blob与String之间的数据格式转换
 */
public class BlobStringTypeHandler extends BaseTypeHandler<String> {

    private static final Logger LOGGER = Logger.getLogger(BlobStringTypeHandler.class);

    // 指定字符集
    private static final String DEFAULT_CHARSET = "utf-8";

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Blob blob = rs.getBlob(columnName);
        byte[] returnValue = null;
        String result = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        try {
            if (null != returnValue) {
                // 把byte转化成string
                result = new String(returnValue, DEFAULT_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        return result;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        LOGGER.debug("enter function");
        String result = null;
        Blob blob = rs.getBlob(columnIndex);
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        try {
            // 把byte转化成string
            if (null != returnValue) {
                result = new String(returnValue, DEFAULT_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        LOGGER.debug("exit function");
        return result;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {

        Blob blob = cs.getBlob(columnIndex);
        byte[] returnValue = null;
        String result = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        try {
            if (null != returnValue) {
                result = new String(returnValue, DEFAULT_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        return result;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    String parameter, JdbcType jdbcType) throws SQLException {

        ByteArrayInputStream bis;
        try {
            // 把String转化成byte流
            bis = new ByteArrayInputStream(parameter.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        ps.setBinaryStream(i, bis, parameter.length());
    }

}
