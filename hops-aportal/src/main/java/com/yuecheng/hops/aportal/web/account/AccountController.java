package com.yuecheng.hops.aportal.web.account;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuecheng.hops.account.entity.CurrencyAccountAddCashRecord;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.CurrencyAccountAddCashRecordVo;
import com.yuecheng.hops.account.service.CurrencyAccountAddCashRecordService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.aportal.constant.PageConstant;
import com.yuecheng.hops.aportal.web.BaseControl;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.operator.OperatorService;


@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseControl
{
    @Autowired
    private IdentityAccountRoleService     identityAccountRoleService;

    @Autowired
    private CurrencyAccountAddCashRecordService currencyAccountAddCashRecordService;
    
    @Autowired
    private MerchantService merchantService;
    
    @Autowired
    private OperatorService operatorService;
    
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/aportalAccountAddCashRecordList")
    public String aportalAccountAddCashRecordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, 
                                           @RequestParam(value = "pageSize", defaultValue = "10")int pageSize, 
                                           @RequestParam(value = "sortType", defaultValue = "auto")String sortType, 
                                           @RequestParam(value = "operatorName", defaultValue = "")String operatorName,
                                           @RequestParam(value = "beginApplyTime", defaultValue = "")String beginApplyTime,
                                           @RequestParam(value = "endApplyTime", defaultValue = "")String endApplyTime,
                                           @RequestParam(value = "verifyStatus", defaultValue = "")Integer verifyStatus,
                                           ModelMap model, ServletRequest request)
    {
    	if (StringUtil.isBlank(beginApplyTime))
        {
            beginApplyTime = DateUtil.formatDateTime(DateUtil.getDateStart(new Date()));

        }
        if (StringUtil.isBlank(endApplyTime))
        {
            endApplyTime = DateUtil.formatDateTime(new Date());
        }
        Operator loginUser=getLoginUser();
        CurrencyAccountAddCashRecordVo caarVo = new CurrencyAccountAddCashRecordVo();
        caarVo.setMerchantId(loginUser.getOwnerIdentityId());
        caarVo.setOperatorName(operatorName);
        caarVo.setVerifyStatus(verifyStatus);
        caarVo.setBeginApplyTime(beginApplyTime);
        caarVo.setEndApplyTime(endApplyTime);
        List<Operator> operators = operatorService.queryOperatorByMerchant(loginUser.getOwnerIdentityId());
        
        sortType = EntityConstant.CurrencyAccountAddCashRecord.APPLY_TIME_SQL;
        YcPage<CurrencyAccountAddCashRecord> page = currencyAccountAddCashRecordService.findCurrencyAccountAddCashRecordByParams(caarVo, pageNumber, pageSize, sortType);
        model.addAttribute("mlist", page.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page.getCountTotal() + "");
        model.addAttribute("pagetotal", page.getPageTotal() + "");
        model.addAttribute("operators", operators);
        model.addAttribute("operatorName", operatorName);
        model.addAttribute("verifyStatus", verifyStatus);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("beginApplyTime", beginApplyTime);
        model.addAttribute("endApplyTime", endApplyTime);
        return "account/accountAddCashRecordList";
    }
    
    @RequestMapping(value = "/toSaveAddCashRecord")
    public String toSaveAddCashRecord(ModelMap model, ServletRequest request)
    {
        return "account/addCashRegister";
    }
    
    @RequestMapping(value = "/doSaveAddCashRecord")
    public String doSaveAddCashRecord(@RequestParam(value = "rmk")String rmk,
                           @RequestParam(value = "amt")String amt,
                           ModelMap model, ServletRequest request)
    {
        try
        {
        	Operator loginUser=getLoginUser();
            Merchant merchant = merchantService.queryMerchantById(loginUser.getOwnerIdentityId());
            IdentityAccountRole merchantAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                Constant.AccountType.MERCHANT_DEBIT, loginUser.getOwnerIdentityId(),
                IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            
            CurrencyAccountAddCashRecord caar = new CurrencyAccountAddCashRecord();
            caar.setAccountId(merchantAccount.getAccountId());
            caar.setApplyAmt(new BigDecimal(amt));
            caar.setApplyTime(new Date());
            caar.setMerchantId(loginUser.getOwnerIdentityId());
            caar.setMerchantName(merchant.getMerchantName());
            caar.setOperatorName(getLoginUser().getOperatorName());
            caar.setRmk(rmk);
            caar.setVerifyStatus(Constant.AddCashStatus.WAIT_VERIFY);
//            currencyAccountAddCashRecordService.verify(id, verifyStatus, new BigDecimal(amt));
            currencyAccountAddCashRecordService.saveCurrencyAccountAddCashRecord(caar);
            model.put("next_url", "account/aportalAccountAddCashRecordList");
            model.put("next_msg", "加款申请列表");
            model.put("message", "操作成功");
            model.put("canback", false);
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
}
