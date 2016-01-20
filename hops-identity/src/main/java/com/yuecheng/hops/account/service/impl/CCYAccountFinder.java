package com.yuecheng.hops.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.account.entity.MerchantCreditAccount;
import com.yuecheng.hops.account.entity.MerchantDebitAccount;
import com.yuecheng.hops.account.entity.SystemDebitAccount0;
import com.yuecheng.hops.account.entity.SystemDebitAccount1;
import com.yuecheng.hops.account.entity.SystemDebitAccount2;
import com.yuecheng.hops.account.entity.SystemDebitAccount3;
import com.yuecheng.hops.account.entity.SystemDebitAccount4;
import com.yuecheng.hops.account.entity.SystemDebitAccount5;
import com.yuecheng.hops.account.entity.SystemDebitAccount6;
import com.yuecheng.hops.account.entity.SystemDebitAccount7;
import com.yuecheng.hops.account.entity.SystemDebitAccount8;
import com.yuecheng.hops.account.entity.SystemDebitAccount9;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;

@Service("abstractCurrencyAccountFinder")
public class CCYAccountFinder {

	@Autowired
	private AccountTypeService accountTypeService;
	
	public CCYAccount getAccountByAccountType(AccountType accountType,Long transactionNo)
	{
		CCYAccount account = null;
		
		String tableName = accountTypeService.chooseTable(accountType.getAccountTypeId(), transactionNo);
		
		if(tableName.equalsIgnoreCase("ccy_account"))
		{
			account = new CurrencyAccount();
		}
		else if(tableName.equalsIgnoreCase("merchant_credit_account"))
		{
			account = new MerchantCreditAccount();
		}
		else if(tableName.equalsIgnoreCase("merchant_debit_account"))
		{
			account = new MerchantDebitAccount();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account0"))
		{
			account = new SystemDebitAccount0();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account1"))
		{
			account = new SystemDebitAccount1();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account2"))
		{
			account = new SystemDebitAccount2();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account3"))
		{
			account = new SystemDebitAccount3();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account4"))
		{
			account = new SystemDebitAccount4();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account5"))
		{
			account = new SystemDebitAccount5();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account6"))
		{
			account = new SystemDebitAccount6();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account7"))
		{
			account = new SystemDebitAccount7();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account8"))
		{
			account = new SystemDebitAccount8();
		}
		else if(tableName.equalsIgnoreCase("system_debit_account9"))
		{
			account = new SystemDebitAccount9();
		}
		else
        {
            String[] msgParams = new String[] {accountType.getTypeModel().toString()};
            ApplicationException ae = new ApplicationException("identity001116", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
		return account;
	}
}
