package com.yuecheng.hops.transaction.config;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.config.entify.query.SupplyQueryTactics;


/**
 * 设置上游商户查询规则
 * 
 * @author hope
 */
public interface SupplyQueryTacticsService
{

    /**
     * 分页查询上游商户查询规则
     * 
     * @param searchParams
     *            查询条件Map
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    public YcPage<SupplyQueryTactics> querySupplyQueryTactics(Map<String, Object> searchParams,
                                                              int pageNumber, int pageSize,
                                                              String sortType);

    /**
     * 根据ID查询上游商户查询规则
     * 
     * @param supplyqtId
     *            上游商户查询规则ID　
     * @return
     */
    public SupplyQueryTactics getSupplyQueryTactics(Long supplyqtId);

    /**
     * 根据实体对象条件查询上游商户查询规则列表
     * 
     * @param supplyqt
     *            上游商户查询规则
     * @return
     */
    public List<SupplyQueryTactics> querySupplyQueryTactics(SupplyQueryTactics supplyqt);

    /**
     * 保存上游商户查询规则
     * 
     * @param supplyqt
     *            上游商户查询规则
     * @return
     */
    public SupplyQueryTactics saveSupplyQueryTactics(SupplyQueryTactics supplyqt);

    /**
     * 删除上游商户查询规则
     * 
     * @param supplyqt
     *            上游商户查询规则
     */
    public void delSupplyQueryTactics(SupplyQueryTactics supplyqt);

    /**
     * 根据商户ID查询上游商户查询规则
     * 
     * @param merchantId
     *            商户ID
     * @return
     */
    public SupplyQueryTactics querySupplyQueryTacticsByMerchantId(Long merchantId);
}
