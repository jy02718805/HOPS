package com.yuecheng.hops.transaction.basic.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;


public class OrderExportVo implements Serializable
{
    public static final long serialVersionUID = -1882068604517443210L;

    private String ORDERNO;

    private String ORDERSTATUS;

    private String MERCHANTORDERNO;

    private String USERCODE;

    private String ORDERTITLE;

    private String ORDERDESC;

    private String BUSINESSTYPE;

    private String BUSINESSNO;

    private BigDecimal ORDERFEE;

    private BigDecimal ORDERSALESFEE;

    private String PRODUCTNO;

    private String PRODUCTNAME;

    private String PRODUCTFACE;

    private String PRODUCTSALEDISCOUNT;

    private String PRODUCTNUM;

    private String ORDERREQUESTTIME;

    private String ORDERFINISHTIME;

    private String EXT1;

    private String EXT2;

    private String EXT3;

    private String EXT4;

    private String ORDERSUCCESSFEE;

    private BigDecimal ORDERWAITFEE;

    private String SUPPLIERNAME;

    private String PROVINCENAME;

    private String CARRIERNAME;

    private String CITYNAME;

    private String DISPLAYVALUE;

    private String PRESUCCESSSTATUS;

    public String getORDERNO()
    {
        return ORDERNO;
    }

    public void setORDERNO(String oRDERNO)
    {
        ORDERNO = oRDERNO;
    }

    public String getORDERSTATUS()
    {
        return ORDERSTATUS;
    }

    public void setORDERSTATUS(String oRDERSTATUS)
    {
        ORDERSTATUS = oRDERSTATUS;
    }

    public String getMERCHANTORDERNO()
    {
        return MERCHANTORDERNO;
    }

    public void setMERCHANTORDERNO(String mERCHANTORDERNO)
    {
        MERCHANTORDERNO = mERCHANTORDERNO;
    }

    public String getUSERCODE()
    {
        return USERCODE;
    }

    public void setUSERCODE(String uSERCODE)
    {
        USERCODE = uSERCODE;
    }

    public String getORDERTITLE()
    {
        return ORDERTITLE;
    }

    public void setORDERTITLE(String oRDERTITLE)
    {
        ORDERTITLE = oRDERTITLE;
    }

    public String getORDERDESC()
    {
        return ORDERDESC;
    }

    public void setORDERDESC(String oRDERDESC)
    {
        ORDERDESC = oRDERDESC;
    }

    public String getBUSINESSTYPE()
    {
        return BUSINESSTYPE;
    }

    public void setBUSINESSTYPE(String bUSINESSTYPE)
    {
        BUSINESSTYPE = bUSINESSTYPE;
    }

    public String getBUSINESSNO()
    {
        return BUSINESSNO;
    }

    public void setBUSINESSNO(String bUSINESSNO)
    {
        BUSINESSNO = bUSINESSNO;
    }

    public String getPRODUCTNO()
    {
        return PRODUCTNO;
    }

    public void setPRODUCTNO(String pRODUCTNO)
    {
        PRODUCTNO = pRODUCTNO;
    }

    public String getPRODUCTNAME()
    {
        return PRODUCTNAME;
    }

    public void setPRODUCTNAME(String pRODUCTNAME)
    {
        PRODUCTNAME = pRODUCTNAME;
    }

    public String getPRODUCTFACE()
    {
        return PRODUCTFACE;
    }

    public void setPRODUCTFACE(String pRODUCTFACE)
    {
        PRODUCTFACE = pRODUCTFACE;
    }

    public String getPRODUCTSALEDISCOUNT()
    {
        return PRODUCTSALEDISCOUNT;
    }

    public void setPRODUCTSALEDISCOUNT(String pRODUCTSALEDISCOUNT)
    {
        PRODUCTSALEDISCOUNT = pRODUCTSALEDISCOUNT;
    }

    public String getPRODUCTNUM()
    {
        return PRODUCTNUM;
    }

    public void setPRODUCTNUM(String pRODUCTNUM)
    {
        PRODUCTNUM = pRODUCTNUM;
    }

    public String getORDERREQUESTTIME()
    {
        return ORDERREQUESTTIME;
    }

    public void setORDERREQUESTTIME(String oRDERREQUESTTIME)
    {
        ORDERREQUESTTIME = oRDERREQUESTTIME;
    }

    public String getORDERFINISHTIME()
    {
        return ORDERFINISHTIME;
    }

    public void setORDERFINISHTIME(String oRDERFINISHTIME)
    {
        ORDERFINISHTIME = oRDERFINISHTIME;
    }

    public String getEXT1()
    {
        return EXT1;
    }

    public void setEXT1(String eXT1)
    {
        EXT1 = eXT1;
    }

    public String getEXT2()
    {
        return EXT2;
    }

    public void setEXT2(String eXT2)
    {
        EXT2 = eXT2;
    }

    public String getEXT3()
    {
        return EXT3;
    }

    public void setEXT3(String eXT3)
    {
        EXT3 = eXT3;
    }

    public String getEXT4()
    {
        return EXT4;
    }

    public void setEXT4(String eXT4)
    {
        EXT4 = eXT4;
    }

    public String getORDERSUCCESSFEE()
    {
        return ORDERSUCCESSFEE;
    }

    public void setORDERSUCCESSFEE(String oRDERSUCCESSFEE)
    {
        ORDERSUCCESSFEE = oRDERSUCCESSFEE;
    }

    public String getSUPPLIERNAME()
    {
        return SUPPLIERNAME;
    }

    public void setSUPPLIERNAME(String sUPPLIERNAME)
    {
        SUPPLIERNAME = sUPPLIERNAME;
    }

    public String getPROVINCENAME()
    {
        return PROVINCENAME;
    }

    public void setPROVINCENAME(String pROVINCENAME)
    {
        PROVINCENAME = pROVINCENAME;
    }

    public String getCARRIERNAME()
    {
        return CARRIERNAME;
    }

    public void setCARRIERNAME(String cARRIERNAME)
    {
        CARRIERNAME = cARRIERNAME;
    }

    public String getCITYNAME()
    {
        return CITYNAME;
    }

    public void setCITYNAME(String cITYNAME)
    {
        CITYNAME = cITYNAME;
    }

    public String getDISPLAYVALUE()
    {
        return DISPLAYVALUE;
    }

    public void setDISPLAYVALUE(String dISPLAYVALUE)
    {
        DISPLAYVALUE = dISPLAYVALUE;
    }

    public BigDecimal getORDERFEE()
    {
        return ORDERFEE;
    }

    public void setORDERFEE(BigDecimal oRDERFEE)
    {
        ORDERFEE = oRDERFEE;
    }

    public BigDecimal getORDERSALESFEE()
    {
        return ORDERSALESFEE;
    }

    public void setORDERSALESFEE(BigDecimal oRDERSALESFEE)
    {
        ORDERSALESFEE = oRDERSALESFEE;
    }

    public BigDecimal getORDERWAITFEE()
    {
        return ORDERWAITFEE;
    }

    public void setORDERWAITFEE(BigDecimal oRDERWAITFEE)
    {
        ORDERWAITFEE = oRDERWAITFEE;
    }

    public String getPRESUCCESSSTATUS()
    {
        return PRESUCCESSSTATUS;
    }

    public void setPRESUCCESSSTATUS(String pRESUCCESSSTATUS)
    {
        PRESUCCESSSTATUS = pRESUCCESSSTATUS;
    }
}
