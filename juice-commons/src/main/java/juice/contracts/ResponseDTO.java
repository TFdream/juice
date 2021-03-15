package juice.contracts;

import java.io.Serializable;

/**
 * @author Ricky Fung
 */
public class ResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = 255L;

    private int code;
    private String message;
    private T data;

    public static ResponseDTO ok() {
        return ok(null);
    }

    public static ResponseDTO ok(Object data) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.SUCCESS.getCode());
        responseDTO.setMessage("请求成功");
        responseDTO.setData(data);
        return responseDTO;
    }

    //--------参数错误
    public static ResponseDTO invalidParam(String errorMsg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.INVALID_PARAM.getCode());
        responseDTO.setMessage(errorMsg);
        return responseDTO;
    }

    //-------用户未登录
    public static ResponseDTO notLogin() {
        return notLogin(ResponseCode.USER_UNAUTHORIZED.getMessage());
    }
    public static ResponseDTO notLogin(String errorMsg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.USER_UNAUTHORIZED.getCode());
        responseDTO.setMessage(errorMsg);
        return responseDTO;
    }

    //-------用户无权限
    public static ResponseDTO notAllowed() {
        return notAllowed(ResponseCode.USER_NON_PRIVILEGED.getMessage());
    }
    public static ResponseDTO notAllowed(String errorMsg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.USER_NON_PRIVILEGED.getCode());
        responseDTO.setMessage(errorMsg);
        return responseDTO;
    }

    //---------系统升级维护中
    public static ResponseDTO notAvailable() {
        return notAvailable(ResponseCode.SYSTEM_MAINTAINING.getMessage());
    }
    public static ResponseDTO notAvailable(String errorMsg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.SYSTEM_MAINTAINING.getCode());
        responseDTO.setMessage(errorMsg);
        return responseDTO;
    }

    //--------业务自定义异常
    public static ResponseDTO failure(int code, String errorMsg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMessage(errorMsg);
        return responseDTO;
    }
    public static <T> ResponseDTO failure(int code, String errorMsg, T data) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMessage(errorMsg);
        responseDTO.setData(data);
        return responseDTO;
    }

    //-------系统异常
    public static ResponseDTO systemError() {
        return systemError(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
    }
    public static ResponseDTO systemError(String message) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
        responseDTO.setMessage(message);
        return responseDTO;
    }

    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS.getCode();
    }

    //----------
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
