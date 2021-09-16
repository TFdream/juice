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
     * Sets the bit at the specified index to {@code true}.
     * @param source
     * @param bitIndex [0, 31]
     * @return
     */
    public static int set(int source, int bitIndex) {
        source |= (1L << bitIndex);
        return source;
    }
    
    /**
     * Sets the bit at the specified index to the specified value.
     * @param source
     * @param bitIndex
     * @param value
     * @return
     */
    public static int set(int source, int bitIndex, boolean value) {
        if (value) {
            return set(source, bitIndex);
        } else {
            return clear(source, bitIndex);
        }
    }
    
    /**
     * Sets the bit specified by the index to {@code false}.
     * @param source
     * @param bitIndex
     * @return
     */
    public static int clear(int source, int bitIndex) {
        source &= ~(1L << bitIndex);
        return source;
    }
    
    /**
     * Returns the value of the bit with the specified index. The value
     * is {@code true} if the bit with the index {@code bitIndex}
     * is currently 1; otherwise, the result
     * is {@code false}.
     * @param source
     * @param bitIndex
     * @return
     */
    public static boolean get(int source, int bitIndex) {
        return ((source & (1L << bitIndex)) != 0);
    }
    
}