package juice.datasource.annotation;

import juice.datasource.aop.DynamicDataSourceInterceptor;
import juice.datasource.aop.DynamicDataSourceRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

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
     * The name of the Dynamic DataSource, default is {@link DynamicDataSourceInterceptor#DEFAULT_DATASOURCE_BEAN_NAME}.
     * @return
     */
    String dataSource() default "";

    /**
     * The order of the DynamicDataSourcePointcutAdvisor, default is {@link Ordered#LOWEST_PRECEDENCE}, which is Integer.MAX_VALUE.
     * @return
     */
    int order() default -1;
}
