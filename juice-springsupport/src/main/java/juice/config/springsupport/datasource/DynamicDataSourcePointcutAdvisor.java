package juice.config.springsupport.datasource;

import juice.config.springsupport.annotation.DSRouting;
import juice.config.springsupport.CommonAnnotationPointcut;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

import java.lang.annotation.Annotation;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourcePointcutAdvisor extends AbstractPointcutAdvisor implements InitializingBean {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final Advice advice;
    private final Pointcut pointcut;

    //目标注解
    private Class<? extends Annotation> targetAnnotation;

    public DynamicDataSourcePointcutAdvisor() {
        this(DSRouting.class);
    }

    public DynamicDataSourcePointcutAdvisor(Class<? extends Annotation> targetAnnotation) {
        this.targetAnnotation = targetAnnotation;
        this.pointcut = buildPointcut(targetAnnotation);
        this.advice = buildAdvice();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    public void setTargetAnnotation(Class<? extends Annotation> targetAnnotation) {
        this.targetAnnotation = targetAnnotation;
    }

    protected Advice buildAdvice() {
        return new DynamicDataSourceInterceptor();
    }

    protected Pointcut buildPointcut(Class<? extends Annotation> targetAnnotation) {
        return new CommonAnnotationPointcut(targetAnnotation, true);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("动态数据源切换-初始化完成, order={}, targetAnnotation={}, pointcut={}, advice={}",
                getOrder(), targetAnnotation, this.pointcut, this.advice);
    }

}
