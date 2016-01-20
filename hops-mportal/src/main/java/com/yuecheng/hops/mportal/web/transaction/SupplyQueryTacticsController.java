package com.yuecheng.hops.mportal.web.transaction;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.vo.transaction.UpQueryTacticsVO;
import com.yuecheng.hops.transaction.config.SupplyQueryTacticsService;
import com.yuecheng.hops.transaction.config.entify.query.SupplyQueryTactics;


@Controller
@RequestMapping(value = "/transaction")
public class SupplyQueryTacticsController
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private SupplyQueryTacticsService supplyQueryTacticsService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    IdentityService identityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyQueryTacticsController.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/setRuleList")
    public String setRuleList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                              @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
                              UpQueryTacticsVO upQueryTacticsVO, Model model,
                              ServletRequest request)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsController:setRuleList()] ");
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (!StringUtil.isNullOrEmpty(upQueryTacticsVO.getMerchantId()))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.SupplyQueryTactics.MERCHANT_ID,
                upQueryTacticsVO.getMerchantId());
        }

        YcPage<SupplyQueryTactics> page_list = new YcPage<SupplyQueryTactics>();
        List<Merchant> upMerchants = new ArrayList<Merchant>();
        try
        {

            page_list = supplyQueryTacticsService.querySupplyQueryTactics(searchParams,
                pageNumber, pageSize, EntityConstant.SupplyQueryTactics.ID);
            upMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("[" + format.format(new Date())
                             + "][SupplyQueryTacticsController:setRuleList()] " + e.getMessage());
            }
        }

        model.addAttribute("upMerchants", upMerchants);
        model.addAttribute("uqtList", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return PageConstant.PAGE_SETRULE_LIST;
    }

    /**
     * 显示设置规则
     * 
     * @return
     */
    @RequestMapping(value = "/showsaveSetRule")
    public String showsaveSetRule(@RequestParam(value = "merchantId") Long merchantId,
                                  @RequestParam(value = "id") Long id, Model model,
                                  ServletRequest request)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsController:showsaveSetRule()] ");
        }
        SupplyQueryTactics uqt = new SupplyQueryTactics();
        uqt.setId(id);
        uqt.setMerchantId(merchantId);
        Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantId,
            IdentityType.MERCHANT);
        SupplyQueryTactics uqts = supplyQueryTacticsService.saveSupplyQueryTactics(uqt);
        model.addAttribute("uqt", uqts);
        model.addAttribute("merchant", merchant);
        return PageConstant.PAGE_SETRULE_EDIT;
    }

    /**
     * 显示新增设置规则
     * 
     * @return
     */
    @RequestMapping(value = "/toAddSetRuleShow")
    public String showAddSetRule(@RequestParam(value = "merchantId", defaultValue = "0") Long merchantId,
                                 Model model, ServletRequest request)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsController:showAddSetRule()] ");
        }
        List<Merchant> upMerchants = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);
        SupplyQueryTactics uqt = null;
        model.addAttribute("merchantId", merchantId);
        model.addAttribute("uqt", uqt);
        model.addAttribute("upMerchants", upMerchants);
        return PageConstant.PAGE_SETRULE_ADD;
    }

    /**
     * 更新规则
     * 
     * @param uqt
     * @return
     */
    @RequestMapping(value = "/toSaveSetRule")
    public String saveSetRule(@ModelAttribute("UpQueryTactics") SupplyQueryTactics uqt,
                              Model model, ServletRequest request)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsController:saveSetRule()] ");
        }
        try
        {
            SupplyQueryTactics uqts = supplyQueryTacticsService.saveSupplyQueryTactics(uqt);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("[" + format.format(new Date())
                             + "][SupplyQueryTacticsController:saveSetRule()] " + e.getMessage());
            }
        }

        Long merchantId = uqt.getMerchantId();
        return "Merchant/merchantInterfaceConfList?merchantId=" + merchantId;
    }

    /**
     * 新增规则
     * 
     * @param uqt
     * @return
     */
    @RequestMapping(value = "/toAddSetRule")
    public String addSetRule(@ModelAttribute("UpQueryTactics") SupplyQueryTactics uqt,
                             Model model, ServletRequest request)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsController:addSetRule()] ");
        }
        try
        {
            SupplyQueryTactics uqts = supplyQueryTacticsService.saveSupplyQueryTactics(uqt);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("[" + format.format(new Date())
                             + "][SupplyQueryTacticsController:addSetRule()] " + e.getMessage());
            }
        }
        Long merchantId = uqt.getMerchantId();
        return "Merchant/merchantInterfaceConfList?merchantId=" + merchantId;
    }

    /**
     * 删除规则
     * 
     * @param uqt
     * @return
     */
    @RequestMapping(value = "/toDelSetRule")
    public String delSetRule(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "merchantId") Long merchantId)
    {
        SupplyQueryTactics uqt = new SupplyQueryTactics();
        if (BeanUtils.isNotNull(merchantId) && StringUtil.isNotBlank(merchantId.toString()))
        {
            uqt.setId(id);
            uqt.setMerchantId(merchantId);
        }
        supplyQueryTacticsService.delSupplyQueryTactics(uqt);
        return "redirect:/transaction/setRuleList";
    }

    /**
     * 时间区间控制
     * 
     * @param id
     * @param timeDifferenceLow
     * @param timeDifferenceHigh
     * @param model
     * @return
     */
    @RequestMapping(value = "/toRuleTime")
    @ResponseBody
    public String setRuleTime(@RequestParam(value = "id", defaultValue = "0") Long id,
                              @RequestParam(value = "merchantId") Long merchantId,
                              @RequestParam(value = "intervalUnit") String intervalUnit,
                              @RequestParam(value = "timeDifferenceLow") String timeDifferenceLow,
                              @RequestParam(value = "timeDifferenceHigh") String timeDifferenceHigh,
                              ModelMap model)
    {
        SupplyQueryTactics uqt = new SupplyQueryTactics();
        uqt.setId(id);
        uqt.setMerchantId(merchantId);
        // 时间区间
        Long timeDLow = Long.valueOf(timeDifferenceLow);
        Long timeDHigh = Long.valueOf(timeDifferenceHigh);
        List<SupplyQueryTactics> list = supplyQueryTacticsService.querySupplyQueryTactics(uqt);
        for (SupplyQueryTactics ut : list)
        {
            if (ut.getIntervalUnit().equals(intervalUnit))
            {
                if (ut.getId().equals(id))
                {
                    continue;
                }
                if (timeDHigh <= ut.getTimeDifferenceHigh()
                    && timeDHigh >= ut.getTimeDifferenceLow())
                {
                    return PageConstant.FALSE;
                }
                if (timeDLow <= ut.getTimeDifferenceHigh()
                    && timeDLow >= ut.getTimeDifferenceLow())
                {
                    return PageConstant.FALSE;
                }
            }
        }
        return PageConstant.TRUE;
    }

    @RequestMapping(value = "/toBackSetRules")
    public String backSetRules(@RequestParam(value = "merchantId") Long merchantId)
    {

        return "redirect:/transaction/setRuleList?merchantId=" + merchantId;
    }

    @RequestMapping(value = "/saveOrUpdateQueryRule")
    public String saveOrUpdateQueryRule(@RequestParam(value = "merchantId") Long merchantId,
                                        ModelMap model)
    {
        SupplyQueryTactics upQueryTactics = supplyQueryTacticsService.querySupplyQueryTacticsByMerchantId(merchantId);
        if (upQueryTactics != null)
        {
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantId,
                IdentityType.MERCHANT);
            model.addAttribute("uqt", upQueryTactics);
            model.addAttribute("merchant", merchant);
            return PageConstant.PAGE_SETRULE_EDIT;
        }
        else
        {
            return "redirect:/transaction/toAddSetRuleShow?merchantId=" + merchantId;
        }
    }
}
