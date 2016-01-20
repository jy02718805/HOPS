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

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
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
import com.yuecheng.hops.mportal.vo.rebate.RebateRecordVo;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.rebate.entity.RebateRecord;
import com.yuecheng.hops.rebate.entity.RebateRecordHistory;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordAssist;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordHistoryAssist;
import com.yuecheng.hops.rebate.service.RebateRecordHistoryService;
import com.yuecheng.hops.rebate.service.RebateRecordService;


@Controller
@RequestMapping(value = "/rebateRecord")
public class RebateRecordController extends BaseControl
{

    @Autowired
    private RebateRecordService rebateRecordService;

    @Autowired
    private RebateRecordHistoryService rebateRecordHistoryService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantStatusManagement merchantStatusManagement;

    @Autowired
    private SpService spService;

    private static Logger logger = LoggerFactory.getLogger(RebateRecordController.class);
    
    @RequestMapping(value = "/rebateRecordList")
    public String rebateRecordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                                   @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                                   RebateRecordVo rebateRecordVo, ModelMap model,
                                   ServletRequest request)
    {
        try
        {
            Date beginTime = null;
            Date endTime = null;
            Map<String, Object> searchParams = new HashMap<String, Object>();
            //默认前一天时间 （别删）
//            if (StringUtil.isBlank(rebateRecordVo.getBeginDate())&&StringUtil.isBlank(rebateRecordVo.getEndDate()))
//            {
//            	beginTime = DateUtil.getYesterdayBeginTime();
//            	endTime = DateUtil.getYesterdayEndTime();
//            	rebateRecordVo.setBeginDate(sdf.format(beginTime));
//            	rebateRecordVo.setEndDate(sdf.format(endTime));
//            }
            if (StringUtil.isNotBlank(rebateRecordVo.getMerchantId()))
            {
                searchParams.put(EntityConstant.RebateRecord.MERCHANT_ID,
                    String.valueOf(rebateRecordVo.getMerchantId()));
            }
            if (StringUtil.isNotBlank(rebateRecordVo.getRebateMerchantId()))
            {
                searchParams.put(EntityConstant.RebateRecord.REBATE_MERCHANT_ID,
                    String.valueOf(rebateRecordVo.getRebateMerchantId()));
            }
            if (StringUtil.isNotBlank(rebateRecordVo.getBeginDate()))
            {
                beginTime = sdf.parse(rebateRecordVo.getBeginDate());
                beginTime=DateUtil.getDateStart(beginTime);
            }
            if (StringUtil.isNotBlank(rebateRecordVo.getEndDate()))
            {
                endTime = sdf.parse(rebateRecordVo.getEndDate());
                endTime=DateUtil.getDateEnd(endTime);
            }
            if (StringUtil.isNotBlank(rebateRecordVo.getStatus()))
            {
                searchParams.put(EntityConstant.RebateRecord.STATUS,
                    String.valueOf(rebateRecordVo.getStatus()));
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.RebateRecord.ID);
            YcPage<RebateRecordAssist> page_list = rebateRecordService.queryPageRebateRecord(searchParams,
                beginTime, endTime, pageNumber, pageSize, bsort);
            List<Merchant> rebateMerchants = merchantStatusManagement.queryMerchantByIsRebate(true);
            List<Merchant> agentMerchant = merchantService.queryAllMerchant(MerchantType.AGENT,null);
            List<Merchant> supplyMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY,null);

            List<Merchant> allMerchant = new ArrayList<Merchant>();
            allMerchant.addAll(agentMerchant);
            allMerchant.addAll(supplyMerchant);
            model.addAttribute("rebateMerchants", rebateMerchants);
            model.addAttribute("merchants", allMerchant);
            model.addAttribute("rebateRecordVo", rebateRecordVo);
            model.addAttribute("mlist", page_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("counttotal", page_list.getCountTotal() + "");
            model.addAttribute("pagetotal", page_list.getPageTotal() + "");
            return "rebate/rebateRecordList";
        }
        catch (RpcException e)
        {
                logger.debug("[RebateRecordController:rebateRecordList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
                logger.debug("[RebateRecordController:rebateRecordList()]" + e.getMessage());
            return Constant.Common.FAIL;

        }
    }

    @RequestMapping(value = "/deleteRebateRecord")
    public String deleteRebateRecord(@RequestParam(value = "id") Long id, ModelMap model)
    {
        try
        {
            rebateRecordService.deleteRebateRecord(id);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
        }
        catch (RpcException e)
        {
                logger.debug("[RebateRecordController:deleteRebateRecord()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    //手动生成前一天返佣数据
    @RequestMapping(value = "/generateDatas")
    public String generateDatas(ModelMap model)
    {
        try
        {
        	Date date=new Date();
        	date=DateUtil.addDate(Constant.DateUnit.TIME_UNIT_DAY, 1, date);
            rebateRecordService.createRebateRecords(date);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRecordController:generateDatas()]" + e.getMessage());
            model.put("message", "操作失败["+e.getMessage()+"]");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/generateData", method = RequestMethod.GET)
    public String generateData(@RequestParam(value = "rebateRecordId", defaultValue = "") String ids,
                               @RequestParam(value = "mid", defaultValue = "") String mid,
                               @RequestParam(value = "rmid", defaultValue = "") String rmid,
                               @RequestParam(value = "beginDate", defaultValue = "") String beginDate,
                               @RequestParam(value = "endDate", defaultValue = "") String endDate,
                               ModelMap model)
    {
        try
        {
                logger.debug("[RebateRecordController:generateData(" + ids + ")]");
            if (StringUtil.isNotBlank(ids))
            {
                String[] idList = ids.split("\\|");
                int i = 0;
                RebateRecordAssist rebateRecordAssistAll = new RebateRecordAssist();
                BigDecimal rebateAmt = new BigDecimal("0");
                Long transactionNum = new Long("0");
                while (i < idList.length)
                {
                    RebateRecordAssist rebateRecordAssist = rebateRecordService.queryRebateRecordById(new Long(
                        idList[i]));
                    rebateRecordAssistAll = rebateRecordAssist;
                    RebateRecord rebateRecord=rebateRecordAssist.getRebateRecord();
                    // 修改
                    rebateAmt = rebateAmt.add(rebateRecord.getRebateAmt());
                    transactionNum = transactionNum + rebateRecord.getTransactionVolume();
                    i++ ;
                }
                rebateRecordAssistAll.getRebateRecord().setTransactionVolume(transactionNum);
                rebateRecordAssistAll.getRebateRecord().setRebateAmt(rebateAmt);
                model.addAttribute("rebateRecordAll", rebateRecordAssistAll);
                model.addAttribute("beginDate", beginDate);
                model.addAttribute("endDate", endDate);
                model.addAttribute("ids", ids);
                return PageConstant.PAGE_REBATE_BUILD;
            }
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRecordController:generateData()]" + e.getMessage());
            model.put("message", "操作失败["+e.getMessage()+"]");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/generateData", method = RequestMethod.POST)
    public String generateData(@RequestParam(value = "ids", defaultValue = "") String ids,RebateRecordVo rRecordVo,
                               ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
                logger.info("[RebateRecordController:generateData(" + rRecordVo!=null?rRecordVo.toString():null + ")]");

            if (BeanUtils.isNotNull(rRecordVo))
            {
                RebateRecordHistoryAssist rebateRecordHistoryAssist = saveRebateRecordHistory(ids, rRecordVo);
                if (rebateRecordHistoryAssist != null)
                {
                	String merchantType=rRecordVo.getMerchantType().toString();
                	// 资金为0时账户不做实际操作
                    if (!"0".equals(rRecordVo.getRebateAmt())&&!"0.0000".equals(rRecordVo.getRebateAmt())&&MerchantType.AGENT.toString().equals(merchantType))
                    {
                    	//注：代理商需要转账，供货商不需要转账
	                    // 转帐（系统中间利润户到应付账户）--------------------------------------------
	                    SP sp = spService.getSP();
	                    BigDecimal rebateAmt = new BigDecimal(rRecordVo.getRebateAmt());
	                    String desc = "生成返佣数据操作，系统中间利润户到应付账户，金额为：" + rebateAmt;
                    	
	                    Long historyId=rebateRecordHistoryAssist.getRebateRecordHistory().getId();
	                    try{
		                    rebateRecordService.doRebateTransfer(historyId,sp.getId(), sp.getIdentityType().toString(),
		                        Constant.AccountType.SYSTEM_MIDDLE_PROFIT, sp.getId(),
		                        sp.getIdentityType().toString(), Constant.AccountType.REBATE_DEAL,
		                        rebateAmt,Constant.TransferType.TRANSFER_REBATE_AMT_LIQUIDATIOR, desc);
	                    }
	                    catch(Exception e)
	                    {
	                    	logger.debug("生成数据出现未知异常："+e.getMessage());
	                    	rebateRecordHistoryService.deleteRebateRecordHistory(historyId);
	                    	throw e;
	                    }
                    }
                    String[] idList = ids.split("\\|");
                    int i = 0;
                    while (i < idList.length)
                    {
                        RebateRecordAssist rebateRecordAssist = rebateRecordService.queryRebateRecordById(new Long(
                            idList[i]));
                        RebateRecord rebateRecord=rebateRecordAssist.getRebateRecord();
                        rebateRecord.setStatus(Constant.RebateStatus.ENABLE);
                        rebateRecord.setUpdateDate(new Date());
                        rebateRecord.setUpdateUser(loginPerson.getOperatorName());
                        merchantType=rebateRecord.getMerchantType();
                        rebateRecordService.updateRebateRecord(rebateRecord);
                        i++ ;
                    }
                    model.put("message", "操作成功");
                    model.put("canback", false);
                    model.put("next_url", "rebateRecord/rebateRecordList");
                    model.put("next_msg", "返佣记录列表");
                    return PageConstant.PAGE_COMMON_NOTIFY;
                }
                model.put("message", "操作失败,保存返佣统计数据失败");
                model.put("canback", false);
                model.put("next_url", "rebateRecord/rebateRecordList");
                model.put("next_msg", "返佣记录列表");
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            model.put("message", "操作失败，获取界面VO对象数据失败");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[RebateRecordController:generateData()]" + e.getMessage());
            model.put("message", "操作失败["+e.getMessage()+"]");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
        }
        catch (Exception e)
        {
            logger.debug("[RebateRecordController:generateData()]" + e.getMessage());
            model.put("message", "操作失败["+e.getMessage()+"]");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
    
    public RebateRecordHistoryAssist saveRebateRecordHistory(String ids,RebateRecordVo rRecordVo)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            RebateRecordHistory rebateRecordHistory = new RebateRecordHistory();
            BigDecimal rebateAmt = new BigDecimal(rRecordVo.getRebateAmt());
            Long transactionNum = new Long(rRecordVo.getTransactionVolume());
            rebateRecordHistory.setMerchantId(new Long(rRecordVo.getMerchantId()));
            rebateRecordHistory.setRebateMerchantId(new Long(rRecordVo.getRebateMerchantId()));
            rebateRecordHistory.setRebateProductId(rRecordVo.getRebateProductId());
            rebateRecordHistory.setRebateStartDate(sdf.parse(rRecordVo.getBeginDate()));
            rebateRecordHistory.setRebateEndDate(sdf.parse(rRecordVo.getEndDate()));
            rebateRecordHistory.setMerchantType(rRecordVo.getMerchantType());
            rebateRecordHistory.setTransactionVolume(transactionNum);
            rebateRecordHistory.setRebateAmt(rebateAmt);
            rebateRecordHistory.setRebateType(new Long(rRecordVo.getRebateType()));
            rebateRecordHistory.setActulAmount(new BigDecimal("0"));
            rebateRecordHistory.setBalance(new BigDecimal("0"));
            String merchantType=rRecordVo.getMerchantType().toString();
            if(new BigDecimal("0").equals(rebateAmt)&&MerchantType.AGENT.toString().equals(merchantType))
            {
	            rebateRecordHistory.setBalanceStatus(Constant.RebateStatus.ENABLE);
	            rebateRecordHistory.setRebateStatus(Constant.RebateStatus.ENABLE);
            }else 
            {
                rebateRecordHistory.setBalanceStatus(Constant.RebateStatus.DISABLE);
                rebateRecordHistory.setRebateStatus(Constant.RebateStatus.DISABLE);
            }
            rebateRecordHistory.setCreateDate(new Date());
            rebateRecordHistory.setCreateUser(loginPerson.getOperatorName());
            rebateRecordHistory.setUpdateDate(new Date());
            rebateRecordHistory.setUpdateUser(loginPerson.getOperatorName());
            rebateRecordHistory.setRemark(ids);
            rebateRecordHistory.setStatus(Constant.RebateStatus.ENABLE);
            RebateRecordHistoryAssist rebateRecordHistoryAssist = rebateRecordHistoryService.saveRebateRecordHistory(rebateRecordHistory);
            return rebateRecordHistoryAssist;
        }catch(Exception e)
        {
        	logger.debug("[RebateRecordController:saveRebateRecordHistory()]" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/rebuildRebateRecord")
    public String rebuildRebateRecord(@RequestParam(value = "id", defaultValue = "0l") Long id,
                                      ModelMap model)
    {
        try
        {
            RebateRecordAssist rebateRecordAssist = rebateRecordService.queryRebateRecordById(id);
            RebateRecord rebateRecord=rebateRecordAssist.getRebateRecord();
            Date endTime = DateUtil.getDateEnd(rebateRecord.getRebateDate());
            // 输入值与原值相同时走重新生成数据流程
            rebateRecordService.createRebateRecord(rebateRecord.getMerchantId(),
                MerchantType.valueOf(rebateRecord.getMerchantType()),
                rebateRecord.getRebateRuleId(), rebateRecord.getRebateDate(),
                endTime, id);

            /*
             * rebateRecordService.createRebateRecord( rebateRecord.getMerchantId(),
             * rebateRecord.getRebateRuleId(), rebateRecord.getBeginTime(),
             * rebateRecord.getEndTime(), id);
             */
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "rebateRecord/rebateRecordList");
            model.put("next_msg", "返佣记录列表");
        }
        catch (RpcException e)
        {
                logger.debug("[RebateRecordController:rebuildRebateRecord()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return "redirect:/rebateRecord/rebateRecordList";
    }
}
