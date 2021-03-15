package juice.amazonaws.s3.metadata;

import java.time.LocalDateTime;

/**
 * @author Ricky Fung
 */
public interface FileMetadata {

    String getPrefixPath();

    LocalDateTime getCurrentTime();

    String getFilename();

    /**
     * 文件扩展名
     * 例如:
     * .png
     * .jpg
     * .jpeg
     * .txt
     * @return
     */
    String getSuffix();

    /**
     * 文件内容MD5
     * @return
     */
    String getContentMd5();
}
