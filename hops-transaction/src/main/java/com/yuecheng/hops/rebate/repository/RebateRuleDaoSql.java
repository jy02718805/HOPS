/*
 * 文件名：RebateRuleDaoSql.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.repository;

import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.rebate.entity.RebateRule;

public interface RebateRuleDaoSql
{
    public YcPage<RebateRule> queryPageRebateRule(Map<String, Object> searchParams, int pageNumber,
        int pageSize);
}
