package juice.core.util.math;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ricky Fung
 */
public abstract class RandomUtils {

    public static int nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static long nextLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    public static long nextLong(long bound) {
        return ThreadLocalRandom.current().nextLong(bound);
    }
}
