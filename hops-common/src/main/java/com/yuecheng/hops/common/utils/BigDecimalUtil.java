package com.yuecheng.hops.common.utils;


import java.math.BigDecimal;


public class BigDecimalUtil
{
    /*
     * ROUND_CEILING: 舍位时往正无穷方向移动 1.1->2 1.5->2 1.8->2 -1.1->-1 -1.5->-1 -1.8->-1
     * ROUND_DOWN:向0的方向移动1.1->1 1.5->1 1.8->1 -1.1->-1 -1.5->-1 -1.8>-1 
     * ROUND_FLOOR:与CEILING相反，往负无穷 1.1->1 1.5->1 1.8->1 -1.1->-2 -1.5->-2 -1.8->-2 
     * ROUND_HALF_DOWN:以5为分界线，或曰五舍六入1.5->1 1.6->1 -1.5->-1 -1.6->-2 1.15->1.1 1.16->1.2 1.55->1.6 1.56->1.6
     * ROUND_HALF_EVEN:同样以5为分界线，如果是5，则前一位变偶数1.15->1.2 1.16->1.2 1.25->1.2 1.26->1.3
     * ROUND_HALF_UP:最常见的四舍五入 ROUND_UNNECESSARY:无需舍位 ROUND_UP:与ROUND_DOWN，远离0的方向1.1->2 1.5->2
     * 1.8->2 -1.1->-2 -1.5->-2 -1.8->-2
     */

    /*
     * b1与b2相加，留decimal位，按照bigDecimalType规则进位
     */
    public static BigDecimal add(BigDecimal b1, BigDecimal b2, int decimal, int bigDecimalType)
    {
        BigDecimal sumAmt = b1.add(b2);
        sumAmt = sumAmt.setScale(decimal, bigDecimalType);
        return sumAmt;
    }

    public static BigDecimal sub(BigDecimal b1, BigDecimal b2, int decimal, int bigDecimalType)
    {
        BigDecimal subAmt = b1.subtract(b2);
        subAmt = subAmt.setScale(decimal, bigDecimalType);
        return subAmt;
    }

    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2, int decimal, int bigDecimalType)
    {
        BigDecimal multiplyAmt = b1.multiply(b2);
        multiplyAmt = multiplyAmt.setScale(decimal, bigDecimalType);
        return multiplyAmt;
    }

    public static BigDecimal divide(BigDecimal b1, BigDecimal b2, int decimal, int bigDecimalType)
    {
    	BigDecimal divideAmt = b1.divide(b2, decimal, bigDecimalType);
        return divideAmt;
    }
}
