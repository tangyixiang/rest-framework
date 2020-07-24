package com.sky.rest.framework.annotation.request;

import com.sky.rest.framework.constant.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value();

    String[] method() default {HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
}
