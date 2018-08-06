package juice.core.util;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author Ricky Fung
 */
public class DateUtils {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_COMPACT_FORMAT = "yyyyMMdd";

    /**日期加减法**/
    public static DateTime plusYears(Date date, int years){
        return new DateTime(date).plusYears(years);
    }
    public static DateTime minusYears(Date date, int years){
        return new DateTime(date).minusYears(years);
    }

    public static DateTime plusMonths(Date date, int months){
        return new DateTime(date).plusMonths(months);
    }
    public static DateTime minusMonths(Date date, int months){
        return new DateTime(date).minusMonths(months);
    }

    public static DateTime plusWeeks(Date date, int weeks){
        return new DateTime(date).plusWeeks(weeks);
    }
    public static DateTime minusWeeks(Date date, int weeks){
        return new DateTime(date).minusWeeks(weeks);
    }

    public static DateTime plusDays(Date date, int days){
        return new DateTime(date).plusDays(days);
    }
    public static DateTime minusDays(Date date, int days){
        return new DateTime(date).minusDays(days);
    }

    public static DateTime plusHours(Date date, int hours){
        return new DateTime(date).plusHours(hours);
    }
    public static DateTime minusHours(Date date, int hours){
        return new DateTime(date).minusHours(hours);
    }

    public static DateTime plusMinutes(Date date, int minutes){
        return new DateTime(date).plusMinutes(minutes);
    }
    public static DateTime minusMinutes(Date date, int minutes){
        return new DateTime(date).minusMinutes(minutes);
    }

    public static DateTime plusSeconds(Date date, int seconds){
        return new DateTime(date).plusSeconds(seconds);
    }
    public static DateTime minusSeconds(Date date, int seconds){
        return new DateTime(date).minusSeconds(seconds);
    }

    public static DateTime now() {
        return DateTime.now();
    }

    /**计算时间差*/
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

    /**格式化日期*/
    public static String format(Date date) {
        return format(date, STANDARD_FORMAT);
    }
    public static String format(Date date, String format) {
        return new DateTime(date).toString(format);
    }

    public static String format(DateTime date) {
        return format(date, STANDARD_FORMAT);
    }
    public static String format(DateTime date, String format) {
        return date.toString(format);
    }

    /**解析日期&时间*/
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

    /** 计算开始/截止日期 **/
    /** day **/
    public static DateTime getStartOfDay(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0, 0);
    }

    public static DateTime getEndOfDay(DateTime dateTime) {
        return getStartOfDay(dateTime).plusDays(1).minusSeconds(1);
    }

    /** week **/
    public static DateTime getStartOfWeek(DateTime dateTime) {
        return dateTime.withDayOfWeek(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    public static DateTime getEndOfWeek(DateTime dateTime) {
        return dateTime.withDayOfWeek(7).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    /** month **/
    public static DateTime getStartOfMonth(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0, 0);
    }

    public static DateTime getEndOfMonth(DateTime dateTime) {
        return getStartOfMonth(dateTime).plusMonths(1).minusSeconds(1);
    }

    /** year **/
    public static DateTime getStartOfYear(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), 1, 1, 0, 0, 0);
    }

    public static DateTime getEndOfYear(DateTime dateTime) {
        return getStartOfYear(dateTime).plusYears(1).minusSeconds(1);
    }
}
