package juice.ftp;

/**
 * @author Ricky Fung
 */
public class FtpAccessException extends RuntimeException {
    public FtpAccessException() {
    }

    public FtpAccessException(String message) {
        super(message);
    }

    public FtpAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
