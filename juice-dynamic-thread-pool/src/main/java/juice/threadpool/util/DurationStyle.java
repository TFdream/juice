package juice.threadpool.util;

import juice.commons.Assertions;
import juice.util.StringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Duration format styles.
 *
 * @author Phillip Webb
 * @since 2.0.0
 */
public enum DurationStyle {

	/**
	 * Simple formatting, for example '1s'.
	 */
	SIMPLE("^([+-]?\\d+)([a-zA-Z]{0,2})$") {
		@Override
		public Duration parse(String value, ChronoUnit unit) {
			try {
				Matcher matcher = matcher(value);
				Assertions.state(matcher.matches(), "Does not match simple duration pattern");
				String suffix = matcher.group(2);
				return (StringUtils.isNotEmpty(suffix) ? Unit.fromSuffix(suffix) : Unit.fromChronoUnit(unit))
						.parse(matcher.group(1));
			} catch (Exception ex) {
				throw new IllegalArgumentException("'" + value + "' is not a valid simple duration", ex);
			}
		}

		@Override
		public String print(Duration value, ChronoUnit unit) {
			return Unit.fromChronoUnit(unit).print(value);
		}

	},

	/**
	 * ISO-8601 formatting.
	 */
	ISO8601("^[+-]?P.*$") {
		@Override
		public Duration parse(String value, ChronoUnit unit) {
			try {
				return Duration.parse(value);
			} catch (Exception ex) {
				throw new IllegalArgumentException("'" + value + "' is not a valid ISO-8601 duration", ex);
			}
		}

		@Override
		public String print(Duration value, ChronoUnit unit) {
			return value.toString();
		}

	};

	private final Pattern pattern;

	DurationStyle(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	protected final boolean matches(String value) {
		return this.pattern.matcher(value).matches();
	}

	protected final Matcher matcher(String value) {
		return this.pattern.matcher(value);
	}

	/**
	 * Parse the given value to a duration.
	 *
	 * @param value the value to parse
	 * @return a duration
	 */
	public Duration parse(String value) {
		return parse(value, null);
	}

	/**
	 * Parse the given value to a duration.
	 *
	 * @param value the value to parse
	 * @param unit  the duration unit to use if the value doesn't specify one ({@code null}
	 *              will default to ms)
	 * @return a duration
	 */
	public abstract Duration parse(String value, ChronoUnit unit);

	/**
	 * Print the specified duration.
	 * @param value the value to print
	 * @return the printed result
	 */
	public String print(Duration value) {
		return print(value, null);
	}

	/**
	 * Print the specified duration using the given unit.
	 * @param value the value to print
	 * @param unit the value to use for printing
	 * @return the printed result
	 */
	public abstract String print(Duration value, ChronoUnit unit);

	/**
	 * Detect the style from the given source value.
	 * @param value the source value
	 * @return the duration style
	 * @throws IllegalArgumentException if the value is not a known style
	 */
	public static DurationStyle detect(String value) {
		Assertions.notNull(value, "Value must not be null");
		for (DurationStyle candidate : values()) {
			if (candidate.matches(value)) {
				return candidate;
			}
		}
		throw new IllegalArgumentException("'" + value + "' is not a valid duration");
	}

	/**
	 * Units that we support.
	 */
	enum Unit {

		/**
		 * Nanoseconds.
		 */
		NANOS(ChronoUnit.NANOS, "ns", Duration::toNanos),

		/**
		 * Microseconds.
		 */
		MICROS(ChronoUnit.MICROS, "us", (duration) -> duration.toMillis() * 1000L),

		/**
		 * Milliseconds.
		 */
		MILLIS(ChronoUnit.MILLIS, "ms", Duration::toMillis),

		/**
		 * Seconds.
		 */
		SECONDS(ChronoUnit.SECONDS, "s", Duration::getSeconds),

		/**
		 * Minutes.
		 */
		MINUTES(ChronoUnit.MINUTES, "m", Duration::toMinutes),

		/**
		 * Hours.
		 */
		HOURS(ChronoUnit.HOURS, "h", Duration::toHours),

		/**
		 * Days.
		 */
		DAYS(ChronoUnit.DAYS, "d", Duration::toDays);

		private final ChronoUnit chronoUnit;

		private final String suffix;

		private Function<Duration, Long> longValue;

		Unit(ChronoUnit chronoUnit, String suffix, Function<Duration, Long> toUnit) {
			this.chronoUnit = chronoUnit;
			this.suffix = suffix;
			this.longValue = toUnit;
		}

		public Duration parse(String value) {
			return Duration.of(Long.parseLong(value), this.chronoUnit);
		}

		public String print(Duration value) {
			return longValue(value) + this.suffix;
		}

		public long longValue(Duration value) {
			return this.longValue.apply(value);
		}

		public static Unit fromChronoUnit(ChronoUnit chronoUnit) {
			if (chronoUnit == null) {
				return Unit.MILLIS;
			}
			for (Unit candidate : values()) {
				if (candidate.chronoUnit == chronoUnit) {
					return candidate;
				}
			}
			throw new IllegalArgumentException("Unknown unit " + chronoUnit);
		}

		public static Unit fromSuffix(String suffix) {
			for (Unit candidate : values()) {
				if (candidate.suffix.equalsIgnoreCase(suffix)) {
					return candidate;
				}
			}
			throw new IllegalArgumentException("Unknown unit '" + suffix + "'");
		}

	}

}