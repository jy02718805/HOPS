/*
 * 文件名：InterfaceParamServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年12月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.injection.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.InterfaceParam;
import com.yuecheng.hops.injection.repository.InterfaceParamDao;
import com.yuecheng.hops.injection.service.InterfaceParamService;

@Service("interfaceParamService")
public class InterfaceParamServiceImpl implements InterfaceParamService
{
    @Autowired
    private InterfaceParamDao interfaceParamDao;

    @Override
    public List<InterfaceParam> getInterfaceParamByParams(Long interfaceDefinitionId,
                                                          String connectionModule, String responseResult)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        Sort sort = new Sort(Direction.valueOf(Direction.class, BSort.Direct.ASC.toString()), EntityConstant.InterfaceParam.SEQUENCE);
        // 查询requestParams
        if(BeanUtils.isNotNull(interfaceDefinitionId))
        {
            filters.put(EntityConstant.InterfaceParam.INTERFACE_DEFINITION_ID, new SearchFilter(EntityConstant.InterfaceParam.INTERFACE_DEFINITION_ID, Operator.EQ, interfaceDefinitionId));
        }
        if(StringUtil.isNotBlank(connectionModule))
        {
            filters.put(EntityConstant.InterfaceParam.CONNECTION_MODULE, new SearchFilter(EntityConstant.InterfaceParam.CONNECTION_MODULE, Operator.EQ, connectionModule));
        }
        if(StringUtil.isNotBlank(responseResult))
        {
            filters.put(EntityConstant.InterfaceParam.RESPONSE_RESULT, new SearchFilter( EntityConstant.InterfaceParam.RESPONSE_RESULT, Operator.EQ, responseResult));
        }
        Specification<InterfaceParam> spec_requestParams = DynamicSpecifications.bySearchFilter(filters.values(), InterfaceParam.class);
        List<InterfaceParam> params = interfaceParamDao.findAll(spec_requestParams,sort);
        return params;
    }


    @Override
    @Transactional
    public List<InterfaceParam> saveAll(Long definitionId, String requestOrResponse, List<InterfaceParam> interfaceParams)
    {
        if(BeanUtils.isNotNull(interfaceParams)){
            for(InterfaceParam interfaceParam : interfaceParams){
                interfaceParam.setInterfaceDefinitionId(definitionId);
                interfaceParam.setConnectionModule(requestOrResponse);
                if(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(requestOrResponse) && StringUtil.isBlank(interfaceParam.getResponseResult()))
                {
                    interfaceParam.setResponseResult(Constant.Common.SUCCESS);
                }
                interfaceParamDao.save(interfaceParam);
            }
        }
        return interfaceParams;
    }

    @Override
    @Transactional
    public void deleteAll(Long definitionId, String requestOrResponse, List<InterfaceParam> interfaceParams)
    {
        interfaceParamDao.delete(interfaceParams);
    }

}
