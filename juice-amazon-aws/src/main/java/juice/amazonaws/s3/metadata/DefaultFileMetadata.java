package juice.amazonaws.s3.metadata;

import juice.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author Ricky Fung
 */
public class DefaultFileMetadata implements FileMetadata {
    private String prefixPath;
    private LocalDateTime currentTime;
    private String filename;
    private String suffix;
    private String contentMd5;

    public void setPrefixPath(String prefixPath) {
        this.prefixPath = prefixPath;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setSuffix(String suffix) {
        if (StringUtils.isEmpty(suffix) || !suffix.startsWith(".")) {
            throw new IllegalArgumentException("文件后缀名不正确, 正确示例: .png");
        }
        this.suffix = suffix;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    @Override
    public String getPrefixPath() {
        return prefixPath;
    }

    @Override
    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public String getContentMd5() {
        return contentMd5;
    }
}
