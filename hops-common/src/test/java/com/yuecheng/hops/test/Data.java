/*
 * 文件名：Data.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年11月12日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.test;

public class Data
{
    private String id;

    private String dom;

    private String regno;

    private String entname;

    private String lerep;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDom()
    {
        return dom;
    }

    public void setDom(String dom)
    {
        this.dom = dom;
    }

    public String getRegno()
    {
        return regno;
    }

    public void setRegno(String regno)
    {
        this.regno = regno;
    }

    public String getEntname()
    {
        return entname;
    }

    public void setEntname(String entname)
    {
        this.entname = entname;
    }


    public String getLerep()
    {
        return lerep;
    }

    public void setLerep(String lerep)
    {
        this.lerep = lerep;
    }

    @Override
    public String toString()
    {
        if(lerep.length()==2){
            lerep = lerep + "  ";
        }
        return "regno=" + regno + ", lerep=" + lerep + ", entname=" + entname;
               
    }
    
    static boolean isChinese(char c) {
        boolean result = false;
        if (c >= 19968 && c <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
            result = true;
        }
        return result;
    }
}
