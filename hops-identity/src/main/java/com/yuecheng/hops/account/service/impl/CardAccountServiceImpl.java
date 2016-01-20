package com.yuecheng.hops.account.service.impl;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CardAccount;
import com.yuecheng.hops.account.entity.vo.SpAccountVo;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.repository.CardAccountDao;
import com.yuecheng.hops.account.service.AccountService;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CardAccountService;
import com.yuecheng.hops.account.utils.CardAccountSignUtil;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;


/**
 * 卡账户服务
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see CardAccountServiceImpl
 * @since
 */
@Service("cardAccountService")
public class CardAccountServiceImpl implements CardAccountService, AccountService ,Serializable
{
    private static final long serialVersionUID = 6578353715537998218L;

    private static Logger logger = LoggerFactory.getLogger(CardAccountServiceImpl.class);

    @Autowired
    private CardAccountDao cardAccountDao;
    
    @Autowired
    private AccountTypeService accountTypeService;
    
    @Autowired
    private transient AccountDaoFinder accountDaoFinder;

    @Override
    public YcPage<CardAccount> queryCardAccount(Map<String, Object> searchParams, int pageNumber,
                                                int pageSize, String sortType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        YcPage<CardAccount> ycPage = PageUtil.queryYcPage(cardAccountDao, filters, pageNumber,
            pageSize, new Sort(Direction.DESC, EntityConstant.CardAccount.STATUS),
            CardAccount.class);
        return ycPage;
    }

    @Override
    public CardAccount queryCardAccountById(Long accountId)
    {
        CardAccount ca = cardAccountDao.findOne(accountId);
        if (ca != null)
        {
            return ca;
        }
        else
        {
            return null;
        }
    }

    @Override
    public CardAccount saveCardAccount(AccountType accountType, String accountStatus, String rmk,
                                       String relation)
    {
        try
        {
            logger.debug("保存卡账户   AccountType" + accountType + "  accountStatus [" + accountStatus
                         + "] rmk[" + rmk + "] relation[" + relation + "] [开始]");
            CardAccount cardAccount = new CardAccount();
            cardAccount.setAccountType(accountType);
            cardAccount.setStatus(accountStatus);
            cardAccount.setRmk(rmk);
            cardAccount.setBalance(new BigDecimal(Constant.Account.ZERO));
            cardAccount.setCardNum(new BigDecimal(Constant.Account.ZERO));
            cardAccount = cardAccountDao.save(cardAccount);
            String signStr = CardAccountSignUtil.getSign(cardAccount);
            cardAccount.setSign(signStr);
            cardAccount = cardAccountDao.save(cardAccount);
            logger.debug("保存卡账户" + cardAccount + "[结束]");
            return cardAccount;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity101068",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e));
        }
    }

    @Override
    public YcPage<CardAccount> queryCardAccountsByIds(final List<Long> ids, int pageNumber,
                                                      int pageSize)
    {
        Page<CardAccount> page = cardAccountDao.findAll(new Specification<CardAccount>()
        {
            public Predicate toPredicate(Root<CardAccount> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb)
            {
                List<Predicate> orPredicates = new ArrayList<Predicate>();
                Path<Long> accountId = root.get(EntityConstant.Account.ACCOUNT_ID);
                Predicate p1 = null;
                for (Long id : ids)
                {
                    p1 = cb.equal(accountId, id);
                    orPredicates.add(cb.or(p1));
                }
                return null;
            }
        }, new PageRequest(pageNumber - 1, pageSize));
        YcPage<CardAccount> ycPage = PageUtil.transPage2YcPage(page);
        return ycPage;
    }

    @Override
    public YcPage<SpAccountVo> queryAccountWithSp(Map<String, Object> searchParams,
                                                      int pageNumber, int pageSize)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean creditAction(Long accountId, Long accountTypeId, BigDecimal amt, Long transactionId,
                          Long transactionNo, String type, String remark)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean debitAction(Long accountId, Long accountTypeId, BigDecimal amt, Long transactionId,
                         Long transactionNo, String type, String remark)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public CardAccount getAccountByParams(Long accountId, Long accountTypeId, Long transactionNo)
    {
        AccountType accountType = accountTypeService.queryAccountTypeById(accountTypeId);
        AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
        CardAccount account = (CardAccount)accountDao.findOne(accountId);
        return account;
    }
}
