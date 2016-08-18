package com.servitization.commons.socket.remote;

import com.servitization.commons.socket.enums.CharsetEnum;
import com.servitization.commons.socket.enums.CompressEnum;
import com.servitization.commons.socket.enums.EncryptEnum;
import com.servitization.commons.socket.enums.ProtocolEnum;
import com.servitization.commons.socket.remote.adapter.ConvertAdapter;
import com.servitization.commons.socket.remote.adapter.DefaultAdapter;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteService {
    String serviceName();

    String serviceVersion() default "";

    String serviceType();

    CharsetEnum charset() default CharsetEnum.UTF8;

    CompressEnum compress() default CompressEnum.NON;

    EncryptEnum encrypt() default EncryptEnum.NON;

    ProtocolEnum protocol() default ProtocolEnum.OBJECT;

    boolean async() default false;

    long timeOut() default 15000;

    Class<?> resultType() default DefaultType.class;

    Class<? extends ConvertAdapter> resultAdapter() default DefaultAdapter.class;
}
