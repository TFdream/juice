package juice.core.util;

import com.google.gson.*;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * @author Ricky Fung
 */
public class JsonUtils {
    private static final Gson GSON = new GsonBuilder().setDateFormat(DateUtils.STANDARD_FORMAT).create();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T parseObject(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
    public static <T> T parseObject(Reader json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T parseObject(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }
    public <T> T parseObject(Reader json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public static JsonObject parseJsonObject(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return new JsonParser().parse(json).getAsJsonObject();
    }

    public static JsonArray parseJsonArray(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return new JsonParser().parse(json).getAsJsonArray();
    }
}
