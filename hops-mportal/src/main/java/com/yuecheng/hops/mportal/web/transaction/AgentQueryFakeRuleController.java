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

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.transaction.config.AgentQueryFakeRuleService;
import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;


@Controller
@RequestMapping(value = "/agentQueryFakeRule")
public class AgentQueryFakeRuleController
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private AgentQueryFakeRuleService agentQueryFakeRuleService;

    @Autowired
    private MerchantService merchantService;
    
    private static final Logger logger = LoggerFactory.getLogger(AgentQueryFakeRuleController.class);

    @RequestMapping(value = "/agentQueryFakeRuleList")
    public String agentQueryFakeRuleList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                         @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                                         @RequestParam(value = "sortType", defaultValue = "auto") String sortType,
                                         @RequestParam(value = "merchantName", defaultValue = "") String merchantName,
                                         ModelMap model, ServletRequest request)
    {
    	try{
	        Map<String, Object> searchParams = new HashMap<String, Object>();
	        if (StringUtils.isNotBlank(merchantName))
	        {
	            searchParams.put(EntityConstant.Merchant.MERCHANT_NAME, merchantName);
	        }
	        BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.AgentQueryFakeRule.ID);
	        YcPage<AgentQueryFakeRule> fakeRule_list = agentQueryFakeRuleService.queryAgentQueryFakeRule(
	            searchParams, pageNumber, pageSize, bsort);
	        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT,null);
	
	        model.addAttribute("downMerchants", downMerchants);
	        model.addAttribute("merchantName", merchantName);
	        model.addAttribute("mlist", fakeRule_list.getList());
	        model.addAttribute("page", pageNumber);
	        model.addAttribute("counttotal", fakeRule_list.getCountTotal() + "");
	        model.addAttribute("pagetotal", fakeRule_list.getPageTotal() + "");
	        model.addAttribute("pageSize", pageSize);
	        
	        return "transaction/fakeRuleList";
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

    @RequestMapping(value = "/toSaveAgentQueryFakeRule")
    public String toSaveAgentQueryFakeRule(Model model)
    {
        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT,null);
        model.addAttribute("downMerchants", downMerchants);
        return "transaction/toSaveFakeRule";
    }

    @RequestMapping(value = "/toEditAgentQueryFakeRule")
    public String toEditAgentQueryFakeRule(@RequestParam(value = "merchantId", defaultValue = "-1") Long merchantId,
                                           Model model)
    {
        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT,null);
        AgentQueryFakeRule fakeRule = agentQueryFakeRuleService.queryAgentQueryFakeRuleById(merchantId);
        model.addAttribute("downMerchants", downMerchants);
        model.addAttribute("fakeRule", fakeRule);
        return "transaction/toEditFakeRule";
    }

    @RequestMapping(value = "/doSaveAgentQueryFakeRule")
    public String doSaveAgentQueryFakeRule(AgentQueryFakeRule downQueryFakeRule, ModelMap model)
    {
        try
        {
            agentQueryFakeRuleService.save(downQueryFakeRule);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "agentQueryFakeRule/agentQueryFakeRuleList");
            model.put("next_msg", "代理商预成功配置");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/doEditAgentQueryFakeRule")
    public String doEditAgentQueryFakeRule(AgentQueryFakeRule downQueryFakeRule, ModelMap model)
    {
        try
        {
            agentQueryFakeRuleService.save(downQueryFakeRule);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "agentQueryFakeRule/agentQueryFakeRuleList");
            model.put("next_msg", "代理商预成功配置");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/doDeleteAgentQueryFakeRule")
    public String doDeleteAgentQueryFakeRule(@RequestParam(value = "merchantId") Long merchantId, ModelMap model)
    {
        try
        {
            agentQueryFakeRuleService.deleteAgentQueryFakeRule(merchantId);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "agentQueryFakeRule/agentQueryFakeRuleList");
            model.put("next_msg", "代理商预成功配置");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

}
