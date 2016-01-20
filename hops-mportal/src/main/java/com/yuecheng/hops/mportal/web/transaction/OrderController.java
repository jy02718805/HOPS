package com.yuecheng.hops.mportal.web.transaction;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.BindOrderType;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.service.ResponseCodeTranslationService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.constant.PortalTransactionMapKey;
import com.yuecheng.hops.mportal.thread.BatchVerifyOrderListThread;
import com.yuecheng.hops.mportal.vo.order.OrderVO;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CheckNumSectionService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityTypeService;
import com.yuecheng.hops.transaction.DefaultTransactionRequestImpl;
import com.yuecheng.hops.transaction.TransactionCodeConstant;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.OrderApplyOperateHistory;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderNativeVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderStatisticsVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;
import com.yuecheng.hops.transaction.execution.order.OrderTransaction;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderApplyOperateHistoryService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.OrderPageQuery;
import com.yuecheng.hops.transaction.service.order.OrderService;


@Controller
@RequestMapping(value = "/transaction")
public class OrderController extends BaseControl
{

    private static final String PAGE_SIZE = "10";

    @Autowired
    private OrderTransaction orderTransaction;

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderPageQuery orderPageQuery;

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
    private SecurityTypeService securityTypeService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private DeliveryManagement deliveryManagement;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private OrderApplyOperateHistoryService orderApplyOperateHistoryService;

    @Autowired
    private ResponseCodeTranslationService responseCodeTranslationService;

    @Autowired
    private CheckNumSectionService checkNumSectionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
    @Autowired
    private NotifyService notifyService;

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
                order.setBeginDate(DateUtil.toDateMinute(new Date()));
                order.setEndDate(DateUtil.getDateTime());
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

            YcPage<OrderNativeVo> order_list = orderPageQuery.queryOrders(pageNumber, pageSize,
                order);
            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                null);

            model.addAttribute("pageSize", pageSize);
            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("merchants", merchants);
            model.addAttribute("supplyMerchants", supplyMerchants);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            return "transaction/orderList";
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/orderPartSuccessList")
    public String orderPartSuccessList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, OrderParameterVo order, ModelMap model, ServletRequest request)
    {
        try
        {
            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.toDateMinute(new Date()));
                order.setEndDate(DateUtil.getDateTime());
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
            order.setOrderStatus(Constant.OrderStatus.SUCCESS_PART);
            YcPage<OrderVo> order_list = orderPageQuery.queryPartSuccessOrders(order, pageNumber,
                pageSize);
            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("merchants", merchants);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            model.addAttribute("pageSize", pageSize);
            return "transaction/orderPartSuccessList";
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/orderTimeOutList")
    public String orderTimeOutList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, OrderParameterVo order, ModelMap model, ServletRequest request)
    {
        try
        {
            order.setPreSuccessStatus(String.valueOf(Constant.OrderStatus.PRE_SUCCESS_STATUS_NO_NEED));
            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.toDateMinute(new Date()));
                order.setEndDate(DateUtil.getDateTime());
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

            YcPage<OrderNativeVo> order_list = orderPageQuery.queryTimeOutOrders(order,
                pageNumber, pageSize);

            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                null);

            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("merchants", merchants);
            model.addAttribute("supplyMerchants", supplyMerchants);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            model.addAttribute("pageSize", pageSize);
            return "transaction/orderTimeOutList";
        }
        catch (RpcException e)
        {
            logger.error("[OrderController:orderTimeOutList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[OrderController:orderTimeOutList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/fakeOrderTimeOutList")
    public String fakeOrderTimeOutList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, OrderParameterVo order, ModelMap model, ServletRequest request)
    {
        try
        {
            order.setPreSuccessStatus(String.valueOf(Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT));
            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.toDateMinute(new Date()));
                order.setEndDate(DateUtil.getDateTime());
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

            YcPage<OrderNativeVo> order_list = orderPageQuery.queryTimeOutOrders(order,
                pageNumber, pageSize);

            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                null);

            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("merchants", merchants);
            model.addAttribute("supplyMerchants", supplyMerchants);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            model.addAttribute("pageSize", pageSize);
            return "transaction/fakeOrderTimeOutList";
        }
        catch (RpcException e)
        {
            logger.error("[OrderController:fakeOrderTimeOutList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[OrderController:fakeOrderTimeOutList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/orderReTry")
    public String orderReTry(OrderVO order, ModelMap model, ServletRequest request)
    {
        if (!order.getOrderNo().isEmpty())
        {
            boolean isAuto = false;
            Map<String, Object> request_fields = BeanUtils.transBean2Map(order);
            request_fields.put(TransactionMapKey.IS_AUTO, isAuto);

            TransactionRequest transactionRequest = new DefaultTransactionRequestImpl();
            transactionRequest.setTransactionCode(TransactionCodeConstant.BIND_ORDER_CODE);
            transactionRequest.setTransactionTime(new Date());
            transactionRequest.setParameter("bindOrderType", BindOrderType.RETRY);

            for (Map.Entry<String, Object> entry : request_fields.entrySet())
            {
                transactionRequest.setParameter(entry.getKey(), entry.getValue());
            }
            transactionService.doTransaction(transactionRequest);
        }
        else
        {
            model.put("message", "操作失败[" + order.getOrderNo() + "]参数异常！");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        return "redirect:/transaction/orderList";
    }

    @RequestMapping(value = "/orderReBind")
    public String orderReBind(OrderVO order, ModelMap model, ServletRequest request)
    {
        if (!order.getOrderNo().isEmpty())
        {
            boolean isAuto = false;
            Map<String, Object> request_fields = BeanUtils.transBean2Map(order);
            request_fields.put(TransactionMapKey.IS_AUTO, isAuto);

            TransactionRequest transactionRequest = new DefaultTransactionRequestImpl();
            transactionRequest.setTransactionCode(TransactionCodeConstant.BIND_ORDER_CODE);
            transactionRequest.setTransactionTime(new Date());

            for (Map.Entry<String, Object> entry : request_fields.entrySet())
            {
                transactionRequest.setParameter(entry.getKey(), entry.getValue());
            }
            transactionService.doTransaction(transactionRequest);
        }
        else
        {
            model.put("message", "操作失败[" + order.getOrderNo() + "]参数异常！");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        return "redirect:/transaction/orderList";
    }

    @RequestMapping(value = "/manualAuditOrderList")
    public String manualAuditorderList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, OrderParameterVo order, ModelMap model, ServletRequest request)
    {
        try
        {

            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.toDateMinute(new Date()));
                order.setEndDate(DateUtil.getDateTime());
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

            YcPage<OrderVo> order_list = orderPageQuery.queryManualAuditOrders(order, pageNumber,
                pageSize);

            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                null);
            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("merchants", merchants);
            model.addAttribute("supplyMerchants", supplyMerchants);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            model.addAttribute("pageSize", pageSize);
            return "transaction/manualAuditorderList";
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/fakeOrderList")
    public String fakeOrderList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, OrderVO order, ModelMap model, ServletRequest request)
    {
        try
        {
            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.toDateMinute(new Date()));
                order.setEndDate(DateUtil.getDateTime());
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

            Map<String, Object> searchParams = new HashMap<String, Object>();
            searchParams.put(EntityConstant.Order.EXT1, order.getCarrierInfo());
            searchParams.put(EntityConstant.Order.EXT2, order.getProvince());
            searchParams.put(EntityConstant.Order.ORDER_FEE, order.getParValue());
            searchParams.put(EntityConstant.Order.MERCHANT_ID, order.getDownMerchant());
            searchParams.put(EntityConstant.Order.ORDER_STATUS, order.getStatus());
            searchParams.put(EntityConstant.Order.BEGIN_DATE, order.getBeginDate());
            searchParams.put(EntityConstant.Order.END_DATE, order.getEndDate());
            searchParams.put(EntityConstant.Order.NOTIFY_STATUS, order.getNotifyStatus());
            searchParams.put(EntityConstant.Order.PRE_SUCCESS_STATUS, order.getPreSuccessStatus());
            searchParams.put(EntityConstant.Order.USER_CODE, order.getUsercode());
            searchParams.put(EntityConstant.Order.ORDER_NO, order.getOrderNo());
            searchParams.put(EntityConstant.Order.MERCHANT_ORDER_NO, order.getMerchantOrderNo());

            YcPage<OrderVo> order_list = orderPageQuery.queryFakeOrders(searchParams, true,
                pageNumber, pageSize);
            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);

            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("merchants", merchants);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            model.addAttribute("pageSize", pageSize);

            return "transaction/fakeOrderList";
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/closeOrder")
    public String closeOrder(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, @RequestParam(value = "orderNo")
    Long orderNo, ModelMap model, ServletRequest request)
    {
        try
        {
            // 调用方法执行失败审核
            Operator operator = getLoginUser();
            orderTransaction.closeOrder(operator.getDisplayName(), orderNo, null, null, null);

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "transaction/orderList");
            model.put("next_msg", "返回列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/successPartSuccessOrder")
    public String successPartSuccessOrder(@RequestParam(value = "orderNo")
    Long orderNo, Model model, ServletRequest request)
    {
        orderTransaction.successPartSuccessOrder(orderNo);
        return "redirect:/transaction/orderList";
    }

    @RequestMapping(value = "/showOrderDetail")
    public String showOrderDetail(@RequestParam(value = "orderNo")
    Long orderNo, Model model, ServletRequest request)
    {
        OrderVo order = orderManagement.getOrderVoById(orderNo);
        List<TransactionHistoryVo> transactionHistoryList = transactionHistoryService.getTransactionHistoryByTransactionNo(orderNo.toString());
        List<OrderApplyOperateHistory> oaohList = orderApplyOperateHistoryService.queryApplyOperateHistoryListByOrderNo(orderNo);

        model.addAttribute("order", order);
        model.addAttribute("transactionHistoryList", transactionHistoryList);
        model.addAttribute("oaohList", oaohList);
        return "transaction/orderDetail";
    }

    @RequestMapping(value = "/toOrderRequestHandler")
    public String toOrderRequestHandler(Model model)
    {
        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT,
            Constant.MerchantStatus.ENABLE);
        model.addAttribute("downMerchants", downMerchants);
        return "transaction/orderRequestHandler";
    }

    @RequestMapping(value = "/orderRequestHandler")
    public String orderRequestHandler(@RequestParam(value = "phoneNo")
    String phoneNo, @RequestParam(value = "notifyUrl")
    String notifyUrl, @RequestParam(value = "merchantId")
    String merchantId, @RequestParam(value = "faceValue")
    String faceValue, ModelMap model, ServletRequest request)
    {
        try
        {
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                merchantId), IdentityType.MERCHANT);
            SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.AGENTMD5KEY);
            SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialByParams(
                merchant.getId(), IdentityType.MERCHANT,
                securityTyp != null ? securityTyp.getSecurityTypeId() : null,
                Constant.SecurityCredentialStatus.ENABLE_STATUS);
            String signValue = securityCredentialManagerService.decryptKeyBySecurityId(securityCredential.getSecurityId());
            signValue = signValue.toString().toUpperCase();
            if (phoneNo.isEmpty() || faceValue.isEmpty())
            {
                model.put("message", "参数异常 电话号码:[" + phoneNo + "] 充值金额:[" + faceValue + "]元");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            else
            {
                DateFormat df = new SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
                OrderVo order = new OrderVo();
                order.setMerchantId(merchant.getId());

                Integer randomInt = Math.abs(UUID.randomUUID().hashCode());
                if (Integer.MIN_VALUE == randomInt)
                {
                    randomInt = Math.abs(UUID.randomUUID().hashCode());
                }
                order.setMerchantOrderNo(Constant.Common.FAKE_ORDER_HEAD + df.format(new Date())
                                         + randomInt);
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
                transactionRequest.setParameter(PortalTransactionMapKey.SIGN, signValue);
                TransactionResponse response = transactionService.doTransaction(transactionRequest);

                Map<String, Object> response_fields = responseCodeTranslationService.translationMapToResponse(
                    Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER, response);
                String result = (String)response_fields.get(Constant.TransactionCode.RESULT);
                String msg = (String)response_fields.get(Constant.TransactionCode.MSG);
                if (result.equalsIgnoreCase(Constant.TrueOrFalse.TRUE))
                {
                    model.put("message", "操作成功");
                    model.put("canback", false);
                    model.put("next_url", "transaction/orderList");
                    model.put("next_msg", "订单列表");
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

    @RequestMapping(value = "/checkOrder")
    public String checkOrder(@RequestParam(value = "orderNo")
    Long orderNo, @RequestParam(value = "url")
    String url, ModelMap model, ServletRequest request)
    {
        try
        {
            Order order = orderManagement.findOne(orderNo);
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                Constant.MerchantStatus.ENABLE);
            List<Delivery> deliverys = deliveryManagement.findUnfinishedDeliveryList(orderNo);
            model.addAttribute("order", order);
            model.addAttribute("merchants", merchants);
            model.addAttribute("deliverys", deliverys);
            model.addAttribute("url", url);
            return "transaction/checkOrder.ftl";
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController:checkOrder()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/getProducts")
    @ResponseBody
    public String getProducts(@RequestParam(value = "identityId")
    Long identityId, @RequestParam(value = "phoneNum")
    Long phoneNum, @RequestParam(value = "productFace")
    String productFace, ModelMap model, ServletRequest request)
    {
        try
        {
            NumSection numSection = checkNumSectionService.checkNum(phoneNum.toString());

            List<AirtimeProduct> apList = productService.getProductTree(
                numSection.getProvince().getProvinceId(), new BigDecimal(productFace).toString(),
                numSection.getCarrierInfo().getCarrierNo(), numSection.getCity().getCityId(),
                Integer.valueOf(Constant.BusinessType.BUSINESS_TYPE_HF));

            List<SupplyProductRelation> supplyProducts = new ArrayList<SupplyProductRelation>();
            if (BeanUtils.isNotNull(apList) && apList.size() > 0)
            {
                supplyProducts = supplyProductRelationService.querySupplyProductByProductId(
                    apList, identityId);
            }
            String result = "";
            for (SupplyProductRelation supplyProductRelation : supplyProducts)
            {
                result = result + supplyProductRelation.getId() + "&"
                         + supplyProductRelation.getDiscount() + "*"
                         + supplyProductRelation.getProductName() + "|";
            }
            return result;
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController:getProducts()]" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/checkSuccessOrder")
    public String checkSuccessOrder(@RequestParam(value = "orderNo")
    Long orderNo, @RequestParam(value = "orderSuccessFee")
    String orderSuccessFee, @RequestParam(value = "userCode")
    String userCode, @RequestParam(value = "productId", defaultValue = "")
    Long productId, @RequestParam(value = "phoneNo", defaultValue = "")
    String phoneNo, @RequestParam(value = "url")
    String url, ModelMap model, ServletRequest request)
    {
        try
        {
            Operator operator = getLoginUser();
            if (BeanUtils.isNotNull(productId) && StringUtils.isNotBlank(phoneNo))
            {
                // 审核成功但没有发货记录，重新选定上游
                SupplyProductRelation supplyProductRelation = supplyProductRelationService.querySupplyProductRelationById(productId);
                orderTransaction.successOrder(operator, orderNo, new BigDecimal(orderSuccessFee),
                    supplyProductRelation, phoneNo);
            }
            else
            {
                if (BeanUtils.isNotNull(productId))
                {
                    SupplyProductRelation supplyProductRelation = supplyProductRelationService.querySupplyProductRelationById(productId);
                    orderTransaction.successOrder(operator, orderNo, new BigDecimal(
                        orderSuccessFee), supplyProductRelation, userCode);
                }
                else
                {
                    // 调用方法执行成功审核
                    orderTransaction.successOrder(operator, orderNo, new BigDecimal(
                        orderSuccessFee), null, userCode);
                }
            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "transaction/" + url);
            model.put("next_msg", "返回列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/checkFailOrder")
    public String checkFailOrder(@RequestParam(value = "orderNo")
    Long orderNo, @RequestParam(value = "productId", defaultValue = "")
    Long productId, @RequestParam(value = "phoneNo", defaultValue = "")
    String phoneNo, @RequestParam(value = "url")
    String url, @RequestParam(value = "operaType", defaultValue = "")
    String operaType, ModelMap model, ServletRequest request)
    {
        try
        {
            // 调用方法执行失败审核
            SupplyProductRelation supplyProductRelation = null;
            if (BeanUtils.isNotNull(productId))
            {
                supplyProductRelation = supplyProductRelationService.querySupplyProductRelationById(productId);
            }
            Operator operator = getLoginUser();
            orderTransaction.closeOrder(operator.getDisplayName(), orderNo, supplyProductRelation,
                phoneNo, operaType);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "transaction/" + url);
            model.put("next_msg", "返回列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/checkOrderList")
    public String checkOrderList(@RequestParam(value = "orderNos")
    String orderNos, @RequestParam(value = "supplyMerchantId")
    String supplyMerchantId, ModelMap model, ServletRequest request)
    {
        try
        {
            Operator operator = getLoginUser();

            Long supplyMerchantId_Long = null;

            if (BeanUtils.isNotNull(supplyMerchantId))
            {
                supplyMerchantId = supplyMerchantId.trim();
                supplyMerchantId_Long = supplyMerchantId.length() <= 0 ? null : Long.valueOf(supplyMerchantId);
            }

            List<Long> orders = orderTransaction.batchUpdateOrderManualFlag(orderNos,
                Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY);

            threadPoolTaskExecutor.execute(new BatchVerifyOrderListThread(supplyMerchantId_Long,
                orders, operator.getDisplayName()));

            // Boolean flag = orderTransaction.batchCheckOrders(orderNos, supplyMerchantId_Long,
            // operator.getDisplayName());
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "transaction/manualAuditOrderList");
            model.put("next_msg", "返回列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            logger.error("批量重绑曹组欧发生异常：" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
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

    @RequestMapping(value = "/turnOrder")
    @ResponseBody
    public Integer turnOrder(@RequestParam(value = "orderNo")
    Long orderNo, @RequestParam(value = "preSuccessStatus")
    Integer preSuccessStatus, @RequestParam(value = "orderReason")
    String orderReason, ModelMap model, ServletRequest request)
    {
        int result = 0;
        try
        {
            result = orderManagement.updateOrderReason(preSuccessStatus, orderReason, orderNo);

        }
        catch (RpcException e)
        {
            logger.debug("[OrderController: turnOrder()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return result;
    }

    @RequestMapping(value = "/reissueOrderList")
    public String reissueOrderList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, OrderParameterVo order, ModelMap model, ServletRequest request)
    {
        try
        {
            if (BeanUtils.isNull(order.getBeginDate()) && BeanUtils.isNull(order.getEndDate()))
            {
                order.setBeginDate(DateUtil.toDateMinute(new Date()));
                order.setEndDate(DateUtil.getDateTime());
            }

            if (BeanUtils.isNull(order.getOrderStatus()))
            {
                order.setOrderStatus(Constant.OrderStatus.RECHARGING);
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

            YcPage<OrderNativeVo> order_list = orderPageQuery.queryOrders(pageNumber, pageSize,
                order);
            List<Province> provinces = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
            List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                null);

            model.addAttribute("pageSize", pageSize);
            model.addAttribute("order", order);
            model.addAttribute("provinces", provinces);
            model.addAttribute("carrierInfos", carrierInfos);
            model.addAttribute("merchants", merchants);
            model.addAttribute("supplyMerchants", supplyMerchants);
            model.addAttribute("mlist", order_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", order_list.getCountTotal() + "");
            model.addAttribute("pagetotal", order_list.getPageTotal() + "");
            return "transaction/reissueOrderList";
        }
        catch (RpcException e)
        {
            logger.error("[OrderController: reissueOrderList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[OrderController: reissueOrderList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/reOpenOrder")
    public String reOpenOrder(@RequestParam(value = "orderNo")
    Long orderNo, @RequestParam(value = "productId", defaultValue = "")
    Long productId, @RequestParam(value = "phoneNo", defaultValue = "")
    Long phoneNo, ModelMap model, ServletRequest request)
    {
        try
        {
            Operator operator = getLoginUser();
            SupplyProductRelation supplyProductRelation = null;
            if(phoneNo.toString().length() != 11){
                model.put("message", "操作失败，["+phoneNo+"]手机号码验证失败");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            if (BeanUtils.isNotNull(productId))
            {
                supplyProductRelation = supplyProductRelationService.querySupplyProductRelationById(productId);
            }

            orderTransaction.reOpenOrder(orderNo, phoneNo, supplyProductRelation,
                operator.getDisplayName(), Constant.AuditType.RE_OPEN_ORDER);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "transaction/orderList");
            model.put("next_msg", "订单列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            logger.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
    
    
    @RequestMapping(value = "/reNotity")
    @ResponseBody
    public Integer reNotity(@RequestParam(value = "orderNo") Long orderNo,
            ModelMap model, ServletRequest request) {
        int result = 0;
        try {
            result = notifyService.reNotify(Constant.NotifyStatus.WAIT_NOTIFY,
                    orderNo);
        } catch (RpcException e) {
            logger.debug("[OrderController: reNotity()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return result;
    }
}
