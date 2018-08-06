package juice.ratelimit;

/**
 * @author Ricky Fung
 */
public class Response {
    /**
     * 是否允许访问( 未被限流 )
     */
    private final boolean allowed;
    /**
     * 令牌桶剩余数量
     */
    private final long tokensRemaining;

    public Response(boolean allowed, long tokensRemaining) {
        this.allowed = allowed;
        this.tokensRemaining = tokensRemaining;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public long getTokensRemaining() {
        return tokensRemaining;
    }

}
