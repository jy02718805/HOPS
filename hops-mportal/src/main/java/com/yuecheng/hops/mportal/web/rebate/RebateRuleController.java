package com.yuecheng.hops.mportal.web.rebate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantLevel;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.vo.rebate.RebateJsonParam;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.rebate.entity.RebateProduct;
import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.RebateTradingVolume;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;
import com.yuecheng.hops.rebate.entity.assist.RebateRuleAssist;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;
import com.yuecheng.hops.rebate.service.RebateProductService;
import com.yuecheng.hops.rebate.service.RebateRuleQueryManager;
import com.yuecheng.hops.rebate.service.RebateRuleService;
import com.yuecheng.hops.rebate.service.RebateTradingVolumeService;


@Controller
@RequestMapping(value = "/RebateRule")
public class RebateRuleController extends BaseControl
{
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RebateProductQueryManager rebateProductQueryManager;

    @Autowired
    private SupplyProductRelationService upProductRelationService;

    @Autowired
    private AgentProductRelationService downProductRelationService;

    @Autowired
    private RebateRuleService rebateRuleService;

    @Autowired
    private RebateRuleQueryManager rebateRuleQueryManager;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private RebateProductService rebateProductService;

    @Autowired
    private RebateTradingVolumeService rebateTradingVolumeService;

    @Autowired
    IdentityService identityService;

    @Autowired
    public HttpSession session;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static final Logger logger = LoggerFactory.getLogger(RebateRuleController.class);

    /**
     * 获取城市列表
     * 
     * @return
     */
    @RequestMapping(value = "/getMerchant", method = RequestMethod.POST)
    @ResponseBody
    public String getMerchant(@RequestParam("merchantName")
    String merchantName, ModelMap model)
    {
        try
        {
            merchantName = new String(merchantName.getBytes("ISO-8859-1"), "gbk");
            logger.debug("[RebateRuleController:getMerchant(" + merchantName + ")]");
            List<Merchant> list2 = merchantService.queryAllMerchantByMerchantNameFuzzy(merchantName,null);

            int i = 0;
            String result = "";
            while (i < list2.size())
            {
                Merchant merchant = list2.get(i);
                if (merchant.getIdentityStatus().getStatus().equals(Constant.MerchantStatus.ENABLE))
                {
                    result = result + merchant.getId() + "*" + merchant.getMerchantName() + "|";
                }
                i++ ;
            }
            return result;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:getMerchant()]" + e.getMessage());
            return null;
        }
        catch (Exception e)
        {
            logger.debug("[RebateRuleController:getMerchant()]" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取城市列表
     * 
     * @return
     */
    @RequestMapping(value = "/getParentMerchant", method = RequestMethod.POST)
    @ResponseBody
    public String getParentMerchant(@RequestParam("merchantId")
    String merchantId, ModelMap model)
    {
        try
        {
            logger.debug("[RebateRuleController:getParentMerchant(" + merchantId + ")]");
            Long identityId = new Long(merchantId);
            Merchant merchant0 = (Merchant)identityService.findIdentityByIdentityId(identityId,
                IdentityType.MERCHANT);
            String result = "";
            if (merchant0.getMerchantType().equals(MerchantType.AGENT))
            {
                List<Merchant> list2 = merchantService.queryParentMerchantByMerchantId(identityId);
                list2 = changeListProt(list2);
                int i = 0;
                while (i < list2.size())
                {
                    Merchant merchant = list2.get(i);
                    if (merchant.getIdentityStatus().getStatus().equals(
                        Constant.MerchantStatus.ENABLE))
                    {
                        result = result + merchant.getId() + "*" + merchant.getMerchantName()
                                 + "*" + merchant.getMerchantLevel() + "|";
                    }
                    i++ ;
                }
            }
            List<RebateRuleAssist> rebateRuleAssists = rebateRuleQueryManager.queryRebateRuleByMId(identityId);
            List<String> rebateProductIds = new ArrayList<String>();
            for (RebateRuleAssist rebateRuleAssist : rebateRuleAssists)
            {
                RebateRule rebateRule = rebateRuleAssist.getRebateRule();
                rebateProductIds.add(rebateRule.getRebateProductId());
            }
            List<RebateProductAssist> rebateProductList = rebateProductQueryManager.queryProductsByRProductId(rebateProductIds);
            String tree = this.getProductByMerchantId(identityId,
                merchant0.getMerchantType().toString(), null, rebateProductList);
            return result + "@" + tree + "@" + merchant0.getMerchantLevel();
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:getParentMerchant()]" + e.getMessage());
            return null;
        }
    }

    /**
     * 进入返佣设置添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/rebateruleadd", method = RequestMethod.GET)
    public String addRebateRule(@RequestParam("merchantId")
    String merchantId, ModelMap model)
    {
        try
        {
            logger.info("[RebateRuleController:addRebateRule(" + merchantId + ")]");
            Merchant merchant = new Merchant();
            Merchant merchantOne = new Merchant();
            Merchant merchantTwo = new Merchant();
            String tree = "";
            if (StringUtil.isNotBlank(merchantId))
            {
                Long identityId = new Long(merchantId);
                merchant = (Merchant)identityService.findIdentityByIdentityId(identityId,
                    IdentityType.MERCHANT);
                if (BeanUtils.isNull(merchant.getId()))
                {
                    merchant.setId(0l);
                    merchant.setMerchantName("");
                    logger.debug("[RebateRuleController:addRebateRule(" + merchantId + ",获取商户为空)]");
                }
                if (BeanUtils.isNull(merchant.getMerchantLevel()))
                {
                    merchant.setMerchantLevel(MerchantLevel.Zero);
                }
                List<Merchant> list2 = new ArrayList<Merchant>();
                list2 = merchantService.queryParentMerchantByMerchantId(identityId);
                list2 = changeListProt(list2);
                int size = list2.size();
                if (size > 0)
                {
                    if (size == 1)
                    {
                        merchantOne = list2.get(0);
                        merchantTwo.setId(0l);
                        merchantTwo.setMerchantName("");
                    }
                    else if (size == 2)
                    {
                        merchantOne = list2.get(0);
                        merchantTwo = list2.get(1);
                    }
                }
                else
                {
                    merchantOne.setId(0l);
                    merchantOne.setMerchantName("");
                    merchantTwo.setId(0l);
                    merchantTwo.setMerchantName("");
                    logger.debug("[RebateRuleController:addRebateRule(" + merchantId
                                 + ",获取父级商户为空)]");
                }
                List<RebateRuleAssist> rebateRuleList = rebateRuleQueryManager.queryRebateRuleByMId(identityId);
                List<String> rebateProductIds = new ArrayList<String>();
                for (RebateRuleAssist rebateRuleAssist : rebateRuleList)
                {
                    rebateProductIds.add(rebateRuleAssist.getRebateRule().getRebateProductId());
                }
                List<RebateProductAssist> rebateProductList = rebateProductQueryManager.queryProductsByRProductId(rebateProductIds);
                tree = this.getProductByMerchantId(identityId,
                    merchant.getMerchantType().toString(), null, rebateProductList);
            }
            else
            {
                merchant.setId(0l);
                merchant.setMerchantName("");
                merchantOne.setId(0l);
                merchantOne.setMerchantName("");
                merchantTwo.setId(0l);
                merchantTwo.setMerchantName("");

                logger.debug("[RebateRuleController:addRebateRule(商户ID为空)]");
            }
            model.addAttribute("tree", tree);
            model.addAttribute("merchant", merchant);
            model.addAttribute("merchantOne", merchantOne);
            model.addAttribute("merchantTwo", merchantTwo);
            return PageConstant.PAGE_REBATE_RULE_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:addRebateRule(GET)]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入返佣设置添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addRebateRule", method = RequestMethod.POST)
    @ResponseBody
    public String addRebateRule(@RequestParam("merchantId")
    String merchantId, @RequestParam("productIds")
    String productIds, @RequestParam("rebateTimeType")
    String rebateTimeType, @RequestParam("data")
    String data, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            logger.info("[RebateRuleController:addRebateRule(POST)]" + merchantId + ","
                        + productIds + "," + rebateTimeType + "," + data);
            Long identityId = new Long(merchantId);
            Gson gson = new Gson();
            List<RebateJsonParam> params = gson.fromJson(data,
                new TypeToken<List<RebateJsonParam>>()
                {}.getType());
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(identityId,
                IdentityType.MERCHANT);
            List<RebateRule> rebateRuleList = new ArrayList<RebateRule>();
            rebateRuleList = this.savaRebateRuleList(merchant, productIds, rebateTimeType, params);
            if (rebateRuleList != null && rebateRuleList.size() > 0)
            {
                merchant.setIsRebate(Constant.MerchantRebateStatus.ENABLE);
                identityService.saveIdentity(merchant, loginPerson.getOperatorName());
                return PageConstant.TRUE;
            }
            else
            {
                return PageConstant.FALSE;
            }
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:addRebateRule(POST)]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入用户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/list")
    public String listRebateRule(@RequestParam(value = "merchantId", defaultValue = "")
    String merchantId, @RequestParam(value = "rebateMerchantId", defaultValue = "")
    String rebateMerchantId, @RequestParam(value = "rebateTimeType", defaultValue = "")
    String rebateTimeType, @RequestParam(value = "rebateType", defaultValue = "")
    String rebateType, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            logger.debug("[RebateRuleController:listRebateRule(" + merchantId + ","
                        + rebateMerchantId + "," + rebateTimeType + "," + rebateType + ")]");
            List<Merchant> agentMerchant = merchantService.queryAllMerchant(MerchantType.AGENT,
                null);
            List<Merchant> supplyMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                null);

            List<Merchant> allMerchant = new ArrayList<Merchant>();
            allMerchant.addAll(agentMerchant);
            allMerchant.addAll(supplyMerchant);

            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(merchantId))
            {
                searchParams.put(EntityConstant.RebateRule.MERCHANT_ID, merchantId);
            }
            if (StringUtil.isNotBlank(rebateMerchantId))
            {
                searchParams.put(EntityConstant.RebateRule.REBATE_MERCHANT_ID, rebateMerchantId);
            }
            if (StringUtil.isNotBlank(rebateTimeType))
            {
                searchParams.put(EntityConstant.RebateRule.REBATE_TIME_TYPE, rebateTimeType);
            }
            if (StringUtil.isNotBlank(rebateType))
            {
                searchParams.put(EntityConstant.RebateRule.REBATE_TYPE, rebateType);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.RebateRule.REBATE_RULE_ID);
            YcPage<RebateRuleAssist> page_list = (YcPage<RebateRuleAssist>)rebateRuleQueryManager.queryPageRebateRule(
                searchParams, pageNumber, pageSize, bsort);
            List<RebateRuleAssist> list = page_list.getList();
            List<RebateRuleAssist> result = new ArrayList<RebateRuleAssist>();
            for (RebateRuleAssist rebateRuleAssist : list)
            {
                String ruleName = getTrdingVolumeStr(
                    rebateRuleAssist.getRebateRule().getRebateRuleId(),
                    rebateRuleAssist.getRebateTradingVolume());
                rebateRuleAssist.setRuleName(ruleName);
                result.add(rebateRuleAssist);
            }

            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("merchantList", allMerchant);
            model.addAttribute("mlist", result);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            
            model.addAttribute("merchantId", StringUtil.isNotBlank(merchantId)?new Long(merchantId):0l);
            model.addAttribute("rebateMerchantId", StringUtil.isNotBlank(rebateMerchantId)?new Long(rebateMerchantId):0l);
            model.addAttribute("rebateTimeType", rebateTimeType);
            model.addAttribute("rebateType", rebateType);

            // 根据用户名获取该用户的角色
            return PageConstant.PAGE_REBATE_RULE_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:listRebateRule()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[RebateRuleController:listRebateRule()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 删除商户分组
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/deleteRebateRule")
    public String deleteRebateRule(@RequestParam("id")
    Long id, ModelMap model)
    {
        try
        {
        	Operator operator=getLoginUser();
            logger.debug("[RebateRuleController:deleteRebateRule(" + id + ")]");
            RebateRuleAssist rebateRuleAssist=rebateRuleService.queryRebateRuleById(id);
            if(BeanUtils.isNotNull(rebateRuleAssist)&&BeanUtils.isNotNull(rebateRuleAssist.getRebateRule()))
            {
	            rebateRuleService.deleteRebateRule(id);
            	RebateRule rebateRule=rebateRuleAssist.getRebateRule();
	            List<RebateRuleAssist> list=rebateRuleQueryManager.queryRebateRuleByParams(rebateRule.getMerchantId(),rebateRule.getRebateMerchantId());
	            if(BeanUtils.isNull(list)||list.size()<=0)
	            {
	            	Merchant merchant=merchantService.queryMerchantById(rebateRule.getMerchantId());
	            	if(BeanUtils.isNotNull(merchant))
	            	{
	            		merchant.setUpdateTime(new Date());
	            		merchant.setUpdateUser(operator.getOperatorName());
	            		merchant.setIsRebate(Constant.MerchantRebateStatus.DISABLE);
	            		identityService.saveIdentity(merchant,IdentityType.MERCHANT.toString());
	            	}
	            }
	            
	            model.put("message", "操作成功");
	            model.put("canback", false);
	            model.put("next_url", "RebateRule/list");
	            model.put("next_msg", "返佣比例配置列表");
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:deleteRebateRule()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 编辑商户分组
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/editRebateRule", method = RequestMethod.GET)
    public String editRebateRule(@RequestParam("id")
    Long id, ModelMap model)
    {
        try
        {
            logger.debug("[RebateRuleController:editRebateRule(" + id + ")]");
            RebateRuleAssist rebateRuleAssist = rebateRuleService.queryRebateRuleById(id);
            RebateRule rebateRule = rebateRuleAssist.getRebateRule();
            List<RebateProductAssist> rebateProductList = new ArrayList<RebateProductAssist>();
            rebateProductList = rebateProductQueryManager.queryProductsByRProductId(rebateRule.getRebateProductId());
            List<RebateTradingVolume> rebateTradingVolumeList = new ArrayList<RebateTradingVolume>();
            rebateTradingVolumeList = rebateTradingVolumeService.queryRebateTradingVolumesByParams(rebateRule.getRebateRuleId());
            List<RebateRuleAssist> rebateRuleList = rebateRuleQueryManager.queryRebateRuleByMId(rebateRule.getMerchantId());
            List<String> rebateProductIds = new ArrayList<String>();
            for (RebateRuleAssist rebateRuleAssist2 : rebateRuleList)
            {
                RebateRule rebateRuleTemp = rebateRuleAssist2.getRebateRule();
                if (!rebateRuleTemp.getRebateRuleId().equals(id))
                {
                    rebateProductIds.add(rebateRuleTemp.getRebateProductId());
                }
            }
            List<RebateProductAssist> allConfigProductList = rebateProductQueryManager.queryProductsByRProductId(rebateProductIds);
            String tree = this.getProductByMerchantId(rebateRule.getMerchantId(),
                rebateRule.getMerchantType(), rebateProductList, allConfigProductList);
            model.addAttribute("rebateRule", rebateRuleAssist);
            model.addAttribute("productList", rebateProductList);
            model.addAttribute("tradingVolumeList", rebateTradingVolumeList);
            model.addAttribute("tree", tree);
            return PageConstant.PAGE_REBATE_RULE_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:editRebateRule()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 编辑商户分组
     * 
     * @param upurlrule
     * @param result
     * @return
     */
    @RequestMapping(value = "/editRebateRule", method = RequestMethod.POST)
    @ResponseBody
    public String editRebateRule(@RequestParam("rebateRuleId")
    Long rebateRuleId, @RequestParam("rebateTimeType")
    Long rebateTimeType, @RequestParam("status")
    String status, @RequestParam("rebateType")
    Long rebateType, @RequestParam("discount")
    String discount, @RequestParam("productIds")
    String productIds, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            logger.debug("[RebateRuleController:editRebateRule(" + rebateRuleId + ")]");
            if (BeanUtils.isNotNull(rebateRuleId))
            {
                RebateRuleAssist rebateRuleAssist = rebateRuleService.queryRebateRuleById(rebateRuleId);
                RebateRule rebateRuleNew = rebateRuleAssist.getRebateRule();
                rebateRuleNew.setRebateTimeType(rebateTimeType);
                rebateRuleNew.setRebateType(rebateType);
                rebateRuleNew.setStatus(status);
                rebateRuleNew.setUpdateUser(loginPerson.getOperatorName());
                rebateRuleNew.setUpdateDate(new Date());
                rebateRuleAssist = rebateRuleService.saveRebateRule(rebateRuleNew);
                if (BeanUtils.isNotNull(rebateRuleNew))
                {
                    Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                        rebateRuleNew.getMerchantId(), IdentityType.MERCHANT);
                    // 同步更新该商户配置此批产品的返佣周期
                    rebateRuleQueryManager.queryRebateRuleList(rebateRuleNew.getRebateProductId(),
                        rebateTimeType);
                    // 更新返佣产品列表
                    rebateProductService.saveRebateProductIds(rebateRuleNew.getRebateProductId(),
                        merchant, productIds);
                    // 更新返佣规则
                    rebateTradingVolumeService.saveRebateTradingVolume(
                        rebateRuleNew.getRebateRuleId(), discount);
                    return PageConstant.TRUE;
                }
            }
            return PageConstant.FALSE;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:editRebateRule()]" + e.getMessage());
            return PageConstant.FALSE;
        }
    }

    /**
     * 删除商户分组
     * 
     * @param upurlrule
     * @param result
     * @return merchant,productIds,rebateTimeType,params
     */
    @RequestMapping(value = "/changeRebateRuleStatus")
    public String changeRebateRuleStatus(@RequestParam("id")
    Long id, @RequestParam("status")
    String status, ModelMap model)
    {
        try
        {
            logger.debug("[RebateRuleController:changeRebateRuleStatus(" + id + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            rebateRuleService.updateRebateRule(loginPerson.getOperatorName(),id,status);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "RebateRule/list");
            model.put("next_msg", "返佣比例配置列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRuleController:changeRebateRuleStatus()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 组装在界面显示的交易区间格式
     * 
     * @param rebateRuleId
     * @param rebateTradingVolumeList
     * @return
     */
    public String getTrdingVolumeStr(Long rebateRuleId,
                                     List<RebateTradingVolume> rebateTradingVolumeList)
    {
        logger.debug("[RebateRuleController:getTrdingVolumeStr(" + rebateRuleId + ")]");
        String backStr = StringUtil.initString();
        if (rebateRuleId != null)
        {
            RebateRuleAssist rebateRuleAssist = rebateRuleService.queryRebateRuleById(rebateRuleId);
            RebateRule rebateRule = rebateRuleAssist.getRebateRule();
            for (RebateTradingVolume rebateTradingVolume : rebateTradingVolumeList)
            {
                String maxHigh = rebateTradingVolume.getTradingVolumeHigh().toString();
                if (rebateTradingVolume.getTradingVolumeHigh().equals(
                    Constant.RebateTransactionVolume.MAX_TRADING_VOLUME))
                {
                    maxHigh = "无穷";
                }
                backStr = backStr + "区间：<b>" + rebateTradingVolume.getTradingVolumeLow() + "~"
                          + maxHigh + "</b> 返佣比例：<b>"
                          + rebateTradingVolume.getDiscount().toString() + "</b>";
                if (rebateRule.getRebateType() == 0)
                {
                    backStr = backStr + "%</br>";
                }
                else
                {
                    backStr = backStr + "元</br>";
                }
            }
        }
        logger.debug("[RebateRuleController:getTrdingVolumeStr(" + backStr + ")][返回数据]");
        return backStr;
    }

    /**
     * 批量保存返佣规则信息
     * 
     * @param merchant
     * @param productIds
     * @param rebateTimeType
     * @param params
     * @return
     */
    public List<RebateRule> savaRebateRuleList(Merchant merchant, String productIds,
                                               String rebateTimeType, List<RebateJsonParam> params)
    {
        logger.debug("[RebateRuleController:savaRebateRuleList()]");
        String rebateProductId = rebateProductService.saveRebateProductIds(null, merchant,
            productIds);
        List<RebateRule> rebateRuleList = new ArrayList<RebateRule>();
        if (params != null)
        {
            int i = 0;
            while (i < params.size())
            {
                RebateJsonParam rebateJsonParam = params.get(i);
                if (rebateJsonParam.getRebateStatus().equals(Constant.RebateStatus.ENABLE))
                {
                    if (rebateJsonParam.getRebateMerchantId() != null
                        && !rebateJsonParam.getRebateMerchantId().isEmpty()
                        && rebateJsonParam.getRebateMerchantId() != Constant.Common.ZERO)
                    {
                        RebateRule rebateRule = saveRebateRule(rebateJsonParam, merchant,
                            new Long(rebateTimeType), rebateProductId);
                        rebateRuleList.add(rebateRule);
                    }
                }
                i++ ;
            }
            return rebateRuleList;
        }
        return null;
    }

    /**
     * 单独保存返佣规则信息
     * 
     * @param rebateJsonParam
     * @param merchant
     * @param rebateTimeType
     * @param rebateProductId
     * @return
     */
    public RebateRule saveRebateRule(RebateJsonParam rebateJsonParam, Merchant merchant,
                                     Long rebateTimeType, String rebateProductId)
    {
        com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
        Long rebateMerchantId = new Long(rebateJsonParam.getRebateMerchantId());
        Merchant rebateMerchant = (Merchant)identityService.findIdentityByIdentityId(
            rebateMerchantId, IdentityType.MERCHANT);
        RebateRule rebateRule = new RebateRule();
        rebateRule.setMerchantId(merchant.getId());
        rebateRule.setCreateDate(new Date());
        rebateRule.setStatus(rebateJsonParam.getRebateStatus());
        rebateRule.setRebateMerchantId(rebateMerchant.getId());
        rebateRule.setMerchantType(merchant.getMerchantType().toString());
        rebateRule.setRebateTimeType(rebateTimeType);
        rebateRule.setRebateType(new Long(rebateJsonParam.getRebateType()));
        rebateRule.setRebateProductId(rebateProductId);
        rebateRule.setCreateUser(loginPerson.getOperatorName());
        rebateRule.setStatus(Constant.RebateStatus.DISABLE);
        rebateRule.setUpdateDate(new Date());
        rebateRule.setUpdateUser(loginPerson.getOperatorName());
        RebateRuleAssist rebateRuleAssist = rebateRuleService.saveRebateRule(rebateRule);
        logger.debug("[RebateRuleController:savaRebateRule()]保存返佣规则" + rebateRuleAssist != null ? rebateRuleAssist.toString() : null);
        rebateTradingVolumeService.saveRebateTradingVolume(rebateRuleAssist.getRebateRule().getRebateRuleId(),
            rebateJsonParam.getRebateDiscounts());
        return rebateRule;
    }

    /**
     * list列表反转
     * 
     * @param merchantList
     * @return
     */
    public List<Merchant> changeListProt(List<Merchant> merchantList)
    {
        List<Merchant> merchantResultList = new ArrayList<Merchant>();
        int i = merchantList.size();
        while (i > 0)
        {
            i-- ;
            merchantResultList.add(merchantList.get(i));
        }
        return merchantResultList;
    }

    public String getProductByMerchantId(Long merchantId, String merchantType,
                                         List<RebateProductAssist> selectProductAssistList,
                                         List<RebateProductAssist> allConfigProductAssistList)
    {
        StringBuffer sb = new StringBuffer();
        List<RebateProduct> selectProductList = getRebateProduct(selectProductAssistList);
        List<RebateProduct> allConfigProductList = getRebateProduct(allConfigProductAssistList);
        if (merchantType.equals(MerchantType.AGENT.toString()))
        {
            sb = getAgentProductByMerchantId(merchantId, selectProductList, allConfigProductList);
        }
        else
        {
            sb = getSupplyProductByMerchantId(merchantId, selectProductList, allConfigProductList);
        }
        int size = sb.toString().length();
        if (size > 0)
        {
            logger.debug("[RebateRuleController:getProductByMerchantId()]获取未配置产品树结果:"
                         + sb.toString().substring(0, size - 1));
            return sb.toString().substring(0, size - 1);
        }
        else
        {
            return sb.toString();
        }
    }

    public List<RebateProduct> getRebateProduct(List<RebateProductAssist> rebateProductAssists)
    {
        if (null != rebateProductAssists)
        {
            List<RebateProduct> products = new ArrayList<RebateProduct>();
            for (RebateProductAssist rebateProductAssist : rebateProductAssists)
            {
                RebateProduct product = rebateProductAssist.getRebateProduct();
                products.add(product);
            }
            return products;
        }
        else
        {
            return null;
        }
    }

    /**
     * 将返佣产品列表转换成Map格式
     * 
     * @param selectProductList
     * @return
     */
    public Map<Long, RebateProduct> getMapRebateProduct(List<RebateProduct> selectProductList)
    {
        Map<Long, RebateProduct> checkProductList = new HashMap<Long, RebateProduct>();
        if (selectProductList != null)
        {
            for (RebateProduct rebateProduct : selectProductList)
            {
                checkProductList.put(rebateProduct.getProductId(), rebateProduct);
            }
        }
        return checkProductList;
    }

    /**
     * 组装树形结构图前台展示js字符
     * 
     * @param sb
     * @param id
     * @param pid
     * @param name
     * @param title
     * @param isOpen
     * @return
     */
    public StringBuffer getTreesStr(StringBuffer sb, Long id, Long pid, String name, String title,
                                    boolean isOpen)
    {
        sb.append("{ id:" + id + ", pId:" + pid + ", name:\"" + name + "\", t:\"" + title
                  + "\" ,open:" + isOpen + " },\n");
        return sb;
    }

    /**
     * 组装树形结构图前台展示js字符（是否选中）
     * 
     * @param checkProductList
     * @param productId
     * @param sb
     * @param id
     * @param pid
     * @param name
     * @param isCheck
     * @param isOpen
     * @return
     */
    public StringBuffer getTreesStr(Map<Long, RebateProduct> checkProductList, Long productId,
                                    StringBuffer sb, Long id, Long pid, String name,
                                    boolean isCheck, boolean isOpen)
    {
        if (checkProductList.containsKey(productId))
        {
            sb.append("{ id:" + id + ", pId:" + pid + ", name:\"" + name + "\", checked:"
                      + isCheck + ",open:" + isOpen + "},");
        }
        else
        {
            sb.append("{ id:" + id + ", pId:" + pid + ", name:\"" + name + "\", open:" + isOpen
                      + "},");
        }
        return sb;
    }


    public StringBuffer getAgentProductByMerchantId(Long merchantId,
                                                    List<RebateProduct> selectProductList,
                                                    List<RebateProduct> allConfigProductList)
    {
        StringBuffer sb = new StringBuffer();
        List<AgentProductRelation> merchantProducts = (List<AgentProductRelation>)downProductRelationService.getAllProductByAgentMerchantId(
            merchantId, IdentityType.MERCHANT.toString(), "");
        if (allConfigProductList != null)
        {
            merchantProducts = rebateProductQueryManager.queryNoConfigProducts(merchantProducts,
                allConfigProductList, MerchantType.AGENT);
        }
        Map<Long, AgentProductRelation> checkList = new HashMap<Long, AgentProductRelation>();
        for (AgentProductRelation merchantProduct : merchantProducts)
        {
            checkList.put(merchantProduct.getProductId(), merchantProduct);
        }
        Map<Long, RebateProduct> checkProductList = getMapRebateProduct(selectProductList);

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
                sb = getTreesStr(checkProductList, rootDpr.getProductId(), sb, rootDpr.getId(),
                    pid0, rootDpr.getProductName(), true, true);
            }
            List<AirtimeProduct> childProducts = productService.getChildProducts(rootProduct.getProductId());
            pid1=pid0;
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
                    sb = getTreesStr(checkProductList, childDpr.getProductId(), sb,
                        childDpr.getId(), pid1, childDpr.getProductName(), true, true);
                }
                List<AirtimeProduct> childProducts2 = productService.getChildProducts(childProduct.getProductId());
                if (pid1 != null)
                {
                    if (childDprId != null)
                    {
                    	if (pid1.compareTo(childDprId) != 0)
		                {
		                	pid2 = childDprId;
		                }else{
		                	pid2=pid1;
		                }
                    }else{
                    	pid2=pid1;
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
                        sb = getTreesStr(checkProductList, childDpr2.getProductId(), sb,
                            childDpr2.getId(), pid2, childDpr2.getProductName(), true, true);
                    }
                }
                pid2=null;
                pid1=pid0;
            }
            pid0=null;
        }
        return sb;
    }

    public StringBuffer getSupplyProductByMerchantId(Long merchantId,
                                                     List<RebateProduct> selectProductList,
                                                     List<RebateProduct> allConfigProductList)
    {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        List<SupplyProductRelation> merchantProducts = (List<SupplyProductRelation>)upProductRelationService.getAllProductBySupplyMerchantId(
            searchParams, merchantId, IdentityType.MERCHANT.toString(), "");
        if (allConfigProductList != null)
        {
            merchantProducts = rebateProductQueryManager.queryNoConfigProducts(merchantProducts,
                allConfigProductList, MerchantType.SUPPLY);
        }
        Map<Long, SupplyProductRelation> checkList = new HashMap<Long, SupplyProductRelation>();
        for (SupplyProductRelation merchantProduct : merchantProducts)
        {
            checkList.put(merchantProduct.getProductId(), merchantProduct);
        }
        Map<Long, RebateProduct> checkProductList = getMapRebateProduct(selectProductList);

        List<AirtimeProduct> rootProducts = productService.getAllRootProducts();
        Long pid0 = null;
        Long pid1 = null;
        Long pid2 = null;
        for (AirtimeProduct rootProduct : rootProducts)
        {
            if (checkList.containsKey(rootProduct.getProductId()))
            {
                SupplyProductRelation rootDpr = checkList.get(rootProduct.getProductId());
                pid0 = rootDpr.getId();
                sb = getTreesStr(checkProductList, rootDpr.getProductId(), sb, rootDpr.getId(),
                    pid0, rootDpr.getProductName(), true, true);
            }
            List<AirtimeProduct> childProducts = productService.getChildProducts(rootProduct.getProductId());
            pid1=pid0;
            for (AirtimeProduct childProduct : childProducts)
            {
                Long childDprId = null;
                if (checkList.containsKey(childProduct.getProductId()))
                {
                    SupplyProductRelation childDpr = checkList.get(childProduct.getProductId());
                    if (pid1 == null)
                    {
                        pid1 = childDpr.getId();
                    }
                    childDprId=childDpr.getId();
                    sb = getTreesStr(checkProductList, childDpr.getProductId(), sb,
                        childDpr.getId(), pid1, childDpr.getProductName(), true, true);
                }
                List<AirtimeProduct> childProducts2 = productService.getChildProducts(childProduct.getProductId());
                if (pid1 != null)
                {
                    if (childDprId != null)
                    {
                    	if (pid1.compareTo(childDprId) != 0)
		                {
		                	pid2 = childDprId;
		                }else{
		                	pid2=pid1;
		                }
                    }else{
                    	pid2=pid1;
                    }
                }
                for (AirtimeProduct childProduct2 : childProducts2)
                {
                    if (checkList.containsKey(childProduct2.getProductId()))
                    {
                        SupplyProductRelation childDpr2 = checkList.get(childProduct2.getProductId());
                        if (pid2 == null)
                        {
                            pid2 = childDpr2.getId();
                        }
                        sb = getTreesStr(checkProductList, childDpr2.getProductId(), sb,
                            childDpr2.getId(), pid2, childDpr2.getProductName(), true, true);
                    }
                }
                pid2=null;
                pid1=pid0;
            }
            pid0=null;
        }
        return sb;
    }
}
