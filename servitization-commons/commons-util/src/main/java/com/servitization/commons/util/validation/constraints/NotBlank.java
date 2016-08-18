package com.servitization.commons.util.validation.constraints;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotBlank {
    String message();
}
