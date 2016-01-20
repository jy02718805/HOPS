package com.yuecheng.hops.mportal.web.account;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.rebate.service.RebateRecordService;
import com.yuecheng.hops.report.service.AgentTransactionReportService;
import com.yuecheng.hops.report.service.SupplyTransactionReportService;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;
import com.yuecheng.hops.transaction.config.entify.profitImputation.vo.ProfitImputationVo;
import com.yuecheng.hops.transaction.execution.imputation.ProfitImputationService;
import com.yuecheng.hops.transaction.service.order.OrderPageQuery;
import com.yuecheng.hops.transaction.service.profitImputation.ProfitImputationInfoService;


@Controller
@RequestMapping(value = "/profitImputation")
public class ProfitImputationController
{
    @Autowired
    ParameterConfigurationService parameterConfigurationService;

    @Autowired
    private CCYAccountService currencyAccountService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private ProfitImputationInfoService profitImputationInfoService;

    @Autowired
    private ProfitImputationService profitImputationService;

    @Autowired
    private RebateRecordService rebateRecordService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private MerchantStatusManagement merchantService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private OrderPageQuery orderPageQuery;

    @Autowired
    private SupplyTransactionReportService supplyTransactionReportService;

    @Autowired
    private AgentTransactionReportService agentTransactionReportService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitImputationController.class);

    /**
     * 查询账户归档 功能描述: <br> 参数说明: <br>
     */
    @RequestMapping(value = "/profitImputationList")
    public String profitImputationList(@RequestParam(value = "merchantName", defaultValue = "")
    String merchantName, @RequestParam(value = "imputationStatus", defaultValue = "")
    String imputationStatus, @RequestParam(value = "imputationBeginDate", defaultValue = "")
    String imputationBeginDate, @RequestParam(value = "imputationEndDate", defaultValue = "")
    String imputationEndDate, @RequestParam(value = "profitImputationId", defaultValue = "")
    String profitImputationId, @RequestParam(value = "updateBeginDate", defaultValue = "")
    String updateBeginDate, @RequestParam(value = "updateEndDate", defaultValue = "")
    String updateEndDate, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10")
    int pageSize, ModelMap model, ServletRequest request)
    {
        LOGGER.debug("进入利润归集查询: [ProfitImputationController:profitImputationList()][merchantName:"
                     + merchantName + ",imputationStatus :" + imputationStatus
                     + ",imputationBeginDate: " + imputationBeginDate + ",imputationEndDate"
                     + imputationEndDate + "]");
        if (StringUtil.isBlank(imputationBeginDate) || StringUtil.isBlank(imputationEndDate))
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            imputationBeginDate = format.format(DateUtil.getYesterdayBeginTime());
            imputationEndDate = format.format(DateUtil.getYesterdayEndTime());
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();

        searchParams.put(EntityConstant.ProfitImputation.UPDATE_BEGIN_DATE, updateBeginDate);
        searchParams.put(EntityConstant.ProfitImputation.UPDATE_END_DATE, updateEndDate);
        searchParams.put(EntityConstant.ProfitImputation.IMPUTATION_BEGIN_DATE,
            imputationBeginDate);
        searchParams.put(EntityConstant.ProfitImputation.IMPUTATION_END_DATE, imputationEndDate);
        searchParams.put(EntityConstant.ProfitImputation.MERCHANT_NAME, merchantName);
        searchParams.put(EntityConstant.ProfitImputation.IMPUTATION_STATUS, imputationStatus);

        YcPage<ProfitImputationVo> page_list = profitImputationInfoService.queryProfitImputationInfos(
            searchParams, pageNumber, pageSize);

        model.addAttribute("merchantName", merchantName);
        model.addAttribute("imputationStatus", imputationStatus);
        model.addAttribute("imputationBeginDate", imputationBeginDate);
        model.addAttribute("imputationEndDate", imputationEndDate);
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("profitImputationInfos", page_list.getList());
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        LOGGER.debug("结束利润归集查询: [ProfitImputationController:profitImputationList()]");
        return "account/profitImputationList";
    }

    /**
     * 确认利润详情页面 功能描述: <br> 参数说明: <br>
     */
    @RequestMapping(value = "/profitImputationInfo")
    public String profitImputationInfo(@RequestParam(value = "profitImputationId", defaultValue = "")
                                       String profitImputationId, ModelMap model,
                                       ServletRequest request)
    {
        LOGGER.debug("进入确认归集详情页面: [ProfitImputationController: profitImputationConfirm(profitImputationIds:"
                     + profitImputationId + ")]");
        try
        {
            ProfitImputationInfo profitImputationInfo = profitImputationInfoService.getProfitImputationInfoById(Long.valueOf(profitImputationId));
            ProfitImputationVo profitImputationVo = new ProfitImputationVo();
            BeanUtils.copyProperties(profitImputationVo, profitImputationInfo);
            AccountType accountType = accountTypeService.queryAccountTypeById(profitImputationVo.getProfitAccountTypeId());
            profitImputationVo.setProfitAccountType(accountType);
            profitImputationVo.setImputationBeginDate(profitImputationInfo.getImputationBeginDate());
            model.addAttribute("profitImputationVo", profitImputationVo);
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationController :profitImputationInfo(profitImputationId:"
                         + profitImputationId + ")] [报错:" + e.getMessage() + "]");
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        return "account/profitImputationInfo";
    }

    // 归集单条数据
    @RequestMapping(value = "/imputationProfit")
    @ResponseBody
    public String imputationProfit(@RequestParam(value = "merchantName", defaultValue = "")
    String merchantName, @RequestParam(value = "imputationStatus", defaultValue = "")
    String imputationStatus, @RequestParam(value = "imputationBeginDate", defaultValue = "")
    String imputationBeginDate, @RequestParam(value = "imputationEndDate", defaultValue = "")
    String imputationEndDate, @RequestParam(value = "profitImputationId", defaultValue = "")
    String profitImputationId, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10")
    int pageSize, ModelMap model, ServletRequest request)
    {
        LOGGER.debug("归集单条数据: [ProfitImputationController: imputationProfit(profitImputationId:"
                     + profitImputationId + ")]");
        String isImputation = Constant.TrueOrFalse.FALSE;
        try
        {
            ProfitImputationInfo profitImputationInfo = profitImputationInfoService.getProfitImputationInfoById(Long.valueOf(profitImputationId));
            if (profitImputationService.isAccountImputation(profitImputationInfo))
            {
                isImputation = Constant.TrueOrFalse.TRUE;
            }
            else
            {
                isImputation = Constant.TrueOrFalse.FALSE;
            }
        }
        catch (RpcException e)
        {
            LOGGER.error("[ProfitImputationController: imputationProfit][账户归集] [报错:"
                         + e.getMessage() + "]");
            isImputation = Constant.TrueOrFalse.FALSE;
        }
        LOGGER.debug("结束确认归集页面: [ProfitImputationController: imputationProfit(profitImputationId:"
                     + profitImputationId + ")]");
        return isImputation;
    }

    /**
     * 获得归集信息
     */

    // 账户归档
    @RequestMapping(value = "/profitImputations")
    @ResponseBody
    public String profitImputations(@RequestParam(value = "profitImputationIds", defaultValue = "")
                                    String profitImputationIds, ModelMap model,
                                    ServletRequest request)
    {
        LOGGER.debug("进入账户归档: [ProfitImputationController: profitImputations(profitImputationIds:"
                     + profitImputationIds + ")]");

        boolean isImputation = false;
        try
        {
            String[] ids = profitImputationIds.split("_");
            for (String id : ids)
            {
                LOGGER.debug("[ProfitImputationController: getProfitImputationInfoById(id:" + id
                             + ")]");

                ProfitImputationInfo profitImputationInfo = profitImputationInfoService.getProfitImputationInfoById(Long.valueOf(id));
                if (profitImputationInfo.getImputationStatus().equals(
                    Constant.ProfitImputation.WAIT_IMPUTATION))
                {
                    isImputation = profitImputationInfoService.isAccountImputation(profitImputationInfo);
                    if (isImputation)
                    {
                        isImputation = true;
                    }
                    else
                    {
                        isImputation = false;
                    }
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationController: profitImputations][账户归集 ] [报错:"
                         + e.getMessage() + "]");
            isImputation = false;
        }

        LOGGER.debug("结束账户归档: [ProfitImputationController: profitImputations(profitImputationIds:"
                     + profitImputationIds + ")]");
        return String.valueOf(isImputation);
    }

    /**
     * 重新清算
     */
    @RequestMapping(value = "/reImputationProfit")
    @ResponseBody
    public String reImputationProfit(@RequestParam(value = "profitImputationId", defaultValue = "")
                                     String profitImputationId, ModelMap model,
                                     ServletRequest request)
    {
        LOGGER.debug("进入重新清算: [ProfitImputationController: reImputationProfit(profitImputationId:"
                     + profitImputationId + ")]");
        String isReImputation = Constant.TrueOrFalse.FALSE;
        if (StringUtil.isNotBlank(profitImputationId))
        {
            // 重新计算
            try
            {
                profitImputationService.reImputationProfit(Long.valueOf(profitImputationId));
                isReImputation = Constant.TrueOrFalse.TRUE;
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "profitImputation/profitImputationList");
                model.put("next_msg", "利润归集列表");
            }
            catch (RpcException e)
            {
                LOGGER.error("重新清算结束: [ProfitImputationController: reImputationProfit(profitImputationId:"
                             + profitImputationId + ")]");
                isReImputation = Constant.TrueOrFalse.FALSE;
            }

        }
        LOGGER.debug("重新清算结束: [ProfitImputationController: reImputationProfit(profitImputationId:"
                     + profitImputationId + ")]");
        return isReImputation;
    }

    @RequestMapping(value = "/testa")
    public String testAccountSettlement(Model model, ServletRequest request)
    {
        profitImputationService.taskProfitImputation("2015-05-26 00:00:00", "2015-05-26 23:59:59");
        return null;
    }
}
