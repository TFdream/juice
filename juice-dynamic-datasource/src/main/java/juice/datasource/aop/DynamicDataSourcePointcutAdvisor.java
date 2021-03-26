package juice.datasource.aop;

import juice.datasource.annotation.DSRouting;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourcePointcutAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private Advice advice;

    private Pointcut pointcut;
    //默认数据源名称
    private String dataSourceName;

    private Set<Class<? extends Annotation>> targetAnnotationTypes;

    public DynamicDataSourcePointcutAdvisor() {
        this.targetAnnotationTypes = new LinkedHashSet<>(2);
        targetAnnotationTypes.add(DSRouting.class);
        this.advice = buildAdvice();
        this.pointcut = buildPointcut(targetAnnotationTypes);
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceName() {
        return dataSourceName;
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
        this.targetAnnotationTypes.add(annotationType);
        this.pointcut = buildPointcut(this.targetAnnotationTypes);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    protected Advice buildAdvice() {
        DynamicDataSourceInterceptor interceptor = new DynamicDataSourceInterceptor();
        interceptor.configure();
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

}
