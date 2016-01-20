/*
 * 文件名：CheckOrderByBlackListHander.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yao
 * 修改时间：2015年5月25日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.check;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.blacklist.entity.Blacklist;
import com.yuecheng.hops.blacklist.service.CheckBlacklistService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


/**
 * 检查黑名单Hander
 * 
 * @author yao
 * @version 2015年5月25日
 * @see CheckOrderByBlackListHander
 * @since
 */
@Service("checkOrderByBlacklistHander")
public class CheckOrderByBlacklistHander extends ActionHandler
{
    private static Logger logger = LoggerFactory.getLogger(CheckOrderByBlacklistHander.class);

    @Autowired
    private ErrorCodeService errorCodeService;

    @Autowired
    private CheckBlacklistService checkBlacklistService;

    /**
     * 处理方法，调用此方法处理请求
     */
    @Override
    public void handleRequest()
            throws HopsException
    {
        try
        {
            Verify();
            logger.debug("检查黑名单，通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查黑名单，失败！");
            throw new ApplicationException(
                    Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
            throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        Blacklist blacklist = null;
        try
        {
            String userCode = order.getUserCode();
            Long businessType = order.getBusinessType();
            if (StringUtils.isNotBlank(userCode) && businessType != null)
            {
                blacklist = checkBlacklistService.checkNum(userCode,
                        String.valueOf(businessType));
            }
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        catch(RpcException e){
            throw e;
        }
        catch (Exception e)
        {
            logger.info("查询黑名单异常,号码：" + order.getUserCode());
            logger.error("异常信息：" + ExceptionUtil.getStackTraceAsString(e));//!!!异常信息优化
            throw new ApplicationException(Constant.ErrorCode.PDU_ACT_NOT_SUPPORT);
        }
        // 检查黑名单
        if (blacklist != null)
        {
            // 存在于黑名单列表之中
            logger.debug("该号码存在于黑名单列表之中,号码：" + order.getUserCode() + "，黑名单信息："
                         + blacklist.toString());
            throw new ApplicationException(
                    Constant.ErrorCode.PDU_ACT_NOT_SUPPORT);
        }
        /*
         * else { ActionContextUtil.setActionContext(ActionMapKey.BLACKLIST, blacklist);// 261 0502
         * }
         */
    }
}
