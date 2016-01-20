package com.yuecheng.hops.account.service;


import java.util.List;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.common.enump.AccountDirectoryType;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.enump.IdentityType;


public interface AccountTypeService
{
    /**
     * 保存账户类型
     * 
     * @param at
     * @return
     */
    public AccountType saveAccountType(AccountType at);

    /**
     * 更新账户类型
     * 
     * @param at
     * @return
     */
    public AccountType editAccountType(Long accountTypeId, String accountTypeName,
                                       Long accountTypeStatus, String ccy, String scope,
                                       String type, AccountDirectoryType directory,
                                       String identityType, AccountModelType typeModel);

    /**
     * 删除账户类型
     * 
     * @param at
     * @return
     */
    public void deleteAccountType(AccountType at);

    /**
     * 获得全部的账户类型
     * 
     * @return
     */
    public List<AccountType> getAllAccountType();

    /**
     * 获取账户类型
     * 
     * @return
     */
    public AccountType queryAccountTypeById(Long accountTypeId);

    /**
     * 根据identityType来获取账户类型
     */
    public List<AccountType> getAccountTypeByIdentityType(IdentityType identityType);
    
    /**
     * 根据AccountType，确认具体表
     * 
     * @param accountTypeId
     * @param orderNo
     * @return 
     * @see
     */
    public String chooseTable(Long accountTypeId,Long orderNo);
}
