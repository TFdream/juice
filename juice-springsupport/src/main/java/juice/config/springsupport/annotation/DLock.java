package juice.config.springsupport.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * @author Ricky Fung
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DLock {

    String prefix() default "";

    String key();

    long waitTime() default -1L;

    long leaseTime() default 5000L;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
