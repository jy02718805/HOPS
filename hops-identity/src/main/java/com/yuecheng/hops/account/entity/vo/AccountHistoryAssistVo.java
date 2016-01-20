package com.yuecheng.hops.account.entity.vo;


import java.io.Serializable;


public class AccountHistoryAssistVo implements Serializable
{

    private static final long serialVersionUID = 1049253401700714611L;

    private String ACCOUNTID;

    private String IDENTITYNAME;

    private String NEWAVAILABLEBALANCE;

    private String NEWUNAVAILABLEBANLANCE;

    private String NEWCREDITABLEBANLANCE;

    private String CHANGEAMOUNT;

    private String CREATEDATE;

    private String DESCSTR;

    private String ACCOUNTTYPENAME;

    private String TRANSACTIONID;

    private String TRANSACTIONNO;

    private String TYPE;

    private String ACCOUNTTYPE;

    private String TRANSACTIONTYPE;

    private String CREATOR;// 操作人

    private String MERCHANTORDERNO;// 商户订单编码

    private String USERCODE;// 手机号

    private String PRODUCTFACE; // 商户面值

    public String PRODUCTNO; // 产品编号

    public String REMARK; 

    public String getACCOUNTID()
    {
        return ACCOUNTID;
    }

    public void setACCOUNTID(String aCCOUNTID)
    {
        ACCOUNTID = aCCOUNTID;
    }

    public String getIDENTITYNAME()
    {
        return IDENTITYNAME;
    }

    public void setIDENTITYNAME(String iDENTITYNAME)
    {
        IDENTITYNAME = iDENTITYNAME;
    }

    public String getNEWAVAILABLEBALANCE()
    {
        return NEWAVAILABLEBALANCE;
    }

    public void setNEWAVAILABLEBALANCE(String nEWAVAILABLEBALANCE)
    {
        NEWAVAILABLEBALANCE = nEWAVAILABLEBALANCE;
    }

    public String getNEWUNAVAILABLEBANLANCE()
    {
        return NEWUNAVAILABLEBANLANCE;
    }

    public void setNEWUNAVAILABLEBANLANCE(String nEWUNAVAILABLEBANLANCE)
    {
        NEWUNAVAILABLEBANLANCE = nEWUNAVAILABLEBANLANCE;
    }

    public String getNEWCREDITABLEBANLANCE()
    {
        return NEWCREDITABLEBANLANCE;
    }

    public void setNEWCREDITABLEBANLANCE(String nEWCREDITABLEBANLANCE)
    {
        NEWCREDITABLEBANLANCE = nEWCREDITABLEBANLANCE;
    }

    public String getCHANGEAMOUNT()
    {
        return CHANGEAMOUNT;
    }

    public void setCHANGEAMOUNT(String cHANGEAMOUNT)
    {
        CHANGEAMOUNT = cHANGEAMOUNT;
    }

    public String getCREATEDATE()
    {
        return CREATEDATE;
    }

    public void setCREATEDATE(String cREATEDATE)
    {
        CREATEDATE = cREATEDATE;
    }

    public String getDESCSTR()
    {
        return DESCSTR;
    }

    public void setDESCSTR(String dESCSTR)
    {
        DESCSTR = dESCSTR;
    }

    public String getACCOUNTTYPENAME()
    {
        return ACCOUNTTYPENAME;
    }

    public void setACCOUNTTYPENAME(String aCCOUNTTYPENAME)
    {
        ACCOUNTTYPENAME = aCCOUNTTYPENAME;
    }

    public String getTRANSACTIONID()
    {
        return TRANSACTIONID;
    }

    public void setTRANSACTIONID(String tRANSACTIONID)
    {
        TRANSACTIONID = tRANSACTIONID;
    }

    public String getTRANSACTIONNO()
    {
        return TRANSACTIONNO;
    }

    public void setTRANSACTIONNO(String tRANSACTIONNO)
    {
        TRANSACTIONNO = tRANSACTIONNO;
    }

    public String getTYPE()
    {
        return TYPE;
    }

    public void setTYPE(String tYPE)
    {
        TYPE = tYPE;
    }

    public String getACCOUNTTYPE()
    {
        return ACCOUNTTYPE;
    }

    public void setACCOUNTTYPE(String aCCOUNTTYPE)
    {
        ACCOUNTTYPE = aCCOUNTTYPE;
    }

    public String getTRANSACTIONTYPE()
    {
        return TRANSACTIONTYPE;
    }

    public void setTRANSACTIONTYPE(String tRANSACTIONTYPE)
    {
        TRANSACTIONTYPE = tRANSACTIONTYPE;
    }

    public String getCREATOR()
    {
        return CREATOR;
    }

    public void setCREATOR(String cREATOR)
    {
        CREATOR = cREATOR;
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

    public String getPRODUCTFACE()
    {
        return PRODUCTFACE;
    }

    public void setPRODUCTFACE(String pRODUCTFACE)
    {
        PRODUCTFACE = pRODUCTFACE;
    }

    public String getPRODUCTNO()
    {
        return PRODUCTNO;
    }

    public void setPRODUCTNO(String pRODUCTNO)
    {
        PRODUCTNO = pRODUCTNO;
    }

    public String getREMARK()
    {
        return REMARK;
    }

    public void setREMARK(String rEMARK)
    {
        REMARK = rEMARK;
    }

    @Override
    public String toString()
    {
        return "AccountHistoryAssistVo [ACCOUNTID=" + ACCOUNTID + ", IDENTITYNAME=" + IDENTITYNAME
               + ", NEWAVAILABLEBALANCE=" + NEWAVAILABLEBALANCE + ", NEWUNAVAILABLEBANLANCE="
               + NEWUNAVAILABLEBANLANCE + ", NEWCREDITABLEBANLANCE=" + NEWCREDITABLEBANLANCE
               + ", CHANGEAMOUNT=" + CHANGEAMOUNT + ", CREATEDATE=" + CREATEDATE + ", DESCSTR="
               + DESCSTR + ", ACCOUNTTYPENAME=" + ACCOUNTTYPENAME + ", TRANSACTIONID="
               + TRANSACTIONID + ", TRANSACTIONNO=" + TRANSACTIONNO + ", TYPE=" + TYPE
               + ", ACCOUNTTYPE=" + ACCOUNTTYPE + ", TRANSACTIONTYPE=" + TRANSACTIONTYPE
               + ", CREATOR=" + CREATOR + ", MERCHANTORDERNO=" + MERCHANTORDERNO + ", USERCODE="
               + USERCODE + ", PRODUCTFACE=" + PRODUCTFACE + ", PRODUCTNO=" + PRODUCTNO
               + ", REMARK=" + REMARK + "]";
    }

    
}
