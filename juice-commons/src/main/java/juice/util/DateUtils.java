package juice.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 基于Java8 time API
 * @author Ricky Fung
 */
public class DateUtils {
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String STANDARD_DATE_FORMAT_UTC_SHORT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String STANDARD_DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * 日期
     */
    public static final String DATE_STANDARD_FORMAT = "yyyy-MM-dd";

    public static final String DATE_COMPACT_FORMAT = "yyyyMMdd";

    /**
     * 时间格式
     */
    public static final String TIME_STANDARD_FORMAT = "HH:mm:ss";

    private DateUtils() {}
    
    //========
    public static LocalDateTime parseDateTime(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STANDARD_FORMAT);
        return LocalDateTime.parse(dateStr, formatter);
    }
    public static LocalDateTime parseDateTime(String dateStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, formatter);
    }

    //========
    public static LocalDate parseDate(String dateStr) {
        return parseDate(dateStr, DATE_STANDARD_FORMAT);
    }
    public static LocalDate parseDate(String dateStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, formatter);
    }

    //========
    public static Date parseJdkDate(String dateStr) {
        return parseJdkDate(dateStr, STANDARD_FORMAT);
    }
    public static Date parseJdkDate(String dateStr, String pattern) {
        LocalDateTime dateTime = parseDateTime(dateStr, pattern);
        return convertToDateViaInstant(dateTime);
    }

    //=========
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, STANDARD_FORMAT);
    }
    public static String format(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    public static String format(Date date) {
        return format(date, STANDARD_FORMAT);
    }

    public static String format(Date date, String pattern) {
        return format(convertToLocalDateTime(date), pattern);
    }

    public static String formatDate(LocalDate date) {
        return formatDate(date, DATE_STANDARD_FORMAT);
    }

    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    //=========
    public static Date convertToDateViaInstant(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date convertToDateViaInstant(LocalDate date) {
        return Date.from(date.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    //========= java.util.Date ---> LocalDateTime
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalDateTime convertToLocalDateTimeViaMilisecond(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    //========= java.util.Date ---> LocalDate
    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDate convertToLocalDateViaMilisecond(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}