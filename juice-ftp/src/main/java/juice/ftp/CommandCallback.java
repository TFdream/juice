package juice.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
@FunctionalInterface
public interface CommandCallback<T> {

    T execute(FTPClient ftpClient) throws IOException;
}
