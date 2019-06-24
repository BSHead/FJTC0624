package com.foorich.auscashier.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-22 14:24
 * desc   : 时间戳转换
 * version: 1.0
 */
public class DateUtil {



    /**
     * 获取系统时间戳
     *
     * @return
     */
    public long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getCurDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static Date parseServerTime(String serverTime, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
            LogUtil.e("e:" + e);
        }
        return date;
    }

    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }

    // 时间转换
    public static String transform(String from) {
        String to = "";

        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat simple = new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 本地时区
        Calendar nowCal = Calendar.getInstance();
        TimeZone localZone = nowCal.getTimeZone();
        // 设定SDF的时区为本地
        simple.setTimeZone(localZone);

        SimpleDateFormat simple1 = new SimpleDateFormat("HH:mm:ss");
        // SimpleDateFormat simple1 = new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置 DateFormat的时间区域为GMT
        simple1.setTimeZone(TimeZone.getTimeZone("GMT"));

        // 把字符串转化为Date对象，然后格式化输出这个Date
        Date fromDate = new Date();
        // 时间string解析成GMT时间
        try {
            fromDate = simple1.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // GMT时间转成当前时区的时间
        to = simple.format(fromDate);
        return to;
    }

}
