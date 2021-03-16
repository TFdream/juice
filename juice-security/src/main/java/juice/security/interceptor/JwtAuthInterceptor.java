package juice.security.interceptor;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * JWT用户身份认证 - 统一权限拦截器
 * @author Ricky Fung
 */
public class JwtAuthInterceptor extends AbstractAuthInterceptor {
    private static final int COOKIE_SEPARATOR_LENGTH = 7;

    @Override
    protected boolean checkUserAuth(HttpServletRequest request, HandlerMethod handlerMethod, boolean required) throws Exception {
        String methodName = handlerMethod.getMethod().getName();

        return true;
    }

}
