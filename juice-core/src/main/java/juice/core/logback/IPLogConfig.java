package juice.core.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import juice.util.IpUtils;

/**
 * @author Ricky Fung
 */
public class IPLogConfig extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return IpUtils.getLocalIp();
    }
}
