package juice.datasource.aop;

import juice.datasource.DynamicDataSource;
import juice.datasource.annotation.DSRouting;
import juice.util.StringUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourceInterceptor implements MethodInterceptor {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private DynamicDataSource dynamicDataSource;

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

    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

}
