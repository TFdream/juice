package juice.core.util;

import juice.contracts.ResponseCode;
import juice.contracts.ResponseDTO;
import juice.contracts.ResultDTO;
import juice.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ricky Fung
 */
public abstract class ServletResponseUtils {
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    public static void sendSystemError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
    }

    public static void sendSystemError(HttpServletResponse response, String msg) throws IOException {
        response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, msg);
    }

    public static void sendError(HttpServletResponse response, int sc, String msg) throws IOException {
        response.sendError(sc, msg);
    }

    //-----------
    public static void writeResponse(HttpServletResponse response, ResponseDTO responseDTO) throws IOException {
        writeString(response, JsonUtils.toJson(responseDTO));
    }

    public static void writeResult(HttpServletResponse response, ResultDTO result) throws IOException {
        writeString(response, JsonUtils.toJson(result));
    }

    public static void writeString(HttpServletResponse response, String data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", CONTENT_TYPE);
        response.getOutputStream().write(data.getBytes(StringUtils.UTF_8));
    }

    public static void writeBytes(HttpServletResponse response, byte[] buf) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", CONTENT_TYPE);
        response.getOutputStream().write(buf);
    }
}
