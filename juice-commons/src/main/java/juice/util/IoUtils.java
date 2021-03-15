package juice.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Ricky Fung
 */
public abstract class IoUtils {
    /**
     * Represents the end-of-file (or stream).
     * @since 2.5 (made public)
     */
    public static final int EOF = -1;

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;


    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    //==========
    public static void close(InputStream input) throws IOException {
        close((Closeable)input);
    }

    public static void close(OutputStream output) throws IOException {
        close((Closeable)output);
    }

    //-------
    public static void closeQuietly(InputStream input){
        closeQuietly((Closeable)input);
    }

    public static void closeQuietly(OutputStream output){
        closeQuietly((Closeable)output);
    }

    //-------
    public static void closeQuietly(Closeable closeable) {
        try {
            if(closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            //ignore
        }
    }

    public static void close(Closeable closeable) throws IOException {
        if(closeable != null) {
            closeable.close();
        }
    }

    public static void closeQuietly(Closeable... closeables){
        for (Closeable closeable : closeables){
            closeQuietly(closeable);
        }
    }
}
