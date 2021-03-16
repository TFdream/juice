package juice.datasource.aop;

import juice.datasource.DynamicDataSource;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourcePointcutAdvisor extends AbstractPointcutAdvisor{
    private static final String[] EMPTY_ARRAY = {};

    private final String[] basePackages;

    private DynamicDataSource dynamicDataSource;

    public DynamicDataSourcePointcutAdvisor(){
        this(EMPTY_ARRAY);
    }

    public DynamicDataSourcePointcutAdvisor(String[] basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public Pointcut getPointcut() {
        return new DynamicDataSourcePointcut(this.basePackages);
    }

    @Override
    public Advice getAdvice() {
        return new DynamicDataSourceInterceptor(dynamicDataSource);
    }

    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    public DynamicDataSource getDynamicDataSource() {
        return dynamicDataSource;
    }

}
