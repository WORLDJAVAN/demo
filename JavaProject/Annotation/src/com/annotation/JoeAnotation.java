package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
/* ע��ı����ڡ����Ա�����class�ļ��� */
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE})
/* ���Ա�ע���ֶ��뷽���� */
public @interface JoeAnotation {
	String table() default "";
	String column() default "";
	String Id() default "";
	String condition() default "";
}
