package juice.amazonaws.s3.metadata;

/**
 * @author Ricky Fung
 */
public enum UserMetadataKey {
    FILENAME_KEY("filename", "原始文件名"),
    TIMESTAMP_KEY("timestamp", "上传时间"),
    MD5("md5", "文件签名"),
    ;
    private String name;
    private String desc;
    UserMetadataKey(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
