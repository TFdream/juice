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
     * @param n
     * @param m
     * @return
     */
    public static int getBit(int n, int m) {
        return  (n >> (m - 1)) & 1;
    }
    
    /**
     * 将n的第m位 置1或者0
     * @param n
     * @param m
     * @param flag
     * @return
     */
    public static int setBit(int n, int m, boolean flag) {
        if (flag) {
            return n | (1 << (m - 1));
        }
        return n & ~(0 << (m - 1));
    }
    
}
