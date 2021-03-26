package juice.datasource.aop;

import juice.datasource.DynamicDataSource;
import juice.datasource.annotation.DSRouting;
import juice.util.StringUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourceInterceptor implements MethodInterceptor, BeanFactoryAware {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_DATASOURCE_BEAN_NAME = "dynamicDataSource";

    private DynamicDataSource dynamicDataSource;
    private BeanFactory beanFactory;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (this.dynamicDataSource == null) {
            LOG.warn("【动态数据源切换】当前上下文中没有查找到DynamicDataSource类型bean");
            return invocation.proceed();
        }

        Method method = invocation.getMethod();
        Class<?> targetClass = method.getDeclaringClass();

        String pkgName = targetClass.getPackage().getName();
        //优先从方法上查找
        DSRouting dsRouting = AnnotationUtils.getAnnotation(method, DSRouting.class);
        if (dsRouting == null) {    //尝试从类上查找
            dsRouting = AnnotationUtils.findAnnotation(targetClass, DSRouting.class);
        }

        // 没有这个注解说明不正常，直接抛出异常
        if (dsRouting == null) {
            LOG.warn("【动态数据源切换】没有数据源切换标识, 不做任何处理, method={}, class={}",
                    method.getName(), ClassUtils.getShortName(targetClass));
            return invocation.proceed();
        }

        //优先从注解上获取
        String targetDsKey = dsRouting.value();
        if(StringUtils.isEmpty(targetDsKey)) {    //不存在时尝试根据包名来获取
            targetDsKey = this.dynamicDataSource.getPkgDefaultDsKey(pkgName);
            if (LOG.isDebugEnabled()) {
                LOG.debug("【动态数据源切换】注解上配置为空, 尝试根据包名来获取, pkgName:{}, method={}, class={}, targetDsKey:{}",
                        pkgName, method.getName(), ClassUtils.getShortName(targetClass), targetDsKey);
            }
        }

        if (StringUtils.isEmpty(targetDsKey)) {
            LOG.warn("【动态数据源切换】没有找到数据源标识key 请检查代码配置, method={}, class={}",
                    method.getName(), ClassUtils.getShortName(targetClass));
        }

        //记录当前key
        String currDsKey = this.dynamicDataSource.getDataSourceKey();

        this.dynamicDataSource.setDataSourceKey(targetDsKey);

        if (LOG.isDebugEnabled()) {
            LOG.debug("【动态数据源切换】路由主键配置:{}, 数据源从={} 切换到={})",
                    dsRouting.value(), currDsKey, targetDsKey);
        }

        try {
            return invocation.proceed();
        } finally {
            if (LOG.isDebugEnabled()) {
                LOG.debug("【动态数据源切换】方法执行完成 清理当前数据源={}", this.dynamicDataSource.getDataSourceKey());
            }
            //执行完成后清理
            this.dynamicDataSource.clear();
        }
    }

    public void configure() {
        //初始化
        LOG.info("动态数据源拦截器-初始化开始, beanFactory={}", this.beanFactory);
        this.dynamicDataSource = getDefaultDynamicDataSource(this.beanFactory);
        LOG.info("动态数据源拦截器-初始化结束, dynamicDataSource={}", dynamicDataSource);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected DynamicDataSource getDefaultDynamicDataSource(BeanFactory beanFactory) {
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
                    return beanFactory.getBean(DEFAULT_DATASOURCE_BEAN_NAME, DynamicDataSource.class);
                }
                catch (NoSuchBeanDefinitionException ex2) {
                    if (LOG.isInfoEnabled()) {
                        LOG.info(String.format("More than one DynamicDataSource bean found within the context, and none is named " +
                                "'%s'. Mark one of them as primary or name it '%s' (possibly " +
                                "as an alias) in order to use it for routing processing: ", DEFAULT_DATASOURCE_BEAN_NAME, ex.getBeanNamesFound()));
                    }
                }
            }
        }
        return null;
    }
}
