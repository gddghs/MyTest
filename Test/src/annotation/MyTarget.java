package annotation;
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface MyTarget{

}//定义个一注解 @MyTarget，用RetentionPolicy.RUNTIME修饰；package com.self;
