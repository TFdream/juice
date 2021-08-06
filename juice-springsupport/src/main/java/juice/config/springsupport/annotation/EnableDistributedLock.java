package juice.config.springsupport.annotation;

import juice.config.springsupport.Constant;
import juice.config.springsupport.lock.DLockRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * @author Ricky Fung
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DLockRegistrar.class)
public @interface EnableDistributedLock {

    String lockManager() default Constant.DEFAULT_LOCK_MANAGER_BEAN_NAME;

    int order() default Ordered.LOWEST_PRECEDENCE;
}
