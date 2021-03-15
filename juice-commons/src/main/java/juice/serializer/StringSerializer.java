package juice.serializer;

import juice.commons.Assertions;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Ricky Fung
 */
public class StringSerializer implements ObjectSerializer<String> {

    public static final StringSerializer GBK = new StringSerializer(Charset.forName("GBK"));

    public static final StringSerializer UTF_8 = new StringSerializer(StandardCharsets.UTF_8);

    private final Charset charset;
    /**
     * Creates a new {@link StringSerializer} using {@link StandardCharsets#UTF_8 UTF-8}.
     */
    public StringSerializer() {
        this(StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link StringSerializer} using the given {@link Charset} to encode and decode strings.
     *
     * @param charset must not be {@literal null}.
     */
    public StringSerializer(Charset charset) {

        Assertions.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
     */
    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.serializer.RedisSerializer#serialize(java.lang.Object)
     */
    @Override
    public byte[] serialize(String string) {
        return (string == null ? null : string.getBytes(charset));
    }
}
