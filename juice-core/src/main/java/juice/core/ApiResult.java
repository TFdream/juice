package juice.core;

import java.io.Serializable;

/**
 * @author Ricky Fung
 */
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = -583598838232637332L;

    /**
     * SUCCESS
     */
    public static final int SUCCESS = 0;
    private static final String SUCCESS_TIPS = "SUCCESS";

    /**
     * Failure
     */
    public static final int FAILURE = 1;

    private int status;
    private String errorCode;
    private String message;
    private T data;

    public ApiResult() {
    }

    public ApiResult(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiResult(int status, String errorCode, String message, T data) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResult<T> buildSuccessResult() {
        return new ApiResult<>(SUCCESS, "", SUCCESS_TIPS);
    }
    public static <T> ApiResult<T> buildSuccessResult(T data) {
        return new ApiResult<>(SUCCESS, "", SUCCESS_TIPS, data);
    }

    //---------调用失败------------
    public static <T> ApiResult<T> buildFailureResult(String errorCode, String msg) {
        return new ApiResult(FAILURE, errorCode, msg, null);
    }
    public static <T> ApiResult<T> buildFailureResult(String errorCode, String msg, T data) {
        return new ApiResult(FAILURE, errorCode, msg, data);
    }

    //---------系统停服------------
    public static <T> ApiResult<T> buildServiceUnavailableResult() {
        return new ApiResult(FAILURE, ApiResultCode.SERVICE_UNAVAILABLE.getCode(), ApiResultCode.SERVICE_UNAVAILABLE.getDesc());
    }
    public static <T> ApiResult<T> buildServiceUnavailableResult(String msg) {
        return new ApiResult(FAILURE, ApiResultCode.SERVICE_UNAVAILABLE.getCode(), msg);
    }
    public static <T> ApiResult<T> buildServiceUnavailableResult(String msg, T data) {
        return new ApiResult(FAILURE, ApiResultCode.SERVICE_UNAVAILABLE.getCode(), msg, data);
    }

    //---------系统内部错误---------
    public static <T> ApiResult<T> buildInternalSeverErrorResult() {
        return new ApiResult(FAILURE, ApiResultCode.INTERNAL_SERVER_ERROR.getCode(), ApiResultCode.INTERNAL_SERVER_ERROR.getCode());
    }
    public static <T> ApiResult<T> buildInternalSeverErrorResult(String msg) {
        return new ApiResult(FAILURE, ApiResultCode.INTERNAL_SERVER_ERROR.getCode(), msg);
    }
    public static <T> ApiResult<T> buildInternalSeverErrorResult(String msg, T data) {
        return new ApiResult(FAILURE, ApiResultCode.INTERNAL_SERVER_ERROR.getCode(), msg, data);
    }

    public int getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
