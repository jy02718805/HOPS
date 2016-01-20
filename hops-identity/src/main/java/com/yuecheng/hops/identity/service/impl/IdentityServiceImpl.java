package com.yuecheng.hops.identity.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springside.modules.persistence.SearchFilter;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.IdentityConstants;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.IdentityStatusTransfer;
import com.yuecheng.hops.identity.repository.IdentityDao;
import com.yuecheng.hops.identity.service.IdentityDaoFinder;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.IdentityStatusTransferService;


@Service("identityService")
@Component
public class IdentityServiceImpl implements IdentityService
{
    @Autowired
    private IdentityDaoFinder identityDaoFinder;

    @Autowired
    private IdentityStatusTransferService identityStatusTransferService;

    private static Logger logger = LoggerFactory.getLogger(IdentityServiceImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public AbstractIdentity findIdentityByIdentityId(Long identityId, IdentityType identityType)
    {
        try
        {
            logger.debug("[IdentityServiceImpl:findIdentityByIdentityId(" + (BeanUtils.isNotNull(identityId) ? identityId.toString() :"")
                                                                                                                         + ","
                                                                                                                         + identityType != null ? identityType.toString() :""
                                                                                                                                                                            + ")]");

            AbstractIdentity result = null;
            @SuppressWarnings("rawtypes")
            IdentityDao identityDao = identityDaoFinder.getByIdentityType(identityType);
            if (identityDao != null)
            {
                result = (AbstractIdentity)identityDao.findOne(identityId);
            }
            logger.debug("[IdentityServiceImpl:findIdentityByIdentityId(" + (BeanUtils.isNotNull(result)  ? result.toString() :"")
                                                                                                                 + ")][返回信息]");

            return result;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        catch (Exception e)
        {
            logger.error("[IdentityServiceImpl:findIdentityByIdentityId(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"findIdentityByIdentityId"};
            ApplicationException ae = new ApplicationException("identity001014", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteIdentity(Long identityId, IdentityType identityType)
    {
        try
        {
            logger.debug("[IdentityServiceImpl:deleteIdentity(" + (BeanUtils.isNotNull(identityId) ? identityId.toString() :"")
                                                                                                               + ","
                                                                                                               + identityType != null ? identityType.toString() :""
                                                                                                                                                                  + ")]");
            @SuppressWarnings("rawtypes")
            IdentityDao identityDao = identityDaoFinder.getByIdentityType(identityType);
            if (identityDao != null)
            {
                identityDao.delete(identityId);
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityServiceImpl:deleteIdentity(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"deleteIdentity"};
            ApplicationException ae = new ApplicationException("identity001015", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractIdentity saveIdentity(AbstractIdentity identity, String updateUser)
    {
        try
        {
            IdentityType identityType = identity.getIdentityType();
            logger.debug("[IdentityServiceImpl:saveIdentity(" + (BeanUtils.isNotNull(identity)? identity.toString() :"")
                                                                                                         + ")]");
            @SuppressWarnings("rawtypes")
            IdentityDao identityDao = identityDaoFinder.getByIdentityType(identityType);
            if (identityDao != null)
            {
                identity = (AbstractIdentity)identityDao.save(identity);
            }
            logger.debug("[IdentityServiceImpl:saveIdentity(" + (BeanUtils.isNotNull(identity)? identity.toString() :"")
                                                                                                         + ")][返回信息]");
            return identity;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityServiceImpl:saveIdentity(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveIdentity"};
            ApplicationException ae = new ApplicationException("identity001016", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public YcPage<AbstractIdentity> queryIdentity(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort bsort,
                                                  IdentityType identityType)
    {
        logger.debug("[IdentityServiceImpl:queryIdentity(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                              + ","
                                                                                                              + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"")
                                                                                                                                                                 + ")]");
        YcPage<AbstractIdentity> ycPage = new YcPage<AbstractIdentity>();
        @SuppressWarnings("rawtypes")
        IdentityDao identityDao = identityDaoFinder.getByIdentityType(identityType);
        if (identityDao != null)
        {
            Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
            String orderCloumn = bsort == null ? EntityConstant.Identity.IDENTITY_ID : bsort.getCloumn();
            String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
            Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
            ycPage = PageUtil.queryYcPage(identityDao, filters, pageNumber, pageSize, sort,
                AbstractIdentity.class);
            List<AbstractIdentity> list = ycPage.getList();
            logger.debug("[IdentityServiceImpl:queryIdentity(" + (BeanUtils.isNotNull(list) ? Collections3.convertToString(
                list, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        }
        return ycPage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AbstractIdentity> getAllIdentityList(IdentityType identityType)
    {
        try
        {
            logger.debug("[IdentityServiceImpl:getAllIdentityList(" + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"")
                                                                                                                       + ")]");

            List<AbstractIdentity> result = null;
            @SuppressWarnings("rawtypes")
            IdentityDao identityDao = identityDaoFinder.getByIdentityType(identityType);
            if (identityDao != null)
            {
                result = (List<AbstractIdentity>)identityDao.findAll();
            }
            logger.debug("[IdentityServiceImpl:getAllIdentityList(" + (BeanUtils.isNotNull(result) ? Collections3.convertToString(
                result, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return result;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityServiceImpl:getAllIdentityList(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getAllIdentityList", identityType.toString()};
            ApplicationException ae = new ApplicationException("identity001042", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public AbstractIdentity updateIdentityStatus(IdentityStatus newStatus, Long identityId,
                                                 IdentityType identityType)
    {
        try
        {
            String[] msgParams = new String[] {"updateIdentityStatus"};
            logger.debug("IdentityServiceImpl:updateIdentityStatus(" + identityId + ","
                         + newStatus != null ? newStatus.toString() :"" + ")");
            if (BeanUtils.isNull(identityId) || BeanUtils.isNull(newStatus)
                || StringUtil.isNullOrEmpty(newStatus.getStatus()))
            {
                logger.debug("IdentityServiceImpl:updateIdentityStatus(" + identityId + ","
                             + newStatus != null ? newStatus.toString() :"" + ")");
                ApplicationException ae = new ApplicationException("identity001043", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            String[] mstatus = {IdentityConstants.MERCHANT_INIT,
                IdentityConstants.MERCHANT_ENABLE, IdentityConstants.MERCHANT_DISABLE};
            if (!ArrayUtils.contains(mstatus, newStatus.getStatus()))
            {
                logger.debug("IdentityServiceImpl:updateIdentityStatus(" + identityId + ","
                             + newStatus != null ? newStatus.toString() :"" + ")");
                ApplicationException ae = new ApplicationException("identity001044", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            AbstractIdentity identity = findIdentityByIdentityId(identityId, identityType);
            if (null == identity)
            {
                logger.debug("IdentityServiceImpl:updateIdentityStatus(" + identityId + ","
                             + newStatus != null ? newStatus.toString() :"" + ")");
                ApplicationException ae = new ApplicationException("identity001045", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            IdentityStatusTransfer identityStatusTransfer = identityStatusTransferService.queryIdentityStatusTransferByParams(
                identity.getIdentityStatus(), newStatus, identityType);
            if (null != identityStatusTransfer)
            {
                identity.setIdentityStatus(newStatus);
                identity.setIdentityType(identityType);
                identity = saveIdentity(identity, "");
                logger.debug("IdentityServiceImpl:updateIdentityStatus(" + (BeanUtils.isNotNull(identity)? identity.toString() :"")
                                                                                                                    + ")[返回信息]");
                return identity;
            }
            else
            {
                logger.error("[IdentityServiceImpl:updateIdentityStatus(您要操作的用户状态不能进行转换:"
                             + identityType.toString() + ")]");
                msgParams = new String[] {"updateIdentityStatus",
                    identity.getIdentityStatus().getStatus(), newStatus.getStatus(),
                    identityType.toString()};
                ApplicationException ae = new ApplicationException("identity001074", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityServiceImpl:updateIdentityStatus(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"updateIdentityStatus"};
            ApplicationException ae = new ApplicationException("identity001024", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
