package com.papermanagement.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日历工具类
 */
public class CalendarUtils {

    /**
     * 获得今年
     * @return 年份
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获得本月
     * @return 月份
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * 获得今天
     * @return 今日
     */
    public static int getDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前小时
     * @return
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 获得今天的时间格式
     * @return      今天
     */
    public static String getToDay() {
        return getYear() + "-" + formatCalendar(getMonth() + 1) + "-" +formatCalendar(getDayOfMonth());
    }

    /**
     * 获得昨天
     * @return 昨天日期
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + formatCalendar(month) + "-" + formatCalendar(day);
    }

    /**
     * 格式化月份或者天
     * @param dayOrMonth 月份或者天
     * @return  格式化结果
     */
    public static String formatCalendar(int dayOrMonth) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(dayOrMonth);
    }

    /**
     * 获得当前时间
     * @return 当前时间
     */
    public static String getTodayTime() {
        SimpleDateFormat formatter = new SimpleDateFormat ("hh:mm");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

}
