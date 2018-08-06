package juice.core.util.io;

import juice.core.util.AssertUtils;

import java.io.*;

/**
 * @author Ricky Fung
 */
public abstract class FileUtils {

    /**
     * 递归删除文件
     * @param root
     * @return
     */
    public static boolean deleteRecursively(File root) {
        if (root != null && root.exists()) {
            if (root.isDirectory()) {
                File[] children = root.listFiles();
                if (children != null) {
                    for (File child : children) {
                        deleteRecursively(child);
                    }
                }
            }
            return root.delete();
        }
        return false;
    }

    /**
     * 拷贝文件
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyRecursively(File src, File dest) throws IOException {
        AssertUtils.isTrue(src != null && (src.isDirectory() || src.isFile()), "Source File must denote a directory or file");
        AssertUtils.notNull(dest, "Destination File must not be null");
        doCopyRecursively(src, dest);
    }

    private static void doCopyRecursively(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            dest.mkdir();
            File[] entries = src.listFiles();
            if (entries == null) {
                throw new IOException("Could not list files in directory: " + src);
            }
            for (File entry : entries) {
                doCopyRecursively(entry, new File(dest, entry.getName()));
            }
        }
        else if (src.isFile()) {
            try {
                dest.createNewFile();
            } catch (IOException ex) {
                IOException ioex = new IOException("Failed to create file: " + dest);
                ioex.initCause(ex);
                throw ioex;
            }
            copy(src, dest);
        }
    }

    public static int copy(File in, File out) throws IOException {
        AssertUtils.notNull(in, "No input File specified");
        AssertUtils.notNull(out, "No output File specified");
        return copy(new BufferedInputStream(new FileInputStream(in)),
                new BufferedOutputStream(new FileOutputStream(out)));
    }

    public static int copy(InputStream in, OutputStream out) throws IOException {
        AssertUtils.notNull(in, "No InputStream specified");
        AssertUtils.notNull(out, "No OutputStream specified");
        try {
            return StreamUtils.copy(in, out);
        } finally {
            StreamUtils.closeQuietly(in);
            StreamUtils.closeQuietly(out);
        }
    }

    /**
     * Copy the contents of the given Reader to the given Writer.
     * Closes both when done.
     * @param in the Reader to copy from
     * @param out the Writer to copy to
     * @return the number of characters copied
     * @throws IOException in case of I/O errors
     */
    public static int copy(Reader in, Writer out) throws IOException {
        AssertUtils.notNull(in, "No Reader specified");
        AssertUtils.notNull(out, "No Writer specified");

        try {
            int byteCount = 0;
            char[] buffer = new char[StreamUtils.BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        }
        finally {
            StreamUtils.closeQuietly(in);
            StreamUtils.closeQuietly(out);
        }
    }

    /**
     * Copy the contents of the given input File into a new byte array.
     * @param in the file to copy from
     * @return the new byte array that has been copied to
     * @throws IOException in case of I/O errors
     */
    public static byte[] copyToByteArray(File in) throws IOException {
        AssertUtils.notNull(in, "No input File specified");

        return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
    }

    /**
     * Copy the contents of the given InputStream into a new byte array.
     * Closes the stream when done.
     * @param in the stream to copy from (may be {@code null} or empty)
     * @return the new byte array that has been copied to (possibly empty)
     * @throws IOException in case of I/O errors
     */
    public static byte[] copyToByteArray(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(StreamUtils.BUFFER_SIZE);
        copy(in, out);
        return out.toByteArray();
    }
}
