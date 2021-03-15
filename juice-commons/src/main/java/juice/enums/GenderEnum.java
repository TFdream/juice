package juice.enums;

import juice.util.StringUtils;

/**
 * @author Ricky Fung
 */
public enum GenderEnum {
    FEMALE(0, "女"),
    MALE(1, "男"),
    UNKNOWN(-1, "未知"),
    ;
    private int type;
    private String gender;

    GenderEnum(int type, String gender) {
        this.type = type;
        this.gender = gender;
    }

    public static GenderEnum getByType(int type) {
        for (GenderEnum g : GenderEnum.values()) {
            if (g.getType() == type) {
                return g;
            }
        }
        return UNKNOWN;
    }

    public static GenderEnum getByGender(String gender) {
        if (StringUtils.isEmpty(gender)) {
            return UNKNOWN;
        }
        for (GenderEnum g : GenderEnum.values()) {
            if (g.getGender().equals(gender)) {
                return g;
            }
        }
        return UNKNOWN;
    }

    public int getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }
}
