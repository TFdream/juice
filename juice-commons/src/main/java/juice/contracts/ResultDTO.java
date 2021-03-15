package juice.contracts;

import java.io.Serializable;

/**
 * @author Ricky Fung
 */
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 128L;

    private int code;
    private String msg;
    private T data;

    public ResultDTO() {}

    public ResultDTO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDTO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return code == ResultCode.SUCCESS.getCode();
    }

    //======== getter/setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //========
    public static ResultDTO ok() {
        return new ResultDTO(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }
    public static <T> ResultDTO ok(T bean) {
        return new ResultDTO(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), bean);
    }

    public static ResultDTO failure(int code, String message) {
        return new ResultDTO(code, message);
    }

    public static <T> ResultDTO failure(int code, String message, T bean) {
        return new ResultDTO(code, message, bean);
    }

    //未登录
    public static ResultDTO notLogin() {
        return new ResultDTO(ResultCode.USER_UNAUTHORIZED.getCode(), ResultCode.USER_UNAUTHORIZED.getMessage());
    }
    public static ResultDTO notLogin(String message) {
        return new ResultDTO(ResultCode.USER_UNAUTHORIZED.getCode(), message);
    }

    //参数非法
    public static ResultDTO invalidParam() {
        return new ResultDTO(ResultCode.INVALID_PARAM.getCode(), ResultCode.INVALID_PARAM.getMessage());
    }
    public static ResultDTO invalidParam(String message) {
        return new ResultDTO(ResultCode.INVALID_PARAM.getCode(), message);
    }

    //系统异常
    public static ResultDTO systemError() {
        return new ResultDTO(ResultCode.INTERNAL_SERVER_ERROR.getCode(), ResultCode.INTERNAL_SERVER_ERROR.getMessage());
    }
    public static ResultDTO systemError(String message) {
        return new ResultDTO(ResultCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }

}
