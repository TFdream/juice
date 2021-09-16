package juice.util;

import org.junit.Test;

/**
 * @author Ricky Fung
 */
public class BitUtilsTest {
    
    @Test
    public void testBit() {
        byte b = -70;
        System.out.println("二进制形式："+Integer.toBinaryString(b & 0xFF));
        
        System.out.println("高4位：" + BitUtils.getHigh4Bit(b));
        System.out.println("低4位：" + BitUtils.getLow4Bit(b));
    }
    
    @Test
    public void testSet() {
        int n = 5;
        System.out.println("二进制形式："+Integer.toBinaryString(n));
        
        System.out.println("高4位：" + BitUtils.setBit(n, 2, true));
        System.out.println("高4位：" + BitUtils.setBit(n, 2, false));
    }
}
