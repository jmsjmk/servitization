package com.servitization.commons.db.interceptor;

import com.servitization.commons.log.GlobalConstant;
import com.servitization.commons.log.Log;
import com.servitization.commons.log.trace.OSUtil;
import com.servitization.commons.log.trace.Spanner;
import com.servitization.commons.log.trace.Tracer;
import com.servitization.commons.util.DateUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class})})
public class MybatisInterceptor implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object returnValue;
        MappedStatement mappedStatement = (MappedStatement) invocation
                .getArgs()[0];
        String sqlId = mappedStatement.getId();
        writeBeforeLog(sqlId);
        try {
            returnValue = invocation.proceed();
            writeAfterLog(sqlId, start, null);
        } catch (Exception e) {
            writeAfterLog(sqlId, start, e);
            throw e;
        }
        return returnValue;
    }

    private static void writeBeforeLog(String sqlId) {
        Spanner span = Tracer.getTracer().startRpcTrace();
        Log log = new Log();
        Date date = new Date();
        log.setLogTime(DateUtil.getFormatDate(date, DateUtil.FORMAT_ALL_MM));
        log.setTraceId(span.getTraceId());
        log.setSpan(span.toSpanString());
        log.setBusinessLine(GlobalConstant.getBusinessLine());
        log.setAppName(GlobalConstant.getAppName());
        log.setLogType("001");
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setUserLogType("db_begin");
        log.setServiceName(sqlId);
    }

    private static void writeAfterLog(String sqlId, long starttime, Throwable e) {
        Spanner span = Tracer.getTracer().endRpcTrace();
        Log log = new Log();
        Date date = new Date();
        log.setLogTime(DateUtil.getFormatDate(date, DateUtil.FORMAT_ALL_MM));
        log.setTraceId(span.getTraceId());
        log.setSpan(span.toSpanString());
        log.setBusinessLine(GlobalConstant.getBusinessLine());
        log.setAppName(GlobalConstant.getAppName());
        log.setLogType("001");
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setUserLogType("db_end");
        log.setServiceName(sqlId);
        log.setElapsedTime(String.valueOf(System.currentTimeMillis() - starttime));
        log.setException(e);

    }
//	public static String getSql(Configuration configuration, BoundSql boundSql,
//			String sqlId, long time) {
//		String sql = showSql(configuration, boundSql);
//		StringBuilder str = new StringBuilder(100);
//		str.append(sqlId);
//		str.append(":");
//		str.append(sql);
//		str.append(":");
//		str.append(time);
//		str.append("ms");
//		return str.toString();
//	}

//	private static String getParameterValue(Object obj) {
//		String value = null;
//		if (obj instanceof String) {
//			value = "'" + obj.toString() + "'";
//		} else if (obj instanceof Date) {
//			DateFormat formatter = DateFormat.getDateTimeInstance(
//					DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//
//			value = "'" + formatter.format(new Date()) + "'";
//		} else {
//			if (obj != null) {
//				value = obj.toString();
//			} else {
//				value = "";
//			}
//		}
//		return value;
//	}

//	public static String showSql(Configuration configuration, BoundSql boundSql) {
//		Object parameterObject = boundSql.getParameterObject();
//		List<ParameterMapping> parameterMappings = boundSql
//				.getParameterMappings();
//		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
//		if (parameterMappings.size() > 0 && parameterObject != null) {
//			TypeHandlerRegistry typeHandlerRegistry = configuration
//					.getTypeHandlerRegistry();
//			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
//				sql = sql.replaceFirst("\\?",
//						getParameterValue(parameterObject));
//			} else {
//				MetaObject metaObject = configuration
//						.newMetaObject(parameterObject);
//				for (ParameterMapping parameterMapping : parameterMappings) {
//					String propertyName = parameterMapping.getProperty();
//					if (metaObject.hasGetter(propertyName)) {
//						Object obj = metaObject.getValue(propertyName);
//						sql = sql.replaceFirst("\\?", getParameterValue(obj));
//					} else if (boundSql.hasAdditionalParameter(propertyName)) {
//						Object obj = boundSql
//								.getAdditionalParameter(propertyName);
//						sql = sql.replaceFirst("\\?", getParameterValue(obj));
//					}
//				}
//			}
//		}
//		return sql;
//	}

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) {
    }
}
