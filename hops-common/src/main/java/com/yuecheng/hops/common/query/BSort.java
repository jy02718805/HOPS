package com.yuecheng.hops.common.query;


import java.io.Serializable;
import java.util.Locale;


/**
 * 用于排序的对象
 * 
 * @author xwj
 */
public class BSort implements Serializable
{

    private String cloumn;

    private Direct direct;

    public static enum Direct {
        ASC, DESC;
        public static Direct fromString(String value)
        {

            try
            {
                return Direct.valueOf(value.toUpperCase(Locale.US));
            }
            catch (Exception e)
            {
                throw new IllegalArgumentException(
                    String.format(
                        "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).",
                        value), e);
            }
        }
    }

    public BSort(Direct direct, String column)
    {
        this.direct = direct;
        this.cloumn = column;
    }

    public String getCloumn()
    {
        return cloumn;
    }

    public void setCloumn(String cloumn)
    {
        this.cloumn = cloumn;
    }

    public Direct getDirect()
    {
        return direct;
    }

    public void setDirect(Direct direct)
    {
        this.direct = direct;
    }

}
