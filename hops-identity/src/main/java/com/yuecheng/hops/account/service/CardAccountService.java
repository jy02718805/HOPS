package com.yuecheng.hops.account.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CardAccount;
import com.yuecheng.hops.account.entity.vo.SpAccountVo;
import com.yuecheng.hops.common.query.YcPage;


public interface CardAccountService extends AccountService
{
    /**
     * 查询实体卡账户列表
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    public YcPage<CardAccount> queryCardAccount(Map<String, Object> searchParams, int pageNumber,
                                                int pageSize, String sortType);

    /**
     * 根据主键查询实体卡账户信息
     * 
     * @return
     */
    public CardAccount queryCardAccountById(Long accountId);

    /**
     * 保存账户
     * 
     * @param ac
     * @return
     */
    public CardAccount saveCardAccount(AccountType accountType, String accountStatus, String rmk,
                                       String relation);

    /**
     * Description:根据IDs查询对应的卡账户组
     * 
     * @param ids
     * @param pageNumber
     * @param pageSize
     * @return
     * @see
     */
    public YcPage<CardAccount> queryCardAccountsByIds(final List<Long> ids, int pageNumber,
                                                      int pageSize);
    
    
    /**
     * 根据账户类型查询对应账户
     * @param accountTypeIds
     * @param pageNumber
     * @param pageSize
     * @return 
     * @see
     */
    public YcPage<SpAccountVo> queryAccountWithSp(Map<String, Object> searchParams, int pageNumber, int pageSize);
}
