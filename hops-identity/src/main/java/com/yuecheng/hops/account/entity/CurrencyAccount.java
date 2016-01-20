package com.yuecheng.hops.account.entity;


import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "ccy_account")
public class CurrencyAccount extends CCYAccount
{

    public static final long serialVersionUID = 8588308864279765131L;

}
