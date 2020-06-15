package com.qfedu.commons;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface isLoginAnno {
    boolean mustLogin() default false;
}
