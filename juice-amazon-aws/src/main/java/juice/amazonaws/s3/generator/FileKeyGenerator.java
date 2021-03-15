package juice.amazonaws.s3.generator;

import juice.amazonaws.s3.metadata.FileMetadata;

/**
 * @author Ricky Fung
 */
public interface FileKeyGenerator {

    String generateKey(FileMetadata metadata);
}
