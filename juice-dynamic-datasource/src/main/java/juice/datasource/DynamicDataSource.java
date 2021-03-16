package juice.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Collections;
import java.util.Map;

/**
 * 动态数据源
 * @author Ricky Fung
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> DATASOURCE_KEY_HOLDER = new ThreadLocal<>();

    //key: package name value:数据源名称
    private Map<String, String> pkgDefaultDsKeyMap = Collections.EMPTY_MAP;

    public void setPkgDefaultDsKeyMap(Map<String, String> pkgDefaultDsKeyMap) {
        this.pkgDefaultDsKeyMap = pkgDefaultDsKeyMap;
    }

    public String getPkgDefaultDsKey(String pkgName) {
        return this.pkgDefaultDsKeyMap.get(pkgName);
    }

    public void setDataSourceKey(String dataSourceKey) {
        DATASOURCE_KEY_HOLDER.set(dataSourceKey);
    }

    public String getDataSourceKey() {
        return DATASOURCE_KEY_HOLDER.get();
    }

    public void clear() {
        DATASOURCE_KEY_HOLDER.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSourceKey();
    }
}
