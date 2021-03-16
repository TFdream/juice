package juice.security.internal;

import juice.security.UserPrincipal;

/**
 * @author Ricky Fung
 */
public class JwtUserPrincipal implements UserPrincipal {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 颁发时间，单位：秒
     */
    private long issueAt;
    /**
     * 失效时间，单位：秒
     */
    private long expiryAt;

    /**
     * 1.pc 2.mobile-h5 3.android 4.ios
     */
    private int clientType;
    /**
     * 签名
     */
    private String sign;

    @Override
    public Long getId() {
        return userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public long getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(long issueAt) {
        this.issueAt = issueAt;
    }

    @Override
    public long getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(long expiryAt) {
        this.expiryAt = expiryAt;
    }

    @Override
    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String getToken() {
        return null;
    }

}
