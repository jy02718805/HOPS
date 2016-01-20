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
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.repository.SecurityTypeDao;
import com.yuecheng.hops.security.repository.impl.jpa.SecurityTypeJpaDao;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * @ClassName: SecurityTypeServiceImpl
 * @Description: TODO
 * @author 肖进
 * @date 2014年9月1日 下午3:24:05
 */
@Service("securityTypeService")
public class SecurityTypeServiceImpl implements SecurityTypeService
{

    @Autowired
    SecurityTypeDao securityTypeDao;

    @Autowired
    SecurityTypeJpaDao securityTypeJpaDao;

    private static final Logger logger = LoggerFactory.getLogger(SecurityTypeServiceImpl.class);

    @Override
    public YcPage<SecurityCredentialType> queryPageSecurityType(String securitytypename,
                                                                String modeltype,
                                                                String encrypttype,
                                                                Long securityruleid, Long status,
                                                                int pageNumber, int pageSize)
    {
        try
        {
            logger.debug("[SecurityTypeServiceImpl: queryPageSecurityType(" + securitytypename
                         + "," + modeltype + "," + encrypttype + "," + securityruleid + ","
                         + status + "," + pageNumber + "," + pageSize + ")]");
            YcPage<SecurityCredentialType> ycPage = securityTypeDao.querySecurityTypeList(
                securitytypename, modeltype, encrypttype, securityruleid, status, pageNumber,
                pageSize);
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("[SecurityTypeServiceImpl: queryPageSecurityType(根据条件分页查询密钥类型列表失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"queryPageSecurityType"};
            ApplicationException ae = new ApplicationException("identity001086", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredentialType addSecurityType(SecurityCredentialType securityType)
    {
        logger.debug("[SecurityTypeServiceImpl: addSecurityType("
                     + (BeanUtils.isNotNull(securityType) ? securityType.toString() : "") + ")]");
        securityType = securityTypeJpaDao.save(securityType);
        logger.debug("[SecurityTypeServiceImpl: addSecurityType("
                     + (BeanUtils.isNotNull(securityType) ? securityType.toString() : "")
                     + ")][返回信息]");
        return securityType;
    }

    @Override
    public SecurityCredentialType editSecurityType(SecurityCredentialType securityType)
    {
        logger.debug("[SecurityTypeServiceImpl: editSecurityType("
                     + (BeanUtils.isNotNull(securityType) ? securityType.toString() : "") + ")]");
        securityType = securityTypeJpaDao.save(securityType);
        logger.debug("[SecurityTypeServiceImpl: editSecurityType("
                     + (BeanUtils.isNotNull(securityType) ? securityType.toString() : "")
                     + ")][返回信息]");
        return securityType;
    }

    @Override
    public void delSecurityType(Long securityTypeId)
    {
        logger.debug("[SecurityTypeServiceImpl: delSecurityType(" + securityTypeId + ")]");
        SecurityCredentialType securityType = securityTypeJpaDao.getSecurityTypeById(securityTypeId);
        securityType.setStatus(2l);
        securityTypeJpaDao.save(securityType);
        logger.debug("[SecurityTypeServiceImpl: delSecurityType(逻辑刪除，状态改为：2)]");
    }

    @Override
    public SecurityCredentialType getSecurityType(Long securityTypeId)
    {
        logger.debug("[SecurityTypeServiceImpl: getSecurityType(" + securityTypeId + ")]");
        SecurityCredentialType securityType = securityTypeJpaDao.getSecurityTypeById(securityTypeId);
        logger.debug("[SecurityTypeServiceImpl: getSecurityType("
                     + (BeanUtils.isNotNull(securityType) ? securityType.toString() : "")
                     + ")][返回信息]");
        return securityType;
    }

    @Override
    public SecurityCredentialType updateSecurityTypeStatus(Long securityTypeId, Long status)
    {
        logger.debug("[SecurityTypeServiceImpl: updateSecurityTypeStatus(" + securityTypeId + ","
                     + status + ")]");
        SecurityCredentialType securityType = securityTypeJpaDao.getSecurityTypeById(securityTypeId);
        securityType.setStatus(status);
        securityType = securityTypeJpaDao.save(securityType);
        logger.debug("[SecurityTypeServiceImpl: updateSecurityTypeStatus("
                     + (BeanUtils.isNotNull(securityType) ? securityType.toString() : "")
                     + ")][返回信息]");
        return securityType;
    }

    @Override
    public List<SecurityCredentialType> querySecurityTypeAll()
    {
        try
        {
            logger.debug("[SecurityTypeServiceImpl: querySecurityTypeAll()]");
            List<SecurityCredentialType> list = securityTypeJpaDao.getSecurityTypeAll();
            logger.debug("[SecurityTypeServiceImpl: querySecurityTypeAll("
                         + (BeanUtils.isNotNull(list) ? Collections3.convertToString(list,
                             Constant.Common.SEPARATOR) : "") + ")][返回信息]");
            return list;
        }
        catch (Exception e)
        {
            logger.error("[SecurityTypeServiceImpl: querySecurityTypeAll(获取所有删除的密钥类型列表失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"querySecurityTypeAll"};
            ApplicationException ae = new ApplicationException("identity001091", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<SecurityCredentialType> querySecurityTypeBystatus(Long status)
    {
        try
        {
            logger.debug("[SecurityTypeServiceImpl: querySecurityTypeBystatus(" + status + ")]");
            List<SecurityCredentialType> list = securityTypeJpaDao.getSecurityTypeBystatus(status);
            logger.debug("[SecurityRuleServiceImpl: querySecurityTypeBystatus("
                         + (BeanUtils.isNotNull(list) ? Collections3.convertToString(list,
                             Constant.Common.SEPARATOR) : "") + ")][返回信息]");
            return list;
        }
        catch (Exception e)
        {
            logger.error("[SecurityTypeServiceImpl: queryPageSecurityType(根据状态获取密钥类型列表失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"queryPageSecurityType"};
            ApplicationException ae = new ApplicationException("identity001092", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredentialType querySecurityTypeByTypeName(String typeName)
    {
        try
        {
            logger.debug("[SecurityTypeServiceImpl: querySecurityTypeByTypeName(" + typeName
                         + ")]");
            SecurityCredentialType securityType = securityTypeJpaDao.getSecurityTypeByTypeName(typeName);
            logger.debug("[SecurityTypeServiceImpl: querySecurityTypeByTypeName("
                         + (BeanUtils.isNotNull(securityType) ? securityType.toString() : "")
                         + ")][返回信息]");
            return securityType;
        }
        catch (Exception e)
        {
            logger.error("[SecurityTypeServiceImpl: querySecurityTypeByTypeName(根据类型名称查询密钥类型失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"querySecurityTypeByTypeName"};
            ApplicationException ae = new ApplicationException("identity001093", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<SecurityCredentialType> querySecurityTypeByRule(Long securityRuleId)
    {
        List<SecurityCredentialType> securityTypes = securityTypeJpaDao.querySecurityTypeByRule(securityRuleId);
        return securityTypes;
    }

}
