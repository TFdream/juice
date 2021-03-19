package juice;

import juice.entity.UserAddress;
import juice.entity.UserInfo;
import juice.util.ClassUtils;
import juice.util.DateUtils;
import juice.util.ReflectionUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Ricky Fung
 */
public class AppTest {

    @Test
    public void testDateUtils() {
        System.out.println(DateUtils.format(new Date()));

        System.out.println(DateUtils.parseDate("2021-03-18"));
        System.out.println(DateUtils.parseDateTime("2021-03-18 12:00:00"));
        System.out.println(DateUtils.parseJdkDate("2021-03-18 12:00:00"));
    }

    @Test
    public void testReflectionUtils() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("ricky");
        userInfo.setAddress(new UserAddress());

        Class clazz = userInfo.getClass();
        Method[] methods = ReflectionUtils.getDeclaredMethods(clazz);
        System.out.println(methods);

        Method method = ReflectionUtils.findMethod(clazz, "getName");
        System.out.println(method);

        System.out.println(ReflectionUtils.invokeMethod(method, userInfo));

        System.out.println("==========");

        Field[] fields = ReflectionUtils.getDeclaredFields(clazz);
        System.out.println(fields);

        Field field = ReflectionUtils.findField(clazz, "name");
        System.out.println(field);
        System.out.println(ReflectionUtils.getField(field, userInfo));

        System.out.println(ReflectionUtils.getFieldValue(userInfo, "name"));
    }

    @Test
    public void testClassUtils() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("ricky");
        userInfo.setAddress(new UserAddress());

        System.out.println(ClassUtils.getClassFileName(userInfo.getClass()));
        System.out.println(ClassUtils.getPackageName(userInfo.getClass()));
        System.out.println(ClassUtils.getQualifiedName(userInfo.getClass()));

        System.out.println("==========");
        System.out.println(ClassUtils.isPrimitiveWrapper(userInfo.getClass()));
        System.out.println(ClassUtils.isPrimitiveOrWrapper(userInfo.getClass()));
    }
}
