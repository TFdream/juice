package juice.config.springsupport.annotation;

import juice.config.springsupport.datasource.DynamicDataSourceRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Ricky Fung
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DynamicDataSourceRegistrar.class)
public @interface EnableDynamicDataSource {

    /**
     * The order of the DynamicDataSourcePointcutAdvisor, default is -1.
     * @return
     */
    int order() default -1;
}
