/*
 * 文件名：SynProductStatusListener.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：machenike
 * 修改时间：2015年7月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.event.SynProductStatusEvent;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;

@Component("synProductStatusListener")
public class SynProductStatusListener implements ApplicationListener<SynProductStatusEvent>
{
	@Autowired
	@Qualifier("gatewayAction")
	private AbstractEventAction gatewayAction;

	@Autowired
	@Qualifier("checkSynProductStatusIntfExistsHandler")
	private AbstractEventAction checkSynProductStatusIntfExistsHandler;

	private static Logger logger = LoggerFactory.getLogger(SynProductStatusListener.class);

	@Override
	@Async
	public void onApplicationEvent(SynProductStatusEvent event)
	{
		try
		{
			if (StringUtil.isNullOrEmpty(event.getMerchantProdCode()))
			{
				logger.debug("同步商品状态到商户,接收外部编号为空,不发送报文. 状态: [" + event.getStatus() + "]");
				return;
			}
			String targetStatus = StringUtil.initString();
			if (Constant.AgentProductStatus.OPEN_STATUS.equals(event.getStatus()))
			{
				targetStatus = Constant.AgentTargetProductStatus.TARGET_OPEN_STATUS;

			}
			else if (Constant.AgentProductStatus.CLOSE_STATUS.equals(event.getStatus()))
			{
				targetStatus = Constant.AgentTargetProductStatus.TARGET_CLOSE_STATUS;

			}
			else
			{
				logger.debug("同步商品状态到商户,接收状态不正确,不发送报文. 状态: [" + event.getStatus() + "]");
				return;
			}
			Map<String, Object> responseMap = null;
			Map<String, Object> fileds = new HashMap<String, Object>();
			fileds.put(ActionMapKey.MERCHANT_ID, event.getMerchantId());
			fileds.put("itemCode", event.getMerchantProdCode());
			fileds.put("actionType", targetStatus);
			
			int randomValue = new Random().nextInt();
			fileds.put("randomValue", Math.abs(randomValue));
			logger.debug("同步商品状态到商户开始, 参数内容: [" + fileds + "]");
			ActionContextUtil.init();
			ActionContextUtil.setActionContext(ActionMapKey.REQUEST_MAP, fileds);
			checkSynProductStatusIntfExistsHandler.handleRequest();
			Object interfaceTypeObj = ActionContextUtil.getActionContextParam(ActionMapKey.INTERFACE_TYPE);
			if (BeanUtils.isNotNull(interfaceTypeObj))
			{
				fileds.put(TransactionMapKey.INTERFACE_TYPE, (String) interfaceTypeObj);
				ActionContextUtil.setActionContext(ActionMapKey.REQUEST_MAP, fileds);
				gatewayAction.handleRequest();
				responseMap = (Map<String, Object>) ActionContextUtil.getActionContextParam(ActionMapKey.RESPONSE_MAP);
				logger.debug("同步商品状态到商户返回报文: [" + responseMap + "]");
			}
		}
		catch (Exception e)
		{
			logger.error("SynProductStatusListener 错误详细[" + ExceptionUtil.getStackTraceAsString(e) + "]");
		}

	}

}
