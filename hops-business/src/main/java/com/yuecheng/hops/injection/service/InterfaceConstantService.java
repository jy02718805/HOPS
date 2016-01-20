package com.yuecheng.hops.injection.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.injection.entity.InterfaceConstant;


public interface InterfaceConstantService
{
    /**
     * 根据条件查询接口常量配置列表
     * 
     * @param identityId
     *            商户ID
     * @param identityType
     *            商户类型
     * @return
     */
    public List<InterfaceConstant> getInterfaceConstantByParams(Long identityId,
                                                                String identityType);

    /**
     * 保存接口常量配置
     * 
     * @param interfaceConstant
     *            接口常量配置
     * @return
     */
    public InterfaceConstant saveInterfaceConstant(InterfaceConstant interfaceConstant);

    /**
     * 根据ID删除接口常量配置
     * 
     * @param interfaceConstantId
     *            接口常量配置ID
     */
    public InterfaceConstant deleteInterfaceConstant(Long interfaceConstantId);

    /**
     * 更新接口常量配置
     * 
     * @param interfaceConstant
     *            接口常量配置
     * @return
     */
    public InterfaceConstant updateInterfaceConstant(InterfaceConstant interfaceConstant);

    /**
     * 根据ID获取接口常量配置
     * 
     * @param interfaceConstantId
     *            接口常量配置ID
     * @return
     */
    public InterfaceConstant getInterfaceConstantById(Long interfaceConstantId);

    /**
     * 分页查询接口常量配置列表
     * 
     * @param searchParams
     *            查询条件map
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @return
     */
    public YcPage<InterfaceConstant> queryInterfaceConstant(Map<String, Object> searchParams,
                                                            int pageNumber, int pageSize,
                                                            BSort bsort);

    /**
     * 根据条件查询接口常量配置
     * 
     * @param identityId
     *            商户ID
     * @param identityType
     *            商户类型
     * @param key
     *            关键字
     * @return
     */
    public InterfaceConstant getInterfaceConstant(Long identityId, String identityType, String key);
}
