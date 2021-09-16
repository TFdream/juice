package juice.util;

/**
 * @author Ricky Fung
 */
public class BitUtils {
    private BitUtils() {}
    
    public static int getHigh4Bit(byte b){
        return (b & 0xf0) >> 4;
    }
    
    public static int getLow4Bit(byte b){
        return b & 0x0f;
    }
    
    //========
    
    /**
     * 从低位到高位的顺序取n的第m位
     * @param num
     * @param bitIndex 从1开始
     * @return
     */
    public static int getBit(int num, int bitIndex) {
        return (num >> (bitIndex - 1)) & 1;
    }
    
    public static boolean getBoolean(int num, int bitIndex) {
        return (num & (1L << bitIndex)) != 0;
    }
    
    /**
     * 将n的第m位 置1或者0
     * @param num
     * @param bitIndex 从1开始
     * @param flag
     * @return
     */
    public static int setBit(int num, int bitIndex, boolean flag) {
        if (flag) {
            return num | (1 << (bitIndex - 1));
        }
        return num & ~(0 << (bitIndex - 1));
    }
}
