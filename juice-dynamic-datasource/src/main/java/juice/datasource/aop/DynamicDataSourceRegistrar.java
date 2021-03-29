package juice.datasource.aop;

import juice.datasource.bpp.DynamicAdvisingBeanPostProcessor;
import juice.datasource.annotation.EnableDynamicDataSource;
import juice.datasource.util.BeanRegistrarUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(EnableDynamicDataSource.class.getName()));

        //优先级
        int order = attributes.getNumber("order");
        BeanDefinitionBuilder bdb = BeanRegistrarUtils.genericBeanDefinition(DynamicAdvisingBeanPostProcessor.class);
        bdb.addPropertyValue("order", order);
        //属性赋值
        bdb.addPropertyValue("dataSourceName", attributes.getString("dataSource"));

        BeanDefinition bd = bdb.getBeanDefinition();

        BeanRegistrarUtils.registerBeanDefinitionIfNotExists(registry, DynamicAdvisingBeanPostProcessor.class.getName(), bd);
    }
}
