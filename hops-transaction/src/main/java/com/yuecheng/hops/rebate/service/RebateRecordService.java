package com.yuecheng.hops.rebate.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.rebate.entity.RebateRecord;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordAssist;


public interface RebateRecordService
{

    /**
     * 根据条件分页查询
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param searchParams
     * @param beginDate
     * @param endDate
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return 
     * @see
     */
    public YcPage<RebateRecordAssist> queryPageRebateRecord(Map<String, Object> searchParams,
                                                  Date beginDate, Date endDate, int pageNumber,
                                                  int pageSize, BSort bsort);

    /**
     * 手动创建返佣记录
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param merchantId
     * @param merchantType
     * @param rebate_rule_id
     * @param beginTime
     * @param endTime
     * @param rebateRecordId
     * @return 
     * @see
     */
    public RebateRecordAssist createRebateRecord(Long merchantId, MerchantType merchantType,
                                           Long rebate_rule_id, Date beginTime, Date endTime,
                                           Long rebateRecordId);

    /**
     * 批量定时创建返佣记录
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param createTime 
     * @see
     */
    public void createRebateRecords(Date createTime);

    /**
     * 在未返佣之前，可以删除返佣记录。并重新生成
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param id 
     * @see
     */
    public void deleteRebateRecord(Long id);

    /**
     * 获取所有的返佣记录
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @return 
     * @see
     */
    public List<RebateRecordAssist> getAllRebateRecord();

    /**更新返佣记录
     * 
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param rebateRecord
     * @return 
     * @see
     */
    public RebateRecordAssist updateRebateRecord(RebateRecord rebateRecord);

    /**
     * 根据返佣商户，获取所有已经配置了的被返佣商户
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param merchantId
     * @return 
     * @see
     */
    public List<Merchant> queryRebateMerchantsByMerchantId(Long merchantId);

    /**
     * 根据ID获取返佣数据
     * 
     * @param id
     * @return
     */
    public RebateRecordAssist queryRebateRecordById(Long id);

    /**
     * 获得该商户时间范围内的返佣信息
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param merchantId
     * @param beginDate
     * @param endDate
     * @return 
     * @see
     */
    public List<RebateRecordAssist> queryRebateRecordsByMerchantIdAndDate(Long merchantId, Date beginDate,
                                                                  Date endDate);

    /**
     * 生成数据账户资金流向
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param payerIdentityId
     * @param payerIdentityType
     * @param payerAccountType
     * @param payeeIdentityId
     * @param payeeIdentityType
     * @param payeeAccountType
     * @param amt
     * @param desc 
     * @see
     */
    public void doRebateTransfer(Long transactionNo,Long payerIdentityId, String payerIdentityType,
                              Long payerAccountType, Long payeeIdentityId,
                              String payeeIdentityType, Long payeeAccountType, BigDecimal amt,String type,
                              String desc);
}
