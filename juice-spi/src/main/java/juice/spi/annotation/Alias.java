package juice.spi.annotation;

import java.lang.annotation.*;

/**
 * @author Ricky Fung
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Alias {
    
    String value() default "";
}
