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
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.injection.entity.InterfaceSendtimesConf;
import com.yuecheng.hops.injection.repository.InterfaceSendtimesConfDao;
import com.yuecheng.hops.injection.service.InterfaceSendtimesConfService;


@Service("interfaceSendtimesConfService")
public class InterfaceSendtimesConfServiceImpl implements InterfaceSendtimesConfService
{
    private static Logger logger = LoggerFactory.getLogger(InterfaceSendtimesConfServiceImpl.class);

    @Autowired
    private InterfaceSendtimesConfDao interfaceSendtimesConfDao;

    @Override
    public List<InterfaceSendtimesConf> getAllInterfaceSendtimesConf()
    {
        return interfaceSendtimesConfDao.queryInterfaceSendtimesConfList();
    }

    @Override
    public InterfaceSendtimesConf saveInterfaceSendtimesConf(InterfaceSendtimesConf interfaceSendtimesConf)
    {
        return interfaceSendtimesConfDao.save(interfaceSendtimesConf);
    }

    @Override
    public void deleteInterfaceSendtimesConf(Long interfaceSendtimesConfId)
    {
        interfaceSendtimesConfDao.delete(interfaceSendtimesConfId);
    }

    @Override
    public InterfaceSendtimesConf updateInterfaceSendtimesConf(InterfaceSendtimesConf interfaceSendtimesConf)
    {
        return this.saveInterfaceSendtimesConf(interfaceSendtimesConf);
    }

    @Override
    public InterfaceSendtimesConf getInterfaceSendtimesConfById(Long interfaceSendtimesConfId)
    {
        return interfaceSendtimesConfDao.findOne(interfaceSendtimesConfId);
    }

    @Override
    public YcPage<InterfaceSendtimesConf> queryInterfaceSendtimesConf(Map<String, Object> searchParams,
                                                                      int pageNumber,
                                                                      int pageSize, BSort bsort)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[InterfaceSendtimesConfServiceImpl:queryInterfaceSendtimesConf("
                        + searchParams != null ? searchParams.toString() : null + "," + pageNumber
                                                                           + "," + pageSize + ")]");
        }
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? "id" : bsort.getCloumn();
        String orderDirect = bsort == null ? "DESC" : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        Page<InterfaceSendtimesConf> page = PageUtil.queryPage(interfaceSendtimesConfDao, filters,
            pageNumber, pageSize, sort, InterfaceSendtimesConf.class);
        YcPage<InterfaceSendtimesConf> ycPage = new YcPage<InterfaceSendtimesConf>();
        ycPage.setList(page.getContent());
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setCountTotal((int)page.getTotalElements());
        return ycPage;
    }

    @Override
    public InterfaceSendtimesConf getInterfaceSendtimesConfByParams(Long merchantId,
                                                                    String interfaceType)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[InterfaceSendtimesConfServiceImpl:getInterfaceSendtimesConfByParams("
                        + merchantId + "," + interfaceType + ")]");
        }
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.InterfaceSendtimesConf.MERCHANT_ID, new SearchFilter(
            EntityConstant.InterfaceSendtimesConf.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.InterfaceSendtimesConf.INTERFACE_TYPE, new SearchFilter(
            EntityConstant.InterfaceSendtimesConf.INTERFACE_TYPE, Operator.EQ, interfaceType));
        Specification<InterfaceSendtimesConf> spec_InterfaceSendtimesConf = DynamicSpecifications.bySearchFilter(
            filters.values(), InterfaceSendtimesConf.class);
        InterfaceSendtimesConf interfaceSendtimesConf = interfaceSendtimesConfDao.findOne(spec_InterfaceSendtimesConf);
        return interfaceSendtimesConf;
    }

}
