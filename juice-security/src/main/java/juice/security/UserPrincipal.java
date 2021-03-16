package juice.security;

/**
 * @author Ricky Fung
 */
public interface UserPrincipal {

    Long getId();

    String getNickname();

    String getToken();

    /**
     * 颁发时间 单位：秒
     * @return
     */
    long getIssueAt();

    /**
     * 过期时间 单位：秒
     * @return
     */
    long getExpiryAt();

    /**
     * 获取客户端类型 参考: ClientType 枚举类
     * @return
     */
    int getClientType();
}
