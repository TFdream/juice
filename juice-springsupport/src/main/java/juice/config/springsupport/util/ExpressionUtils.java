package juice.config.springsupport.util;

import juice.config.springsupport.ExpressionEvaluator;
import juice.config.springsupport.lock.LockInvokeContext;

/**
 * @author Ricky Fung
 */
public abstract class ExpressionUtils {

    public static Object eval(String condition, LockInvokeContext context) {
        ExpressionEvaluator e = new ExpressionEvaluator(condition, context.getMethod());
        return e.apply(context);
    }

}
