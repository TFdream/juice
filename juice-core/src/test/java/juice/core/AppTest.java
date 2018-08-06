package juice.core;

import juice.core.model.User;
import juice.core.util.DateUtils;
import juice.core.util.JsonUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricky Fung
 */
public class AppTest {

    @Test
    public void testDate() {
        DateTime now = DateTime.now().plusDays(1);
        System.out.println("start"+DateUtils.getStartOfWeek(now));
        System.out.println("end:"+DateUtils.getEndOfWeek(now));
    }

    @Test
    public void testApp() {
        User user = new User();
        user.setId(15L);
        user.setName("ricky fung");
        user.setAge(28);
        user.setMale(true);
        user.setBirthday(DateUtils.parseDate("1989-09-15 00:00:00"));
        user.setHobbies(Arrays.asList("NBA"));
        Map<String, String> tags = new HashMap<>(4);
        tags.put("lable", "80后");
        tags.put("job", "IT");
        user.setTags(tags);

        String json = JsonUtils.toJson(user);
        System.out.println(json);

        user = JsonUtils.parseObject(json, User.class);
        System.out.println(user);
    }
}
