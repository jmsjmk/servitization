package com.servitization.commons.db;

/**
 * 动态数据库选择
 * 根据DataSourceType是数据源的类型来决定是使用数据库
 * 通过ThreadLocal绑定实现选择功能
 */
public class ManyDataSourceSwitch {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }


}
