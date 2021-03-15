package juice.serializer;

import juice.exceptions.SerializationException;

/**
 * @author Ricky Fung
 */
public interface ObjectSerializer<T> {

    /**
     * Serialize the given object to binary data.
     *
     * @param t object to serialize. Can be {@literal null}.
     * @return the equivalent binary data. Can be {@literal null}.
     */
    byte[] serialize(T t) throws SerializationException;

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation. Can be {@literal null}.
     * @return the equivalent object instance. Can be {@literal null}.
     */
    T deserialize(byte[] bytes) throws SerializationException;

    //===========
    /**
     * Obtain a simple {@link String} to {@literal byte[]} (and back) serializer using
     * {@link java.nio.charset.StandardCharsets#UTF_8 UTF-8} as the default {@link java.nio.charset.Charset}.
     *
     * @return never {@literal null}.
     * @since 2.1
     */
    static ObjectSerializer<String> string() {
        return StringSerializer.UTF_8;
    }
}
