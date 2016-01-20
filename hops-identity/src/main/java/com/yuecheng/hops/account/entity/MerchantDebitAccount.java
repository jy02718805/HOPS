package com.yuecheng.hops.account.entity;


import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "merchant_debit_account")
public class MerchantDebitAccount extends CCYAccount
{

    public static final long serialVersionUID = 8588308864279765131L;

}
