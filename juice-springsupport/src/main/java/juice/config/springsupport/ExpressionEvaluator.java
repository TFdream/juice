package juice.config.springsupport;

import juice.config.springsupport.lock.LockInvokeContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class ExpressionEvaluator {
    private static ExpressionParser parser = new SpelExpressionParser();
    private static ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final Expression expression;
    private String[] parameterNames;

    public ExpressionEvaluator(String script, Method targetMethod) {
        expression = parser.parseExpression(script);
        if (targetMethod.getParameterCount() > 0) {
            parameterNames = parameterNameDiscoverer.getParameterNames(targetMethod);
        }
    }

    public Object apply(LockInvokeContext root) {
        EvaluationContext context = new StandardEvaluationContext(root);
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], root.getArguments()[i]);
            }
        }
        return expression.getValue(context);
    }
}
