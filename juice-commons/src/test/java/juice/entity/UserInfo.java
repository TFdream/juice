package juice.entity;

/**
 * @author Ricky Fung
 */
public class UserInfo {
    private String name;
    private UserAddress address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }
}
