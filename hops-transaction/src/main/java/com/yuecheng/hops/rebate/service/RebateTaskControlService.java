/*
 * 文件名：RebateTaskControlService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年2月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.service;

import java.util.Date;

import com.yuecheng.hops.rebate.entity.RebateTaskControl;

public interface RebateTaskControlService {
	/**
	 * 根据条件创建任务
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param rebateTime
	 * @param remark
	 * @return 
	 * @see
	 */
	public RebateTaskControl createrebateTaskControl(Date rebateTime,String remark);

	/**
	 * 添加返佣任务
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param rebateTaskControl
	 * @return 
	 * @see
	 */
	public RebateTaskControl saveRebateTaskControl(RebateTaskControl rebateTaskControl);
	/**
	 * 删除返佣任务
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param rebateTaskControlId 
	 * @see
	 */
	public void delRebateTaskControlById(String rebateTaskControlId);
	/**
	 * 更新返佣任务表
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param rebateTaskControl
	 * @return 
	 * @see
	 */
	public RebateTaskControl updateRebateTaskControl(RebateTaskControl rebateTaskControl);
	/**
	 * 根据ID查询返佣任务
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param rebateTaskControlId
	 * @return 
	 * @see
	 */
	public RebateTaskControl queryRebateTaskControlById(String rebateTaskControlId);
	/**
	 * 根据时间查询返佣任务
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param rebateDate
	 * @return 
	 * @see
	 */
	public RebateTaskControl queryRebateTaskControlByRebateDate(Date rebateDate);
}
