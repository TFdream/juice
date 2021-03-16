package juice.security.security;

import juice.security.SecurityContext;
import juice.security.UserPrincipal;

/**
 * 默认实现
 * @author Ricky Fung
 */
public class DefaultSecurityContext implements SecurityContext {
    public static final DefaultSecurityContext ANON = new DefaultSecurityContext(null, null);

    private Long userId;
    private String token;
    private UserPrincipal principal;

    public DefaultSecurityContext(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public DefaultSecurityContext(Long userId, String token, UserPrincipal principal) {
        this.userId = userId;
        this.token = token;
        this.principal = principal;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public UserPrincipal getUserPrincipal() {
        return principal;
    }
}
