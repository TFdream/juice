package juice.okhttp.spring;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class OkHttpClientFactoryBean implements FactoryBean<OkHttpClient>, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long DEFAULT_TIMEOUT = 1000;

    private Long connectTimeout;
    private Long writeTimeout;
    private Long readTimeout;

    /** http proxy**/
    private HttpProxy proxy;

    private OkHttpClient okHttpClient;

    @Override
    public OkHttpClient getObject() throws Exception {
        //参数校验
        if (connectTimeout == null) {
            connectTimeout = DEFAULT_TIMEOUT;
        }
        if (writeTimeout == null) {
            writeTimeout = DEFAULT_TIMEOUT;
        }
        if (readTimeout == null) {
            readTimeout = DEFAULT_TIMEOUT;
        }

        logger.info("okhttp init connectTimeout:{} writeTimeout:{} readTimeout:{}",
                connectTimeout, writeTimeout, readTimeout);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS);

        if (proxy!=null) {
            logger.info("okhttp add proxy hostname:{} :port:{}", proxy.getHostname(), proxy.getPort());
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getHostname(), proxy.getPort())));
        }

        this.okHttpClient = builder.build();
        return this.okHttpClient;
    }

    @Override
    public Class<?> getObjectType() {
        return OkHttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        logger.info("okhttp destroy");
        if (this.okHttpClient!=null) {
            this.okHttpClient.dispatcher().cancelAll();
            this.okHttpClient.connectionPool().evictAll();
        }
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setWriteTimeout(Long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public void setReadTimeout(Long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setProxy(HttpProxy proxy) {
        this.proxy = proxy;
    }

}