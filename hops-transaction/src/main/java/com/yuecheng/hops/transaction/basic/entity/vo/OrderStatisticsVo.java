package com.yuecheng.hops.transaction.basic.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;


public class OrderStatisticsVo implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    private BigDecimal PRODUCTFACE;
    
    private BigDecimal ORDERSUCCESSFEE;
    
    private BigDecimal ORDERWAITFEE;
    
    private BigDecimal ORDERFAILFEE;

    private BigDecimal ORDERSALESFEE;
    
    public BigDecimal getPRODUCTFACE()
    {
        return PRODUCTFACE;
    }

    public void setPRODUCTFACE(BigDecimal pRODUCTFACE)
    {
        PRODUCTFACE = pRODUCTFACE;
    }

    public BigDecimal getORDERSUCCESSFEE()
    {
        return ORDERSUCCESSFEE;
    }

    public void setORDERSUCCESSFEE(BigDecimal oRDERSUCCESSFEE)
    {
        ORDERSUCCESSFEE = oRDERSUCCESSFEE;
    }

    public BigDecimal getORDERWAITFEE()
    {
        return ORDERWAITFEE;
    }

    public void setORDERWAITFEE(BigDecimal oRDERWAITFEE)
    {
        ORDERWAITFEE = oRDERWAITFEE;
    }

    public BigDecimal getORDERFAILFEE()
    {
        return ORDERFAILFEE;
    }

    public void setORDERFAILFEE(BigDecimal oRDERFAILFEE)
    {
        ORDERFAILFEE = oRDERFAILFEE;
    }

    public BigDecimal getORDERSALESFEE()
    {
        return ORDERSALESFEE;
    }

    public void setORDERSALESFEE(BigDecimal oRDERSALESFEE)
    {
        ORDERSALESFEE = oRDERSALESFEE;
    }
}
