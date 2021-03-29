package juice.datasource.aop;

import juice.commons.Assertions;
import juice.datasource.DynamicDataSource;
import juice.util.StringUtils;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourcePointcutAdvisor extends AbstractPointcutAdvisor {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_DATASOURCE_BEAN_NAME = "dynamicDataSource";

    private Advice advice;

    private Pointcut pointcut;

    //默认数据源名称
    private String dataSourceName;

    private Set<Class<? extends Annotation>> targetAnnotationTypes;

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        Assert.notNull(annotationType, "'annotationType' must not be null");
        if (targetAnnotationTypes == null) {
            targetAnnotationTypes = new LinkedHashSet<>(2);
        }
        targetAnnotationTypes.add(annotationType);
    }

    public void configure(BeanFactory beanFactory) {
        this.pointcut = buildPointcut(targetAnnotationTypes);
        this.advice = buildAdvice(beanFactory);
    }

    protected Advice buildAdvice(BeanFactory beanFactory) {
        DynamicDataSourceInterceptor interceptor = new DynamicDataSourceInterceptor();
        DynamicDataSource dynamicDataSource = getDefaultDynamicDataSource(beanFactory, dataSourceName);
        Assertions.notNull(dynamicDataSource, "dynamicDataSource is NULL");
        interceptor.setDynamicDataSource(dynamicDataSource);
        return interceptor;
    }

    protected Pointcut buildPointcut(Set<Class<? extends Annotation>> dsAnnotationTypes) {
        ComposablePointcut result = null;
        for (Class<? extends Annotation> dsAnnotationType : dsAnnotationTypes) {
            Pointcut cpc = new AnnotationMatchingPointcut(dsAnnotationType, true);
            Pointcut mpc = new AnnotationMatchingPointcut(null, dsAnnotationType, true);
            if (result == null) {
                result = new ComposablePointcut(cpc);
            } else {
                result.union(cpc);
            }
            result = result.union(mpc);
        }
        return (result != null ? result : Pointcut.TRUE);
    }

    //======

    protected DynamicDataSource getDefaultDynamicDataSource(BeanFactory beanFactory, String name) {
        if (StringUtils.isEmpty(name)) {
            name = DEFAULT_DATASOURCE_BEAN_NAME;
        }
        if (beanFactory != null) {
            try {
                // Search for TaskExecutor bean... not plain Executor since that would
                // match with ScheduledExecutorService as well, which is unusable for
                // our purposes here. TaskExecutor is more clearly designed for it.
                return beanFactory.getBean(DynamicDataSource.class);
            }
            catch (NoUniqueBeanDefinitionException ex) {
                LOG.debug("Could not find unique DynamicDataSource bean", ex);
                try {
                    return beanFactory.getBean(name, DynamicDataSource.class);
                }
                catch (NoSuchBeanDefinitionException ex2) {
                    if (LOG.isInfoEnabled()) {
                        LOG.info(String.format("More than one DynamicDataSource bean found within the context, and none is named " +
                                "'%s'. Mark one of them as primary or name it '%s' (possibly " +
                                "as an alias) in order to use it for routing processing: ", name, ex.getBeanNamesFound()));
                    }
                }
            }
        }
        return null;
    }
}
