package juice.util;

/**
 * @author Ricky Fung
 */
public class JdkUtils {
    private static final int JAVA_VERSION = javaVersion0();
    
    private JdkUtils() {}
    
    /**
     * Return the version of Java under which this library is used.
     */
    public static int javaVersion() {
        return JAVA_VERSION;
    }
    
    private static int javaVersion0() {
        String version = System.getProperty("java.specification.version", "1.6");
        int majorVersion = majorVersion(version);
        return majorVersion;
    }
    
    // Package-private for testing only
    static int majorVersion(final String javaSpecVersion) {
        final String[] components = javaSpecVersion.split("\\.");
        final int[] version = new int[components.length];
        for (int i = 0; i < components.length; i++) {
            version[i] = Integer.parseInt(components[i]);
        }
        
        if (version[0] == 1) {
            assert version[1] >= 6;
            return version[1];
        } else {
            return version[0];
        }
    }
}
