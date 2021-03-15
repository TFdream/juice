package juice.contracts;

/**
 * @author Ricky Fung
 */
public enum ResultCode {
    SUCCESS(1, "请求成功"),
    INVALID_PARAM(400, "请求参数不合法"),
    USER_UNAUTHORIZED(401, "用户未登录"),
    USER_NON_PRIVILEGED(403, "您没有权限"),
    INTERNAL_SERVER_ERROR(500, "服务器开小差了，请稍后再试！"),
    SYSTEM_MAINTAINING(503, "系统维护中，给您带来不便敬请谅解！"),
    ;
    private int code;
    private String message;
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
