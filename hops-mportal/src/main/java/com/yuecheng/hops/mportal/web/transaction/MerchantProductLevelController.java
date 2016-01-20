package com.yuecheng.hops.mportal.web.transaction;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.transaction.config.entify.product.MerchantProductLevel;
import com.yuecheng.hops.transaction.config.product.MerchantProductLevelService;


@Controller
@RequestMapping(value = "/MerchantLevel")
public class MerchantProductLevelController
{
    @Autowired
    private MerchantProductLevelService merchantProductLevelService;

    private static Logger logger = LoggerFactory.getLogger(MerchantProductLevelController.class);

    /**
     * 进入商户分组添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addMerchantLevel(ModelMap model)
    {
        try
        {
            return PageConstant.PAGE_MERCHANT_LEVEL_ADD;
        }
        catch (RpcException e)
        {
                logger.error("[MerchantProductLevelController:addMerchantLevel(GET)]"
                             + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加商户分组
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMerchantLevel(@ModelAttribute("merchantLevel") MerchantProductLevel merchantLevel,
                                   ModelMap model, BindingResult result)
    {
        try
        {
                logger.info("[MerchantProductLevelController:addMerchantLevel(" + merchantLevel != null ? merchantLevel.toString() : null
                                                                                                                                     + ")]");
            if (merchantLevel.getMerchantLevel() == 1)
            {
                merchantLevel.setMerchantName(Constant.MerchantProductLevelName.HIGH_PRODUCT);
            }
            else if (merchantLevel.getMerchantLevel() == 2)
            {
                merchantLevel.setMerchantName(Constant.MerchantProductLevelName.MEDIUM_PRODUCT);
            }
            else
            {
                merchantLevel.setMerchantName(Constant.MerchantProductLevelName.COMMON_PRODUCT);
            }
            merchantLevel = merchantProductLevelService.saveMerchantProductLevel(merchantLevel);
            if (merchantLevel != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "MerchantLevel/list");
                model.put("next_msg", "商户分组列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
                logger.error("[MerchantProductLevelController:addMerchantLevel(POST)]"
                             + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 删除商户分组
     * 
     * @param merchantLevel
     * @param result
     * @return
     */
    @RequestMapping(value = "/merchantlevel_delete", method = RequestMethod.GET)
    public String deleteMerchantLevel(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
                logger.info("[MerchantProductLevelController:deleteMerchantLevel(" + id + ")]");
            merchantProductLevelService.deleteMerchantProductLevel(new Long(id));
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "MerchantLevel/list");
            model.put("next_msg", "商户分组列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
                logger.error("[MerchantProductLevelController:deleteMerchantLevel()]"
                             + e.getMessage());
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
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listMerchantLevel(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "page.size", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                                    @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                                    ModelMap model, ServletRequest request)
    {
        try
        {
                logger.info("[MerchantProductLevelController:listMerchantLevel()]");
            Map<String, Object> searchParams = new HashMap<String, Object>();
            BSort bsort = new BSort(BSort.Direct.DESC,
                EntityConstant.MerchantProductLevel.MERCHANT_LEVEL);
            YcPage<MerchantProductLevel> page_list = merchantProductLevelService.queryMerchantProductLevel(
                searchParams, pageNumber, pageSize, bsort);
            List<MerchantProductLevel> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("mlist", list);
            if (list.size() > 1)
            {
                model.addAttribute("list", list.get(1));
            }
            else
            {
                model.addAttribute("list", 0);
            }
            model.addAttribute("page", pageNumber);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);

            // 根据用户名获取该用户的角色
            return PageConstant.PAGE_MERCHANT_LEVEL_LIST;
        }
        catch (RpcException e)
        {
                logger.error("[MerchantProductLevelController:listMerchantLevel()]"
                             + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入分配角色
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/merchantlevel_edit", method = RequestMethod.GET)
    public String editCustomer(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
                logger.info("[MerchantProductLevelController:editCustomer(" + id + ")]");
            MerchantProductLevel merchantLevel = merchantProductLevelService.getMerchantProductLevelById(new Long(
                id));
            model.addAttribute("merchantLevel", merchantLevel);
            return PageConstant.PAGE_MERCHANT_LEVEL_EDIT;
        }
        catch (RpcException e)
        {
                logger.error("[MerchantProductLevelController:editCustomer()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 编辑用户后保存
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editCustomerDo(@ModelAttribute("merchantLevel") MerchantProductLevel merchantLevel,
                                 ModelMap model)
    {
        try
        {
                logger.info("[MerchantProductLevelController:editCustomerDo(" + merchantLevel != null ? merchantLevel.toString() : null
                                                                                                                                   + ")]");
            if (merchantLevel.getMerchantLevel() == 1)
            {
                merchantLevel.setMerchantName(Constant.MerchantProductLevelName.HIGH_PRODUCT);
                // 同步修改下级的最低分流比
                MerchantProductLevel merchantLevelNext = merchantProductLevelService.getMerchantProductLevelByLevel(2l);
                merchantLevelNext.setOrderPercentagelow(merchantLevel.getOrderPercentageHigh());
                merchantProductLevelService.updateMerchantProductLevel(merchantLevelNext);
            }
            else if (merchantLevel.getMerchantLevel() == 2)
            {
                merchantLevel.setMerchantName(Constant.MerchantProductLevelName.MEDIUM_PRODUCT);
                // 同步修改上级的分流比
                MerchantProductLevel merchantLevelUp = merchantProductLevelService.getMerchantProductLevelByLevel(1l);
                merchantLevelUp.setOrderPercentageHigh(merchantLevel.getOrderPercentagelow());
                merchantProductLevelService.updateMerchantProductLevel(merchantLevelUp);
                // 同步修改下级的最低分流比
                MerchantProductLevel merchantLevelNext = merchantProductLevelService.getMerchantProductLevelByLevel(3l);
                merchantLevelNext.setOrderPercentagelow(merchantLevel.getOrderPercentageHigh());
                merchantProductLevelService.updateMerchantProductLevel(merchantLevelNext);
            }
            else
            {
                merchantLevel.setMerchantName(Constant.MerchantProductLevelName.COMMON_PRODUCT);
                // 同步修改上级的最高分流比
                MerchantProductLevel merchantLevelUp = merchantProductLevelService.getMerchantProductLevelByLevel(2l);
                merchantLevelUp.setOrderPercentageHigh(merchantLevel.getOrderPercentagelow());
                merchantProductLevelService.updateMerchantProductLevel(merchantLevelUp);
            }
            merchantLevel = merchantProductLevelService.updateMerchantProductLevel(merchantLevel);
            if (merchantLevel != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "MerchantLevel/list");
                model.put("next_msg", "产品等级分配");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
                logger.error("[MerchantProductLevelController:editCustomerDo()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
}
