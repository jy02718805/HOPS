package com.yuecheng.hops.mportal.web.product;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.alibaba.dubbo.rpc.RpcException;
import com.google.gson.Gson;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.entity.history.ProductDiscountHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationHistoryBak;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationHistoryAssist;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationRuleAssist;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductOperationBakService;
import com.yuecheng.hops.product.service.ProductOperationRuleBakService;
import com.yuecheng.hops.product.service.ProductOperationRuleService;
import com.yuecheng.hops.product.service.ProductOperationService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;


@Controller
@RequestMapping(value = "/productOperation")
public class ProductOperationController  extends BaseControl
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private ProductOperationService productOperationService;

    @Autowired
    private ProductOperationBakService productOperationBakService;
    
    @Autowired
    private ProductOperationRuleService productOperationRuleService;
    
    @Autowired
    private ProductOperationRuleBakService productOperationRuleBakService;
    
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private CityService cityService;

    @Autowired
    private MerchantService merchantService;
    
    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private AgentProductRelationService agentProductRelationService;
    
    private static Logger logger = LoggerFactory.getLogger(ProductOperationController.class);

    @RequestMapping(value = "/productOperationList")
    public String productTypeList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                                  @RequestParam(value = "sortType", defaultValue = "createDate") String sortType,
                                  Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        YcPage<ProductOperationHistory> page_list = productOperationService.queryProductOperationHistory(
            searchParams, pageNumber, pageSize, sortType);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "product/productOperationList";
    }

    @RequestMapping(value = "/productOperationBakList")
    public String productOperationBakList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                                  @RequestParam(value = "sortType", defaultValue = "createDate") String sortType,
                                  Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        YcPage<ProductOperationHistoryBak> page_list = productOperationBakService.queryProductOperationHistory(
            searchParams, pageNumber, pageSize, sortType);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "product/productOperationBakList";
    }
    
    @RequestMapping(value = "/toSaveProductOperationHistory")
    public String toSaveProductOperationHistory(Model model)
    {
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        List<Province> provinces = provinceService.getAllProvince();
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("provinces", provinces);
        return "product/toSaveProductOperationHistory";
    }
    
    @RequestMapping(value = "/toShowProductOperationHistory")
    public String toShowProductOperationHistory(@RequestParam("historyId") Long historyId,ModelMap model)
    {
        try{
            if(BeanUtils.isNotNull(historyId))
            {
                ProductOperationHistory productOperationHistory=productOperationService.queryProductOperationHistoryById(historyId);
                if(BeanUtils.isNotNull(productOperationHistory))
                {
                    List<ProductOperationRuleAssist> productOperationRuleAssists=productOperationRuleService.queryProductOperationRuleAssistByHisId(historyId);
                    CarrierInfo carrierInfo = new CarrierInfo();
                    if(StringUtils.isNotBlank(productOperationHistory.getCarrierName()))
                    {
                        carrierInfo = carrierInfoService.findOne(productOperationHistory.getCarrierName());
                    }
                    Province province = new Province();
                    if(StringUtils.isNotBlank(productOperationHistory.getProvince()))
                    {
                        province = provinceService.findOne(productOperationHistory.getProvince());
                    }
                    City city = new City();
                    if(StringUtils.isNotBlank(productOperationHistory.getCity()))
                    {
                        city = cityService.findOne(productOperationHistory.getCity());
                    }
                    model.addAttribute("carrierInfo", carrierInfo);
                    model.addAttribute("province", province);
                    model.addAttribute("city", city);
                    model.addAttribute("poHistory", productOperationHistory);
                    model.addAttribute("poRuleAssists", productOperationRuleAssists);
                    return "product/toShowProductOperationHistory";
                }else{
                    logger.error("[ProductOperationController:toShowProductOperationHistory(未找到ID为："+historyId+"的任务)]");
                    model.put("message", "操作失败[未找到ID为："+historyId+"的任务]");
                    model.put("canback", true);
                }
            }else
            {
                logger.error("[ProductOperationController:toShowProductOperationHistory(historyId为空)]");
                model.put("message", "操作失败[historyId为空]");
                model.put("canback", true);
            }
        }catch (RpcException e)
        {
            logger.error("[ProductOperationController:toShowProductOperationHistory()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
    @RequestMapping(value = "/toShowProductOperationHistoryBak")
    public String toShowProductOperationHistoryBak(@RequestParam("historyId") Long historyId,ModelMap model)
    {
        try{
            if(BeanUtils.isNotNull(historyId))
            {
                ProductOperationHistoryBak productOperationHistoryBak=productOperationBakService.queryProductOperationHistoryById(historyId);
                if(BeanUtils.isNotNull(productOperationHistoryBak))
                {
                    List<ProductOperationRuleAssist> productOperationRuleAssists=productOperationRuleBakService.queryProductOperationRuleAssistByHisId(historyId);
                    CarrierInfo carrierInfo = new CarrierInfo();
                    if(StringUtils.isNotBlank(productOperationHistoryBak.getCarrierName()))
                    {
                        carrierInfo = carrierInfoService.findOne(productOperationHistoryBak.getCarrierName());
                    }
                    Province province = new Province();
                    if(StringUtils.isNotBlank(productOperationHistoryBak.getProvince()))
                    {
                        province = provinceService.findOne(productOperationHistoryBak.getProvince());
                    }
                    City city = new City();
                    if(StringUtils.isNotBlank(productOperationHistoryBak.getCity()))
                    {
                        city = cityService.findOne(productOperationHistoryBak.getCity());
                    }
                    model.addAttribute("carrierInfo", carrierInfo);
                    model.addAttribute("province", province);
                    model.addAttribute("city", city);
                    model.addAttribute("poHistory", productOperationHistoryBak);
                    model.addAttribute("poRuleAssists", productOperationRuleAssists);
                    return "product/toShowProductOperationHistoryBak";
                }else{
                    logger.error("[ProductOperationController:toShowProductOperationHistory(未找到ID为："+historyId+"的任务)]");
                    model.put("message", "操作失败[未找到ID为："+historyId+"的任务]");
                    model.put("canback", true);
                }
            }else
            {
                logger.error("[ProductOperationController:toShowProductOperationHistory(historyId为空)]");
                model.put("message", "操作失败[historyId为空]");
                model.put("canback", true);
            }
        }catch (RpcException e)
        {
            logger.error("[ProductOperationController:toShowProductOperationHistory()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
    @RequestMapping(value = "/getMerchantByMType")
    @ResponseBody
    public String getMerchantByMType(@RequestParam("merchantType") String merchantType,Model model)
    {
    	try{
    		List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.valueOf(merchantType.toUpperCase(Locale.ENGLISH)), Constant.MerchantStatus.ENABLE);
	        String result = "";
	        for (Merchant merchant : merchants) {
	        	result = result + merchant.getId() + "*" + merchant.getMerchantName() + "|";
			}
	        return result;
    	}
        catch (RpcException e)
        {
            logger.debug("[ProductOperationController:getMerchantByMType()]" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/doSaveProductOperationHistory")
    public String doSaveProductOperationHistory(ProductOperationHistory productOperationHistory,@RequestParam("merchantIds") String merchantIds,
                                                ModelMap model)
    {
        try
        {
        	Operator loginPerson = getLoginUser();
            productOperationHistory.setCreateDate(new Date());
            String operationName = StringUtil.initString();
            if (productOperationHistory.getCarrierName() != null
                && !productOperationHistory.getCarrierName().isEmpty())
            {
                operationName = operationName + "[" + productOperationHistory.getCarrierName()
                                + "]";
            }
            else
            {
                productOperationHistory.setCarrierName("");
            }
            if (productOperationHistory.getProvince() != null
                && !productOperationHistory.getProvince().isEmpty())
            {
                operationName = operationName + "[" + productOperationHistory.getProvince() + "]";
            }
            else
            {
                productOperationHistory.setProvince("");
            }
            if (productOperationHistory.getCity() != null
                && !productOperationHistory.getCity().isEmpty())
            {
                operationName = operationName + "[" + productOperationHistory.getCity() + "]";
            }
            else
            {
                productOperationHistory.setCity("");
            }
            if (productOperationHistory.getParValue() != null
                && productOperationHistory.getParValue().intValue() > 0)
            {
                operationName = operationName + "[" + productOperationHistory.getParValue() + "]";
            }
            else
            {
                productOperationHistory.setParValue(new BigDecimal(0));
            }
            if (productOperationHistory.getMerchantType() != null
                && !productOperationHistory.getMerchantType().isEmpty())
            {
                if (productOperationHistory.getMerchantType().equalsIgnoreCase(
                    Constant.Common.MERCHANTTYPE_AGENT))
                {
                    operationName = operationName + "[代理商]";
                }
                else if (productOperationHistory.getMerchantType().equalsIgnoreCase(
                    Constant.Common.MERCHANTTYPE_SUPPLY))
                {
                    operationName = operationName + "[供货商]";
                }
                else if (productOperationHistory.getMerchantType().equalsIgnoreCase(
                    Constant.Common.MERCHANTTYPE_ALL))
                {
                    operationName = operationName + "[全部商户]";
                }
            }
            if (productOperationHistory.getOperationFlag() != null
                && !productOperationHistory.getOperationFlag().isEmpty())
            {
                if (productOperationHistory.getOperationFlag().equalsIgnoreCase(
                    Constant.ProductOperationHistory.CLOSE_STATUS))
                {
                    operationName = operationName + "[关闭]";
                }
                else if (productOperationHistory.getOperationFlag().equalsIgnoreCase(
                    Constant.ProductOperationHistory.OPEN_STATUS))
                {
                    operationName = operationName + "[打开]";
                }
            }
            productOperationHistory.setOperationName(operationName);
            productOperationHistory.setStatus(Constant.ProductOperationHistory.STATUS_INIT);
            productOperationService.saveProductOperationHistory(productOperationHistory,merchantIds,loginPerson.getOperatorName());
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "productOperation/productOperationList");
            model.put("next_msg", "产品批量操作列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/doProductOperationHistory")
    public String doProductOperationHistory(@RequestParam(value = "id") String id, ModelMap model)
    {
        try
        {
            ProductOperationHistory productOperationHistory = productOperationService.queryProductOperationHistoryById(Long.valueOf(id));
            ProductOperationHistoryAssist productOperationHistoryAssist = productOperationService.doProductOperationHistory(productOperationHistory);
            model.put("message", "操作成功");
            String noUpdatePName=productOperationHistoryAssist.getNoUpdateProductName();
            if(StringUtils.isNotBlank(noUpdatePName))
            {
                model.put("message", "操作完成,部分Hops产品状态未开启不能执行此操作。【"+noUpdatePName+"】");
            }
            model.put("canback", false);
            model.put("next_url", "productOperation/productOperationList");
            model.put("next_msg", "产品批量操作列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/refProductOperationHistory")
    public String refProductOperationHistory(@RequestParam(value = "id") String id, ModelMap model)
    {
        try
        {
            ProductOperationHistory productOperationHistory = productOperationService.queryProductOperationHistoryById(Long.valueOf(id));
            productOperationService.refProductOperationHistory(productOperationHistory);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "productOperation/productOperationList");
            model.put("next_msg", "产品批量操作列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/deleteProductOperationHistory")
    public String deleteProductOperationHistory(@RequestParam(value = "id") String id,
                                                ModelMap model)
    {
        try
        {
            productOperationService.deleteProductOperationHistory(Long.valueOf(id));
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "productOperation/productOperationList");
            model.put("next_msg", "产品批量操作列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
    
    @RequestMapping(value = "/getMerchants")
    @ResponseBody
    public String getMerchants(@RequestParam(value = "productSide", defaultValue = "")String productSide,ModelMap model)
    {
        try
        {
            Gson gson = new Gson();
            String allMerchantStr = StringUtil.initString();
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_AGENT))
            {
                List<Merchant> allMerchant = merchantService.queryAllMerchant(MerchantType.AGENT, null);
                allMerchantStr = gson.toJson(allMerchant);
            }
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_SUPPLY))
            {
                List<Merchant> allMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
                allMerchantStr = gson.toJson(allMerchant);
            }
            return allMerchantStr;
        }
        catch (Exception e)
        {
            return StringUtil.initString();
        }
    }
    
    @RequestMapping(value = "/getProducts")
    @ResponseBody
    public String getProducts(@RequestParam(value = "productSide", defaultValue = "")String productSide,
                               @RequestParam(value = "identityId", defaultValue = "")String identityId,
                               ModelMap model)
    {
        try
        {
            Gson gson = new Gson();
            String allProductStr = StringUtil.initString();
            
            Map<String, Object> searchParams = new HashMap<String, Object>();
            
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_AGENT))
            {
                List<AgentProductRelation> agentProductRelations = agentProductRelationService.getAllAgentProductRelationService(
                    searchParams,Long.valueOf(identityId), IdentityType.MERCHANT.toString());
                allProductStr = gson.toJson(agentProductRelations);
            }
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_SUPPLY))
            {
                List<SupplyProductRelation> supplyProductRelations = supplyProductRelationService.getAllProductBySupplyMerchantId(
                    searchParams, Long.valueOf(identityId), IdentityType.MERCHANT.toString(), null);
                allProductStr = gson.toJson(supplyProductRelations);
            }
            return allProductStr;
        }
        catch (Exception e)
        {
            return StringUtil.initString();
        }
    }
    
    @RequestMapping(value = "/queryProductDiscountHistory")
    public String queryProductDiscountHistory(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                                  @RequestParam(value = "sortType", defaultValue = "createDate") String sortType,
                                  @RequestParam(value = "productId", defaultValue = "") String productId,
                                  OrderParameterVo order,
                                  ProductDiscountHistory productDiscountHistory,
                                  Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        
        if (!StringUtil.isNullOrEmpty(productDiscountHistory.getCarrierName()))
        {
            searchParams.put(org.springside.modules.persistence.SearchFilter.Operator.EQ + "_"
                + "carrierName", productDiscountHistory.getCarrierName());
        }
        if (!StringUtil.isNullOrEmpty(productDiscountHistory.getProvince()))
        {
            searchParams.put(org.springside.modules.persistence.SearchFilter.Operator.EQ + "_"
                + "province", productDiscountHistory.getProvince());
        }
        if (!StringUtil.isNullOrEmpty(productDiscountHistory.getCity()))
        {
            searchParams.put(org.springside.modules.persistence.SearchFilter.Operator.EQ + "_"
                + "city", productDiscountHistory.getCity());
        }
        if (!BeanUtils.isNull(productDiscountHistory.getParValue()))
        {
            productDiscountHistory.setParValue(productDiscountHistory.getParValue().replace(",", ""));
            if(isNumber(productDiscountHistory.getParValue())){
            searchParams.put(org.springside.modules.persistence.SearchFilter.Operator.EQ + "_"
                + "parValue", productDiscountHistory.getParValue());
            }
        }
        if (!BeanUtils.isNull(productDiscountHistory.getIdentityId()) && productDiscountHistory.getIdentityId().length() > 0)
        {
            searchParams.put(org.springside.modules.persistence.SearchFilter.Operator.EQ + "_"
                + "identityId", productDiscountHistory.getIdentityId());
        }
        if (!StringUtil.isNullOrEmpty(productDiscountHistory.getAction()))
        {
            searchParams.put(org.springside.modules.persistence.SearchFilter.Operator.EQ + "_"
                + "action", productDiscountHistory.getAction());
        }
        if (!BeanUtils.isNull(productDiscountHistory.getBusinessType()))
        {
            searchParams.put(org.springside.modules.persistence.SearchFilter.Operator.EQ + "_"
                + "businessType", productDiscountHistory.getBusinessType());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = null;
        try
        {
            if (!BeanUtils.isNull(order.getBeginDate()))
            {
                beginDate = format.parse(order.getBeginDate());
                model.addAttribute("beginDate", order.getBeginDate());
            }else{
                beginDate = DateUtil.subTime(Constant.DateUnit.TIME_UNIT_MINUTE, 10);
                model.addAttribute("beginDate", DateUtil.toDateMinute(new Date()));
            }
        }
        catch (Exception e)
        {
            
        }
        
        
        YcPage<ProductDiscountHistory> page_list = productOperationService.queryProductDiscountHistory(
            searchParams, pageNumber, pageSize, sortType, beginDate);
        
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        String productSide = productDiscountHistory.getProductSide();
        if(BeanUtils.isNotNull(productSide)){
            
            List<Merchant> allMerchant = new ArrayList<Merchant>();
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_AGENT))
            {
                allMerchant = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            }
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_SUPPLY))
            {
                allMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
            }
            model.addAttribute("merchants", allMerchant);
        }
        
        if(BeanUtils.isNotNull(productDiscountHistory.getIdentityId())){
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_AGENT))
            {
                List<AgentProductRelation> agentProductRelations = agentProductRelationService.getAllAgentProductRelationService(
                    new HashMap<String, Object>(),Long.valueOf(productDiscountHistory.getIdentityId()), IdentityType.MERCHANT.toString());
                model.addAttribute("products", agentProductRelations);
            }
            if(!BeanUtils.isNull(productSide) && productSide.equalsIgnoreCase(Constant.Product.PRODUCT_TYPE_SUPPLY))
            {
                List<SupplyProductRelation> supplyProductRelations = supplyProductRelationService.getAllProductBySupplyMerchantId(
                    new HashMap<String, Object>(), Long.valueOf(productDiscountHistory.getIdentityId()), IdentityType.MERCHANT.toString(), null);
                model.addAttribute("products", supplyProductRelations);
            }
        }
        
        if(BeanUtils.isNotNull(productDiscountHistory.getProvince())){
            List<City> citys = cityService.getCityByProvince(productDiscountHistory.getProvince());
            model.addAttribute("citys", citys);
        }
        
        model.addAttribute("productId", productId);
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        model.addAttribute("productDiscountHistoryVO", productDiscountHistory);
        return "product/productDiscountHistory";
    }
    
    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
     try {
      Integer.parseInt(value);
      return true;
     } catch (NumberFormatException e) {
      return false;
     }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
     try {
      Double.parseDouble(value);
      if (value.contains("."))
       return true;
      return false;
     } catch (NumberFormatException e) {
      return false;
     }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
     return isInteger(value) || isDouble(value);
    }
}
