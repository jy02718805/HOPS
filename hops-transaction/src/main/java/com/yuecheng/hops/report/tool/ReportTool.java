package com.yuecheng.hops.report.tool;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;


/**
 * 报表的工具类
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ReportTool
 * @since
 */
public class ReportTool
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportTool.class);

    public static Date getBeginTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date)currentDate.getTime().clone();
    }

    public static Date getEndTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return (Date)currentDate.getTime().clone();
    }

    public static String getBeginTimeStr()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(getBeginTime());
    }

    public static String getEndTimeStr()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(getEndTime());
    }

    public static String setOrderStatusList(Long status)
    {
        // if (status == Constant.OrderStatus.WAIT_PAY)
        // {
        // return "待付款";
        // }
        // else if (status == Constant.OrderStatus.WAIT_RECHARGE)
        // {
        // return "待发货";
        // }
        // else if (status == Constant.OrderStatus.RECHARGING)
        // {
        // return "发货中";
        // }
        // else

        if (status == Constant.OrderStatus.SUCCESS)
        {
            return "成功";
        }
        else if (status == Constant.OrderStatus.FAILURE_ALL)
        {
            return "失败";
        }
        else
        {
            return "未知";
        }
    }

    public static String setDeliveryStatus(Long status)
    {
        if (status == Constant.Delivery.DELIVERY_STATUS_SENDING)
        {
            return "等待发货";
        }
        if (status == Constant.Delivery.DELIVERY_STATUS_SENDING)
        {
            return "发货采购中";
        }
        if (status == Constant.Delivery.DELIVERY_STATUS_SENDED)
        {
            return "已经发货";
        }
        if (status == Constant.Delivery.DELIVERY_STATUS_SUCCESS)
        {
            return "发货成功";
        }
        if (status == Constant.Delivery.DELIVERY_STATUS_FAIL)
        {
            return "发货失败";
        }
        return "请检查发货状态";
    }

    public static Date getPlusTime(Date date)
    {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        currentDate.add(Calendar.DATE, 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date)currentDate.getTime().clone();
    }

    /**
     * 应用在查询统计报表中
     * 
     * @param object
     * @return
     * @see
     */
    public static boolean isNotNull(Object object)
    {
        if (BeanUtils.isNotNull(object) && StringUtil.isNotBlank(object + ""))
        {
            return true;
        }
        return false;
    }

    public static String getFormatDate(String time)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            time = format.format(format.parse(time));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            LOGGER.error("[ReportTool. getFormatDate(time:" + time + ")][异常: " + e.getMessage()
                         + "]");
        }
        return time;
    }
}
