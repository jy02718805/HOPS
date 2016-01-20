/*
 * 文件名：CheckSynProductStatusInterface.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：machenike
 * 修改时间：2015年7月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.check;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Service("checkSynProductStatusIntfExistsHandler")
public class CheckSynProductStatusIntfExistsHandler extends ActionHandler
{
	private static Logger logger = LoggerFactory.getLogger(CheckSynProductStatusIntfExistsHandler.class);
	@Autowired
	private InterfaceService interfaceService;

	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest() throws HopsException
	{
		Boolean checkFlag = false;
		try
		{
			Map<String, Object> fileds = (Map<String, Object>) ActionContextUtil
					.getActionContextParam(ActionMapKey.REQUEST_MAP);

			if (BeanUtils.isNotNull(fileds.get(ActionMapKey.MERCHANT_ID)))
			{
				Long merchantId = (Long) fileds.get(ActionMapKey.MERCHANT_ID);
				checkFlag = interfaceService.checkInterfaceIsExist(merchantId,
						Constant.Interface.INTERFACE_TYPE_SYN_PRODUCT_STATUS_TO_AGENT);
				logger.debug("是否同步产品状态到商户" + checkFlag);

				if (checkFlag)
				{
					logger.debug("商户ID[" + merchantId + "] 配置了同步商品接口,需要同步商品状态到商户");
					ActionContextUtil.setActionContext(ActionMapKey.INTERFACE_TYPE,
							Constant.Interface.INTERFACE_TYPE_SYN_PRODUCT_STATUS_TO_AGENT);
				}
				else
				{
					logger.debug("商户ID[" + merchantId + "] 没有配置同步商品接口,不需要同步商品状态到商户");
				}
			}
		}
		catch (Exception e)
		{
			logger.error("检查是否同步产品状态到商户，失败！失败原因:[" + ExceptionUtil.getStackTraceAsString(e) + "]");
		}

	}
}
