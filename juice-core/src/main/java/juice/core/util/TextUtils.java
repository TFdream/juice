package juice.core.util;

import juice.core.util.io.StreamUtils;

import java.io.*;

/**
 * @author Ricky Fung
 */
public class TextUtils {
    private static final String UTF_8 = "UTF-8";
    private static final int DEFAULT_CAPACITY = 2048;

    /** read class path .txt file**/
    public static String readClassPath(String fullName) throws IOException {
        return readClassPath(fullName, UTF_8, DEFAULT_CAPACITY);
    }
    public static String readClassPath(String fullName, int capacity) throws IOException {
        return readClassPath(fullName, UTF_8, capacity);
    }
    public static String readClassPath(String fullName, String charset, int capacity) throws IOException {
        InputStream in = ClassUtils.getResourceAsStream(fullName);
        return read(in, charset, capacity);
    }

    public static String read(String filename) throws IOException {
        return read(filename, UTF_8, DEFAULT_CAPACITY);
    }
    public static String read(String filename, int capacity) throws IOException {
        return read(filename, UTF_8, capacity);
    }
    public static String read(String filename, String charset, int capacity) throws IOException {
        return read(new File(filename), charset, capacity);
    }

    public static String read(File file) throws IOException {
        return read(new FileInputStream(file), UTF_8, DEFAULT_CAPACITY);
    }
    public static String read(File file, String charset) throws IOException {
        return read(new FileInputStream(file), charset, DEFAULT_CAPACITY);
    }
    public static String read(File file, String charset, int capacity) throws IOException {
        return read(new FileInputStream(file), charset, capacity);
    }

    public static String read(InputStream in, String charset, int capacity) throws IOException {
        StringBuilder sb = new StringBuilder(capacity);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } finally {
            StreamUtils.closeQuietly(br);
        }
        return sb.toString();
    }
}
