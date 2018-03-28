package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
/* 注解的保留期、可以保留在class文件上 */
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE})
/* 可以标注在字段与方法上 */
public @interface JoeAnotation {
	String table() default "";
	String column() default "";
	String Id() default "";
	String condition() default "";
}
