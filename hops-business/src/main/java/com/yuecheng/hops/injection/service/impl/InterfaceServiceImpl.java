package com.yuecheng.hops.injection.service.impl;


import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinition;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;
import com.yuecheng.hops.injection.entity.InterfaceParam;
import com.yuecheng.hops.injection.repository.InterfacePacketsDefinitionDao;
import com.yuecheng.hops.injection.service.InterfacePacketTypeConfService;
import com.yuecheng.hops.injection.service.InterfaceParamService;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.injection.service.MerchantResponseService;


/**
 * 接口服务
 */
@Service("interfaceService")
public class InterfaceServiceImpl implements InterfaceService
{
    private static Logger logger = LoggerFactory.getLogger(InterfaceServiceImpl.class);

    @Autowired
    private InterfacePacketsDefinitionDao interfacePacketsDefinitionDao;

    @Autowired
    private InterfaceParamService interfaceParamService;

    @Autowired
    private MerchantRequestService merchantRequestService;

    @Autowired
    private MerchantResponseService merchantResponseService;

    @Autowired
    private InterfacePacketTypeConfService interfacePacketTypeConfService;

    @Override
    public List<InterfacePacketsDefinition> getAllInterfacePacketByMerchantId(Long merchantId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.InterfacePacketsDefinition.MERCHANT_ID, new SearchFilter(
            EntityConstant.InterfacePacketsDefinition.MERCHANT_ID, Operator.EQ, merchantId));
        Specification<InterfacePacketsDefinition> spec_InterfacePackets = DynamicSpecifications.bySearchFilter(
            filters.values(), InterfacePacketsDefinition.class);
        List<InterfacePacketsDefinition> interfacePacketsDefinitions = interfacePacketsDefinitionDao.findAll(spec_InterfacePackets);
        List<InterfacePacketsDefinition> result = new ArrayList<InterfacePacketsDefinition>();
        if (interfacePacketsDefinitions != null && interfacePacketsDefinitions.size() > 0)
        {
            for (InterfacePacketsDefinition interfacePacketsDefinition : interfacePacketsDefinitions)
            {
                List<InterfaceParam> requestParams = interfaceParamService.getInterfaceParamByParams(interfacePacketsDefinition.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST,null);
                interfacePacketsDefinition.setRequestParams(requestParams);
                // 查询responseParams
                List<InterfaceParam> responseParams = interfaceParamService.getInterfaceParamByParams(interfacePacketsDefinition.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE,null);
                interfacePacketsDefinition.setResponseParams(responseParams);
                result.add(interfacePacketsDefinition);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public InterfacePacketsDefinition saveInterfacePacketsDefinition(InterfacePacketsDefinition ifpd)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.InterfacePacketsDefinition.MERCHANT_ID, new SearchFilter(EntityConstant.InterfacePacketsDefinition.MERCHANT_ID, Operator.EQ, ifpd.getMerchantId()));
        filters.put(EntityConstant.InterfacePacketsDefinition.INTERFACE_TYPE, new SearchFilter(EntityConstant.InterfacePacketsDefinition.INTERFACE_TYPE, Operator.EQ, ifpd.getInterfaceType()));
        Specification<InterfacePacketsDefinition> spec_InterfacePackets = DynamicSpecifications.bySearchFilter(filters.values(), InterfacePacketsDefinition.class);
        InterfacePacketsDefinition interfacePacketsDefinition = interfacePacketsDefinitionDao.findOne(spec_InterfacePackets);
        if (interfacePacketsDefinition == null)
        {
            ifpd = interfacePacketsDefinitionDao.save(ifpd);
            interfaceParamService.saveAll(ifpd.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST, ifpd.getRequestParams());
            interfaceParamService.saveAll(ifpd.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE, ifpd.getResponseParams());
            
            InterfacePacketTypeConf requestInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(ifpd.getMerchantId(), ifpd.getInterfaceType(), Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
            ifpd.setRequestInterfacePacketTypeConf(requestInterfacePacketTypeConf);
            
            InterfacePacketTypeConf responseInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(ifpd.getMerchantId(), ifpd.getInterfaceType(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
            ifpd.setResponseInterfacePacketTypeConf(responseInterfacePacketTypeConf);
        }
        else
        {
            ifpd = null;
        }
        return ifpd;
    }
    
    @Override
    public YcPage<InterfacePacketsDefinition> queryInterfacePacketsDefinition(Map<String, Object> searchParams,
                                                                              int pageNumber,
                                                                              int pageSize,
                                                                              BSort bsort)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Page<InterfacePacketsDefinition> page = PageUtil.queryPage(interfacePacketsDefinitionDao,
            filters, pageNumber, pageSize, new Sort(Direction.DESC, bsort.getCloumn()),
            InterfacePacketsDefinition.class);
        YcPage<InterfacePacketsDefinition> ycPage = new YcPage<InterfacePacketsDefinition>();
        ycPage.setList(page.getContent());
        ycPage.setCountTotal((int)page.getTotalElements());
        ycPage.setPageTotal(page.getTotalPages());
        return ycPage;
    }

    @Override
    @Transactional
    public InterfacePacketsDefinition updateInterfacePacketsDefinition(InterfacePacketsDefinition ifpd)
    {
        List<InterfaceParam> requestParams = ifpd.getRequestParams();
        String requestOrResponse = StringUtil.initString();
        
        requestOrResponse = Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST;
        List<InterfaceParam> needDeleteParams = interfaceParamService.getInterfaceParamByParams(ifpd.getId(), requestOrResponse, StringUtil.initString());
        interfaceParamService.deleteAll(ifpd.getId(), requestOrResponse, needDeleteParams);
        if (requestParams != null)
        {
            
            requestParams = interfaceParamService.saveAll(ifpd.getId(), requestOrResponse, requestParams);
        }
        List<InterfaceParam> responseParams = ifpd.getResponseParams();
        requestOrResponse = Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE;
        List<InterfaceParam> needDeleteParams2 = interfaceParamService.getInterfaceParamByParams(ifpd.getId(), requestOrResponse, StringUtil.initString());
        interfaceParamService.deleteAll(ifpd.getId(), requestOrResponse, needDeleteParams2);
        if (responseParams != null)
        {
            responseParams = interfaceParamService.saveAll(ifpd.getId(), requestOrResponse, responseParams);
        }
        ifpd = interfacePacketsDefinitionDao.save(ifpd);
        ifpd.setRequestParams(requestParams);
        ifpd.setResponseParams(responseParams);
        InterfacePacketTypeConf requestInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(ifpd.getMerchantId(), ifpd.getInterfaceType(), Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
        ifpd.setRequestInterfacePacketTypeConf(requestInterfacePacketTypeConf);
        InterfacePacketTypeConf responseInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(ifpd.getMerchantId(), ifpd.getInterfaceType(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
        ifpd.setResponseInterfacePacketTypeConf(responseInterfacePacketTypeConf);
        return ifpd;
    }

    @Override
    public InterfacePacketsDefinition getInterfacePacketsDefinitionById(Long id)
    {
        InterfacePacketsDefinition interfacePacketsDefinition = interfacePacketsDefinitionDao.findOne(id);
        if (interfacePacketsDefinition != null)
        {
            // 查询requestParams
            List<InterfaceParam> requestParams = interfaceParamService.getInterfaceParamByParams(interfacePacketsDefinition.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST,null);
            interfacePacketsDefinition.setRequestParams(requestParams);
            // 查询responseParams
            List<InterfaceParam> responseParams = interfaceParamService.getInterfaceParamByParams(interfacePacketsDefinition.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE,null);
            interfacePacketsDefinition.setResponseParams(responseParams);
            
            InterfacePacketTypeConf requestInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(interfacePacketsDefinition.getMerchantId(), interfacePacketsDefinition.getInterfaceType(), Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
            interfacePacketsDefinition.setRequestInterfacePacketTypeConf(requestInterfacePacketTypeConf);
            
            InterfacePacketTypeConf responseInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(interfacePacketsDefinition.getMerchantId(), interfacePacketsDefinition.getInterfaceType(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
            interfacePacketsDefinition.setResponseInterfacePacketTypeConf(responseInterfacePacketTypeConf);
        }
        return interfacePacketsDefinition;
    }
    
    
    @Override
    public InterfacePacketsDefinitionBo getInterfacePacketsDefinitionByParams(Long merchantId, String interfaceType)
    {
        InterfacePacketsDefinition ipd = null;
        InterfacePacketsDefinitionBo ipdb = new InterfacePacketsDefinitionBo();
        if(BeanUtils.isNotNull(merchantId))
        {
            ipd = interfacePacketsDefinitionDao.queryInterfacePacketsDefinitionByParams(merchantId, interfaceType, Constant.Interface.OPEN);
        }
        else
        {
            ipd = interfacePacketsDefinitionDao.queryInterfacePacketsDefinitionByParams(interfaceType, Constant.Interface.OPEN);
        }
        try
        {
            Assert.notNull(ipd);
            BeanUtils.copyProperties(ipdb, ipd);
        }
        catch (Exception e)
        {
            logger.error("InterfacePacketsDefinitionBo transform failed,caused by:["+ExceptionUtil.getStackTraceAsString(e)+"]");
            throw new ApplicationException("businesss000025", e);
        }
        if (ipdb != null)
        {
            List<InterfaceParam> requestParams = interfaceParamService.getInterfaceParamByParams(ipdb.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST,null);
            ipdb.setRequestParams(requestParams);
            
            List<InterfaceParam> responseSuccessParams = interfaceParamService.getInterfaceParamByParams(ipdb.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE, Constant.Common.SUCCESS);
            ipdb.setResponseSuccessParams(responseSuccessParams);
            
            List<InterfaceParam> responseFailParams = interfaceParamService.getInterfaceParamByParams(ipdb.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE, Constant.Common.FAIL);
            ipdb.setResponseFailParams(responseFailParams);
            
            List<InterfaceParam> responseUnderwayParams = interfaceParamService.getInterfaceParamByParams(ipdb.getId(), Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE,Constant.Common.UNDERWAY);
            ipdb.setResponseUnderwayParams(responseUnderwayParams);
            
            InterfacePacketTypeConf requestInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(merchantId, interfaceType, Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
            ipdb.setRequestInterfacePacketTypeConf(requestInterfacePacketTypeConf);
            
            InterfacePacketTypeConf responseInterfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(merchantId, interfaceType, Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
            ipdb.setResponseInterfacePacketTypeConf(responseInterfacePacketTypeConf);
        }
        return ipdb;
    }
    
    @Override
    public Boolean checkInterfaceIsExist(Long merchantId, String interfaceType)
    {
        try
        {
            InterfacePacketsDefinition ipd = interfacePacketsDefinitionDao.queryInterfacePacketsDefinitionByParams(merchantId, interfaceType, Constant.Interface.OPEN);
            return null!=ipd;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
