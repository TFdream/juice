package juice.core.util;

/**
 * @author Ricky Fung
 */
public abstract class ByteUtils {

    private static final int SHORT_BYTE_SIZE = 2;
    private static final int INT_BYTE_SIZE = 4;
    private static final int LONG_BYTE_SIZE = 8;

    public static byte[] shortToByteArray(short i) {
        byte[] result = new byte[2];
        result[0] = (byte)((i >> 8) & 0xFF);
        result[1] = (byte)(i & 0xFF);
        return result;
    }

    public static short byteArrayToShort(byte[] data) {
        return (short) (data[1] & 0xFF |
                        (data[0] & 0xFF) << 8);
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }

    public static int byteArrayToInt(byte[] data) {
        AssertUtils.isTrue(data.length==INT_BYTE_SIZE);
        return data[3] & 0xFF |
                (data[2] & 0xFF) << 8 |
                (data[1] & 0xFF) << 16 |
                (data[0] & 0xFF) << 24;
    }

    public static int byteArrayToInt(byte[] data, int from, int len) {
        AssertUtils.isTrue(len==INT_BYTE_SIZE);
        int res = 0, temp = 0;
        for (int i = 0; i < 4; i++) {
            res <<= 8;
            temp = data[from + i] & 0xFF;
            res |= temp;
        }
        return res;
    }

    public static byte[] longToByteArray(long i) {
        byte[] result = new byte[8];
        result[0] = (byte)((i >> 56) & 0xFF);
        result[1] = (byte)((i >> 48) & 0xFF);
        result[2] = (byte)((i >> 40) & 0xFF);
        result[3] = (byte)((i >> 32) & 0xFF);
        result[4] = (byte)((i >> 24) & 0xFF);
        result[5] = (byte)((i >> 16) & 0xFF);
        result[6] = (byte)((i >> 8) & 0xFF);
        result[7] = (byte)(i & 0xFF);
        return result;
    }

    public static long byteArrayToLong(byte[] data) {
        return data[7] & 0xFF |
                (data[6] & 0xFF) << 8 |
                (data[5] & 0xFF) << 16 |
                (data[4] & 0xFF) << 24 |
                (data[3] & 0xFF) << 32 |
                (data[2] & 0xFF) << 40 |
                (data[1] & 0xFF) << 48 |
                (data[0] & 0xFF) << 56;
    }
}
