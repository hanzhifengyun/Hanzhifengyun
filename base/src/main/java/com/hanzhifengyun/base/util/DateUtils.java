package com.hanzhifengyun.base.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类for ERP时间转换
 */

public class DateUtils {

    public static final String PATTERN_DEFAULT = "yyyy年MM月dd日";
    public static final String PATTERN_DEFAULT_TIME = "yyyy年MM月dd日 HH:mm";
    public static final String PATTERN_SPRIT = "yyyy/MM/dd";
    public static final String PATTERN_TRANSVERSE = "yyyy-MM-dd";
    public static final String PATTERN_TRANSVERSE_TIME = "yyyy-MM-dd HH:mm";




    private static SimpleDateFormat getDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }


    public static String format(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }



    public static String erpTimeToDateStr(String erpTime, String pattern) {
        Date date = erpTimeToDate(erpTime);
        return format(date, pattern);
    }

    /**
     * ERP后台时间格式转换为时间戳
     *
     * @param erpTime ERP后台时间
     * @return Date
     */
    public static Date erpTimeToDate(String erpTime) {
        try {
            if (!TextUtils.isEmpty(erpTime)) {
                long dateLong = Long.parseLong(erpTime.substring(erpTime.indexOf("(") + 1, erpTime.indexOf("+")));
                return new Date(dateLong);
            } else {
                return new Date();
            }
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 获取ERP后台时间格式
     *
     * @param millisecond 毫秒值
     * @return
     */
    public static String dateToErpTime(long millisecond) {
        return "/Date(" + millisecond + "+0800)/";
    }

    /**
     * 获取ERP后台时间格式
     *
     * @param date date
     * @return
     */
    public static String dateToErpTime(Date date) {
        return dateToErpTime(date.getTime());
    }


    /**
     * 获取ERP后台时间格式
     *
     * @param dateStr     yyyy-MM-dd
     * @param pattern     "yyyy-MM-dd"
     * @return   /Date(1491753600000+0800)/
     */
    public static String dateStrToErpTime(String dateStr, String pattern) {
        SimpleDateFormat dateFormat = getDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return dateToErpTime(date);
    }



}
