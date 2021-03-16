package juice.security;

import org.springframework.util.Assert;

/**
 * @author Ricky Fung
 */
public class SecurityContextHolder {
	private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();

	public static void setContext(SecurityContext context) {
		Assert.notNull(context, "context is NULL");
		contextHolder.set(context);
	}

	public static SecurityContext getContext() {
		return contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}
}
