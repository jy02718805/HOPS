package com.yuecheng.hops.report.entity.po;


import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 交易量报表 po
 */
public class TransactionReportPo implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long TRANSACTIONREPORTID;

    private Long MERCHANTID;

    private String MERCHANTNAME;

    private String MERCHANTTYPE;

    private String MERCHANTTYPENAME;

    private String PROVINCE;

    private String PROVINCENAME;

    private String CITY;

    private String CITYNAME;

    private String CARRIERNO;

    private String CARRIERNAME;

    private BigDecimal PARVALUE;

    private BigDecimal TOTALPARVALUE;

    private BigDecimal TOTALSALESFEE;

    private String REPORTSSTATUS;

    private String REPORTSSTATUSNAME;

    private Long TRANSACTIONNUM;

    private String BEGINTIME;

    private String ENDTIME;
    
    private String USERCODE;
    
    private Long BUSINESSTYPE;
    
    private String MERCHANTORDERNO;


	

	public String getMERCHANTORDERNO() {
		return MERCHANTORDERNO;
	}

	public void setMERCHANTORDERNO(String mERCHANTORDERNO) {
		MERCHANTORDERNO = mERCHANTORDERNO;
	}

	public Long getBUSINESSTYPE() {
		return BUSINESSTYPE;
	}

	public void setBUSINESSTYPE(Long bUSINESSTYPE) {
		BUSINESSTYPE = bUSINESSTYPE;
	}

	public String getUSERCODE() {
		return USERCODE;
	}

	public void setUSERCODE(String uSERCODE) {
		USERCODE = uSERCODE;
	}

	public Long getTRANSACTIONREPORTID()
    {
        return TRANSACTIONREPORTID;
    }

    public void setTRANSACTIONREPORTID(Long tRANSACTIONREPORTID)
    {
        TRANSACTIONREPORTID = tRANSACTIONREPORTID;
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

    public BigDecimal getTOTALSALESFEE()
    {
        return TOTALSALESFEE;
    }

    public void setTOTALSALESFEE(BigDecimal tOTALSALESFEE)
    {
        TOTALSALESFEE = tOTALSALESFEE;
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

    public Long getTRANSACTIONNUM()
    {
        return TRANSACTIONNUM;
    }

    public void setTRANSACTIONNUM(Long tRANSACTIONNUM)
    {
        TRANSACTIONNUM = tRANSACTIONNUM;
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
