package com.yuecheng.hops.injection.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.entity.InterfaceConstant;
import com.yuecheng.hops.injection.repository.InterfaceConstantDao;
import com.yuecheng.hops.injection.service.InterfaceConstantService;


@Service("interfaceConstantService")
public class InterfaceConstantServiceImpl implements InterfaceConstantService
{
    private static Logger logger = LoggerFactory.getLogger(InterfaceConstantServiceImpl.class);

    @Autowired
    private InterfaceConstantDao interfaceConstantDao;

    @Override
    public InterfaceConstant saveInterfaceConstant(InterfaceConstant interfaceConstant)
    {
        InterfaceConstant interfaceConstantTemp = interfaceConstantDao.queryInterfaceConstantByParams(
            interfaceConstant.getIdentityId(), interfaceConstant.getIdentityType(),
            interfaceConstant.getKey());
        if (BeanUtils.isNotNull(interfaceConstantTemp))
        {
            throw ExceptionUtil.throwException(new ApplicationException("businesss000024",
                new String[] {String.valueOf(interfaceConstant).toString()}));
        }
        else
        {
            interfaceConstant = interfaceConstantDao.save(interfaceConstant);
        }
        return interfaceConstant;
    }

    @Override
    public InterfaceConstant deleteInterfaceConstant(Long interfaceConstantId)
    {
        InterfaceConstant interfaceConstant = interfaceConstantDao.findOne(interfaceConstantId);
        interfaceConstantDao.delete(interfaceConstant);
        return interfaceConstant;
    }

    @Override
    public InterfaceConstant updateInterfaceConstant(InterfaceConstant interfaceConstant)
    {
        return interfaceConstantDao.save(interfaceConstant);
    }

    @Override
    public InterfaceConstant getInterfaceConstantById(Long interfaceConstantId)
    {
        return interfaceConstantDao.findOne(interfaceConstantId);
    }

    @Override
    public YcPage<InterfaceConstant> queryInterfaceConstant(Map<String, Object> searchParams,
                                                            int pageNumber, int pageSize,
                                                            BSort bsort)
    {
        logger.debug("[InterfaceConstantServiceImpl:queryInterfaceConstant(" + searchParams != null ? searchParams.toString() : null
                                                                                                                                + ","
                                                                                                                                + pageNumber
                                                                                                                                + ","
                                                                                                                                + pageSize
                                                                                                                                + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? "id" : bsort.getCloumn();
        String orderDirect = bsort == null ? "DESC" : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        Page<InterfaceConstant> page = PageUtil.queryPage(interfaceConstantDao, filters,
            pageNumber, pageSize, sort, InterfaceConstant.class);
        YcPage<InterfaceConstant> ycPage = new YcPage<InterfaceConstant>();
        ycPage.setList(page.getContent());
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setCountTotal((int)page.getTotalElements());
        return ycPage;
    }

    @Override
    public List<InterfaceConstant> getInterfaceConstantByParams(Long identityId,
                                                                String identityType)
    {
        logger.debug("[InterfaceConstantServiceImpl:getInterfaceConstantByParams(" + identityId
                     + "," + identityType + ")]");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.InterfaceConstant.IDENTITY_ID, new SearchFilter(
            EntityConstant.InterfaceConstant.IDENTITY_ID, Operator.EQ, identityId));
        filters.put(EntityConstant.InterfaceConstant.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.InterfaceConstant.IDENTITY_TYPE, Operator.EQ, identityType));
        Specification<InterfaceConstant> spec_InterfacePackets = DynamicSpecifications.bySearchFilter(
            filters.values(), InterfaceConstant.class);
        List<InterfaceConstant> InterfaceConstants = interfaceConstantDao.findAll(spec_InterfacePackets);
        return InterfaceConstants;
    }

    @Override
    public InterfaceConstant getInterfaceConstant(Long identityId, String identityType, String key)
    {
        logger.debug("[InterfaceConstantServiceImpl:getInterfaceConstant(" + identityId + ","
                     + identityType + "," + key + ")]");
        InterfaceConstant InterfaceConstants = interfaceConstantDao.queryInterfaceConstantByParams(
            identityId, identityType, key);
        return InterfaceConstants;
    }
}
