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
    public void testGetBit() {
        int num = 9;
        System.out.println("二进制形式："+Integer.toBinaryString(num));
    
        System.out.println(BitUtils.getBit(num, 2));
        System.out.println(BitUtils.getBoolean(num, 2));
    }
    
    @Test
    public void testSetBit() {
        int num = -70;
        System.out.println("二进制形式："+Integer.toBinaryString(num));
        
        int result = BitUtils.setBit(num, 3, true);
        System.out.println("二进制形式："+Integer.toBinaryString(result) + "对应十进制："+result);
    
        result = BitUtils.setBit(num, 3, false);
        System.out.println("二进制形式："+Integer.toBinaryString(result) + "对应十进制："+result);
    }
}
