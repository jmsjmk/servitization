package com.servitization.commons.business.agent;

import com.servitization.commons.business.agent.enums.DataTypeEnum;
import com.servitization.commons.business.agent.enums.MethodTypeEnum;
import com.servitization.commons.business.agent.enums.RPCTypeEnum;
import com.servitization.commons.business.agent.rpc.ContentType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AgentService {

    String name();

    MethodTypeEnum methodType() default MethodTypeEnum.POST;

    DataTypeEnum reqType() default DataTypeEnum.JSON;

    DataTypeEnum resultType() default DataTypeEnum.JSON;

    RPCTypeEnum rpcType() default RPCTypeEnum.HTTP;

    ContentType contentType() default ContentType.POST_JSON;

    boolean customizeParameter() default false;                            //是否定制参数

    int timeOut() default 30000;                            //连接超时时间,默认30秒

    int readTimeOut() default 200000;                            //读数据超时时间,默认200秒

}
