package com.yangh.today.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangH on 2019/3/1.
 */
public class TimeUtils {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    private static Date d;

    /**
     * 转换带时区的时间字符串
     *
     * @param date
     * @return
     */
    public static String getNormalDate(String date) {
        //        String date = "2019-03-01T02:54:25.522Z";
        date = date.replace("Z", " UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String prizeDate = null;
        try {
            //这里用sdf的解析标准先把原始的字串还原. 这里的d就是标准时间了.
            d = sdf.parse(date);
            //这里再用ff输出你想要的形式就可以了.
            //            prizeDate = ff.format(d);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - d.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";

    }


}
