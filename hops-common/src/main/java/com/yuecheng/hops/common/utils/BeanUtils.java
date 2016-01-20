package com.yuecheng.hops.common.utils;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanUtils
{
	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    static
    {
        // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        org.apache.commons.beanutils.ConvertUtils.register(
                new SqlDateConverter(), java.sql.Date.class);
        org.apache.commons.beanutils.ConvertUtils.register(
                new UtilDateConverter(), java.util.Date.class);
        org.apache.commons.beanutils.ConvertUtils.register(
                new UtilBigDecimalConverter(), java.math.BigDecimal.class);
    }

    /**
     * 私有构造方法
     */
    private BeanUtils()
    {

    }

    public static void copyProperties(Object target, Object source)
            throws InvocationTargetException, IllegalAccessException
    {
        // update bu zhuzf at 2004-9-29
        // 支持对日期copy

        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);

    }

    public static boolean isNotNull(Object bean)
    {
        return null != bean;
    }

    public static boolean isNull(Object bean)
    {
        return null == bean;
    }

    public static boolean isNullOrEmpty(Object obj)
    {
        if (obj == null)
        {
            return true;
        }
        else if (obj instanceof String)
        {
            if (null != obj && !obj.toString().isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if (obj instanceof Collection)
        {
            Collection<?> collObj = (Collection<?>)obj;
            return collObj.size() > 0 ? false : true;
        }
        else if (obj instanceof Object[])
        {
            Object[] arrayObj = (Object[])obj;
            return arrayObj.length > 0 ? false : true;
        }
        else
        {
            throw new IllegalArgumentException("object type");
        }
    }

    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj)
    {
        if (map == null || obj == null)
        {
            return;
        }
        try
        {
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        }
        catch (Exception e)
        {
        	logger.error("BeanUtils:[transMap2Bean]["+e.getMessage()+"]");
        }
    }

    public static Object populate(Object obj, Map<String, Object> properties)
    {
        try
        {
            org.apache.commons.beanutils.BeanUtils.populate(obj, properties);
        }
        catch (IllegalAccessException e)
        {
        	logger.error("BeanUtils:[populate]["+e.getMessage()+"]");
        }
        catch (InvocationTargetException e)
        {
        	logger.error("BeanUtils:[populate]["+e.getMessage()+"]");
        }
        return obj;
    }

    public static Map<String, Object> transBean2Map(Object obj)
    {

        if (obj == null)
        {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors)
            {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class"))
                {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        }
        catch (Exception e)
        {
        	logger.error("BeanUtils:[transBean2Map]["+e.getMessage()+"]");
        }

        return map;

    }
}
