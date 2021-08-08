package juice.util;

import java.net.*;
import java.util.Enumeration;

/**
 * @author Ricky Fung
 */
public abstract class IpUtils {

    /**
     * IP转换为无符号整数
     * @param ipStr
     * @return
     */
    public static long ip2Long(String ipStr) {
        String[] ip = ipStr.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16)
                + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 无符号整数转换为IP
     * @param ipLong
     * @return
     */
    public static String long2Ip(long ipLong) {
        StringBuilder sb = new StringBuilder(15);
        sb.append(ipLong >>> 24).append(".");
        sb.append((ipLong >>> 16) & 0xFF).append(".");
        sb.append((ipLong >>> 8) & 0xFF).append(".");
        sb.append(ipLong & 0xFF);
        return sb.toString();
    }

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
