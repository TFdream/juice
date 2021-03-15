package juice.ftp.util;

import juice.util.CollectionUtils;
import juice.util.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ricky Fung
 */
public abstract class FtpUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FtpUtils.class);

    public static final String ROOT_PATH = "/";
    public static final String PATH_SEPARATOR = ROOT_PATH;

    private static final Map<String, Boolean> existsPathCache = new ConcurrentHashMap<>(512);

    /**
     * 创建多级目录
     * @param ftpClient
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean makeDirectories(FTPClient ftpClient, String path) throws IOException {
        List<String> pathList = getHierarchyPaths(path);
        if (CollectionUtils.isEmpty(pathList)) {
            return false;
        }
        //例如 [abc, abc/2020, abc/2020/08]
        boolean success = true;
        for (String pathVal : pathList) {
            if (!makeDirectory(ftpClient, pathVal)) {
                LOG.error("创建path:{} 失败", pathVal);
                success = false;
                break;
            }
        }
        return success;
    }

    /**
     * 创建单级目录
     * @param ftpClient
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean makeDirectory(FTPClient ftpClient, String path) throws IOException {
        if (existPath(ftpClient, path)) {
            return true;
        }
        String parentPath = getParentPath(path);
        boolean success = ftpClient.changeWorkingDirectory(parentPath);
        if (success) {
            String directory = getEndingPath(path);
            return ftpClient.makeDirectory(directory);
        }
        return false;
    }

    //=========

    public static boolean deleteFile(FTPClient ftpClient, String path, String filename) throws IOException {
        //切换FTP目录
        ftpClient.changeWorkingDirectory(path);
        ftpClient.dele(filename);
        return true;
    }

    //=========
    //判断ftp服务器文件是否存在
    public static boolean existPath(FTPClient ftpClient, String path) throws IOException {
        Boolean exists = existsPathCache.get(path);
        if (exists != null) {
            return true;
        }
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr != null && ftpFileArr.length > 0) {
            flag = true;
            existsPathCache.put(path, Boolean.TRUE);
        }
        return flag;
    }

    //==========

    /**
     * 根据path推断出所有父级
     * 示例：
     * / => [/]
     * /static/images/2020/08 => [/, /static, /static/images, /static/images/2020, /static/images/2020/08]
     * @param path
     * @return
     */
    public static List<String> getHierarchyPaths(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path为空");
        }

        //path格式化
        String formatPath = getFormatPath(path);
        if (ROOT_PATH.equals(formatPath)) {
            return Arrays.asList(ROOT_PATH);
        }

        String[] arr = formatPath.split("/");
        List<String> list = new ArrayList<>(arr.length);
        for (int i=0; i<arr.length; i++) {
            if(i != 0) {
                list.add(appendParentPath(arr, 0, i));
            } else {
                list.add(ROOT_PATH);
            }
        }
        return list;
    }

    /**
     * 获取父级路径
     * / => /
     * /abc => /
     * /abc/2020 => /abc
     * @param path
     * @return
     */
    public static String getParentPath(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path为空");
        }
        String formatPath = getFormatPath(path);
        if (formatPath.equals(ROOT_PATH)) {
            return ROOT_PATH;
        }
        int index = formatPath.lastIndexOf(PATH_SEPARATOR);
        if (index > 0) {
            return formatPath.substring(0, index);
        }
        return ROOT_PATH;
    }

    /**
     * 获取最后一级目录
     * / => /
     * /abc => abc
     * /abc/2020 => 2020
     * @param path
     * @return
     */
    public static String getEndingPath(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path为空");
        }
        String formatPath = getFormatPath(path);
        if (formatPath.equals(ROOT_PATH)) {
            return ROOT_PATH;
        }

        int index = formatPath.lastIndexOf(PATH_SEPARATOR);
        if (index >= 0) {
            return formatPath.substring(index+1);
        }
        return path;
    }

    /**
     * 返回标准path
     * / => /
     * abc => /abc
     * /abc/2020 => /abc/2020
     * /abc/2020/ => /abc/2020
     * @param path
     * @return
     */
    public static String getFormatPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return path;
        }
        if (path.equals(ROOT_PATH)) {
            return path;
        }
        if (!path.startsWith(ROOT_PATH)) {
            StringBuilder sb = new StringBuilder(path.length()+1);
            sb.append(ROOT_PATH).append(path);
            path = sb.toString();
        }
        if (path.endsWith(PATH_SEPARATOR)) {    //结尾
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    public static boolean isValidPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (!path.startsWith(ROOT_PATH)) {
            return false;
        }
        return true;
    }

    //========

    private static String appendParentPath(String[] arr, int start, int end) {
        StringBuilder sb = new StringBuilder(120);
        for (int i=start; i<=end; i++) {
            sb.append(arr[i]).append(PATH_SEPARATOR);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
