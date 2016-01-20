package com.yuecheng.hops.transaction.service.check;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.NumberUtil;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.numsection.service.CheckNumSectionService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.config.product.TmallTSCService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Service("checkOrderByNumSectionHandler")
public class CheckOrderByNumSectionHandler extends ActionHandler
{
    private static Logger logger = LoggerFactory.getLogger(CheckOrderByNumSectionHandler.class);

    @Autowired
    private ErrorCodeService errorCodeService;

    @Autowired
    private CheckNumSectionService checkNumSectionService;

    @Autowired
    private TmallTSCService tmallTSCService;

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
            logger.debug("检查号段！通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查号段，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        NumSection numSection = null;
        try
        {
            String userCode = order.getUserCode();
            if(NumberUtil.isFixedPhone(userCode))//!!!合并
            {
                numSection = checkNumSectionService.checkNum(order.getUserCode());
            }
            else
            {
                if (userCode.length() != 11)
                {
                    // 充值产品和账号不匹配
                    logger.debug("手机号码不匹配或不等于11位，号码：" + userCode);
                    throw new ApplicationException(Constant.ErrorCode.PDU_ACT_NOT_SUPPORT);
                }
                if (!isMobile(userCode))
                {
                    // 号码有误
                    logger.debug("手机号码错误，号码：" + userCode);
                    throw new ApplicationException(Constant.ErrorCode.PDU_ACT_NOT_SUPPORT);
                }
                numSection = checkNumSectionService.checkNum(order.getUserCode());
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
            logger.error("查询号段异常,号码：" + order.getUserCode() + "异常信息：" + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException(Constant.ErrorCode.PDU_ACT_NOT_SUPPORT);
        }
        // 检查号段
        if (numSection == null || numSection.getSectionId() == null)
        {
            // 充值产品和账号不匹配
            logger.error("号段不存在,号码：" + order.getUserCode());
            throw new ApplicationException(Constant.ErrorCode.PDU_ACT_NOT_SUPPORT);
        }
        else
        {
            ActionContextUtil.setActionContext(ActionMapKey.NUM_SECTION, numSection);// 261 0502
        }
    }

    /**
     * 手机号验证
     * 
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str)
    {
        boolean b = false;
        if (str.length() == 11 && StringUtils.isNumeric(str))
        {
            b = true;
        }

        // p = Pattern.compile("^[]$"); // 验证手机号
        // m = p.matcher(str);
        // b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     * 
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str)
    {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        if (str.length() > 9)
        {
            m = p1.matcher(str);
            b = m.matches();
        }
        else
        {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

}
