
package com.yuecheng.hops.mportal.web.report;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.yuecheng.hops.transaction.service.order.OrderApplyOperateHistoryService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.OrderPageQuery;
import com.yuecheng.hops.transaction.service.order.OrderService;


@Controller
@RequestMapping(value = "/orderReport")
public class OrderReportController extends BaseControl
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

    private static Logger logger = LoggerFactory.getLogger(OrderReportController.class);

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
            return "report/orderReportList";
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

}
