package com.yuecheng.hops.mportal.web.rebate;


import java.math.BigDecimal;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.vo.rebate.RebateRecordHistoryVo;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.rebate.entity.RebateRecord;
import com.yuecheng.hops.rebate.entity.RebateRecordHistory;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordAssist;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordHistoryAssist;
import com.yuecheng.hops.rebate.service.RebateRecordHistoryService;
import com.yuecheng.hops.rebate.service.RebateRecordService;


@Controller
@RequestMapping(value = "/rebateRecordHistory")
public class RebateRecordHistoryController extends BaseControl
{
    @Autowired
    private RebateRecordHistoryService rebateRecordHistoryService;

    @Autowired
    private RebateRecordService rebateRecordService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantStatusManagement merchantStatusManagement;

    @Autowired
    private CCYAccountService currencyAccountService;

    @Autowired
    private SpService spService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RebateRecordHistoryController.class);
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @RequestMapping(value = "/rebateRecordHistoryList")
    public final String rebateRecordHistoryList(@RequestParam(value = "page", defaultValue = "1")
                                                final int pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                                                final int pageSize,
                                                @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
                                                final String sortType,
                                                final RebateRecordHistoryVo rebateRecordHistoryVo,
                                                final ModelMap model, final ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            Date endTime=null;
            Date beginTime=null;
            //默认前一天时间 （别删）
//            if (StringUtil.isBlank(rebateRecordHistoryVo.getRebateStartDate())&&StringUtil.isBlank(rebateRecordHistoryVo.getRebateEndDate()))
//            {
//            	beginTime = DateUtil.getYesterdayBeginTime();
//            	endTime = DateUtil.getYesterdayEndTime();
//            	rebateRecordHistoryVo.setRebateStartDate(format.format(beginTime));
//            	rebateRecordHistoryVo.setRebateEndDate(format.format(endTime));
//            }
            if (BeanUtils.isNotNull(rebateRecordHistoryVo.getMerchantId()))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.RebateRecordHistory.MERCHANT_ID,
                    rebateRecordHistoryVo.getMerchantId().toString());
            }
            if (BeanUtils.isNotNull(rebateRecordHistoryVo.getRebateMerchantId()))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.RebateRecordHistory.REBATE_MERCHANT_ID,
                    rebateRecordHistoryVo.getRebateMerchantId().toString());
            }
            if (StringUtil.isNotBlank(rebateRecordHistoryVo.getRebateStartDate()))
            {
                beginTime=format.parse(rebateRecordHistoryVo.getRebateStartDate());
                beginTime=DateUtil.getDateStart(beginTime);
            }
            if (StringUtil.isNotBlank(rebateRecordHistoryVo.getRebateEndDate()))
            {
                endTime=format.parse(rebateRecordHistoryVo.getRebateEndDate());
                endTime=DateUtil.getDateEnd(endTime);
            }
            if (StringUtil.isNotBlank(rebateRecordHistoryVo.getRebateStatus())
                && rebateRecordHistoryVo.getRebateStatus().length() > 0)
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.RebateRecordHistory.REBATE_STATUS,
                                 rebateRecordHistoryVo.getRebateStatus());
            }
            if (StringUtil.isNotBlank(rebateRecordHistoryVo.getBalanceStatus())
                && rebateRecordHistoryVo.getBalanceStatus().length() > 0)
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.RebateRecordHistory.BALANCE_STATUS,
                                 rebateRecordHistoryVo.getBalanceStatus());
            }
            if (BeanUtils.isNotNull(rebateRecordHistoryVo.getRebateRecordHistoryId()))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.RebateRecordHistory.ID,
                    rebateRecordHistoryVo.getRebateRecordHistoryId().toString());
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.RebateRecord.ID);
            YcPage<RebateRecordHistoryAssist> page_list = rebateRecordHistoryService.queryPageRebateRecordHistory(
                searchParams, pageNumber, pageSize, bsort,beginTime,endTime);
            List<Merchant> rebateMerchants = merchantStatusManagement.queryMerchantByIsRebate(true);
            List<Merchant> agentMerchant = merchantService.queryAllMerchant(MerchantType.AGENT,null);
            List<Merchant> supplyMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);

            List<Merchant> allMerchant = new ArrayList<Merchant>();
            allMerchant.addAll(agentMerchant);
            allMerchant.addAll(supplyMerchant);
            model.addAttribute("rebateMerchants", rebateMerchants);
            model.addAttribute("merchants", allMerchant);
            model.addAttribute("mlist", page_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("counttotal", page_list.getCountTotal());
            model.addAttribute("pagetotal", page_list.getPageTotal());
            model.addAttribute("rebateRecordHistory",rebateRecordHistoryVo);
            return PageConstant.PAGE_REBATE_HISTORY_LIST;
        }
        catch (RpcException e)
        {
                LOGGER.debug("[RebateRecordHistoryController:rebateRecordHistoryList()]"
                             + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
                LOGGER.debug("[RebateRecordHistoryController:rebateRecordHistoryList()]"
                             + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
    @RequestMapping(value = "/deleteRebateRecordHistory")
    public String deleteRebateRecordHistory(@RequestParam(value = "id", defaultValue = "0l")
    final Long id, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (id != 0l)
            {
            	BigDecimal zero= new BigDecimal("0");
            	BigDecimal zeroO= new BigDecimal("0.0000");
                RebateRecordHistoryAssist rebateRecordHistoryAssist = rebateRecordHistoryService.queryRebateRecordHistoryById(id);
                RebateRecordHistory rebateRecordHistory=rebateRecordHistoryAssist.getRebateRecordHistory();
                // 代理商需返回资金 资金为0时可以不走账户回滚流程
                if (!zero.equals(rebateRecordHistory.getRebateAmt())&&!zeroO.equals(rebateRecordHistory.getRebateAmt())&&MerchantType.AGENT.toString().equals(rebateRecordHistory.getMerchantType()))
                {
                    // 转帐（资金回滚：应付账户到系统中间利润户）--------------------------------------------
                    String desc = "应付账户  回退到  系统中间账户   金额为:[" + rebateRecordHistory.getRebateAmt()
                                  + "]";
                    SP sp = spService.getSP();
                    
                    rebateRecordService.doRebateTransfer(rebateRecordHistory.getId(),sp.getId(), sp.getIdentityType().toString(),
                        Constant.AccountType.REBATE_DEAL, sp.getId(),
                        sp.getIdentityType().toString(),
                        Constant.AccountType.SYSTEM_MIDDLE_PROFIT,
                        rebateRecordHistory.getRebateAmt(),Constant.TransferType.TRANSFER_REBATE_AMT_ROLLBACK, desc);
                }
                String ids = rebateRecordHistory.getRemark();
                if (StringUtil.isNotBlank(ids))
                {
                    // 之前生成的数据恢复状态
                    String[] idList = ids.split("\\|");
                    int i = 0;
                    while (i < idList.length)
                    {
                        RebateRecordAssist rebateRecordAssist = rebateRecordService.queryRebateRecordById(new Long(
                            idList[i]));
                        RebateRecord rebateRecord=rebateRecordAssist.getRebateRecord();
                        rebateRecord.setStatus(Constant.RebateStatus.DISABLE);
                        rebateRecord.setUpdateDate(new Date());
                        rebateRecord.setUpdateUser(loginPerson.getOperatorName());
                        rebateRecordService.updateRebateRecord(rebateRecord);
                        i++ ;
                    }
                }
                // 逻辑删除
                rebateRecordHistory.setStatus(Constant.RebateStatus.DEL_STATUS);
                rebateRecordHistoryService.saveRebateRecordHistory(rebateRecordHistory);
            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "rebateRecordHistory/rebateRecordHistoryList");
            model.put("next_msg", "返佣记录列表");
        }
        catch (RpcException e)
        {
                LOGGER.debug("[RebateRecordHistoryController:deleteRebateRecordHistory()]"
                             + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/acturlRebate", method = RequestMethod.GET)
    public String acturlRebate(@RequestParam(value = "id", defaultValue = "0l")
    final Long id, ModelMap model)
    {
        try
        {
            RebateRecordHistoryAssist rebateRecordHistoryAssist = new RebateRecordHistoryAssist();
            if (id != 0l)
            {
                rebateRecordHistoryAssist = rebateRecordHistoryService.queryRebateRecordHistoryById(id);
            }
            model.addAttribute("rebateRecordHistory", rebateRecordHistoryAssist);
            return PageConstant.PAGE_REBATE_HISTORY_ACTURL_REBATE;
        }
        catch (RpcException e)
        {
                LOGGER.debug("[RebateRecordHistoryController:acturlRebate()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/acturlRebate", method = RequestMethod.POST)
    public String acturlRebate(@RequestParam(value = "id", defaultValue = "0l")
    final Long id, @RequestParam(value = "actulAmount", defaultValue = "0")
    final BigDecimal amt, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            RebateRecordHistoryAssist rebateRecordHistoryAssist = new RebateRecordHistoryAssist();
            if (id != 0l)
            {
            	BigDecimal zero= new BigDecimal("0");
            	BigDecimal zeroO= new BigDecimal("0.0000");
                rebateRecordHistoryAssist = rebateRecordHistoryService.queryRebateRecordHistoryById(id);
                RebateRecordHistory rebateRecordHistory=rebateRecordHistoryAssist.getRebateRecordHistory();
                BigDecimal balance = zero;
                balance = rebateRecordHistory.getRebateAmt().subtract(amt);
                rebateRecordHistory.setActulAmount(amt);
                rebateRecordHistory.setBalance(balance);
                rebateRecordHistory.setRebateStatus(Constant.RebateStatus.ENABLE);
                rebateRecordHistory.setUpdateDate(new Date());
                rebateRecordHistory.setUpdateUser(loginPerson.getOperatorName());
                
                String merchantType=rebateRecordHistory.getMerchantType();
                LOGGER.debug("[RebateRecordHistoryController:acturlRebate() 返佣统计数据ID：" + id+"，返佣金额："+amt+",余额："+balance+"]");
                // 资金流向 资金为0时账户不做实际操作
                if(!zero.equals(amt)&&!zeroO.equals(amt))
                {
	                if (MerchantType.AGENT.toString().equals(merchantType))
	                {
	                    // 转帐（应付账户到下游商户账户）--------------------------------------------
	                    String desc = "应付账户  转账到  下游商户[" + rebateRecordHistoryAssist.getMerchantName() + ":"
	                                  + rebateRecordHistory.getMerchantId() + "]账户   金额为:["
	                                  + rebateRecordHistory.getActulAmount() + "]";
	                    SP sp = spService.getSP();
	                    rebateRecordService.doRebateTransfer(rebateRecordHistory.getId(),sp.getId(), sp.getIdentityType().toString(),
	                        Constant.AccountType.REBATE_DEAL, rebateRecordHistory.getMerchantId(),
	                        IdentityType.MERCHANT.toString(), Constant.AccountType.MERCHANT_DEBIT,
	                        rebateRecordHistory.getActulAmount(),Constant.TransferType.TRANSFER_REBATE_AMT_CONFIRM, desc);
	                    if(zero.equals(balance))
	                    {
	                    	rebateRecordHistory.setBalanceStatus(Constant.RebateStatus.ENABLE);
	                    }
	                }
	                else if (MerchantType.SUPPLY.toString().equals(merchantType))
	                {
	                    // 转帐（上游商户账户到上游利润户）--------------------------------------------
	                    String desc = "上游商户账户  转账到  上游利润户[" + rebateRecordHistoryAssist.getMerchantName()
	                                  + ":" + rebateRecordHistory.getMerchantId() + "]账户   金额为:["
	                                  + rebateRecordHistory.getActulAmount() + "]";
	                    SP sp = spService.getSP();
	                    rebateRecordService.doRebateTransfer(rebateRecordHistory.getId(),rebateRecordHistory.getMerchantId(),
	                        IdentityType.MERCHANT.toString(), Constant.AccountType.MERCHANT_CREDIT,
	                        sp.getId(), sp.getIdentityType().toString(),
	                        Constant.AccountType.SUPPLY_PROFIT, rebateRecordHistory.getActulAmount(),
	                        Constant.TransferType.TRANSFER_REBATE_AMT_CONFIRM,desc);
	                }
                }else{
                	if (MerchantType.SUPPLY.toString().equals(merchantType))
                	{
                		rebateRecordHistory.setBalanceStatus(Constant.RebateStatus.ENABLE);
                	}
                }
                rebateRecordHistoryService.saveRebateRecordHistory(rebateRecordHistory);
            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "rebateRecordHistory/rebateRecordHistoryList");
            model.put("next_msg", "返佣记录列表");
        }
        catch (RpcException e)
        {
                LOGGER.debug("[RebateRecordHistoryController:acturlRebate()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    public String getBalance(@RequestParam(value = "id", defaultValue = "0l")
    final Long id, ModelMap model)
    {
        try
        {
            RebateRecordHistoryAssist rebateRecordHistoryAssist = new RebateRecordHistoryAssist();
            if (id != 0l)
            {
                rebateRecordHistoryAssist = rebateRecordHistoryService.queryRebateRecordHistoryById(id);
            }
            model.addAttribute("rebateRecordHistory", rebateRecordHistoryAssist);
            return PageConstant.PAGE_REBATE_HISTORY_GET_BALANCE;
        }
        catch (RpcException e)
        {
                LOGGER.debug("[RebateRecordHistoryController:acturlRebate()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    public String getBalanceDo(@RequestParam(value = "id", defaultValue = "0l")
    final Long id, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            RebateRecordHistoryAssist rebateRecordHistoryAssist = new RebateRecordHistoryAssist();
            if (id != 0l)
            {
            	BigDecimal zero= new BigDecimal("0");
            	BigDecimal zeroO= new BigDecimal("0.0000");
                rebateRecordHistoryAssist = rebateRecordHistoryService.queryRebateRecordHistoryById(id);
                RebateRecordHistory rebateRecordHistory=rebateRecordHistoryAssist.getRebateRecordHistory();
                rebateRecordHistory.setBalanceStatus(Constant.RebateStatus.ENABLE);
                rebateRecordHistory.setUpdateDate(new Date());
                rebateRecordHistory.setUpdateUser(loginPerson.getOperatorName());
                String merchantType=rebateRecordHistory.getMerchantType();
                LOGGER.debug("[RebateRecordHistoryController:getBalance() 返佣统计数据ID：" + id+"，实际金额："+rebateRecordHistory.getActulAmount()+",余额："+rebateRecordHistory.getBalance()+"]");
                // 资金流向 资金为0时账户不做实际操作
                if (MerchantType.AGENT.toString().equals(merchantType)&&!zero.equals(rebateRecordHistory.getBalance())&&!zeroO.equals(rebateRecordHistory.getBalance()))
                {
                    // 转帐（应付账户到系统利润户）--------------------------------------------
                    String desc = "应付账户  转账到  系统利润户    金额为:[" + rebateRecordHistory.getBalance()
                                  + "]";
                    SP sp = spService.getSP();
                    rebateRecordService.doRebateTransfer(rebateRecordHistory.getId(),sp.getId(), sp.getIdentityType().toString(),
                        Constant.AccountType.REBATE_DEAL, sp.getId(),
                        sp.getIdentityType().toString(), Constant.AccountType.SYSTEM_PROFIT_OWN,
                        rebateRecordHistory.getBalance(),Constant.TransferType.TRANSFER_REBATE_BALANCE_RECOVER, desc);
                }
                else if (MerchantType.SUPPLY.toString().equals(merchantType)&&!zero.equals(rebateRecordHistory.getActulAmount()))
                {
                    // 转帐（上游利润户到系统中间利润户）--------------------------------------------
                    String desc = "上游利润户  转账到  系统中间利润户    金额为:["
                                  + rebateRecordHistory.getActulAmount() + "]";
                    SP sp = spService.getSP();
                    rebateRecordService.doRebateTransfer(rebateRecordHistory.getId(),sp.getId(), sp.getIdentityType().toString(),
                        Constant.AccountType.SUPPLY_PROFIT, sp.getId(),
                        sp.getIdentityType().toString(),
                        Constant.AccountType.SYSTEM_MIDDLE_PROFIT,
                        rebateRecordHistory.getActulAmount(),Constant.TransferType.TRANSFER_REBATE_ACTULAMOUNT_RECOVER, desc);
                }
                rebateRecordHistoryService.saveRebateRecordHistory(rebateRecordHistory);
            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "rebateRecordHistory/rebateRecordHistoryList");
            model.put("next_msg", "返佣记录列表");
        }
        catch (RpcException e)
        {
                LOGGER.debug("[RebateRecordHistoryController:acturlRebate()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
}
