package juice.datasource.bpp;

import juice.datasource.annotation.DSRouting;
import juice.datasource.aop.DynamicDataSourcePointcutAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author Ricky Fung
 */
public class DynamicAdvisingBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    //默认数据源名称
    private String dataSourceName;

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        LOG.info("BeanPostProcessor init start, order={}, beanFactory={}, dataSourceName={}", getOrder(), beanFactory, dataSourceName);
        super.setBeanFactory(beanFactory);
        DynamicDataSourcePointcutAdvisor advisor = new DynamicDataSourcePointcutAdvisor();
        advisor.setAnnotationType(DSRouting.class);
        advisor.setDataSourceName(dataSourceName);
        //优先级
        advisor.setOrder(getOrder());
        //初始化
        advisor.configure(beanFactory);
        this.advisor = advisor;
    }
}
