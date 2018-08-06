package juice.core;

/**
 * @author Ricky Fung
 */
public enum ApiResultCode {

    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    INSUFFICIENT_PRIVILEGE("403", "insufficient privileges"),

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    BAD_GATEWAY("502", "Bad Gateway"),
    SERVICE_UNAVAILABLE("503", "Service Unavailable"),
    ;
    private String code;
    private String desc;
    ApiResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
