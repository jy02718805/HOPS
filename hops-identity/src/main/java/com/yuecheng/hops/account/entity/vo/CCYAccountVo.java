/*
 * 文件名：CurrencyAccountVo.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;


/**
 * 现金账户VO对象 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see CCYAccountVo
 * @since
 */
public class CCYAccountVo implements Serializable
{
    private static final long serialVersionUID = -5570951647124289984L;

    private Long accountId;

    private String status; // 状态

    private String rmk;

    private String lastUpdateUser;

    private Date lastUpdateDate;

    private String creator;

    private Date createDate;

    private String sign;

    private int version;

    private AccountType accountType;

    private BigDecimal availableBalance; // 可用余额

    private BigDecimal unavailableBanlance; // 不可用余额

    private BigDecimal creditableBanlance; // 授信余额

    private Merchant merchant;

    private SP sp;

    private String relation;

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public AccountType getAccountType()
    {
        return accountType;
    }

    public void setAccountType(AccountType accountType)
    {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance()
    {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance)
    {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getUnavailableBanlance()
    {
        return unavailableBanlance;
    }

    public void setUnavailableBanlance(BigDecimal unavailableBanlance)
    {
        this.unavailableBanlance = unavailableBanlance;
    }

    public BigDecimal getCreditableBanlance()
    {
        return creditableBanlance;
    }

    public void setCreditableBanlance(BigDecimal creditableBanlance)
    {
        this.creditableBanlance = creditableBanlance;
    }

    public Merchant getMerchant()
    {
        return merchant;
    }

    public void setMerchant(Merchant merchant)
    {
        this.merchant = merchant;
    }

    public SP getSp()
    {
        return sp;
    }

    public void setSp(SP sp)
    {
        this.sp = sp;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getRmk()
    {
        return rmk;
    }

    public void setRmk(String rmk)
    {
        this.rmk = rmk;
    }

    public String getLastUpdateUser()
    {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser)
    {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getLastUpdateDate()
    {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getRelation()
    {
        return relation;
    }

    public void setRelation(String relation)
    {
        this.relation = relation;
    }

}
