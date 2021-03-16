package juice.threadpool.constants.enums;

import juice.threadpool.queue.ResizableCapacityLinkedBlockingQueue;
import java.util.concurrent.*;

/**
 * @author Ricky Fung
 */
public enum QueueType {
    ARRAY_BLOCKING_QUEUE("array", ArrayBlockingQueue.class),
    LINKED_BLOCKING_QUEUE("linked", LinkedBlockingQueue.class),
    SYNCHRONOUS_QUEUE("synchronous", SynchronousQueue.class),
    PRIORITY_BLOCKING_QUEUE("priority", PriorityBlockingQueue.class),
    DELAY_QUEUE("delay", DelayQueue.class),
    LINKED_TRANSFER_QUEUE("linked_transfer", LinkedTransferQueue.class),
    RESIZABLE_CAPACITY_BLOCKING_QUEUE("resizable", ResizableCapacityLinkedBlockingQueue.class),
    ;
    private String type;
    private Class<? extends BlockingQueue> queueOfType;

    QueueType(String type, Class<? extends BlockingQueue> queueOfType) {
        this.type = type;
        this.queueOfType = queueOfType;
    }

    public static QueueType of(String queueType) {
        for (QueueType qt : QueueType.values()) {
            if (qt.getType().equals(queueType)) {
                return qt;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public Class getQueueOfType() {
        return queueOfType;
    }
}
