package com.yuecheng.hops.security.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.security.entity.SecurityCredentialStatusTransfer;
import com.yuecheng.hops.security.repository.SecurityCredentialStatusTransferDao;
import com.yuecheng.hops.security.service.SecurityCredentialStatusTransferService;


@Service("securityCredentialStatusTransferService")
public class SecurityCredentialStatusTransferServiceImpl implements SecurityCredentialStatusTransferService
{
    @Autowired
    private SecurityCredentialStatusTransferDao securityCredentialStatusTransferDao;

    private static final Logger logger = LoggerFactory.getLogger(SecurityCredentialStatusTransferServiceImpl.class);

    @Override
    public SecurityCredentialStatusTransfer savaSecurityCredentialStatusTransfer(SecurityCredentialStatusTransfer securityCredentialStatusTransfer)
    {
        logger.debug("SecurityCredentialStatusTransferServiceImpl:savaSecurityCredentialStatusTransfer("
                     + (BeanUtils.isNotNull(securityCredentialStatusTransfer) ? securityCredentialStatusTransfer.toString() :"")
                                                                                                                + ")");
        securityCredentialStatusTransfer = securityCredentialStatusTransferDao.save(securityCredentialStatusTransfer);
        logger.debug("SecurityCredentialStatusTransferServiceImpl:savaSecurityCredentialStatusTransfer("
                     + (BeanUtils.isNotNull(securityCredentialStatusTransfer) ? securityCredentialStatusTransfer.toString() :"")
                                                                                                                + ")[返回信息]");
        return securityCredentialStatusTransfer;
    }

    @Override
    public SecurityCredentialStatusTransfer querySecurityCredentialStatusTransferById(Long id)
    {
        logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransferById("
                     + id + ")");
        SecurityCredentialStatusTransfer securityCredentialStatusTransfer = securityCredentialStatusTransferDao.findOne(id);
        logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransferById("
                     + (BeanUtils.isNotNull(securityCredentialStatusTransfer) ? securityCredentialStatusTransfer.toString() :"")
                                                                                                                + ")[返回信息]");
        return securityCredentialStatusTransfer;
    }

    @Override
    public SecurityCredentialStatusTransfer querySecurityCredentialStatusTransferByParams(String oldStatus,
                                                                                          String newStatus)
    {
        logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransferByParams("
                     + oldStatus + "," + newStatus + ")");
        if (null != oldStatus && null != newStatus)
        {
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            filters.put(EntityConstant.SecurityCredentialStatusTransfer.IDENTITY_OLD,
                new SearchFilter(EntityConstant.SecurityCredentialStatusTransfer.IDENTITY_OLD,
                    Operator.EQ, oldStatus));
            filters.put(EntityConstant.SecurityCredentialStatusTransfer.IDENTITY_NEW,
                new SearchFilter(EntityConstant.SecurityCredentialStatusTransfer.IDENTITY_NEW,
                    Operator.EQ, newStatus));
            Specification<SecurityCredentialStatusTransfer> spec = DynamicSpecifications.bySearchFilter(
                filters.values(), SecurityCredentialStatusTransfer.class);
            SecurityCredentialStatusTransfer securityCredentialStatusTransfer = securityCredentialStatusTransferDao.findOne(spec);
            if (BeanUtils.isNotNull(securityCredentialStatusTransfer))
            {
                logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransferByParams("
                             + (BeanUtils.isNotNull(securityCredentialStatusTransfer) ? securityCredentialStatusTransfer.toString() :"")
                                                                                                                        + ")[返回信息]");
                return securityCredentialStatusTransfer;
            }
            else
            {
                logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransferByParams(未找到密钥状态机信息)[返回信息]");
                String[] msgParams = new String[] {oldStatus.toString(), newStatus.toString()};
                ApplicationException e = new ApplicationException("identity001105", msgParams);
                throw ExceptionUtil.throwException(e);
            }
        }
        else
        {
            logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransferByParams(参数为空)[返回信息]");
            ApplicationException e = new ApplicationException("identity001106");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public void deleteSecurityCredentialStatusTransferById(Long id)
    {
        logger.debug("SecurityCredentialStatusTransferServiceImpl:deleteSecurityCredentialStatusTransferById("
                     + id + ")");
        securityCredentialStatusTransferDao.delete(id);
    }

    @Override
    public void deleteSecurityCredentialStatusTransferByParams(String oldStatus, String newStatus)
    {
        logger.debug("SecurityCredentialStatusTransferServiceImpl:deleteSecurityCredentialStatusTransferByParams("
                     + oldStatus + "," + newStatus + ")");
        SecurityCredentialStatusTransfer securityCredentialStatusTransfer = querySecurityCredentialStatusTransferByParams(
            oldStatus, newStatus);
        logger.debug("SecurityCredentialStatusTransferServiceImpl:deleteSecurityCredentialStatusTransferByParams("
                     + (BeanUtils.isNotNull(securityCredentialStatusTransfer) ? securityCredentialStatusTransfer.toString() :"")
                                                                                                                + ")[返回信息]");
    }

    @Override
    public SecurityCredentialStatusTransfer updateSecurityCredentialStatusTransfer(SecurityCredentialStatusTransfer securityCredentialStatusTransfer)
    {
        logger.debug("SecurityCredentialStatusTransferServiceImpl:updateSecurityCredentialStatusTransfer("
                     + (BeanUtils.isNotNull(securityCredentialStatusTransfer) ? securityCredentialStatusTransfer.toString() :"")
                                                                                                                + ")");
        securityCredentialStatusTransfer = securityCredentialStatusTransferDao.save(securityCredentialStatusTransfer);
        logger.debug("SecurityCredentialStatusTransferServiceImpl:updateSecurityCredentialStatusTransfer("
                     + (BeanUtils.isNotNull(securityCredentialStatusTransfer) ? securityCredentialStatusTransfer.toString() :"")
                                                                                                                + ")[返回信息]");
        return securityCredentialStatusTransfer;
    }

    @Override
    public List<SecurityCredentialStatusTransfer> querySecurityCredentialStatusTransfer()
    {
        logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransfer()");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        Specification<SecurityCredentialStatusTransfer> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SecurityCredentialStatusTransfer.class);
        List<SecurityCredentialStatusTransfer> securityCredentialStatusTransferList = securityCredentialStatusTransferDao.findAll(spec);
        if (BeanUtils.isNotNull(securityCredentialStatusTransferList))
        {
            logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransfer("
                         + (BeanUtils.isNotNull(securityCredentialStatusTransferList) ? Collections3.convertToString(
                securityCredentialStatusTransferList, Constant.Common.SEPARATOR) :"")
                                                                                   + ")[返回信息]");
            return securityCredentialStatusTransferList;
        }
        else
        {
            logger.debug("SecurityCredentialStatusTransferServiceImpl:querySecurityCredentialStatusTransfer(未找到密钥状态机信息)[返回信息]");
            ApplicationException e = new ApplicationException("identity001105");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public YcPage<SecurityCredentialStatusTransfer> queryPageSecurityCredentialStatusTransfer(Map<String, Object> searchParams,
                                                                                              int pageNumber,
                                                                                              int pageSize,
                                                                                              BSort bsort)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<SecurityCredentialStatusTransfer> ycPage = PageUtil.queryYcPage(
            securityCredentialStatusTransferDao, filters, pageNumber, pageSize, sort,
            SecurityCredentialStatusTransfer.class);
        List<SecurityCredentialStatusTransfer> list = ycPage.getList();
        logger.debug("[SecurityCredentialStatusTransferServiceImpl:queryPageSecurityCredentialStatusTransfer("
                     + (BeanUtils.isNotNull(list) ? Collections3.convertToString(list, Constant.Common.SEPARATOR) :"")
                                                                                                      + ")][返回信息]");
        return ycPage;
    }

}
