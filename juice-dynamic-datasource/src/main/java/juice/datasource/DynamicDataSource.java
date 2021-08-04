package juice.datasource;

import juice.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * 动态数据源
 * @author Ricky Fung
 */
public class DynamicDataSource extends AbstractDataSource implements InitializingBean {
    /**
     * Specify the map of target DataSources
     */
    private Map<String, DataSource> targetDataSources;

    /**
     * Specify the default target DataSource
     */
    private DataSource defaultTargetDataSource;

    private boolean lenientFallback = true;

    public void setTargetDataSources(Map<String, DataSource> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    public void setDefaultTargetDataSource(DataSource defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    public void clear() {
        DynamicDSContextHolder.clear();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.targetDataSources == null) {
            throw new IllegalArgumentException("Property 'targetDataSources' is required");
        }
    }

    public Map<String, DataSource> getTargetDataSources() {
        return targetDataSources;
    }

    //=========

    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.targetDataSources, "targetDataSources is NULL");

        String lookupKey = DynamicDSContextHolder.get();
        DataSource dataSource = this.targetDataSources.get(lookupKey);

        if (dataSource == null && (this.lenientFallback || StringUtils.isEmpty(lookupKey))) {
            dataSource = this.defaultTargetDataSource;
        }

        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }
}