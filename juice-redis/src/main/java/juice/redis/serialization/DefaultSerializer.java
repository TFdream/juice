package juice.redis.serialization;

import juice.core.util.io.StreamUtils;
import java.io.*;

/**
 * jdk serializer
 * @author Ricky Fung
 */
public class DefaultSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ObjectOutputStream output = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            output = new ObjectOutputStream(baos);
            output.writeObject(obj);
            output.flush();

            return baos.toByteArray();
        } finally {
            StreamUtils.closeQuietly(output);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> classOfT) throws IOException {
        // Read Obj from File
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new ByteArrayInputStream(data));
            return (T) input.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("class not found", e);
        } finally {
            input.close();
            StreamUtils.closeQuietly(input);
        }
    }
}
