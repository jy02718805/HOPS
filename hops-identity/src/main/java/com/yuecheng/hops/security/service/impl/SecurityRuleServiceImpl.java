package com.yuecheng.hops.security.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;
import com.yuecheng.hops.security.repository.SecurityRuleDao;
import com.yuecheng.hops.security.repository.impl.jpa.SecurityRuleJpaDao;
import com.yuecheng.hops.security.repository.impl.sql.SecurityRuleSqlDao;
import com.yuecheng.hops.security.service.SecurityRuleService;


/**
 * @ClassName: SecurityRuleService
 * @Description: TODO
 * @author 肖进
 * @date 2014年9月1日 下午3:24:05
 */
@Service("securityRuleservice")
public class SecurityRuleServiceImpl implements SecurityRuleService
{

    @Autowired
    private SecurityRuleDao securityRuleDao = new SecurityRuleSqlDao();

    @Autowired
    private SecurityRuleJpaDao securityRuleJpaDao;

    private static final Logger logger = LoggerFactory.getLogger(SecurityRuleServiceImpl.class);

    @Override
    public YcPage<SecurityCredentialRule> queryPageSecurityRule(String securityrulename,
                                                                String letter, String figure,
                                                                String specialcharacter,
                                                                Long status, int pageNumber,
                                                                int pageSize)
    {
        try
        {
            logger.debug("[SecurityRuleServiceImpl: queryPageSecurityRule(" + securityrulename
                         + "," + letter + "," + figure + "," + specialcharacter + "," + status
                         + "," + pageNumber + "," + pageSize + ")]");
            YcPage<SecurityCredentialRule> ycPage = securityRuleDao.querySecurityRuleList(
                securityrulename, letter, figure, specialcharacter, status, pageNumber, pageSize);
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("[SecurityRuleServiceImpl: queryPageSecurityRule(根据条件分页查询密钥规则列表失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"queryPageSecurityRule"};
            ApplicationException ae = new ApplicationException("identity001086", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredentialRule addSecurityRule(SecurityCredentialRule securityRule)
    {
        logger.debug("[SecurityRuleServiceImpl: addSecurityRule("
                     + (BeanUtils.isNotNull(securityRule) ? securityRule.toString() : "") + ")]");
        securityRule = securityRuleJpaDao.save(securityRule);
        logger.debug("[SecurityRuleServiceImpl: addSecurityRule("
                     + (BeanUtils.isNotNull(securityRule) ? securityRule.toString() : "")
                     + ")][返回信息]");
        return securityRule;
    }

    @Override
    public SecurityCredentialRule editSecurityRule(SecurityCredentialRule securityRule)
    {
        logger.debug("[SecurityRuleServiceImpl: editSecurityRule("
                     + (BeanUtils.isNotNull(securityRule) ? securityRule.toString() : "") + ")]");
        securityRule = securityRuleJpaDao.save(securityRule);
        logger.debug("[SecurityRuleServiceImpl: addSecurityRule("
                     + (BeanUtils.isNotNull(securityRule) ? securityRule.toString() : "")
                     + ")][返回信息]");
        return securityRule;
    }

    @Override
    public void delSecurityRule(Long securityruleid)
    {
        logger.debug("[SecurityRuleServiceImpl: editSecurityRule(" + securityruleid + ")]");
        SecurityCredentialRule securityRule = securityRuleJpaDao.getSecurityRuleById(
            securityruleid, 2l);
        securityRule.setStatus(2l);
        securityRuleJpaDao.save(securityRule);
        logger.debug("[SecurityRuleServiceImpl: editSecurityRule(逻辑刪除，状态改为：2)]");
    }

    @Override
    public SecurityCredentialRule querySecurityRuleById(Long securityruleid)
    {
        logger.debug("[SecurityRuleServiceImpl: querySecurityRuleById(" + securityruleid + ")]");
        SecurityCredentialRule securityRule = securityRuleJpaDao.getSecurityRuleById(
            securityruleid, 2l);
        logger.debug("[SecurityRuleServiceImpl: querySecurityRuleById("
                     + (BeanUtils.isNotNull(securityRule) ? securityRule.toString() : "")
                     + ")][返回信息]");
        return securityRule;
    }

    @Override
    public SecurityCredentialRule updateSecurityRuleStatus(Long securityruleid, Long status)
    {
        try
        {
            logger.debug("[SecurityRuleServiceImpl: updateSecurityRuleStatus(" + securityruleid
                         + "," + status + ")]");
            SecurityCredentialRule securityRule = securityRuleJpaDao.getSecurityRuleById(
                securityruleid, 2l);
            securityRule.setStatus(status);
            securityRule = securityRuleJpaDao.save(securityRule);
            logger.debug("[SecurityRuleServiceImpl: updateSecurityRuleStatus("
                         + (BeanUtils.isNotNull(securityRule) ? securityRule.toString() : "")
                         + ")][返回信息]");
            return securityRule;
        }
        catch (Exception e)
        {
            logger.error("[SecurityRuleServiceImpl: updateSecurityRuleStatus(根据ID和状态修改密钥规则失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"updateSecurityRuleStatus"};
            ApplicationException ae = new ApplicationException("identity001087", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<SecurityCredentialRule> queryAllDelStatusSecurityRule()
    {
        try
        {
            logger.debug("[SecurityRuleServiceImpl: queryAllDelStatusSecurityRule()]");
            List<SecurityCredentialRule> list = securityRuleJpaDao.getSecurityRuleAll(2l);
            logger.debug("[SecurityRuleServiceImpl: queryAllDelStatusSecurityRule("
                         + (BeanUtils.isNotNull(list) ? Collections3.convertToString(list,
                             Constant.Common.SEPARATOR) : "") + ")][返回信息]");
            return list;
        }
        catch (Exception e)
        {
            logger.error("[SecurityRuleServiceImpl: querySecurityRuleByKeyValue(获取所有删除的密钥规则列表失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"querySecurityRuleByKeyValue"};
            ApplicationException ae = new ApplicationException("identity001089", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredentialRule getSecurityRuleByName(String ruleName)
    {
        try
        {
            logger.debug("[SecurityTypeServiceImpl: querySecurityTypeByTypeName(" + ruleName
                         + ")]");
            SecurityCredentialRule securityCredentialRule = securityRuleJpaDao.getSecurityRuleByName(ruleName);
            logger.debug("[SecurityRuleServiceImpl: querySecurityTypeByTypeName("
                         + (BeanUtils.isNotNull(securityCredentialRule) ? securityCredentialRule.toString() : "")
                         + ")][返回信息]");
            return securityCredentialRule;
        }
        catch (Exception e)
        {
            logger.error("[SecurityRuleServiceImpl: getSecurityRuleByName(根据规则名称查询密钥规则失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"getSecurityRuleByName"};
            ApplicationException ae = new ApplicationException("identity101221", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
