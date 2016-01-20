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
import com.yuecheng.hops.injection.entity.InterfaceSendtimesConf;
import com.yuecheng.hops.injection.service.InterfaceSendtimesConfService;
import com.yuecheng.hops.mportal.constant.PageConstant;


@Controller
@RequestMapping(value = "/InterfaceSendtimesConf")
public class InterfaceSendtimesConfControl
{
    @Autowired
    private InterfaceSendtimesConfService interfaceSendtimesConfService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    IdentityService identityService;

    private static Logger logger = LoggerFactory.getLogger(InterfaceSendtimesConfControl.class);

    /**
     * 进入添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addinterfacetimes", method = RequestMethod.GET)
    public String addInterfaceSendtimesConf(ModelMap model)
    {
        try
        {
            List<AbstractIdentity> merchantList = identityService.getAllIdentityList(IdentityType.MERCHANT);
            model.addAttribute("merchantList", merchantList);
            return PageConstant.PAGE_INTERFACE_SENDTIMES_CONF_ADD;
        }
        catch (RpcException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("[InterfaceSendtimesConfControl:addInterfaceSendtimesConf(GET)]"
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
    @RequestMapping(value = "/addinterfacetimes", method = RequestMethod.POST)
    public String addInterfaceSendtimesConf(@RequestParam("merchantId") String merchantId,
                                            @RequestParam("interfaceType") String interfaceType,
                                            @RequestParam("totalTimes") String totalTimes,
                                            ModelMap model)
    {
        try
        {
            Long merchantid = new Long(merchantId);
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantid,
                IdentityType.MERCHANT);
            InterfaceSendtimesConf interfaceSendtimesConf = new InterfaceSendtimesConf();
            interfaceSendtimesConf.setMerchantId(merchantid);
            interfaceSendtimesConf.setInterfaceType(interfaceType);
            interfaceSendtimesConf.setTotalTimes(new Long(totalTimes));
            if (merchant != null && merchant.getId() != null)
            {
                interfaceSendtimesConf.setMerchantName(merchant.getMerchantName());
            }
            interfaceSendtimesConf = interfaceSendtimesConfService.saveInterfaceSendtimesConf(interfaceSendtimesConf);
            if (interfaceSendtimesConf != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "InterfaceSendtimesConf/list");
                model.put("next_msg", "发送次数配置列表");
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
                logger.debug("[InterfaceSendtimesConfControl:addInterfaceSendtimesConf(GET)]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/deleteinterfacetimes")
    public String deleteInterfaceSendtimes(@RequestParam("id") Long id, ModelMap model)
    {
        try
        {
            if (logger.isInfoEnabled())
            {
                logger.info("[InterfaceSendtimesController:deleteInterfaceSendtimes(" + id + ")]");
            }
            interfaceSendtimesConfService.deleteInterfaceSendtimesConf(id);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "InterfaceSendtimesConf/list");
            model.put("next_msg", "发送次数配置列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("[InterfaceSendtimesController:deleteInterfaceSendtimes()]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/editinterfacetimes", method = RequestMethod.GET)
    public String editInterfaceSendtimes(@RequestParam("id") Long id, ModelMap model)
    {
        try
        {
            if (logger.isInfoEnabled())
            {
                logger.info("[InterfaceSendtimesController:editInterfaceSendtimes(" + id + ")]");
            }
            InterfaceSendtimesConf interfaceTimes = interfaceSendtimesConfService.getInterfaceSendtimesConfById(id);
            List<AbstractIdentity> merchantList = identityService.getAllIdentityList(IdentityType.MERCHANT);
            model.addAttribute("merchantList", merchantList);
            model.addAttribute("interfaceTimes", interfaceTimes);
            return PageConstant.PAGE_INTERFACE_SENDTIMES_CONF_EDIT;
        }
        catch (RpcException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("[InterfaceSendtimesController:editInterfaceSendtimes()]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/editinterfacetimes", method = RequestMethod.POST)
    public String editInterfaceSendtimes(@RequestParam("id") Long id,
                                         @RequestParam("merchantId") String merchantId,
                                         @RequestParam("interfaceType") String interfaceType,
                                         @RequestParam("totalTimes") String totalTimes,
                                         ModelMap model)
    {
        try
        {
            if (logger.isInfoEnabled())
            {
                logger.info("[InterfaceSendtimesController:editInterfaceSendtimes(" + id + ")]");
            }
            InterfaceSendtimesConf interfaceTimes = interfaceSendtimesConfService.getInterfaceSendtimesConfById(id);
            Long merchantid = new Long(merchantId);
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantid,
                IdentityType.MERCHANT);
            interfaceTimes.setInterfaceType(interfaceType);
            interfaceTimes.setMerchantId(merchantid);
            interfaceTimes.setMerchantName(merchant.getMerchantName());
            interfaceTimes.setTotalTimes(new Long(totalTimes));
            interfaceTimes = interfaceSendtimesConfService.updateInterfaceSendtimesConf(interfaceTimes);
            if (interfaceTimes != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "InterfaceSendtimesConf/list");
                model.put("next_msg", "发送次数配置列表");
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
                logger.debug("[InterfaceSendtimesController:editInterfaceSendtimes()]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/list")
    public String listInterfaceSendtimes(@RequestParam(value = "merchantId", defaultValue = "") String merchantId,
                                         @RequestParam(value = "interfaceType", defaultValue = "") String interfaceType,
                                         @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                         @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                                         @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                                         ModelMap model, ServletRequest request)
    {
        try
        {
            if (logger.isInfoEnabled())
            {
                logger.info("[InterfaceSendtimesController:listInterfaceSendtimes()]");
            }
            List<AbstractIdentity> merchantList = identityService.getAllIdentityList(IdentityType.MERCHANT);
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (!StringUtil.isNullOrEmpty(merchantId))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.InterfaceSendtimesConf.MERCHANT_ID, merchantId);
            }
            if (!StringUtil.isNullOrEmpty(interfaceType))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.InterfaceSendtimesConf.INTERFACE_TYPE,
                    interfaceType);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.InterfaceSendtimesConf.ID);
            YcPage<InterfaceSendtimesConf> page_list = (YcPage<InterfaceSendtimesConf>)interfaceSendtimesConfService.queryInterfaceSendtimesConf(
                searchParams, pageNumber, pageSize, bsort);
            List<InterfaceSendtimesConf> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("merchantList", merchantList);
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);

            // 根据用户名获取该用户的角色
            return PageConstant.PAGE_INTERFACE_SENDTIMES_CONF_LIST;
        }
        catch (RpcException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("[InterfaceSendtimesController:listInterfaceSendtimes()]"
                             + e.getMessage());
            }
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
}
