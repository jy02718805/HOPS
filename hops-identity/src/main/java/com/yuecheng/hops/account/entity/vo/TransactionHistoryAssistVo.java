package com.yuecheng.hops.account.entity.vo;


import java.io.Serializable;
/**
 * 账户交易日志:读取表内容
 * @author Administrator
 * @version 2014年11月14日
 * @see TransactionHistoryAssistVo
 * @since
 */
public class TransactionHistoryAssistVo implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;
    private String TRANSACTIONID;
    private String TRANSACTIONNO;
    private String PAYERACCOUNTID;
    private String PAYERTYPEMODEL;
    private String PAYEEACCOUNTID;
    private String PAYEETYPEMODEL;
    private String DESCSTR;
    private String AMT;
    private String CREATEDATE;
    private String TYPE;
    private String CREATOR;
    private String PAYERIDENTITYNAME;
    private String PAYEEIDENTITYNAME;
    private String PAYERACCOUNTTYPENAME;
    private String PAYEEACCOUNTTYPENAME;
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
    public String getPAYERACCOUNTID()
    {
        return PAYERACCOUNTID;
    }
    public void setPAYERACCOUNTID(String pAYERACCOUNTID)
    {
        PAYERACCOUNTID = pAYERACCOUNTID;
    }
    public String getPAYERTYPEMODEL()
    {
        return PAYERTYPEMODEL;
    }
    public void setPAYERTYPEMODEL(String pAYERTYPEMODEL)
    {
        PAYERTYPEMODEL = pAYERTYPEMODEL;
    }
    public String getPAYEEACCOUNTID()
    {
        return PAYEEACCOUNTID;
    }
    public void setPAYEEACCOUNTID(String pAYEEACCOUNTID)
    {
        PAYEEACCOUNTID = pAYEEACCOUNTID;
    }
    public String getPAYEETYPEMODEL()
    {
        return PAYEETYPEMODEL;
    }
    public void setPAYEETYPEMODEL(String pAYEETYPEMODEL)
    {
        PAYEETYPEMODEL = pAYEETYPEMODEL;
    }
    public String getDESCSTR()
    {
        return DESCSTR;
    }
    public void setDESCSTR(String dESCSTR)
    {
        DESCSTR = dESCSTR;
    }
    public String getAMT()
    {
        return AMT;
    }
    public void setAMT(String aMT)
    {
        AMT = aMT;
    }
    public String getCREATEDATE()
    {
        return CREATEDATE;
    }
    public void setCREATEDATE(String cREATEDATE)
    {
        CREATEDATE = cREATEDATE;
    }
    public String getTYPE()
    {
        return TYPE;
    }
    public void setTYPE(String tYPE)
    {
        TYPE = tYPE;
    }
    public String getCREATOR()
    {
        return CREATOR;
    }
    public void setCREATOR(String cREATOR)
    {
        CREATOR = cREATOR;
    }
    public String getPAYERIDENTITYNAME()
    {
        return PAYERIDENTITYNAME;
    }
    public void setPAYERIDENTITYNAME(String pAYERIDENTITYNAME)
    {
        PAYERIDENTITYNAME = pAYERIDENTITYNAME;
    }
    public String getPAYEEIDENTITYNAME()
    {
        return PAYEEIDENTITYNAME;
    }
    public void setPAYEEIDENTITYNAME(String pAYEEIDENTITYNAME)
    {
        PAYEEIDENTITYNAME = pAYEEIDENTITYNAME;
    }
    public String getPAYERACCOUNTTYPENAME()
    {
        return PAYERACCOUNTTYPENAME;
    }
    public void setPAYERACCOUNTTYPENAME(String pAYERACCOUNTTYPENAME)
    {
        PAYERACCOUNTTYPENAME = pAYERACCOUNTTYPENAME;
    }
    public String getPAYEEACCOUNTTYPENAME()
    {
        return PAYEEACCOUNTTYPENAME;
    }
    public void setPAYEEACCOUNTTYPENAME(String pAYEEACCOUNTTYPENAME)
    {
        PAYEEACCOUNTTYPENAME = pAYEEACCOUNTTYPENAME;
    }
}
