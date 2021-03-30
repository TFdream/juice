package juice.core.util;

import com.google.gson.*;
import juice.util.DateUtils;
import juice.util.StringUtils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Ricky Fung
 */
public class JsonUtils {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Date.class, new JdkDateSerializer())
            .registerTypeAdapter(LocalDateTime.class, new Java8DateTimeSerializer())
            .registerTypeAdapter(LocalDate.class, new Java8DateSerializer())
            .create();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T parseObject(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
    public static <T> T parseObject(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }
}

//java8
class Java8DateSerializer implements
        JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json==null || json.isJsonNull()) {
            return null;
        }
        String dateStr = json.getAsString();
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        return DateUtils.parseDate(dateStr);
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        if (src==null) {
            return null;
        }
        return new JsonPrimitive(DateUtils.formatDate(src));
    }
}

//java8
class Java8DateTimeSerializer implements
        JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json==null || json.isJsonNull()) {
            return null;
        }
        String dateStr = json.getAsString();
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        return DateUtils.parseDateTime(dateStr);
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        if (src==null) {
            return null;
        }
        return new JsonPrimitive(DateUtils.format(src));
    }
}

/**
 * gson Date序列化/反序列化
 * @author Ricky Fung
 */
class JdkDateSerializer implements
        JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json==null || json.isJsonNull()) {
            return null;
        }
        String dateStr = json.getAsString();
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        return DateUtils.parseJdkDate(dateStr);
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        if (src==null) {
            return null;
        }
        return new JsonPrimitive(DateUtils.format(src));
    }
}
