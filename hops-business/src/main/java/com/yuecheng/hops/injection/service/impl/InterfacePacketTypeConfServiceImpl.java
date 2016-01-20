package com.yuecheng.hops.injection.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;
import com.yuecheng.hops.injection.repository.InterfacePacketTypeConfDao;
import com.yuecheng.hops.injection.service.InterfacePacketTypeConfService;


@Service("interfacePacketTypeConfService")
public class InterfacePacketTypeConfServiceImpl implements InterfacePacketTypeConfService
{

    @Autowired
    private InterfacePacketTypeConfDao interfacePacketTypeConfDao;

    @Override
    @Transactional
    public InterfacePacketTypeConf saveInterfacePacketTypeConf(InterfacePacketTypeConf interfacePacketTypeConf)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.InterfacePacketTypeConf.MERCHANT_ID, new SearchFilter(
            EntityConstant.InterfacePacketTypeConf.MERCHANT_ID, Operator.EQ,
            interfacePacketTypeConf.getMerchantId()));
        filters.put(EntityConstant.InterfacePacketTypeConf.INTERFACE_TYPE, new SearchFilter(
            EntityConstant.InterfacePacketTypeConf.INTERFACE_TYPE, Operator.EQ,
            interfacePacketTypeConf.getInterfaceType()));
        filters.put(EntityConstant.InterfacePacketTypeConf.CONNECTION_MODULE, new SearchFilter(
            EntityConstant.InterfacePacketTypeConf.CONNECTION_MODULE, Operator.EQ,
            interfacePacketTypeConf.getConnectionModule()));
        Specification<InterfacePacketTypeConf> spec_InterfacePacketTypeConf = DynamicSpecifications.bySearchFilter(
            filters.values(), InterfacePacketTypeConf.class);
        InterfacePacketTypeConf check_interfacePacketTypeConf = interfacePacketTypeConfDao.findOne(spec_InterfacePacketTypeConf);
        if (check_interfacePacketTypeConf == null)
        {
            interfacePacketTypeConf = interfacePacketTypeConfDao.save(interfacePacketTypeConf);
        }
        else
        {
            interfacePacketTypeConf.setId(check_interfacePacketTypeConf.getId());
            interfacePacketTypeConf = interfacePacketTypeConfDao.save(interfacePacketTypeConf);
        }
        return interfacePacketTypeConf;
    }

    @Override
    @Transactional
    public void deleteInterfacePacketTypeConf(Long interfacePacketTypeConfId)
    {
        InterfacePacketTypeConf interfacePacketTypeConf = interfacePacketTypeConfDao.findOne(interfacePacketTypeConfId);
        interfacePacketTypeConfDao.delete(interfacePacketTypeConf);
    }

    @Override
    public InterfacePacketTypeConf updateInterfacePacketTypeConf(InterfacePacketTypeConf interfacePacketTypeConf)
    {
        interfacePacketTypeConf = interfacePacketTypeConfDao.save(interfacePacketTypeConf);
        return interfacePacketTypeConf;
    }

    @Override
    public InterfacePacketTypeConf getInterfacePacketTypeConfByParams(Long merchantId,
                                                                      String interfaceType,
                                                                      String connectionModule)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if(BeanUtils.isNotNull(merchantId))
        {
            filters.put(EntityConstant.InterfacePacketTypeConf.MERCHANT_ID, new SearchFilter(EntityConstant.InterfacePacketTypeConf.MERCHANT_ID, Operator.EQ, merchantId));
        }
        if(StringUtil.isNotBlank(interfaceType))
        {
            filters.put(EntityConstant.InterfacePacketTypeConf.INTERFACE_TYPE, new SearchFilter(EntityConstant.InterfacePacketTypeConf.INTERFACE_TYPE, Operator.EQ, interfaceType));
        }
        if(StringUtil.isNotBlank(connectionModule))
        {
            filters.put(EntityConstant.InterfacePacketTypeConf.CONNECTION_MODULE, new SearchFilter(EntityConstant.InterfacePacketTypeConf.CONNECTION_MODULE, Operator.EQ, connectionModule));
        }
        Specification<InterfacePacketTypeConf> spec_InterfacePacketTypeConf = DynamicSpecifications.bySearchFilter(filters.values(), InterfacePacketTypeConf.class);
        InterfacePacketTypeConf interfacePacketTypeConf = interfacePacketTypeConfDao.findOne(spec_InterfacePacketTypeConf);
        return interfacePacketTypeConf;
    }
    
//    没有调用
//    @Override
//    public InterfacePacketTypeConf getInterfacePacketTypeConfByInterfaceType(String interfaceType,
//                                                                             String connectionModule)
//    {
//        InterfacePacketTypeConf interfacePacketTypeConf = (InterfacePacketTypeConf)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_PACKET_TYPE_CONF+Constant.StringSplitUtil.ENCODE + interfaceType +Constant.StringSplitUtil.ENCODE + connectionModule);
//        if(BeanUtils.isNull(interfacePacketTypeConf))
//        {
//            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
//            filters.put(EntityConstant.InterfacePacketTypeConf.INTERFACE_TYPE, new SearchFilter(
//                EntityConstant.InterfacePacketTypeConf.INTERFACE_TYPE, Operator.EQ, interfaceType));
//            filters.put(EntityConstant.InterfacePacketTypeConf.CONNECTION_MODULE, new SearchFilter(
//                EntityConstant.InterfacePacketTypeConf.CONNECTION_MODULE, Operator.EQ,
//                connectionModule));
//            Specification<InterfacePacketTypeConf> spec_InterfacePacketTypeConf = DynamicSpecifications.bySearchFilter(
//                filters.values(), InterfacePacketTypeConf.class);
//            interfacePacketTypeConf = interfacePacketTypeConfDao.findOne(spec_InterfacePacketTypeConf);
//            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_PACKET_TYPE_CONF+Constant.StringSplitUtil.ENCODE + interfaceType +Constant.StringSplitUtil.ENCODE + connectionModule, interfacePacketTypeConf);
//        }
//        return interfacePacketTypeConf;
//    }

}
