package com.yuecheng.hops.transaction.service.check;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Service("checkQueryAccountParamsHandler")
public class CheckQueryAccountParamsHandler extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(CheckQueryAccountParamsHandler.class);

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
            logger.debug("检查接口-查询代理商账户必输项！ 通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查接口-查询代理商账户必输项，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        String merchantCode = (String)ActionContextUtil.getActionContextParam(ActionMapKey.MERCHANT_CODE);
        Date tsp = (Date)ActionContextUtil.getActionContextParam(ActionMapKey.TSP);
        try
        {
            Assert.notNull(merchantCode);
            Assert.notNull(tsp);
        }
        catch (Exception e)
        {
            logger.error("必填参数为空！");
            throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR);
        }
    }

}
