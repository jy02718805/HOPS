package com.yuecheng.hops.product.service;

import java.util.List;

import com.yuecheng.hops.product.entity.history.ProductOperationRuleBak;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationRuleAssist;

public interface ProductOperationRuleBakService {

	/**
	 * 根据规则ID查询相关规则列表并查询出MerchantName
	 * 
	 * @param historyId 任务ID
	 * @return 
	 * @see
	 */
	public List<ProductOperationRuleAssist> queryProductOperationRuleAssistByHisId(Long historyId);
	
	/**
	 * 根据规则ID查询相关规则列表
	 * 
	 * @param historyId
	 * @return 
	 * @see
	 */
	public List<ProductOperationRuleBak> queryProductOperationRuleByHisId(Long historyId);

}
