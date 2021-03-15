package juice.util;

import java.util.UUID;

/**
 * @author Ricky Fung
 */
public abstract class UUIDUtils {

    public static String getId() {
        return UUID.randomUUID().toString();
    }

    public static String getCompactId() {
        return getId().replace("-", "");
    }
}
