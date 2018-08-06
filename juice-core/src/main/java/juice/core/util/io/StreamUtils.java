package juice.core.util.io;

import juice.core.util.AssertUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Ricky Fung
 */
public abstract class StreamUtils {

    public static final int BUFFER_SIZE = 4096;

    public static int copy(InputStream in, OutputStream out) throws IOException {
        AssertUtils.notNull(in, "No InputStream specified");
        AssertUtils.notNull(out, "No OutputStream specified");
        int byteCount = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            byteCount += bytesRead;
        }
        out.flush();
        return byteCount;
    }


    public static void closeQuietly(InputStream input){
        closeQuietly((Closeable)input);
    }

    public static void closeQuietly(OutputStream output){
        closeQuietly((Closeable)output);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if(closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeQuietly(Closeable... closeables){
        for (Closeable closeable : closeables){
            closeQuietly(closeable);
        }
    }

}
