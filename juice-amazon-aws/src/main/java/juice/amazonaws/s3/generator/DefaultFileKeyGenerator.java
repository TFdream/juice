package juice.amazonaws.s3.generator;

import juice.amazonaws.s3.metadata.FileMetadata;
import juice.util.StringUtils;
import juice.util.UUIDUtils;

import java.time.LocalDate;

/**
 * @author Ricky Fung
 */
public class DefaultFileKeyGenerator implements FileKeyGenerator {
    private static final String SEPARATOR = "/";

    /**
     * prefix/year/month/md5(file).suffix
     * @param metadata
     * @return
     */
    @Override
    public String generateKey(FileMetadata metadata) {
        LocalDate current = metadata.getCurrentTime().toLocalDate();

        StringBuilder sb = new StringBuilder(160);
        if (StringUtils.isNotEmpty(metadata.getPrefixPath())) {
            sb.append(metadata.getPrefixPath()).append(SEPARATOR);
        }

        sb.append(current.getYear()).append(SEPARATOR);
        int month = current.getMonth().getValue();

        sb.append(month > 9 ? String.valueOf(month) : "0"+month).append(SEPARATOR);
        if (StringUtils.isNotEmpty(metadata.getContentMd5())) {
            sb.append(metadata.getContentMd5());
        } else {    //生成唯一ID
            sb.append(UUIDUtils.getCompactId());
        }

        if (StringUtils.isNotEmpty(metadata.getSuffix())) {
            sb.append(metadata.getSuffix());
        }
        return sb.toString();
    }
}
