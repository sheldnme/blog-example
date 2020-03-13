package com.xxs.example.blog.dal.model;

import java.lang.annotation.*;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName FieldName
 * @description 注解
 * @date created in 11:03 2020/3/12
 */
@Inherited//注解可继承
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {

    String name() default "";
}
