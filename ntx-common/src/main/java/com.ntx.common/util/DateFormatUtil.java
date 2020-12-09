package com.ntx.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

    //斜杠：slash
    public static final String SLASH_DATE = "yyyy/MM/dd";
    public static final String SLASH_DATETIME = "yyyy/MM/dd HH:mm:ss";
    public static final String SLASH_DATETIME_DETAIL = "yyyy/MM/dd HH:mm:ss SSS";

    //拼接：joint
    public static final String JOINT_DATE = "yyyyMMdd";
    public static final String JOINT_DATETIME = "yyyyMMddHHmmss";
    public static final String JOINT_DATETIME_DETAIL = "yyyyMMddHHmmssSSS";

    //连字符：hyphens（默认格式）
    public static final String HYPHENS_DATE = "yyyy-MM-dd";
    public static final String HYPHENS_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String HYPHENS_DATETIME_DETAIL = "yyyy-MM-dd HH:mm:ss SSS";

    public static final long MINUTE = 60 * 1000;    //1分钟(单位：毫秒)
    public static final long HOUR = 60 * MINUTE; //1小时(单位：毫秒)
    public static final long DAY = 24 * HOUR; //1天(单位：毫秒)


    /**
     * @Description 指定格式格式化日期
     * @param date 日期
     * @param pattern 格式
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 格式化之后的日期字符
     */
    public static String format(Date date, String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * @Description 指定格式格式化当前日期
     * @param pattern 格式
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 格式化之后的日期字符
     */
    public static String formatCurrentDate(String pattern){
        return format(new Date(), pattern);
    }

    /**
     * @Description 默认格式格式化当前日期
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 格式化之后的日期字符
     */
    public static String formatCurrentDate(){
        return formatCurrentDate(HYPHENS_DATETIME);
    }

    /**
     * @Description 指定格式：日期字符转日期格式
     * @param date 日期字符
     * @param pattern 日期格式
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 格式化之后的日期
     */
    public static Date parse(String date, String pattern) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(date);
    }

    /**
     * @Description 日期字符转日期格式,默认日期格式为:HYPHENS_DATETIME
     * @param date 日期字符
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 格式化之后的日期
     */
    public static Date parse(String date) throws ParseException{
        return parse(date, HYPHENS_DATETIME);
    }

    /**
     * @Description 获取当前日期的毫秒数
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 毫秒数
     */
    public static long millisecond(){
        return new Date().getTime();
    }

    /**
     * @Description 毫秒数转日期
     * @param millisecond 毫秒数
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 日期
     */
    public static Date millisecondToDate(long millisecond){
        return new Date(millisecond);
    }

    /**
     * @Description 毫秒数转日期字符
     * @param millisecond 毫秒数
     * @param pattern 日期格式
     * @Author dengyinghui
     * @Date 2019/7/9
     * @return 日期字符
     */
    public static String millisecondToFormatDate(long millisecond, String pattern){
        Date date = millisecondToDate(millisecond);
        return format(date, pattern);
    }

    /**
     * 获取当前日期的年份
     * @return
     */
    public static int getYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 根据日期获取该日期的年
     * @param date
     * @return
     */
    public static int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }





}
