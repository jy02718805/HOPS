package com.yuecheng.hops.gateway.application.tranfer;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.common.utils.UUIDUtils;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.injection.entity.InterfaceConstant;
import com.yuecheng.hops.injection.entity.InterfaceParam;
import com.yuecheng.hops.injection.service.InterfaceConstantService;


@Service("beanEncoderTransfer")
public class BeanEncoderTransfer
{
    private static Logger logger = LoggerFactory.getLogger(BeanEncoderTransfer.class);

    @Autowired
    private InterfaceConstantService interfaceConstantService;

    // response Map<orderNo> -> Map<oid>
    public Map<String, Object> encode(Map<String, Object> originalMap,
                                      List<InterfaceParam> interfaceParams, Long merchantId)
        throws Exception
    {
        Map<String, Object> fieldsMap = new HashMap<String, Object>();
        try
        {
            String inputParamName = StringUtil.initString();
            if (BeanUtils.isNotNull(interfaceParams))
            {
                long datetime = 0;
                long time = 0;
                for (InterfaceParam interfaceParam : interfaceParams)
                {
                    String key = interfaceParam.getOutParamName();
                    inputParamName = interfaceParam.getInputParamName();
                    Object value = null;
                    if (interfaceParam.getParamType().equalsIgnoreCase(
                        Constant.Interface.INTERFACE_PARAM_PARAMTYPE_TRANSPARAM))
                    {
                        value = originalMap.get(inputParamName);
                        if (BeanUtils.isNotNull(key)
                            && (key.equalsIgnoreCase(Constant.MobileType.P4_PRODUCTCODE) || key.equalsIgnoreCase(Constant.MobileType.R4_PRODUCTCODE)))
                        {
                            if (Constant.MobileType.YD.equalsIgnoreCase((String)originalMap.get("ext1")))
                            {
                                value = Constant.MobileType.SHKC;
                            }
                            else if (Constant.MobileType.LT.equalsIgnoreCase((String)originalMap.get("ext1")))
                            {
                                value = Constant.MobileType.SHKC_CU;
                            }
                            else if (Constant.MobileType.DX.equalsIgnoreCase((String)originalMap.get("ext1")))
                            {
                                value = Constant.MobileType.SHKC_CT;
                            }
                        }

                        // 普通
                        if (BeanUtils.isNotNull(inputParamName)
                            && inputParamName.equalsIgnoreCase("orderStatus"))
                        {
                            if (null != GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)
                                && Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER.equals(GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)))
                            {
                                value = String.valueOf(value).equals(
                                    Constant.OrderStatus.FAILURE_ALL + "") ? Constant.TaoBaoNotify.FAILED : Constant.TaoBaoNotify.SUCCESS;
                            }
                            else
                            {
                                // 订单状态, 0 充值成功 2 充值中 3 充值失败 6 部分成功 ,
                                // 新增预下单查询接口，0预下单失败，1 预下单成功
                                if (value != null)
                                {
                                    value = Integer.valueOf(String.valueOf(value)) == 3 ? 0 : Integer.valueOf(String.valueOf(value)) == 4 ? 3 : Integer.valueOf(String.valueOf(value)) == 0 ? 0 : Integer.valueOf(String.valueOf(value)) == 1 ? 1 : 2;
                                }
                            }
                        }
                        // if (BeanUtils.isNotNull(inputParamName) &&
                        // inputParamName.equalsIgnoreCase("orderFinishTime"))
                        // {
                        // SimpleDateFormat formatter = new
                        // SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
                        // value = formatter.format(originalMap.get(inputParamName));
                        // }
                    }
                    else if (interfaceParam.getParamType().equalsIgnoreCase(
                        Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT))
                    {
                        InterfaceConstant interfaceConstant = interfaceConstantService.getInterfaceConstant(
                            merchantId, IdentityType.MERCHANT.toString(), key);
                        String[] keys = key.split("\\*");
                        key = keys[0];
                        value = interfaceConstant.getValue().trim();

                        // 对于不同的商户，对运行商的显示不同 移动-1 或者 移动- SHKC 等等。。。
                        String[] values = ((String)value).split("\\|");
                        if (values.length > 1)
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            for (int i = 0; i < values.length; i++ )
                            {
                                map.put(values[i].split("\\:")[0], values[i].split("\\:")[1]);
                            }
                            value = map.get((String)originalMap.get("ext1"));
                        }
                    }
                    else if (interfaceParam.getParamType().equalsIgnoreCase(
                        Constant.Interface.INTERFACE_PARAM_PARAMTYPE_AMOUNT))
                    {
                        if (Constant.Common.MONEY_FORMAT_TYPE_YUAN.equals(interfaceParam.getFormatType()))
                        {
                            DecimalFormat mformat = new DecimalFormat(
                                Constant.Common.MONEY_FORMAT_TYPE);
                            value = originalMap.get(inputParamName);
                            if (BeanUtils.isNotNull(value))
                            {
                                value = mformat.format(Double.valueOf(String.valueOf(value)));
                            }
                        }
                        else if (Constant.Common.MONEY_FORMAT_TYPE_CENT.equals(interfaceParam.getFormatType()))
                        {
                            DecimalFormat mformat = new DecimalFormat(
                                Constant.Common.MONEY_FORMAT_TYPE);
                            value = originalMap.get(inputParamName);
                            if (BeanUtils.isNotNull(value))
                            {
                                value = mformat.format(Double.valueOf(String.valueOf(value)));
                                value = BigDecimalUtil.multiply(new BigDecimal(value.toString()),
                                    new BigDecimal("100"), DecimalPlaces.ZERO.value(),
                                    BigDecimal.ROUND_HALF_UP);
                            }
                        }
                    }
                    else if (interfaceParam.getParamType().equalsIgnoreCase(
                        Constant.Interface.INTERFACE_PARAM_PARAMTYPE_DATE))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(
                            Constant.Common.DATE_FORMAT_TYPE);
                        if (interfaceParam.getFormatType() != null
                            && "long".equals(interfaceParam.getFormatType()))
                        {
                            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                                "1970-01-01 00:00:00").getTime();
                            value = datetime - time;
                        }
                        // DELIVERY_START_TIME 提上来判断
                        else if (BeanUtils.isNotNull(inputParamName) && null != GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)
                            && Constant.Interface.INTERFACE_TYPE_QUERY_ORDER.equals(GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)))
                        {
                            if (interfaceParam.getFormatType() != null
                                && !"timeStamp".equals(interfaceParam.getFormatType()))
                            {
                                formatter = new SimpleDateFormat(interfaceParam.getFormatType());
                            }
                            value = originalMap.get(inputParamName);
                            if (BeanUtils.isNotNull(value))
                            {
                                value = formatter.format((Date)value);
                            }
                        }
                        else
                        {
                            if (interfaceParam.getFormatType() != null
                                && !"timeStamp".equals(interfaceParam.getFormatType()))
                            {
                                formatter = new SimpleDateFormat(interfaceParam.getFormatType());
                            }

                            if (null != GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)
                                && Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER.equals(GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)))
                            {
                                value = originalMap.get(inputParamName);
                                if (BeanUtils.isNotNull(value)
                                    && !StringUtil.initString().equals(value))
                                {
                                    value = formatter.format(value);
                                }
                                else if (key.equalsIgnoreCase("coopOrderSuccessTime"))
                                {
                                    value = formatter.format(new Date());
                                }
                            }
                            else if (null != GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)
                                     && Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_SUCCESS_PAIPAI.equals(GatewayContextUtil.getParameter(GatewayConstant.INTERFACE)))
                            {
                                value = originalMap.get(inputParamName);
                                if (BeanUtils.isNotNull(value)
                                    && !StringUtil.initString().equals(value))
                                {
                                    value = formatter.format(value);
                                    value = URLEncoder.encode((String)value);
                                    value = String.valueOf(value).replace("+", "%20");
                                }
                                else
                                {
                                    value = String.valueOf(System.currentTimeMillis()).substring(
                                        0, 10);
                                }
                            }
                            else
                            {
                                if ("timeStamp".equals(interfaceParam.getFormatType()))
                                {
                                    value = String.valueOf(System.currentTimeMillis()).substring(
                                        0, 10);
                                }
                                else
                                {
                                    value = formatter.format(new Date());
                                    datetime = formatter.parse((String)value).getTime();
                                }
                            }
                        }
                    }
                    else if (interfaceParam.getParamType().equalsIgnoreCase(
                        Constant.Interface.INTERFACE_PARAM_RANDOM))
                    {
                        value = UUIDUtils.uuid();
                    }
                    else if (interfaceParam.getParamType().equalsIgnoreCase(
                        Constant.Interface.INTERFACE_PARAM_COMBINNUM))
                    {
                        // 根据意桥接口文档要求，组装他们能识别的orderNO
                        value = String.valueOf(originalMap.get("merchantCode"))
                                + String.valueOf(originalMap.get("deliveryId"))
                                + String.valueOf("0000000");
                    }
                    fieldsMap.put(key, value);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("BeanEncoderTransfer Faied caused by "
                         + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR, e);
        }
        logger.debug("BeanEncoderTransfer success!");
        return fieldsMap;
    }
}
