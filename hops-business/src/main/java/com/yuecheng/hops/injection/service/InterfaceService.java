package com.yuecheng.hops.injection.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinition;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;


public interface InterfaceService
{
    /**
     * 根据商户ID获取所有配置信息
     * 
     * @param merchantId
     *            商户ID
     * @return
     */
    public List<InterfacePacketsDefinition> getAllInterfacePacketByMerchantId(Long merchantId);

    /**
     * 保存接口信息
     * 
     * @param ifpd
     *            接口详细信息
     * @return
     */
    public InterfacePacketsDefinition saveInterfacePacketsDefinition(InterfacePacketsDefinition ifpd);

    /**
     * 根据ID获取接口详细信息
     * 
     * @param id
     *            接口详细信息ID
     * @return
     */
    public InterfacePacketsDefinition getInterfacePacketsDefinitionById(Long id);

    /**
     * 更新接口详细信息
     * 
     * @param ifpd
     *            接口详细信息
     * @return
     */
    public InterfacePacketsDefinition updateInterfacePacketsDefinition(InterfacePacketsDefinition ifpd);

    /**
     * 分页查询接口详细信息列表
     * 
     * @param searchParams
     *            分页查询条件Map
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @return
     */
    public YcPage<InterfacePacketsDefinition> queryInterfacePacketsDefinition(Map<String, Object> searchParams,
                                                                              int pageNumber,
                                                                              int pageSize,
                                                                              BSort bsort);
    
    /**
     * 根据商户编号与接口类型，获取接口定义。交易时调用，AOP缓存
     * 
     * @param merchantId
     * @param interfaceType
     * @return 
     * @see
     */
    public InterfacePacketsDefinitionBo getInterfacePacketsDefinitionByParams(Long merchantId, String interfaceType);
    
    /**
     * 检查接口是否存在
     * 
     * @param merchantId
     * @param interfaceType
     * @return 
     * @see
     */
    public Boolean checkInterfaceIsExist(Long merchantId, String interfaceType);
}
