package juice.datasource;

import juice.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class DynamicDSContextHolder {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDSContextHolder.class);

    /**
     * 为了支持嵌套切换，如ABC三个service都是不同的数据源
     * 其中A的某个业务要调B的方法，B的方法需要调用C的方法。一级一级调用切换，形成了链。
     * 传统的只设置当前线程的方式不能满足此业务需求，必须使用栈，后进先出。
     */
    private static ThreadLocal<Stack<String>> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(() -> new Stack<>());

    public static String get() {
        Stack<String> stack = LOOKUP_KEY_HOLDER.get();
        if (stack == null || stack.isEmpty()) {
            LOG.warn("动态数据源切换-上下文, 当前上下文为空");
            return null;
        }
        return stack.peek();
    }

    /**
     * 入栈
     * @param ds
     * @return
     */
    public static String push(String ds) {
        String dataSourceStr = StringUtils.isNotEmpty(ds) ? ds : StringUtils.EMPTY;
        LOOKUP_KEY_HOLDER.get().push(dataSourceStr);
        return dataSourceStr;
    }

    /**
     * 出栈
     */
    public static void pop() {
        Stack<String> stack = LOOKUP_KEY_HOLDER.get();
        stack.pop();
        if (stack.isEmpty()) {
            LOOKUP_KEY_HOLDER.remove();
        }
    }

    public static void clear() {
        LOOKUP_KEY_HOLDER.remove();
    }
}
