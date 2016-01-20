package com.yuecheng.hops.transaction.service.profitImputation;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;
import com.yuecheng.hops.transaction.config.entify.profitImputation.vo.ProfitImputationVo;


/**
 * 利润归集
 * 
 * @author AdministratorimputationProfits
 * @version 2014年10月23日
 * @see ProfitImputationInfoService
 * @since
 */
public interface ProfitImputationInfoService
{
    /**
     * 分页查询利润归集帐户
     * 
     * @param searchParams
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    YcPage<ProfitImputationVo> queryProfitImputationInfos(Map<String, Object> searchParams,
                                                          int pageNumber, int pageSize);

    /**
     * 保存利润归集账户
     * 
     * @param profitImputation
     * @return
     */
    ProfitImputationInfo saveProfitImputation(ProfitImputationInfo profitImputation);

    /**
     * 保存利润归集账户集合
     * 
     * @param profitImputation
     * @return
     */
    List<ProfitImputationInfo> saveProfitImputations(List<ProfitImputationInfo> profitImputation);

    /**
     * 获取利润归集账户
     * 
     * @param profitImputationId
     * @return
     */
    ProfitImputationInfo getProfitImputationInfoById(Long profitImputationId);

    /**
     * 归集账户
     * 
     * @param profitImputationInfo
     * @return
     * @see
     */
    boolean isAccountImputation(ProfitImputationInfo profitImputationInfo);
}
