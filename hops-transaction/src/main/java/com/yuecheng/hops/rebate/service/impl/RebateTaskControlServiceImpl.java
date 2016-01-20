/*
 * 文件名：RebateTaskControlServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年2月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.rebate.entity.RebateTaskControl;
import com.yuecheng.hops.rebate.repository.RebateTaskControlDao;
import com.yuecheng.hops.rebate.service.RebateTaskControlService;

@Service("rebateTaskControlService")
public class RebateTaskControlServiceImpl implements RebateTaskControlService{

    @Autowired
    private RebateTaskControlDao rebateTaskControlDao;
    
    private static Logger logger = LoggerFactory.getLogger(RebateTaskControlServiceImpl.class);

    private SimpleDateFormat controlIdformat = new SimpleDateFormat("yyyyMMdd");
    
	@Override
	public RebateTaskControl saveRebateTaskControl(
			RebateTaskControl rebateTaskControl) {
		logger.debug("[RebateTaskControlServiceImpl:saveRebateTaskControl("+ (BeanUtils.isNotNull(rebateTaskControl ) ? rebateTaskControl.toString() :StringUtil.initString())+ ")]");
		rebateTaskControl=rebateTaskControlDao.save(rebateTaskControl);
		logger.debug("[RebateTaskControlServiceImpl:saveRebateTaskControl(" + (BeanUtils.isNotNull(rebateTaskControl ) ? rebateTaskControl.toString() :StringUtil.initString())
	            + ")][返回信息]");
		return rebateTaskControl;
	}

	@Override
	public void delRebateTaskControlById(String rebateTaskControlId) {
		logger.debug("[RebateTaskControlServiceImpl:delRebateTaskControlById("+ rebateTaskControlId+ ")]");
		if(BeanUtils.isNotNull(rebateTaskControlId))
		{
			rebateTaskControlDao.delete(rebateTaskControlId);
		}else{
			logger.error("[RebateTaskControlServiceImpl:delRebateTaskControlById() 返佣任务ID为空]");
		   String[] msgParams = new String[] {"delRebateTaskControlById"};
		   ApplicationException ae = new ApplicationException("transaction002041", msgParams);
		   throw ExceptionUtil.throwException(ae);
		}
	}

	@Override
	public RebateTaskControl updateRebateTaskControl(
			RebateTaskControl rebateTaskControl) {
		logger.debug("[RebateTaskControlServiceImpl:updateRebateTaskControl("+ (BeanUtils.isNotNull(rebateTaskControl ) ? rebateTaskControl.toString() :StringUtil.initString())+ ")]");
		if(BeanUtils.isNotNull(rebateTaskControl)&&BeanUtils.isNotNull(rebateTaskControl.getRebateControlId()))
		{
			rebateTaskControl=rebateTaskControlDao.save(rebateTaskControl);
			logger.debug("[RebateTaskControlServiceImpl:updateRebateTaskControl(" + (BeanUtils.isNotNull(rebateTaskControl ) ? rebateTaskControl.toString() :StringUtil.initString())
		            + ")][返回信息]");
			return rebateTaskControl;
		}else{
			logger.error("[RebateTaskControlServiceImpl:updateRebateTaskControl() 需要更新的数据或ID为空]");
		   String[] msgParams = new String[] {"updateRebateTaskControl"};
		   ApplicationException ae = new ApplicationException("transaction002042", msgParams);
		   throw ExceptionUtil.throwException(ae);
		}
	}

	@Override
	public RebateTaskControl queryRebateTaskControlById(String rebateTaskControlId) {
		logger.debug("[RebateTaskControlServiceImpl:queryRebateTaskControlById("+ rebateTaskControlId+ ")]");
		if(BeanUtils.isNotNull(rebateTaskControlId))
		{
			RebateTaskControl rebateTaskControl=rebateTaskControlDao.findOne(rebateTaskControlId);
			return rebateTaskControl;
		}else{
			logger.error("[RebateTaskControlServiceImpl:queryRebateTaskControlById() 返佣任务ID为空]");
		   String[] msgParams = new String[] {"queryRebateTaskControlById"};
		   ApplicationException ae = new ApplicationException("transaction002041", msgParams);
		   throw ExceptionUtil.throwException(ae);
		}
	}

	@Override
	public RebateTaskControl queryRebateTaskControlByRebateDate(Date rebateDate) {
		logger.debug("[RebateTaskControlServiceImpl:queryRebateTaskControlByRebateDate("+ rebateDate+ ")]");
		if(BeanUtils.isNotNull(rebateDate))
		{
			RebateTaskControl rebateTaskControl=rebateTaskControlDao.getRebateTaskControlByRebateDate(rebateDate);
			return rebateTaskControl;
		}else{
			logger.error("[RebateTaskControlServiceImpl:queryRebateTaskControlByRebateDate() 返佣任务时间为空]");
		   String[] msgParams = new String[] {"queryRebateTaskControlByRebateDate"};
		   ApplicationException ae = new ApplicationException("transaction002043", msgParams);
		   throw ExceptionUtil.throwException(ae);
		}
	}

	@Override
	public RebateTaskControl createrebateTaskControl(Date rebateTime,
			String remark) {
		String controlId=controlIdformat.format(rebateTime);
    	logger.debug("[RebateTaskControlServiceImpl:createrebateTaskControl("+rebateTime+","+remark+")]:新增返佣任务ID："+controlId);
    	Date createTime=new Date();
    	RebateTaskControl rebateTaskControl=new RebateTaskControl();
    	rebateTaskControl.setRebateControlId(controlId);
    	rebateTaskControl.setCreateDate(createTime);
    	rebateTaskControl.setUpdateDate(createTime);
    	rebateTaskControl.setRebateDate(rebateTime);
    	rebateTaskControl.setStatus(Constant.RebateStatus.DISABLE);
    	rebateTaskControl.setRemark(remark);
    	rebateTaskControl=rebateTaskControlDao.save(rebateTaskControl);
    	logger.debug("[RebateTaskControlServiceImpl:createrebateTaskControl(" + (BeanUtils.isNotNull(rebateTaskControl ) ? rebateTaskControl.toString() :StringUtil.initString())
	            + ")][返回信息]");
    	return rebateTaskControl;
	}

}
