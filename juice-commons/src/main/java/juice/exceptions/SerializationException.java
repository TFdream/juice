package juice.exceptions;

/**
 * @author Ricky Fung
 */
public class SerializationException extends NestedRuntimeException {

    /**
     * Constructs a new {@link SerializationException} instance.
     *
     * @param msg
     */
    public SerializationException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new {@link SerializationException} instance.
     *
     * @param msg the detail message.
     * @param cause the nested exception.
     */
    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
