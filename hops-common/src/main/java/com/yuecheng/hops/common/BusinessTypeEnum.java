package com.yuecheng.hops.common;

/**
 * 业务类型枚举类
 * 
 * @author yao
 * @version 2015年6月4日
 * @see BusinessTypeEnum
 * @since
 */
public enum BusinessTypeEnum {
    AIRTIME("话费业务", "0"), FLOW("流量业务", "1");
    private String name;

    private String index;

    private BusinessTypeEnum(String name, String index)
    {
        this.name = name;
        this.index = index;
    }

    public static String getName(String index)
    {
        if (index != null)
        {
            for (BusinessTypeEnum businessType : BusinessTypeEnum.values())
            {
                if (index.equalsIgnoreCase(businessType.getIndex()))
                {
                    return businessType.name;
                }
            }
        }
        return null;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }
}