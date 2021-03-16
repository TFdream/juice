package juice.threadpool.constants;

import juice.threadpool.constants.enums.RejectedExecutionType;

/**
 * @author Ricky Fung
 */
public abstract class Constant {

    public static final String POOL_NAME_KEY = "name";

    public static final String CORE_POOL_SIZE_KEY = "coreSize";
    public static final String MAXIMUM_POOL_SIZE_KEY = "maximumSize";
    public static final String KEEP_ALIVE_TIME_KEY = "keepAliveTime";
    public static final String QUEUE_TYPE_KEY = "queueType";
    public static final String QUEUE_CAPACITY_KEY = "queueCapacity";


    public static final String REJECTED_TYPE_KEY = "rejectedType";

    //============

    public static final String DEFAULT_POOL_NAME = "dynamic";

    public static final String DEFAULT_QUEUE_TYPE = "resizable";

    public static final int DEFAULT_QUEUE_CAPACITY = 64;

    public static final String DEFAULT_REJECTED_TYPE = RejectedExecutionType.ABORT.name();

}
