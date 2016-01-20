/*
 * 文件名：AccountAttribute.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月21日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.entity.bo;


import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 账户统计辅助
 * 
 * @author Administrator
 * @version 2014年10月24日
 * @see AccountBalanceHistoryBo
 * @since
 */
public class AccountBalanceHistoryBo implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    private Long ACCOUNTID;

    private Long ACCOUNTTYPEID;

    private String ACCOUNTTYPENAME;

    // 上期余额
    private BigDecimal PREVIOUSBALANCE;

    // 本期余额
    private BigDecimal PERIODBALANCE;

    // 本期入账
    private BigDecimal PERIODPLUSSECTION;

    // 本期支出
    private BigDecimal CURRENTEXPENDITURE;

    // 本期不可用
    private BigDecimal PERIODUNAVAILABLEBALANCE;

    // 上期不可用
    private BigDecimal PREVIOUSUNAVAILABLEBALANCE;
    
    private BigDecimal CURRENTREVENUE;
    //供货商加款统计
    private BigDecimal PERIODADDAMTPAYER;
    //代理商加款统计
    private BigDecimal PERIODADDAMTPAYEE;

    public BigDecimal getPREVIOUSBALANCE()
    {
        return PREVIOUSBALANCE;
    }

    public void setPREVIOUSBALANCE(BigDecimal pREVIOUSBALANCE)
    {
        PREVIOUSBALANCE = pREVIOUSBALANCE;
    }

    public BigDecimal getPERIODBALANCE()
    {
        return PERIODBALANCE;
    }

    public void setPERIODBALANCE(BigDecimal pERIODBALANCE)
    {
        PERIODBALANCE = pERIODBALANCE;
    }

    public BigDecimal getPERIODPLUSSECTION()
    {
        return PERIODPLUSSECTION;
    }

    public void setPERIODPLUSSECTION(BigDecimal pERIODPLUSSECTION)
    {
        PERIODPLUSSECTION = pERIODPLUSSECTION;
    }

    public BigDecimal getCURRENTEXPENDITURE()
    {
        return CURRENTEXPENDITURE;
    }

    public void setCURRENTEXPENDITURE(BigDecimal cURRENTEXPENDITURE)
    {
        CURRENTEXPENDITURE = cURRENTEXPENDITURE;
    }

    public Long getACCOUNTID()
    {
        return ACCOUNTID;
    }

    public void setACCOUNTID(Long aCCOUNTID)
    {
        ACCOUNTID = aCCOUNTID;
    }

    public Long getACCOUNTTYPEID()
    {
        return ACCOUNTTYPEID;
    }

    public void setACCOUNTTYPEID(Long aCCOUNTTYPEID)
    {
        ACCOUNTTYPEID = aCCOUNTTYPEID;
    }

    public String getACCOUNTTYPENAME()
    {
        return ACCOUNTTYPENAME;
    }

    public void setACCOUNTTYPENAME(String aCCOUNTTYPENAME)
    {
        ACCOUNTTYPENAME = aCCOUNTTYPENAME;
    }

    public BigDecimal getPERIODUNAVAILABLEBALANCE()
    {
        return PERIODUNAVAILABLEBALANCE;
    }

    public void setPERIODUNAVAILABLEBALANCE(BigDecimal pERIODUNAVAILABLEBALANCE)
    {
        PERIODUNAVAILABLEBALANCE = pERIODUNAVAILABLEBALANCE;
    }

    public BigDecimal getPREVIOUSUNAVAILABLEBALANCE()
    {
        return PREVIOUSUNAVAILABLEBALANCE;
    }

    public void setPREVIOUSUNAVAILABLEBALANCE(BigDecimal pREVIOUSUNAVAILABLEBALANCE)
    {
        PREVIOUSUNAVAILABLEBALANCE = pREVIOUSUNAVAILABLEBALANCE;
    }

    public BigDecimal getCURRENTREVENUE()
    {
        return CURRENTREVENUE;
    }

    public void setCURRENTREVENUE(BigDecimal cURRENTREVENUE)
    {
        CURRENTREVENUE = cURRENTREVENUE;
    }

    public BigDecimal getPERIODADDAMTPAYER()
    {
        return PERIODADDAMTPAYER;
    }

    public void setPERIODADDAMTPAYER(BigDecimal pERIODADDAMTPAYER)
    {
        PERIODADDAMTPAYER = pERIODADDAMTPAYER;
    }

    public BigDecimal getPERIODADDAMTPAYEE()
    {
        return PERIODADDAMTPAYEE;
    }

    public void setPERIODADDAMTPAYEE(BigDecimal pERIODADDAMTPAYEE)
    {
        PERIODADDAMTPAYEE = pERIODADDAMTPAYEE;
    }
    
}
