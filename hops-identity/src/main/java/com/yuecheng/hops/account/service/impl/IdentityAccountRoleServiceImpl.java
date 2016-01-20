package com.yuecheng.hops.account.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.convertor.AccountVoConvertor;
import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.SpAccountVo;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.repository.IdentityAccountRoleDao;
import com.yuecheng.hops.account.repository.IdentityAccountRoleJpaDao;
import com.yuecheng.hops.account.service.AccountServiceFinder;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CardAccountService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.customer.Customer;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;


@Service("identityAccountRoleService")
public class IdentityAccountRoleServiceImpl implements IdentityAccountRoleService
{
    private static Logger logger = LoggerFactory.getLogger(IdentityAccountRoleServiceImpl.class);

    @Autowired
    private IdentityAccountRoleJpaDao identityAccountRoleJpaDao;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private AccountDaoFinder accountDaoFinder;

    @Autowired
    private AccountServiceFinder accountServiceFinder;

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private CardAccountService cardAccountService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AccountVoConvertor accountVoConvertor;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IdentityAccountRoleDao identityAccountRoleDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityAccountRoleServiceImpl.class);
    
    


    @Override
    @Transactional
    public IdentityAccountRole saveIdentityAccountRole(Long identityId, String identityType,
                                                       Long accountId, Long accountTypeId,
                                                       String relation, Long transactionNo)
    {
        logger.debug("保存用户账户关系记录 [开始] identityId[" + identityId + "] identityType[" + identityType
                     + "] accountId[" + accountId + "] accountType[" + accountTypeId
                     + "] relation[" + relation + "]");
        if (checkIsExits(accountId, accountTypeId, identityId, identityType, relation))
        {
            String[] msgParams = new String[] {};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("identity000006", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
        String tableName = accountTypeService.chooseTable(accountTypeId, transactionNo);
        IdentityAccountRole iar = new IdentityAccountRole(identityId, identityType, accountId,
            accountTypeId, relation, tableName);
        iar = identityAccountRoleJpaDao.save(iar);
        logger.debug("保存用户账户关系记录 [结束]");
        return iar;
    }

    @Override
    public IdentityAccountRole queryIdentityAccountRoleByParames(Long accountId, String relation)
    {
        IdentityAccountRole iar = identityAccountRoleJpaDao.queryIdentityAccountRoleByParames(
            accountId, relation);
        return iar;
    }

    @Override
    public List<IdentityAccountRole> queryIdentityAccountRoleByAccoutId(Long accoutId)
    {
        // TODO Auto-generated method stub
        List<IdentityAccountRole> identityAccountRoles = identityAccountRoleJpaDao.queryIdentityAccountRoles(accoutId);
        return identityAccountRoles;
    }

    /**
     * 通过identity找账户
     */
    @Override
    public List<Account> queryAccountByIdentity(AbstractIdentity identity,Long transactionNo)
    {
        List<Account> result = null;
        // 1.根据用户查询出 用户账户关系数据
        List<IdentityAccountRole> identityAccountRoles = identityAccountRoleJpaDao.queryIdentityAccountRoleByIdentity(
            identity.getId(), identity.getIdentityType().toString());
        if (identityAccountRoles.size() > 0)
        {
            result = new ArrayList<Account>();
        }
        for (Iterator<IdentityAccountRole> iterator = identityAccountRoles.iterator(); iterator.hasNext();)
        {
            IdentityAccountRole identityAccountRole = iterator.next();
            AccountType accountType = accountTypeService.queryAccountTypeById(Long.valueOf(identityAccountRole.getAccountType()));
            if (accountType.getIdentityType().equalsIgnoreCase(IdentityType.MERCHANT.toString()))
            {
                AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
                Account account = (Account)accountDao.findOne(identityAccountRole.getAccountId());
                account.setAccountType(accountType);
                result.add(account);
            }
        }
        return result;
    }

    @Override
    public YcPage<CCYAccount> queryCurrencyAccountByMerchant(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize)
    {
        YcPage<CCYAccount> currencyAccounts = identityAccountRoleDao.queryCurrencyAccountByMerchant(
            searchParams, pageNumber, pageSize);
        return currencyAccounts;
    }

    @Override
    @Transactional
    public Account saveAccount(AbstractIdentity identity, AccountType accountType,
                               String accountStatus, String rmk, String relation,
                               Long transactionNo)
    {
        try
        {
            logger.debug("保存账户 [开始] identity[" + identity + "] relation[" + relation + "]");
            Account account = null;
            String tableName = accountTypeService.chooseTable(accountType.getAccountTypeId(),
                transactionNo);
            logger.debug("表名："+tableName);
            if (AccountModelType.FUNDS.equals(accountType.getTypeModel()))
            {
                logger.debug("保存现金账户");
                account = ccyAccountService.saveCCYAccount(accountType,
                    accountStatus, rmk, relation, transactionNo);
            }
            else
            {
                logger.debug("保存卡账户");
                account = cardAccountService.saveCardAccount(accountType, accountStatus, rmk,
                    relation);
            }

            if (checkIsExits(account.getAccountId(), accountType.getAccountTypeId(),
                identity.getId(), identity.getIdentityType().toString(), relation))
            {
                String[] msgParams = new String[] {};
                String[] viewParams = new String[] {};
                ApplicationException ae = new ApplicationException("identity000006", msgParams,
                    viewParams);
                throw ExceptionUtil.throwException(ae);
            }

            IdentityAccountRole iar = new IdentityAccountRole(identity.getId(),
                identity.getIdentityType().toString(), account.getAccountId(),
                account.getAccountType().getAccountTypeId(),
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN, tableName);
            iar = identityAccountRoleJpaDao.save(iar);
            logger.debug("保存卡账户  [结束]");
            return account;
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            throw ExceptionUtil.throwException(new ApplicationException("identity101066",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}));
        }

    }

    /**
     * 保存账户时检查用户账户关系是否已经存在 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param accountType
     * @param identity
     * @param relation
     * @return
     * @see
     */
    public boolean checkIsExits(Long accountId, Long accountTypeId, Long identityId,
                                String identityType, String relation)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.IdentityAccountRole.IDENTITY_ID, new SearchFilter(
            EntityConstant.IdentityAccountRole.IDENTITY_ID, Operator.EQ, identityId));
        filters.put(EntityConstant.IdentityAccountRole.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.IdentityAccountRole.IDENTITY_TYPE, Operator.EQ, identityType));
        filters.put(EntityConstant.IdentityAccountRole.ACCOUNT_ID, new SearchFilter(
            EntityConstant.IdentityAccountRole.ACCOUNT_ID, Operator.EQ, accountId));
        filters.put(EntityConstant.IdentityAccountRole.ACCOUNT_TYPE, new SearchFilter(
            EntityConstant.IdentityAccountRole.ACCOUNT_TYPE, Operator.EQ, accountTypeId));
        filters.put(EntityConstant.IdentityAccountRole.RELATION, new SearchFilter(
            EntityConstant.IdentityAccountRole.RELATION, Operator.EQ, relation));
        Specification<IdentityAccountRole> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), IdentityAccountRole.class);
        List<IdentityAccountRole> SHJFOrderLists = identityAccountRoleJpaDao.findAll(spec,
            new Sort(Direction.ASC, EntityConstant.Identity.IDENTITY_ID));

        if (SHJFOrderLists.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public List<IdentityAccountRole> queryIdentityAccountRoleByParams(Long identityId,
                                                                      String identityType,
                                                                      Long accountTypeId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.Identity.IDENTITY_ID, new SearchFilter(
            EntityConstant.Identity.IDENTITY_ID, Operator.EQ, identityId));
        filters.put(EntityConstant.Identity.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.Identity.IDENTITY_TYPE, Operator.EQ, identityType));
        filters.put(EntityConstant.Account.ACCOUNT_TYPE, new SearchFilter(
            EntityConstant.Account.ACCOUNT_TYPE, Operator.EQ, accountTypeId));
        Specification<IdentityAccountRole> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), IdentityAccountRole.class);
        List<IdentityAccountRole> iarList = identityAccountRoleJpaDao.findAll(spec);
        return iarList;
    }

    @Override
    public String queryIdentityNameByAccountId(Long accountId)
    {
        // TODO Auto-generated method stub
        logger.debug("进入获取identityName方法");
        String identityName = "";
        try
        {
            IdentityAccountRole identityAccountRole = queryIdentityAccountRoleByParames(accountId,
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
            if (IdentityType.MERCHANT.toString().equalsIgnoreCase(
                identityAccountRole.getIdentityType()))
            {
                Merchant mt = (Merchant)identityService.findIdentityByIdentityId(
                    identityAccountRole.getIdentityId(),
                    IdentityType.valueOf(identityAccountRole.getIdentityType()));
                identityName = mt.getMerchantName();
            }
            else if (IdentityType.CUSTOMER.toString().equalsIgnoreCase(
                identityAccountRole.getIdentityType()))
            {
                Customer customer = (Customer)identityService.findIdentityByIdentityId(
                    identityAccountRole.getIdentityId(),
                    IdentityType.valueOf(identityAccountRole.getIdentityType()));
                identityName = customer.getCustomerName();
            }
            else if (IdentityType.SP.toString().equalsIgnoreCase(
                identityAccountRole.getIdentityType()))
            {
                SP sp = (SP)identityService.findIdentityByIdentityId(
                    identityAccountRole.getIdentityId(),
                    IdentityType.valueOf(identityAccountRole.getIdentityType()));
                identityName = sp.getSpName();
            }
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityAccountRoleServiceImpl: queryIdentityNameByAccountId(accountId:"
                         + accountId + ")][报错: " + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(new ApplicationException("identity001114",
                new String[] {"queryIdentityNameByAccountId"}));
        }
        return identityName;
    }

    @Override
    public Account getAccountByParams(Long accountTypeId, Long identityId, String identityType,
                                      String relation, Long transactionNo)
    {
        try
        {
            AccountType accountType = accountTypeService.queryAccountTypeById(accountTypeId);
            AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
            String tableName = accountTypeService.chooseTable(accountTypeId, transactionNo);
            IdentityAccountRole identityAccountRole = identityAccountRoleJpaDao.queryIdentityAccountRoleByParames(
                identityId, identityType, accountTypeId, relation, tableName);
            Account account = (Account)accountDao.findOne(identityAccountRole.getAccountId());
            return account;
        }
        catch (Exception e)
        {
            logger.error("fail to getAccountByParams[accountTypeId="+accountTypeId+", identityId="+identityId+", identityType="+identityType+", relation="+relation+", transactionNo="+transactionNo+"]  "+ExceptionUtil.getStackTraceAsString(e));
            return null;
        }
    }
    
    @Override
    public IdentityAccountRole getIdentityAccountRoleByParams(Long accountTypeId, Long identityId, String identityType,
                                      String relation, Long transactionNo)
    {
        try
        {
            AccountType accountType = accountTypeService.queryAccountTypeById(accountTypeId);
            AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
            String tableName = accountTypeService.chooseTable(accountTypeId, transactionNo);
            IdentityAccountRole identityAccountRole = identityAccountRoleJpaDao.queryIdentityAccountRoleByParames(
                identityId, identityType, accountTypeId, relation, tableName);
            Assert.notNull(identityAccountRole);
            return identityAccountRole;
        }
        catch (Exception e)
        {
            logger.error("fail to getAccountByParams[accountTypeId="+accountTypeId+", identityId="+identityId+", identityType="+identityType+", relation="+relation+", transactionNo="+transactionNo+"]  "+ExceptionUtil.getStackTraceAsString(e));
            throw ExceptionUtil.throwException(new ApplicationException("identity101086", new String[]{String.valueOf(accountTypeId).toString(), String.valueOf(identityId).toString(), identityType, relation, String.valueOf(transactionNo).toString()}, e));
        }
    }
    
    public Account getAccountNoCache(Long accountTypeId, Long identityId, String identityType,
                                     String relation, Long transactionNo)
    {
        AccountType accountType = accountTypeService.queryAccountTypeById(accountTypeId);
        AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
        String tableName = accountTypeService.chooseTable(accountTypeId, transactionNo);
        IdentityAccountRole identityAccountRole = identityAccountRoleJpaDao.queryIdentityAccountRoleByParames(
            identityId, identityType, accountTypeId, relation, tableName);
        Account account = (Account)accountDao.findOne(identityAccountRole.getAccountId());
        return account;
    }

    @Override
    public YcPage<SpAccountVo> queryCurrencyAccountBySp(Map<String, Object> searchParams,
                                                        int pageNumber, int pageSize)
    {
        YcPage<SpAccountVo> currencyAccounts = identityAccountRoleDao.queryCurrencyAccountBySp(
            searchParams, pageNumber, pageSize);
        return currencyAccounts;
    }
}
