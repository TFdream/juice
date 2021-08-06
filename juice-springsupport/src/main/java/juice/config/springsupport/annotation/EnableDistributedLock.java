package juice.config.springsupport.annotation;

import juice.config.springsupport.lock.DLockRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Ricky Fung
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DLockRegistrar.class)
public @interface EnableDistributedLock {

    int order();
}
