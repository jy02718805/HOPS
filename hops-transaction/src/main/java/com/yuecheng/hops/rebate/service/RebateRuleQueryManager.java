/*
 * 文件名：RebateRuleQueryManager.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月23日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.rebate.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.assist.RebateRuleAssist;


public interface RebateRuleQueryManager
{

    /**
     * 根据返佣产品ID和返佣时间类型获取返佣列表
     * 
     * @param rebateProductId
     * @param rebateTimeType
     * @return
     */
    public  List<RebateRuleAssist> queryRebateRuleList(String rebateProductId,
                                                               Long rebateTimeType);

    /**
     * 根据商户ID和商户类型获取返佣列表
     * 
     * @param merchantId
     * @param merchantType
     * @return
     */
    public  List<RebateRuleAssist> queryRebateRulesByParams(Long merchantId,
                                                                    String merchantType);

    /**
     * 根据发生商户ID和返佣商户ID获取返佣配置列表
     * 
     * @param merchantId
     * @param rebateMerchantId
     * @return
     */
    public  List<RebateRuleAssist> queryRebateRuleByParams(Long merchantId,
                                                                   Long rebateMerchantId);

    /**
     * 分页查询返佣配置
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public  YcPage<RebateRuleAssist> queryPageRebateRule(Map<String, Object> searchParams,
                                                                 int pageNumber, int pageSize,
                                                                 BSort bsort);

    /**
     * 根据返佣产品区间ID获取返佣配置列表
     * 
     * @param rebateProductId
     * @return
     */
    public  List<RebateRuleAssist> queryRebateRuleByRPId(String rebateProductId);

    /**
     * 根据商户ID获取返佣配置列表
     * 
     * @param merchantId
     * @return
     */
    public  List<RebateRuleAssist> queryRebateRuleByMId(Long merchantId);

    /**
     * 根据商户ID和状态获取返佣配置列表
     * 
     * @param merchantId
     * @param status
     * @return
     */
    public  List<RebateRuleAssist> queryRebateRuleByParams(Long merchantId, String status);
    public List<RebateRuleAssist> getAllName(List<RebateRule> listRebateRule);
    public RebateRuleAssist getAllName(RebateRule rebateRule);

}