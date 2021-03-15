package juice.core.util;

import juice.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * @author Ricky Fung
 */
public abstract class CookieUtils {

    /**
     * 获取指定cookie的值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        //获取cookie
        Cookie cookie = getCookie(request, cookieName);
        if (cookie==null) {
            return null;
        }
        return cookie.getValue();
    }
    /**
     * 根据名称查找cookie
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null || cookies.length < 1) {
            return null;
        }
        Cookie target = null;
        for (Cookie cookie : cookies) {
            if (cookieName!=null && cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return target;
    }

    //--------

    /**
     * 设置cookie，默认失效时间为1小时
     * @param response
     * @param name
     * @param value
     * @return
     */
    public static Cookie addCookie(HttpServletResponse response, String name, String value) {
        return addCookie(response, name, value, 3600);
    }

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge 单位: 秒
     * @return
     */
    public static Cookie addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        //将token添加到cookie
        Cookie cookie = new Cookie(name, value);
        //设置过期时间 单位：秒
        cookie.setMaxAge(maxAge);
        //HttpOnly: 告知浏览器不允许通过脚本document.cookie去更改这个值，同样这个值在document.cookie中也不可见。
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        return cookie;
    }

    /**
     * 删除cookie
     * @param response
     * @param name
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, StringUtils.EMPTY);
        cookie.setMaxAge(0);//设置立即删除
        response.addCookie(cookie);
    }
}
