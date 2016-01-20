package com.yuecheng.hops.common.message;


import java.util.Locale;

import org.springframework.context.ApplicationContext;

import com.yuecheng.hops.common.utils.SpringUtils;


public class MessageResolver
{
    private static ApplicationContext applicationContext;

    public static String getMessage(String key)
    {
        return getMessage(key, new Object[0], Locale.US);
    }

    public static String getMessage(String key, Object[] params)
    {
        return getMessage(key, params, Locale.US);
    }

    public static String getMessage(String key, Object[] params, Locale locale)
    {
        if (null == applicationContext)
        {
            applicationContext = SpringUtils.getApplicationContext();
        }

        String[] strParams = new String[params.length];
        for (int j = 0; j < params.length; j++ )
        {
            StringBuilder stringBuilder = new StringBuilder();
            strParams[j] = null == params[j] ? null : stringBuilder.append(params[j]).toString();
        }
        return applicationContext.getMessage(key, strParams, null != locale ? locale : Locale.US);
    }
}
