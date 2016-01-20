package com.yuecheng.hops.mportal.web.injection;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.common.utils.UUIDUtils;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.entity.InterfaceConstant;
import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinition;
import com.yuecheng.hops.injection.entity.InterfaceParam;
import com.yuecheng.hops.injection.entity.MerchantRequest;
import com.yuecheng.hops.injection.entity.MerchantResponse;
import com.yuecheng.hops.injection.service.InterfaceConstantService;
import com.yuecheng.hops.injection.service.InterfacePacketTypeConfService;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.injection.service.MerchantResponseService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.vo.ycinterface.InterfacePacketsDefinitionVO;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.service.SecurityCredentialService;


@Controller
@RequestMapping(value = "/interface")
public class InterfaceControl extends BaseControl
{

    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceControl.class);

    private static final String PAGE_SIZE = "10";

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private InterfaceConstantService interfaceConstantService;

    @Autowired
    private InterfacePacketTypeConfService interfacePacketTypeConfService;

    @Autowired
    private MerchantRequestService merchantRequestService;

    @Autowired
    private MerchantResponseService merchantResponseService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    IdentityService identityService;

    @RequestMapping(value = "/createInterfaceDefinition")
    public String createInterfaceDefinition(@RequestParam(value = "merchantId", defaultValue = "")
    String merchantId, ModelMap model)
    {
        model.addAttribute("merchantId", merchantId);
        return "interface/createInterfaceDefinition";
    }

    @RequestMapping(value = "/doCreateInterfaceDefinition")
    public String doCreateInterfaceDefinition(@RequestParam(value = "merchantId", defaultValue = "")
                                              String merchantId,
                                              @RequestParam(value = "interfaceType", defaultValue = "")
                                              String interfaceType,
                                              @RequestParam(value = "isConf", defaultValue = "")
                                              String isConf,
                                              @RequestParam(value = "entityName", defaultValue = "")
                                              String entityName, ModelMap model)
    {
        InterfacePacketsDefinition ifd = new InterfacePacketsDefinition();
        if (null != isConf && !isConf.isEmpty() && isConf.equalsIgnoreCase("1"))
        {
            // 选择动态接口配置，定向配置页面
            return "redirect:/interface/toSaveInterfacePacketsDefinition?merchantId=" + merchantId
                   + "&interfaceType=" + interfaceType + "&isConf=" + isConf;
        }
        else
        {
            try
            {
                ifd.setMerchantId(Long.valueOf(merchantId));
                ifd.setInterfaceType(interfaceType);
                ifd.setIsConf(Long.valueOf(isConf));
                ifd.setEntityName(entityName);
                ifd.setStatus(Constant.Interface.CLOSE);
                interfaceService.saveInterfacePacketsDefinition(ifd);
                // 选择手工接口配置，保存配置信息
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "interface/interfaceConfList?merchantId=" + merchantId);
                model.put("next_msg", "商户接口列表");
            }
            catch (RpcException e)
            {
                model.put("message", "操作失败[" + e.getMessage() + "]");
                model.put("canback", true);
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/toSaveInterfacePacketsDefinition")
    public String toSaveInterfacePacketsDefinition(@RequestParam(value = "merchantId", defaultValue = "")
                                                   String merchantId,
                                                   @RequestParam(value = "interfaceType", defaultValue = "")
                                                   String interfaceType,
                                                   @RequestParam(value = "isConf", defaultValue = "")
                                                   String isConf, Model model,
                                                   ServletRequest request)
    {
        List<InterfaceConstant> interfaceConstants = interfaceConstantService.getInterfaceConstantByParams(
            Long.valueOf(merchantId), IdentityType.MERCHANT.toString());
        model.addAttribute("merchantId", merchantId);
        model.addAttribute("interfaceConstants", interfaceConstants);
        model.addAttribute("interfaceType", interfaceType);
        model.addAttribute("isConf", isConf);
        return "interface/toSaveInterfacePacketsDefinition";
    }

    @RequestMapping(value = "/addInterfaceParam")
    @ResponseBody
    public String addInterfaceParam(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "type", defaultValue = "")
    String type, @RequestParam(value = "inOrOut", defaultValue = "")
    String inOrOut, Model model, ServletRequest request)
    {
        StringBuffer html = new StringBuffer();
        html.append("<tr id=\"" + id + "\">");
        html.append("<td style=\"text-align: left;\">");
        html.append("序号：<input type=\"text\" name=\"sequence\" id=\"sequence\" value=\"" + id
                    + "\" maxlength=\"3\" style=\"width:60px;\" /> ");
        html.append("参数名称：<input type=\"text\" name=\"outParamName\" id=\"outParamName\" value=\"\" maxlength=\"64\" style=\"width:60px;\" /> ");
        if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
        {
            html.append("数据类型：<select name=\"dataType\" id=\"dataType\" onchange=\"addRequestParamTypeByDataType("
                        + id + ")\" >");
        }
        else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
        {
            html.append("数据类型：<select name=\"dataType\" id=\"dataType\" onchange=\"addResponseParamTypeByDataType("
                        + id + ")\" >");
        }
        html.append("<option value=\"\">请选择</option>");
        html.append("<option value=\"string\">字符串</option>");
        html.append("<option value=\"date\">日期</option>");
        html.append("</select> ");
        if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
        {
            html.append("<div id=\"requestParamTypeDiv" + id + "\"></div>");
        }
        else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
        {
            html.append("<div id=\"responseParamTypeDiv" + id + "\"></div>");
        }
        html.append("</td>");
        html.append("<td>");
        if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
        {
            html.append("<a onclick=\"deleteRequestInterfaceParams(" + id + ")\">[删除]</a>");
        }
        else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
        {
            html.append("<a onclick=\"deleteResponseInterfaceParams(" + id + ")\">[删除]</a>");
        }
        else
        {

        }
        html.append("</td>");
        html.append("</tr>");
        return html.toString();
    }

    @RequestMapping(value = "/addParamTypeByDataType")
    @ResponseBody
    public String addParamTypeByDataType(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "dataType", defaultValue = "")
    String dataType, @RequestParam(value = "inOrOut", defaultValue = "")
    String inOrOut, @RequestParam(value = "type", defaultValue = "")
    String type, Model model, ServletRequest request)
    {
        StringBuffer html = new StringBuffer();

        if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
        {
            html.append("参数类型：<select name=\"paramType\" id=\"paramType\" onchange=\"addRequestInterfaceParamPropertyByTypes("
                        + id + ")\" >");
        }
        else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
        {
            html.append("参数类型：<select name=\"paramType\" id=\"paramType\" onchange=\"addResponseInterfaceParamPropertyByTypes("
                        + id + ")\" >");
        }
        if (inOrOut.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_OUT))
        {
            if (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"password\">密码</option>");
                html.append("<option value=\"constant\">常量</option>");
                html.append("<option value=\"transParam\">交易参数</option>");
                html.append("<option value=\"amount\">金额</option>");
                html.append("<option value=\"random\">随机数</option>");
                html.append("<option value=\"combinNum\">组合数</option>");
            }
            else if (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE))
            {
                // select 日期格式
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"date\">日期</option>");
            }
        }
        else if (inOrOut.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_IN))
        {
            // if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
            // {
            if (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"password\">密码</option>");
                html.append("<option value=\"constant\">常量</option>");
                html.append("<option value=\"transParam\">交易参数</option>");
                html.append("<option value=\"amount\">金额</option>");
                html.append("<option value=\"random\">随机数</option>");
                html.append("<option value=\"combinNum\">组合数</option>");
            }
            else if (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE))
            {
                // select 日期格式
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"date\">日期</option>");
            }
            // }
            // else if
            // (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
            // {
            // if (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING))
            // {
            // html.append("<option value=\"\">请选择</option>");
            // html.append("<option value=\"returnSuccess\">正常返回</option>");
            // html.append("<option value=\"returnFail\">异常返回</option>");
            // }
            // else if
            // (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE))
            // {
            // // select 日期格式
            // html.append("<option value=\"\">请选择</option>");
            // html.append("<option value=\"date\">日期</option>");
            // }
            // }
        }

        html.append("</select> ");

        if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
        {
            html.append("<td><div id=\"requestParams" + id + "\"></td>");
        }
        else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
        {
            html.append("<td><div id=\"responseParams" + id + "\"></td>");
        }
        return html.toString();
    }

    @RequestMapping(value = "/addInterfaceParamPropertyByTypes")
    @ResponseBody
    public String addInterfaceParamPropertyByTypes(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "merchantId", defaultValue = "")
    String merchantId, @RequestParam(value = "dataType", defaultValue = "")
    String dataType, @RequestParam(value = "inOrOut", defaultValue = "")
    String inOrOut, @RequestParam(value = "paramType", defaultValue = "")
    String paramType, @RequestParam(value = "type", defaultValue = "")
    String type, @RequestParam(value = "interfaceType", defaultValue = "")
    String interfaceType, Model model, ServletRequest request)
    {
        StringBuffer html = new StringBuffer();
        if (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING))
        {
            if (paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_PASSWORD))
            {
                Merchant merchant = merchantService.queryMerchantById(Long.valueOf(merchantId));
                List<SecurityCredential> scList = securityCredentialService.querySecurityCredentialByIdentity(merchant);
                html.append("设置：<select name=\"inputParamName\" id=\"inputParamName\">");
                html.append("<option value=\"\">请选择</option>");
                for (SecurityCredential securityCredential : scList)
                {
                    html.append("<option value=\"" + securityCredential.getSecurityId() + "\">"
                                + securityCredential.getSecurityName() + "</option>");
                }
                html.append("</select>");
                html.append("设置大小写：<select name=\"isCapital\" id=\"isCapital\">");
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"up\">大写</option>");
                html.append("<option value=\"down\">小写</option>");
                html.append("<option value=\"unchanged\">不变</option>");
                html.append("</select>");
                html.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
                html.append("<option value=\"true\">是</option>");
                html.append("<option value=\"false\">否</option>");
                html.append("</select>");

            }
            else if (paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_TRANSPARAM))
            {
                // select 加密类型
                if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
                {
                    html.append("加密类型：<select name=\"encryptionFunction\" id=\"encryptionFunction\" onchange=\"addRequestInterfaceParamPropertyByEncryptionFunction("
                                + id + ")\" >");
                }
                else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
                {
                    html.append("加密类型：<select name=\"encryptionFunction\" id=\"encryptionFunction\" onchange=\"addResponseInterfaceParamPropertyByEncryptionFunction("
                                + id + ")\" >");
                }
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"NO_NEED\">不加密</option>");
                html.append("<option value=\"RSA\">RSA</option>");
                html.append("<option value=\"AES\">AES</option>");
                html.append("<option value=\"MD5\">MD5</option>");
                html.append("<option value=\"MD5_CMPAY\">MD5_CMPAY</option>");
                html.append("<option value=\"MAC\">MAC</option>");
                html.append("<option value=\"JFMD5\">JFMD5</option>");
                html.append("<option value=\"MD5_YS\">MD5_YS</option>");
                html.append("<option value=\"MD5_HX\">MD5_HX</option>");
                html.append("</select> ");
                if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
                {
                    html.append("<div id=\"requestEncryptionFunction" + id + "\"></div>");
                }
                else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
                {
                    html.append("<div id=\"responseEncryptionFunction" + id + "\"></div>");
                }
            }
            else if (paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_AMOUNT))
            {
                html.append(getInterfaceParamPropertyByInterfaceType(type, interfaceType, inOrOut));
                // input 金额格式
                html.append("<select name=\"encryptionFunction\" id=\"encryptionFunction\" style=\"display: none;\" onchange=\"addRequestInterfaceParamPropertyByEncryptionFunction("
                            + id + ")\" >");
                html.append("<option value=\"NO_NEED\" selected=\"selected\">不加密</option>");
                html.append("</select> ");
                html.append("金额格式：<input type=\"text\" name=\"formatType\" id=\"formatType\" maxlength=\"20\" style=\"width:120px;\" /> ");
                html.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
                html.append("<option value=\"true\">是</option>");
                html.append("<option value=\"false\">否</option>");
                html.append("</select>");
            }
            else if (paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT))
            {
                // select 用户常量
                List<InterfaceConstant> interfaceConstants = interfaceConstantService.getInterfaceConstantByParams(
                    Long.valueOf(merchantId), IdentityType.MERCHANT.toString());
                html.append("常量：<select name=\"outParamName\" id=\"outParamName\">");
                html.append("<option value=\"\">请选择</option>");
                for (InterfaceConstant interfaceConstant : interfaceConstants)
                {
                    html.append("<option value=\"" + interfaceConstant.getValue() + "\">"
                                + interfaceConstant.getKey() + "=" + interfaceConstant.getValue()
                                + "</option>");
                }
                html.append("</select> ");
                html.append("设置大小写：<select name=\"isCapital\" id=\"isCapital\">");
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"up\">大写</option>");
                html.append("<option value=\"down\">小写</option>");
                html.append("<option value=\"unchanged\">不变</option>");
                html.append("</select>");
                html.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
                html.append("<option value=\"true\">是</option>");
                html.append("<option value=\"false\">否</option>");
                html.append("</select>");
            }
            else if (paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_RANDOM))
            {
                html.append("设置大小写：<select name=\"isCapital\" id=\"isCapital\">");
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"up\">大写</option>");
                html.append("<option value=\"down\">小写</option>");
                html.append("<option value=\"unchanged\">不变</option>");
                html.append("</select>");
                html.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
                html.append("<option value=\"true\">是</option>");
                html.append("<option value=\"false\">否</option>");
                html.append("</select>");
            }
            else if (paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_COMBINNUM))
            {
                html.append("设置大小写：<select name=\"isCapital\" id=\"isCapital\">");
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"up\">大写</option>");
                html.append("<option value=\"down\">小写</option>");
                html.append("<option value=\"unchanged\">不变</option>");
                html.append("</select>");
                html.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
                html.append("<option value=\"true\">是</option>");
                html.append("<option value=\"false\">否</option>");
                html.append("</select>");
            }

            else if (paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_RETURN_SUCCESS)
                     || paramType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_RETURN_FAIL))
            {
                html.append("返回表达式：<input type=\"text\" name=\"inputParamName\" id=\"inputParamName\" maxlength=\"100\" /> ");
                html.append("设置大小写：<select name=\"isCapital\" id=\"isCapital\">");
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"up\">大写</option>");
                html.append("<option value=\"down\">小写</option>");
                html.append("<option value=\"unchanged\">不变</option>");
                html.append("</select>");
            }
        }
        else if (dataType.equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE))
        {
            // input 日期格式
            html.append("时间种类：<select name=\"inputParamName\" id=\"inputParamName\" >");
            html.append("<option value=\"\">请选择</option>");
            html.append("<option value=\"deliveryStartTime\">发货时间</option>");
            html.append("</select>");
            html.append("日期格式：<input type=\"text\" name=\"formatType\" id=\"formatType\" maxlength=\"20\" style=\"width:120px;\" /> ");
            html.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
            html.append("<option value=\"true\">是</option>");
            html.append("<option value=\"false\">否</option>");
            html.append("</select>");
        }
        return html.toString();
    }

    @RequestMapping(value = "/addInterfaceParamPropertyByEncryptionFunction")
    @ResponseBody
    public String addInterfaceParamPropertyByEncryptionFunction(@RequestParam(value = "id", defaultValue = "")
                                                                String id,
                                                                @RequestParam(value = "interfaceType", defaultValue = "")
                                                                String interfaceType,
                                                                @RequestParam(value = "type", defaultValue = "")
                                                                String type,
                                                                @RequestParam(value = "inOrOut", defaultValue = "")
                                                                String inOrOut,
                                                                @RequestParam(value = "encryptionFunction", defaultValue = "")
                                                                String encryptionFunction,
                                                                Model model, ServletRequest request)
    {
        StringBuffer html = new StringBuffer();
        if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.NO_NEED))
        {
            html.append(getInterfaceParamPropertyByInterfaceType(type, interfaceType, inOrOut));
        }
        else
        {
            // 加密表达式
            html.append("加密参数:<textarea id=\"encryptionParamNames\" cols='60' rows='3' ></textarea> ");
        }
        html.append("设置大小写：<select name=\"isCapital\" id=\"isCapital\">");
        // delivery
        html.append("<option value=\"\">请选择</option>");
        html.append("<option value=\"up\">大写</option>");
        html.append("<option value=\"down\">小写</option>");
        html.append("<option value=\"unchanged\">不变</option>");
        html.append("</select>");
        html.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
        html.append("<option value=\"true\">是</option>");
        html.append("<option value=\"false\">否</option>");
        html.append("</select>");
        return html.toString();
    }

    public StringBuffer getInterfaceParamPropertyByInterfaceType(String type,
                                                                 String interfaceType,
                                                                 String inOrOut)
    {
        StringBuffer html = new StringBuffer();
        // 不需要加密，看接口类型定义输出变量名
        html.append("设置：<select name=\"inputParamName\" id=\"inputParamName\">");
        if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
        {
            if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SEND_ORDER)
                || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_QUERY_ORDER)
                || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_SEND_ORDER_FLOW)
                || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_QUERY_ORDER_FLOW))
            {
                // delivery
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"deliveryId\">订单号(deliveryId)</option>");
                html.append("<option value=\"merchantOrderNo\">代理商商户订单号(merchantOrderNo)</option>");
                html.append("<option value=\"supplyMerchantOrderNo\">供货商商户订单号(supplyMerchantOrderNo)</option>");
                html.append("<option value=\"productFace\">产品面值(productFace)</option>");
                html.append("<option value=\"productSaleDiscount\">产品销售折扣(productSaleDiscount)</option>");
                html.append("<option value=\"costDiscount\">成本折扣(costDiscount)</option>");
                html.append("<option value=\"costFee\">成本金额(costFee)</option>");
                html.append("<option value=\"userCode\">用户手机号(userCode)</option>");
                html.append("<option value=\"productName\">产品名称(productName)</option>");
                html.append("<option value=\"displayValue\">产品面额(displayValue)</option>");
                html.append("<option value=\"productType\">充值产品类型(productType)</option>");
                html.append("<option value=\"cp\">运营商(cp)</option>");
                html.append("<option value=\"cityCode\">地区码(cityCode)</option>");
                // 流量业务的两个接口对应添加的配置变量字段
                html.append("<option value=\"productId\">产品ID号(productId)</option>");
            }
            else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER)
                     || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FLOW))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"merchantOrderNo\">商户订单号(merchantOrderNo)</option>");
                html.append("<option value=\"orderDesc\">订单描述(orderDesc)</option>");
                html.append("<option value=\"orderStatus\">订单状态(orderStatus)</option>");
                html.append("<option value=\"orderSuccessFee\">订单成功金额(orderSuccessFee)</option>");
                html.append("<option value=\"orderNo\">订单号(orderNo)</option>");
                html.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                html.append("<option value=\"productFace\">产品面值(productFace)</option>");
                html.append("<option value=\"productNo\">产品编号(productNo)</option>");
                html.append("<option value=\"userCode\">用户手机号(userCode)</option>");
                html.append("<option value=\"productSaleDiscount\">产品销售折扣(productSaleDiscount)</option>");
                html.append("<option value=\"orderFinishTime\">充值成功时间(orderFinishTime)</option>");
                html.append("<option value=\"errorCode\">失败代码(failedCode)</option>");
                html.append("<option value=\"closeReason\">失败原因(failedReason)</option>");
                html.append("<option value=\"version\">版本号(version)</option>");
                html.append("<option value=\"displayValue\">产品面额(displayValue)</option>");
            }
            else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER)
                     || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FLOW))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"merchantOrderNo\">商户订单号(merchantOrderNo)</option>");
                html.append("<option value=\"orderDesc\">订单描述(orderDesc)</option>");
                html.append("<option value=\"orderStatus\">订单状态(orderStatus)</option>");
                html.append("<option value=\"orderSuccessFee\">订单成功金额(orderSuccessFee)</option>");
                html.append("<option value=\"orderNo\">订单号(orderNo)</option>");
                html.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                html.append("<option value=\"productFace\">产品面值(productFace)</option>");
                html.append("<option value=\"productNo\">产品编号(productNo)</option>");
                html.append("<option value=\"userCode\">用户手机号(userCode)</option>");
                html.append("<option value=\"productSaleDiscount\">产品销售折扣(productSaleDiscount)</option>");
                html.append("<option value=\"displayValue\">产品面额(displayValue)</option>");
            }
            else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER)
                     || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"deliveryId\">订单号(deliveryId)</option>");
                html.append("<option value=\"errorCode\">接口返回码(errorCode)</option>");
                html.append("<option value=\"merchantStatus\">交易状态(merchantStatus)</option>");
                html.append("<option value=\"msg\">描述信息(msg)</option>");
                html.append("<option value=\"orderSuccessFee\">成功金额(orderSuccessFee)</option>");
                html.append("<option value=\"supplyMerchantOrderNo\">供货商订单编号(supplyMerchantOrderNo)</option>");
                html.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                html.append("<option value=\"type\">接口类型(type)</option>");
                html.append("<option value=\"version\">版本号(version)</option>");
                html.append("<option value=\"requestId\">请求交易流水号(type)</option>");
            }
            // 增加通知淘宝代理商接口
            else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"merchantOrderNo\">商户订单号(merchantOrderNo)</option>");
                html.append("<option value=\"orderDesc\">订单描述(orderDesc)</option>");
                html.append("<option value=\"orderStatus\">订单状态(orderStatus)</option>");
                html.append("<option value=\"orderSuccessFee\">订单成功金额(orderSuccessFee)</option>");
                html.append("<option value=\"orderNo\">订单号(orderNo)</option>");
                html.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                html.append("<option value=\"productFace\">产品面值(productFace)</option>");
                html.append("<option value=\"displayValue\">产品面额(displayValue)</option>");
                html.append("<option value=\"productNo\">产品编号(productNo)</option>");
                html.append("<option value=\"userCode\">用户手机号(userCode)</option>");
                html.append("<option value=\"productSaleDiscount\">产品销售折扣(productSaleDiscount)</option>");
                html.append("<option value=\"orderFinishTime\">充值成功时间(orderFinishTime)</option>");
                html.append("<option value=\"errorCode\">失败代码(failedCode)</option>");
                html.append("<option value=\"closeReason\">失败原因(failedReason)</option>");
                html.append("<option value=\"version\">版本号(version)</option>");
            }
        }
        else if (type.equalsIgnoreCase(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE))
        {
            if (inOrOut.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_OUT))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"deliveryId\">订单号(deliveryId)</option>");
                html.append("<option value=\"errorCode\">接口返回码(errorCode)</option>");
                html.append("<option value=\"merchantStatus\">交易状态(merchantStatus)</option>");
                html.append("<option value=\"msg\">描述信息(msg)</option>");
                html.append("<option value=\"orderSuccessFee\">成功金额(orderSuccessFee)</option>");
                html.append("<option value=\"supplyMerchantOrderNo\">供货商订单编号(supplyMerchantOrderNo)</option>");
                html.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");

                html.append("<option value=\"type\">接口类型(type)</option>");
                html.append("<option value=\"version\">版本号(version)</option>");
                html.append("<option value=\"requestId\">请求交易流水号(type)</option>");
            }
            else if (inOrOut.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_IN))
            {
                html.append("<option value=\"\">请选择</option>");
                html.append("<option value=\"orderNo\">订单号(orderNo)</option>");
                html.append("<option value=\"merchantOrderNo\">商户订单号(merchantOrderNo)</option>");
                html.append("<option value=\"orderDesc\">订单描述(orderDesc)</option>");
                html.append("<option value=\"orderStatus\">订单状态(orderStatus)</option>");
                html.append("<option value=\"orderSuccessFee\">订单成功金额(orderSuccessFee)</option>");
            }
        }
        html.append("</select>");
        return html;
    }

    @RequestMapping(value = "/addMerchantRequest")
    @ResponseBody
    public String addMerchantRequest(@RequestParam(value = "id", defaultValue = "")
    String id, Model model, ServletRequest request)
    {
        StringBuffer html = new StringBuffer();
        html.append("<tr id=\"" + id + "\">");
        html.append("<td><input type=\"text\" name=\"timeDifferenceLow\" id=\"timeDifferenceLow\" /></td>");
        html.append("<td><input type=\"text\" name=\"timeDifferenceHigh\" id=\"timeDifferenceHigh\" /></td>");
        html.append("<td><input type=\"text\" name=\"intervalTime\" id=\"intervalTime\" /></td>");
        html.append("<td>");
        html.append("<select name=\"intervalUnit\" id=\"intervalUnit\" >");
        html.append("<option value=\"s\">秒</option>");
        html.append("<option value=\"m\">分</option>");
        html.append("<option value=\"h\">时</option>");
        html.append("<option value=\"d\">天</option>");
        html.append("</select>");
        html.append("</td>");
        html.append("<td>");
        html.append("<a onclick=\"deleteMerchantRequest(" + id + ")\">[删除]</a>");
        html.append("</td>");
        return html.toString();
    }

    @RequestMapping(value = "/addMerchantResponse")
    @ResponseBody
    public String addMerchantResponse(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "interfaceType")
    String interfaceType, Model model, ServletRequest request)
    {
        StringBuffer html = new StringBuffer();
        // <th><a class=sort href="javascript:;" name=sequence>交易返回码</a> </th>
        // <th><a class=sort href="javascript:;" name=identityName>订单返回码</a> </th>
        // <th><a class=sort href="javascript:;" name=identityName>商户返回信息描述</a> </th>
        // <th><a class=sort href="javascript:;" name=identityName>系统状态</a> </th>
        // <th><a class=sort href="javascript:;" name=identityName>操作</a> </th>
        html.append("<tr id=\"" + id + "\">");
        html.append("<td><input type=\"text\" name=\"errorCode\" id=\"errorCode\" /></td>");
        html.append("<td><input type=\"text\" name=\"merchantStatus\" id=\"merchantStatus\" /></td>");
        html.append("<td><input type=\"text\" name=\"merchantStatusInfo\" id=\"merchantStatusInfo\" /></td>");
        html.append("<td>");
        html.append("<select name=\"status\" id=\"status\" >");
        if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SEND_ORDER)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_SEND_ORDER_FLOW))
        {
            html.append("<option value=\"1\">重试</option>");
            html.append("<option value=\"2\">重绑</option>");
            html.append("<option value=\"3\">强制关闭</option>");
            html.append("<option value=\"4\">正常成功</option>");
            html.append("<option value=\"5\">正常失败</option>");
        }
        else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_QUERY_ORDER)
                 || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_QUERY_ORDER_FLOW))
        {
            html.append("<option value=\"2\">充值中</option>");
            html.append("<option value=\"3\">成功</option>");
            html.append("<option value=\"4\">失败</option>");
            html.append("<option value=\"5\">部分成功</option>");
        }
        else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER)
                 || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FLOW))
        {
            html.append("<option value=\"3\">通知成功</option>");
            html.append("<option value=\"4\">通知失败</option>");
        }
        else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER)
                 || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW))
        {
            html.append("<option value=\"2\">充值中</option>");
            html.append("<option value=\"3\">成功</option>");
            html.append("<option value=\"4\">失败</option>");
            html.append("<option value=\"5\">部分成功</option>");
        }
        html.append("</select>");
        html.append("</td>");
        html.append("<td>");
        html.append("<a onclick=\"deleteMerchantResponse(" + id + ")\">[删除]</a>");
        html.append("</td>");
        return html.toString();
    }

    @RequestMapping(value = "/doSaveInterfacePacketsDefinition")
    public String doSaveInterfacePacketsDefinition(InterfacePacketsDefinitionVO interfacePacketsDefinitionVO,
                                                   @RequestParam(value = "merchantId", defaultValue = "")
                                                   String merchantId, ModelMap model)
    {
        try
        {
            Gson gson = new Gson();
            // 保存数据包格式 begin
            InterfacePacketTypeConf request_interfacePacketTypeConf = new InterfacePacketTypeConf();
            request_interfacePacketTypeConf.setInterfaceType(interfacePacketsDefinitionVO.getInterfaceType());
            request_interfacePacketTypeConf.setMerchantId(Long.valueOf(merchantId));
            request_interfacePacketTypeConf.setPacketType(interfacePacketsDefinitionVO.getRequestPacketType());
            request_interfacePacketTypeConf.setConnectionModule(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
            interfacePacketTypeConfService.saveInterfacePacketTypeConf(request_interfacePacketTypeConf);

            InterfacePacketTypeConf response_interfacePacketTypeConf = new InterfacePacketTypeConf();
            response_interfacePacketTypeConf.setInterfaceType(interfacePacketsDefinitionVO.getInterfaceType());
            response_interfacePacketTypeConf.setMerchantId(Long.valueOf(merchantId));
            response_interfacePacketTypeConf.setPacketType(interfacePacketsDefinitionVO.getResponsePacketType());
            response_interfacePacketTypeConf.setConnectionModule(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
            interfacePacketTypeConfService.saveInterfacePacketTypeConf(response_interfacePacketTypeConf);
            // 保存数据包格式 end

            InterfacePacketsDefinition ifpd = new InterfacePacketsDefinition();
            ifpd.setMerchantId(interfacePacketsDefinitionVO.getMerchantId());
            ifpd.setInterfaceType(interfacePacketsDefinitionVO.getInterfaceType());
            ifpd.setEncoding(interfacePacketsDefinitionVO.getEncoding());
            ifpd.setConnectionType(interfacePacketsDefinitionVO.getConnectionType());
            ifpd.setRequestUrl(interfacePacketsDefinitionVO.getRequestUrl());
            ifpd.setInOrOut(interfacePacketsDefinitionVO.getInOrOut());
            ifpd.setIsConf(Long.valueOf(interfacePacketsDefinitionVO.getIsConf()));
            ifpd.setMethodType(interfacePacketsDefinitionVO.getMethodType());
            ifpd.setStatus(Constant.Interface.CLOSE);

            String requestInterfaceParamsStr = interfacePacketsDefinitionVO.getRequestInterfaceParamsStr();
            String responseInterfaceParamsStr = interfacePacketsDefinitionVO.getResponseInterfaceParamsStr();
            String requestMerchantRequestParams = interfacePacketsDefinitionVO.getRequestMerchantRequestParams();
            String responseMerchantResponseParams = interfacePacketsDefinitionVO.getResponseMerchantResponseParams();

            String replaceStr = UUIDUtils.uuid();
            String replaceStr2 = UUIDUtils.uuid();
            requestInterfaceParamsStr = requestInterfaceParamsStr.replaceAll("=", replaceStr).replaceAll(
                "/", replaceStr2);
            List<InterfaceParam> request_params = gson.fromJson(requestInterfaceParamsStr,
                new TypeToken<List<InterfaceParam>>()
                {}.getType());
            if (CollectionUtils.isNotEmpty(request_params))
            {
                for (InterfaceParam i : request_params)
                {
                    if (null != i.getEncryptionParamNames())
                    {
                        i.setEncryptionParamNames(i.getEncryptionParamNames().replaceAll(
                            replaceStr, "=").replaceAll(replaceStr2, "/"));
                    }
                }
            }

            List<InterfaceParam> response_params = gson.fromJson(responseInterfaceParamsStr,
                new TypeToken<List<InterfaceParam>>()
                {}.getType());
            List<MerchantRequest> merchantRequestList = gson.fromJson(
                requestMerchantRequestParams, new TypeToken<List<MerchantRequest>>()
                {}.getType());
            List<MerchantResponse> merchantResponseList = gson.fromJson(
                responseMerchantResponseParams, new TypeToken<List<MerchantResponse>>()
                {}.getType());
            ifpd.setRequestParams(request_params);
            ifpd.setResponseParams(response_params);
            interfaceService.saveInterfacePacketsDefinition(ifpd);
            merchantResponseService.saveMerchantResponseList(merchantResponseList,
                Long.valueOf(merchantId), interfacePacketsDefinitionVO.getInterfaceType());
            merchantRequestService.saveMerchantRequestList(merchantRequestList,
                Long.valueOf(merchantId), interfacePacketsDefinitionVO.getInterfaceType());

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "interface/interfaceConfList?merchantId=" + merchantId);
            model.put("next_msg", "商户接口列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/updateInterfacePacketsDefinitionStatus")
    public String updateInterfacePacketsDefinitionStatus(@RequestParam(value = "id")
    Long id, @RequestParam(value = "status")
    String status, @RequestParam(value = "merchantId")
    Long merchantId, ModelMap model)
    {
        try
        {
            InterfacePacketsDefinition ipd = interfaceService.getInterfacePacketsDefinitionById(id);
            ipd.setStatus(status);
            ipd = interfaceService.updateInterfacePacketsDefinition(ipd);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "interface/interfaceConfList?merchantId=" + merchantId);
            model.put("next_msg", "商户接口列表");
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    // 页面没有提供该功能
    // @RequestMapping(value = "/deleteInterfacePacketsDefinition")
    // public String deleteInterfacePacketsDefinition(@RequestParam(value = "id", defaultValue =
    // "") String id,
    // @RequestParam(value = "merchantId", defaultValue = "") String merchantId,
    // ModelMap model)
    // {
    // try
    // {
    // if (!id.isEmpty())
    // {
    // interfaceService.deleteInterfacePacketsDefinition(Long.valueOf(id));
    // }
    // else
    // {
    // model.put("message", "删除失败，id异常[" + id + "]");
    // model.put("canback", true);
    // }
    // model.put("message", "操作成功");
    // model.put("canback", false);
    // model.put("next_url", "interface/interfaceConfList?merchantId=" + merchantId);
    // model.put("next_msg", "商户接口列表");
    // }
    // catch (RpcException e)
    // {
    // model.put("message", "操作失败[" + e.getMessage() + "]");
    // model.put("canback", true);
    // }
    // return PageConstant.PAGE_COMMON_NOTIFY;
    // }

    public StringBuffer buildParams(InterfacePacketsDefinition interfacePacketsDefinition,
                                    List<InterfaceParam> requestParams, Long merchantId)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<th><a class=sort href=\"javascript:;\" name=identityName>配置信息</a> </th>");
        sb.append("<th><a class=sort href=\"javascript:;\" name=identityName>操作</a> </th>");
        sb.append("</tr>");
        for (InterfaceParam param : requestParams)
        {
            sb.append("<tr id=\"" + param.getSequence() + "\">");
            sb.append("<td style=\"text-align: left;\">");
            sb.append("序号：<input type=\"text\" name=\"sequence\" id=\"sequence\" value=\""
                      + param.getSequence() + "\" maxlength=\"3\" style=\"width:60px;\">");
            sb.append("参数名称：<input type=\"text\" name=\"outParamName\" id=\"outParamName\" value=\""
                      + param.getOutParamName() + "\" maxlength=\"64\" style=\"width:60px;\">");
            sb.append(getDataTypeByInterfaceParam(interfacePacketsDefinition, param));
            sb.append("</td>");
            sb.append("<td>");
            if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("<a onclick=\"deleteRequestInterfaceParams(" + param.getSequence()
                          + ")\">[删除]</a>");
            }
            else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("<a onclick=\"deleteResponseInterfaceParams(" + param.getSequence()
                          + ")\">[删除]</a>");
            }
            sb.append("</td>");
            sb.append("<td style=\"display: none;\">");
            sb.append("<input type=\"hidden\" name=\"id\" id=\"id\" value=\"" + param.getId()
                      + "\" >");
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        return sb;
    }

    public StringBuffer getDataTypeByInterfaceParam(InterfacePacketsDefinition interfacePacketsDefinition,
                                                    InterfaceParam param)
    {
        if (StringUtil.isNullOrEmpty(param.getDataType()))
        {
            param.setDataType("");
        }

        if (StringUtil.isNullOrEmpty(param.getParamType()))
        {
            param.setParamType("");
        }

        StringBuffer sb = new StringBuffer();

        if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
        {
            sb.append("数据类型：<select name=\"dataType\" id=\"dataType\" onchange=\"addRequestParamTypeByDataType("
                      + param.getSequence() + ")\">");
        }
        else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
        {
            sb.append("数据类型：<select name=\"dataType\" id=\"dataType\" onchange=\"addResponseParamTypeByDataType("
                      + param.getSequence() + ")\">");
        }
        if (StringUtil.isNullOrEmpty(param.getDataType()))
        {
            sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
            if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("<div id=\"requestParamTypeDiv" + param.getSequence() + "\"></div>");
            }
            else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("<div id=\"responseParamTypeDiv" + param.getSequence() + "\"></div>");
            }
        }
        else
        {
            sb.append("<option value=\"\">请选择</option>");
        }
        if (Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING.equalsIgnoreCase(param.getDataType()))
        {
            sb.append("<option value=\"string\" selected=\"selected\" >字符串</option>");
        }
        else
        {
            sb.append("<option value=\"string\">字符串</option>");
        }
        if (!StringUtil.isNullOrEmpty(param.getDataType())
            && Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE.equalsIgnoreCase(param.getDataType()))
        {
            sb.append("<option value=\"date\" selected=\"selected\" >日期</option>");
        }
        else
        {
            sb.append("<option value=\"date\">日期</option>");
        }
        sb.append("</select>");
        sb.append(getParamTypeByInterfaceParam(interfacePacketsDefinition, param));
        return sb;
    }

    public StringBuffer getParamTypeByInterfaceParam(InterfacePacketsDefinition interfacePacketsDefinition,
                                                     InterfaceParam param)
    {
        StringBuffer sb = new StringBuffer();
        if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
        {
            sb.append("<div id=\"requestParamTypeDiv" + param.getSequence() + "\">");
            sb.append("参数类型：<select name=\"paramType\" id=\"paramType\" onchange=\"addRequestInterfaceParamPropertyByTypes("
                      + param.getSequence() + ")\">");
        }
        else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
        {
            sb.append("<div id=\"responseParamTypeDiv" + param.getSequence() + "\">");
            sb.append("参数类型：<select name=\"paramType\" id=\"paramType\" onchange=\"addResponseInterfaceParamPropertyByTypes("
                      + param.getSequence() + ")\">");
        }
        if (Constant.Interface.INTERFACE_TYPE_OUT.equalsIgnoreCase(interfacePacketsDefinition.getInOrOut()))
        {
            if (Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING.equalsIgnoreCase(param.getDataType()))
            {
                if (StringUtil.isNullOrEmpty(param.getParamType()))
                {
                    sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"requestParams" + param.getSequence() + "\"></div>");
                    }
                    else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"responseParams" + param.getSequence() + "\"></div>");
                    }
                }
                else
                {
                    sb.append("<option value=\"\" >请选择</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_PASSWORD.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"password\" selected=\"selected\" >密码</option>");
                }
                else
                {
                    sb.append("<option value=\"password\">密码</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"constant\" selected=\"selected\" >常量</option>");
                }
                else
                {
                    sb.append("<option value=\"constant\">常量</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_TRANSPARAM.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"transParam\" selected=\"selected\" >交易参数</option>");
                }
                else
                {
                    sb.append("<option value=\"transParam\">交易参数</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_RANDOM.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"random\" selected=\"selected\" >随机数</option>");
                }
                else
                {
                    sb.append("<option value=\"random\">随机数</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_COMBINNUM.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"combinNum\" selected=\"selected\" >组合数</option>");
                }
                else
                {
                    sb.append("<option value=\"combinNum\">组合数</option>");
                }

                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_AMOUNT.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"amount\" selected=\"selected\" >金额</option>");
                }
                else
                {
                    sb.append("<option value=\"amount\">金额</option>");
                }
            }
            else if (Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE.equalsIgnoreCase(param.getDataType()))
            {
                if (StringUtil.isNullOrEmpty(param.getParamType()))
                {
                    sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"requestParams" + param.getSequence() + "\"></div>");
                    }
                    else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"responseParams" + param.getSequence() + "\"></div>");
                    }
                }
                else
                {
                    sb.append("<option value=\"\" >请选择</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"date\" selected=\"selected\" >日期</option>");
                }
                else
                {
                    sb.append("<option value=\"date\">日期</option>");
                }
            }
        }
        else if (Constant.Interface.INTERFACE_TYPE_IN.equalsIgnoreCase(interfacePacketsDefinition.getInOrOut()))
        {
            // if
            // (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
            // {
            if (Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING.equalsIgnoreCase(param.getDataType()))
            {
                if (StringUtil.isNullOrEmpty(param.getParamType()))
                {
                    sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"requestParams" + param.getSequence() + "\"></div>");
                    }
                    else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"responseParams" + param.getSequence() + "\"></div>");
                    }
                }
                else
                {
                    sb.append("<option value=\"\" >请选择</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_PASSWORD.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"password\" selected=\"selected\" >密码</option>");
                }
                else
                {
                    sb.append("<option value=\"password\">密码</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"constant\" selected=\"selected\" >常量</option>");
                }
                else
                {
                    sb.append("<option value=\"constant\">常量</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_TRANSPARAM.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"transParam\" selected=\"selected\" >交易参数</option>");
                }
                else
                {
                    sb.append("<option value=\"transParam\">交易参数</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_RANDOM.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"random\" selected=\"selected\" >随机参数</option>");
                }
                else
                {
                    sb.append("<option value=\"random\">随机数</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_COMBINNUM.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"combinNum\" selected=\"selected\" >组合数</option>");
                }
                else
                {
                    sb.append("<option value=\"combinNum\">组合数</option>");
                }

                if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_AMOUNT.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"amount\" selected=\"selected\" >金额</option>");
                }
                else
                {
                    sb.append("<option value=\"amount\">金额</option>");
                }
            }
            else if (Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE.equalsIgnoreCase(param.getDataType()))
            {
                if (StringUtil.isNullOrEmpty(param.getParamType()))
                {
                    sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"requestParams" + param.getSequence() + "\"></div>");
                    }
                    else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
                    {
                        sb.append("<div id=\"responseParams" + param.getSequence() + "\"></div>");
                    }
                }
                else
                {
                    sb.append("<option value=\"\" >请选择</option>");
                }
                if (Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE.equalsIgnoreCase(param.getParamType()))
                {
                    sb.append("<option value=\"date\" selected=\"selected\" >日期</option>");
                }
                else
                {
                    sb.append("<option value=\"date\">日期</option>");
                }
            }
            // }
            // else if
            // (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
            // {
            // if (param.getDataType().equalsIgnoreCase(
            // Constant.Interface.INTERFACE_PARAM_DATATYPE_STRING))
            // {
            // if (StringUtil.isNullOrEmpty(param.getParamType()))
            // {
            // sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
            // if
            // (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
            // {
            // sb.append("<div id=\"requestParams" + param.getSequence()
            // + "\"></div>");
            // }
            // else if
            // (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
            // {
            // sb.append("<div id=\"responseParams" + param.getSequence()
            // + "\"></div>");
            // }
            // }
            // else
            // {
            // sb.append("<option value=\"\" >请选择</option>");
            // }
            // if
            // (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_RETURN_SUCCESS.equalsIgnoreCase(param.getParamType()))
            // {
            // sb.append("<option value=\"returnSuccess\" selected=\"selected\" >正常返回</option>");
            // }
            // else
            // {
            // sb.append("<option value=\"returnSuccess\">正常返回</option>");
            // }
            // if
            // (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_RETURN_FAIL.equalsIgnoreCase(param.getParamType()))
            // {
            // sb.append("<option value=\"returnFail\" selected=\"selected\" >异常返回</option>");
            // }
            // else
            // {
            // sb.append("<option value=\"returnFail\">异常返回</option>");
            // }
            //
            // }
            // else if
            // (Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE.equalsIgnoreCase(param.getDataType()))
            // {
            // if (StringUtil.isNullOrEmpty(param.getParamType()))
            // {
            // sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
            // if
            // (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
            // {
            // sb.append("<div id=\"requestParams" + param.getSequence()
            // + "\"></div>");
            // }
            // else if
            // (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
            // {
            // sb.append("<div id=\"responseParams" + param.getSequence()
            // + "\"></div>");
            // }
            // }
            // else
            // {
            // sb.append("<option value=\"\" >请选择</option>");
            // }
            // if
            // (Constant.Interface.INTERFACE_PARAM_DATATYPE_DATE.equalsIgnoreCase(param.getParamType()))
            // {
            // sb.append("<option value=\"date\" selected=\"selected\" >日期</option>");
            // }
            // else
            // {
            // sb.append("<option value=\"date\">日期</option>");
            // }
            // }
            // }
        }

        sb.append("</select>");
        if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
        {
            sb.append("<div id=\"requestParams" + param.getSequence() + "\">");
        }
        else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
        {
            sb.append("<div id=\"responseParams" + param.getSequence() + "\">");
        }
        if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_PASSWORD.equalsIgnoreCase(param.getParamType()))
        {
            Merchant merchant = merchantService.queryMerchantById(interfacePacketsDefinition.getMerchantId());
            List<SecurityCredential> scList = securityCredentialService.querySecurityCredentialByIdentity(merchant);
            sb.append("设置：<select name=\"inputParamName\" id=\"inputParamName\">");
            sb.append("<option value=\"\">请选择</option>");
            for (SecurityCredential securityCredential : scList)
            {
                if (String.valueOf(securityCredential.getSecurityId()).equalsIgnoreCase(
                    param.getInputParamName()))
                {
                    sb.append("<option value=\"" + securityCredential.getSecurityId()
                              + "\" selected=\"selected\" >"
                              + securityCredential.getSecurityName() + "</option>");
                }
                else
                {
                    sb.append("<option value=\"" + securityCredential.getSecurityId() + "\">"
                              + securityCredential.getSecurityName() + "</option>");
                }
            }
            sb.append("</select>");
            sb.append(getIsCapitalByInterfaceParam(param));
            sb.append(getInBodyByInterfaceParam(param));
        }
        else if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT.equalsIgnoreCase(param.getParamType()))
        {
            List<InterfaceConstant> interfaceConstants = interfaceConstantService.getInterfaceConstantByParams(
                interfacePacketsDefinition.getMerchantId(), IdentityType.MERCHANT.toString());
            sb.append("常量：<select name=\"outParamName\" id=\"outParamName\">");
            sb.append("<option value=\"\">请选择</option>");
            for (InterfaceConstant interfaceConstant : interfaceConstants)
            {
                if (interfaceConstant.getKey().equalsIgnoreCase(param.getOutParamName()))
                {
                    sb.append("<option value=\"" + interfaceConstant.getKey()
                              + "\" selected=\"selected\" >" + interfaceConstant.getKey() + "="
                              + interfaceConstant.getValue() + "</option>");
                }
                else
                {
                    sb.append("<option value=\"" + interfaceConstant.getKey() + "\">"
                              + interfaceConstant.getKey() + "=" + interfaceConstant.getValue()
                              + "</option>");
                }
            }
            sb.append("</select> ");
            sb.append(getIsCapitalByInterfaceParam(param));
            sb.append(getInBodyByInterfaceParam(param));
        }
        else if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_TRANSPARAM.equalsIgnoreCase(param.getParamType()))
        {
            // select 加密类型
            if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("加密类型：<select name=\"encryptionFunction\" id=\"encryptionFunction\" onchange=\"addRequestInterfaceParamPropertyByEncryptionFunction("
                          + param.getSequence() + ")\" >");
            }
            else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("加密类型：<select name=\"encryptionFunction\" id=\"encryptionFunction\" onchange=\"addResponseInterfaceParamPropertyByEncryptionFunction("
                          + param.getSequence() + ")\" >");
            }
            if (StringUtil.isNullOrEmpty(param.getEncryptionFunction()))
            {
                sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
            }
            else
            {
                sb.append("<option value=\"\">请选择</option>");
            }
            if (Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(param.getEncryptionFunction()))
            {
                sb.append("<option value=\"NO_NEED\" selected=\"selected\" >不加密</option>");
            }
            else
            {
                sb.append("<option value=\"NO_NEED\">不加密</option>");
            }
            if (Constant.SecurityCredential.RSA.equalsIgnoreCase(param.getEncryptionFunction()))
            {
                sb.append("<option value=\"RSA\" selected=\"selected\" >RSA</option>");
            }
            else
            {
                sb.append("<option value=\"RSA\">RSA</option>");
            }
            if (param.getEncryptionFunction().equalsIgnoreCase(Constant.SecurityCredential.MD5))
            {
                sb.append("<option value=\"MD5\" selected=\"selected\" >MD5</option>");
            }
            else
            {
                sb.append("<option value=\"MD5\">MD5</option>");
            }
            if (param.getEncryptionFunction().equalsIgnoreCase(
                Constant.SecurityCredential.MD5_CMPAY))
            {
                sb.append("<option value=\"MD5_CMPAY\" selected=\"selected\" >MD5_CMPAY</option>");
            }
            else
            {
                sb.append("<option value=\"MD5_CMPAY\">MD5_CMPAY</option>");
            }
            if (param.getEncryptionFunction().equalsIgnoreCase("MAC"))
            {
                sb.append("<option value=\"MAC\" selected=\"selected\" >MAC</option>");
            }
            else
            {
                sb.append("<option value=\"MAC\">MAC</option>");
            }
            if (param.getEncryptionFunction().equalsIgnoreCase(Constant.SecurityCredential.JFMD5))
            {
                sb.append("<option value=\"JFMD5\" selected=\"selected\" >JFMD5</option>");
            }
            else
            {
                sb.append("<option value=\"JFMD5\">JFMD5</option>");
            }
            if (param.getEncryptionFunction().equalsIgnoreCase(Constant.SecurityCredential.MD5_YS))
            {
                sb.append("<option value=\"MD5_YS\" selected=\"selected\" >MD5_YS</option>");
            }
            else
            {
                sb.append("<option value=\"MD5_YS\">MD5_YS</option>");
            }

            if (param.getEncryptionFunction().equalsIgnoreCase(Constant.SecurityCredential.MD5_HX))
            {
                sb.append("<option value=\"MD5_HX\" selected=\"selected\" >MD5_HX</option>");
            }
            else
            {
                sb.append("<option value=\"MD5_HX\">MD5_HX</option>");
            }

            if (param.getEncryptionFunction().equalsIgnoreCase(Constant.SecurityCredential.AES))
            {
                sb.append("<option value=\"AES\" selected=\"selected\">AES</option>");
            }
            else
            {
                sb.append("<option value=\"AES\">AES</option>");
            }

            sb.append("</select> ");
            if (Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("<div id=\"requestEncryptionFunction" + param.getSequence() + "\">");
            }
            else if (Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE.equalsIgnoreCase(param.getConnectionModule()))
            {
                sb.append("<div id=\"responseEncryptionFunction" + param.getSequence() + "\">");
            }

            sb.append(getParamsByInterfaceParam(interfacePacketsDefinition, param));

            sb.append(getIsCapitalByInterfaceParam(param));
            sb.append(getInBodyByInterfaceParam(param));
            sb.append("</div>");
        }
        else if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_AMOUNT.equalsIgnoreCase(param.getParamType()))
        {
            sb.append(getParamsByInterfaceParam(interfacePacketsDefinition, param));
            sb.append("<select name=\"encryptionFunction\" id=\"encryptionFunction\" style=\"display: none;\" onchange=\"addRequestInterfaceParamPropertyByEncryptionFunction("
                      + param.getSequence() + ")\" >");
            sb.append("<option value=\"NO_NEED\" selected=\"selected\">不加密</option>");
            sb.append("</select> ");
            sb.append("金额格式：<input type=\"text\" name=\"formatType\" id=\"formatType\" value=\""
                      + param.getFormatType() + "\" maxlength=\"20\" style=\"width:120px;\" /> ");
            sb.append(getInBodyByInterfaceParam(param));
        }
        // ---------------------------------------
        else if (Constant.Interface.INTERFACE_PARAM_PARAMTYPE_DATE.equalsIgnoreCase(param.getParamType()))
        {
            // sb.append("日期格式：<input type=\"text\" name=\"formatType\" id=\"formatType\" value=\""
            // + param.getFormatType() + "\" maxlength=\"20\" style=\"width:120px;\" /> ")
            sb.append("时间种类：<select name=\"inputParamName\" id=\"inputParamName\" >");

            if (StringUtil.isNullOrEmpty(param.getInputParamName()))
            {
                sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
            }
            else
            {
                sb.append("<option value=\"\" >请选择</option>");
            }
            if (EntityConstant.Delivery.DELIVERY_START_TIME.equalsIgnoreCase(param.getInputParamName()))
            {
                sb.append("<option value=\"deliveryStartTime\" selected=\"selected\" >发货时间</option>");
            }
            else
            {
                sb.append("<option value=\"deliveryStartTime\">发货时间</option>");
            }

            sb.append("</select>");
            sb.append("日期格式：<input type=\"text\" name=\"formatType\" id=\"formatType\" value=\""
                      + param.getFormatType() + "\" maxlength=\"20\" style=\"width:120px;\" /> ");
            sb.append(getInBodyByInterfaceParam(param));
        }
        else if (Constant.Interface.INTERFACE_PARAM_RANDOM.equalsIgnoreCase(param.getParamType()))
        {
            sb.append(getIsCapitalByInterfaceParam(param));
            sb.append(getInBodyByInterfaceParam(param));
        }
        else if (Constant.Interface.INTERFACE_PARAM_COMBINNUM.equalsIgnoreCase(param.getParamType()))
        {
            sb.append(getIsCapitalByInterfaceParam(param));
            sb.append(getInBodyByInterfaceParam(param));
        }

        // ------------------------------------------
        sb.append("</div>");
        sb.append("</div>");
        return sb;
    }

    public StringBuffer getIsCapitalByInterfaceParam(InterfaceParam param)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("设置大小写：<select name=\"isCapital\" id=\"isCapital\">");
        if (StringUtil.isNullOrEmpty(param.getIsCapital()))
        {
            sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
        }
        else
        {
            sb.append("<option value=\"\">请选择</option>");
        }
        if (!StringUtil.isNullOrEmpty(param.getIsCapital())
            && Constant.Interface.INTERFACE_IS_CAPITAL_UP.equalsIgnoreCase(param.getIsCapital()))
        {
            sb.append("<option value=\"up\" selected=\"selected\" >大写</option>");
        }
        else
        {
            sb.append("<option value=\"up\">大写</option>");
        }
        if (!StringUtil.isNullOrEmpty(param.getIsCapital())
            && Constant.Interface.INTERFACE_IS_CAPITAL_DOWN.equalsIgnoreCase(param.getIsCapital()))
        {
            sb.append("<option value=\"down\" selected=\"selected\" >小写</option>");
        }
        else
        {
            sb.append("<option value=\"down\">小写</option>");
        }
        if (!StringUtil.isNullOrEmpty(param.getIsCapital())
            && Constant.Interface.INTERFACE_IS_CAPITAL_UNCHANGED.equalsIgnoreCase(param.getIsCapital()))
        {
            sb.append("<option value=\"unchanged\" selected=\"selected\" >不变</option>");
        }
        else
        {
            sb.append("<option value=\"unchanged\">不变</option>");
        }
        sb.append("</select>");
        return sb;
    }

    public StringBuffer getInBodyByInterfaceParam(InterfaceParam param)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("是否存在于报文体内：<select name=\"inBody\" id=\"inBody\" >");
        if (Constant.Interface.INTERFACE_INBODY_TRUE.equalsIgnoreCase(param.getInBody()))
        {
            sb.append("<option value=\"true\" selected=\"selected\" >是</option>");
        }
        else
        {
            sb.append("<option value=\"true\">是</option>");
        }
        if (Constant.Interface.INTERFACE_INBODY_FALSE.equalsIgnoreCase(param.getInBody()))
        {
            sb.append("<option value=\"false\" selected=\"selected\" >否</option>");
        }
        else
        {
            sb.append("<option value=\"false\">否</option>");
        }
        sb.append("</select>");
        return sb;
    }

    public StringBuffer getParamsByInterfaceParam(InterfacePacketsDefinition interfacePacketsDefinition,
                                                  InterfaceParam param)
    {
        StringBuffer sb = new StringBuffer();
        if (param.getConnectionModule().equalsIgnoreCase(
            Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST))
        {
            if (Constant.Interface.INTERFACE_TYPE_SEND_ORDER.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType())
                || Constant.Interface.INTERFACE_TYPE_QUERY_ORDER.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType())
                || Constant.Interface.INTERFACE_TYPE_SUPPLY_SEND_ORDER_FLOW.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType())
                || Constant.Interface.INTERFACE_TYPE_SUPPLY_QUERY_ORDER_FLOW.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType()))
            {
                if (null == param.getEncryptionFunction()
                    || Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(param.getEncryptionFunction()))
                {
                    sb.append("<select name=\"inputParamName\" id=\"inputParamName\">");
                    if (StringUtil.isNullOrEmpty(param.getInputParamName()))
                    {
                        sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"\">请选择</option>");
                    }
                    if (EntityConstant.Delivery.DELIVERY_ID.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"deliveryId\" selected=\"selected\" >订单号(deliveryId)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"deliveryId\">订单号(deliveryId)</option>");
                    }
                    if (EntityConstant.Order.MERCHANT_ORDER_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantOrderNo\" selected=\"selected\" >商户订单号(merchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantOrderNo\">商户订单号(merchantOrderNo)</option>");
                    }
                    if (EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\" selected=\"selected\" >供货商订单编号(supplyMerchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\">供货商订单编号(supplyMerchantOrderNo)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_FACE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productFace\" selected=\"selected\" >产品面值(productFace)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productFace\">产品面值(productFace)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_SALE_DISCOUNT.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productSaleDiscount\" selected=\"selected\" >产品销售折扣(productSaleDiscount)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productSaleDiscount\">产品销售折扣(productSaleDiscount)</option>");
                    }
                    if (EntityConstant.Delivery.COST_DISCOUNT.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"costDiscount\" selected=\"selected\" >成本折扣(costDiscount)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"costDiscount\">成本折扣(costDiscount)</option>");
                    }
                    if (EntityConstant.Delivery.COST_FEE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"costFee\" selected=\"selected\" >成本金额(costFee)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"costFee\">成本金额(costFee)</option>");
                    }
                    if (EntityConstant.Delivery.USER_CODE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"userCode\" selected=\"selected\" >用户手机号(userCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"userCode\">用户手机号(userCode)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_NAME.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productName\" selected=\"selected\" >产品名称(productName)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productName\">产品名称(productName)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_ID.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productId\" selected=\"selected\" >产品ID(productId)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productId\">产品ID(productId)</option>");
                    }
                    if (EntityConstant.Delivery.COOPORDERSUCCESSTIME.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderFinishTime\" selected=\"selected\">充值成功时间(orderFinishTime)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderFinishTime\">充值成功时间(orderFinishTime)</option>");
                    }
                    if (EntityConstant.Delivery.FILED_CODE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"errorCode\" selected=\"selected\">失败代码(failedCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"errorCode\" >失败代码(failedCode)</option>");
                    }
                    if (EntityConstant.Delivery.FILED_REASON.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"closereason\" selected=\"selected\">失败原因(failedReason)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"closeReason\">失败原因(failedReason)</option>");
                    }
                    if (EntityConstant.Delivery.VERSION.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"version\" selected=\"selected\">版本号(version)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"version\">版本号(version)</option>");
                    }

                    if (EntityConstant.Delivery.DISPLAYVALUE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"displayValue\" selected=\"selected\" >产品面额(displayValue)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"displayValue\">产品面额(displayValue)</option>");
                    }

                    if (EntityConstant.Delivery.PRODUCT_TYPE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productType\" selected=\"selected\">充值产品类型(productType)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productType\">充值产品类型(productType)</option>");
                    }
                    if (EntityConstant.Delivery.CP.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"cp\" selected=\"selected\">运营商(cp)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"cp\">运营商(cp)</option>");
                    }
                    if (EntityConstant.Delivery.CITY_CODE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"cityCode\" selected=\"selected\">地区码(cityCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"cityCode\">地区码(cityCode)</option>");
                    }
                    sb.append("</select>");
                }
                else
                {
                    sb.append("加密参数: <textarea id=\"encryptionParamNames\" cols='60' rows='3' >"
                              + param.getEncryptionParamNames() + "</textarea>");
                }
            }
            else if (Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType())
                     || Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FLOW.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType()))
            {
                if (Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(param.getEncryptionFunction()))
                {
                    sb.append("<select name=\"inputParamName\" id=\"inputParamName\">");
                    if (StringUtil.isNullOrEmpty(param.getInputParamName()))
                    {
                        sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"\">请选择</option>");
                    }
                    if (EntityConstant.Order.MERCHANT_ORDER_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantOrderNo\" selected=\"selected\" >商户订单号(merchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantOrderNo\">商户订单号(merchantOrderNo)</option>");
                    }
                    if (EntityConstant.Order.ORDER_DESC.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderDesc\" selected=\"selected\" >订单描述(orderDesc)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderDesc\">订单描述(orderDesc)</option>");
                    }
                    if (EntityConstant.Order.ORDER_STATUS.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderStatus\" selected=\"selected\" >订单状态(orderStatus)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderStatus\">订单状态(orderStatus)</option>");
                    }
                    if (EntityConstant.Order.ORDER_SUCCESS_FEE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderSuccessFee\" selected=\"selected\" >订单成功金额(orderSuccessFee)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderSuccessFee\">订单成功金额(orderSuccessFee)</option>");
                    }
                    // ----------------------
                    if (EntityConstant.Order.ORDER_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderNo\" selected=\"selected\" >订单号(orderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderNo\">订单号(orderNo)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_FACE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productFace\" selected=\"selected\" >产品面值(productFace)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productFace\">产品面值(productFace)</option>");
                    }
                    if (EntityConstant.AirtimeProduct.PRODUCT_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productNo\" selected=\"selected\" >产品编号(productNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productNo\">产品编号(productNo)</option>");
                    }
                    if (EntityConstant.Order.USER_CODE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"userCode\" selected=\"selected\" >用户手机号(userCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"userCode\">用户手机号(userCode)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_SALE_DISCOUNT.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productSaleDiscount\" selected=\"productSaleDiscount\" >产品销售折扣(productSaleDiscount)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productSaleDiscount\">产品销售折扣(productSaleDiscount)</option>");
                    }
                    if (EntityConstant.Delivery.COOPORDERSUCCESSTIME.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderFinishTime\" selected=\"selected\">充值成功时间(orderFinishTime)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderFinishTime\">充值成功时间(orderFinishTime)</option>");
                    }
                    if (EntityConstant.Delivery.FILED_CODE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"errorCode\" selected=\"selected\">失败代码(failedCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"errorCode\" >失败代码(failedCode)</option>");
                    }
                    if (EntityConstant.Delivery.FILED_REASON.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"closeReason\" selected=\"selected\">失败原因(failedReason)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"closeReason\">失败原因(failedReason)</option>");
                    }
                    if (EntityConstant.Delivery.VERSION.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"version\" selected=\"selected\">版本号(version)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"version\">版本号(version)</option>");
                    }
                    if (EntityConstant.MerchantResponse.MERCHANT_CODE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantCode\" selected=\"selected\" >商户编号(merchantCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                    }
                    if (EntityConstant.Delivery.DISPLAYVALUE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"displayValue\" selected=\"selected\" >产品面额(displayValue)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"displayValue\">产品面额(displayValue)</option>");
                    }
                    sb.append("</select>");
                }
                else
                {
                    sb.append("加密参数：  <textarea id=\"encryptionParamNames\" cols='60' rows='3' >"
                              + param.getEncryptionParamNames() + "</textarea>");
                }
            }
            // 增加淘宝代理商接口
            else if (Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType()))
            {
                if (Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(param.getEncryptionFunction()))
                {
                    sb.append("<select name=\"inputParamName\" id=\"inputParamName\">");
                    if (StringUtil.isNullOrEmpty(param.getInputParamName()))
                    {
                        sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"\">请选择</option>");
                    }
                    if (EntityConstant.Order.MERCHANT_ORDER_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantOrderNo\" selected=\"selected\" >商户订单号(merchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantOrderNo\">商户订单号(merchantOrderNo)</option>");
                    }
                    if (EntityConstant.Order.ORDER_DESC.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderDesc\" selected=\"selected\" >订单描述(orderDesc)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderDesc\">订单描述(orderDesc)</option>");
                    }
                    if (EntityConstant.Order.ORDER_STATUS.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderStatus\" selected=\"selected\" >订单状态(orderStatus)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderStatus\">订单状态(orderStatus)</option>");
                    }
                    if (EntityConstant.Order.ORDER_SUCCESS_FEE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderSuccessFee\" selected=\"selected\" >订单成功金额(orderSuccessFee)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderSuccessFee\">订单成功金额(orderSuccessFee)</option>");
                    }
                    // ----------------------
                    if (EntityConstant.Order.ORDER_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderNo\" selected=\"selected\" >订单号(orderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderNo\">订单号(orderNo)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_FACE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productFace\" selected=\"selected\" >产品面值(productFace)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productFace\">产品面值(productFace)</option>");
                    }
                    if (EntityConstant.AirtimeProduct.PRODUCT_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productNo\" selected=\"selected\" >产品编号(productNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productNo\">产品编号(productNo)</option>");
                    }
                    if (EntityConstant.Order.USER_CODE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"userCode\" selected=\"selected\" >用户手机号(userCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"userCode\">用户手机号(userCode)</option>");
                    }
                    if (EntityConstant.Delivery.PRODUCT_SALE_DISCOUNT.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"productSaleDiscount\" selected=\"productSaleDiscount\" >产品销售折扣(userCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"productSaleDiscount\">产品销售折扣(productSaleDiscount)</option>");
                    }
                    if (EntityConstant.Delivery.COOPORDERSUCCESSTIME.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderFinishTime\" selected=\"selected\">充值成功时间(orderFinishTime)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderFinishTime\">充值成功时间(orderFinishTime)</option>");
                    }
                    if (EntityConstant.Delivery.FILED_CODE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"errorCode\" selected=\"selected\">失败代码(failedCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"errorCode\" >失败代码(failedCode)</option>");
                    }
                    if (EntityConstant.Delivery.FILED_REASON.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"closeReason\" selected=\"selected\">失败原因(failedReason)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"closeReason\">失败原因(failedReason)</option>");
                    }
                    if (EntityConstant.Delivery.VERSION.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"version\" selected=\"selected\">版本号(version)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"version\">版本号(version)</option>");
                    }
                    if (EntityConstant.MerchantResponse.MERCHANT_CODE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantCode\" selected=\"selected\" >商户编号(merchantCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                    }
                    if (EntityConstant.Delivery.DISPLAYVALUE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"displayValue\" selected=\"selected\" >产品面额(displayValue)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"displayValue\">产品面额(displayValue)</option>");
                    }
                    sb.append("</select>");
                }
                else
                {
                    sb.append("加密参数：  <textarea id=\"encryptionParamNames\" cols='60' rows='3' >"
                              + param.getEncryptionParamNames() + "</textarea>");
                }
            }
            else if (Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType())
                     || Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW.equalsIgnoreCase(interfacePacketsDefinition.getInterfaceType()))
            {
                if (Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(param.getEncryptionFunction()))
                {
                    sb.append("<select name=\"inputParamName\" id=\"inputParamName\">");
                    if (StringUtil.isNullOrEmpty(param.getInputParamName()))
                    {
                        sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"\">请选择</option>");
                    }
                    if (EntityConstant.MerchantResponse.DELIVERY_ID.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"deliveryId\" selected=\"selected\" >订单号(deliveryId)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"deliveryId\">订单号(deliveryId)</option>");
                    }
                    if (EntityConstant.MerchantResponse.ERROR_CODE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"errorCode\" selected=\"selected\" >接口返回码(errorCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"errorCode\">接口返回码(errorCode)</option>");
                    }
                    if (EntityConstant.MerchantResponse.MERCHANT_STATUS.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantStatus\" selected=\"selected\" >交易状态(merchantStatus)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantStatus\">交易状态(merchantStatus)</option>");
                    }
                    if (EntityConstant.MerchantResponse.MSG.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"msg\" selected=\"selected\" >描述信息(msg)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"msg\">描述信息(msg)</option>");
                    }
                    if (EntityConstant.MerchantResponse.ORDER_SUCCESS_FEE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderSuccessFee\" selected=\"selected\" >成功金额(orderSuccessFee)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderSuccessFee\">成功金额(orderSuccessFee)</option>");
                    }
                    if (EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\" selected=\"selected\" >供货商订单编号(supplyMerchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\">供货商订单编号(supplyMerchantOrderNo)</option>");
                    }
                    if (EntityConstant.MerchantResponse.MERCHANT_CODE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantCode\" selected=\"selected\" >商户编号(merchantCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                    }

                    if (EntityConstant.TransactionHistory.TYPE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"type\" selected=\"selected\" >接口类型(type)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"type\">接口类型(type)</option>");
                    }
                    if (EntityConstant.Delivery.VERSION.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"version\" selected=\"selected\" >版本号(version )</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"version\">版本号(version )</option>");
                    }
                    if (EntityConstant.MerchantResponse.REQUESTID.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"requestId\" selected=\"selected\" >请求交易流水号(requestId )</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"requestId\">请求交易流水号(requestId )</option>");
                    }

                    sb.append("</select>");
                }
                else
                {
                    sb.append("加密参数:  <textarea id=\"encryptionParamNames\" cols='60' rows='3' >"
                              + param.getEncryptionParamNames() + "</textarea>");
                }
            }
        }
        else
        {
            if (Constant.Interface.INTERFACE_TYPE_OUT.equalsIgnoreCase(interfacePacketsDefinition.getInOrOut()))
            {
                if (null == param.getEncryptionFunction()
                    || Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(param.getEncryptionFunction()))
                {
                    sb.append("<select name=\"inputParamName\" id=\"inputParamName\">");
                    if (StringUtil.isNullOrEmpty(param.getInputParamName()))
                    {
                        sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"\">请选择</option>");
                    }
                    if (EntityConstant.MerchantResponse.DELIVERY_ID.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"deliveryId\" selected=\"selected\" >订单号(deliveryId)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"deliveryId\">订单号(deliveryId)</option>");
                    }
                    if (!StringUtil.isNullOrEmpty(param.getInputParamName())
                        && EntityConstant.MerchantResponse.ERROR_CODE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"errorCode\" selected=\"selected\" >接口返回码(errorCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"errorCode\">接口返回码(errorCode)</option>");
                    }
                    if (!StringUtil.isNullOrEmpty(param.getInputParamName())
                        && EntityConstant.MerchantResponse.MERCHANT_STATUS.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantStatus\" selected=\"selected\" >交易状态(merchantStatus)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantStatus\">交易状态(merchantStatus)</option>");
                    }
                    if (!StringUtil.isNullOrEmpty(param.getInputParamName())
                        && EntityConstant.MerchantResponse.MSG.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"msg\" selected=\"selected\" >描述信息(msg)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"msg\">描述信息(msg)</option>");
                    }

                    if (EntityConstant.MerchantResponse.MERCHANT_CODE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantCode\" selected=\"selected\" >商户编号(merchantCode)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantCode\">商户编号(merchantCode)</option>");
                    }
                    if (!StringUtil.isNullOrEmpty(param.getInputParamName())
                        && EntityConstant.MerchantResponse.ORDER_SUCCESS_FEE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderSuccessFee\" selected=\"selected\" >成功金额(orderSuccessFee)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderSuccessFee\">成功金额(orderSuccessFee)</option>");
                    }
                    if (EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\" selected=\"selected\" >供货商订单编号(supplyMerchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\">供货商订单编号(supplyMerchantOrderNo)</option>");
                    }

                    if (EntityConstant.TransactionHistory.TYPE.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"type\" selected=\"selected\" >接口类型(type)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"type\">接口类型(type)</option>");
                    }
                    if (EntityConstant.Delivery.VERSION.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"version\" selected=\"selected\" >版本号(version )</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"version\">版本号(version )</option>");
                    }
                    if (EntityConstant.MerchantResponse.REQUESTID.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"requestId\" selected=\"selected\" >请求交易流水号(requestId )</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"requestId\">请求交易流水号(requestId )</option>");
                    }
                    sb.append("</select>");
                }
                else
                {
                    sb.append("加密参数:  <textarea id=\"encryptionParamNames\" cols='60' rows='3' >"
                              + param.getEncryptionParamNames() + "</textarea>");
                }
            }
            else if (Constant.Interface.INTERFACE_TYPE_IN.equalsIgnoreCase(interfacePacketsDefinition.getInOrOut()))
            {
                if (null == param.getEncryptionFunction()
                    || Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(param.getEncryptionFunction()))
                {
                    sb.append("<select name=\"inputParamName\" id=\"inputParamName\">");
                    if (StringUtil.isNullOrEmpty(param.getInputParamName()))
                    {
                        sb.append("<option value=\"\" selected=\"selected\" >请选择</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"\">请选择</option>");
                    }
                    if (EntityConstant.Order.ORDER_NO.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderNo\" selected=\"selected\" >订单号(orderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderNo\">订单号(orderNo)</option>");
                    }
                    if (EntityConstant.Order.MERCHANT_ORDER_NO.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"merchantOrderNo\" selected=\"selected\" >商户订单号(merchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"merchantOrderNo\">代理商商户订单号(merchantOrderNo)</option>");
                    }
                    if (EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO.equals(param.getInputParamName()))
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\" selected=\"selected\" >供货商订单编号(supplyMerchantOrderNo)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"supplyMerchantOrderNo\">供货商订单编号(supplyMerchantOrderNo)</option>");
                    }
                    if (EntityConstant.Order.ORDER_DESC.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderDesc\" selected=\"selected\" >订单描述(orderDesc)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderDesc\">订单描述(orderDesc)</option>");
                    }
                    if (EntityConstant.Order.ORDER_STATUS.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderStatus\" selected=\"selected\" >订单状态(orderStatus)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderStatus\">订单状态(orderStatus)</option>");
                    }
                    if (EntityConstant.Order.ORDER_SUCCESS_FEE.equalsIgnoreCase(param.getInputParamName()))
                    {
                        sb.append("<option value=\"orderSuccessFee\" selected=\"selected\" >订单成功金额(orderSuccessFee)</option>");
                    }
                    else
                    {
                        sb.append("<option value=\"orderSuccessFee\">订单成功金额(orderSuccessFee)</option>");
                    }
                    sb.append("</select>");
                }
                else
                {
                    sb.append("加密参数:  <textarea id=\"encryptionParamNames\" cols='60' rows='3' >"
                              + param.getEncryptionParamNames() + "</textarea>");
                }
            }
        }
        return sb;
    }

    public StringBuffer buildMerchantRequests(List<MerchantRequest> merchantRequests)
    {
        StringBuffer merchantRequest_html = new StringBuffer();
        for (int i = 1; i <= merchantRequests.size(); i++ )
        {
            MerchantRequest merchantRequest = merchantRequests.get(i - 1);
            merchantRequest_html.append("<tr id=\"" + i + "\">");
            merchantRequest_html.append("<td>");
            merchantRequest_html.append("<input type=\"text\" name=\"timeDifferenceLow\" id=\"timeDifferenceLow\" value=\""
                                        + (BeanUtils.isNull(merchantRequest.getTimeDifferenceLow()) ? StringUtil.initString() : merchantRequest.getTimeDifferenceLow())
                                        + "\" >");
            merchantRequest_html.append("</td>");
            merchantRequest_html.append("<td>");
            merchantRequest_html.append("<input type=\"text\" name=\"timeDifferenceHigh\" id=\"timeDifferenceHigh\" value=\""
                                        + (BeanUtils.isNull(merchantRequest.getTimeDifferenceHigh()) ? StringUtil.initString() : merchantRequest.getTimeDifferenceHigh())
                                        + "\" >");
            merchantRequest_html.append("</td>");
            merchantRequest_html.append("<td>");
            merchantRequest_html.append("<input type=\"text\" name=\"intervalTime\" id=\"intervalTime\" value=\""
                                        + (BeanUtils.isNull(merchantRequest.getIntervalTime()) ? StringUtil.initString() : merchantRequest.getIntervalTime())
                                        + "\" >");
            merchantRequest_html.append("</td>");
            merchantRequest_html.append("<td>");
            merchantRequest_html.append("<select name=\"intervalUnit\" id=\"intervalUnit\" >");
            if (merchantRequest.getIntervalUnit().equalsIgnoreCase(
                Constant.DateUnit.TIME_UNIT_SECOND))
            {
                merchantRequest_html.append("<option value=\"s\" selected=\"selected\" >秒</option>");
            }
            else
            {
                merchantRequest_html.append("<option value=\"s\">秒</option>");
            }
            if (merchantRequest.getIntervalUnit().equalsIgnoreCase(
                Constant.DateUnit.TIME_UNIT_MINUTE))
            {
                merchantRequest_html.append("<option value=\"m\">分</option>");
            }
            else
            {
                merchantRequest_html.append("<option value=\"m\">分</option>");
            }
            if (merchantRequest.getIntervalUnit().equalsIgnoreCase(
                Constant.DateUnit.TIME_UNIT_HOUR))
            {
                merchantRequest_html.append("<option value=\"h\" selected=\"selected\" >时</option>");
            }
            else
            {
                merchantRequest_html.append("<option value=\"h\">时</option>");
            }
            if (merchantRequest.getIntervalUnit().equalsIgnoreCase(Constant.DateUnit.TIME_UNIT_DAY))
            {
                merchantRequest_html.append("<option value=\"d\" selected=\"selected\" >天</option>");
            }
            else
            {
                merchantRequest_html.append("<option value=\"d\">天</option>");
            }
            merchantRequest_html.append("</select>");
            merchantRequest_html.append("</td>");
            merchantRequest_html.append("<td>");
            merchantRequest_html.append("<a onclick=\"deleteMerchantRequest(" + i + ")\">[删除]</a>");
            merchantRequest_html.append("</td>");
            merchantRequest_html.append("<td style=\"display: none;\">");
            merchantRequest_html.append("<input type=\"hidden\" name=\"id\" id=\"id\" value=\""
                                        + merchantRequest.getId() + "\" >");
            merchantRequest_html.append("</td>");
            merchantRequest_html.append("</tr>");
        }
        return merchantRequest_html;
    }

    public StringBuffer buildMerchantResponse(List<MerchantResponse> merchantResponses,
                                              String interfaceType)
    {
        StringBuffer merchantResponse_html = new StringBuffer();
        for (int i = 1; i <= merchantResponses.size(); i++ )
        {
            MerchantResponse merchantResponse = merchantResponses.get(i - 1);
            merchantResponse_html.append("<tr id=\"" + i + "\">");
            merchantResponse_html.append("<td>");
            merchantResponse_html.append("<input type=\"text\" name=\"errorCode\" id=\"errorCode\" value=\""
                                         + (BeanUtils.isNull(merchantResponse.getErrorCode()) ? StringUtil.initString() : merchantResponse.getErrorCode())
                                         + "\" >");
            merchantResponse_html.append("</td>");
            merchantResponse_html.append("<td>");
            if (merchantResponse.getMerchantStatus() != null)
            {
                merchantResponse_html.append("<input type=\"text\" name=\"merchantStatus\" id=\"merchantStatus\" value=\""
                                             + (BeanUtils.isNull(merchantResponse.getMerchantStatus()) ? StringUtil.initString() : merchantResponse.getMerchantStatus())
                                             + "\" >");
            }
            else
            {
                merchantResponse_html.append("<input type=\"text\" name=\"merchantStatus\" id=\"merchantStatus\" value=\"\" >");
            }
            merchantResponse_html.append("</td>");
            merchantResponse_html.append("<td>");
            merchantResponse_html.append("<input type=\"text\" name=\"merchantStatusInfo\" id=\"merchantStatusInfo\" value=\""
                                         + merchantResponse.getMerchantStatusInfo() + "\" >");
            merchantResponse_html.append("</td>");
            merchantResponse_html.append("<td>");
            merchantResponse_html.append("<select name=\"status\" id=\"status\" >");
            if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SEND_ORDER)
                || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_SEND_ORDER_FLOW))
            {
                if (merchantResponse.getStatus().compareTo(
                    Constant.MerchantResponseStatus.RETRY_STATUS) == 0)
                {
                    merchantResponse_html.append("<option value=\"1\" selected=\"selected\" >重试</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"1\">重试</option>");
                }
                if (merchantResponse.getStatus().compareTo(
                    Constant.MerchantResponseStatus.RE_BIND_STATUS) == 0)
                {
                    merchantResponse_html.append("<option value=\"2\" selected=\"selected\" >重绑</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"2\">重绑</option>");
                }
                if (merchantResponse.getStatus().compareTo(
                    Constant.MerchantResponseStatus.CLOSE_STATUS) == 0)
                {
                    merchantResponse_html.append("<option value=\"3\" selected=\"selected\" >强制关闭</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"3\">强制关闭</option>");
                }
                if (merchantResponse.getStatus().compareTo(
                    Constant.MerchantResponseStatus.SUCCESS_STATUS) == 0)
                {
                    merchantResponse_html.append("<option value=\"4\" selected=\"selected\" >正常成功</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"4\">正常成功</option>");
                }
                if (merchantResponse.getStatus().compareTo(
                    Constant.MerchantResponseStatus.FAIL_STATUS) == 0)
                {
                    merchantResponse_html.append("<option value=\"5\" selected=\"selected\" >正常失败</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"5\">正常失败</option>");
                }
            }
            else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_QUERY_ORDER)
                     || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER)
                     || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_QUERY_ORDER_FLOW)
                     || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW))
            {
                if (merchantResponse.getStatus().compareTo(Constant.OrderStatus.RECHARGING) == 0)
                {
                    merchantResponse_html.append("<option value=\"2\" selected=\"selected\" >充值中</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"2\">充值中</option>");
                }
                if (merchantResponse.getStatus().compareTo(Constant.OrderStatus.SUCCESS) == 0)
                {
                    merchantResponse_html.append("<option value=\"3\" selected=\"selected\" >成功</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"3\">成功</option>");
                }
                if (merchantResponse.getStatus().compareTo(Constant.OrderStatus.FAILURE_ALL) == 0)
                {
                    merchantResponse_html.append("<option value=\"4\" selected=\"selected\" >失败</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"4\">失败</option>");
                }
                if (merchantResponse.getStatus().compareTo(Constant.OrderStatus.SUCCESS_PART) == 0)
                {
                    merchantResponse_html.append("<option value=\"5\" selected=\"selected\" >部分成功</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"5\">部分成功</option>");
                }
            }
            else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER)
                     || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FLOW))
            {
                if (merchantResponse.getStatus().compareTo(Constant.NotifyStatus.NOTIFY_SUCCESS) == 0)
                {
                    merchantResponse_html.append("<option value=\"3\" selected=\"selected\" >通知成功</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"3\">通知成功</option>");
                }
                if (merchantResponse.getStatus().compareTo(Constant.NotifyStatus.NOTIFY_FAIL) == 0)
                {
                    merchantResponse_html.append("<option value=\"4\" selected=\"selected\" >通知失败</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"4\">通知失败</option>");
                }
            }
            // 通知淘宝代理商接口
            else if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER))
            {
                if (merchantResponse.getStatus().compareTo(Constant.NotifyStatus.NOTIFY_SUCCESS) == 0)
                {
                    merchantResponse_html.append("<option value=\"3\" selected=\"selected\" >通知成功</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"3\">通知成功</option>");
                }
                if (merchantResponse.getStatus().compareTo(Constant.NotifyStatus.NOTIFY_FAIL) == 0)
                {
                    merchantResponse_html.append("<option value=\"4\" selected=\"selected\" >通知失败</option>");
                }
                else
                {
                    merchantResponse_html.append("<option value=\"4\">通知失败</option>");
                }
            }
            merchantResponse_html.append("</select>");
            merchantResponse_html.append("</td>");
            merchantResponse_html.append("<td>");
            merchantResponse_html.append("<a onclick=\"deleteMerchantResponse(" + i
                                         + ")\">[删除]</a>");
            merchantResponse_html.append("</td>");
            merchantResponse_html.append("<td style=\"display: none;\">");
            merchantResponse_html.append("<input type=\"hidden\" name=\"id\" id=\"id\" value=\""
                                         + merchantResponse.getId() + "\" >");
            merchantResponse_html.append("</td>");
            merchantResponse_html.append("</tr>");
        }
        return merchantResponse_html;
    }

    @RequestMapping(value = "/toEditInterfacePacketsDefinition")
    public String toEditInterfacePacketsDefinition(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "merchantId", defaultValue = "")
    String merchantId, ModelMap model)
    {
        InterfacePacketsDefinition interfacePacketsDefinition = interfaceService.getInterfacePacketsDefinitionById(Long.valueOf(id));

        InterfacePacketTypeConf request_interfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(
            Long.valueOf(merchantId), interfacePacketsDefinition.getInterfaceType(),
            Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
        InterfacePacketTypeConf response_interfacePacketTypeConf = interfacePacketTypeConfService.getInterfacePacketTypeConfByParams(
            Long.valueOf(merchantId), interfacePacketsDefinition.getInterfaceType(),
            Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);

        model.addAttribute("interfacePacketsDefinition", interfacePacketsDefinition);
        model.addAttribute("request_interfacePacketTypeConf", request_interfacePacketTypeConf);
        model.addAttribute("response_interfacePacketTypeConf", response_interfacePacketTypeConf);
        model.addAttribute("merchantId", merchantId);
        return "interface/toEditInterfacePacketsDefinition";
    }

    @RequestMapping(value = "/getMerchantResponse")
    @ResponseBody
    public String getMerchantResponse(Model model, ServletRequest request)
    {
        MerchantResponse response = merchantResponseService.getMerchantResponseByParams(150000l,
            Constant.Interface.INTERFACE_TYPE_QUERY_ORDER, "100", "3");
        System.out.println(response.getStatus());
        return response.toString();
    }

    @RequestMapping(value = "/initRequestParam")
    @ResponseBody
    public String initRequestParam(@RequestParam(value = "id", defaultValue = "")
    String id, Model model, ServletRequest request)
    {
        StringBuffer requestParams_html = new StringBuffer();
        try
        {
            InterfacePacketsDefinition interfacePacketsDefinition = interfaceService.getInterfacePacketsDefinitionById(Long.valueOf(id));
            List<InterfaceParam> requestParams = interfacePacketsDefinition.getRequestParams();
            requestParams_html = buildParams(interfacePacketsDefinition, requestParams,
                interfacePacketsDefinition.getMerchantId());
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }

        return requestParams_html.toString();
    }

    @RequestMapping(value = "/initResponseParam")
    @ResponseBody
    public String initResponseParam(@RequestParam(value = "id", defaultValue = "")
    String id, Model model, ServletRequest request)
    {
        InterfacePacketsDefinition interfacePacketsDefinition = interfaceService.getInterfacePacketsDefinitionById(Long.valueOf(id));
        List<InterfaceParam> responseParams = interfacePacketsDefinition.getResponseParams();
        StringBuffer responseParams_html = buildParams(interfacePacketsDefinition, responseParams,
            interfacePacketsDefinition.getMerchantId());
        return responseParams_html.toString();
    }

    @RequestMapping(value = "/initMerchantRequest")
    @ResponseBody
    public String initMerchantRequest(@RequestParam(value = "id", defaultValue = "")
    String id, Model model, ServletRequest request)
    {
        InterfacePacketsDefinition interfacePacketsDefinition = interfaceService.getInterfacePacketsDefinitionById(Long.valueOf(id));
        List<MerchantRequest> merchantRequests = merchantRequestService.getMerchantRequestByParams(
            interfacePacketsDefinition.getMerchantId(),
            interfacePacketsDefinition.getInterfaceType());
        StringBuffer merchantRequests_html = buildMerchantRequests(merchantRequests);
        return merchantRequests_html.toString();
    }

    @RequestMapping(value = "/initMerchantResponse")
    @ResponseBody
    public String initMerchantResponse(@RequestParam(value = "id", defaultValue = "")
    String id, Model model, ServletRequest request)
    {
        InterfacePacketsDefinition interfacePacketsDefinition = interfaceService.getInterfacePacketsDefinitionById(Long.valueOf(id));
        List<MerchantResponse> merchantResponse = merchantResponseService.getMerchantResponseListByParams(
            interfacePacketsDefinition.getMerchantId(),
            interfacePacketsDefinition.getInterfaceType());
        StringBuffer merchantResponse_html = buildMerchantResponse(merchantResponse,
            interfacePacketsDefinition.getInterfaceType());
        return merchantResponse_html.toString();
    }

    @RequestMapping(value = "/doEditInterfacePacketsDefinition")
    public String doEditInterfacePacketsDefinition(InterfacePacketsDefinitionVO interfacePacketsDefinitionVO,
                                                   @RequestParam(value = "merchantId", defaultValue = "")
                                                   String merchantId, ModelMap model)
    {
        try
        {
            Gson gson = new Gson();
            InterfacePacketTypeConf request_interfacePacketTypeConf = new InterfacePacketTypeConf();
            request_interfacePacketTypeConf.setId(interfacePacketsDefinitionVO.getRequestInterfacePacketTypeConfId());
            request_interfacePacketTypeConf.setInterfaceType(interfacePacketsDefinitionVO.getInterfaceType());
            request_interfacePacketTypeConf.setMerchantId(Long.valueOf(merchantId));
            request_interfacePacketTypeConf.setPacketType(interfacePacketsDefinitionVO.getRequestPacketType());
            request_interfacePacketTypeConf.setConnectionModule(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
            interfacePacketTypeConfService.updateInterfacePacketTypeConf(request_interfacePacketTypeConf);

            InterfacePacketTypeConf response_interfacePacketTypeConf = new InterfacePacketTypeConf();
            response_interfacePacketTypeConf.setId(interfacePacketsDefinitionVO.getResponseInterfacePacketTypeConfId());
            response_interfacePacketTypeConf.setInterfaceType(interfacePacketsDefinitionVO.getInterfaceType());
            response_interfacePacketTypeConf.setMerchantId(Long.valueOf(merchantId));
            response_interfacePacketTypeConf.setPacketType(interfacePacketsDefinitionVO.getResponsePacketType());
            response_interfacePacketTypeConf.setConnectionModule(Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
            interfacePacketTypeConfService.updateInterfacePacketTypeConf(response_interfacePacketTypeConf);

            InterfacePacketsDefinition ifpd = new InterfacePacketsDefinition();
            ifpd.setId(interfacePacketsDefinitionVO.getInterfaceDefinitionId());
            ifpd.setMerchantId(interfacePacketsDefinitionVO.getMerchantId());
            ifpd.setInterfaceType(interfacePacketsDefinitionVO.getInterfaceType());
            ifpd.setEncoding(interfacePacketsDefinitionVO.getEncoding());
            ifpd.setConnectionType(interfacePacketsDefinitionVO.getConnectionType());
            ifpd.setRequestUrl(interfacePacketsDefinitionVO.getRequestUrl());
            ifpd.setInOrOut(interfacePacketsDefinitionVO.getInOrOut());
            ifpd.setIsConf(Long.valueOf(interfacePacketsDefinitionVO.getIsConf()));
            ifpd.setMethodType(interfacePacketsDefinitionVO.getMethodType());

            String requestInterfaceParamsStr = interfacePacketsDefinitionVO.getRequestInterfaceParamsStr();
            String responseInterfaceParamsStr = interfacePacketsDefinitionVO.getResponseInterfaceParamsStr();
            String requestMerchantRequestParams = interfacePacketsDefinitionVO.getRequestMerchantRequestParams();
            String responseMerchantResponseParams = interfacePacketsDefinitionVO.getResponseMerchantResponseParams();

            String replaceStr = UUIDUtils.uuid();
            String replaceStr2 = UUIDUtils.uuid();
            requestInterfaceParamsStr = requestInterfaceParamsStr.replaceAll("=", replaceStr).replaceAll(
                "/", replaceStr2);
            List<InterfaceParam> request_params = gson.fromJson(requestInterfaceParamsStr,
                new TypeToken<List<InterfaceParam>>()
                {}.getType());
            if (CollectionUtils.isNotEmpty(request_params))
            {
                for (InterfaceParam i : request_params)
                {
                    if (null != i.getEncryptionParamNames())
                    {
                        i.setEncryptionParamNames(i.getEncryptionParamNames().replaceAll(
                            replaceStr, "=").replaceAll(replaceStr2, "/"));
                    }
                }
            }

            responseInterfaceParamsStr = responseInterfaceParamsStr.replaceAll("=", replaceStr).replaceAll(
                "/", replaceStr2);
            List<InterfaceParam> response_params = gson.fromJson(responseInterfaceParamsStr,
                new TypeToken<List<InterfaceParam>>()
                {}.getType());
            if (CollectionUtils.isNotEmpty(response_params))
            {
                for (InterfaceParam i : response_params)
                {
                    if (null != i.getEncryptionParamNames())
                    {
                        i.setEncryptionParamNames(i.getEncryptionParamNames().replaceAll(
                            replaceStr, "=").replaceAll(replaceStr2, "/"));
                    }
                }
            }

            List<MerchantRequest> merchantRequestList = gson.fromJson(
                requestMerchantRequestParams, new TypeToken<List<MerchantRequest>>()
                {}.getType());
            List<MerchantResponse> merchantResponseList = gson.fromJson(
                responseMerchantResponseParams, new TypeToken<List<MerchantResponse>>()
                {}.getType());

            ifpd.setRequestParams(request_params);
            ifpd.setResponseParams(response_params);
            interfaceService.updateInterfacePacketsDefinition(ifpd);

            merchantResponseService.saveMerchantResponseList(merchantResponseList,
                Long.valueOf(merchantId), interfacePacketsDefinitionVO.getInterfaceType());
            merchantRequestService.saveMerchantRequestList(merchantRequestList,
                Long.valueOf(merchantId), interfacePacketsDefinitionVO.getInterfaceType());

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "interface/interfaceConfList?merchantId=" + merchantId);
            model.put("next_msg", "商户接口列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/interfaceConfList")
    public String interfaceConfList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, @RequestParam(value = "merchantId", defaultValue = "-1")
    String merchantId, @RequestParam(value = "encoding", defaultValue = "")
    String encoding, @RequestParam(value = "connectionType", defaultValue = "")
    String connectionType, @RequestParam(value = "interfaceType", defaultValue = "")
    String interfaceType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (!merchantId.isEmpty())
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.InterfacePacketsDefinition.MERCHANT_ID, merchantId);
        }
        if (!encoding.isEmpty())
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.InterfacePacketsDefinition.ENCODING, encoding);
        }
        if (!connectionType.isEmpty())
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.InterfacePacketsDefinition.CONNECTION_TYPE,
                connectionType);
        }
        if (!interfaceType.isEmpty())
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.InterfacePacketsDefinition.INTERFACE_TYPE,
                interfaceType);
        }
        BSort bsort = new BSort(BSort.Direct.DESC,
            EntityConstant.InterfacePacketsDefinition.MERCHANT_ID);
        YcPage<InterfacePacketsDefinition> interfacePacketsDefinitions = interfaceService.queryInterfacePacketsDefinition(
            searchParams, pageNumber, pageSize, bsort);
        List<AbstractIdentity> merchants = identityService.getAllIdentityList(IdentityType.MERCHANT);
        model.addAttribute("merchantId", Long.valueOf(merchantId));
        model.addAttribute("interfaceType", interfaceType);
        model.addAttribute("encoding", encoding);
        model.addAttribute("connectionType", connectionType);
        model.addAttribute("merchants", merchants);
        model.addAttribute("interfacePacketsDefinitions", interfacePacketsDefinitions.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", interfacePacketsDefinitions.getCountTotal() + "");
        model.addAttribute("pagetotal", interfacePacketsDefinitions.getPageTotal() + "");
        return "interface/interfacePacketsDefinitionList";
    }

}
