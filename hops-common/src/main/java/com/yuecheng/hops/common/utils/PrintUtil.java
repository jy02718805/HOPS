/*
 * 文件名：PrintUtil.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年11月17日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.common.utils;


import java.util.Map;


public class PrintUtil
{
    public static String mapToString(Map<String, Object> map)
    {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            sb.append(entry.getKey() + ":" + entry.getValue() + "\t");
        }
        return sb.toString();
    }
}
