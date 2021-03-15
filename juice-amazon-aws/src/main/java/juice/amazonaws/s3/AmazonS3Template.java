package juice.amazonaws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import juice.amazonaws.constants.AmazonS3Constant;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * @author Ricky Fung
 */
public class AmazonS3Template {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonS3 amazonS3;

    public AmazonS3Template(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    public S3Object getObject(String bucketName, String key) {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, key));
        return s3Object;
    }

    //-----
    public PutObjectResult putObject(String bucketName, String key, byte[] data, Map<String, String> map) {
        Date expirationTime = DateTime.now().plusYears(5).toDate();
        return putObject(bucketName, key, data, map, expirationTime);
    }

    public PutObjectResult putObject(String bucketName, String key, byte[] data, Map<String, String> map, Date expirationTime) {
        ByteArrayInputStream input = new ByteArrayInputStream(data);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        //自定义元数据
        metadata.setUserMetadata(map);
        metadata.setContentEncoding(AmazonS3Constant.UTF_8);
        //设置过期时间
        metadata.setExpirationTime(expirationTime);
        return amazonS3.putObject(bucketName, key, input, metadata);
    }

    //--------
    public PutObjectResult putObject(String bucketName, String key, String content) {
        return amazonS3.putObject(bucketName, key, content);
    }

    public PutObjectResult putObject(String bucketName, String key, File file) {
        return amazonS3.putObject(bucketName, key, file);
    }

    public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        return amazonS3.putObject(bucketName, key, input, metadata);
    }

    //--------

    /**
     * 遍历对象
     * 例如: /iqj_ops/cube/12343/
     * @param bucketName
     * @param prefix
     * @return
     */
    public List<S3ObjectSummary> listObjects(String bucketName, String prefix) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(prefix)
                .withDelimiter(AmazonS3Constant.PATH_DELIMITER);

        List<S3ObjectSummary> objectSummaries = new ArrayList<>(1000);
        ObjectListing objects = amazonS3.listObjects(listObjectsRequest);
        do{
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                objectSummaries.add(objectSummary);
            }
            objects = amazonS3.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());

        return objectSummaries;
    }

    //--------

    /**
     * 创建bucket
     * @param bucketName
     * @return
     */
    public boolean createBucket(String bucketName) {
        if (isBucketExists(bucketName)) {
            logger.info("S3文件存储-创建bucket, bucket:{} 已经存在", bucketName);
            return true;
        }
        //CREATING A BUCKET
        Bucket bucket = amazonS3.createBucket(bucketName);
        logger.info("S3文件存储-创建bucket, bucket:{} 创建成功", bucket.getName());
        return true;
    }

    /**
     * 判断某个bucket是否存在
     * @param bucketName
     * @return
     */
    public boolean isBucketExists(String bucketName) {
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket bucket : buckets) {
            if (Objects.equals(bucket.getName(), bucketName)) {
                return true;
            }
        }
        return false;
    }

}
