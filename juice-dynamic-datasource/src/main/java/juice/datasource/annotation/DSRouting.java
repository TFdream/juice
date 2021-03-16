package juice.datasource.annotation;

import java.lang.annotation.*;

/**
 * 数据源路由
 * @author Ricky Fung
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DSRouting {

    String value() default "";
}
