package juice.commons;

import juice.util.StringUtils;

/**
 * 十进制与其它进制转换
 * @author Ricky Fung
 */
public class NumberCodec {
    /**
     * 进制对应的字典表
     */
    private final char[] dictionary;
    /**
     * 进制基数，例如十六进制则值为16
     */
    private final int baseNum;
    /**
     * 该进制对应的字符串最大长度
     */
    private final int maxNumLength;

    public NumberCodec(char[] dictionary) {
        this.dictionary = dictionary;
        this.baseNum = this.dictionary.length;

        this.maxNumLength = deduceMaxLength(Long.MAX_VALUE, baseNum);
    }

    protected int deduceMaxLength(long num, int baseNum) {
        int len = 0;
        while (num > 0) {
            num = num / baseNum;
            len++;
        }
        return len;
    }

    /**
     * 编码（将十进制数转为对应的N进制）
     * 思路：十进制转N进制数，将其不断除以N取余数，然后倒序。
     * @param num
     * @return
     */
    public String encode(long num) {
        if (num < 0) {
            throw new IllegalArgumentException("must be non-negative number");
        }
        if (num==0) {
            //取码表第一个字符
            return new String(new char[]{dictionary[0]});
        }
        StringBuilder sb = new StringBuilder(maxNumLength);
        long val = num;
        while (val > 0) {
            int mod = (int)(val % baseNum);
            sb.append(getCharByIndex(mod));
            val = val / baseNum;
        }
        //倒序后返回
        return sb.reverse().toString();
    }

    /**
     * 解码（将X进制字符串转换为对应的十进制数）
     * 思路：X进制转十进制数，从右往左每个数 乘以X的N次方，N从0开始。
     * @param data N进制字符串
     * @return
     */
    public long decode(String data) {
        if (StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("null or empty:"+data);
        }

        char[] chars = data.toCharArray();
        long num = 0;
        int pow = 0;
        //从右边开始
        for (int i=chars.length-1; i>=0; i--) {
            char item = chars[i];
            int index = getIndexByChar(item);
            num += getRepresentNum(index, pow);
            pow++;
        }
        return num;
    }

    //---------
    protected char getCharByIndex(int index) {
        return dictionary[index];
    }

    protected int getIndexByChar(char ch) {
        for (int i=0; i<baseNum; i++) {
            char item = dictionary[i];
            if (ch == item) {
                return i;
            }
        }
        return -1;
    }

    protected long getRepresentNum(int num, int pos) {
        return num * (long) Math.pow(baseNum, pos);
    }
}
