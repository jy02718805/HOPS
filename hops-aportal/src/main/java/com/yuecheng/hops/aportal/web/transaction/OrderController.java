package com.yuecheng.hops.aportal.web.transaction;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryVo;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.aportal.constant.PageConstant;
import com.yuecheng.hops.aportal.constant.PortalTransactionMapKey;
import com.yuecheng.hops.aportal.vo.order.OrderVO;
import com.yuecheng.hops.aportal.web.BaseControl;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.service.ResponseCodeTranslationService;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityTypeService;
import com.yuecheng.hops.transaction.DefaultTransactionRequestImpl;
import com.yuecheng.hops.transaction.TransactionCodeConstant;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderNativeVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderStatisticsVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.OrderPageQuery;
import com.yuecheng.hops.transaction.service.order.OrderService;


@Controller
@RequestMapping(value = "/transaction")
public class OrderController extends BaseControl
{

    private static final String PAGE_SIZE = "10";

    @Autowired
    private OrderPageQuery orderPageQuery;

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CCYAccountService ccyAccountService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    SimpleDateFormat orderFormatter = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    public HttpSession session;

    @Autowired
    IdentityService identityService;

    @Autowired
    private ResponseCodeTranslationService responseCodeTranslationService;

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/orderList")
    public String orderList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, OrderParameterVo order, ModelMap model, ServletRequest request)
    {
        try
        {
            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.formatDateTime(DateUtil.getDateStart(new Date())));
                order.setEndDate(DateUtil.formatDateTime(DateUtil.getDateEnd(new Date())));
            }
            String userCode = order.getUsercode();
            String merchantOrderNo = order.getMerchantOrderNo();
            userCode = new String(
                (StringUtils.isNotBlank(userCode) ? userCode : "").getBytes("ISO-8859-1"), "UTF-8");
            merchantOrderNo = new String(
                (StringUtils.isNotBlank(merchantOrderNo) ? merchantOrderNo : "").getBytes("ISO-8859-1"),
                "UTF-8");
            order.setUsercode(userCode);
            order.setMerchantOrderNo(merchantOrderNo);
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (loginPerson != null)
            {
                order.setMerchantId(loginPerson.getOwnerIdentityId().toString());
                order.setPreSuccessStatus(null);
            }
            YcPage<OrderNativeVo> order_list = orderPageQuery.queryOrders(pageNumber, pageSize,
                order);
            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            // List<Merchant> merchants = merchantService.getAllMerchant(MerchantType.AGENT);

            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            return "transaction/orderList";
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/orderPartSuccessList")
    public String orderPartSuccessList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, OrderVO order, Model model, ServletRequest request)
    {
        // Map<String, Object> searchParams = Servlets.getParametersStartingWith(request,
        // "search_");
        // YcPage<OrderVo> order_list = orderPageQuery.queryPartSuccessOrders(searchParams,
        // pageNumber, pageSize, order.getCarrierInfo(), order.getProvince(),
        // order.getParValue(), order.getDownMerchant(), EntityConstant.Order.ORDER_NO,
        // Constant.OrderStatus.SUCCESS_PART, order.getBeginDate(), order.getEndDate(),
        // order.getNotifyStatus(), order.getPreSuccessStatus(), order.getUsercode(),
        // order.getOrderNo(), order.getMerchantOrderNo());
        // List<Province> provinces = provinceService.getAllProvince();
        // List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        // List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
        // model.addAttribute("order", order);
        // model.addAttribute("provinces", provinces);
        // model.addAttribute("carrierInfos", carrierInfos);
        // model.addAttribute("merchants", merchants);
        // model.addAttribute("mlist", order_list.getList());
        // model.addAttribute("page", pageNumber);
        // model.addAttribute("counttotal", order_list.getCountTotal() + "");
        // model.addAttribute("pagetotal", order_list.getPageTotal() + "");

        return "transaction/orderPartSuccessList";
    }

    @RequestMapping(value = "/preSuccessOrderList")
    public String preSuccessOrderList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, OrderParameterVo order, ModelMap model, ServletRequest request)
    {
        try
        {
            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.formatDateTime(DateUtil.getDateStart(new Date())));
                order.setEndDate(DateUtil.formatDateTime(DateUtil.getDateEnd(new Date())));
            }
            if (StringUtil.isNotBlank(order.getPreSuccessStatus())
                && order.getPreSuccessStatus().equals(
                    Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE + ""))
            {
                order.setOrderStatus(null);
            }
            String userCode = order.getUsercode();
            String merchantOrderNo = order.getMerchantOrderNo();
            userCode = new String(
                (StringUtils.isNotBlank(userCode) ? userCode : "").getBytes("ISO-8859-1"), "UTF-8");
            merchantOrderNo = new String(
                (StringUtils.isNotBlank(merchantOrderNo) ? merchantOrderNo : "").getBytes("ISO-8859-1"),
                "UTF-8");
            order.setUsercode(userCode);
            order.setMerchantOrderNo(merchantOrderNo);
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (loginPerson != null)
            {
                order.setMerchantId(loginPerson.getOwnerIdentityId().toString());
            }
            YcPage<OrderNativeVo> order_list = orderPageQuery.queryOrders(pageNumber, pageSize,
                order);
            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            // List<Merchant> merchants = merchantService.getAllMerchant(MerchantType.AGENT);

            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            return "transaction/preSuccessOrderList";
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/showPreSuccessOrderDetail")
    public String showPreSuccessOrderDetail(@RequestParam(value = "orderNo")
    Long orderNo, Model model, ServletRequest request)
    {
        OrderVo order = orderManagement.getOrderVoById(orderNo);
        // getTransactionHistory
        List<TransactionHistoryVo> transactionHistoryList = transactionHistoryService.getTransactionHistoryByTransactionNo(orderNo.toString());
        model.addAttribute("order", order);
        model.addAttribute("transactionHistoryList", transactionHistoryList);
        return "transaction/preSuccessOrderDetail";
    }

    @RequestMapping(value = "/showOrderDetail")
    public String showOrderDetail(@RequestParam(value = "orderNo")
    Long orderNo, Model model, ServletRequest request)
    {
        OrderVo order = orderManagement.getOrderVoById(orderNo);
        // getTransactionHistory
        List<TransactionHistoryVo> transactionHistoryList = transactionHistoryService.getTransactionHistoryByTransactionNo(orderNo.toString());
        model.addAttribute("order", order);
        model.addAttribute("transactionHistoryList", transactionHistoryList);
        return "transaction/orderDetail";
    }

    @RequestMapping(value = "/toMOrderRequestHandler")
    public String toOrderRequestHandler(Model model)
    {
        return "transaction/morderRequestHandler";
    }

    @RequestMapping(value = "/morderRequestHandler")
    public String orderRequestHandler(@RequestParam(value = "phoneNo")
    String phoneNo, @RequestParam(value = "faceValue")
    String faceValue, @RequestParam(value = "notifyUrl")
    String notifyUrl, ModelMap model, ServletRequest request)
    {
        try
        {
            if (phoneNo.isEmpty() || faceValue.isEmpty() || notifyUrl.isEmpty())
            {
                model.put("message", "参数异常 电话号码:[" + phoneNo + "] 充值金额:[" + faceValue
                                     + "]元 通知地址:[" + notifyUrl + "]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            else
            {
                com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
                if (loginPerson != null)
                {
                    Long merchantId = loginPerson.getOwnerIdentityId();
                    SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.AGENTMD5KEY);
                    SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialByParams(
                        merchantId, IdentityType.MERCHANT,
                        securityTyp != null ? securityTyp.getSecurityTypeId() : null,
                        Constant.SecurityCredentialStatus.ENABLE_STATUS);
                    String keyValue = securityCredentialManagerService.decryptKeyBySecurityId(securityCredential.getSecurityId());
                    keyValue = keyValue.toString().toUpperCase();

                    Random r = new Random();
                    DateFormat df = new SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
                    OrderVo order = new OrderVo();
                    order.setMerchantId(merchantId);
                    order.setMerchantOrderNo(Constant.Common.FAKE_ORDER_HEAD
                                             + df.format(new Date()) + r.nextInt());
                    order.setProductFace(new BigDecimal(faceValue));
                    order.setOrderFee(new BigDecimal(faceValue));
                    order.setNotifyUrl(notifyUrl);
                    order.setUserCode(phoneNo);
                    order.setOrderRequestTime(new Date());
                    TransactionRequest transactionRequest = new DefaultTransactionRequestImpl();
                    transactionRequest.setTransactionCode(TransactionCodeConstant.AGENT_ORDER_RECEPTOR_CODE);
                    transactionRequest.setTransactionTime(new Date());
                    Map<String, Object> request_fields = (Map<String, Object>)BeanUtils.transBean2Map(order);
                    for (Map.Entry<String, Object> entry : request_fields.entrySet())
                    {
                        transactionRequest.setParameter(entry.getKey(), entry.getValue());
                    }
                    transactionRequest.setParameter(EntityConstant.Order.PORDUCT_NUM, 1);
                    transactionRequest.setParameter(PortalTransactionMapKey.INTERFACE_TYPE,
                        Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER);
                    transactionRequest.setParameter(PortalTransactionMapKey.SIGN, keyValue);
                    TransactionResponse response = transactionService.doTransaction(transactionRequest);

                    Map<String, Object> response_fields = responseCodeTranslationService.translationMapToResponse(
                        Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER, response);
                    String result = (String)response_fields.get(Constant.TransactionCode.RESULT);
                    String msg = (String)response_fields.get(Constant.TransactionCode.MSG);
                    if (result.equals(Constant.TrueOrFalse.TRUE))
                    {
                        model.put("message", "操作成功[" + msg + "]");
                        model.put("canback", false);
                        model.put("next_url", "transaction/toMOrderRequestHandler");
                        model.put("next_msg", "手工充值");
                        return PageConstant.PAGE_COMMON_NOTIFY;
                    }
                    else
                    {
                        model.put("message", "操作失败[" + msg + "]");
                        model.put("canback", true);
                        return PageConstant.PAGE_COMMON_NOTIFY;
                    }
                }
            }
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    public String getSign(OrderVo ro, String key)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
        StringBuffer sb = new StringBuffer();
        sb.append(ro.getMerchantOrderNo());
        sb.append(ro.getMerchantId());
        sb.append(ro.getProductFace());
        sb.append(ro.getNumber());
        sb.append(ro.getProductFace());
        sb.append(ro.getUserCode());
        sb.append(ro.getNotifyUrl());
        sb.append(dateFormat.format(ro.getOrderRequestTime()));
        sb.append(key);
        return sb.toString();
    }

    @RequestMapping(value = "/statisticsOrderInfo")
    @ResponseBody
    public OrderStatisticsVo statisticsOrderInfo(OrderParameterVo orderParameterVo,
                                                 @RequestParam(value = "type", defaultValue = "")
                                                 String type, ModelMap model,
                                                 ServletRequest request)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (loginPerson != null)
            {
                orderParameterVo.setMerchantId(loginPerson.getOwnerIdentityId().toString());
                orderParameterVo.setPreSuccessStatus(null);
            }

            OrderStatisticsVo orderStatisticsVo = orderPageQuery.statisticsOrderInfo(
                orderParameterVo, type);
            return orderStatisticsVo;
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController: statisticsOrderInfo()]" + e.getMessage());
            return null;
        }
    }
}
