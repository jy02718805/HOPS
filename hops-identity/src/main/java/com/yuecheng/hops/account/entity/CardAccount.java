package com.yuecheng.hops.account.entity;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;


@Entity
@Table(name = "card_account")
public class CardAccount extends Account implements Serializable
{

    public static final long serialVersionUID = -1232610697379338210L;

    @Column(name = "value", length = 30)
    private BigDecimal cardNum; // 卡张数

    @Column(name = "balance", length = 30)
    private BigDecimal balance; // 总价值

    public BigDecimal getCardNum()
    {
        return cardNum;
    }

    public void setCardNum(BigDecimal cardNum)
    {
        this.cardNum = cardNum;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
