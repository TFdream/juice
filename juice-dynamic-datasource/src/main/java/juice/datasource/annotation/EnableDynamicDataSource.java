package juice.datasource.annotation;

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
     * 扫描的包路径
     */
    String[] basePackages() default {};

    /**
     * 动态数据源名称
     * @return
     */
    String dataSource() default "dynamicDataSource";

    /**
     * The order of the apollo config, default is {@link Ordered#LOWEST_PRECEDENCE}, which is Integer.MAX_VALUE.
     * If there are properties with the same name in different apollo configs, the apollo config with smaller order wins.
     * @return
     */
    int order() default -1;
}
