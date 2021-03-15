package juice.exceptions;

/**
 * @author Ricky Fung
 */
public class InvalidTokenException extends NestedRuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
