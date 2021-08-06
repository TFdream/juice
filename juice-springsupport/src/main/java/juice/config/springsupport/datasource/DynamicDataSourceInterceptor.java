package juice.config.springsupport.datasource;

import juice.config.springsupport.annotation.DSRouting;
import juice.config.springsupport.util.AnnotationScanUtils;
import juice.datasource.DynamicDSContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourceInterceptor implements MethodInterceptor {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        String dsKey = determineDatasourceKey(invocation);
        DynamicDSContextHolder.push(dsKey);
        if (LOG.isDebugEnabled()) {
            LOG.debug("动态数据源切换-拦截器开始, 方法签名={}, 入栈dsKey={}", getMethodKey(invocation), dsKey);
        }
        try {
            return invocation.proceed();
        } finally {
            if (LOG.isDebugEnabled()) {
                LOG.debug("动态数据源切换-拦截器结束, 方法签名={}, 出栈dsKey={}", getMethodKey(invocation), dsKey);
            }
            DynamicDSContextHolder.pop();
        }
    }

    //==========

    private String determineDatasourceKey(MethodInvocation invocation) {
        DSRouting dsRouting = getTargetAnnotation(invocation);
        if (dsRouting != null) {
            return dsRouting.value();
        }
        return null;
    }

    private String getMethodKey(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        return String.format("%s#%s", method.getDeclaringClass().getSimpleName(), method.getName());
    }

    private DSRouting getTargetAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        //优先从方法上查找
        //注：此处需要使用AnnotatedElementUtils.findMergedAnnotation，不能使用AnnotationUtils.findAnnotation
        DSRouting dsRouting = AnnotationScanUtils.findAnnotation(method, DSRouting.class);
        if (dsRouting == null) {    //尝试从类上查找
            dsRouting = AnnotationScanUtils.findAnnotation(method.getDeclaringClass(), DSRouting.class);
        }
        return dsRouting;
    }
}
