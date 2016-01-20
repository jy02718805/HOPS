package com.yuecheng.hops.security.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialStatusTransfer;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.entity.vo.SecurityCredentialVo;
import com.yuecheng.hops.security.repository.SecurityCredentialDao;
import com.yuecheng.hops.security.repository.impl.jpa.SecurityCredentialJpaDao;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityCredentialStatusTransferService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * 密匙表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-26
 */

@Service("securityCredentialService")
public class SecurityCredentialServiceImpl implements SecurityCredentialService
{

    @Autowired
    private SecurityCredentialJpaDao securityCredentialJpaDao;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SecurityCredentialStatusTransferService securityCredentialStatusTransferService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityCredentialDao securityCredentialDao;

    private static Logger logger = LoggerFactory.getLogger(SecurityCredentialServiceImpl.class);

    /**
     * 添加密匙
     */
    @Override
    public SecurityCredential saveSecurityCredential(SecurityCredential securityCredential)
    {
        logger.debug("[SecurityCredentialServiceImpl:saveSecurityCredential("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")]");
        securityCredential = securityCredentialJpaDao.save(securityCredential);
        logger.debug("[SecurityCredentialServiceImpl:saveSecurityCredential("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        return securityCredential;
    }

    /**
     * 删除密匙
     */
    @Override
    public void deleteSecurityCredential(Long securityCredentialId)
    {
        logger.debug("[SecurityCredentialServiceImpl:delete(" + securityCredentialId + ")]");
        // securityCredentialJpaDao.delete(securityCredentialId);
        try
        {
            SecurityCredential securityCredential = querySecurityCredentialById(securityCredentialId);
            SecurityCredentialStatusTransfer securityCredentialStatusTransfer = securityCredentialStatusTransferService.querySecurityCredentialStatusTransferByParams(
                securityCredential.getStatus(), Constant.SecurityCredentialStatus.DELETE_STATUS);
            if (null != securityCredentialStatusTransfer)
            {
                securityCredential.setStatus(Constant.SecurityCredentialStatus.DELETE_STATUS);
                securityCredential.setUpdateDate(new Date());
                int updateStatus = securityCredentialJpaDao.updateSecurityCredentialStatus(
                    Constant.SecurityCredentialStatus.DELETE_STATUS,
                    securityCredential.getSecurityId());
                if (updateStatus == 0)
                {
                    logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus("
                                 + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                                 + ")][返回信息]");
                    ApplicationException ae = new ApplicationException("identity101222");
                    throw ExceptionUtil.throwException(ae);
                }
            }
            else
            {
                logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus(修改密钥状态机信息失败，状态不能进行转换)]");
                ApplicationException ae = new ApplicationException("identity001108");
                throw ExceptionUtil.throwException(ae);
            }
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            ApplicationException ae = new ApplicationException("identity001109");
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredential updateSecurityCredential(SecurityCredential securityCredential)
    {
        logger.debug("[SecurityCredentialServiceImpl:updateSecurityCredential("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")]");
        securityCredential = securityCredentialJpaDao.save(securityCredential);
        logger.debug("[SecurityCredentialServiceImpl:updateSecurityCredential("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        return securityCredential;
    }

    /**
     * 查询密匙
     */
    @Override
    public SecurityCredential querySecurityCredentialById(Long securityCredentialId)
    {
        logger.debug("[SecurityCredentialServiceImpl:querySecurityCredentialById("
                     + securityCredentialId + ")]");
        SecurityCredential securityCredential = securityCredentialJpaDao.findOne(securityCredentialId);
        logger.debug("[SecurityCredentialServiceImpl:querySecurityCredentialById("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        return securityCredential;
    }

    /**
     * 查询密匙列表
     */
    @Override
    public List<SecurityCredential> queryAllSecurityCredential()
    {
        logger.debug("[SecurityCredentialServiceImpl:selectAll()]");
        return securityCredentialJpaDao.selectAll();
    }

    @Override
    public List<SecurityCredential> querySecurityCredentialByIdentity(AbstractIdentity identity)
    {
        logger.debug("[SecurityCredentialServiceImpl:querySecurityCredentialByIdentity("
                     + (BeanUtils.isNotNull(identity) ? identity.toString() : "") + ")]");
        List<SecurityCredential> securityCredentials = null;
        try
        {
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            filters.put(EntityConstant.SecurityCredential.IDENTITY_ID, new SearchFilter(
                EntityConstant.SecurityCredential.IDENTITY_ID, Operator.EQ, identity.getId()));
            filters.put(EntityConstant.SecurityCredential.IDENTITY_TYPE,
                new SearchFilter(EntityConstant.SecurityCredential.IDENTITY_TYPE, Operator.EQ,
                    identity.getIdentityType()));
            filters.put(EntityConstant.SecurityCredential.STATUS, new SearchFilter(
                EntityConstant.SecurityCredential.STATUS, Operator.EQ,
                Constant.SecurityCredentialStatus.ENABLE_STATUS));
            Specification<SecurityCredential> spec = DynamicSpecifications.bySearchFilter(
                filters.values(), SecurityCredential.class);
            securityCredentials = securityCredentialJpaDao.findAll(spec);
            logger.debug("[SecurityCredentialServiceImpl:querySecurityCredentialByIdentity("
                         + (BeanUtils.isNotNull(securityCredentials) ? Collections3.convertToString(
                             securityCredentials, ",") : "") + ")][返回信息]");
            return securityCredentials;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialServiceImpl:querySecurityCredentialByIdentity(根据Identity查询密钥列表失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"querySecurityCredentialByIdentity"};
            ApplicationException ae = new ApplicationException("identity001083", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 分页查询
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    @Override
    public YcPage<SecurityCredentialVo> queryPageSecurityCredential(Map<String, Object> searchParams,
                                                                    int pageNumber, int pageSize)
    {
        YcPage<SecurityCredentialVo> ycPage = securityCredentialDao.queryPageSecurityCredential(
            searchParams, pageNumber, pageSize);
        return ycPage;
    }

    @Override
    public SecurityCredential querySecurityCredentialByParams(Long identityId,
                                                              IdentityType identityType,
                                                              Long securityTypeId, String status)
    {
        logger.debug("[SecurityCredentialServiceImpl:querySecurityCredentialByParams("
                     + identityId + ","
                     + (BeanUtils.isNotNull(identityType) ? identityType.toString() : "") + ","
                     + securityTypeId + ")]");
        SecurityCredential securityCredential = new SecurityCredential();
        if (StringUtil.isNotBlank(status))
        {
            securityCredential = securityCredentialJpaDao.queryIdentitySecurityCredential(
                identityId, identityType, securityTypeId, status);
        }
        else
        {
            securityCredential = securityCredentialJpaDao.queryIdentitySecurityCredential(
                identityId, identityType, securityTypeId);
        }

        logger.debug("[SecurityCredentialServiceImpl:querySecurityCredentialByParams("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        return securityCredential;
    }

    @Override
    public SecurityCredential getSecurityCredentialByName(String name)
    {
        logger.debug("[SecurityCredentialServiceImpl:getSecurityCredentialByName(" + name + ")]");
        SecurityCredential securityCredential = securityCredentialJpaDao.getSecurityCredentialByName(
            name, Constant.SecurityCredentialStatus.ENABLE_STATUS);
        logger.debug("[SecurityCredentialServiceImpl:getSecurityCredentialByName("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        return securityCredential;
    }

    @Override
    public List<SecurityCredential> getSecurityCredentialListByStatus(String status)
    {
        logger.debug("[SecurityCredentialServiceImpl:getSecurityCredentialListByStatus(" + status
                     + ")]");
        List<SecurityCredential> securityCredentialList = securityCredentialJpaDao.getSecurityCredentialByStatus(status);
        logger.debug("[SecurityCredentialServiceImpl:getSecurityCredentialListByStatus("
                     + (BeanUtils.isNotNull(securityCredentialList) ? Collections3.convertToString(
                         securityCredentialList, Constant.Common.SEPARATOR) : "") + ")][返回信息]");
        return securityCredentialList;
    }

    @Override
    public SecurityCredential queryProclaimedSecurityByIdentity(Long identityId,
                                                                IdentityType identityType,
                                                                Long securityTypeId)
    {
        logger.debug("[SecurityCredentialServiceImpl:queryProclaimedSecurityByIdentity("
                     + identityId + ","
                     + (BeanUtils.isNotNull(identityType) ? identityType.toString() : "") + ","
                     + securityTypeId + ")]");
        SecurityCredential securityCredential = querySecurityCredentialByParams(identityId,
            identityType, securityTypeId, Constant.SecurityCredentialStatus.ENABLE_STATUS);
        String securityValue = securityCredentialManagerService.decryptKeyBySecurity(securityCredential);
        securityCredential.setSecurityValue(securityValue);
        logger.debug("[SecurityCredentialServiceImpl:queryProclaimedSecurityByIdentity("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        return securityCredential;
    }

    @Override
    public boolean checkIsExistSecurity(Long identityId, IdentityType identityType,
                                        Long securityTypeId)
    {
        logger.debug("[SecurityCredentialServiceImpl: checkExistSecurity(" + identityId + ","
                     + (BeanUtils.isNotNull(identityType) ? identityType.toString() : "") + ","
                     + securityTypeId + ")]");
        SecurityCredential securityCredential = securityCredentialJpaDao.getExistSecurityCredential(
            identityId, identityType, securityTypeId);

        logger.debug("[SecurityCredentialServiceImpl: checkExistSecurity("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        if (BeanUtils.isNull(securityCredential))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public SecurityCredential querySecurityCredentialByParam(Long identityId,
                                                             IdentityType identityType,
                                                             String securityTypeName, String status)
    {
        logger.debug("[SecurityCredentialServiceImpl: querySecurityCredentialByParam("
                     + identityId + ","
                     + (BeanUtils.isNotNull(identityType) ? identityType.toString() : "") + ","
                     + securityTypeName + "," + status + ")]");
        SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(securityTypeName);
        SecurityCredential securityCredential = querySecurityCredentialByParams(identityId,
            identityType, securityTyp.getSecurityTypeId(), status);
        logger.debug("[SecurityCredentialServiceImpl :querySecurityCredentialByParam("
                     + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                     + ")][返回信息]");
        return securityCredential;
    }

    @Override
    public String querySecurityCredentialValueByParams(Long identityId, IdentityType identityType,
                                                       String securityTypeName, String status)
    {
        logger.debug("[SecurityCredentialServiceImpl: querySecurityCredentialValueByParams("
                     + identityId + ","
                     + (BeanUtils.isNotNull(identityType) ? identityType.toString() : "") + ","
                     + securityTypeName + "," + status + ")]");
        SecurityCredential securityCredential = querySecurityCredentialByParam(identityId,
            identityType, securityTypeName, status);
        String signValue = securityCredentialManagerService.decryptKeyBySecurityId(securityCredential.getSecurityId());
        logger.debug("[SecurityCredentialServiceImpl :querySecurityCredentialValueByParams(signValue: "
                     + signValue + ")][返回信息]");
        return signValue;
    }

    @Override
    public List<SecurityCredential> getSecurityCredentialByType(Long securityTypeId)
    {
        logger.debug("[SecurityCredentialServiceImpl: getSecurityCredentialByType("
                     + securityTypeId + ")]");
        List<SecurityCredential> securityCredentials = securityCredentialJpaDao.getSecurityCredentialByType(securityTypeId);
        return securityCredentials;
    }

}
