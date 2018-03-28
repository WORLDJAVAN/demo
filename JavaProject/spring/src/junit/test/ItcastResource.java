package junit.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //��־ע���������
@Target({ElementType.FIELD,ElementType.METHOD})              //��־ע���λ��
public @interface ItcastResource {
  public String name() default "";
}