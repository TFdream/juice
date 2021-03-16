package juice.security;


/**
 * 用户身份认证上下文
 * @author Ricky Fung
 */
public interface SecurityContext {
    /**
     * 用户ID
     * @return
     */
    Long getUserId();

    /**
     * 原始token串
     * @return
     */
    String getToken();

    /**
     * 获取用户详细信息
     * @return
     */
    UserPrincipal getUserPrincipal();
}
