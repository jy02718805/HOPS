package com.yuecheng.hops.account.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.repository.AccountTypeDao;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.AccountDirectoryType;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;


/**
 * 账户类型服务
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountTypeServiceImpl
 * @since
 */
@Service("accountTypeService")
public class AccountTypeServiceImpl implements AccountTypeService
{
    private static Logger logger = LoggerFactory.getLogger(AccountTypeServiceImpl.class);

    @Autowired
    private AccountTypeDao accountTypeDao;

    @Override
    @Transactional
    public AccountType saveAccountType(AccountType at)
    {
        logger.debug("保存账户类型" + at + "[开始]");
        List<AccountType> accountTypeList = accountTypeDao.queryAccountTypeByName(
            at.getAccountTypeName(), at.getScope(), at.getDirectory().toString());
        if (accountTypeList.isEmpty())
        {
            at = accountTypeDao.save(at);
            logger.debug("保存账户类型" + at + "[结束]");
            return at;
        }
        else
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity000019",
                new String[] {at.toString()}));
        }
    }

    /**
     * 修改账户类型
     */
    @Override
    @Transactional
    public AccountType editAccountType(Long accountTypeId, String accountTypeName,
                                       Long accountTypeStatus, String ccy, String scope,
                                       String type, AccountDirectoryType directory,
                                       String identityType, AccountModelType typeModel)
    {
        try
        {
            logger.debug("更新账户类型[开始]");
            AccountType accountType = accountTypeDao.findOne(accountTypeId);
            accountType.setAccountTypeName(accountTypeName);
            accountType.setAccountTypeStatus(accountTypeStatus);
            accountType.setDirectory(directory);
            accountType.setIdentityType(identityType);
            accountType.setTypeModel(typeModel);
            accountType.setCcy(ccy);
            accountType.setScope(scope);
            accountType.setType(type);
            accountType = accountTypeDao.save(accountType);
            logger.debug("更新账户类型[结束]" + accountType);
            return accountType;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity000001",
                new String[] {accountTypeId.toString()}, e));
        }
    }

    /**
     * 删除账户类型
     */
    @Transactional
    public void deleteAccountType(AccountType accountType)
    {
        try
        {
            logger.debug("删除账户类型[开始]" + accountType);
            accountType.setAccountTypeStatus(Constant.AccountType.DISABLE);
            accountTypeDao.save(accountType);
            logger.debug("删除账户类型[结束]" + accountType);
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity000002",
                new String[] {accountType.toString()}, e));
        }
    }

    @Override
    public List<AccountType> getAllAccountType()
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put(EntityConstant.AccountType.ACCOUNT_TYPE_STATUS, new SearchFilter(
            EntityConstant.AccountType.ACCOUNT_TYPE_STATUS, Operator.EQ,
            Constant.AccountType.ENABLE));
        Specification<AccountType> spec = DynamicSpecifications.bySearchFilter(filters.values(),
            AccountType.class);
        List<AccountType> list = accountTypeDao.findAll(spec);
        return list;
    }

    @Override
    public List<AccountType> getAccountTypeByIdentityType(IdentityType identityType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AccountType.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.AccountType.IDENTITY_TYPE, Operator.LIKE, identityType.toString()));
        Specification<AccountType> spec = DynamicSpecifications.bySearchFilter(filters.values(),
            AccountType.class);
        List<AccountType> list = accountTypeDao.findAll(spec);
        return list;
    }

    @Override
    public AccountType queryAccountTypeById(Long accountTypeId)
    {
        AccountType at = accountTypeDao.findOne(accountTypeId);
        if (at != null)
        {
            return at;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public String chooseTable(Long accountTypeId,Long orderNo)
    {
        AccountType accountType = accountTypeDao.findOne(accountTypeId);
        String tableNameFlag = StringUtil.initString();
        Integer modNum = accountType.getSubNumber();
        if(accountType.getSubFlag().compareTo(0) == 1 && BeanUtils.isNotNull(orderNo))
        {
            //分表
            Long result = orderNo%modNum;
            tableNameFlag = accountType.getTableName()+result;
        }
        else
        {
            //未分表
            tableNameFlag = accountType.getTableName();
        }
        return tableNameFlag;
    }
}
