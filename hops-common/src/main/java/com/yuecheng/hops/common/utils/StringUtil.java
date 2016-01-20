package com.yuecheng.hops.common.utils;


import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;


/**
 * CommonFunction 抽离公共的方法
 * 
 * @author：Jinger
 * @date：2014-07-19
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils
{
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    public static boolean isNullOrEmpty(String str)
    {
        if (null != str && !str.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 字符串反转
     * 
     * @param result
     * @return
     */
    public static String reverse(String result)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(result);
        return sb.reverse().toString();
    }

    /**
     * 初始化字符串
     * 
     * @return
     */
    public static String initString()
    {
        String str = new String();
        return str;
    }

    /**
     * 检查是否有特殊字符
     * 
     * @param string
     * @return
     */
    public static boolean isConSpeCharacters(String string)
    {
        if (string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*\\s*", "").length() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 是否包含字母
     * 
     * @param string
     * @return
     */
    public static boolean isLetter(String string)
    {
        try
        {
            String regex = ".*[a-zA-Z]+.*";
            Matcher m = Pattern.compile(regex).matcher(string);
            return m.matches();
        }
        catch (Exception e)
        {
        	logger.error("StringUtil:[isLetter]["+e.getMessage()+"]");
        }
        return false;
    }

    /**
     * 判断字符串中 字母大写
     * 
     * @param string
     * @return
     */
    public static boolean isUpperCase(String string)
    {
        string = string.replaceAll("[^a-zA-Z]", "");
        if (string.equals(string.toUpperCase()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判断字符串中 字母小写
     * 
     * @param string
     * @return
     */
    public static boolean isLowerCase(String string)
    {
        string = string.replaceAll("[^a-zA-Z]", "");
        if (string.equals(string.toLowerCase()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 是否包含数字
     * 
     * @param string
     * @return
     */
    public static boolean isNumber(String string)
    {
        String regex = "[0-9]+?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string);
        return m.find();
    }

    /**
     * 随机生成字符串
     * 
     * @param length
     * @return
     */
    public static String getRandomString(int length)
    { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++ )
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 转换字符串 NULL->""
     * 
     * @param str
     * @return
     */
    public static String replaceNullToBlank(String str)
    {
        if (isNullOrEmpty(str))
        {
            return initString();
        }
        else
        {
            return str;
        }
    }

    public static String lowerFirst(String str)
    {
        if (StringUtils.isBlank(str))
        {
            return "";
        }
        else
        {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }

    public static String upperFirst(String str)
    {
        if (StringUtils.isBlank(str))
        {
            return "";
        }
        else
        {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html)
    {
        if (isBlank(html))
        {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     * 
     * @param str
     *            目标字符串
     * @param length
     *            截取长度
     * @return
     */
    public static String abbr(String str, int length)
    {
        if (str == null)
        {
            return "";
        }
        try
        {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray())
            {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3)
                {
                    sb.append(c);
                }
                else
                {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        }
        catch (UnsupportedEncodingException e)
        {
        	logger.error("StringUtil:[abbr-UnsupportedEncodingException]["+e.getMessage()+"]");
        }
        return "";
    }

    /**
     * 缩略字符串（替换html）
     * 
     * @param str
     *            目标字符串
     * @param length
     *            截取长度
     * @return
     */
    public static String rabbr(String str, int length)
    {
        return abbr(replaceHtml(str), length);
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val)
    {
        if (val == null)
        {
            return 0D;
        }
        try
        {
            return Double.valueOf(trim(val.toString()));
        }
        catch (Exception e)
        {
        	logger.error("StringUtil:[toDouble]["+e.getMessage()+"]");
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val)
    {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val)
    {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val)
    {
        return toLong(val).intValue();
    }

    /**
     * 获得i18n字符串
     */
    public static String getMessage(String code, Object[] args)
    {
        LocaleResolver localLocaleResolver = SpringUtils.getBean(LocaleResolver.class);
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Locale localLocale = localLocaleResolver.resolveLocale(request);
        return SpringUtils.getApplicationContext().getMessage(code, args, localLocale);
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request)
    {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr))
        {
            remoteAddr = request.getHeader("X-Forwarded-For");
        }
        else if (isNotBlank(remoteAddr))
        {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        else if (isNotBlank(remoteAddr))
        {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }
    
    /**
     * 字符数组转换字符串,用分隔符分开
     * @param strS
     * @param separator
     * @return
     */
    public static String getToString(String[] strS,String separator)
    {
        String result=StringUtil.initString();
        for (String string : strS)
        {
            result=result+string+separator;
        }
        return result;
    }
}
