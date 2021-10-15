package juice.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Ricky Fung
 */
public class FileUtils {

    private static final long _KB = 1 << 10;

    private static final long _MB = 1 << 20;

    private static final long _GB = 1 << 30;

    private FileUtils() {}
    
    public static String formatSize(long size) {
        if (size < _KB) {
            return String.format("%sB", size);
        }
        if (size < _MB) {
            return String.format("%sKB", DecimalUtils.div(size, _KB, 2));
        }
        if (size < _GB) {
            return String.format("%sMB", DecimalUtils.div(size, _MB, 2));
        }
        return String.format("%sGB", DecimalUtils.div(size, _GB, 2));
    }

    public static String getFormatFileSize(File file) {
        long size = getFileSize(file);
        return formatSize(size);
    }

    public static long getFileSize(File file) {
        if (file == null) {
            return 0;
        }
        if (!file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            return file.length();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            long total = 0;
            for (File f : files) {
                total += getFileSize(f);
            }
            return total;
        }
        throw new IllegalStateException("未知文件类型:"+file.getAbsolutePath());
    }

    //==========

    /**
     * 删除单个文件
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir
     * @return
     */
    public static boolean deleteDirectory(File dir) {
        if (dir == null || !dir.exists()) {
            return true;
        }
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            //递归删除目录中的子目录下
            for (File file : children) {
                boolean success = deleteDirectory(file);
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    //=============

    /**
     * 压缩文件
     * @param sourceFile
     * @param zipFile
     * @throws IOException
     */
    public static void zipFile(File sourceFile, File zipFile) throws IOException {
        zipFile(sourceFile, zipFile, StandardCharsets.UTF_8, true);
    }

    public static void zipFile(File sourceFile, File zipFile, Charset charset) throws IOException {
        zipFile(sourceFile, zipFile, charset, true);
    }

    public static void zipFile(File sourceFile, File zipFile, Charset charset, boolean keepDirStructure) throws IOException {
        String rootName = sourceFile.getName();

        FileOutputStream out = new FileOutputStream(zipFile);
        ZipOutputStream zos = null;
        try {
            if (charset != null) {
                zos = new ZipOutputStream(out, charset);
            } else {
                zos = new ZipOutputStream(out);
            }
            if (sourceFile.isFile()) {    //文件
                compress(sourceFile, sourceFile.getName(), zos);
            } else {    //目录
                File[] files = sourceFile.listFiles();
                if(files == null || files.length == 0){
                    if(keepDirStructure) {
                        // 空文件夹的处理
                        zos.putNextEntry(new ZipEntry(rootName + File.separator));
                        zos.closeEntry();
                    }
                } else {
                    for (File file : files) {
                        // 判断是否需要保留原来的文件结构
                        if (keepDirStructure) {
                            // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                            // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                            compress(file, rootName + File.separator + file.getName(), zos);
                        } else {
                            compress(file, file.getName(), zos);
                        }
                    }
                }
            }
        } finally {
            IoUtils.close(zos);
        }
    }

    public static void unzipFile(File zipFile, File dir) throws IOException {
        unzipFile(zipFile, dir, StandardCharsets.UTF_8);
    }

    /**
     * 解压缩zip文件
     * @param zipFile
     * @param dir
     */
    public static void unzipFile(File zipFile, File dir, Charset charset) throws IOException {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        ZipFile zip;
        if (charset != null) {
            zip = new ZipFile(zipFile, charset);
        } else {
            zip = new ZipFile(zipFile);
        }
        for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();

            File outFile = new File(dir, entry.getName());
            if(!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }

            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if(entry.isDirectory()) {
                outFile.mkdirs();
                continue;
            }

            InputStream input = zip.getInputStream(entry);
            OutputStream output = new FileOutputStream(outFile);
            IoUtils.copy(input, output);
            input.close();
            output.close();
        }
    }


    private static void compress(File file, String filename, ZipOutputStream zos) throws IOException {
        zos.putNextEntry(new ZipEntry(filename));
        // copy文件到zip输出流中
        FileInputStream in = new FileInputStream(file);
        IoUtils.copy(in, zos);
        // Complete the entry
        zos.closeEntry();
        in.close();
    }

    //==========

    public static String getFileSuffix(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return filename;
        }
        int index = filename.lastIndexOf(".");
        if (index > 0) {
            return filename.substring(index);
        }
        return null;
    }

}
