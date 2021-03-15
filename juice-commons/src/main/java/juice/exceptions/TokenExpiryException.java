package juice.exceptions;

/**
 * @author Ricky Fung
 */
public class TokenExpiryException extends Exception {
    public TokenExpiryException() {
    }

    public TokenExpiryException(String message) {
        super(message);
    }

    public TokenExpiryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpiryException(Throwable cause) {
        super(cause);
    }
}
