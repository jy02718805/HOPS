package com.yuecheng.hops.account.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.SpAccountVo;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.AbstractIdentity;


public interface IdentityAccountRoleService
{

    IdentityAccountRole saveIdentityAccountRole(Long identityId, String identityType,
                                                Long accountId, Long accountType, String relation,
                                                Long tableName);

    IdentityAccountRole queryIdentityAccountRoleByParames(Long accountId, String relation);

    List<IdentityAccountRole> queryIdentityAccountRoleByAccoutId(Long accoutId);

    /**
     * 根据用户查询账户
     * 
     * @param identity
     * @return
     * @see
     */
    public List<Account> queryAccountByIdentity(AbstractIdentity identity, Long transactionNo);

    /**
     * 通过商户查询对应账户信息
     * 
     * @param identity
     * @return
     * @see
     */
    YcPage<CCYAccount> queryCurrencyAccountByMerchant(Map<String, Object> searchParams,
                                                           int pageNumber, int pageSize);

    /**
     * 保存用户账户
     * 
     * @param account
     * @param identity
     * @param relation
     * @return
     * @see
     */
    Account saveAccount(AbstractIdentity identity, AccountType accountType, String accountStatus,
                        String rmk, String relation, Long transactionNo);

    /**
     * Description:根据查询条件获取用户账户关系
     * 
     * @param identityId
     * @param identityType
     * @param accountTypeId
     * @return
     * @see
     */
    List<IdentityAccountRole> queryIdentityAccountRoleByParams(Long identityId,
                                                               String identityType,
                                                               Long accountTypeId);

    /**
     * 根据accountid查询identityName 功能描述: <br> 参数说明: <br>
     */
    String queryIdentityNameByAccountId(Long accountId);

    /**
     * 根据账户类型查询对应账户
     * 
     * @param accountTypeIds
     * @param pageNumber
     * @param pageSize
     * @return
     * @see
     */
    public YcPage<SpAccountVo> queryCurrencyAccountBySp(Map<String, Object> searchParams,
                                                        int pageNumber, int pageSize);

    /**
     * 根据用户ID，用户类型，账户类型，关系 字段，查询对应账户
     * 
     * @param accountTypeId
     * @param identityId
     * @param identityType
     * @param relation
     * @param transactionNo
     * @return
     * @see
     */
    Account getAccountByParams(Long accountTypeId, Long identityId, String identityType,
                               String relation, Long transactionNo);
    
    /**
     * 获取账户信息（不进缓存）
     * 功能描述: <br>
     * 参数说明: <br>
     */
    Account getAccountNoCache(Long accountTypeId, Long identityId, String identityType,
                                     String relation, Long transactionNo);

    /**
     * 交易之前查询账户关系信息
     * @param accountTypeId
     * @param identityId
     * @param identityType
     * @param relation
     * @param transactionNo
     * @return 
     * @see
     */
    public IdentityAccountRole getIdentityAccountRoleByParams(Long accountTypeId, Long identityId, String identityType,
                                                              String relation, Long transactionNo);
}