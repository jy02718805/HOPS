/*
 * 文件名：CheckProductForPrepareOrderHandler.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yuanbiao
 * 修改时间：2015年4月10日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.check;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;

@Service("checkProductForPrepareOrderHandler")
public class CheckProductForPrepareOrderHandler extends ActionHandler {

	private static Logger logger = LoggerFactory
			.getLogger(CheckOrderParamsHandler.class);

	/**
	 * 处理方法，调用此方法处理请求
	 */
	@Override
	public void handleRequest() throws HopsException {
		try {
			Verify();
			logger.debug("检查订单必输项！ 通过");
		} catch (HopsException e) {
			throw e;
		} catch (Exception e) {
			logger.error("检查订单必输项，失败！");
			throw new ApplicationException(Constant.ErrorCode.MANUAL);
		}

	}

	public void Verify() throws HopsException {

		Order order = (Order) ActionContextUtil
				.getActionContextParam(ActionMapKey.ORDER);
		String sign = (String) ActionContextUtil
				.getActionContextParam(ActionMapKey.SIGN);

		String signType = (String) ActionContextUtil
				.getActionContextParam(ActionMapKey.SIGNTYPE);

		if (null == order.getMerchantId()) {
			logger.error("agentId不可以为空");
			throw new ApplicationException(Constant.ConInterface.AGENTID_NULL);
		}

		if (StringUtil.isEmpty(order.getUserCode())) {
			logger.error("充值手机号码不可以为空：" + order.getMerchantId());
			throw new ApplicationException(Constant.ConInterface.MOBILE_NULL);
		}

		if (order.getOrderFee() == null) {
			logger.error("充值面额不能为空：" + order.getMerchantId());
			throw new ApplicationException(
					Constant.ConInterface.FACE_MONEY_NULL);
		}
		// 默认加密方式为md5 1
		if (StringUtils.isEmpty(signType) || !signType.equals("1")) {
			logger.error("加密方式参数传递错误：" + order.getMerchantId());
			throw new ApplicationException(
					Constant.ConInterface.ENCRYPT_PARAM_ERROR);
		}
		if (StringUtils.isEmpty(sign)) {
			logger.error(" 签名不能为空：" + order.getMerchantId());
			throw new ApplicationException(Constant.ConInterface.SIGN_NULL);
		}

	}
}
