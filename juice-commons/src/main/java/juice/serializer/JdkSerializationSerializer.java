package juice.serializer;

import juice.commons.Assertions;
import juice.exceptions.NestedIOException;
import juice.exceptions.SerializationException;
import juice.util.ClassUtils;

import java.io.*;

/**
 * Java Serialization serializer.
 * @author Ricky Fung
 */
public class JdkSerializationSerializer implements ObjectSerializer<Object> {

    private final ClassLoader classLoader;

    /**
     * Creates a new {@link JdkSerializationSerializer} using the default class loader.
     */
    public JdkSerializationSerializer() {
        this(ClassUtils.getDefaultClassLoader());
    }

    /**
     * Creates a new {@link JdkSerializationSerializer} using a {@link ClassLoader}.
     *
     * @param classLoader the {@link ClassLoader} to use for deserialization. Can be {@literal null}.
     * @since 1.7
     */
    public JdkSerializationSerializer(ClassLoader classLoader) {
        Assertions.notNull(classLoader, "classLoader not null");
        this.classLoader = classLoader;
    }

    @Override
    public byte[] serialize(Object source) throws SerializationException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
        try  {
            serialize(source, byteStream);
            return byteStream.toByteArray();
        } catch (Throwable ex) {
            throw new SerializationException("Failed to serialize object using " +
                    this.getClass().getSimpleName(), ex);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        try {
            return deserialize(byteStream);
        }
        catch (Throwable ex) {
            throw new SerializationException("Failed to deserialize payload. " +
                    "Is the byte array a result of corresponding serialization for " +
                    this.getClass().getSimpleName() + "?", ex);
        }
    }

    //==============
    /**
     * Writes the source object to an output stream using Java serialization.
     * The source object must implement {@link Serializable}.
     * @see ObjectOutputStream#writeObject(Object)
     */
    public void serialize(Object object, OutputStream outputStream) throws IOException {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " +
                    "but received an object of type [" + object.getClass().getName() + "]");
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    public Object deserialize(InputStream inputStream) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            return objectInputStream.readObject();
        } catch (ClassNotFoundException ex) {
            throw new NestedIOException("Failed to deserialize object type", ex);
        }
    }

}
