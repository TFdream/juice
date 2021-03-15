package juice.amazonaws.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import juice.amazonaws.constants.AmazonS3Constant;
import juice.util.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author Ricky Fung
 */
public class AmazonS3Builder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String protocol;

    public AmazonS3Builder() {
        this.protocol = AmazonS3Constant.PROTOCOL_HTTP;
    }

    public AmazonS3Builder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public AmazonS3Builder accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public AmazonS3Builder secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public AmazonS3Builder protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    //------
    public AmazonS3 build() {
        if (StringUtils.isAnyEmpty(endpoint, accessKey, secretKey)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        return buildAmazonS3();
    }

    private AmazonS3 buildAmazonS3() {
        logger.info("初始化AmazonS3, endpoint:{}, accessKey:{}, secretKey:{}", endpoint, accessKey, secretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration()
                .withProtocol("HTTPS".equalsIgnoreCase(protocol)? Protocol.HTTPS : Protocol.HTTP);
        clientConfiguration.getApacheHttpClientConfig().withSslSocketFactory(getSslFactory());

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, Regions.CN_NORTH_1.getName()))
                //协议类型
                .withClientConfiguration(clientConfiguration)
                .withPathStyleAccessEnabled(true)
                .build();
        return amazonS3;
    }

    private SSLConnectionSocketFactory getSslFactory() {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });

            return new SSLConnectionSocketFactory(sslContextBuilder.build()
                    , new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}
                    , null
                    , NoopHostnameVerifier.INSTANCE);

        } catch (Exception ex) {
            logger.error("SSL支持异常:", ex);
            throw new RuntimeException(ex);
        }
    }
}
