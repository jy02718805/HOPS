package com.yuecheng.hops.injection.service;


import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;


/**
 * 商户接口（发送、返回）报文格式定义
 */
public interface InterfacePacketTypeConfService
{
    /**
     * 保存商户接口报文格式定义
     * 
     * @param interfacePacketTypeConf
     *            商户接口报文格式定义
     * @return
     */
    public InterfacePacketTypeConf saveInterfacePacketTypeConf(InterfacePacketTypeConf interfacePacketTypeConf);

    /**
     * 根据ID删除商户接口报文格式定义
     * 
     * @param interfacePacketTypeConfId
     *            商户接口报文格式定义ID
     */
    public void deleteInterfacePacketTypeConf(Long interfacePacketTypeConfId);

    /**
     * 更新商户接口报文格式定义
     * 
     * @param interfacePacketTypeConf
     *            商户接口报文格式定义
     * @return
     */
    public InterfacePacketTypeConf updateInterfacePacketTypeConf(InterfacePacketTypeConf interfacePacketTypeConf);

    /**
     * 根据条件查询商户接口报文格式定义
     * 
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     * @param connectionModule
     *            连接方式HttpOrSocket
     * @return
     */
    public InterfacePacketTypeConf getInterfacePacketTypeConfByParams(Long merchantId,
                                                                      String interfaceType,
                                                                      String connectionModule);

//    /**
//     * 根据接口类型，查询接口报文定义
//     * 
//     * @param interfaceType
//     * @return
//     */
//    public InterfacePacketTypeConf getInterfacePacketTypeConfByInterfaceType(String interfaceType,
//                                                                             String connectionModule);

}
