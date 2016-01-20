package com.yuecheng.hops.report.entity.po;


import java.io.Serializable;
import java.math.BigDecimal;

public class AgentTransactionReportPo implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Long AGENTTRANSACTIONREPORTID;

    private Long MERCHANTID;

    private String MERCHANTNAME;

    private String MERCHANTTYPE;

    private String MERCHANTTYPENAME;

    private String BEGINTIME;

    private String ENDTIME;

    private Long TRANSACTIONNUM;

    private String REPORTSSTATUS;

    private String REPORTSSTATUSNAME;

    private Long PRODUCTID;

    private String PRODUCTNAME;

    private String CARRIERNO;

    private String CARRIERNAME;

    private String PROVINCE;

    private String PROVINCENAME;

    private String CITY;

    private String CITYNAME;

    private BigDecimal TOTALPARVALUE;

    private BigDecimal TOTALSALESFEE;

    private BigDecimal PARVALUE;
    
    private Long BUSINESSTYPE;
    

    public Long getBUSINESSTYPE() {
		return BUSINESSTYPE;
	}

	public void setBUSINESSTYPE(Long bUSINESSTYPE) {
		BUSINESSTYPE = bUSINESSTYPE;
	}

	public Long getAGENTTRANSACTIONREPORTID()
    {
        return AGENTTRANSACTIONREPORTID;
    }

    public void setAGENTTRANSACTIONREPORTID(Long aGENTTRANSACTIONREPORTID)
    {
        AGENTTRANSACTIONREPORTID = aGENTTRANSACTIONREPORTID;
    }

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

    public Long getTRANSACTIONNUM()
    {
        return TRANSACTIONNUM;
    }

    public void setTRANSACTIONNUM(Long tRANSACTIONNUM)
    {
        TRANSACTIONNUM = tRANSACTIONNUM;
    }

    public String getREPORTSSTATUS()
    {
        return REPORTSSTATUS;
    }

    public void setREPORTSSTATUS(String rEPORTSSTATUS)
    {
        REPORTSSTATUS = rEPORTSSTATUS;
    }

    public String getREPORTSSTATUSNAME()
    {
        return REPORTSSTATUSNAME;
    }

    public void setREPORTSSTATUSNAME(String rEPORTSSTATUSNAME)
    {
        REPORTSSTATUSNAME = rEPORTSSTATUSNAME;
    }

    public Long getPRODUCTID()
    {
        return PRODUCTID;
    }

    public void setPRODUCTID(Long pRODUCTID)
    {
        PRODUCTID = pRODUCTID;
    }

    public String getPRODUCTNAME()
    {
        return PRODUCTNAME;
    }

    public void setPRODUCTNAME(String pRODUCTNAME)
    {
        PRODUCTNAME = pRODUCTNAME;
    }

    public String getCARRIERNO()
    {
        return CARRIERNO;
    }

    public void setCARRIERNO(String cARRIERNO)
    {
        CARRIERNO = cARRIERNO;
    }

    public String getCARRIERNAME()
    {
        return CARRIERNAME;
    }

    public void setCARRIERNAME(String cARRIERNAME)
    {
        CARRIERNAME = cARRIERNAME;
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

    public String getCITYNAME()
    {
        return CITYNAME;
    }

    public void setCITYNAME(String cITYNAME)
    {
        CITYNAME = cITYNAME;
    }

    public BigDecimal getTOTALPARVALUE()
    {
        return TOTALPARVALUE;
    }

    public void setTOTALPARVALUE(BigDecimal tOTALPARVALUE)
    {
        TOTALPARVALUE = tOTALPARVALUE;
    }

    public BigDecimal getTOTALSALESFEE()
    {
        return TOTALSALESFEE;
    }

    public void setTOTALSALESFEE(BigDecimal tOTALSALESFEE)
    {
        TOTALSALESFEE = tOTALSALESFEE;
    }

    public BigDecimal getPARVALUE()
    {
        return PARVALUE;
    }

    public void setPARVALUE(BigDecimal pARVALUE)
    {
        PARVALUE = pARVALUE;
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

}
