/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved. Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yuecheng.hops.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.Constant;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author ThinkGem
 * @version 2013-3-15
 */
public class DateUtil extends org.apache.commons.lang.time.DateUtils
{
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate()
    {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern)
    {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern)
    {
        String formatDate = null;
        if (pattern != null && pattern.length > 0)
        {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        }
        else
        {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date)
    {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime()
    {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime()
    {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear()
    {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth()
    {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay()
    {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek()
    {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
     * "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
        	logger.error("DateUtil:[parseDate-ParseException]["+e.getMessage()+"]");
            return null;
        }
    }

    /**
     * 获取过去的天数
     * 
     * @param date
     * @return
     */
    public static long pastDays(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    public static Date getDateStart(Date date)
    {
        if (date == null)
        {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
        }
        catch (ParseException e)
        {
        	logger.error("DateUtil:[getDateStart-ParseException]["+e.getMessage()+"]");
        }
        return date;
    }

    public static Date getDateEnd(Date date)
    {
        if (date == null)
        {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
        }
        catch (ParseException e)
        {
        	logger.error("DateUtil:[getDateEnd-ParseException]["+e.getMessage()+"]");
        }
        return date;
    }

    public static Date addTime(String unit, int time)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (unit.equals(Constant.DateUnit.TIME_UNIT_SECOND))
        {
            calendar.add(Calendar.SECOND, time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_MINUTE))
        {
            calendar.add(Calendar.MINUTE, time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_HOUR))
        {
            calendar.add(Calendar.HOUR, time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_DAY))
        {
            calendar.add(Calendar.DAY_OF_MONTH, time);
        }
        return calendar.getTime();
    }

    public static Date subTime(String unit, int time)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (unit.equals(Constant.DateUnit.TIME_UNIT_SECOND))
        {
            calendar.add(Calendar.SECOND, -time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_MINUTE))
        {
            calendar.add(Calendar.MINUTE, -time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_HOUR))
        {
            calendar.add(Calendar.HOUR, -time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_DAY))
        {
            calendar.add(Calendar.DAY_OF_MONTH, -time);
        }
        return calendar.getTime();
    }

    public static String dateToString(Date date)
    {
        return DateFormat.getInstance().format(date);
    }

    public static Date addDate(String unit, int time, Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (unit.equals(Constant.DateUnit.TIME_UNIT_SECOND))
        {
            calendar.add(Calendar.SECOND, time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_MINUTE))
        {
            calendar.add(Calendar.MINUTE, time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_HOUR))
        {
            calendar.add(Calendar.HOUR, time);
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_DAY))
        {
            calendar.add(Calendar.DAY_OF_MONTH, time);
        }
        return calendar.getTime();
    }

    public static Date getYesterdayBeginTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date)currentDate.getTime().clone();
    }

    public static Date getYesterdayEndTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return (Date)currentDate.getTime().clone();
    }

    /**
     * 根据月份获取季度所对应的月份值
     * 
     * @param month
     * @param isQuarterStart
     * @return
     */
    public static int getQuarterInMonth(int month, boolean isQuarterStart)
    {
        int months[] = {1, 4, 7, 10};
        if (!isQuarterStart)
        {
            months = new int[] {3, 6, 9, 12};
        }
        if (month >= 1 && month <= 3)
        {
            return months[0];
        }
        else if (month >= 4 && month <= 6)
        {
            return months[1];
        }
        else if (month >= 7 && month <= 9)
        {
            return months[2];
        }
        else
        {
            return months[3];
        }
    }

    /**
     * 根据月份获取季度数
     * 
     * @param month
     * @return
     */
    public static int getQuarterInMonth(int month)
    {
        int months[] = {1, 2, 3, 4};
        if (month >= 1 && month <= 3)
        {
            return months[0];
        }
        else if (month >= 4 && month <= 6)
        {
            return months[1];
        }
        else if (month >= 7 && month <= 9)
        {
            return months[2];
        }
        else
        {
            return months[3];
        }
    }

    /**
     * 获取当月季度第一天的日期
     * 
     * @param date
     * @return
     */
    public static Date getYearFirstDay(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beginTime = calendar.getTime();
        return beginTime;
    }

    /**
     * 获取当月季度第一天的日期
     * 
     * @param date
     * @return
     */
    public static Date getYearLastDay(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endTime = calendar.getTime();
        return endTime;
    }

    /**
     * 获取当月季度第一天的日期
     * 
     * @param date
     * @return
     */
    public static Date getQuarterFirstDay(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int month = getQuarterInMonth(calendar.get(Calendar.MONTH), true);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beginTime = calendar.getTime();
        return beginTime;
    }

    /**
     * 获取当月季度第一天的日期
     * 
     * @param date
     * @return
     */
    public static Date getQuarterLastDay(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int month = getQuarterInMonth(calendar.get(Calendar.MONTH), false);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endTime = calendar.getTime();
        return endTime;
    }

    /**
     * 获取第一天的日期
     * 
     * @param date
     * @return
     */
    public static String getFirstDay(Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        // 上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar)Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        return day_first;
    }

    /**
     * 获取最后一天的日期
     * 
     * @param date
     * @return
     */
    public static String getLastDay(Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 上个月最后一天
        // calendar.add(Calendar.MONTH, 1); //加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        return day_last;
    }

    /**
     * 获取一天最早的时间点
     * 
     * @return
     */
    public static Date getFirstTime()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date beginTime = calendar.getTime();
        return beginTime;
    }

    /**
     * 获取一点最晚的时间点
     * 
     * @return
     */
    public static Date getLastTime()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date endTime = calendar.getTime();
        return endTime;
    }

    public String getFirstMonthDay()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        String dateStr = dateFormat.format(cal.getTime());
        return dateStr;
    }

    public String getLastMonthDay()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        String dateStr = dateFormat.format(cal.getTime());
        return dateStr;
    }

    public static int formatUnit(String unit, int time)
    {
        if (unit.equals(Constant.DateUnit.TIME_UNIT_SECOND))
        {
            return time;
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_MINUTE))
        {
            return time * 60;
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_HOUR))
        {
            return time * 60 * 60;
        }
        else if (unit.equals(Constant.DateUnit.TIME_UNIT_DAY))
        {
            return time * 60 * 60 * 24;
        }
        return time;
    }

    public static String toDateMinute(Date d)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE,-10);
        d = calendar.getTime();
        return formatDateTime(d);
    }
    
    public static int daysBetween(Date date1,Date date2)  
    {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date1);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(date2);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);
          
       return Math.abs(Integer.parseInt(String.valueOf(between_days)));
    }
}
