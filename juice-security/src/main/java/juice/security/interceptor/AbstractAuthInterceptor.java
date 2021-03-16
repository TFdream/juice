package juice.security.interceptor;

import juice.security.SecurityContextHolder;
import juice.security.annotation.OptionalAuth;
import juice.security.annotation.RequiredAuth;
import juice.security.security.DefaultSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器基类
 * @author Ricky Fung
 */
public abstract class AbstractAuthInterceptor extends HandlerInterceptorAdapter {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiredAuth requiredAuth = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), RequiredAuth.class);
        if (requiredAuth != null) {
            return checkAuth0(request, handlerMethod, true);
        }

        OptionalAuth optionalAuth = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), OptionalAuth.class);
        if (optionalAuth != null) {
            return checkAuth0(request, handlerMethod, false);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除
        SecurityContextHolder.clear();
    }

    //============

    protected boolean checkAuth0(HttpServletRequest request, HandlerMethod handlerMethod, boolean required) throws Exception {
        //设置默认context
        SecurityContextHolder.setContext(DefaultSecurityContext.ANON);

        return checkUserAuth(request, handlerMethod, required);
    }

    protected abstract boolean checkUserAuth(HttpServletRequest request, HandlerMethod handlerMethod, boolean required) throws Exception;

}
