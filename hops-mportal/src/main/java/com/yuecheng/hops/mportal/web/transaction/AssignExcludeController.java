package com.yuecheng.hops.mportal.web.transaction;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
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
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.config.entify.product.AssignExclude;
import com.yuecheng.hops.transaction.config.product.AssignExcludeService;


@Controller
@RequestMapping(value = "/AssignExclude")
public class AssignExcludeController
{
    @Autowired
    private AssignExcludeService assignExcludeService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    private ProductPageQuery productPageQuery;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    IdentityService identityService;

    private static Logger logger = LoggerFactory.getLogger(AssignExcludeController.class);

    /**
     * 进入商户分组添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addAssignExclude", method = RequestMethod.GET)
    public String addAssignExclude(ModelMap model)
    {
        try
        {
            List<Merchant> upMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
            List<Merchant> downMerchant = merchantService.queryAllMerchant(MerchantType.AGENT,null);
            List<Province> province = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfo = carrierInfoService.getAllCarrierInfo();
            model.addAttribute("province", province);
            model.addAttribute("carrierInfo", carrierInfo);
            model.addAttribute("upMerchant", upMerchant);
            model.addAttribute("downMerchant", downMerchant);
            return PageConstant.PAGE_ASSIGN_EXCLUDE_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[AssignExcludeController:addAssignExclude(GET)]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/addProductAssignExclude", method = RequestMethod.GET)
    public String addProductAssignExclude(ModelMap model)
    {
        try
        {
            List<Merchant> upMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
            List<Merchant> downMerchant = merchantService.queryAllMerchant(MerchantType.AGENT,null);
            List<Province> province = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfo = carrierInfoService.getAllCarrierInfo();
            model.addAttribute("province", province);
            model.addAttribute("carrierInfo", carrierInfo);
            model.addAttribute("upMerchant", upMerchant);
            model.addAttribute("downMerchant", downMerchant);
            return PageConstant.PAGE_ASSIGN_EXCLUDE_ADD_PRODUCT;
        }
        catch (RpcException e)
        {
            logger.debug("[AssignExcludeController:addAssignExclude(GET)]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
    /**
     * 获取城市列表
     * 
     * @return
     */
    @RequestMapping(value = "/addCity", method = RequestMethod.POST)
    @ResponseBody
    public String addCity(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.info("[AssignExcludeController:addCity(" + id + ")]");
            List<City> citylist = cityService.getCityByProvince(id);
            int i = 0;
            String result = "";
            while (i < citylist.size())
            {
                City city = citylist.get(i);
                result = result + city.getCityId() + "*" + city.getCityName() + "|";
                i++ ;
            }
            return result;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:addCity()]" + e.getMessage());
            return null;
        }
    }
    
    /**
     * 获取产品列表
     * 
     * @return
     */
    @RequestMapping(value = "/getProductByMerchant")
    @ResponseBody
    public String getProductByMerchant(@RequestParam(value = "businessNo", defaultValue = "") String businessNo,
                             @RequestParam(value = "mid", defaultValue = "") Long mid,
                             @RequestParam(value = "type", defaultValue = "") String type,
                             @RequestParam(value = "remid", defaultValue = "") Long remid,
                             @RequestParam(value = "carrier", defaultValue = "") String carrier,
                             @RequestParam(value = "province", defaultValue = "") String province,
                             @RequestParam(value = "city", defaultValue = "") String city,
                             @RequestParam(value = "ruleType", defaultValue = "") String ruleType)
    {
        try
        {
                logger.info("[AssignExcludeController:getProductByMerchant(" + mid + ")]");
            if (type != null && !type.isEmpty())
            {
                Map<String, Object> searchParams = new HashMap<String, Object>();
                if (!StringUtil.isNullOrEmpty(carrier) && !carrier.equals("0"))
                {
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.SupplyProductRelation.CARRIER_NAME, carrier);
                }
                if (!StringUtil.isNullOrEmpty(province) && !province.equals("0"))
                {
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.SupplyProductRelation.PROVINCE, province);
                }
                if (!StringUtil.isNullOrEmpty(city) && !city.equals("0"))
                {
                    searchParams.put(
                        Operator.EQ + "_" + EntityConstant.SupplyProductRelation.CITY, city);
                }
                if (!BeanUtils.isNullOrEmpty(businessNo))
                {
                    String businessType=businessNo.equals(Constant.BusinessNo.HU_FEI)?
                                        Constant.BusinessType.BUSINESS_TYPE_HF:businessNo.equals(Constant.BusinessType.BUSINESS_TYPE_FLOW)?
                                        Constant.BusinessType.BUSINESS_TYPE_FLOW:Constant.BusinessType.BUSINESS_TYPE_FIXED;
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.SupplyProductRelation.BUSINESSTYPE,businessType );
                }
                searchParams.put(
                    Operator.EQ + "_" + EntityConstant.SupplyProductRelation.IDENTITY_TYPE, IdentityType.MERCHANT.toString());
//                searchParams.put(Operator.EQ + "_" + EntityConstant.SupplyProductRelation.STATUS,
//                    Constant.AgentProductStatus.OPEN_STATUS);
                if (type.equals(MerchantType.SUPPLY.toString()))
                {
                    return getAgentProducts(searchParams, mid, remid,ruleType);
                }
                else
                {
                    return getSupplyProducts(searchParams, mid, remid,ruleType);
                }
            }
            return null;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:getProduct()]" + e.getMessage());
            return null;
        }
    }
    /**
     * 供货商指定（排除）代理商，根据条件获取能匹配的产品
     * 
     * @param searchParams  过滤条件
     * @param identityId    作用商户ID
     * @param remid         被作用商户ID
     * @param ruleType      类型（指定、排除）
     * @return 
     * @see
     */
    public String getAgentProducts(Map<String, Object> searchParams,Long identityId,Long remid,String ruleType)
    {
        List<SupplyProductRelation> upProductList =supplyProductRelationService.getAllProductBySupplyMerchantId(
            searchParams, identityId, IdentityType.MERCHANT.toString(),null);
        Map<String,SupplyProductRelation> map=new HashMap<String,SupplyProductRelation>();
        Set<AgentProductRelation> set=new HashSet<AgentProductRelation>();
        String carrierNo=(String)searchParams.get(Operator.EQ + "_" + EntityConstant.SupplyProductRelation.CARRIER_NAME);
        String proviceNo=(String)searchParams.get(Operator.EQ + "_" + EntityConstant.SupplyProductRelation.PROVINCE);
        String cityNo=(String)searchParams.get(Operator.EQ + "_" + EntityConstant.SupplyProductRelation.CITY);
        //如为supply则需要将作用商户和被作用商户互换
        List<AssignExclude> assigenExcludes=getConfigAssignExclude(carrierNo, proviceNo, cityNo, remid,identityId, ruleType,MerchantType.SUPPLY.toString());
        //获取反向指定排除数据
        List<AssignExclude> unAssigenExcludes=getConfigAssignExclude(carrierNo, proviceNo, cityNo, remid,identityId, Constant.AssignExclude.RULE_TYPE_ASSIGN.equals(ruleType)?Constant.AssignExclude.RULE_TYPE_EXCLUDE:Constant.AssignExclude.RULE_TYPE_ASSIGN,MerchantType.SUPPLY.toString());
        
        for (SupplyProductRelation supplyProductRelation : upProductList)
        {
            //供货商指定代理商的情况不允许找出代理商父级产品
            String key=supplyProductRelation.getCarrierName()+supplyProductRelation.getProvince()+supplyProductRelation.getCity()+supplyProductRelation.getParValue().toString();
            SupplyProductRelation temp=map.get(key);
            if(BeanUtils.isNull(temp)){
                searchParams = new HashMap<String, Object>();
                if (!StringUtil.isNullOrEmpty(supplyProductRelation.getCarrierName()) && !supplyProductRelation.getCarrierName().equals("0"))
                {
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.AgentProductRelation.CARRIER_NAME, supplyProductRelation.getCarrierName());
                }
                if (!StringUtil.isNullOrEmpty(supplyProductRelation.getProvince()) && !supplyProductRelation.getProvince().equals("0"))
                {
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.AgentProductRelation.PROVINCE, supplyProductRelation.getProvince());
                }else{
                    searchParams.put(Operator.EQ + "_"
                        + EntityConstant.AgentProductRelation.PROVINCE, StringUtil.initString());
                }
                if (!StringUtil.isNullOrEmpty(supplyProductRelation.getCity()) && !supplyProductRelation.getCity().equals("0"))
                {
                    searchParams.put(
                        Operator.EQ + "_" + EntityConstant.AgentProductRelation.CITY, supplyProductRelation.getCity());
                }else{
                    searchParams.put(Operator.EQ + "_"
                        + EntityConstant.AgentProductRelation.CITY, StringUtil.initString());
                }
                if (!StringUtil.isNullOrEmpty(supplyProductRelation.getParValue().toString()) )
                {
                    searchParams.put(
                        Operator.EQ + "_" + EntityConstant.AgentProductRelation.PAR_VALUE, supplyProductRelation.getParValue().toString());
                }
                List<AgentProductRelation> downProductList =agentProductRelationService.getAllAgentProductRelation(
                    searchParams, remid, IdentityType.MERCHANT.toString());
                set.addAll(downProductList);
                map.put(key, supplyProductRelation);
            }
        }
        List<AgentProductRelation> list = new ArrayList<AgentProductRelation>(set);
        //去除掉排除（指定）过的产品
        list=removeAgentProducts(list, unAssigenExcludes,MerchantType.SUPPLY.toString(),Constant.AssignExclude.ASSIGN_EXCLUDE_PRODUCT);
        List<String[]> objList = assignExcludeService.getAllAgentProduct(
            list, assigenExcludes);

        return getConfigMerchant(objList);
        
    }
    /**
     * 代理商指定（排除）供货商，获取能匹配的产品
     * 
     * @param searchParams  过滤条件
     * @param identityId    作用商户ID
     * @param remid         被作用商户ID
     * @param ruleType      类型（指定、排除）
     * @return 
     * @see
     */
    public String getSupplyProducts(Map<String, Object> searchParams,Long identityId,Long remid,String ruleType)
    {
        List<AgentProductRelation> downProductList =agentProductRelationService.getAllAgentProductRelationService(
            searchParams, identityId, IdentityType.MERCHANT.toString());
        Set<SupplyProductRelation> set=new HashSet<SupplyProductRelation>();
        String carrierNo=(String)searchParams.get(Operator.EQ + "_" + EntityConstant.SupplyProductRelation.CARRIER_NAME);
        String proviceNo=(String)searchParams.get(Operator.EQ + "_" + EntityConstant.SupplyProductRelation.PROVINCE);
        String cityNo=(String)searchParams.get(Operator.EQ + "_" + EntityConstant.SupplyProductRelation.CITY);
        List<AssignExclude> assigenExcludes=getConfigAssignExclude(carrierNo, proviceNo, cityNo, identityId, remid, ruleType,MerchantType.AGENT.toString());
        //获取反向指定排除数据
        List<AssignExclude> unAssigenExcludes=getConfigAssignExclude(carrierNo, proviceNo, cityNo, identityId, remid, Constant.AssignExclude.RULE_TYPE_ASSIGN.equals(ruleType)?Constant.AssignExclude.RULE_TYPE_EXCLUDE:Constant.AssignExclude.RULE_TYPE_ASSIGN,MerchantType.AGENT.toString());
        List<SupplyProductRelation> upProductList =supplyProductRelationService.getProductRelationByIdentityId(remid);
        for (AgentProductRelation agentProductRelation : downProductList)
        {
            //代理商指定供货商的情况允许找出供货商父级产品，所以key不用加上了productId
            String province=StringUtils.isNotBlank(agentProductRelation.getProvince())?agentProductRelation.getProvince():StringUtil.initString();
            String city=StringUtils.isNotBlank(agentProductRelation.getCity())?agentProductRelation.getCity():StringUtil.initString();
            for (SupplyProductRelation supplyProductRelation : upProductList)
            {
                boolean carrierFlag=agentProductRelation.getCarrierName().equals(supplyProductRelation.getCarrierName());
                boolean parValueFlag=agentProductRelation.getParValue().equals(supplyProductRelation.getParValue());
                boolean provinceFlag=StringUtils.isBlank(supplyProductRelation.getProvince())||province.equals(supplyProductRelation.getProvince());
                boolean cityFlag=StringUtils.isBlank(supplyProductRelation.getCity())||city.equals(supplyProductRelation.getCity());
                if(carrierFlag&&provinceFlag&&cityFlag&&parValueFlag)
                {
                    set.add(supplyProductRelation);
                }
            }
        }
        

        List<SupplyProductRelation> list = new ArrayList<SupplyProductRelation>(set);
        //去除掉排除（指定）过的产品
        list=removeSupplyProducts(list,unAssigenExcludes,MerchantType.AGENT.toString(),Constant.AssignExclude.ASSIGN_EXCLUDE_PRODUCT);
        List<String[]> objList = assignExcludeService.getAllSupplyProduct(
            list, assigenExcludes);

        return getConfigMerchant(objList);
    }
    /**
     * 供货商指定代理商时移除掉被排除的产品（供货商排除代理商时移除掉被指定的产品）
     * 
     * @param list              代理商产品列表
     * @param assigenExcludes   被反向指定（排除）的列表
     * @return 
     * @see
     */
    public List<AgentProductRelation> removeAgentProducts(List<AgentProductRelation> list,List<AssignExclude> assigenExcludes,String merchantType,String assignExcludeType)
    {
        List<AgentProductRelation> agentProductRelations=new ArrayList<AgentProductRelation>();
        for (AgentProductRelation agentProductRelation : list)
        {
            boolean flag=true;
            for (AssignExclude assignExclude : assigenExcludes)
            {
                if(Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT.equals(assignExcludeType)&&MerchantType.AGENT.toString().equals(merchantType))
                {
                    flag=!assignExclude.getObjectMerchantId().equals(agentProductRelation.getIdentityId());
                }else if(Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT.equals(assignExcludeType)&&MerchantType.SUPPLY.toString().equals(merchantType)){
                    flag=!assignExclude.getMerchantId().equals(agentProductRelation.getIdentityId());
                }else if(Constant.AssignExclude.ASSIGN_EXCLUDE_PRODUCT.equals(assignExcludeType)&&assignExclude.getProductNo().equals(agentProductRelation.getProductId())){
                    flag=false;
                }
            }
            if(flag)
            {
                agentProductRelations.add(agentProductRelation);
            }
        }
        return agentProductRelations;
    }
    /**
     * 代理商指定供货商时移除掉被排除的产品（代理商排除指定供货商时移除掉被指定排除的产品）
     * 
     * @param list              供货商产品列表
     * @param assigenExcludes   被反向指定（排除）的列表
     * @return 
     * @see
     */
    public List<SupplyProductRelation> removeSupplyProducts(List<SupplyProductRelation> list,List<AssignExclude> assigenExcludes,String merchantType,String assignExcludeType)
    {
        List<SupplyProductRelation> supplyProductRelations=new ArrayList<SupplyProductRelation>();
        for (SupplyProductRelation supplyProductRelation : list)
        {
            boolean flag=true;
            for (AssignExclude assignExclude : assigenExcludes)
            {
                if(Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT.equals(assignExcludeType)&&MerchantType.AGENT.toString().equals(merchantType))
                {
                    flag=!assignExclude.getObjectMerchantId().equals(supplyProductRelation.getIdentityId());
                }else if(Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT.equals(assignExcludeType)&&MerchantType.SUPPLY.toString().equals(merchantType)){
                    flag=!assignExclude.getMerchantId().equals(supplyProductRelation.getIdentityId());
                }else if(Constant.AssignExclude.ASSIGN_EXCLUDE_PRODUCT.equals(assignExcludeType)&&assignExclude.getProductNo().equals(supplyProductRelation.getProductId())){
                    flag=false;
                }
            }
            if(flag)
            {
                supplyProductRelations.add(supplyProductRelation);
            }
        }
        return supplyProductRelations;
    }
    /**
     * 根据条件获取已配置的指定排除信息
     * 
     * @param carrierNo     运营商
     * @param proviceNo     省份
     * @param cityNo        城市
     * @param identityId    作用商户ID
     * @param remid         被作用商户ID
     * @param ruleType      类型（指定、排除）
     * @return 
     * @see
     */
    public List<AssignExclude> getConfigAssignExclude(String carrierNo,String proviceNo,String cityNo,Long identityId,Long remid,String ruleType,String type)
    {
        Map<String, Object> assigenExcludeSearchParams=new HashMap<String,Object>();
        List<AssignExclude> assigenExcludes=new ArrayList<AssignExclude>();
        if (ruleType != null)
        {
            assigenExcludeSearchParams.put(EntityConstant.AssignExclude.RULE_TYPE, ruleType);
        }
        if (BeanUtils.isNotNull(identityId))
        {
            assigenExcludeSearchParams.put(EntityConstant.AssignExclude.MERCHANT_ID, identityId.toString());
        }
        if (BeanUtils.isNotNull(remid))
        {
            assigenExcludeSearchParams.put(EntityConstant.AssignExclude.OBJECT_MERCHANT_ID, remid.toString());
        }
        if (StringUtils.isNotBlank(carrierNo)&& !carrierNo.equals(Constant.Common.ZERO))
        {
            assigenExcludeSearchParams.put(EntityConstant.AssignExclude.CARRIER_NO,carrierNo);
        }
        if (StringUtils.isNotBlank(proviceNo)&& !proviceNo.equals(Constant.Common.ZERO))
        {
            assigenExcludeSearchParams.put(EntityConstant.AssignExclude.PROVICE_NO,proviceNo);
        }
        if (StringUtils.isNotBlank(cityNo)&& !cityNo.equals(Constant.Common.ZERO))
        {
            assigenExcludeSearchParams.put(EntityConstant.AssignExclude.CITY_NO,cityNo);
        }
        if (StringUtils.isNotBlank(type))
        {
            assigenExcludeSearchParams.put(EntityConstant.AssignExclude.MERCHANT_TYPE,type);
        }
        assigenExcludes=assignExcludeService.getAllAssignExcludeRelation(assigenExcludeSearchParams);
        return BeanUtils.isNotNull(assigenExcludes)?assigenExcludes:new ArrayList<AssignExclude>();
    }
    
    /**
     * 获取产品列表
     * 
     * @return
     */
    @RequestMapping(value = "/getProduct")
    @ResponseBody
    public String getProduct(@RequestParam(value = "businessNo", defaultValue = "") String businessNo,
                             @RequestParam(value = "mid", defaultValue = "") Long mid,
                             @RequestParam(value = "type", defaultValue = "") String type,
                             @RequestParam(value = "carrier", defaultValue = "") String carrier,
                             @RequestParam(value = "province", defaultValue = "") String province,
                             @RequestParam(value = "city", defaultValue = "") String city)
    {
        try
        {
                logger.info("[AssignExcludeController:getProduct(" + mid + ")]");
            if (type != null && !type.isEmpty())
            {
                Map<String, Object> searchParams = new HashMap<String, Object>();
                if (!StringUtil.isNullOrEmpty(carrier) && !carrier.equals("0"))
                {
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.SupplyProductRelation.CARRIER_NAME, carrier);
                }
                if (!StringUtil.isNullOrEmpty(province) && !province.equals("0"))
                {
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.SupplyProductRelation.PROVINCE, province);
                }
                if (!StringUtil.isNullOrEmpty(city) && !city.equals("0"))
                {
                    searchParams.put(
                        Operator.EQ + "_" + EntityConstant.SupplyProductRelation.CITY, city);
                }
                if (!BeanUtils.isNullOrEmpty(businessNo))
                {
                    String businessType=businessNo.equals(Constant.BusinessNo.HU_FEI)?
                        Constant.BusinessType.BUSINESS_TYPE_HF:businessNo.equals(Constant.BusinessType.BUSINESS_TYPE_FLOW)?
                        Constant.BusinessType.BUSINESS_TYPE_FLOW:Constant.BusinessType.BUSINESS_TYPE_FIXED;
                    searchParams.put(Operator.EQ + "_"
                                     + EntityConstant.SupplyProductRelation.BUSINESSTYPE,businessType );
                }
                if (type.equals(MerchantType.SUPPLY.toString()))
                {
                    List<SupplyProductRelation> upProductList = supplyProductRelationService.getAllProductBySupplyMerchantId(
                        searchParams, mid, IdentityType.MERCHANT.toString(),
                        Constant.SupplyProductStatus.OPEN_STATUS);
                    int i = 0;
                    String result = "";
                    while (i < upProductList.size())
                    {
                        SupplyProductRelation upProduct = upProductList.get(i);
                        result = result + upProduct.getProductId() + "*"
                                 + upProduct.getProductName() + "|";
                        i++ ;
                    }
                    return result;
                }
                else
                {
                    List<AgentProductRelation> downProductList = agentProductRelationService.getAllAgentProductRelationService(
                        searchParams, mid, IdentityType.MERCHANT.toString());
                    int i = 0;
                    String result = "";
                    while (i < downProductList.size())
                    {
                        AgentProductRelation downProduct = downProductList.get(i);
                        result = result + downProduct.getProductId() + "*"
                                 + downProduct.getProductName() + "|";
                        i++ ;
                    }
                    return result;
                }
            }
            return null;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:getProduct()]" + e.getMessage());
            return null;
        }
    }
    /**
     * 根据条件获取已配置的指定排除信息
     * 
     * @param productNo     产品编号
     * @param identityId    作用商户（被作用商户）
     * @param ruleType      类型（指定、排除）
     * @param type          商户类型（Agent、Supply）
     * @return 
     * @see
     */
    public List<AssignExclude> getConfigAssignExclude(String productNo,Long identityId,String ruleType,String type)
    {
        Map<String, Object> assigenExcludeSearchParams=new HashMap<String,Object>();
        List<AssignExclude> assigenExcludes=new ArrayList<AssignExclude>();
        if (ruleType != null)
        {
            assigenExcludeSearchParams.put(Operator.EQ + "_"
                + EntityConstant.AssignExclude.RULE_TYPE, ruleType);
        }
        if (BeanUtils.isNotNull(identityId))
        {
            if(MerchantType.AGENT.toString().equals(type))
            {
                assigenExcludeSearchParams.put(Operator.EQ + "_"
                    + EntityConstant.AssignExclude.MERCHANT_ID, identityId.toString());
            }else
            {
                assigenExcludeSearchParams.put(Operator.EQ + "_"
                    + EntityConstant.AssignExclude.OBJECT_MERCHANT_ID, identityId.toString());  
            }
        }
        if (StringUtils.isNotBlank(productNo)&& !productNo.equals("0"))
        {
            assigenExcludeSearchParams.put(Operator.EQ + "_"
                + EntityConstant.AssignExclude.PRODUCT_NO,productNo);
        }
        assigenExcludes=assignExcludeService.getAllAssignExclude(assigenExcludeSearchParams);
        return BeanUtils.isNotNull(assigenExcludes)?assigenExcludes:new ArrayList<AssignExclude>();
    }

    /**
     * 获取有选中产品的商户列表
     * 
     * @return
     */
    @RequestMapping(value = "/getMerchant")
    @ResponseBody
    public String getMerchant(@RequestParam("pid") Long pid, @RequestParam("mid") Long mid,
                              @RequestParam("ruleType") Long ruleType,
                              @RequestParam("type") String type)
    {
        try
        {
                logger.info("[AssignExcludeController:getMerchant(" + pid + "," + mid + ","
                            + ruleType + "," + type + ")]");
                
            List<AssignExclude> assignExcludeList = getConfigAssignExclude( pid.toString(),mid,ruleType.toString(),type);
            List<AssignExclude> unAssignExcludes = getConfigAssignExclude( pid.toString(),mid,Constant.AssignExclude.RULE_TYPE_ASSIGN.equals(ruleType.toString())?Constant.AssignExclude.RULE_TYPE_EXCLUDE:Constant.AssignExclude.RULE_TYPE_ASSIGN,type);
            if (!StringUtil.isNullOrEmpty(type))
            {
                if (type.equals(MerchantType.SUPPLY.toString()))
                {
                    // 获取所有配置此产品的下游商户
                    List<AgentProductRelation> downProductList = agentProductRelationService.getAllAgentProductRelationByUp(pid);
                    //去除掉排除（指定）过的产品
                    downProductList=removeAgentProducts(downProductList,unAssignExcludes,type,Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT);
                    List<String[]> objList = assignExcludeService.getAllAgentMerchant(
                        downProductList, assignExcludeList);

                    return getConfigMerchant(objList);
                }
                else
                {
                    // 获取所有配置此产品的上游商户
                    List<SupplyProductRelation> upProductList = supplyProductRelationService.getAllSupplyProductRelationByDown(pid);
                    //去除掉排除（指定）过的产品
                    upProductList=removeSupplyProducts(upProductList,unAssignExcludes,type,Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT);
                    List<String[]> objList = assignExcludeService.getAllSupplyMerchant(
                        upProductList, assignExcludeList);

                    return getConfigMerchant(objList);
                }
            }
            return null;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:getMerchant()]" + e.getMessage());
            return null;
        }
    }
    /**
     * 组装已配置的商户信息
     * 
     * @param objList
     * @return 
     * @see
     */
    public String getConfigMerchant(List<String[]> objList)
    {
            logger.info("[AssignExcludeController:getConfigMerchant()]" + objList != null ? objList.toString() : null);
        int i = 0;
        String result = "";
        while (i < objList.size())
        {
            String[] upProduct = objList.get(i);
            result = result + upProduct[0] + "*" + upProduct[1] + "*" + upProduct[2] + "|";
            i++ ;
        }
            logger.info("[AssignExcludeController:getConfigMerchant()]" + result);
        return result;
    }
    /**
     * 添加商户分组
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/saveAssignExcludeProduct")
    @ResponseBody
    public String saveAssignExcludeProduct(@RequestParam(value = "businessNo", defaultValue = "") String businessNoStr,
                                    @RequestParam(value = "mid", defaultValue = "") Long mid,
                                    @RequestParam(value = "type", defaultValue = "") String type,
                                    @RequestParam(value = "remid", defaultValue = "") Long remid,
                                    @RequestParam(value = "carrier", defaultValue = "") String carrier,
                                    @RequestParam(value = "province", defaultValue = "") String province,
                                    @RequestParam(value = "city", defaultValue = "") String city,
                                    @RequestParam(value = "ruleType", defaultValue = "") String ruleType,
                                    @RequestParam(value = "productNo", defaultValue = "") String productNoStr,
                                    ModelMap model)
    {
        try
        {
                logger.info("[AssignExcludeController:saveAssignExcludeProduct(" + businessNoStr + ","
                            + mid + "," + type + "," + remid + "," + carrier + "," + province + "," + city + "," + ruleType + ","
                            + productNoStr + ")]");
            List<AssignExclude> assignExcludeList = new ArrayList<AssignExclude>();
            assignExcludeList = this.saveAssignExcludeProductList(businessNoStr, mid, type, remid, carrier, province, city, ruleType, productNoStr);
            if (assignExcludeList != null)
            {
                return PageConstant.TRUE;
            }
            else
            {
                return PageConstant.FALSE;
            }
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:saveAssignExcludeProduct()]" + e.getMessage());
            return PageConstant.FALSE;
        }
    }
    /**
     * 添加商户分组
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/saveAssignExclude")
    @ResponseBody
    public String saveAssignExclude(@RequestParam("businessNo") String businessNoStr,
                                    @RequestParam("merchantType") String merchantType,
                                    @RequestParam("ruleType") String ruleTypeStr,
                                    @RequestParam("merchantId") String merchantIdStr,
                                    @RequestParam("productNo") String productNoStr,
                                    @RequestParam("objectMerchant") String objectMerchant,
                                    ModelMap model)
    {
        try
        {
                logger.info("[AssignExcludeController:saveAssignExclude(" + businessNoStr + ","
                            + merchantType + "," + ruleTypeStr + "," + merchantIdStr + ","
                            + productNoStr + "," + objectMerchant + ")]");
            List<AssignExclude> assignExcludeList = new ArrayList<AssignExclude>();
            assignExcludeList = this.saveAssignExcludeList(businessNoStr, merchantType,
                ruleTypeStr, merchantIdStr, productNoStr, objectMerchant);
            if (assignExcludeList != null)
            {
                return PageConstant.TRUE;
            }
            else
            {
                return PageConstant.FALSE;
            }
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:saveAssignExclude()]" + e.getMessage());
            return PageConstant.FALSE;
        }
    }

    /**
     * 删除商户分组
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/assignExclude_delete")
    public String assignExclude_delete(@RequestParam("id") Long id, ModelMap model)
    {
        try
        {
                logger.info("[AssignExcludeController:deleteAssignExclude(" + id + ")]");
            assignExcludeService.deleteAssignExclude(id);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "AssignExclude/listAssignExclude");
            model.put("next_msg", "指定排除配置列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:deleteAssignExclude()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
    
    /**
     * 批量删除商户分组
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/deleteAssignExcludes")
    @ResponseBody
    public String deleteAssignExcludes(@RequestParam("ids") String ids, ModelMap model)
    {
        try
        {
            logger.info("[AssignExcludeController:deleteAssignExclude(" + ids + ")]");
            if (ids != null && !ids.isEmpty())
            {
                String[] assignExcludeIds = ids.split(Constant.StringSplitUtil.DECODE);
                int i = 0;
                while (i < assignExcludeIds.length)
                {
                    Long id=new Long(assignExcludeIds[i].trim());
                    assignExcludeService.deleteAssignExclude(id);
                    i++;
                }
                return PageConstant.TRUE;
            }
            return PageConstant.FALSE;
        }
        catch (RpcException e)
        {
            logger.debug("[AssignExcludeController:deleteAssignExclude()]" + e.getMessage());
            return PageConstant.FALSE;
        }
    }
    /**
     * 进入用户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/listAssignExclude")
    public String listAssignExclude(@RequestParam(value = "merchantType", defaultValue = "") String merchantType,
                                    @RequestParam(value = "merchantId", defaultValue = "") String merchantId,
                                    @RequestParam(value = "reMerchantId", defaultValue = "") String reMerchantId,
                                    @RequestParam(value = "carrierNo", defaultValue = "") String carrierNo,
                                    @RequestParam(value = "provinceNo", defaultValue = "") String provinceNo,
                                    @RequestParam(value = "cityId", defaultValue = "") String cityId,
                                    @RequestParam(value = "ruleType", defaultValue = "") String ruleType,
                                    @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                                    @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                                    ModelMap model, ServletRequest request)
    {
        try
        {
                logger.info("[AssignExcludeController:listAssignExclude(" + merchantType + ","
                            + merchantId + "," + ruleType + ")]");

            Long merchantIdl = new Long(0);
            Long reMerchantIdl = new Long(0);
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (!StringUtil.isNullOrEmpty(merchantType))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.AssignExclude.MERCHANT_TYPE,
                    merchantType);
            }
            if (!StringUtil.isNullOrEmpty(merchantId))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.AssignExclude.MERCHANT_ID,
                    merchantId);
                merchantIdl = new Long(merchantId);
            }
            if (!StringUtil.isNullOrEmpty(ruleType))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.AssignExclude.RULE_TYPE,
                    ruleType);
            }
            if (!StringUtil.isNullOrEmpty(reMerchantId))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.AssignExclude.OBJECT_MERCHANT_ID,
                    reMerchantId);
                reMerchantIdl = new Long(reMerchantId);
            }
            if (!StringUtil.isNullOrEmpty(carrierNo))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.AssignExclude.CARRIER_NO,
                    carrierNo);
            }
            if (!StringUtil.isNullOrEmpty(provinceNo))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.AssignExclude.PROVICE_NO,
                    provinceNo);
            }
            if (!StringUtil.isNullOrEmpty(cityId))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.AssignExclude.CITY_NO,
                    cityId);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.AssignExclude.ID);
            YcPage<AssignExclude> page_list = assignExcludeService.queryAssignExclude(
                searchParams, pageNumber, pageSize, bsort);
            List<AssignExclude> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            List<Merchant> upMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
            List<Merchant> downMerchant = merchantService.queryAllMerchant(MerchantType.AGENT,null);
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantIdl,
                IdentityType.MERCHANT);
            Merchant reMerchant = (Merchant)identityService.findIdentityByIdentityId(reMerchantIdl,
                IdentityType.MERCHANT);
            List<Province> province = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfo = carrierInfoService.getAllCarrierInfo();
            model.addAttribute("province", province);
            model.addAttribute("carrierInfo", carrierInfo);
            model.addAttribute("upMerchant", upMerchant);
            model.addAttribute("downMerchant", downMerchant);
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("merchantType", merchantType);
            model.addAttribute("merchantId", merchantIdl);
            model.addAttribute("merchant", merchant);
            model.addAttribute("reMerchantId", reMerchantIdl);
            model.addAttribute("reMerchant", reMerchant);
            model.addAttribute("carrierNo", carrierNo);
            model.addAttribute("provinceNo", provinceNo);
            model.addAttribute("cityId", cityId);
            model.addAttribute("ruleType", ruleType);

            // 根据用户名获取该用户的角色
            return PageConstant.PAGE_ASSIGN_EXCLUDE_LIST;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:listAssignExclude()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入分配角色
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/assignExclude_edit", method = RequestMethod.GET)
    public String assignExclude_edit(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
                logger.info("[AssignExcludeController:editAssignExclude(" + id + ")]");
            AssignExclude assignExclude = assignExcludeService.getAssignExcludeById(new Long(id));
            String type = assignExclude.getMerchantType();
            Long pid = assignExclude.getProductNo();
            List<AssignExclude> assignExcludeList = getConfigAssignExclude( pid.toString(),assignExclude.getMerchantId(),assignExclude.getRuleType().toString(),type);
            List<AssignExclude> unAssignExcludes = getConfigAssignExclude( pid.toString(),assignExclude.getMerchantId(),Constant.AssignExclude.RULE_TYPE_ASSIGN.equals(assignExclude.getRuleType().toString())?Constant.AssignExclude.RULE_TYPE_EXCLUDE:Constant.AssignExclude.RULE_TYPE_ASSIGN,type);
            if (!StringUtil.isNullOrEmpty(type))
            {
                if (type.equals(MerchantType.SUPPLY.toString()))
                {
                    // 获取所有配置此产品的下游商户
                    List<AgentProductRelation> downProductList = agentProductRelationService.getAllAgentProductRelationByUp(pid);
                    //去除掉排除（指定）过的产品
                    downProductList=removeAgentProducts(downProductList,unAssignExcludes,type,Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT);
                    List<String[]> objList = assignExcludeService.getAllAgentMerchant(
                        downProductList, assignExcludeList);

                    model.addAttribute("objList", objList);
                }
                else
                {
                    // 获取所有配置此产品的上游商户
                    List<SupplyProductRelation> upProductList = supplyProductRelationService.getAllSupplyProductRelationByDown(pid);
                    //去除掉排除（指定）过的产品
                    upProductList=removeSupplyProducts(upProductList,unAssignExcludes,type,Constant.AssignExclude.ASSIGN_EXCLUDE_MERCHANT);
                    List<String[]> objList = assignExcludeService.getAllSupplyMerchant(
                        upProductList, assignExcludeList);

                    model.addAttribute("objList", objList);
                }
            }
            model.addAttribute("assignExclude", assignExclude);
            return PageConstant.PAGE_ASSIGN_EXCLUDE_EDIT;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:editAssignExclude()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 编辑用户后保存
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/assignExclude_edit", method = RequestMethod.POST)
    @ResponseBody
    public String assignExclude_edit(@RequestParam("businessNo") String businessNoStr,
                                     @RequestParam("merchantType") String merchantType,
                                     @RequestParam("ruleType") String ruleTypeStr,
                                     @RequestParam("merchantId") String merchantIdStr,
                                     @RequestParam("productNo") String productNoStr,
                                     @RequestParam("objectMerchant") String objectMerchant,
                                     ModelMap model)
    {
        try
        {
                logger.info("[AssignExcludeController:editMerchantResponseDo(" + businessNoStr
                            + "," + merchantType + "," + ruleTypeStr + "," + merchantIdStr + ","
                            + productNoStr + "," + objectMerchant + ")]");

            List<AssignExclude> assignExcludeList = this.saveAssignExcludeList(businessNoStr, merchantType,
                ruleTypeStr, merchantIdStr, productNoStr, objectMerchant);
            if (assignExcludeList != null)
            {
                return PageConstant.TRUE;
            }
            else
            {
                return PageConstant.FALSE;
            }
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:editMerchantResponseDo()]" + e.getMessage());
            return PageConstant.FALSE;
        }
    }
    /**
     * 根据条件保存指定排除信息（批量选择产品）
     * 
     * @param businessNoStr 业务编号
     * @param mid           作用商户ID
     * @param type          商户类型（Agent、Supply）
     * @param remid         被作用商户ID
     * @param carrier       运营商
     * @param province      省份
     * @param city          城市
     * @param ruleType      类型（指定、排除）
     * @param productNoStr  产品ID字符串（多个用|分开）
     * @return 
     * @see
     */
    public List<AssignExclude> saveAssignExcludeProductList( String businessNoStr,Long mid,String type,Long remid,String carrier,String province,String city,String ruleType,String productNoStr)
    {
        try
        {
            logger.info("[AssignExcludeController:saveAssignExcludeProductList(" + businessNoStr + ","
                            + mid + "," + type + "," + remid + "," + carrier + "," + province + "," + city + "," + ruleType + ","
                            + productNoStr + ")]");
            List<AssignExclude> assigenExcludeDel=new ArrayList<AssignExclude>();
            if(MerchantType.SUPPLY.toString().equals(type))
            {
                assigenExcludeDel=getConfigAssignExclude(carrier, province, city, remid, mid, ruleType,type);
            }else{
                assigenExcludeDel=getConfigAssignExclude(carrier, province, city, mid, remid, ruleType,type);
            }
            assignExcludeService.deleteAssignExcludeList(assigenExcludeDel);
            List<AssignExclude> assignExcludeList = new ArrayList<AssignExclude>();
            if (productNoStr != null && !productNoStr.isEmpty())
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                    mid, IdentityType.MERCHANT);
                Merchant reMerchant = (Merchant)identityService.findIdentityByIdentityId(
                    remid, IdentityType.MERCHANT);
                if(MerchantType.SUPPLY.toString().equals(type))
                {
                    //如为supply则需要将作用商户和被作用商户互换
                    Merchant temp=new Merchant();
                    temp=merchant;
                    merchant=reMerchant;
                    reMerchant=temp;
                }   
                String[] productlist = productNoStr.split(Constant.StringSplitUtil.DECODE);
                int i = 0;
                while (i < productlist.length)
                {
                    AssignExclude assignExclude = new AssignExclude();
                    if(MerchantType.AGENT.toString().equals(type))
                    {
                        assignExclude = getUpProductRelation(assignExclude, new Long(productlist[i].trim()), remid);
                    }
                    else
                    {
                        assignExclude = getDownProductRelation(assignExclude, new Long(productlist[i].trim()), remid);
                    }
                    assignExclude.setRuleType(new Long(ruleType));
                    assignExclude.setBusinessNo(businessNoStr);
                    assignExclude.setMerchantId(merchant.getId());
                    assignExclude.setMerchantType(merchant.getMerchantType().toString());
                    assignExclude.setMerchantName(merchant.getMerchantName());
                    assignExclude.setObjectMerchantId(reMerchant.getId());
                    assignExclude.setObjectMerchantType(reMerchant.getMerchantType().toString());
                    assignExclude.setObjectMerchantName(reMerchant.getMerchantName());
                    assignExcludeList.add(assignExclude);
                    i++ ;
                }
            }
            assignExcludeList = assignExcludeService.saveAssignExcludeList(assignExcludeList);
            return assignExcludeList;
        }
        catch (RpcException e)
        {
            logger.debug("[AssignExcludeController:saveAssignExcludeProductList()]" + e.getMessage());
            return null;
        }
    }
    /**
     * 根据条件保存指定排除信息（批量选择商户）
     * 
     * @param businessNo        业务编号
     * @param merchantType      商户类型（Agent、Supply）
     * @param ruleTypeStr       类型（指定、排除）
     * @param merchantIdStr     作用商户ID
     * @param productNoStr      产品ID
     * @param objectMerchant    被作用商户ID（多个用|分开）
     * @return 
     * @see
     */
    public List<AssignExclude> saveAssignExcludeList(String businessNo, String merchantType,
                                                     String ruleTypeStr, String merchantIdStr,
                                                     String productNoStr, String objectMerchant)
    {
        try
        {
                logger.info("[AssignExcludeController:saveAssignExcludeList(" + businessNo + ","
                            + merchantType + "," + ruleTypeStr + "," + merchantIdStr + ","
                            + productNoStr + "," + objectMerchant + ")]");
            Long ruleType = new Long(ruleTypeStr);
            Long merchantId = new Long(merchantIdStr);
            Long productNo = new Long(productNoStr);
            List<AssignExclude> assignExcludes=getConfigAssignExclude(productNoStr,merchantId,ruleTypeStr,merchantType);
            assignExcludeService.deleteAssignExcludeList(assignExcludes);
            List<AssignExclude> assignExcludeList = new ArrayList<AssignExclude>();
            if (objectMerchant != null && !objectMerchant.isEmpty())
            {
                String[] merchantlist = objectMerchant.split(Constant.StringSplitUtil.DECODE);
                int i = 0;
                while (i < merchantlist.length)
                {
                    AssignExclude assignExclude = new AssignExclude();
                    Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                        merchantId, IdentityType.MERCHANT);
                    Merchant reMerchant = (Merchant)identityService.findIdentityByIdentityId(
                        new Long(merchantlist[i].trim()), IdentityType.MERCHANT);
                    if (MerchantType.SUPPLY.toString().equals(merchantType))
                    {
                        //如为supply则需要将作用商户和被作用商户互换
                        assignExclude = getUpProductRelation(assignExclude, productNo, merchantId);
                        Merchant temp=new Merchant();
                        temp=merchant;
                        merchant=reMerchant;
                        reMerchant=temp;
                    }
                    else
                    {
                        assignExclude = getDownProductRelation(assignExclude, productNo,merchantId);
                    }
                    assignExclude.setRuleType(ruleType);
                    assignExclude.setBusinessNo(businessNo);
                    assignExclude.setMerchantId(merchant.getId());
                    assignExclude.setMerchantType(merchant.getMerchantType().toString());
                    assignExclude.setMerchantName(merchant.getMerchantName());
                    assignExclude.setObjectMerchantId(reMerchant.getId());
                    assignExclude.setObjectMerchantType(reMerchant.getMerchantType().toString());
                    assignExclude.setObjectMerchantName(reMerchant.getMerchantName());
                    assignExcludeList.add(assignExclude);
                    i++ ;
                }
            }
            assignExcludeList = assignExcludeService.saveAssignExcludeList(assignExcludeList);
            return assignExcludeList;
        }
        catch (RpcException e)
        {
                logger.debug("[AssignExcludeController:saveAssignExcludeList()]" + e.getMessage());
            return null;
        }
    }
    /**
     * 将供货商产品信息数据赋值给指定排除实体
     * 
     * @param assignExclude 指定排除实体
     * @param productNo     供货商产品ID
     * @param merchantId    供货商ID
     * @return 
     * @see
     */
    public AssignExclude getUpProductRelation(AssignExclude assignExclude, Long productNo,
                                              Long merchantId)
    {
            logger.info("[AssignExcludeController:getUpProductRelation(" + assignExclude + ","
                        + productNo + "," + merchantId + ")]");
        SupplyProductRelation upProduct = supplyProductRelationService.querySupplyProductRelationByParams(
            productNo, merchantId,null);
        CarrierInfo carrier = new CarrierInfo();
        if (BeanUtils.isNotNull(upProduct)&&upProduct.getCarrierName() != null)
        {
            carrier = carrierInfoService.findOne(upProduct.getCarrierName());
            assignExclude.setCarrierNo(carrier.getCarrierNo());
            assignExclude.setCarrierName(carrier.getCarrierName());
        }
        Province province = new Province();
        if (BeanUtils.isNotNull(upProduct)&&upProduct.getProvince() != null)
        {
            province = provinceService.findOne(upProduct.getProvince());
            assignExclude.setProvinceNo(province.getProvinceId());
            assignExclude.setProvinceName(province.getProvinceName());
        }
        City city = new City();
        if (BeanUtils.isNotNull(upProduct)&&upProduct.getCity() != null)
        {
            city = cityService.findOne(upProduct.getCity());
            assignExclude.setCityNo(city.getCityId());
            assignExclude.setCityName(city.getCityName());
        }
        AirtimeProduct product = productPageQuery.queryAirtimeProductById(productNo);
        assignExclude.setProductNo(product.getProductId());
        assignExclude.setParValue(product.getParValue());
        assignExclude.setProductName(product.getProductName());
            logger.debug("[AssignExcludeController:getUpProductRelation()]" + assignExclude != null ? assignExclude.toString() : null);
        return assignExclude;
    }
    /**
     * 将代理商产品信息数据赋值给指定排除实体
     * 
     * @param assignExclude 指定排除实体
     * @param productNo     代理商产品ID
     * @param merchantId    代理商ID
     * @return 
     * @see
     */
    public AssignExclude getDownProductRelation(AssignExclude assignExclude, Long productNo,
                                                Long merchantId)
    {
            logger.info("[AssignExcludeController:getDownProductRelation(" + assignExclude + ","
                        + productNo + "," + merchantId + ")]");
        AgentProductRelation downProduct = agentProductRelationService.queryAgentProductRelationByParams(
            productNo, merchantId,null);
        CarrierInfo carrier = new CarrierInfo();
        if (BeanUtils.isNotNull(downProduct)&&downProduct.getCarrierName() != null)
        {
            carrier = carrierInfoService.findOne(downProduct.getCarrierName());
            assignExclude.setCarrierNo(carrier.getCarrierNo());
            assignExclude.setCarrierName(carrier.getCarrierName());
        }
        Province province = new Province();
        if (BeanUtils.isNotNull(downProduct)&&downProduct.getProvince() != null)
        {
            province = provinceService.findOne(downProduct.getProvince());
            assignExclude.setProvinceNo(province.getProvinceId());
            assignExclude.setProvinceName(province.getProvinceName());
        }
        City city = new City();
        if (BeanUtils.isNotNull(downProduct)&&downProduct.getCity() != null)
        {
            city = cityService.findOne(downProduct.getCity());
            assignExclude.setCityNo(city.getCityId());
            assignExclude.setCityName(city.getCityName());
        }
        AirtimeProduct product = productPageQuery.queryAirtimeProductById(productNo);
        assignExclude.setProductNo(product.getProductId());
        assignExclude.setParValue(product.getParValue());
        assignExclude.setProductName(product.getProductName());
            logger.debug("[AssignExcludeController:getDownProductRelation()]" + assignExclude != null ? assignExclude.toString() : null);
        return assignExclude;
    }
}
