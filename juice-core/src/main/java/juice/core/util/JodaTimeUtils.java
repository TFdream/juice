package juice.core.util;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author Ricky Fung
 */
public class JodaTimeUtils {
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

    public static final String HOUR_FORMAT = "HH:mm:ss";

    /** 格式化日期&时间 **/
    /** java.util.Date **/
    public static String format(Date date) {
        return format(date, STANDARD_FORMAT);
    }

    public static String format(Date date, String format) {
        return new DateTime(date).toString(format);
    }

    /** DateTime **/
    public static String format(DateTime date) {
        return format(date, STANDARD_FORMAT);
    }

    public static String format(DateTime date, String format) {
        return date.toString(format);
    }

    //--------------
    public static String formatDate(Date date) {
        return format(date, DATE_STANDARD_FORMAT);
    }
    public static String formatDate(Date date, String format) {
        return format(date, format);
    }

    public static String formatDate(DateTime date) {
        return format(date, DATE_STANDARD_FORMAT);
    }
    public static String formatDate(DateTime date, String format) {
        return format(date, format);
    }

    //-----------------
    public static String formatTime(Date date) {
        return format(date, TIME_STANDARD_FORMAT);
    }
    public static String formatTime(Date date, String format) {
        return format(date, format);
    }

    public static String formatTime(DateTime date) {
        return format(date, TIME_STANDARD_FORMAT);
    }
    public static String formatTime(DateTime date, String format) {
        return format(date, format);
    }
    //-----------------

    /** 解析日期&时间 **/
    public static DateTime parseDateTime(String date) {
        return parseDateTime(date, STANDARD_FORMAT);
    }
    public static DateTime parseDateTime(String date, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(date, dateTimeFormatter);
    }

    public static Date parseDate(String date) {
        return parseDate(date, STANDARD_FORMAT);
    }
    public static Date parseDate(String date, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(date, dateTimeFormatter).toDate();
    }

    public static DateTime now() {
        return DateTime.now();
    }

    //----------

    public static Date getDate(int year, int month, int date, int hour, int minute, int second) {
        return new DateTime(year, month, date, hour, minute, second).toDate();
    }

    public static DateTime getDateTime(int year, int month, int date, int hour, int minute, int second) {
        return new DateTime(year, month, date, hour, minute, second);
    }

    /**计算时间差**/
    public static int yearsBetween(Date start, Date end){
        return yearsBetween(new DateTime(start), new DateTime(end));
    }
    public static int yearsBetween(ReadableInstant start, ReadableInstant end){
        return Years.yearsBetween(start, end).getYears();
    }
    public static int yearsBetween(ReadablePartial start, ReadablePartial end){
        return Years.yearsBetween(start, end).getYears();
    }

    /** months **/
    public static int monthsBetween(Date start, Date end){
        return monthsBetween(new DateTime(start), new DateTime(end));
    }
    public static int monthsBetween(ReadableInstant start, ReadableInstant end){
        return Months.monthsBetween(start, end).getMonths();
    }
    public static int monthsBetween(ReadablePartial start, ReadablePartial end){
        return Months.monthsBetween(start, end).getMonths();
    }

    /** weeks **/
    public static int weeksBetween(Date start, Date end){
        return weeksBetween(new DateTime(start), new DateTime(end));
    }
    public static int weeksBetween(ReadableInstant start, ReadableInstant end){
        return Weeks.weeksBetween(start, end).getWeeks();
    }
    public static int weeksBetween(ReadablePartial start, ReadablePartial end){
        return Weeks.weeksBetween(start, end).getWeeks();
    }

    /** days **/
    public static int daysBetween(Date start, Date end){
        return daysBetween(new DateTime(start), new DateTime(end));
    }
    public static int daysBetween(ReadableInstant start, ReadableInstant end){
        return Days.daysBetween(start, end).getDays();
    }
    public static int daysBetween(ReadablePartial start, ReadablePartial end) {
        return Days.daysBetween(start, end).getDays();
    }

    /** hours **/
    public static int hoursBetween(Date start, Date end){
        return hoursBetween(new DateTime(start), new DateTime(end));
    }
    public static int hoursBetween(ReadableInstant start, ReadableInstant end){
        return Hours.hoursBetween(start, end).getHours();
    }
    public static int hoursBetween(ReadablePartial start, ReadablePartial end){
        return Hours.hoursBetween(start, end).getHours();
    }

    /** minutes **/
    public static int minutesBetween(Date start, Date end){
        return minutesBetween(new DateTime(start), new DateTime(end));
    }
    public static int minutesBetween(ReadableInstant start, ReadableInstant end){
        return Minutes.minutesBetween(start, end).getMinutes();
    }
    public static int minutesBetween(ReadablePartial start, ReadablePartial end){
        return Minutes.minutesBetween(start, end).getMinutes();
    }

    /** seconds **/
    public static int secondsBetween(Date start, Date end){
        return secondsBetween(new DateTime(start), new DateTime(end));
    }
    public static int secondsBetween(ReadableInstant start, ReadableInstant end){
        return Seconds.secondsBetween(start, end).getSeconds();
    }
    public static int secondsBetween(ReadablePartial start, ReadablePartial end){
        return Seconds.secondsBetween(start, end).getSeconds();
    }


    /** 计算开始/截止日期 **/
    /** day **/
    public static DateTime getStartOfDay(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0, 0);
    }

    public static DateTime getEndOfDay(DateTime dateTime) {
        return dateTime.millisOfDay().withMaximumValue();
    }

    /** week **/
    public static DateTime getStartOfWeek(DateTime dateTime) {
        DateTime start = dateTime.dayOfWeek().withMinimumValue();
        return getStartOfDay(start);
    }

    public static DateTime getEndOfWeek(DateTime dateTime) {
        DateTime end = dateTime.dayOfWeek().withMaximumValue();
        return getEndOfDay(end);
    }

    /** month **/
    public static DateTime getStartOfMonth(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0, 0);
    }

    public static DateTime getEndOfMonth(DateTime dateTime) {
        DateTime end = dateTime.dayOfMonth().withMaximumValue();
        return getEndOfDay(end);
    }

    /** year **/
    public static DateTime getStartOfYear(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), 1, 1, 0, 0, 0);
    }

    public static DateTime getEndOfYear(DateTime dateTime) {
        DateTime end = dateTime.dayOfYear().withMaximumValue();
        return getEndOfDay(end);
    }

    /**
     * 在指定日期上加上指定天数
     * @param date
     * @param days 天数
     * @return
     */
    public static DateTime plusDays(Date date, int days) {
        return new DateTime(date).plusDays(days);
    }

    public static DateTime plusDays(DateTime date, int days) {
        return date.plusDays(days);
    }

    //------
    public static DateTime minusDays(Date date, int days) {
        return new DateTime(date).minusDays(days);
    }

    public static DateTime minusDays(DateTime date, int days) {
        return date.minusDays(days);
    }

    //-------
    /**
     * 判断是否为同一天
     * @param start
     * @param end
     * @return
     */
    public static boolean isSameDay(DateTime start, DateTime end) {
        LocalDate startDate = new LocalDate(start);
        LocalDate endDate = new LocalDate(end);
        return startDate.isEqual(endDate);
    }

    /**
     * 判断 给定的时间是否在 基准时间 一天以内
     * @param time 给定的时间
     * @param pivot 基准时间
     * @return
     */
    public static boolean isWithinOneDay(DateTime time, DateTime pivot) {
        LocalDate date = new LocalDate(time);
        LocalDate pivotDate = new LocalDate(pivot);
        return Math.abs(Days.daysBetween(date, pivotDate).getDays()) <= 1;
    }

    /**
     * 判断 给定的时间是否在 基准时间 一天以外
     * @param time
     * @param pivot
     * @return
     */
    public static boolean isBeyondOneDay(DateTime time, DateTime pivot) {
        return !isWithinOneDay(time, pivot);
    }
}
