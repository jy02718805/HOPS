package com.yuecheng.hops.identity.service.impl;


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
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.IdentityStatusTransfer;
import com.yuecheng.hops.identity.repository.IdentityStatusTransferDao;
import com.yuecheng.hops.identity.service.IdentityStatusTransferService;


@Service("identityStatusTransferService")
public class IdentityStatusTransferServiceImpl implements IdentityStatusTransferService
{
    @Autowired
    private IdentityStatusTransferDao identityStatusTransferDao;

    private static final Logger logger = LoggerFactory.getLogger(IdentityStatusTransferServiceImpl.class);

    @Override
    public IdentityStatusTransfer savaIdentityStatusTransfer(IdentityStatusTransfer identityStatusTransfer)
    {
        logger.debug("IdentityStatusTransferServiceImpl:savaIdentityStatusTransfer("
                     + (BeanUtils.isNotNull(identityStatusTransfer) ? identityStatusTransfer.toString() :"")
                                                                                            + ")");
        identityStatusTransfer = identityStatusTransferDao.save(identityStatusTransfer);
        logger.debug("IdentityStatusTransferServiceImpl:savaIdentityStatusTransfer("
                     + (BeanUtils.isNotNull(identityStatusTransfer) ? identityStatusTransfer.toString() :"")
                                                                                            + ")[返回信息]");
        return identityStatusTransfer;
    }

    @Override
    public IdentityStatusTransfer queryIdentityStatusTransferById(Long id)
    {
        logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransferById(" + id
                     + ")");
        IdentityStatusTransfer identityStatusTransfer = identityStatusTransferDao.findOne(id);
        logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransferById("
                     + (BeanUtils.isNotNull(identityStatusTransfer) ? identityStatusTransfer.toString() :"")
                                                                                            + ")[返回信息]");
        return identityStatusTransfer;
    }

    @Override
    public IdentityStatusTransfer queryIdentityStatusTransferByParams(IdentityStatus oldStatus,
                                                                      IdentityStatus newStatus,
                                                                      IdentityType identityType)
    {
        logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransferByParams("
                     + (BeanUtils.isNotNull(oldStatus) ? oldStatus.getStatus().toString() :"") + ","
                                                                              + (BeanUtils.isNotNull(newStatus) ? newStatus.getStatus().toString() :"")
                                                                                                                                       + ","
                                                                                                                                       + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"")
                                                                                                                                                                                          + ")");
        if (BeanUtils.isNotNull(oldStatus) && BeanUtils.isNotNull(newStatus) && BeanUtils.isNotNull(identityType))
        {
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            filters.put(EntityConstant.IdentityStatusTransfer.IDENTITY_STATUS_OLD,
                new SearchFilter(EntityConstant.IdentityStatusTransfer.IDENTITY_STATUS_OLD,
                    Operator.EQ, oldStatus.getStatus()));
            filters.put(EntityConstant.IdentityStatusTransfer.IDENTITY_STATUS_NEW,
                new SearchFilter(EntityConstant.IdentityStatusTransfer.IDENTITY_STATUS_NEW,
                    Operator.EQ, newStatus.getStatus()));
            filters.put(EntityConstant.IdentityStatusTransfer.IDENTITY_TYPE,
                new SearchFilter(EntityConstant.IdentityStatusTransfer.IDENTITY_TYPE, Operator.EQ,
                    identityType));
            Specification<IdentityStatusTransfer> spec = DynamicSpecifications.bySearchFilter(
                filters.values(), IdentityStatusTransfer.class);
            IdentityStatusTransfer identityStatusTransfer = identityStatusTransferDao.findOne(spec);
            if (identityStatusTransfer != null)
            {
                logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransferByParams("
                             + (BeanUtils.isNotNull(identityStatusTransfer) ? identityStatusTransfer.toString() :"")
                                                                                                    + ")[返回信息]");
                return identityStatusTransfer;
            }
            else
            {
                logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransferByParams(未找到状态机信息)[返回信息]");
                String[] msgParams = new String[] {oldStatus.getStatus().toString(),
                    newStatus.getStatus().toString(), identityType.toString()};
                ApplicationException e = new ApplicationException("identity001054", msgParams);
                throw ExceptionUtil.throwException(e);
            }
        }
        else
        {
            logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransferByParams(参数为空)[返回信息]");
            ApplicationException e = new ApplicationException("identity001055");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public void deleteIdentityStatusTransferById(Long id)
    {
        logger.debug("IdentityStatusTransferServiceImpl:deleteIdentityStatusTransferById(" + id
                     + ")");
        identityStatusTransferDao.delete(id);
    }

    @Override
    public void deleteIdentityStatusTransferByParams(IdentityStatus oldStatus,
                                                     IdentityStatus newStatus,
                                                     IdentityType identityType)
    {
        logger.debug("IdentityStatusTransferServiceImpl:deleteIdentityStatusTransferByParams("
                     + (BeanUtils.isNotNull(oldStatus) ? oldStatus.getStatus().toString() :"") + ","
                                                                              + (BeanUtils.isNotNull(newStatus) ? newStatus.getStatus().toString() :"")
                                                                                                                                       + ","
                                                                                                                                       + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"")
                                                                                                                                                                                          + ")");
        IdentityStatusTransfer identityStatusTransfer = queryIdentityStatusTransferByParams(
            oldStatus, newStatus, identityType);
        logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransferByParams("
                     + (BeanUtils.isNotNull(identityStatusTransfer) ? identityStatusTransfer.toString() :"")
                                                                                            + ")[返回信息]");

    }

    @Override
    public IdentityStatusTransfer updateIdentityStatusTransfer(IdentityStatusTransfer identityStatusTransfer)
    {
        logger.debug("IdentityStatusTransferServiceImpl:updateIdentityStatusTransfer("
                     + (BeanUtils.isNotNull(identityStatusTransfer) ? identityStatusTransfer.toString() :"")
                                                                                            + ")");
        identityStatusTransfer = identityStatusTransferDao.save(identityStatusTransfer);
        logger.debug("IdentityStatusTransferServiceImpl:updateIdentityStatusTransfer("
                     + (BeanUtils.isNotNull(identityStatusTransfer)? identityStatusTransfer.toString() :"")
                                                                                            + ")[返回信息]");
        return identityStatusTransfer;
    }

    @Override
    public List<IdentityStatusTransfer> queryIdentityStatusTransfer(IdentityType identityType)
    {
        logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransfer("
                     + (BeanUtils.isNotNull(identityType)? identityType.toString() :"") + ")");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.IdentityStatusTransfer.IDENTITY_TYPE,
            new SearchFilter(EntityConstant.IdentityStatusTransfer.IDENTITY_TYPE, Operator.EQ,
                identityType));
        Specification<IdentityStatusTransfer> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), IdentityStatusTransfer.class);
        List<IdentityStatusTransfer> identityStatusTransferList = identityStatusTransferDao.findAll(spec);
        if (identityStatusTransferList != null)
        {
            logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransfer("
                         + (BeanUtils.isNotNull(identityStatusTransferList) ? Collections3.convertToString(
                identityStatusTransferList, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
            return identityStatusTransferList;
        }
        else
        {
            logger.debug("IdentityStatusTransferServiceImpl:queryIdentityStatusTransfer(未找到状态机信息)[返回信息]");
            String[] msgParams = new String[] {"", "", identityType.toString()};
            ApplicationException e = new ApplicationException("identity001054", msgParams);
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public YcPage<IdentityStatusTransfer> queryPageIdentityStatusTransfer(Map<String, Object> searchParams,
                                                                          int pageNumber,
                                                                          int pageSize,
                                                                          BSort bsort,
                                                                          IdentityType identityType)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? EntityConstant.Identity.IDENTITY_ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<IdentityStatusTransfer> ycPage = PageUtil.queryYcPage(identityStatusTransferDao,
            filters, pageNumber, pageSize, sort, IdentityStatusTransfer.class);
        List<IdentityStatusTransfer> list = ycPage.getList();
        logger.debug("[IdentityStatusTransferServiceImpl:queryPageIdentityStatusTransfer(" + (BeanUtils.isNotNull(list) ? Collections3.convertToString(
            list, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return ycPage;
    }

}
