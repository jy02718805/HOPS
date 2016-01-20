package com.yuecheng.hops.mportal.web.identity;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.blacklist.entity.Blacklist;
import com.yuecheng.hops.blacklist.service.BlacklistService;
import com.yuecheng.hops.common.BusinessTypeEnum;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.privilege.service.MenuService;


/**
 * 黑名单页面控制层
 * 
 * @author yao
 * @version 2015年5月26日
 * @see BlacklistController
 * @since
 */
@Controller
@RequestMapping(value = "/Blacklist")
public class BlacklistController
{
    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private MenuService menuService;

    private static Logger logger = LoggerFactory.getLogger(BlacklistController.class);

    /**
     * 进入黑名单添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBlacklist(ModelMap model)
    {
        return PageConstant.PAGE_BLACKLIST_ADD;
    }

    /**
     * 添加黑名单
     * 
     * @param blacklist
     * @param result
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveBlacklist(@RequestParam(value = "blacklistNo", defaultValue = "") String blacklistNo,
                                @RequestParam(value = "businessType", defaultValue = "0") String businessType,
                                @RequestParam(value = "remark", defaultValue = "") String remark,
                                ModelMap model)
    {
        // 先判断该业务类型的该黑名单是否已存在
        Blacklist existingBlacklist = blacklistService.findOne(blacklistNo, businessType);
        if (existingBlacklist != null)
        {
            String type = BusinessTypeEnum.getName(businessType);
            model.put("message", "操作失败：黑名单[ " + blacklistNo + "-" + type + " ]已存在！");
            model.put("canback", true);
        }
        // 不存在时，再进行添加
        else
        {
            try
            {
                Blacklist blacklist = new Blacklist();
                blacklist.setBlacklistNo(blacklistNo);
                blacklist.setBusinessType(businessType);
                blacklist.setRemark(remark);
                blacklist.setCreateTime(new Date());
                logger.debug("[BlacklistController:saveBlacklist(" + blacklist != null ? blacklist.toString() : null
                                                                                                                + ")]");
                blacklist = blacklistService.saveBlacklist(blacklist);
                if (blacklist != null)
                {
                    model.put("message", "操作成功");
                    model.put("canback", false);
                    model.put("next_url", "Blacklist/list");
                    model.put("next_msg", "黑名单列表");
                }
                else
                {
                    model.put("message", "操作失败");
                    model.put("canback", true);
                }
            }
            catch (RpcException e)
            {
                logger.debug("[BlacklistController:saveBlacklist()]" + e.getMessage());
                model.put("message", "操作失败" + e.getMessage());
                model.put("canback", true);
            }
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 删除黑名单
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/blacklist_delete", method = RequestMethod.GET)
    public String deleteBlacklist(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.debug("[BlacklistController:deleteBlacklist(" + id + ")]");
            blacklistService.deleteBlacklist(Long.valueOf(id));
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "Blacklist/list");
            model.put("next_msg", "号段列表");
        }
        catch (RpcException e)
        {
            logger.debug("[BlacklistController:deleteBlacklist()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入用户编辑界面
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/blacklist_edit", method = RequestMethod.GET)
    public String editBlacklist(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.debug("[BlacklistController:editBlacklist(" + id + ")]");
            Blacklist blacklist = blacklistService.findOne(Long.valueOf(id));
            model.addAttribute("blacklist", blacklist);
            return PageConstant.PAGE_BLACKLIST_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[BlacklistController:editBlacklist()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }

    }

    /**
     * 编辑用户后保存
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editBlacklistDo(@ModelAttribute("blacklist") Blacklist blacklist, ModelMap model)
    {
        try
        {
            logger.debug("[BlacklistController:editBlacklistDo(" + blacklist != null ? blacklist.toString() : null
                                                                                                              + ")]");
            Blacklist newBlacklist = blacklistService.findOne(blacklist.getBlacklistId());
            newBlacklist.setRemark(blacklist.getRemark());
            blacklist = blacklistService.saveBlacklist(newBlacklist);
            if (blacklist != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Blacklist/list");
                model.put("next_msg", "黑名单列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[BlacklistController:editBlacklistDo()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入用户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listBlacklist(@RequestParam(value = "blacklistNo", defaultValue = "") String blacklistNo,
                                 @RequestParam(value = "businessType", defaultValue = "") String businessType,
                                 @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                                 @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                                 ModelMap model, ServletRequest request)
    {
        try
        {
            logger.debug("[BlacklistController:listdBlacklist(" + blacklistNo + ")]");
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(blacklistNo))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.Blacklist.BLACKLIST_NO,
                    blacklistNo);
            }
            if (StringUtil.isNotBlank(businessType))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.Blacklist.BUSINESS_TYPE,
                    businessType);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, "createTime");
            YcPage<Blacklist> page_list = blacklistService.queryBlacklist(searchParams,
                pageNumber, pageSize, bsort);
            List<Blacklist> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("blacklistNo", blacklistNo);
            return PageConstant.PAGE_BLACKLIST_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[BlacklistController:listdBlacklist()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
}
