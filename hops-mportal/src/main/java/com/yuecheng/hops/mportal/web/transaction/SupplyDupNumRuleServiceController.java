package com.yuecheng.hops.mportal.web.transaction;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.parameter.entity.SupplyDupNumRule;
import com.yuecheng.hops.parameter.service.SupplyDupNumRuleService;
import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;


@Controller
@RequestMapping(value = "/supplyDupNumRule")
public class SupplyDupNumRuleServiceController
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private SupplyDupNumRuleService supplyDupNumRuleService;

    @Autowired
    private MerchantService merchantService;
    
    private static final Logger logger = LoggerFactory.getLogger(SupplyDupNumRuleServiceController.class);

    @RequestMapping(value = "/supplyDupNumRuleList")
    public String supplyDupNumRuleList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                         @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                                         @RequestParam(value = "sortType", defaultValue = "auto") String sortType,
                                         @RequestParam(value = "merchantName", defaultValue = "") String merchantName,
                                         ModelMap model, ServletRequest request)
    {
    	try{
	        Map<String, Object> searchParams = new HashMap<String, Object>();
	        if (StringUtils.isNotBlank(merchantName))
	        {
	            Map<String, Object> merchantMap = new HashMap<String, Object>();
                merchantMap.put(Operator.LIKE + "_" + EntityConstant.Merchant.MERCHANT_NAME, merchantName);
                List<Merchant> merchants = merchantService.queryMerchantList(merchantMap);
                if(BeanUtils.isNotNull(merchants) && merchants.size() > 0){
                    searchParams.put(Operator.EQ + "_" + EntityConstant.Order.MERCHANT_ID, merchants.get(0).getId().toString());
                }
	        }
	        YcPage<SupplyDupNumRule> supplyDupNumRule_list = supplyDupNumRuleService.querySupplyDupNumRule(
	            searchParams, pageNumber, pageSize, EntityConstant.AgentQueryFakeRule.ID);
	        List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
	
	        model.addAttribute("supplyMerchants", supplyMerchants);
	        model.addAttribute("merchantName", merchantName);
	        model.addAttribute("mlist", supplyDupNumRule_list.getList());
	        model.addAttribute("page", pageNumber);
	        model.addAttribute("counttotal", supplyDupNumRule_list.getCountTotal() + "");
	        model.addAttribute("pagetotal", supplyDupNumRule_list.getPageTotal() + "");
	        model.addAttribute("pageSize", pageSize);
	        
	        return "transaction/supplyDupNumRuleList";
    	}
        catch (RpcException e)
        {
            logger.debug("[AgentQueryFakeRuleController:agentQueryFakeRuleList()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[AgentQueryFakeRuleController:agentQueryFakeRuleList()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/toSaveSupplyDupNumRule")
    public String toSaveAgentQueryFakeRule(Model model)
    {
        List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
        model.addAttribute("supplyMerchants", supplyMerchants);
        return "transaction/toSaveSupplyDupNumRule";
    }

    @RequestMapping(value = "/toEditSupplyDupNumRule")
    public String toEditSupplyDupNumRule(@RequestParam(value = "merchantId", defaultValue = "-1") Long merchantId,
                                           Model model)
    {
        List<Merchant> supplyMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
        SupplyDupNumRule supplyDupNumRule = supplyDupNumRuleService.getSupplyDupNumRuleByMerchantId(merchantId);
        model.addAttribute("supplyMerchants", supplyMerchants);
        model.addAttribute("supplyDupNumRule", supplyDupNumRule);
        return "transaction/toEditSupplyDupNumRule";
    }

    @RequestMapping(value = "/doSupplyDupNumRule")
    public String doSaveSupplyDupNumRule(SupplyDupNumRule supplyDupNumRule, ModelMap model)
    {
        try
        {
            supplyDupNumRuleService.saveSupplyDupNumRule(supplyDupNumRule);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "supplyDupNumRule/supplyDupNumRuleList");
            model.put("next_msg", "供货商号码规则列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/doEditSupplyDupNumRule")
    public String doEditSupplyDupNumRule(SupplyDupNumRule supplyDupNumRule, ModelMap model)
    {
        try
        {
            supplyDupNumRuleService.updateSupplyDupNumRule(supplyDupNumRule);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "supplyDupNumRule/supplyDupNumRuleList");
            model.put("next_msg", "供货商号码规则列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/doDeleteSupplyDupNumRule")
    public String doDeleteSupplyDupNumRule(@RequestParam(value = "id") Long id, ModelMap model)
    {
        try
        {
            supplyDupNumRuleService.deleteSupplyDupNumRule(id);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "supplyDupNumRule/supplyDupNumRuleList");
            model.put("next_msg", "供货商号码规则列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

}
