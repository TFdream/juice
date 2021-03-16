package juice.threadpool;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import juice.concurrent.DefaultThreadFactory;
import juice.threadpool.constants.Constant;
import juice.threadpool.constants.enums.QueueType;
import juice.threadpool.constants.enums.RejectedExecutionType;
import juice.threadpool.queue.ResizableCapacityLinkedBlockingQueue;
import juice.threadpool.util.DurationStyle;
import juice.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.*;

/**
 * 动态线程池: https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
 *
 * @author Ricky Fung
 */
public class DynamicThreadPoolExecutor extends AbstractExecutorService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置中心namespace
     */
    private final String namespace;
    /**
     * 配置中心key前缀
     */
    private final String keyPrefix;

    //=======
    /**
     * 线程池名称
     */
    private String name;

    private ThreadPoolExecutor delegate;

    //Apollo Config
    private Config config;

    public DynamicThreadPoolExecutor(String namespace, String keyPrefix) {
        if (StringUtils.isEmpty(namespace)) {
            throw new IllegalArgumentException("namespace为空");
        }
        if (StringUtils.isEmpty(keyPrefix)) {
            throw new IllegalArgumentException("keyPrefix为空");
        }
        this.namespace = namespace;
        this.keyPrefix = keyPrefix;
        //执行初始化逻辑
        initialize();
    }

    //==========

    /**
     * 初始化
     */
    private void initialize() {
        //初始化Apollo配置监听
        this.config = ConfigService.getConfig(namespace);
        this.config.addChangeListener((event) -> onConfigChange(event));

        //构造线程池
        this.delegate = createThreadPoolExecutor();
    }

    private void onConfigChange(ConfigChangeEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("动态线程池-配置发生变化, namespace={}", event.getNamespace());
        }
        if (!event.getNamespace().equals(namespace)) {
            return;
        }
        for (String key : event.changedKeys()) {
            if (key.startsWith(keyPrefix)) {
                ConfigChange change = event.getChange(key);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("动态线程池-配置发生变化, namespace={}, key={} 变化内容={}", event.getNamespace(), key, change);
                }
                refreshThreadPoolExecutor(this.delegate);
                break;
            }
        }
    }

    private void refreshThreadPoolExecutor(final ThreadPoolExecutor threadPoolExecutor) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("动态线程池-配置发生变化-刷新线程池开始, name={}, namespace={}, keyPrefix={}", name, namespace, keyPrefix);
        }

        threadPoolExecutor.setCorePoolSize(getConfigCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(getConfigMaximumPoolSize());
        threadPoolExecutor.setKeepAliveTime(getConfigKeepAliveTime(), TimeUnit.MILLISECONDS);
        threadPoolExecutor.setRejectedExecutionHandler(getRejectedExecutionHandler(getRejectedExecutionType()));
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        if (queue instanceof ResizableCapacityLinkedBlockingQueue) {
            ((ResizableCapacityLinkedBlockingQueue<Runnable>) queue).setCapacity(getConfigQueueCapacity());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("动态线程池-配置发生变化-刷新线程池结束, name={}, namespace={}, keyPrefix={}", name, namespace, keyPrefix);
        }
    }

    private ThreadPoolExecutor createThreadPoolExecutor() {
        //线程池名称
        this.name = getConfigPoolName();

        int corePoolSize = getConfigCorePoolSize();
        int maxPoolSize =getConfigMaximumPoolSize();
        long keepAliveTime = getConfigKeepAliveTime();

        QueueType queueType = getConfigQueueType();
        int queueCapacity = getConfigQueueCapacity();

        if (LOG.isDebugEnabled()) {
            LOG.debug("动态线程池-初始化开始, corePoolSize={}, maxPoolSize={}, keepAliveTime={}, queueType={}, queueCapacity={}, poolName={}",
                    corePoolSize, maxPoolSize, keepAliveTime, queueType, queueCapacity, name);
        }

        BlockingQueue<Runnable> workQueue = getWorkQueue(queueType, queueCapacity, false);

        RejectedExecutionHandler rejectedHandler = getRejectedExecutionHandler(getRejectedExecutionType());

        LOG.debug("动态线程池-初始化, corePoolSize={}, maxPoolSize={}, keepAliveTime={}, queueType={}, queueCapacity={}, poolName={}",
                corePoolSize, maxPoolSize, keepAliveTime, queueType, queueCapacity, name);

        return new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS, workQueue,
                new DefaultThreadFactory(name), rejectedHandler);
    }

    private RejectedExecutionHandler getRejectedExecutionHandler(String type) {
        RejectedExecutionType rejectedType = RejectedExecutionType.of(type);
        if (rejectedType == RejectedExecutionType.CALLER_RUNS) {
            return new ThreadPoolExecutor.CallerRunsPolicy();
        }
        if (rejectedType == RejectedExecutionType.DISCARD) {
            return new ThreadPoolExecutor.DiscardPolicy();
        }
        if (rejectedType == RejectedExecutionType.DISCARD_OLDEST) {
            return new ThreadPoolExecutor.DiscardOldestPolicy();
        }
        return new ThreadPoolExecutor.AbortPolicy();
    }

    private BlockingQueue<Runnable> getWorkQueue(QueueType queueType, int queueCapacity, boolean fair) {
        if (queueType == null) {
            throw new IllegalArgumentException("队列类型不存在:" + queueType);
        }
        if (queueType == QueueType.ARRAY_BLOCKING_QUEUE) {
            return new ArrayBlockingQueue<>(queueCapacity);
        }
        if (queueType == QueueType.LINKED_BLOCKING_QUEUE) {
            return new LinkedBlockingQueue<>(queueCapacity);
        }
        if (queueType == QueueType.SYNCHRONOUS_QUEUE) {
            return new SynchronousQueue<>(fair);
        }
        if (queueType == QueueType.PRIORITY_BLOCKING_QUEUE) {
            return new PriorityBlockingQueue<>(queueCapacity);
        }
        if (queueType == QueueType.DELAY_QUEUE) {
            return new DelayQueue();
        }
        if (queueType == QueueType.LINKED_TRANSFER_QUEUE) {
            return new LinkedTransferQueue<>();
        }
        return new ResizableCapacityLinkedBlockingQueue(queueCapacity);
    }

    //============
    private int getConfigCorePoolSize() {
        String key = getConfigKey(Constant.CORE_POOL_SIZE_KEY);
        return config.getIntProperty(key, 0);
    }

    private int getConfigMaximumPoolSize() {
        String key = getConfigKey(Constant.MAXIMUM_POOL_SIZE_KEY);
        return config.getIntProperty(key, 0);
    }

    private long getConfigKeepAliveTime() {
        String key = getConfigKey(Constant.KEEP_ALIVE_TIME_KEY);
        String time = config.getProperty(key, null);

        //默认3分钟
        Duration duration = Duration.ofMinutes(3);
        if (StringUtils.isNotEmpty(time)) {
            duration = DurationStyle.detect(time).parse(time, ChronoUnit.MILLIS);
        }
        long keepAliveTime = duration.toMillis();
        return keepAliveTime;
    }

    private QueueType getConfigQueueType() {
        String key = getConfigKey(Constant.QUEUE_TYPE_KEY);
        String type = config.getProperty(key, Constant.DEFAULT_QUEUE_TYPE);
        return QueueType.of(type);
    }

    private int getConfigQueueCapacity() {
        String key = getConfigKey(Constant.QUEUE_CAPACITY_KEY);
        return config.getIntProperty(key, Constant.DEFAULT_QUEUE_CAPACITY);
    }

    private String getConfigPoolName() {
        String key = getConfigKey(Constant.POOL_NAME_KEY);
        return config.getProperty(key, Constant.DEFAULT_POOL_NAME);
    }

    private String getRejectedExecutionType() {
        String key = getConfigKey(Constant.REJECTED_TYPE_KEY);
        return config.getProperty(key, Constant.DEFAULT_REJECTED_TYPE);
    }

    private String getConfigKey(String key) {
        if (StringUtils.isNotEmpty(keyPrefix)) {
            key = String.format("%s.%s", keyPrefix, key);
        }
        return key;
    }

    //============
    public String getName() {
        return name;
    }

    public int getCorePoolSize() {
        return this.delegate.getCorePoolSize();
    }

    public int getPoolSize() {
        return this.delegate.getPoolSize();
    }

    public int getLargestPoolSize() {
        return this.delegate.getLargestPoolSize();
    }

    public int getMaximumPoolSize() {
        return this.delegate.getMaximumPoolSize();
    }

    public int getWaitTaskCount() {
        return this.delegate.getQueue().size();
    }

    public int getActiveCount() {
        return this.delegate.getActiveCount();
    }

    public long getTaskCount() {
        return this.delegate.getTaskCount();
    }

    public long getCompletedTaskCount() {
        return this.delegate.getCompletedTaskCount();
    }

    //============

    @Override
    public void shutdown() {
        this.delegate.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.delegate.awaitTermination(timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        this.delegate.execute(command);
    }
}
