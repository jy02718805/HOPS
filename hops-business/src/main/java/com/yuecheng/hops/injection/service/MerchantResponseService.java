package com.yuecheng.hops.injection.service;


import java.util.List;

import com.yuecheng.hops.injection.entity.MerchantResponse;


/**
 * 上游返回码规则服务层
 * 
 * @author Jinger 2014-03-26
 */
public interface MerchantResponseService
{

    /**
     * 批量保存上游返回码规则
     * 
     * @param merchantResponseList
     *            上游返回码规则列表
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     */
    public List<MerchantResponse> saveMerchantResponseList(List<MerchantResponse> merchantResponseList,
                                         Long merchantId, String interfaceType);

    /**
     * 根据条件获取上游返回码规则列表
     * 
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     * @return
     */
    public List<MerchantResponse> getMerchantResponseListByParams(Long merchantId, String interfaceType);

    /**
     * @param merchantId
     * @param interfaceType
     * @param errorCode
     * @param merchantStatus
     * @return
     */
    public MerchantResponse getMerchantResponseByParams(Long merchantId, String interfaceType,
                                                        String errorCode, String merchantStatus);

}
