/*
 * 文件名：UtilDateConverter.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月13日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * beanUtil转换DATE类型帮助类
 */
public class UtilDateConverter implements Converter
{

    public static final String DATE_PATTERN = "yyyyMMddHHmmss";

	private static final Logger logger = LoggerFactory.getLogger(UtilDateConverter.class);
    /*
     * (non-Javadoc)
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    @Override
    public Object convert(Class arg0, Object arg1)
    {

        Object obj = null;
        if (java.util.Date.class == arg0)
        {
            if (arg1 != null && arg1 instanceof String)
            {
                DateFormat df = new SimpleDateFormat(DATE_PATTERN);

                try
                {
                    obj = df.parse((String)arg1);
                }
                catch (ParseException e)
                {
                	logger.error("UtilDateConverter:[convert-ParseException]["+e.getMessage()+"]");
                }

            }
        }

        return obj;
    }

}
