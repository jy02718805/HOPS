package com.yuecheng.hops.report.entity.po;


import java.io.Serializable;
import java.math.BigDecimal;


public class RefundReportPo implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Long REFUNDREPORTID;

    private Long MERCHANTID;

    private String MERCHANTNAME;

    private String MERCHANTTYPE;

    private String MERCHANTTYPENAME;

    private String PROVINCE;

    private String PROVINCENAME;

    private String CITY;

    private String CARRIERNAME;

    private String CARRIERNO;

    private String CITYNAME;

    private BigDecimal PARVALUE;

    private BigDecimal TOTALPARVALUE;

    private BigDecimal REFUNDFACE;

    private BigDecimal COSTFEE;

    private BigDecimal ORDERSALESFEE;

    private BigDecimal REFUNDPROFIT;

    private Long REFUNDNUM;

    private String BEGINTIME;

    private String ENDTIME;

    private String REFUNDORDERNO;

    private String MANUALAUDITDATE;

    private Long BUSINESSTYPE;

    public Long getMERCHANTID()
    {
        return MERCHANTID;
    }

    public void setMERCHANTID(Long mERCHANTID)
    {
        MERCHANTID = mERCHANTID;
    }

    public String getMERCHANTNAME()
    {
        return MERCHANTNAME;
    }

    public void setMERCHANTNAME(String mERCHANTNAME)
    {
        MERCHANTNAME = mERCHANTNAME;
    }

    public String getMERCHANTTYPE()
    {
        return MERCHANTTYPE;
    }

    public void setMERCHANTTYPE(String mERCHANTTYPE)
    {
        MERCHANTTYPE = mERCHANTTYPE;
    }

    public String getMERCHANTTYPENAME()
    {
        return MERCHANTTYPENAME;
    }

    public void setMERCHANTTYPENAME(String mERCHANTTYPENAME)
    {
        MERCHANTTYPENAME = mERCHANTTYPENAME;
    }

    public String getPROVINCE()
    {
        return PROVINCE;
    }

    public void setPROVINCE(String pROVINCE)
    {
        PROVINCE = pROVINCE;
    }

    public String getPROVINCENAME()
    {
        return PROVINCENAME;
    }

    public void setPROVINCENAME(String pROVINCENAME)
    {
        PROVINCENAME = pROVINCENAME;
    }

    public String getCITY()
    {
        return CITY;
    }

    public void setCITY(String cITY)
    {
        CITY = cITY;
    }

    public String getCARRIERNAME()
    {
        return CARRIERNAME;
    }

    public void setCARRIERNAME(String cARRIERNAME)
    {
        CARRIERNAME = cARRIERNAME;
    }

    public String getCARRIERNO()
    {
        return CARRIERNO;
    }

    public void setCARRIERNO(String cARRIERNO)
    {
        CARRIERNO = cARRIERNO;
    }

    public String getCITYNAME()
    {
        return CITYNAME;
    }

    public void setCITYNAME(String cITYNAME)
    {
        CITYNAME = cITYNAME;
    }

    public BigDecimal getPARVALUE()
    {
        return PARVALUE;
    }

    public void setPARVALUE(BigDecimal pARVALUE)
    {
        PARVALUE = pARVALUE;
    }

    public BigDecimal getTOTALPARVALUE()
    {
        return TOTALPARVALUE;
    }

    public void setTOTALPARVALUE(BigDecimal tOTALPARVALUE)
    {
        TOTALPARVALUE = tOTALPARVALUE;
    }

    public BigDecimal getCOSTFEE()
    {
        return COSTFEE;
    }

    public void setCOSTFEE(BigDecimal cOSTFEE)
    {
        COSTFEE = cOSTFEE;
    }

    public BigDecimal getORDERSALESFEE()
    {
        return ORDERSALESFEE;
    }

    public void setORDERSALESFEE(BigDecimal oRDERSALESFEE)
    {
        ORDERSALESFEE = oRDERSALESFEE;
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

    public BigDecimal getREFUNDFACE()
    {
        return REFUNDFACE;
    }

    public void setREFUNDFACE(BigDecimal rEFUNDFACE)
    {
        REFUNDFACE = rEFUNDFACE;
    }

    public Long getREFUNDREPORTID()
    {
        return REFUNDREPORTID;
    }

    public void setREFUNDREPORTID(Long rEFUNDREPORTID)
    {
        REFUNDREPORTID = rEFUNDREPORTID;
    }

    public BigDecimal getREFUNDPROFIT()
    {
        return REFUNDPROFIT;
    }

    public void setREFUNDPROFIT(BigDecimal rEFUNDPROFIT)
    {
        REFUNDPROFIT = rEFUNDPROFIT;
    }

    public Long getREFUNDNUM()
    {
        return REFUNDNUM;
    }

    public void setREFUNDNUM(Long rEFUNDNUM)
    {
        REFUNDNUM = rEFUNDNUM;
    }

    public String getREFUNDORDERNO()
    {
        return REFUNDORDERNO;
    }

    public void setREFUNDORDERNO(String rEFUNDORDERNO)
    {
        REFUNDORDERNO = rEFUNDORDERNO;
    }

    public String getMANUALAUDITDATE()
    {
        return MANUALAUDITDATE;
    }

    public void setMANUALAUDITDATE(String mANUALAUDITDATE)
    {
        MANUALAUDITDATE = mANUALAUDITDATE;
    }

    public Long getBUSINESSTYPE()
    {
        return BUSINESSTYPE;
    }

    public void setBUSINESSTYPE(Long bUSINESSTYPE)
    {
        BUSINESSTYPE = bUSINESSTYPE;
    }
}
