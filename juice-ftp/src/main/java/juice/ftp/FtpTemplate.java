package juice.ftp;

import juice.ftp.constants.FtpConstant;
import juice.ftp.util.FtpUtils;
import juice.util.CollectionUtils;
import juice.util.IoUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class FtpTemplate {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String hostname;
    private int port;
    private String username;
    private String password;
    private int connectTimeout;
    private int socketTimeout;

    public FtpTemplate(String hostname, int port, String username, String password) {
        this(hostname, port, username, password, 5000, 60 * 1000);
    }

    public FtpTemplate(String hostname, int port, String username, String password,
                       int connectTimeout, int socketTimeout) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }

    //============

    public File getFile(String rootPath, final String filename, File dir) throws FtpAccessException {
        return executeCommand((ftpClient)->{

            //将客户端设置为被动模式
            ftpClient.enterLocalPassiveMode();
            //切换目录
            boolean success = ftpClient.changeWorkingDirectory(rootPath);

            if (success) {
                File file = new File(dir, filename);
                OutputStream out = new FileOutputStream(file);
                ftpClient.retrieveFile(filename, out);

                return file;
            }
            return null;
        });
    }

    public OutputStream getFile(String rootPath, final String filename) throws FtpAccessException {
        return executeCommand((ftpClient)->{

            //将客户端设置为被动模式
            ftpClient.enterLocalPassiveMode();
            //切换目录
            boolean success = ftpClient.changeWorkingDirectory(rootPath);

            if (success) {
                OutputStream out = new ByteArrayOutputStream(1024 * 16);
                ftpClient.retrieveFile(filename, out);

                return out;
            }
            return null;
        });
    }

    //=========
    public int uploadFiles(String rootPath, List<FileItem> files) throws FtpAccessException {
        if (CollectionUtils.isEmpty(files)) {
            return 0;
        }

        return executeCommand((ftpClient)-> {

            int total = files.size();
            LOG.info("FTP文件服务-批量上传文件开始, rootPath:{}, 文件数量:{}", rootPath, total);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            if (!FtpUtils.existPath(ftpClient, rootPath)) {
                //创建目录
                boolean success = FtpUtils.makeDirectories(ftpClient, rootPath);
                if (!success) {
                    LOG.info("FTP文件服务-批量上传文件, rootPath:{} 目录创建失败", rootPath);
                    return 0;
                }
            }

            //切换目录
            boolean success = ftpClient.changeWorkingDirectory(rootPath);

            int successCnt = 0;
            for (int i=0; i<total; i++) {
                FileItem item = files.get(i);
                String filename = item.getFilename();
                InputStream in = new FileInputStream(item.getFile());
                ftpClient.storeFile(filename, in);
                IoUtils.closeQuietly(in);

                successCnt++;

                LOG.info("FTP文件服务-批量上传文件, rootPath:{}, filename:{} 上传完成 进度条:[{}/{}/{}]",
                        rootPath, filename, (i+1), successCnt, total);
            }
            return successCnt;
        });
    }

    //=========
    public void uploadFile(String rootPath, String filename, File file) throws FtpAccessException {
        try {
            createFile(rootPath, filename, new FileInputStream(file), true);
        } catch (FileNotFoundException e) {
            throw new FtpAccessException("上传文件不存在", e);
        }
    }

    public void uploadFile(String rootPath, String filename, InputStream in) throws FtpAccessException {
        createFile(rootPath, filename, in, false);
    }

    /**
     * 在指定路径下 创建文件
     * 如果文件已经存在则直接覆盖
     * @param rootPath
     * @param filename
     * @param in 不会关闭流
     * @return
     */
    public boolean createFile(String rootPath, String filename, InputStream in, boolean closeStream) throws FtpAccessException {

        return executeCommand((ftpClient)->{
            LOG.info("FTP文件服务-上传文件开始, rootPath:{}, filename:{}", rootPath, filename);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            if (!FtpUtils.existPath(ftpClient, rootPath)) {
                //创建目录
                boolean success = FtpUtils.makeDirectories(ftpClient, rootPath);
                if (!success) {
                    LOG.info("FTP文件服务-上传文件, rootPath:{}, filename:{} 目录创建失败", rootPath, filename);
                    return false;
                }
            }

            //切换目录
            boolean success = ftpClient.changeWorkingDirectory(rootPath);

            ftpClient.storeFile(filename, in);

            LOG.info("FTP文件服务-上传文件结束, rootPath:{}, filename:{}, 切换工作目录结果:{}", rootPath, filename, success);

            if (closeStream) {
                in.close();
            }
            return true;
        });
    }

    //===========
    public <T> T executeCommand(CommandCallback<T> action) throws FtpAccessException {
        FTPClient ftpClient = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        config.setDefaultDateFormatStr(FtpConstant.DATE_FORMAT_PATTERN);
        config.setRecentDateFormatStr(FtpConstant.DATE_FORMAT_PATTERN);
        /**设置文件传输的编码*/
        ftpClient.setControlEncoding(FtpConstant.UTF_8);
        // for example config.setServerTimeZoneId("Pacific/Pitcairn")
        ftpClient.setConnectTimeout(connectTimeout);
        ftpClient.setDefaultTimeout(socketTimeout);
        ftpClient.configure(config);
        try {
            LOG.info("FTP服务客户端-初始化开始, FTP Server [{}:{}] connectTimeout:{}, socketTimeout:{}",
                    hostname, port, connectTimeout, socketTimeout);

            ftpClient.connect(hostname, port);
            LOG.info("FTP服务客户端-建立连接, FTP Server [{}:{}] reply:{}", hostname, port, ftpClient.getReplyString());

            // After connection attempt, you should check the reply code to verify success.
            int reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new FtpAccessException("FTP server refused connection.");
            }

            ftpClient.login(username, password);
            LOG.info("FTP服务客户端-登录认证, FTP Server [{}:{}] reply:{}", hostname, port, ftpClient.getReplyString());

            reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new FtpAccessException("FTP server Loin error.");
            }
            // transfer files
            T result = action.execute(ftpClient);

            ftpClient.logout();

            return result;
        } catch(Exception e) {
            LOG.error(String.format("FTP服务客户端-访问FTP服务器 [%s:%s]异常", hostname, port), e);
            throw new FtpAccessException("访问FTP服务器异常", e);
        } finally {
            if(ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch(IOException ioe) {
                    // do nothing
                    LOG.error(String.format("FTP服务客户端-访问FTP服务器 [%s:%s] 断开链接异常", hostname, port), ioe);
                }
            }
        }
    }

}
