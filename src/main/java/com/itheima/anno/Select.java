package com.itheima.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Chen Shaoying
 * @date 2020-08-25 04:17
 */

@Target(ElementType.METHOD)  //注解使用范围
@Retention(RetentionPolicy.RUNTIME) //注解生命周期保留到运行时
public @interface Select {
    String value();
}
