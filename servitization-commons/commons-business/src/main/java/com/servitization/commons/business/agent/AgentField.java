package com.servitization.commons.business.agent;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AgentField {
    String name();
}
