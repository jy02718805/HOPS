package com.yuecheng.hops.transaction.basic.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;


public class DeliveryStatisticsVo implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    private BigDecimal PRODUCTFACE;
    
    private BigDecimal ORDERSUCCESSFEE;
    
    private BigDecimal COSTFEE;
    
    private BigDecimal SALESFEE;

    public BigDecimal getSALESFEE()
	{
		return SALESFEE;
	}

	public void setSALESFEE(BigDecimal sALESFEE)
	{
		SALESFEE = sALESFEE;
	}

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

    public BigDecimal getCOSTFEE()
    {
        return COSTFEE;
    }

    public void setCOSTFEE(BigDecimal cOSTFEE)
    {
        COSTFEE = cOSTFEE;
    }
}
