package juice.redis.serialization;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
public interface Serializer {

    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> classOfT) throws IOException;

}
