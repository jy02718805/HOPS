package com.yuecheng.hops.account.entity;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;


@MappedSuperclass
public class CCYAccount extends Account
{

    public static final long serialVersionUID = 2832490526079048506L;

    @Column(name = "available_balance")
    private BigDecimal availableBalance; // 可用余额

    @Column(name = "unavailable_banlance")
    private BigDecimal unavailableBanlance; // 不可用余额

    @Column(name = "creditable_banlance")
    private BigDecimal creditableBanlance; // 授信余额

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

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
