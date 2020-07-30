package com.stone.demo.sign.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotRepeatSubmit {

    /***
     * 过期时间 单位毫秒 5000
     *
     * @return
     */
    long value() default 5000;

}
