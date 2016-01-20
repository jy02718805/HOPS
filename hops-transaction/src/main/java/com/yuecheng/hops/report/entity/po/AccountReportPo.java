package com.yuecheng.hops.report.entity.po;


import java.io.Serializable;
import java.math.BigDecimal;

public class AccountReportPo implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long ACCOUNTREPORTID;

    private Long ACCOUNTID;

    private Long ACCOUNTTYPEID;

    private Long IDENTITYID;

    private String IDENTITYNAME;

    private Long TRANSACTIONTYPE;

    private BigDecimal PREVIOUSBALANCE;

    private BigDecimal PERIODPLUSSECTION;

    private BigDecimal CURRENTEXPENDITURE;

    private BigDecimal PERIODBALANCE;

    private String BEGINTIME;

    private String ENDTIME;

    private Long ACCOUNTADDNUM;

    private Long ACCOUNTEXPENSESNUM;

    private String IDENTITYTYPE;

    private String ACCOUNTTYPENAME;

    private String IDENTITYTYPENAME;
    
    private BigDecimal PERIODUNAVAILABLEBALANCE;

    private BigDecimal PREVIOUSUNAVAILABLEBALANCE;
    
    private BigDecimal PERIODADDAMT;

    public Long getACCOUNTREPORTID()
    {
        return ACCOUNTREPORTID;
    }

    public void setACCOUNTREPORTID(Long aCCOUNTREPORTID)
    {
        ACCOUNTREPORTID = aCCOUNTREPORTID;
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

    public Long getIDENTITYID()
    {
        return IDENTITYID;
    }

    public void setIDENTITYID(Long iDENTITYID)
    {
        IDENTITYID = iDENTITYID;
    }

    public String getIDENTITYNAME()
    {
        return IDENTITYNAME;
    }

    public void setIDENTITYNAME(String iDENTITYNAME)
    {
        IDENTITYNAME = iDENTITYNAME;
    }

    public Long getTRANSACTIONTYPE()
    {
        return TRANSACTIONTYPE;
    }

    public void setTRANSACTIONTYPE(Long tRANSACTIONTYPE)
    {
        TRANSACTIONTYPE = tRANSACTIONTYPE;
    }

    public BigDecimal getPREVIOUSBALANCE()
    {
        return PREVIOUSBALANCE;
    }

    public void setPREVIOUSBALANCE(BigDecimal pREVIOUSBALANCE)
    {
        PREVIOUSBALANCE = pREVIOUSBALANCE;
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

    public BigDecimal getPERIODBALANCE()
    {
        return PERIODBALANCE;
    }

    public void setPERIODBALANCE(BigDecimal pERIODBALANCE)
    {
        PERIODBALANCE = pERIODBALANCE;
    }

    public Long getACCOUNTADDNUM()
    {
        return ACCOUNTADDNUM;
    }

    public void setACCOUNTADDNUM(Long aCCOUNTADDNUM)
    {
        ACCOUNTADDNUM = aCCOUNTADDNUM;
    }

    public Long getACCOUNTEXPENSESNUM()
    {
        return ACCOUNTEXPENSESNUM;
    }

    public void setACCOUNTEXPENSESNUM(Long aCCOUNTEXPENSESNUM)
    {
        ACCOUNTEXPENSESNUM = aCCOUNTEXPENSESNUM;
    }

    public String getIDENTITYTYPE()
    {
        return IDENTITYTYPE;
    }

    public void setIDENTITYTYPE(String iDENTITYTYPE)
    {
        IDENTITYTYPE = iDENTITYTYPE;
    }

    public String getACCOUNTTYPENAME()
    {
        return ACCOUNTTYPENAME;
    }

    public void setACCOUNTTYPENAME(String aCCOUNTTYPENAME)
    {
        ACCOUNTTYPENAME = aCCOUNTTYPENAME;
    }

    public String getIDENTITYTYPENAME()
    {
        return IDENTITYTYPENAME;
    }

    public void setIDENTITYTYPENAME(String iDENTITYTYPENAME)
    {
        IDENTITYTYPENAME = iDENTITYTYPENAME;
    }

    public String getBEGINTIME()
    {
        return BEGINTIME;
    }

    public void setBEGINTIME(String bEGINTIME)
    {
        BEGINTIME = bEGINTIME;
    }

    public String getENDTIME()
    {
        return ENDTIME;
    }

    public void setENDTIME(String eNDTIME)
    {
        ENDTIME = eNDTIME;
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

    public BigDecimal getPERIODADDAMT()
    {
        return PERIODADDAMT;
    }

    public void setPERIODADDAMT(BigDecimal pERIODADDAMT)
    {
        PERIODADDAMT = pERIODADDAMT;
    }

}
