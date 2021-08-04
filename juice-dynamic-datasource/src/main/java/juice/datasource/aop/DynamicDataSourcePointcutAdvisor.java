package juice.datasource.aop;

import juice.datasource.annotation.DSRouting;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.beans.factory.InitializingBean;

import java.lang.annotation.Annotation;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourcePointcutAdvisor extends AbstractPointcutAdvisor implements InitializingBean {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_DATASOURCE_BEAN_NAME = "dynamicDataSource";

    private Advice advice;

    private Pointcut pointcut;

    //默认数据源名称
    private String dataSourceName;

    public DynamicDataSourcePointcutAdvisor() {}

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = buildPointcut();
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        if (advice == null) {
            advice = buildAdvice();
        }
        return advice;
    }

    protected Advice buildAdvice() {
        DynamicDataSourceInterceptor interceptor = new DynamicDataSourceInterceptor();
        return interceptor;
    }

    protected Pointcut buildPointcut() {
        return new AnnotationMethodPointcut(DSRouting.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("动态数据源切换-初始化开始, order={}, dataSourceName={}", getOrder(), dataSourceName);
        this.pointcut = getPointcut();
        this.advice = getAdvice();
    }

    static class AnnotationMethodPointcut implements Pointcut {
        private ClassFilter classFilter;
        private AnnotationMethodMatcher methodMatcher;
        AnnotationMethodPointcut(Class<? extends Annotation> annotationType) {
            this(annotationType, true);
        }

        AnnotationMethodPointcut(Class<? extends Annotation> annotationType, boolean checkInherited) {
            this.classFilter = new AnnotationClassFilter(annotationType, checkInherited);
            this.methodMatcher = new AnnotationMethodMatcher(annotationType, checkInherited);
        }

        @Override
        public ClassFilter getClassFilter() {
            return classFilter;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return this.methodMatcher;
        }
    }
}
