package com.yuecheng.hops.injection.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.injection.entity.MerchantRequest;


/**
 * 上游请求规则服务层
 * 
 * @author Jinger 2014-03-26
 */
public interface MerchantRequestService
{
    /**
     * 获取所有上游请求规则列表
     * 
     * @return
     */
    public List<MerchantRequest> getAllMerchantRequest();

    /**
     * 保存上游请求规则
     * 
     * @param merchantRequest
     *            上游请求规则
     * @return
     */
    public MerchantRequest saveMerchantRequest(MerchantRequest merchantRequest);

    /**
     * 根据条件删除上游请求规则
     * 
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     */
    public void deleteMerchantRequest(Long merchantId, String interfaceType);

    /**
     * 更新上游请求规则
     * 
     * @param merchantRequest
     *            上游请求规则
     * @return
     */
    public MerchantRequest updateMerchantRequest(MerchantRequest merchantRequest);

    /**
     * 根据ID获取上游请求规则
     * 
     * @param merchantRequestId
     *            上游请求规则ID
     * @return
     */
    public MerchantRequest getMerchantRequestById(Long merchantRequestId);

    /**
     * 分页查询上游请求规则
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
    public YcPage<MerchantRequest> queryMerchantRequest(Map<String, Object> searchParams,
                                                        int pageNumber, int pageSize, BSort bsort);

    /**
     * 批量保存上游请求规则
     * 
     * @param merchantRequestList
     *            上游请求规则列表
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     */
    public void saveMerchantRequestList(List<MerchantRequest> merchantRequestList,
                                        Long merchantId, String interfaceType);

    /**
     * 根据条件获取上游请求规则列表
     * 
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     * @return
     */
    public List<MerchantRequest> getMerchantRequestByParams(Long merchantId, String interfaceType);

    /**
     * 获取下次上游查询时间
     * 
     * @param pastTime
     *            时间间隔
     * @param now
     *            当前时间
     * @param merchantId
     *            商户ID
     * @param interfaceType
     *            接口类型
     * @return
     */
    public Date getNextQueryDate(Date pastTime, Date now, Long merchantId, String interfaceType);
}
