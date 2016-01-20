/*
 * 文件名：SpAccountVo.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年12月18日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.entity.vo;


import java.io.Serializable;


public class SpAccountVo implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    private String ACCOUNTTYPEID;

    private String ACCOUNTTYPENAME;

    private String IDENTITYID;

    private String MERCHANTNAME;

    private String RELATION;

    private String ACCOUNTID;

    private String AVAILABLEBALANCE;

    private String UNAVAILABLEBANLANCE;

    private String CREDITABLEBANLANC;

    private String RMK;

    public String getACCOUNTTYPEID()
    {
        return ACCOUNTTYPEID;
    }

    public void setACCOUNTTYPEID(String aCCOUNTTYPEID)
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

    public String getIDENTITYID()
    {
        return IDENTITYID;
    }

    public void setIDENTITYID(String iDENTITYID)
    {
        IDENTITYID = iDENTITYID;
    }

    public String getMERCHANTNAME()
    {
        return MERCHANTNAME;
    }

    public void setMERCHANTNAME(String mERCHANTNAME)
    {
        MERCHANTNAME = mERCHANTNAME;
    }

    public String getRELATION()
    {
        return RELATION;
    }

    public void setRELATION(String rELATION)
    {
        RELATION = rELATION;
    }

    public String getACCOUNTID()
    {
        return ACCOUNTID;
    }

    public void setACCOUNTID(String aCCOUNTID)
    {
        ACCOUNTID = aCCOUNTID;
    }

    public String getAVAILABLEBALANCE()
    {
        return AVAILABLEBALANCE;
    }

    public void setAVAILABLEBALANCE(String aVAILABLEBALANCE)
    {
        AVAILABLEBALANCE = aVAILABLEBALANCE;
    }

    public String getUNAVAILABLEBANLANCE()
    {
        return UNAVAILABLEBANLANCE;
    }

    public void setUNAVAILABLEBANLANCE(String uNAVAILABLEBANLANCE)
    {
        UNAVAILABLEBANLANCE = uNAVAILABLEBANLANCE;
    }

    public String getCREDITABLEBANLANC()
    {
        return CREDITABLEBANLANC;
    }

    public void setCREDITABLEBANLANC(String cREDITABLEBANLANC)
    {
        CREDITABLEBANLANC = cREDITABLEBANLANC;
    }

    public String getRMK()
    {
        return RMK;
    }

    public void setRMK(String rMK)
    {
        RMK = rMK;
    }

}
