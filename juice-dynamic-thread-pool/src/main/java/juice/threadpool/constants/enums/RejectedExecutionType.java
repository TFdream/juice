package juice.threadpool.constants.enums;

/**
 * @author Ricky Fung
 */
public enum RejectedExecutionType {
    CALLER_RUNS,
    ABORT,
    DISCARD,
    DISCARD_OLDEST,
    ;

    public static RejectedExecutionType of(String type) {
        for (RejectedExecutionType ret : RejectedExecutionType.values()) {
            if (ret.name().equalsIgnoreCase(type)) {
                return ret;
            }
        }
        return null;
    }
}
