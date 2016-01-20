package com.yuecheng.hops.mportal.web.injection;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.entity.InterfaceConstant;
import com.yuecheng.hops.injection.service.InterfaceConstantService;
import com.yuecheng.hops.mportal.constant.PageConstant;


@Controller
@RequestMapping(value = "/InterfaceConstant")
public class InterfaceConstantControl
{
    @Autowired
    private InterfaceConstantService interfaceConstantService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    IdentityService identityService;

    private static Logger logger = LoggerFactory.getLogger(InterfaceConstantControl.class);

    /**
     * 进入添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addinterfaceconstant", method = RequestMethod.GET)
    public String addInterfaceConstant(ModelMap model)
    {
        try
        {
            List<AbstractIdentity> merchantList = identityService.getAllIdentityList(IdentityType.MERCHANT);
            model.addAttribute("merchantList", merchantList);
            return PageConstant.PAGE_INTERFACE_CONSTANT_ADD;
        }
        catch (RpcException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("[InterfaceConstantControl:addInterfaceConstant(GET)]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addinterfaceconstant", method = RequestMethod.POST)
    public String addInterfaceConstant(@RequestParam("identityId")
    String identityId, @RequestParam("identityType")
    String identityType, @RequestParam("key")
    String key, @RequestParam("value")
    String value, ModelMap model)
    {
        try
        {
            Long merchantid = new Long(identityId);
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantid,
                IdentityType.MERCHANT);
            InterfaceConstant interfaceConstant = new InterfaceConstant();
            interfaceConstant.setIdentityId(merchantid);
            interfaceConstant.setIdentityType(identityType);
            interfaceConstant.setKey(key);
            interfaceConstant.setValue(value);
            if (merchant != null && merchant.getId() != null)
            {
                interfaceConstant.setIdentityName(merchant.getMerchantName());
            }
            interfaceConstant = interfaceConstantService.saveInterfaceConstant(interfaceConstant);
            if (interfaceConstant != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "InterfaceConstant/list");
                model.put("next_msg", "接口常量配置列表");
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
        }
        catch (RpcException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("[InterfaceConstantControl:addInterfaceConstant(GET)]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/deleteinterfaceconstant")
    public String deleteInterfaceConstant(@RequestParam("id")
    Long id, ModelMap model)
    {
        try
        {
            if (logger.isInfoEnabled())
            {
                logger.info("[InterfaceConstantController:deleteInterfaceConstant(" + id + ")]");
            }
            interfaceConstantService.deleteInterfaceConstant(id);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "InterfaceConstant/list");
            model.put("next_msg", "接口常量配置列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("[InterfaceConstantController:deleteInterfaceConstant()]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/editinterfaceconstant", method = RequestMethod.GET)
    public String editInterfaceConstant(@RequestParam("id")
    Long id, ModelMap model)
    {
        try
        {
            logger.info("[InterfaceConstantController:editInterfaceConstant(" + id + ")]");
            InterfaceConstant interfaceConstant = interfaceConstantService.getInterfaceConstantById(id);
            List<AbstractIdentity> merchantList = identityService.getAllIdentityList(IdentityType.MERCHANT);
            model.addAttribute("merchantList", merchantList);
            model.addAttribute("interfaceConstant", interfaceConstant);
            return PageConstant.PAGE_INTERFACE_CONSTANT_EDIT;
        }
        catch (RpcException e)
        {
            logger.error("[InterfaceConstantController:editInterfaceConstant()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/editinterfaceconstant", method = RequestMethod.POST)
    public String editInterfaceConstant(@RequestParam("id")
    Long id, @RequestParam("identityId")
    String identityId, @RequestParam("identityType")
    String identityType, @RequestParam("key")
    String key, @RequestParam("value")
    String value, ModelMap model)
    {
        try
        {
            logger.info("[InterfaceConstantController:editInterfaceConstant(" + id + ")]");
            InterfaceConstant interfaceConstant = interfaceConstantService.getInterfaceConstantById(id);
            Long merchantid = new Long(identityId);
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantid,
                IdentityType.MERCHANT);
            interfaceConstant.setIdentityId(merchantid);
            interfaceConstant.setIdentityType(identityType);
            interfaceConstant.setKey(key);
            interfaceConstant.setValue(value);
            if (merchant != null && merchant.getId() != null)
            {
                interfaceConstant.setIdentityName(merchant.getMerchantName());
            }
            interfaceConstant = interfaceConstantService.updateInterfaceConstant(interfaceConstant);
            if (interfaceConstant != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "InterfaceConstant/list");
                model.put("next_msg", "接口常量配置列表");
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
        }
        catch (RpcException e)
        {
            logger.error("[InterfaceConstantController:editInterfaceConstant()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/list")
    public String listInterfaceConstant(@RequestParam(value = "merchantId", defaultValue = "0")
    String merchantId, @RequestParam(value = "identityType", defaultValue = "")
    String identityType, @RequestParam(value = "key", defaultValue = "")
    String key, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            logger.info("[InterfaceConstantController:listInterfaceConstant()]");
            key = new String(key.getBytes("ISO-8859-1"), "UTF-8");
            List<AbstractIdentity> merchantList = identityService.getAllIdentityList(IdentityType.MERCHANT);
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (!StringUtil.isNullOrEmpty(merchantId) && !"0".equalsIgnoreCase(merchantId))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.InterfaceConstant.IDENTITY_ID,
                    merchantId);
            }
            if (StringUtil.isNotBlank(identityType))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.InterfaceConstant.IDENTITY_TYPE, identityType);
            }
            if (!StringUtil.isNullOrEmpty(key))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.InterfaceConstant.KEY, key);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.InterfaceConstant.ID);
            YcPage<InterfaceConstant> page_list = (YcPage<InterfaceConstant>)interfaceConstantService.queryInterfaceConstant(
                searchParams, pageNumber, pageSize, bsort);
            List<InterfaceConstant> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("merchantId", Long.valueOf(merchantId));
            model.addAttribute("merchantList", merchantList);
            model.addAttribute("identityType", identityType);
            model.addAttribute("key", key);
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);

            // 根据用户名获取该用户的角色
            return PageConstant.PAGE_INTERFACE_CONSTANT_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[InterfaceConstantController:listInterfaceConstant()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[InterfaceConstantController:listInterfaceConstant()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
}
