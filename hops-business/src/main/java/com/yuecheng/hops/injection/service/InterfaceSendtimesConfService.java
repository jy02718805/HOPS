package com.yuecheng.hops.injection.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.injection.entity.InterfaceSendtimesConf;


public interface InterfaceSendtimesConfService
{
    /**
     * 获取所有接口发送次数配置列表
     * 
     * @return
     */
    public List<InterfaceSendtimesConf> getAllInterfaceSendtimesConf();

    /**
     * 保存接口发送次数配置
     * 
     * @param interfaceSendtimesConf
     *            接口发送次数配置
     * @return
     */
    public InterfaceSendtimesConf saveInterfaceSendtimesConf(InterfaceSendtimesConf interfaceSendtimesConf);

    /**
     * 根据ID删除接口发送次数配置
     * 
     * @param interfaceSendtimesConfId
     *            接口发送次数配置ID
     */
    public void deleteInterfaceSendtimesConf(Long interfaceSendtimesConfId);

    /**
     * 更新接口发送次数配置
     * 
     * @param interfaceSendtimesConf
     *            接口发送次数配置
     * @return
     */
    public InterfaceSendtimesConf updateInterfaceSendtimesConf(InterfaceSendtimesConf interfaceSendtimesConf);

    /**
     * 根据ID获取接口发送次数配置
     * 
     * @param interfaceSendtimesConfId
     *            接口发送次数配置ID
     * @return
     */
    public InterfaceSendtimesConf getInterfaceSendtimesConfById(Long interfaceSendtimesConfId);

    /**
     * 分页查询接口发送次数配置列表
     * 
     * @param searchParams
     *            查询条件Map
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @return
     */
    public YcPage<InterfaceSendtimesConf> queryInterfaceSendtimesConf(Map<String, Object> searchParams,
                                                                      int pageNumber,
                                                                      int pageSize, BSort bsort);

    /**
     * 根据条件查询接口发送次数配置
     * 
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     * @return
     */
    public InterfaceSendtimesConf getInterfaceSendtimesConfByParams(Long merchantId,
                                                                    String interfaceType);
}
