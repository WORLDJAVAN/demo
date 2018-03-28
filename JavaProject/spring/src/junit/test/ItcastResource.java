package junit.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //标志注解的运行期
@Target({ElementType.FIELD,ElementType.METHOD})              //标志注解的位置
public @interface ItcastResource {
  public String name() default "";
}
