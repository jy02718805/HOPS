package com.yuecheng.hops.rebate.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.rebate.entity.RebateRecordHistory;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordHistoryAssist;


public interface RebateRecordHistoryService
{
    /**
     * 保存返佣数据
     * 
     * @param rebateRecordHistory
     * @return
     */
    public RebateRecordHistoryAssist saveRebateRecordHistory(RebateRecordHistory rebateRecordHistory);

    /**
     * 根据返佣数据ID删除返佣数据
     * 
     * @param rebateRecordHistoryId
     */
    public void deleteRebateRecordHistory(Long rebateRecordHistoryId);

    /**
     * 根据返佣ID查找返佣数据
     * 
     * @param rebateRecordHistoryId
     * @return
     */
    public RebateRecordHistoryAssist queryRebateRecordHistoryById(Long rebateRecordHistoryId);

    /**
     * 获取所有返佣数据列表
     * 
     * @return
     */
    public List<RebateRecordHistoryAssist> queryAllRebateRecordHistory();

    /**
     * 根据条件分页获取返佣数据列表信息
     */
    public YcPage<RebateRecordHistoryAssist> queryPageRebateRecordHistory(Map<String, Object> searchParams,
                                                                int pageNumber, int pageSize,
                                                                BSort bsort,Date beginTime,Date endTime);
}
