package juice.security.internal;

/**
 * @author Ricky Fung
 */
public enum ClientType {
    ANDROID(1, "移动客户端-Android"),
    IOS(2, "移动客户端-iOS"),
    PC(3, "PC端-web"),
    H5(4, "手机端-h5"),
    ;
    private int value;
    private String desc;
    ClientType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
