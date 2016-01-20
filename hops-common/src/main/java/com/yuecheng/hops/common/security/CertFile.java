package com.yuecheng.hops.common.security;


import java.io.File;
import java.io.Serializable;
import java.util.Date;


public class CertFile implements Serializable
{
    private static final long serialVersionUID = 4413125453010141377L;

    private String alias;// 别名

    private int version;// 版本号

    private String serialNumber;// 序列号

    private Date notBefore;// 有效期起始日

    private Date notAfter;// 有效期截至日

    private String sigAlgName;// 签名算法

    private File certFile;

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public Date getNotBefore()
    {
        return notBefore;
    }

    public void setNotBefore(Date notBefore)
    {
        this.notBefore = notBefore;
    }

    public Date getNotAfter()
    {
        return notAfter;
    }

    public void setNotAfter(Date notAfter)
    {
        this.notAfter = notAfter;
    }

    public String getSigAlgName()
    {
        return sigAlgName;
    }

    public void setSigAlgName(String sigAlgName)
    {
        this.sigAlgName = sigAlgName;
    }

    public File getCertFile()
    {
        return certFile;
    }

    public void setCertFile(File certFile)
    {
        this.certFile = certFile;
    }

}