package com.yuecheng.hops.security.entity.vo;

import java.io.Serializable;
public class SecurityCredentialVo implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;
    private String SECURITYID;
    private String SECURITYNAME;
    private String IDENTITYTYPE;
    private String SECURITYTYPENAME;
    private String VALIDITYDATE;
    private String STATUS;
    private String CREATEDATE;
    private String UPDATEDATE;
    private String UPDATEUSER;
    private String IDENTITYNAME;
    
    public String getSECURITYID()
    {
        return SECURITYID;
    }
    public void setSECURITYID(String sECURITYID)
    {
        SECURITYID = sECURITYID;
    }
    public String getSECURITYNAME()
    {
        return SECURITYNAME;
    }
    public void setSECURITYNAME(String sECURITYNAME)
    {
        SECURITYNAME = sECURITYNAME;
    }
    public String getIDENTITYTYPE()
    {
        return IDENTITYTYPE;
    }
    public void setIDENTITYTYPE(String iDENTITYTYPE)
    {
        IDENTITYTYPE = iDENTITYTYPE;
    }
    public String getSECURITYTYPENAME()
    {
        return SECURITYTYPENAME;
    }
    public void setSECURITYTYPENAME(String sECURITYTYPENAME)
    {
        SECURITYTYPENAME = sECURITYTYPENAME;
    }
    public String getVALIDITYDATE()
    {
        return VALIDITYDATE;
    }
    public void setVALIDITYDATE(String vALIDITYDATE)
    {
        VALIDITYDATE = vALIDITYDATE;
    }
    public String getSTATUS()
    {
        return STATUS;
    }
    public void setSTATUS(String sTATUS)
    {
        STATUS = sTATUS;
    }
    public String getCREATEDATE()
    {
        return CREATEDATE;
    }
    public void setCREATEDATE(String cREATEDATE)
    {
        CREATEDATE = cREATEDATE;
    }
    public String getUPDATEDATE()
    {
        return UPDATEDATE;
    }
    public void setUPDATEDATE(String uPDATEDATE)
    {
        UPDATEDATE = uPDATEDATE;
    }
    public String getUPDATEUSER()
    {
        return UPDATEUSER;
    }
    public void setUPDATEUSER(String uPDATEUSER)
    {
        UPDATEUSER = uPDATEUSER;
    }
    public String getIDENTITYNAME()
    {
        return IDENTITYNAME;
    }
    public void setIDENTITYNAME(String iDENTITYNAME)
    {
        IDENTITYNAME = iDENTITYNAME;
    }
    
}
