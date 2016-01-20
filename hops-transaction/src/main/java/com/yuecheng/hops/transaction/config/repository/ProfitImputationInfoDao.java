package com.yuecheng.hops.transaction.config.repository;

import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;

public interface ProfitImputationInfoDao
{
    public YcPage<ProfitImputationInfo> queryProfitImputationInfos(Map<String, Object> searchParams,
        int pageNumber, int pageSize);
}
