/*
 * 文件名：GatewayAction.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.gateway;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.service.GatewayService;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;

@Scope("prototype")
@Service("gatewayAction")
public class GatewayAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(GatewayAction.class);
    
    @Autowired
    private GatewayService gatewayService;

    @Override
    public void handleRequest()
        throws HopsException
    {
        logger.debug("准备开始调用GatewayAction");
        Map<String, Object> fileds = (Map<String, Object>)ActionContextUtil.getActionContextParam(ActionMapKey.REQUEST_MAP);
        logger.debug("请求gateway,发送订单！");
        // 2.发送发货记录 responseStr --> Map
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try
        {
            responseMap = gatewayService.request(fileds);
            logger.debug("调用GatewayAction结束！");
            Assert.notNull(responseMap);
            
            ActionContextUtil.setActionContext(ActionMapKey.RESPONSE_MAP, responseMap);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (HopsException e)
        {
            logger.error("gatewayAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002007", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
    
}
