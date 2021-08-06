package juice.config.springsupport.lock;

import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class LockInvokeContext {
    private Method method;
    private Object[] arguments;
    private Object targetObject;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }
}
