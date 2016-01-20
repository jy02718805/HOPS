package com.yuecheng.hops.product.service;

import java.util.List;

import com.yuecheng.hops.product.entity.history.ProductOperationHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationRule;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationRuleAssist;

public interface ProductOperationRuleService {
	/**
	 * 保存产品批量开关任务规则
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param productOperationRule 任务规则
	 * @return 
	 * @see
	 */
	public ProductOperationRule saveProductOperationRule(ProductOperationRule productOperationRule);
	/**
	 * 根据任务ID查询相关规则列表
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param historyId 任务ID
	 * @return 
	 * @see
	 */
	public List<ProductOperationRule> queryProductOperationRuleByHisId(Long historyId);
	/**
	 * 根据规则ID查询相关规则列表并查询出MerchantName
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param historyId 任务ID
	 * @return 
	 * @see
	 */
	public List<ProductOperationRuleAssist> queryProductOperationRuleAssistByHisId(Long historyId);
	/**
	 * 根据任务信息和商户ID字符批量保存规则信息
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param productOperationHistory 任务信息
	 * @param merchantIds 商户ID
	 * @param updateUser 更新人
	 * @return 
	 * @see
	 */
	public List<ProductOperationRule> saveProductOperationRules(ProductOperationHistory productOperationHistory,String merchantIds,String updateUser);
	
	/**
	 * 根据任务ID删掉所有相关规则
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param historyId
	 * @return 
	 * @see
	 */
	public void delProductOperationRule(Long historyId);

}
