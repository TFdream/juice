package juice.util;

import java.net.*;
import java.util.Enumeration;

/**
 * @author Ricky Fung
 */
public abstract class IpUtils {

    /**
     * 获取本机IP地址
     * @return
     */
    public static String getLocalIp() {
        InetAddress address = getLocalAddress();
        return address != null ? address.getHostAddress() : null;
    }

    private static InetAddress getLocalAddress() {
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
                    continue;
                }
                InetAddress ia = getPreferAddress(ni.getInetAddresses());
                if (ia != null) {
                    return ia;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException("获取日志Ip异常", e);
        }
        return null;
    }

    private static InetAddress getPreferAddress(Enumeration<InetAddress> enums) {
        while (enums != null && enums.hasMoreElements()) {
            InetAddress ia = enums.nextElement();
            if (ia.isLoopbackAddress() || ia instanceof Inet6Address) {
                continue;
            }
            return ia;
        }
        return null;
    }
}
