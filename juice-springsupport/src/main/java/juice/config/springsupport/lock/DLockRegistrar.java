package juice.config.springsupport.lock;

import juice.config.springsupport.annotation.EnableDistributedLock;
import juice.config.springsupport.util.BeanRegistrarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Ricky Fung
 */
public class DLockRegistrar implements ImportBeanDefinitionRegistrar {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(EnableDistributedLock.class.getName()));

        //优先级
        int order = attributes.getNumber("order");
        BeanDefinitionBuilder bdb = BeanRegistrarUtils.genericBeanDefinition(DLockPointcutAdvisor.class);
        bdb.addPropertyValue("order", order);

        BeanDefinition bd = bdb.getBeanDefinition();
        //不能少，否则InfrastructureAdvisorAutoProxyCreator不生效
        bd.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        String beanName = DLockPointcutAdvisor.class.getName();
        BeanRegistrarUtils.registerBeanDefinitionIfNotExists(registry, beanName, bd);

        LOG.info("分布式锁-初始化, bean={} 自动装配完成", beanName);
    }
}
