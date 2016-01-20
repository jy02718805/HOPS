/*
 * 文件名：RebateRecordDaoSql.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.rebate.entity.RebateRecord;

public interface RebateRecordDaoSql
{
    public List<RebateRecord> queryRebateRecordByParams(Long merchantId,Long rebateMerchantId,Date rebateDate,String rebateProductId,Long rebateRuleId );
    
    public YcPage<RebateRecord> queryPageRebateRecord(Map<String, Object> searchParams,
            Date beginDate, Date endDate, int pageNumber,int pageSize, BSort bsort);
}
