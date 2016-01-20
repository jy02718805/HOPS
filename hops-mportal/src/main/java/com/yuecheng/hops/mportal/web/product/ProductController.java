package com.yuecheng.hops.mportal.web.product;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.web.Servlets;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.TrueOrFalse;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.vo.product.AgentProductRelationVo;
import com.yuecheng.hops.mportal.vo.product.ProductRelationVO;
import com.yuecheng.hops.mportal.vo.product.ProductTypeVO;
import com.yuecheng.hops.mportal.vo.product.ProductVO;
import com.yuecheng.hops.mportal.vo.product.SupplyProductRelationVo;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.property.ProductProperty;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.entity.type.ProductType;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductManagement;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.product.service.ProductPropertyService;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.ProductTypeService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.config.entify.product.MerchantProductLevel;
import com.yuecheng.hops.transaction.config.product.MerchantProductLevelService;


@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseControl
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private ProductManagement productManagement;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPageQuery productPageQuery;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductPropertyService productPropertyService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    private MerchantProductLevelService merchantLevelService;

    @Autowired
    IdentityService identityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(value = "/toSaveProductType")
    public String toSaveProductType(Model model)
    {
        List<ProductProperty> productPropertys = productPropertyService.getAllProductProperty();
        String options = "";
        for (Iterator<ProductProperty> iterator = productPropertys.iterator(); iterator.hasNext();)
        {
            ProductProperty productProperty = iterator.next();
            options = options + "<option  value=\\\'" + productProperty.getProductPropertyId()
                      + "\\\'>" + productProperty.getParamName() + "</option>";
        }
        model.addAttribute("productPropertys", productPropertys);
        model.addAttribute("options", options);
        return "product/productTypeRegister";
    }

    @RequestMapping(value = "/saveProductType")
    public String saveProductType(ProductTypeVO productTypeVO, ModelMap model)
    {
        try
        {
            ProductType pt = new ProductType();
            String[] beans_array = productTypeVO.getBeans().split(Constant.StringSplitUtil.DECODE);

            List<ProductProperty> productPropertys = new ArrayList<ProductProperty>();
            for (int i = 0; i < beans_array.length; i++ )
            {
                String bean_str = beans_array[i];
                if (!bean_str.isEmpty())
                {
                    ProductProperty productProperty = new ProductProperty();
                    productProperty.setProductPropertyId(Long.valueOf(bean_str));
                    productPropertys.add(productProperty);
                }
            }
            pt.setProductTypeName(productTypeVO.getProductTypeName());
            pt.setProductTypeStatus("1");
            pt.setPropertyTypes(productPropertys);
            pt = productTypeService.saveProductType(pt);

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productTypeList");
            model.put("next_msg", "产品类型列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        // return "redirect:/product/productTypeList";
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/toEditProductType")
    public String toEditProductType(ProductType pt, Model model)
    {
        pt = productTypeService.queryProductTypeById(pt);

        List<ProductProperty> init_productProperty_list = productPropertyService.getAllProductProperty();

        List<ProductProperty> result = new ArrayList<ProductProperty>();

        List<ProductProperty> productProperty_checked = pt.getPropertyTypes();
        for (ProductProperty productProperty : init_productProperty_list)
        {
            if (productProperty_checked.contains(productProperty))
            {
                productProperty.setFlag(true);
                result.add(productProperty);
            }
            else
            {
                productProperty.setFlag(false);
                result.add(productProperty);
            }
        }
        model.addAttribute("typeId", pt.getTypeId());
        model.addAttribute("name", pt.getProductTypeName());
        model.addAttribute("productPropertys", result);
        return "product/toEditProductType";
    }

    @RequestMapping(value = "/doEditProductType")
    public String doEditProductType(ProductTypeVO productTypeVO, ModelMap model)
    {
        try
        {
            ProductType pt = new ProductType();
            String[] beans_array = productTypeVO.getBeans().split(Constant.StringSplitUtil.DECODE);
            List<ProductProperty> productPropertys = new ArrayList<ProductProperty>();
            for (int i = 0; i < beans_array.length; i++ )
            {
                String bean_str = beans_array[i];
                if (!bean_str.isEmpty())
                {
                    ProductProperty productProperty = new ProductProperty();
                    productProperty.setProductPropertyId(Long.valueOf(bean_str));
                    productPropertys.add(productProperty);
                }
            }
            pt.setProductTypeName(productTypeVO.getProductTypeName());
            pt.setProductTypeStatus("1");
            pt.setPropertyTypes(productPropertys);
            pt.setTypeId(productTypeVO.getTypeId());
            pt = productTypeService.editProductType(pt);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productTypeList");
            model.put("next_msg", "产品类型列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        // return "redirect:/product/productTypeList";
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/deleteProductType")
    public String deleteProductType(ProductTypeVO productTypeVO, ModelMap model)
    {
        try
        {
            ProductType pt = new ProductType();
            pt.setTypeId(productTypeVO.getTypeId());
            productTypeService.deleteProductType(pt);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productTypeList");
            model.put("next_msg", "产品类型列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/productTypeList")
    public String productTypeList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        YcPage<ProductType> page_list = productTypeService.queryCurrencyAccount(searchParams,
            pageNumber, pageSize, "");

        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");

        return "product/productTypeList";
    }

    @RequestMapping(value = "/toSaveProduct")
    public String toSaveProduct(Model model)
    {
        List<ProductType> productTypes = productTypeService.getAllProductType();
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();

        model.addAttribute("productTypes", productTypes);
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        return "product/toSaveProduct";
    }

    @RequestMapping(value = "/doSaveProduct")
    public String doSaveProduct(ProductVO productVO, ModelMap model)
    {
        try
        {
            AirtimeProduct parentProduct = null;
            if (BeanUtils.isNotNull(productVO.getParentProductId())
                && productVO.getParentProductId() > 0)
            {
                parentProduct = productPageQuery.queryAirtimeProductById(productVO.getParentProductId());

                // if (!parentProduct.getCarrierName().equals(productVO.getCarrierName())
                // || parentProduct.getParValue().compareTo(productVO.getParValue()) != 0)
                // {
                // model.put("message", "父节点产品配置错误！");
                // model.put("canback", true);
                // return PageConstant.PAGE_COMMON_NOTIFY;
                // }

                if (!parentProduct.getCarrierName().equalsIgnoreCase(productVO.getCarrierName())
                    || parentProduct.getParValue().compareTo(productVO.getParValue()) != 0)
                {
                    model.put("message", "产品与父产品属性不匹配");
                    model.put("canback", true);
                    return PageConstant.PAGE_COMMON_NOTIFY;
                }
            }
            else if (BeanUtils.isNotNull(productVO.getParentProductId())
                     && productVO.getParentProductId().compareTo(-1l) == 0)
            {
                parentProduct = null;
            }
            else
            {
                model.put("message", "请选择父产品！");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            if (StringUtil.isBlank(productVO.getProductName()))
            {
                model.put("message", "请填写产品名称！");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            AirtimeProduct ap = new AirtimeProduct();
            // 自定义名_运营商-省份-面额
            String productName = StringUtil.isBlank(productVO.getProductName()) ? StringUtil.initString() : productVO.getProductName();
            ap.setProductName(productName);
            ap.setProductStatus(productVO.getProductStatus());
            ap.setTypeId(Long.valueOf(productVO.getTypeId()));
            ap.setParentProductId(productVO.getParentProductId());
            if (productVO.getProvince() != null && !productVO.getProvince().isEmpty())
            {
                ap.setProvince(productVO.getProvince().replace(",", ""));
            }
            if (productVO.getCarrierName() != null && !productVO.getCarrierName().isEmpty())
            {
                ap.setCarrierName(productVO.getCarrierName().replace(",", ""));
            }
            if (productVO.getParValue() != null)
            {
                if (productVO.getParValue().compareTo(new BigDecimal(0)) <= 0)
                {
                    model.put("message", "面值不能小于等于0！");
                    model.put("canback", true);
                    return PageConstant.PAGE_COMMON_NOTIFY;
                }
                ap.setParValue(productVO.getParValue());
            }
            else
            {
                model.put("message", "面值不能小于等于0！");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            //表面金额存入数据库
            if (productVO.getDisplayValue() != null)
            {
                if (productVO.getDisplayValue().compareTo(new BigDecimal(0)) <= 0)
                {
                    model.put("message", "表面金额不能小于等于0！");
                    model.put("canback", true);
                    return PageConstant.PAGE_COMMON_NOTIFY;
                }
                ap.setDisplayValue(productVO.getDisplayValue());
            }
            
            if(null == ap.getDisplayValue())
            {
            	  ap.setDisplayValue(productVO.getParValue());
            }
     
            if (productVO.getCity() != null && !productVO.getCity().isEmpty())
            {
                ap.setCity(productVO.getCity().replace(",", ""));
            }
            
            //产品类型归类
            ProductType productType = productTypeService.queryProductTypeById(Long.parseLong(productVO.getTypeId()));
            ap.setBusinessType(productType.getBusinessType());
      
            ap = productManagement.saveProduct(ap);

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productList");
            model.put("next_msg", "产品列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/productList")
    public String productList(@RequestParam(value = "faceValue", defaultValue = "")
    String faceValue, @RequestParam(value = "carrierInfo", defaultValue = "")
    String carrierInfo, @RequestParam(value = "province", defaultValue = "")
    String province, @RequestParam(value = "city", defaultValue = "")
    String city, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType,@RequestParam(value = "businessType", defaultValue = "")
    String businessType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        if (StringUtil.isNotBlank(carrierInfo))
        {
            searchParams.put(EntityConstant.AirtimeProduct.CARRIER_NAME, carrierInfo);
        }
        if (StringUtil.isNotBlank(province))
        {
            searchParams.put(EntityConstant.AirtimeProduct.PROVINCE, province);
        }
        if (StringUtil.isNotBlank(city))
        {
            searchParams.put(EntityConstant.AirtimeProduct.CITY, city);
        }
        if (StringUtil.isNotBlank(faceValue))
        {
            searchParams.put(EntityConstant.AirtimeProduct.PARVALUE, faceValue);
        }
        if (StringUtils.isNotBlank(businessType))
        {
        	searchParams.put(EntityConstant.AirtimeProduct.BUSINESSTYPE, businessType);
        }
        YcPage<AirtimeProduct> page_list = productPageQuery.queryAirtimeProductList(searchParams,
            pageNumber, pageSize, EntityConstant.AirtimeProduct.PRODUCT_ID);

        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        List<City> citys = cityService.selectAll();

        model.addAttribute("faceValueVal", faceValue);
        model.addAttribute("carrierInfoVal", carrierInfo);
        model.addAttribute("businessType", businessType+"");
        model.addAttribute("provinceVal", province);
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("citys", citys);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "product/productList";
    }

    @RequestMapping(value = "/toSaveAgentProductRelation")
    public String toSaveAgentProductRelation(@RequestParam(value = "merchantId", defaultValue = "")
                                             String merchantId, Model model, ServletRequest request)
    {
        String merchantFlag = "";
        Merchant merchant = null;
        if (!merchantId.isEmpty())
        {
            merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(merchantId),
                IdentityType.MERCHANT);
            merchantFlag = "true";
        }
        else
        {
            merchantFlag = "false";
        }
        List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
        List<AirtimeProduct> products = productService.getAllProductByStatus(Constant.Product.PRODUCT_OPEN);
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("merchant", merchant);
        model.addAttribute("merchants", merchants);
        model.addAttribute("merchantFlag", merchantFlag);
        model.addAttribute("merchantId", merchantId);
        model.addAttribute("products", products);
        return "product/toSaveAgentProductRelation";
    }

    @RequestMapping(value = "/toSaveSupplyProductRelation")
    public String toSaveSupplyProductRelation(@RequestParam(value = "merchantId", defaultValue = "")
                                              String merchantId, Model model,
                                              ServletRequest request)
    {
        String merchantFlag = "";
        Merchant merchant = null;
        if (!merchantId.isEmpty())
        {
            merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(merchantId),
                IdentityType.MERCHANT);
            merchantFlag = "true";
        }
        else
        {
            merchantFlag = "false";
        }
        List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
        List<AirtimeProduct> products = productService.getAllProductByStatus(Constant.Product.PRODUCT_OPEN);
        List<MerchantProductLevel> merchantLevels = merchantLevelService.getAllMerchantProductLevel();
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();

        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("merchant", merchant);
        model.addAttribute("merchants", merchants);
        model.addAttribute("merchantFlag", merchantFlag);
        model.addAttribute("merchantId", merchantId);
        model.addAttribute("products", products);
        model.addAttribute("merchantLevels", merchantLevels);
        return "product/toSaveSupplyProductRelation";
    }

    @RequestMapping(value = "/toEditSupplyProductRelation")
    public String toEditSupplyProductRelation(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "id")
    Long id, @RequestParam(value = "source")
    String source, Model model, ServletRequest request)
    {
        Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
            new Long(merchantId), IdentityType.MERCHANT);
        List<AirtimeProduct> products = productService.getAllNoDelProduct();
        List<MerchantProductLevel> merchantLevels = merchantLevelService.getAllMerchantProductLevel();

        SupplyProductRelation upr = supplyProductRelationService.querySupplyProductRelationById(id);

        model.addAttribute("upr", upr);
        model.addAttribute("products", products);
        model.addAttribute("source", source);
        model.addAttribute("merchantLevels", merchantLevels);
        model.addAttribute("merchant", merchant);
        if (source.equalsIgnoreCase("merchant"))
        {
        	model.addAttribute("backUrl", "Merchant/supplyProductRelationList?merchantId="+merchantId);
        }
        else if (source.equalsIgnoreCase("list"))
        {
        	model.addAttribute("backUrl", "product/allSupplyProductRelation");
        }
        
        return "product/toEditSupplyProductRelation";
    }

    @RequestMapping(value = "/doEditSupplyProductRelation")
    @ResponseBody
    public String doEditSupplyProductRelation(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "source")
    String source, SupplyProductRelation upr, ModelMap model)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            AirtimeProduct product = productPageQuery.queryAirtimeProductById(upr.getProductId());
            upr.setCity(product.getCity());
            upr.setParValue(product.getParValue());
            upr.setCarrierName(product.getCarrierName());
            upr.setProvince(product.getProvince());
			upr.setPrice(product.getDisplayValue().multiply(upr.getDiscount()));
			upr.setBusinessType(product.getBusinessType());
			upr.setDisplayValue(product.getDisplayValue());
           
            supplyProductRelationService.editSupplyProductRelation(upr, operator.getDisplayName());
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            bl = Constant.TrueOrFalse.FALSE;
            LOGGER.error("[ProductController.doEditSupplyProductRelation()][异常:" + e.getMessage()
                         + "]");
        }
        return bl;
    }

    @RequestMapping(value = "/toEditAgentProductRelation")
    public String toEditAgentProductRelation(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "id")
    Long id, @RequestParam(value = "source")
    String source, Model model, ServletRequest request)
    {
        Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
            new Long(merchantId), IdentityType.MERCHANT);
        List<AirtimeProduct> products = productService.getAllNoDelProduct();

        AgentProductRelation dpr = agentProductRelationService.queryAgentProductRelationById(id);
        AirtimeProduct airtimeProduct = new AirtimeProduct();
        if (BeanUtils.isNotNull(dpr))
        {
            airtimeProduct = productService.findOne(dpr.getProductId());
        }
        model.addAttribute("dpr", dpr);
        model.addAttribute("airtimeProduct", airtimeProduct);
        model.addAttribute("products", products);
        model.addAttribute("source", source);
        model.addAttribute("merchant", merchant);
        if (source.equalsIgnoreCase("merchant"))
        {
        	model.addAttribute("backUrl", "Merchant/agentProductRelationList?merchantId="+merchantId);
        }
        else if (source.equalsIgnoreCase("list"))
        {
        	model.addAttribute("backUrl", "product/allAgentProductRelation");
        }
        return "product/toEditAgentProductRelation";
    }

    @RequestMapping(value = "/doEditAgentProductRelation")
    @ResponseBody
    public String doEditAgentProductRelation(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "source")
    String source, AgentProductRelation dpr, ModelMap model)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            AirtimeProduct product = productPageQuery.queryAirtimeProductById(dpr.getProductId());
            dpr.setCity(product.getCity());
            dpr.setParValue(product.getParValue());
            dpr.setCarrierName(product.getCarrierName());
			dpr.setProvince(product.getProvince());
			dpr.setPrice(product.getDisplayValue().multiply(dpr.getDiscount()));
			dpr.setBusinessType(product.getBusinessType());
			dpr.setDisplayValue(product.getDisplayValue());
            agentProductRelationService.editAgentProductRelation(dpr, operator.getDisplayName());
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            bl = Constant.TrueOrFalse.FALSE;
            LOGGER.error("[ProductController.doEditAgentProductRelation()][异常:" + e.getMessage()
                         + "]");
        }
        return bl;
    }

    @RequestMapping(value = "/doSaveAgentProductRelation")
    public String doSaveAgentProductRelation(@RequestParam(value = "merchantFlag")
    String merchantFlag, AgentProductRelation dpr, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            AirtimeProduct product = productPageQuery.queryAirtimeProductById(dpr.getProductId());
            String agentProdId = dpr.getAgentProdId();
            if(null != agentProdId && ! agentProdId.trim().equals(""))
            {
            	   AgentProductRelation agentProductRelation = agentProductRelationService.queryAgentProductRelationByParams(agentProdId, dpr.getIdentityId(), null);
            	   if(null  != agentProductRelation)
            	   {
            		   model.put("message", "操作失败, 代理商产品的外部产品编码重复【" + dpr.getAgentProdId() + "】");
                       model.put("next_url", "");
                       model.put("canback", true);
                       return PageConstant.PAGE_COMMON_NOTIFY;
            	   }
            }
         
            if (product != null)
            {
            	BigDecimal price = product.getDisplayValue().multiply(dpr.getDiscount());
				
                if (product.getProvince() != null && !product.getProvince().isEmpty())
                {
                    dpr.setProvince(product.getProvince());
                }
                if (product.getCarrierName() != null && !product.getCarrierName().isEmpty())
                {
                    dpr.setCarrierName(product.getCarrierName());
                }
                if (product.getParValue() != null)
                {
                    dpr.setParValue(product.getParValue());
                }
                
                if (product.getDisplayValue() != null)
                {
                    dpr.setDisplayValue(product.getDisplayValue());
                }
                dpr.setBusinessType(product.getBusinessType());
                if (product.getCity() != null && !product.getCity().isEmpty())
                {
                    dpr.setCity(product.getCity());
                }
                
                dpr.setPrice(price);
                dpr.setIdentityType(IdentityType.MERCHANT.toString());
                dpr.setStatus(Constant.AgentProductStatus.CLOSE_STATUS);
                dpr.setDefValue(true);
                agentProductRelationService.doSaveAgentProductRelation(dpr, operator.getDisplayName());
                model.put("message", "操作成功");
                model.put("canback", false);
                if (merchantFlag.equalsIgnoreCase(Constant.TrueOrFalse.TRUE))
                {
                    model.put("next_url",
                        "Merchant/agentProductRelationList?merchantId=" + dpr.getIdentityId());
                }
                else
                {
                    model.put("next_url", "product/allAgentProductRelation");
                }
                model.put("next_msg", "商户产品列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("next_url", "");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.doSaveAgentProductRelation()][异常:" + e.getMessage()
                         + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;

        // return "redirect:/Merchant/downProductRelationList?merchantId="+merchantId;
    }

    @RequestMapping(value = "/doSaveSupplyProductRelation")
    public String doSaveSupplyProductRelation(@RequestParam(value = "merchantFlag")
    String merchantFlag, SupplyProductRelation upr, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            AirtimeProduct product = productPageQuery.queryAirtimeProductById(upr.getProductId());
            if (product != null)
            {
                if (product.getProvince() != null && !product.getProvince().isEmpty())
                {
                    upr.setProvince(product.getProvince());
                }
                if (product.getCarrierName() != null && !product.getCarrierName().isEmpty())
                {
                    upr.setCarrierName(product.getCarrierName());
                }
                if (product.getParValue() != null)
                {
                    upr.setParValue(product.getParValue());
                }
                if (product.getDisplayValue() != null)
                {
                	upr.setDisplayValue(product.getDisplayValue());
                }
                upr.setBusinessType(product.getBusinessType());
                if (product.getCity() != null && !product.getCity().isEmpty())
                {
                    upr.setCity(product.getCity());
                }
				
				BigDecimal	price = product.getDisplayValue().multiply(upr.getDiscount());
		
                if(!StringUtil.isNullOrEmpty(upr.getSupplyProdId()))
                {
                    boolean isExists=supplyProductRelationService.isExistsSupplyProductId(upr.getIdentityId(), upr.getSupplyProdId());
                    if(isExists)
                    {
                    	model.put("message", "操作失败，供货商产品的外部产品编码重复【"+upr.getSupplyProdId()+"】");
                        model.put("canback", true);
                        return PageConstant.PAGE_COMMON_NOTIFY;
                    }
                }
                upr.setIdentityType(IdentityType.MERCHANT.toString());
                upr.setPrice(price);
                // upr.setQuality(new
                // BigDecimal(Constant.Product.INIT_UP_PRODUCT_REALTION_QUALITY));
                upr.setStatus(Constant.SupplyProductStatus.CLOSE_STATUS);
                supplyProductRelationService.doSaveSupplyProductRelation(upr, operator.getDisplayName());
                model.put("message", "操作成功");
                model.put("canback", false);
                if (merchantFlag.equalsIgnoreCase(Constant.TrueOrFalse.TRUE))
                {
                    model.put("next_url",
                        "Merchant/supplyProductRelationList?merchantId=" + upr.getIdentityId());
                }
                else
                {
                    model.put("next_url", "product/allSupplyProductRelation");
                }
                model.put("next_msg", "商户产品列表");
            }
            else
            {
                model.put("message", "未找到该产品");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.doSaveSupplyProductRelation()][异常:" + e.getMessage()
                         + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/toProductPropertyRegister")
    public String toProductPropertyRegister(Model model)
    {
        return "product/productPropertyRegister.ftl";
    }

    @RequestMapping(value = "/doProductPropertyRegister")
    public String doProductPropertyRegister(ProductProperty pp, ModelMap model)
    {
        try
        {
            productPropertyService.saveProductProperty(pp);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productPropertyList");
            model.put("next_msg", "产品属性列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.toProductPropertyRegister()][异常:" + e.getMessage()
                         + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/productPropertyList")
    public String productPropertyList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        YcPage<ProductProperty> page_list = productPropertyService.queryProductProperty(
            searchParams, pageNumber, pageSize, "");

        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "product/productPropertyList.ftl";
    }

    @RequestMapping(value = "/getHtmlElement")
    @ResponseBody
    public String getHtmlElement(@RequestParam(value = "productTypeId")
    String productTypeId, Model model)
    {
        ProductType pt = new ProductType();
        pt.setTypeId(Long.valueOf(productTypeId));

        StringBuffer html = new StringBuffer();
        StringBuffer faceVal_sb = new StringBuffer();
        StringBuffer carrier_sb = new StringBuffer();
        StringBuffer province_sb = new StringBuffer();
        StringBuffer city_sb = new StringBuffer();

        // 区别此产品类型是否有城市属性，如果没有。省份select则不用绑定js函数。如果有，身份select则需要绑定js函数
        boolean city_boolean = false;
        pt =  productTypeService.queryProductTypeById(pt);
        List<ProductProperty> productPropertys = pt.getPropertyTypes();
        for (Iterator<ProductProperty> iterator = productPropertys.iterator(); iterator.hasNext();)
        {
            ProductProperty pp = iterator.next();
            if (pp.getParamName().equals("城市"))
            {
                city_boolean = true;
            }
        }

        for (Iterator<ProductProperty> iterator = productPropertys.iterator(); iterator.hasNext();)
        {
            ProductProperty pp = iterator.next();

            if (pp.getAttribute().equalsIgnoreCase("word"))
            {
                if (pp.getParamName().equals("面值"))
                {
                    faceVal_sb.append("<tr class=\"add\">");
                    faceVal_sb.append("<th>");
                    faceVal_sb.append("<span class=\"requiredField\">*</span>" + pp.getParamName()
                                      + ":");
                    faceVal_sb.append("</th>");
                    faceVal_sb.append("<td>");
                    faceVal_sb.append("<input type=\'text\' class=\'ipt\' id=\'"
                                      + pp.getParamEnglishName() + "\' name=\'"
                                      + pp.getParamEnglishName() + "\' maxLength=\'"
                                      + pp.getMaxLength() + "\' >");
                    if(pt.getBusinessType() == Integer.valueOf(Constant.BusinessType.BUSINESS_TYPE_FLOW))
                    {
                    	faceVal_sb.append("<select id=\"unitType\" name=\"unitType\"  class=\"select w80\" >");
                    	faceVal_sb.append("<option value=\"0\" >M</option>");
                    	faceVal_sb.append("<option value=\"1\" >G</option>");
                    	faceVal_sb.append("</select>");
                    	faceVal_sb.append(" 说明：流量包大小");
                    }
                    
                    faceVal_sb.append("</td>");
                    faceVal_sb.append("</tr>");
                 
                }
                else if (pp.getParamName().equals("面额"))
                {
                	faceVal_sb.append("<tr class=\"add\">");
                    faceVal_sb.append("<th>");
                    faceVal_sb.append("<span class=\"requiredField\">*</span>" + pp.getParamName()
                                      + ":");
                    faceVal_sb.append("</th>");
                    faceVal_sb.append("<td>");
                    faceVal_sb.append("<input type=\'text\' class=\'ipt\' id=\'"
                                      + pp.getParamEnglishName() + "\' name=\'"
                                      + pp.getParamEnglishName() + "\' maxLength=\'"
                                      + pp.getMaxLength() + "\' >");
                    faceVal_sb.append(" 说明：产品对应的表面金额,单位为元");
                    faceVal_sb.append("</td>");
                    faceVal_sb.append("</tr>");
                }
            }
            else if (pp.getAttribute().equalsIgnoreCase("select"))
            {
                if (pp.getParamName().equals("运营商"))
                {
                    List<CarrierInfo> carrierList = carrierInfoService.getAllCarrierInfo();
                    carrier_sb.append("<tr class=\"add\">");
                    carrier_sb.append("<th>");
                    carrier_sb.append("<span class=\"requiredField\">*</span>" + pp.getParamName()
                                      + ":");
                    carrier_sb.append("</th>");
                    carrier_sb.append("<td>");

                    carrier_sb.append("<select id=\'" + pp.getParamEnglishName()
                                      + "\' class=\'select\' name=\'" + pp.getParamEnglishName()
                                      + "\' >");
                    for (Iterator<CarrierInfo> iterator2 = carrierList.iterator(); iterator2.hasNext();)
                    {
                        CarrierInfo carrier = iterator2.next();
                        carrier_sb.append("<option value=\'" + carrier.getCarrierNo() + "\'>"
                                          + carrier.getCarrierName() + "</option>");
                    }
                    carrier_sb.append("</select>");
                    carrier_sb.append("</td>");
                    carrier_sb.append("</tr>");
                }
                if (pp.getParamName().equals("省份"))
                {
                    List<Province> provinceList = provinceService.getAllProvince();
                    province_sb.append("<tr class=\"add\">");
                    province_sb.append("<th>");
                    province_sb.append("<span class=\"requiredField\">*</span>"
                                       + pp.getParamName() + ":");
                    province_sb.append("</th>");
                    province_sb.append("<td>");
                    if (city_boolean)
                    {
                        // 有城市属性
                        province_sb.append("<select id=\'" + pp.getParamEnglishName()
                                           + "\' class=\'select\' name=\'"
                                           + pp.getParamEnglishName()
                                           + "\' onchange=\"getCityByProvince(this)\" >");
                    }
                    else
                    {
                        // 没有城市属性
                        province_sb.append("<select id=\'" + pp.getParamEnglishName()
                                           + "\' class=\'select\' name=\'"
                                           + pp.getParamEnglishName() + "\' >");
                    }
                    province_sb.append("<option value=\'\'>请选择</option>");
                    for (Iterator<Province> iterator2 = provinceList.iterator(); iterator2.hasNext();)
                    {
                        Province province = iterator2.next();
                        // if(province.getId().equalsIgnoreCase(Constant.Product.DEFAULT_SELECT_VAL)){
                        // province_sb.append("<option value=\'"+province.getId()+"\' selected=\"selected\">"+province.getProvinceName()+"</option>");
                        // }else{
                        province_sb.append("<option value=\'" + province.getProvinceId() + "\'>"
                                           + province.getProvinceName() + "</option>");
                        // }
                    }
                    province_sb.append("</select>");
                    province_sb.append("</td>");
                    province_sb.append("</tr>");
                }
                if (pp.getParamName().equals("城市"))
                {
                    // List<City> citys =
                    // cityService.getCityByProvince(Constant.Product.DEFAULT_SELECT_VAL);
                    // if(citys.size()>0){
                    city_sb.append("<tr class=\"add\">");
                    city_sb.append("<th>");
                    city_sb.append("<span class=\"requiredField\">*</span>" + pp.getParamName()
                                   + ":");
                    city_sb.append("</th>");
                    city_sb.append("<td>");

                    city_sb.append("<select id=\'" + pp.getParamEnglishName()
                                   + "\' class=\'select\' name=\'" + pp.getParamEnglishName()
                                   + "\' >");
                    city_sb.append("<option value=\'\'>请选择</option>");
                    city_sb.append("<div id=\"city\">");
                    // for (Iterator<City> iterator2 = citys.iterator(); iterator2.hasNext();) {
                    // City city = iterator2.next();
                    // city_sb.append("<option value=\'"+city.getId()+"\'>"+city.getCityName()+"</option>");
                    // }
                    city_sb.append("</div>");
                    city_sb.append("</select>");
                    city_sb.append("</td>");
                    city_sb.append("</tr>");
                    // }

                }
            }
        }
        html = faceVal_sb.append(carrier_sb.append(province_sb.append(city_sb)));
        return html.toString();
    }

    @RequestMapping(value = "/getCityByProvince")
    @ResponseBody
    public String getCityByProvince(@RequestParam(value = "provinceId")
    String provinceId, Model model)
    {
        StringBuffer city_sb = new StringBuffer();
        List<City> citys = cityService.getCityByProvince(provinceId);
        city_sb.append("<option value=\'\'>请选择</option>");
        if (citys.size() > 0)
        {
            for (Iterator<City> iterator2 = citys.iterator(); iterator2.hasNext();)
            {
                City city = iterator2.next();
                city_sb.append("<option value=\'" + city.getCityId() + "\'>" + city.getCityName()
                               + "</option>");
            }
        }
        return city_sb.toString();
    }

    @RequestMapping(value = "/getParentProductIds")
    @ResponseBody
    public String getParentProductIds(@RequestParam(value = "parValue", defaultValue = "")
    String parValue, @RequestParam(value = "carrierName", defaultValue = "")
    String carrierName, @RequestParam(value = "province", defaultValue = "")
    String province, @RequestParam(value = "parentProductId", defaultValue = "")
    String parentProductId, Model model)
    {
        StringBuffer sb = new StringBuffer();
        List<AirtimeProduct> products = productPageQuery.fuzzyQueryAirtimeProductsByParams(
        		 province, parValue, carrierName, StringUtil.initString(),StringUtil.initString());
        String html = StringUtil.initString();
        if (products.size() > 0)
        {
            for (Iterator<AirtimeProduct> iterator2 = products.iterator(); iterator2.hasNext();)
            {
                AirtimeProduct product = iterator2.next();
                if (parentProductId != null && !parentProductId.isEmpty())
                {
                    if (product.getProductId().compareTo(Long.valueOf(parentProductId)) == 0)
                    {
                        sb.append("<option value=\'" + product.getProductId()
                                  + "\' selected=\"selected\">" + product.getProductName()
                                  + "</option>|");
                    }
                    else
                    {
                        sb.append("<option value=\'" + product.getProductId() + "\'>"
                                  + product.getProductName() + "</option>|");
                    }
                }
                else
                {
                    sb.append("<option value=\'" + product.getProductId() + "\'>"
                              + product.getProductName() + "</option>|");
                }
            }
            html = sb.toString().substring(0, sb.length() - 1);
        }
        return html;
    }

    @RequestMapping(value = "/updateProductStatus")
    public String updateProductStatus(AirtimeProduct ap, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            ap = productService.updateProductStatus(ap, ap.getProductStatus());
            if (ap != null)
            {
                if (Constant.Product.PRODUCT_CLOSE.equals(ap.getProductStatus()))
                {
                    List<AgentProductRelation> arlist = agentProductRelationService.getAllAgentProductRelationByUp(ap.getProductId());
                    for (AgentProductRelation agentProductRelation : arlist)
                    {
                        agentProductRelation.setStatus(ap.getProductStatus());
                        agentProductRelationService.editAgentProductRelation(agentProductRelation, operator.getDisplayName());
                    }

                    List<SupplyProductRelation> srlist = supplyProductRelationService.getAllSupplyProductRelationByDown(ap.getProductId());
                    for (SupplyProductRelation supplyProductRelation : srlist)
                    {
                        supplyProductRelation.setStatus(ap.getProductStatus());
                        supplyProductRelationService.editSupplyProductRelation(supplyProductRelation, operator.getDisplayName());
                    }
                }
            }
            else
            {
                model.put("message", "操作失败[修改产品状态失败!]");
                model.put("canback", true);
            }

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productList");
            model.put("next_msg", "产品列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.updateProductStatus()][异常:" + e.getMessage() + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/deleteProudct")
    public String deleteProudct(AirtimeProduct ap, ModelMap model)
    {
        try
        {
            List<AgentProductRelation> agentProductRelations = agentProductRelationService.getAllAgentProductRelationByUp(ap.getProductId());
            List<SupplyProductRelation> supplyProductRelations = supplyProductRelationService.getAllSupplyProductRelationByDown(ap.getProductId());
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            if (agentProductRelations.size() > 0 || supplyProductRelations.size() > 0)
            {
                model.put("message", "操作失败[该产品与商户有关联,请先删除关系]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            productManagement.deleteProduct(ap, operator.getDisplayName());
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productList");
            model.put("next_msg", "产品列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.deleteProudct()][异常:" + e.getMessage() + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/showProductTrees")
    public String showProductTrees(Model model)
    {
        StringBuffer sb = new StringBuffer();
        List<AirtimeProduct> rootProducts = productService.getAllRootProducts();
        for (AirtimeProduct rootProduct : rootProducts)
        {
            sb.append("{ id:" + rootProduct.getProductId() + ", pId:" + rootProduct.getProductId()
                      + ", name:\"" + rootProduct.getProductName() + "\", t:\""
                      + rootProduct.getProductName() + "\"},\n");
            List<AirtimeProduct> childProducts = productService.getChildProducts(rootProduct.getProductId());
            for (AirtimeProduct childProduct : childProducts)
            {
                sb.append("{ id:" + childProduct.getProductId() + ", pId:"
                          + rootProduct.getProductId() + ", name:\""
                          + childProduct.getProductName() + "\", t:\""
                          + rootProduct.getProductName() + "\"},\n");
                List<AirtimeProduct> childProducts2 = productService.getChildProducts(childProduct.getProductId());
                for (AirtimeProduct childProduct2 : childProducts2)
                {
                    sb.append("{ id:" + childProduct2.getProductId() + ", pId:"
                              + childProduct.getProductId() + ", name:\""
                              + childProduct2.getProductName() + "\", t:\""
                              + rootProduct.getProductName() + "\"},\n");
                }
            }
        }
        // { id:11, pId:1, name:"叶子节点 - 1", t:"我很普通，随便点我吧"},
        // { id:12, pId:1, name:"叶子节点 - 2", t:"我很普通，随便点我吧"},
        model.addAttribute("tree", sb.toString());
        return "product/allProducts";
    }

    @RequestMapping(value = "/showProductIdentityRelation")
    public String showProductIdentityRelation(@RequestParam(value = "merchantId")
    Long merchantId, Model model)
    {
        StringBuffer sb = new StringBuffer();
        List<AgentProductRelation> merchantProducts = (List<AgentProductRelation>)agentProductRelationService.getAllProductByAgentMerchantId(
            merchantId, IdentityType.MERCHANT.toString(), "");
        Map<Long, AgentProductRelation> checkList = new HashMap<Long, AgentProductRelation>();
        for (AgentProductRelation merchantProduct : merchantProducts)
        {
            checkList.put(merchantProduct.getProductId(), merchantProduct);
        }

        List<AirtimeProduct> rootProducts = productService.getAllRootProducts();
        Long pid0 = null;
        Long pid1 = null;
        Long pid2 = null;
        for (AirtimeProduct rootProduct : rootProducts)
        {
            if (checkList.containsKey(rootProduct.getProductId()))
            {
                AgentProductRelation rootDpr = checkList.get(rootProduct.getProductId());
                pid0 = rootDpr.getId();
                if (rootDpr.getDefValue())
                {
                    sb.append("{ id:" + rootDpr.getId() + ", pId:" + pid0 + ", name:\""
                              + rootDpr.getProductName() + "\", checked:true,open:true},\n");
                }
                else
                {
                    sb.append("{ id:" + rootDpr.getId() + ", pId:" + pid0 + ", name:\""
                              + rootDpr.getProductName() + "\", open:true},\n");
                }
            }
            List<AirtimeProduct> childProducts = productService.getChildProducts(rootProduct.getProductId());
            pid1 = pid0;
            for (AirtimeProduct childProduct : childProducts)
            {
                Long childDprId = null;
                if (checkList.containsKey(childProduct.getProductId()))
                {
                    AgentProductRelation childDpr = checkList.get(childProduct.getProductId());
                    if (pid1 == null)
                    {
                        pid1 = childDpr.getId();
                    }
                    childDprId = childDpr.getId();
                    if (childDpr.getDefValue())
                    {
                        sb.append("{ id:" + childDpr.getId() + ", pId:" + pid1 + ", name:\""
                                  + childDpr.getProductName() + "\", checked:true,open:true},\n");
                    }
                    else
                    {
                        sb.append("{ id:" + childDpr.getId() + ", pId:" + pid1 + ", name:\""
                                  + childDpr.getProductName() + "\", open:true},\n");
                    }
                }
                List<AirtimeProduct> childProducts2 = productService.getChildProducts(childProduct.getProductId());
                if (pid1 != null)
                {
                    if (childDprId != null)
                    {
                        if (pid1.compareTo(childDprId) != 0)
                        {
                            pid2 = childDprId;
                        }
                        else
                        {
                            pid2 = pid1;
                        }
                    }
                    else
                    {
                        pid2 = pid1;
                    }
                }
                for (AirtimeProduct childProduct2 : childProducts2)
                {
                    if (checkList.containsKey(childProduct2.getProductId()))
                    {
                        AgentProductRelation childDpr2 = checkList.get(childProduct2.getProductId());
                        if (pid2 == null)
                        {
                            pid2 = childDpr2.getId();
                        }
                        if (childDpr2.getDefValue())
                        {
                            sb.append("{ id:" + childDpr2.getId() + ", pId:" + pid2 + ", name:\""
                                      + childDpr2.getProductName()
                                      + "\", checked:true,open:true},\n");
                        }
                        else
                        {
                            sb.append("{ id:" + childDpr2.getId() + ", pId:" + pid2 + ", name:\""
                                      + childDpr2.getProductName() + "\", open:true},\n");
                        }
                    }
                }
                pid2 = null;
                pid1 = pid0;
            }
            pid0 = null;
        }

        model.addAttribute("tree", sb.toString());
        model.addAttribute("merchantId", merchantId);
        return "product/defaultProduct";
    }

    @RequestMapping(value = "/updateDefValueByIds")
    public String updateDefValueByIds(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "changeNodes")
    String changeNodes, ModelMap model)
    {
        try
        {
            agentProductRelationService.updateDefValueByIds(changeNodes, merchantId);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "Merchant/agentProductRelationList?merchantId=" + merchantId);
            model.put("next_msg", "商户产品列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.updateDefValueByIds()][异常:" + e.getMessage() + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/toEditProduct")
    public String toEditProduct(AirtimeProduct ap, Model model)
    {
        AirtimeProduct product = productPageQuery.queryAirtimeProductById(ap.getProductId());
        List<Province> provinceList = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfoList = carrierInfoService.getAllCarrierInfo();
        List<ProductType> productTypes = productTypeService.getAllProductType();
        List<AirtimeProduct> parentProducts = productPageQuery.fuzzyQueryAirtimeProductsByParams(
            null, product.getParValue() == null ? "" : product.getParValue().toString(),
            product.getCarrierName(), null, null);
        ProductType pt = new ProductType();
        pt.setTypeId(product.getTypeId());

        // 区别此产品类型是否有城市属性，如果没有。省份select则不用绑定js函数。如果有，身份select则需要绑定js函数
        boolean city_boolean = false;

        StringBuffer html = new StringBuffer();
        StringBuffer faceVal_sb = new StringBuffer();
        StringBuffer carrier_sb = new StringBuffer();
        StringBuffer province_sb = new StringBuffer();
        StringBuffer city_sb = new StringBuffer();

        List<ProductProperty> productPropertyList = productTypeService.queryProductTypeById(pt).getPropertyTypes();
        for (Iterator<ProductProperty> iterator = productPropertyList.iterator(); iterator.hasNext();)
        {
            ProductProperty pp = iterator.next();
            if (pp.getParamName().equals("城市"))
            {
                city_boolean = true;
            }
        }

        for (Iterator<ProductProperty> iterator = productPropertyList.iterator(); iterator.hasNext();)
        {
            ProductProperty pp = iterator.next();
            if (pp.getAttribute().equalsIgnoreCase("word"))
            {
                if (pp.getParamName().equals("面值"))
                {
                    faceVal_sb.append("<tr class=\"add\">");
                    faceVal_sb.append("<th>");
                    faceVal_sb.append("<span class=\"requiredField\">*</span>" + pp.getParamName()+"(元/M)"
                                      + ":");
                    faceVal_sb.append("</th>");
                    faceVal_sb.append("<td>" + product.getParValue());
                    faceVal_sb.append("<input type=\'hidden\' class=\'ipt\' id=\'"
                                      + pp.getParamEnglishName() + "\' name=\'"
                                      + pp.getParamEnglishName() + "\' maxLength=\'"
                                      + pp.getMaxLength() + "\' value=\'" + product.getParValue()
                                      + "\' readonly=\'true\'>");
                    faceVal_sb.append("</td>");
                    faceVal_sb.append("</tr>");
                }
                else if (pp.getParamName().equals("面额"))
                {
                	faceVal_sb.append("<tr class=\"add\">");
                    faceVal_sb.append("<th>");
                    faceVal_sb.append("<span class=\"requiredField\">*</span>" + pp.getParamName()+"(元)"
                                      + ":");
                    faceVal_sb.append("</th>");
                    faceVal_sb.append("<td>");
//                    faceVal_sb.append(product.getDisplayValue());
                    faceVal_sb.append("<input type=\'text\' class=\'ipt\' id=\'"
                                      + pp.getParamEnglishName() + "\' name=\'"
                                      + pp.getParamEnglishName() + "\' maxLength=\'"
                                      + pp.getMaxLength() +"\' value=\'" + product.getDisplayValue()+ "\'>");
                    faceVal_sb.append(" 说明：产品对应的表面金额,单位为元");
                    faceVal_sb.append("</td>");
                    faceVal_sb.append("</tr>");
                }
            }
            else if (pp.getAttribute().equalsIgnoreCase("select"))
            {
                if (pp.getParamName().equals("运营商"))
                {
                    carrier_sb.append("<tr class=\"add\">");
                    carrier_sb.append("<th>");
                    carrier_sb.append("<span class=\"requiredField\">*</span>" + pp.getParamName()
                                      + ":");
                    carrier_sb.append("</th>");
                    carrier_sb.append("<td>");
                    carrier_sb.append("<select id=\'" + pp.getParamEnglishName()
                                      + "\' class=\'select\' name=\'" + pp.getParamEnglishName()
                                      + "\' disabled=\'true\'>");
                    for (Iterator<CarrierInfo> iterator2 = carrierInfoList.iterator(); iterator2.hasNext();)
                    {
                        CarrierInfo carrier = iterator2.next();
                        if (product.getCarrierName() != null
                            && product.getCarrierName().equalsIgnoreCase(carrier.getCarrierNo()))
                        {
                            carrier_sb.append("<option selected=\'selected\' value=\'"
                                              + carrier.getCarrierNo() + "\'>"
                                              + carrier.getCarrierName() + "</option>");
                        }
                        else
                        {
                            carrier_sb.append("<option value=\'" + carrier.getCarrierNo() + "\'>"
                                              + carrier.getCarrierName() + "</option>");
                        }
                    }
                    carrier_sb.append("</select>");
                    carrier_sb.append("</td>");
                    carrier_sb.append("</tr>");
                }
                if (pp.getParamName().equals("省份"))
                {
                    province_sb.append("<tr class=\"add\">");
                    province_sb.append("<th>");
                    province_sb.append("<span class=\"requiredField\">*</span>"
                                       + pp.getParamName() + ":");
                    province_sb.append("</th>");
                    province_sb.append("<td>");
                    if (city_boolean)
                    {
                        // 有城市属性
                        province_sb.append("<select id=\'"
                                           + pp.getParamEnglishName()
                                           + "\' class=\'select\' name=\'"
                                           + pp.getParamEnglishName()
                                           + "\' onchange=\"getCityByProvince(this)\" disabled=\'true\'>");
                    }
                    else
                    {
                        // 没有城市属性
                        province_sb.append("<select id=\'" + pp.getParamEnglishName()
                                           + "\' class=\'select\' name=\'"
                                           + pp.getParamEnglishName() + "\' disabled=\'true\'>");
                    }
                    province_sb.append("<option value=\'\'>请选择</option>");
                    for (Iterator<Province> iterator2 = provinceList.iterator(); iterator2.hasNext();)
                    {
                        Province province = iterator2.next();
                        if (product.getProvince() != null
                            && product.getProvince().equalsIgnoreCase(province.getProvinceId()))
                        {
                            province_sb.append("<option selected=\'selected\' value=\'"
                                               + province.getProvinceId() + "\'>"
                                               + province.getProvinceName() + "</option>");
                        }
                        else
                        {
                            province_sb.append("<option value=\'" + province.getProvinceId()
                                               + "\'>" + province.getProvinceName() + "</option>");
                        }
                    }
                    province_sb.append("</select>");
                    province_sb.append("</td>");
                    province_sb.append("</tr>");
                }
                if (pp.getParamName().equals("城市"))
                {
                    List<City> citys = cityService.getCityByProvince(product.getProvince());

                    if (citys.size() > 0)
                    {
                        city_sb.append("<tr class=\"add\">");
                        city_sb.append("<th>");
                        city_sb.append("<span class=\"requiredField\">*</span>"
                                       + pp.getParamName() + ":");
                        city_sb.append("</th>");
                        city_sb.append("<td>");
                        city_sb.append("<select id=\'" + pp.getParamEnglishName()
                                       + "\' class=\'select\' name=\'" + pp.getParamEnglishName()
                                       + "\' disabled=\'true\'>");
                        city_sb.append("<option value=\'\'>请选择</option>");
                        city_sb.append("<div id=\"city\">");
                        for (Iterator<City> iterator2 = citys.iterator(); iterator2.hasNext();)
                        {
                            City city = iterator2.next();
                            if (product.getCity() != null
                                && product.getCity().equalsIgnoreCase(city.getCityId()))
                            {
                                city_sb.append("<option selected=\'selected\' value=\'"
                                               + city.getCityId() + "\'>" + city.getCityName()
                                               + "</option>");
                            }
                            else
                            {
                                city_sb.append("<option value=\'" + city.getCityId() + "\'>"
                                               + city.getCityName() + "</option>");
                            }
                        }
                        city_sb.append("</div>");
                        city_sb.append("</select>");
                        city_sb.append("</td>");
                        city_sb.append("</tr>");
                    }

                }
            }
        }
        html = faceVal_sb.append(carrier_sb.append(province_sb.append(city_sb)));
        String productPropertys = html.toString();
        List<AirtimeProduct> products = productService.getAllProduct();

        Iterator<AirtimeProduct> apIterator = parentProducts.iterator();
        while (apIterator.hasNext())
        {
            AirtimeProduct apAirtimeProduct = apIterator.next();

            if (StringUtil.isBlank(apAirtimeProduct.getProvince())
                || apAirtimeProduct.getProvince().equals(product.getProvince()))
            {
                continue;
            }
            else
            {
                apIterator.remove();
            }
        }
        model.addAttribute("parentProducts", parentProducts);
        model.addAttribute("productPropertys", productPropertys);
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("provinceList", provinceList);
        model.addAttribute("carrierInfoList", carrierInfoList);
        return "product/toEditProduct";
    }

    @RequestMapping(value = "/doEditProduct")
    public String doEditProduct(AirtimeProduct product, ModelMap model)
    {
        AirtimeProduct updateProduct = productPageQuery.queryAirtimeProductById(product.getProductId());
        AirtimeProduct parentProduct = productPageQuery.queryAirtimeProductById(product.getParentProductId());
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            if (BeanUtils.isNotNull(product))
            {
                if (!parentProduct.getCarrierName().equals(updateProduct.getCarrierName())
                    || parentProduct.getParValue().compareTo(updateProduct.getParValue()) != 0)
                {
                    model.put("message", "父节点产品配置错误！");
                    model.put("canback", true);
                    return PageConstant.PAGE_COMMON_NOTIFY;
                }
                List<AgentProductRelation> arlist = agentProductRelationService.getAllAgentProductRelationByUp(updateProduct.getProductId());
                List<SupplyProductRelation> srlist = supplyProductRelationService.getAllSupplyProductRelationByDown(updateProduct.getProductId());
                //如果该商品与商户存在关系将不允许修改此产品
                if (arlist.size() > 0 || srlist.size() > 0)
                {
                    model.put("message", "操作失败[该产品与商户有关联,请先删除关系]");
                    model.put("canback", true);
                    return PageConstant.PAGE_COMMON_NOTIFY;
                }

                updateProduct.setProductStatus(product.getProductStatus());
                updateProduct.setParentProductId(product.getParentProductId());
                updateProduct.setParValue(product.getParValue());
                updateProduct.setProductName(product.getProductName());
                updateProduct.setDisplayValue(BeanUtils.isNotNull(product.getDisplayValue())?product.getDisplayValue():product.getParValue());
                AirtimeProduct ap = productManagement.editProduct(updateProduct);
                if (BeanUtils.isNotNull(ap)&&Constant.Product.PRODUCT_CLOSE.equals(ap.getProductStatus()))
                {
                    for (AgentProductRelation agentProductRelation : arlist)
                    {
                        agentProductRelation.setStatus(ap.getProductStatus());
                        agentProductRelationService.editAgentProductRelation(agentProductRelation, operator.getDisplayName());
                    }

                    for (SupplyProductRelation supplyProductRelation : srlist)
                    {
                        supplyProductRelation.setStatus(ap.getProductStatus());
                        supplyProductRelationService.editSupplyProductRelation(supplyProductRelation, operator.getDisplayName());
                    }
                }
            }

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/productList");
            model.put("next_msg", "产品列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.doEditProduct()][异常:" + e.getMessage() + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        // return "redirect:/product/productList";
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/updateAgentProductRelationStatus")
    @ResponseBody
    public String updateAgentProductRelationStatus(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "id")
    Long id, @RequestParam(value = "productId", defaultValue = "")
    Long productId, @RequestParam(value = "status")
    String status, @RequestParam(value = "source")
    String source, ModelMap model)
    {
        String bl = TrueOrFalse.FALSE;
        try
        {
            agentProductRelationService.updateAgentProductRelationStatus(id, status);
            bl = TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            bl = TrueOrFalse.FALSE;
        }
        catch (Exception e)
        {
            bl = TrueOrFalse.FALSE;
            LOGGER.error("[ProductController.updateAgentProductRelationStatus()][异常:"
                         + e.getMessage() + "]");
        }

        return bl;
    }

    @RequestMapping(value = "/updateSupplyProductRelationStatus")
    @ResponseBody
    public String updateSupplyProductRelationStatus(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "id")
    Long id, @RequestParam(value = "productId", defaultValue = "")
    Long productId, @RequestParam(value = "status")
    String status, @RequestParam(value = "source")
    String source, ModelMap model)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        try
        {
            supplyProductRelationService.updateSupplyProductRelationStatus(id, status);
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败:[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            bl = Constant.TrueOrFalse.FALSE;
            LOGGER.error("[ProductController.updateSupplyProductRelationStatus()][异常:"
                         + e.getMessage() + "]");
        }
        return bl;
    }

    /**
     * 开启关闭 功能描述: <br> 参数说明: <br>
     */
    @RequestMapping(value = "/batchUpdateAgentProductRelationStatus")
    @ResponseBody
    public String batchUpdateAgentProductRelationStatus(@RequestParam(value = "id")
    String id, @RequestParam(value = "status")
    String status, ModelMap model)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        try
        {
            String[] sid = id.split("_");
            for (String pid : sid)
            {
                agentProductRelationService.updateAgentProductRelationStatus(Long.valueOf(pid),
                    status);
            }
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            bl = Constant.TrueOrFalse.FALSE;
            LOGGER.error("[ProductController.batchUpdateAgentProductRelationStatus()][异常:"
                         + e.getMessage() + "]");
        }
        return bl;
    }

    /**
     * 开启关闭
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchUpdateSupplyProductRelationStatus")
    @ResponseBody
    public String batchUpdateSupplyProductRelationStatus(@RequestParam(value = "id")
    String id, @RequestParam(value = "status")
    String status, ModelMap model)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        try
        {
            String[] sid = id.split("_");
            for (String pid : sid)
            {
                supplyProductRelationService.updateSupplyProductRelationStatus(Long.valueOf(pid),
                    status);
            }
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            bl = Constant.TrueOrFalse.FALSE;
            LOGGER.error("[ProductController.batchUpdateSupplyProductRelationStatus()][异常:"
                         + e.getMessage() + "]");
        }
        return bl;
    }

    @RequestMapping(value = "/deleteSupplyProductRelationById")
    public String deleteSupplyProductRelationById(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "source")
    String source, @RequestParam(value = "id")
    Long id)
    {
        com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
        supplyProductRelationService.deleteSupplyProductRelationById(id, operator.getDisplayName());

        if (source.equalsIgnoreCase("list"))
        {
            return "redirect:/product/allSupplyProductRelation";
        }
        else
        {
            return "redirect:/Merchant/supplyProductRelationList?merchantId=" + merchantId;
        }
    }

    @RequestMapping(value = "/deleteAgentProductRelationById")
    public String deleteAgentProductRelationById(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "source")
    String source, @RequestParam(value = "id")
    Long id)
    {
        com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
        agentProductRelationService.deleteAgentProductRelationById(id, operator.getDisplayName());

        if (source.equalsIgnoreCase("list"))
        {
            return "redirect:/product/allAgentProductRelation";
        }
        else
        {
            return "redirect:/Merchant/agentProductRelationList?merchantId=" + merchantId;
        }
    }

    @RequestMapping(value = "/allSupplyProductRelation")
    public String allSupplyProductRelation(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, ProductRelationVO productRelation, @RequestParam(value = "businessType", defaultValue = "")
    String businessType,ModelMap model, ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (productRelation.getIdentityId() != null && !productRelation.getIdentityId().isEmpty()
            && !productRelation.getIdentityId().equals("-1"))
        {
            String identityType = IdentityType.MERCHANT.toString();
            searchParams.put(EntityConstant.SupplyProductRelation.IDENTITY_ID,
                productRelation.getIdentityId());
            searchParams.put(EntityConstant.SupplyProductRelation.IDENTITY_TYPE, identityType);
        }
        if (productRelation.getProvince() != null && !productRelation.getProvince().isEmpty()
            && !productRelation.getProvince().equals("-1"))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.PROVINCE,
                productRelation.getProvince());
        }
        if (productRelation.getParValue() != null && !productRelation.getParValue().isEmpty())
        {
            searchParams.put(EntityConstant.SupplyProductRelation.PAR_VALUE,
                productRelation.getParValue());
        }
        if (productRelation.getCarrierInfo() != null
            && !productRelation.getCarrierInfo().isEmpty()
            && !productRelation.getCarrierInfo().equals("-1"))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.CARRIER_NAME,
                productRelation.getCarrierInfo());
        }
        if (StringUtils.isNotBlank(businessType))
        {
        	searchParams.put(EntityConstant.SupplyProductRelation.BUSINESSTYPE, businessType);
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount()))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.DISCOUNT, productRelation.getDiscount());
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount2()))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.MAX_DISCOUNT, productRelation.getDiscount2());
        }
        
        BSort bsort = new BSort(BSort.Direct.DESC, "id");
        YcPage<SupplyProductRelation> page_list = supplyProductRelationService.querySupplyProductRelation(
            searchParams, pageNumber, pageSize, bsort);

        List<Merchant> upMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        List<MerchantProductLevel> merchantLevels = merchantLevelService.getAllMerchantProductLevel();

        model.addAttribute("merchantLevels", merchantLevels);
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("upMerchants", upMerchants);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("businessType", businessType + "");
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");

        // model.addAttribute("identityIdVo", productRelation.getIdentityId());
        // model.addAttribute("parValueVo", productRelation.getParValue());
        // model.addAttribute("carrierInfoVo", productRelation.getCarrierInfo());
        // model.addAttribute("provinceVo", productRelation.getProvince());

        model.addAttribute("productRelationVO", productRelation);

        return "product/allSupplyProductRelation";
    }

    @RequestMapping(value = "/allAgentProductRelation")
    public String allAgentProductRelation(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, ProductRelationVO productRelation,@RequestParam(value = "businessType", defaultValue = "")
    String businessType, ModelMap model, ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (productRelation.getIdentityId() != null && !productRelation.getIdentityId().isEmpty()
            && !productRelation.getIdentityId().equals("-1"))
        {
            String identityType = IdentityType.MERCHANT.toString();
            searchParams.put(EntityConstant.AgentProductRelation.IDENTITY_ID,
                productRelation.getIdentityId());
            searchParams.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, identityType);
        }
        if (productRelation.getProvince() != null && !productRelation.getProvince().isEmpty()
            && !productRelation.getProvince().equals("-1"))
        {
            searchParams.put(EntityConstant.AgentProductRelation.PROVINCE,
                productRelation.getProvince());
        }
        if (productRelation.getParValue() != null && !productRelation.getParValue().isEmpty())
        {
            searchParams.put(EntityConstant.AgentProductRelation.PAR_VALUE,
                productRelation.getParValue());
        }
        if (productRelation.getCarrierInfo() != null
            && !productRelation.getCarrierInfo().isEmpty()
            && !productRelation.getCarrierInfo().equals("-1"))
        {
            searchParams.put(EntityConstant.AgentProductRelation.CARRIER_NAME,
                productRelation.getCarrierInfo());
        }
        if (StringUtils.isNotBlank(businessType))
        {
        	searchParams.put(EntityConstant.AgentProductRelation.BUSINESSTYPE, businessType);
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount()))
        {
            searchParams.put(EntityConstant.AgentProductRelation.DISCOUNT, productRelation.getDiscount());
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount2()))
        {
            searchParams.put(EntityConstant.AgentProductRelation.MAX_DISCOUNT, productRelation.getDiscount2());
        }
        
        BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.AgentProductRelation.PAR_VALUE);
        YcPage<AgentProductRelation> page_list = agentProductRelationService.queryAgentProductRelation(
            searchParams, pageNumber, pageSize, bsort);

        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();

        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("downMerchants", downMerchants);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("businessType", businessType + "");
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");

        // model.addAttribute("identityIdVo", productRelation.getIdentityId());
        // model.addAttribute("parValueVo", productRelation.getParValue());
        // model.addAttribute("carrierInfoVo", productRelation.getCarrierInfo());
        // model.addAttribute("provinceVo", productRelation.getProvince());

        model.addAttribute("productRelationVO", productRelation);
        return "product/allAgentProductRelation";
    }

    @RequestMapping(value = "/toEditSupplyProductRelations")
    @ResponseBody
    public String toEditSupplyProductRelations(@RequestParam(value = "upProductData")
    String upProductData, ModelMap model, ServletRequest request)
    {

        LOGGER.debug("[开始ProductController:toEditSupplyProductRelations(upProductData:"
                     + upProductData + ")]");
        String bl = Constant.TrueOrFalse.FALSE;
        List<SupplyProductRelation> upProductRelationList = new ArrayList<SupplyProductRelation>();
        com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
        if (!StringUtil.isNullOrEmpty(upProductData))
        {
            String[] upProductStr = upProductData.split("__");
            for (String upPstr : upProductStr)
            {
                String[] upProduct = upPstr.split("_");
                SupplyProductRelation upProductRelation = new SupplyProductRelation();
                upProductRelation.setId(Long.valueOf(upProduct[0]));
                BigDecimal uPbd = null;
                try
                {
                    uPbd = new BigDecimal(upProduct[1]);
                }
                catch (Exception e)
                {
                    LOGGER.error("[ProductController: toEditSupplyProductRelations(upProductData:"
                                 + upProductData + ")][报错:" + e.getMessage() + "]");
                }

                upProductRelation.setDiscount(uPbd);
                upProductRelation.setProductId(Long.valueOf(upProduct[2]));
                upProductRelation.setMerchantLevel(Long.valueOf(upProduct[3]));
                upProductRelationList.add(upProductRelation);
            }
        }

        try
        {
            for (SupplyProductRelation upr : upProductRelationList)
            {
                SupplyProductRelation upPr = supplyProductRelationService.querySupplyProductRelationById(upr.getId());
                AirtimeProduct airtimeProduct=productService.findOne(upr.getProductId());
                upPr.setPrice(airtimeProduct.getDisplayValue().multiply(upr.getDiscount()));
                upPr.setDiscount(upr.getDiscount());
                supplyProductRelationService.editSupplyProductRelation(upPr, operator.getDisplayName());
            }
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            bl = Constant.TrueOrFalse.FALSE;
        }
        LOGGER.debug("结束[ProductController:toEditSupplyProductRelations(upProductData:"
                     + upProductData + ")]");
        return bl;
    }

    @RequestMapping(value = "/updateSupplyProductRelationLevel")
    public String updateSupplyProductRelationLevel(@RequestParam(value = "level")
    String level, ModelMap model, ServletRequest request)
    {
        List<SupplyProductRelation> upProductRelationList = new ArrayList<SupplyProductRelation>();
        com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
        if (!StringUtil.isNullOrEmpty(level))
        {
            String[] levels = level.split("__");
            for (String mlevel : levels)
            {
                String[] upProduct = mlevel.split("_");
                SupplyProductRelation upProductRelation = new SupplyProductRelation();
                upProductRelation.setId(Long.valueOf(upProduct[0]));
                upProductRelation.setMerchantLevel(Long.valueOf(upProduct[1]));
                upProductRelationList.add(upProductRelation);
            }
        }

        try
        {
            for (SupplyProductRelation upr : upProductRelationList)
            {
                SupplyProductRelation upPr = supplyProductRelationService.querySupplyProductRelationById(upr.getId());
                upPr.setMerchantLevel(upr.getMerchantLevel());
                supplyProductRelationService.editSupplyProductRelation(upPr, operator.getDisplayName());

            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/allSupplyProductRelation");
            model.put("next_msg", "供货商产品列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.updateSupplyProductRelationLevel()][异常:"
                         + e.getMessage() + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/toEditAgentProductRelations")
    @ResponseBody
    public String toEditAgentProductRelations(@RequestParam(value = "dProductData")
    String dProductData, ModelMap model, ServletRequest request)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        List<AgentProductRelation> dProductRelationList = new ArrayList<AgentProductRelation>();
        com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
        if (!StringUtil.isNullOrEmpty(dProductData))
        {
            String[] dProductStr = dProductData.split("__");
            for (String dPstr : dProductStr)
            {
                String[] dProduct = dPstr.split("_");
                AgentProductRelation downProductRelation = new AgentProductRelation();
                downProductRelation.setId(Long.valueOf(dProduct[0]));
                BigDecimal dPbd = null;
                try
                {
                    dPbd = new BigDecimal(dProduct[1]);
                }
                catch (Exception e)
                {
                    LOGGER.error("[ProductController: toEditAgentProductRelations(upProductData:"
                                 + dProductData + ")][报错:" + e.getMessage() + "]");
                }
                downProductRelation.setDiscount(dPbd);
                downProductRelation.setProductId(Long.valueOf(dProduct[2]));
                dProductRelationList.add(downProductRelation);
            }
        }

        try
        {
            for (AgentProductRelation dr : dProductRelationList)
            {
                AgentProductRelation dPr = agentProductRelationService.queryAgentProductRelationById(dr.getId());
                AirtimeProduct airtimeProduct=productService.findOne(dr.getProductId());
                dPr.setPrice(airtimeProduct.getDisplayValue().multiply(dr.getDiscount()));
                dPr.setDiscount(dr.getDiscount());
                agentProductRelationService.editAgentProductRelation(dPr, operator.getDisplayName());
            }
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (Exception e)
        {
            bl = Constant.TrueOrFalse.FALSE;
            LOGGER.error("[ProductController.toEditAgentProductRelations()][异常:" + e.getMessage()
                         + "]");
        }
        return bl;
    }

    @RequestMapping(value = "/getAllMerchants")
    @ResponseBody
    public String getAllMerchants(@RequestParam(value = "merchantType")
    String merchantType, Model model)
    {
        StringBuffer sb = new StringBuffer();
        List<Merchant> merchants = new ArrayList<Merchant>();
        if (MerchantType.AGENT.toString().equalsIgnoreCase(merchantType))
        {
            merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
        }
        else if (MerchantType.SUPPLY.toString().equalsIgnoreCase(merchantType))
        {
            merchants = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
        }
        for (Merchant merchant : merchants)
        {
            sb.append(merchant.getId() + "," + merchant.getMerchantName() + "|");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @RequestMapping(value = "/toCloneSupplyMerchantProduct")
    public String toCloneSupplyMerchantProduct(Model model)
    {
        List<Merchant> upMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("upMerchants", upMerchants);
        return "product/toCloneSupplyMerchantProduct";
    }

    @RequestMapping(value = "/toCloneAgentMerchantProduct")
    public String toCloneAgentMerchantProduct(Model model)
    {
        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("downMerchants", downMerchants);
        return "product/toCloneAgentMerchantProduct";
    }

    @RequestMapping(value = "/cloneSupplyMerchantProduct")
    public String cloneUpMerchantProduct(@RequestParam(value = "newMerchantId", defaultValue = "-1")
                                         Long newMerchantId,
                                         @RequestParam(value = "oldMerchantId", defaultValue = "-1")
                                         Long oldMerchantId,
                                         @RequestParam(value = "productIds", defaultValue = "-1")
                                         String productIds, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            supplyProductRelationService.cloneSupplyMerchantProduct(newMerchantId, oldMerchantId,
                productIds,operator.getDisplayName());
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/allSupplyProductRelation");
            model.put("next_msg", "供货商产品列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.cloneUpMerchantProduct()][异常:" + e.getMessage() + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/cloneAgentMerchantProduct")
    public String cloneAgentMerchantProduct(@RequestParam(value = "newMerchantId", defaultValue = "-1")
                                            Long newMerchantId,
                                            @RequestParam(value = "oldMerchantId", defaultValue = "-1")
                                            Long oldMerchantId,
                                            @RequestParam(value = "productIds", defaultValue = "-1")
                                            String productIds, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            agentProductRelationService.cloneAgentMerchantProduct(newMerchantId, oldMerchantId,
                productIds,operator.getDisplayName());
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "product/allAgentProductRelation");
            model.put("next_msg", "代理商产品列表");
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.cloneAgentMerchantProduct()][异常:" + e.getMessage()
                         + "]");
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/getSupplyMerchantProductList")
    @ResponseBody
    public String getUpMerchantProductList(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "carrierInfo", defaultValue = "")
    String carrierInfo, @RequestParam(value = "businessType", defaultValue = "")
    String businessType, @RequestParam(value = "province", defaultValue = "")
    String province, @RequestParam(value = "city", defaultValue = "")
    String city, @RequestParam(value = "faceValue")
    String faceValue, @RequestParam(value = "faceValue2")
    String faceValue2, @RequestParam(value = "isCommonUse", defaultValue = "")
    String isCommonUse, @RequestParam(value = "newMerchantId", defaultValue = "")
    String newMerchantId, Model model, ServletRequest request)
    {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put(EntityConstant.AgentProductRelation.IDENTITY_ID, merchantId);
        searchParams.put(EntityConstant.AirtimeProduct.CARRIER_NAME, carrierInfo);
        searchParams.put(EntityConstant.AirtimeProduct.PROVINCE, province);
        searchParams.put(EntityConstant.AirtimeProduct.CITY, city);
        searchParams.put(EntityConstant.AirtimeProduct.PARVALUE, faceValue);
        searchParams.put(EntityConstant.AirtimeProduct.PARVALUE + "2", faceValue2);
        searchParams.put(EntityConstant.AirtimeProduct.IS_COMMON_USE, isCommonUse);
        searchParams.put(EntityConstant.AirtimeProduct.BUSINESS_TYPE, businessType);
        searchParams.put("newMerchantId", newMerchantId);
        List<SupplyProductRelation> upProductRelations = supplyProductRelationService.getProductRelationByParams(searchParams);

        for (SupplyProductRelation upProductRelation : upProductRelations)
        {
            sb.append("<div class=\"fl pd10\"><input type=\"checkbox\" name=\"ids\" value=\""
                      + upProductRelation.getId() + "\" >" + upProductRelation.getProductName()
                      + "</input></div>");
        }
        return sb.toString();
    }

    @RequestMapping(value = "/getAgentMerchantProductList")
    @ResponseBody
    public String getAgentMerchantProductList(@RequestParam(value = "merchantId")
    Long merchantId, @RequestParam(value = "carrierInfo", defaultValue = "")
    String carrierInfo, @RequestParam(value = "businessType", defaultValue = "")
    String businessType, @RequestParam(value = "province", defaultValue = "")
    String province, @RequestParam(value = "city", defaultValue = "")
    String city, @RequestParam(value = "faceValue")
    String faceValue, @RequestParam(value = "faceValue2")
    String faceValue2, @RequestParam(value = "isCommonUse", defaultValue = "")
    String isCommonUse, @RequestParam(value = "newMerchantId", defaultValue = "")
    String newMerchantId, Model model, ServletRequest request)
    {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put(EntityConstant.AgentProductRelation.IDENTITY_ID, merchantId);
        searchParams.put(EntityConstant.AirtimeProduct.CARRIER_NAME, carrierInfo);
        searchParams.put(EntityConstant.AirtimeProduct.PROVINCE, province);
        searchParams.put(EntityConstant.AirtimeProduct.CITY, city);
        searchParams.put(EntityConstant.AirtimeProduct.PARVALUE, faceValue);
        searchParams.put(EntityConstant.AirtimeProduct.PARVALUE + "2", faceValue2);
        searchParams.put(EntityConstant.AirtimeProduct.IS_COMMON_USE, isCommonUse);
        searchParams.put("newMerchantId", newMerchantId);
        searchParams.put(EntityConstant.AirtimeProduct.BUSINESS_TYPE, businessType);
        

        List<AgentProductRelation> productRelations = agentProductRelationService.getProductRelationByParams(searchParams);
        for (AgentProductRelation downProductRelation : productRelations)
        {
            sb.append("<div class=\"fl pd10\"><input type=\"checkbox\" name=\"ids\" value=\""
                      + downProductRelation.getId() + "\">" + downProductRelation.getProductName()
                      + "</input></div>");
        }
        return sb.toString();
    }

    /**
     * 批量页面
     * 
     * @return
     */
    @RequestMapping(value = "/changesSupplyProductRelation")
    public String changesSupplyProductRelation(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, ProductRelationVO productRelation,@RequestParam(value = "businessType", defaultValue = "")
    String businessType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (productRelation.getIdentityId() != null && !productRelation.getIdentityId().isEmpty()
            && !productRelation.getIdentityId().equals("-1"))
        {
            String identityType = IdentityType.MERCHANT.toString();
            searchParams.put(EntityConstant.SupplyProductRelation.IDENTITY_ID,
                productRelation.getIdentityId());
            searchParams.put(EntityConstant.SupplyProductRelation.IDENTITY_TYPE, identityType);
        }
        if (productRelation.getProvince() != null && !productRelation.getProvince().isEmpty()
            && !productRelation.getProvince().equals("-1"))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.PROVINCE,
                productRelation.getProvince());
        }
        if (productRelation.getParValue() != null && !productRelation.getParValue().isEmpty())
        {
            searchParams.put(EntityConstant.SupplyProductRelation.PAR_VALUE,
                productRelation.getParValue());
        }
        if (productRelation.getCarrierInfo() != null
            && !productRelation.getCarrierInfo().isEmpty()
            && !productRelation.getCarrierInfo().equals("-1"))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.CARRIER_NAME,
                productRelation.getCarrierInfo());
        }
        if (StringUtils.isNotBlank(businessType))
        {
        	searchParams.put(EntityConstant.SupplyProductRelation.BUSINESSTYPE, businessType);
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount()))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.DISCOUNT, productRelation.getDiscount());
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount2()))
        {
            searchParams.put(EntityConstant.SupplyProductRelation.MAX_DISCOUNT, productRelation.getDiscount2());
        }
        
        BSort bsort = new BSort(BSort.Direct.DESC, "id");
        YcPage<SupplyProductRelation> page_list = supplyProductRelationService.querySupplyProductRelation(
            searchParams, pageNumber, pageSize, bsort);

        List<SupplyProductRelationVo> srvos = new ArrayList<SupplyProductRelationVo>();
        try
        {
            for (SupplyProductRelation supplyProductRelation : page_list.getList())
            {
                SupplyProductRelationVo srvo = new SupplyProductRelationVo();
                BeanUtils.copyProperties(srvo, supplyProductRelation);
                srvo.setAirtimeProduct(productPageQuery.queryAirtimeProductById(supplyProductRelation.getProductId()));
                srvos.add(srvo);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("[ProductController: changesSupplyProductRelation()][异常:" + e.getMessage()
                + "]");
        }

        List<Merchant> upMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        List<MerchantProductLevel> merchantLevels = merchantLevelService.getAllMerchantProductLevel();
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("upMerchants", upMerchants);
        model.addAttribute("merchantLevels", merchantLevels);
        model.addAttribute("productRelation", productRelation);
        model.addAttribute("upMerchants", upMerchants);
        model.addAttribute("mlist", srvos);
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("businessType", businessType+"");
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "product/changesSupplyProductRelation";
    }

    @RequestMapping(value = "/changesAgentProductRelation")
    public String changesAgentProductRelation(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, ProductRelationVO productRelation,@RequestParam(value = "businessType", defaultValue = "")
    String businessType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (productRelation.getIdentityId() != null && !productRelation.getIdentityId().isEmpty()
            && !productRelation.getIdentityId().equals("-1"))
        {
            String identityType = IdentityType.MERCHANT.toString();
            searchParams.put(EntityConstant.AgentProductRelation.IDENTITY_ID,
                productRelation.getIdentityId());
            searchParams.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, identityType);
        }
        if (productRelation.getProvince() != null && !productRelation.getProvince().isEmpty()
            && !productRelation.getProvince().equals("-1"))
        {
            searchParams.put(EntityConstant.AgentProductRelation.PROVINCE,
                productRelation.getProvince());
        }
        if (productRelation.getParValue() != null && !productRelation.getParValue().isEmpty())
        {
            searchParams.put(EntityConstant.AgentProductRelation.PAR_VALUE,
                productRelation.getParValue());
        }
        if (productRelation.getCarrierInfo() != null
            && !productRelation.getCarrierInfo().isEmpty()
            && !productRelation.getCarrierInfo().equals("-1"))
        {
            searchParams.put(EntityConstant.AgentProductRelation.CARRIER_NAME,
                productRelation.getCarrierInfo());
        }
        if (StringUtils.isNotBlank(businessType))
        {
        	searchParams.put(EntityConstant.AgentProductRelation.BUSINESSTYPE, businessType);
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount()))
        {
            searchParams.put(EntityConstant.AgentProductRelation.DISCOUNT, productRelation.getDiscount());
        }
        
        if (StringUtils.isNotBlank(productRelation.getDiscount2()))
        {
            searchParams.put(EntityConstant.AgentProductRelation.MAX_DISCOUNT, productRelation.getDiscount2());
        }
        
        BSort bsort = new BSort(BSort.Direct.DESC, "id");
        YcPage<AgentProductRelation> page_list = agentProductRelationService.queryAgentProductRelation(
            searchParams, pageNumber, pageSize, bsort);
        List<AgentProductRelationVo> arVos = new ArrayList<AgentProductRelationVo>();
        try
        {
            for (AgentProductRelation agentProductRelation : page_list.getList())
            {
                AgentProductRelationVo arvo = new AgentProductRelationVo();

                BeanUtils.copyProperties(arvo, agentProductRelation);
                arvo.setAirtimeProduct(productPageQuery.queryAirtimeProductById(agentProductRelation.getProductId()));
                arVos.add(arvo);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("[ProductController:changesAgentProductRelation()][异常:" + e.getMessage()
                         + "]");
        }
        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();

        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);
        model.addAttribute("downMerchants", downMerchants);
        model.addAttribute("productRelation", productRelation);
        model.addAttribute("mlist", arVos);
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("businessType", businessType+"");
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "product/changesAgentProductRelation";
    }

    @RequestMapping(value = "/toShowSelectProduct")
    @ResponseBody
    public List<AirtimeProduct> toShowSelectProduct(@RequestParam(value = "businessTypeQuery", defaultValue = "") String businessTypeQuery,
    												@RequestParam(value = "carrierNameQuery", defaultValue = "")
                                                    String carrierNameQuery,
                                                    @RequestParam(value = "provinceQuery", defaultValue = "")
                                                    String provinceQuery,
                                                    @RequestParam(value = "parValueQuery", defaultValue = "")
                                                    String parValueQuery,
                                                    @RequestParam(value = "merchantId", defaultValue = "")
                                                    String merchantId, ModelMap model)
    {
        AirtimeProduct ap = new AirtimeProduct();
        ap.setCarrierName(carrierNameQuery);
        ap.setProvince(provinceQuery);
        if (!parValueQuery.isEmpty())
        {
            ap.setParValue(new BigDecimal(parValueQuery));
        }
        List<AirtimeProduct> products = new ArrayList<AirtimeProduct>();
        products = productPageQuery.fuzzyQueryAirtimeProductsByParams(provinceQuery,
            parValueQuery, carrierNameQuery, null,businessTypeQuery);

        List<AirtimeProduct> subProducts = new ArrayList<AirtimeProduct>();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (StringUtil.isNotBlank(ap.getCarrierName())
            && ap.getCarrierName().length() > 0)
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.AgentProductRelation.CARRIER_NAME,
                             ap.getCarrierName());
        }
        if (StringUtil.isNotBlank(ap.getProvince())
            && ap.getProvince().length() > 0)
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.AgentProductRelation.PROVINCE,
                             ap.getProvince());
        }
        if (StringUtil.isNotBlank(ap.getCity())
            && ap.getCity().length() > 0)
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.AgentProductRelation.CITY,
                             ap.getCity());
        }
        if (BeanUtils.isNotNull(ap.getParValue()))
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.AgentProductRelation.PAR_VALUE,
                             ap.getParValue().toString());
        }
        Merchant merchant = merchantService.queryMerchantById(Long.valueOf(merchantId));
        if (MerchantType.AGENT.equals(merchant.getMerchantType()))
        {
            List<AgentProductRelation> agentProductRelations = agentProductRelationService.getAllAgentProductRelationService(
                searchParams,Long.valueOf(merchantId), IdentityType.MERCHANT.toString());
            for (AgentProductRelation agentProductRelation : agentProductRelations)
            {
                AirtimeProduct product=productService.findOne(agentProductRelation.getProductId());
                subProducts.add(product);
            }
        }
        else
        {
            List<SupplyProductRelation> supplyProductRelations = supplyProductRelationService.getAllProductBySupplyMerchantId(
                searchParams, Long.valueOf(merchantId), IdentityType.MERCHANT.toString(), null);
            for (SupplyProductRelation supplyProductRelation : supplyProductRelations)
            {
                AirtimeProduct product=productService.findOne(supplyProductRelation.getProductId());
                subProducts.add(product);
            }
        }
        products.removeAll(subProducts);
        return products;
    }

    @RequestMapping(value = "/deleteAgentProductRelationList")
    @ResponseBody
    public String deleteAgentProductRelationList(@RequestParam(value = "ids")
    String ids, ModelMap model)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
        try
        {
            String[] sid = ids.split(Constant.StringSplitUtil.DECODE);
            for (String pid : sid)
            {
                agentProductRelationService.deleteAgentProductRelationById(Long.valueOf(pid), operator.getDisplayName());
            }
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.deleteAgentProductRelationList()][异常:"
                         + e.getMessage() + "]");
            bl = Constant.TrueOrFalse.FALSE;
        }
        return bl;

    }

    @RequestMapping(value = "/deleteSupplyProductRelationList")
    @ResponseBody
    public String deleteSupplyProductRelationList(@RequestParam(value = "ids")
    String ids, ModelMap model)
    {
        String bl = Constant.TrueOrFalse.FALSE;
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator operator = getLoginUser();
            String[] sid = ids.split(Constant.StringSplitUtil.DECODE);
            for (String pid : sid)
            {
                supplyProductRelationService.deleteSupplyProductRelationById(Long.valueOf(pid), operator.getDisplayName());
            }
            bl = Constant.TrueOrFalse.TRUE;
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProductController.deleteSupplyProductRelationList()][异常:"
                         + e.getMessage() + "]");
            bl = Constant.TrueOrFalse.FALSE;
        }
        return bl;
    }
}
