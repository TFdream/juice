package juice.exceptions;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class NestedIOException extends IOException {

    public NestedIOException(String message) {
        super(message);
    }

    public NestedIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
