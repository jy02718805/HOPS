package com.yuecheng.hops.transaction.execution.imputation.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationRecord;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.execution.imputation.ProfitImputationService;
import com.yuecheng.hops.transaction.service.order.OrderPageQuery;
import com.yuecheng.hops.transaction.service.order.OrderService;
import com.yuecheng.hops.transaction.service.profitImputation.ProfitImputationInfoService;
import com.yuecheng.hops.transaction.service.profitImputation.ProfitImputationRecordService;
import com.yuecheng.hops.transaction.service.scanner.ProfitImputationScanner;


/**
 * 利润归集
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see ProfitImputationServiceImpl
 * @since
 */
@Service("profitImputationService")
public class ProfitImputationServiceImpl implements ProfitImputationService
{
    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ProfitImputationInfoService profitImputationInfoService;

    @Autowired
    private ProfitImputationRecordService profitImputationRecordService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SpService spService;

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountHistoryService;

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private ProfitImputationScanner profitImputationScanner;

    @Autowired
    private OrderPageQuery orderPageQuery;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitImputationServiceImpl.class);

    /**
     * 定时任务 （归集商户利润到中间利润户）
     */
    public void taskProfitImputation(String beginDate, String endDate)
    {
        LOGGER.debug("[ProfitImputationServiceImpl: taskProfitImputation] [进入每日利润归集定时任务]");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ProfitImputationRecord profitImputationRecord = new ProfitImputationRecord();
        try
        {
            // profitImputationScanner.taskProfitImputation();
            // 1.初始化归集记录 beginTime
            profitImputationRecord = saveProfitImputationRecord(profitImputationRecord,
                Constant.RecordStatus.INITIALIZATION, "初始化");
            // 2.归集利润到中间利润账户
            List<ProfitImputationInfo> profitImputations = imputationProfit(
                format.parse(beginDate), format.parse(endDate));
            // 3 保存到归集控制表
            profitImputationInfoService.saveProfitImputations(profitImputations);
            // 4.记录归集成功
            saveProfitImputationRecord(profitImputationRecord, Constant.RecordStatus.SUCCESS,
                "账户利润归集完成");

        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationServiceImpl: taskProfitImputation] [利润归集定时任务异常] "
                         + ExceptionUtil.getStackTraceAsString(e));
            saveProfitImputationRecord(profitImputationRecord, Constant.RecordStatus.FAILURE,
                "账户利润归集失败 ");
            throw new ApplicationException("imputation0000001",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
        LOGGER.debug("[ProfitImputationServiceImpl: taskProfitImputation] [结束每日利润归集定时任务]");
    }

    public List<ProfitImputationInfo> imputationProfit(Date beginTime, Date endTime)
    {
        LOGGER.debug("[ProfitImputationServiceImpl: imputationProfit(beginTime:" + beginTime
                     + ",endTime:" + endTime + ")] [进入每日利润归集方法]");
        ProfitImputationInfo profitImputationInfo = new ProfitImputationInfo();
        List<ProfitImputationInfo> ImputationInfos = new ArrayList<ProfitImputationInfo>();

        try
        {
            SP sp = spService.getSP();
            CCYAccount middleAccount = (CCYAccount)identityAccountRoleService.getAccountByParams(
                Constant.AccountType.SYSTEM_MIDDLE_PROFIT, sp.getId(), IdentityType.SP.toString(),
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);

            List<BigDecimal> accountIdList = currencyAccountHistoryService.getCensusAccountIdList(
                Constant.AccountType.SYSTEM_PROFIT + "", beginTime, endTime);

            for (BigDecimal accountId : accountIdList)
            {
                LOGGER.debug("[ProfitImputationServiceImpl: taskProfitImputation()] [进入统计归集利润账户:"
                             + accountId + "]");

                CCYAccount ccyAccount = (CCYAccount)ccyAccountService.getAccountByParams(
                    accountId.longValue(), Constant.AccountType.SYSTEM_PROFIT, null);

                IdentityAccountRole iar = identityAccountRoleService.queryIdentityAccountRoleByParames(
                    accountId.longValue(), Constant.Account.ACCOUNT_RELATION_TYPE_USE);

                BigDecimal amtSum = orderPageQuery.getOrderAmtSumByIdentityId(iar.getIdentityId(),
                    Constant.OrderStatus.SUCCESS, Constant.Delivery.DELIVERY_STATUS_SUCCESS,
                    beginTime, endTime);
                // transactionHistoryService.getTransactionHistoryOfAmtSum(
                // accountId.longValue(), Constant.TransferType.TRANSFER_SYSTEM_AGENT_PROFIT,
                // beginTime, endTime);
                if (null == amtSum)
                {
                    amtSum = new BigDecimal(0);
                }

                if (amtSum.compareTo(BigDecimal.ZERO) == 1)
                {
                    amtSum = amtSum.setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
                    profitImputationInfo = getProfitImputationInfo(ccyAccount, middleAccount,
                        amtSum, beginTime, endTime);
                    profitImputationInfo.setAccounBalance(ccyAccount.getAvailableBalance());
                    ImputationInfos.add(profitImputationInfo);
                }

                LOGGER.debug("[商户利润户(" + accountId + ")归集到系统中间利润户: ]");
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "利润归集报错: [ProfitImputationServiceImpl :imputationProfit(beginTime:" + beginTime
                    + ",endTime:" + endTime + ")]", e);
        }
        return ImputationInfos;
    }

    public ProfitImputationInfo getProfitImputationInfo(Account account, Account middleAccount,
                                                        BigDecimal amtSum, Date beginTime,
                                                        Date endTime)
    {
        LOGGER.debug("[ProfitImputationServiceImpl: taskProfitImputation] [统计在时间段内的已成交易的商户利润账户的金额总和] [getProfitImputationInfo("
                     + account.getAccountId() + "," + "," + endTime + "," + endTime + ")]");

        ProfitImputationInfo profitImputationInfo = new ProfitImputationInfo();
        profitImputationInfo.setProfitAccountId(account.getAccountId());
        profitImputationInfo.setProfitAccountTypeId(account.getAccountType().getAccountTypeId());
        profitImputationInfo.setMiddleProfitAccountId(middleAccount.getAccountId());
        profitImputationInfo.setMiddleProfitAccountTypeId(middleAccount.getAccountType().getAccountTypeId());
        IdentityAccountRole iar = identityAccountRoleService.queryIdentityAccountRoleByParames(
            account.getAccountId(), Constant.Account.ACCOUNT_RELATION_TYPE_USE);
        if (BeanUtils.isNotNull(iar)
            && iar.getIdentityType().equalsIgnoreCase(IdentityType.MERCHANT.toString()))
        {
            Merchant mt = merchantService.queryMerchantById(iar.getIdentityId());
            profitImputationInfo.setMerchantId(iar.getIdentityId());
            profitImputationInfo.setMerchantName(mt.getMerchantName());
        }
        profitImputationInfo.setImputationProfit(amtSum);
        profitImputationInfo.setImputationStatus(Constant.ProfitImputation.WAIT_IMPUTATION);
        profitImputationInfo.setImputationBeginDate(beginTime);
        profitImputationInfo.setImputationEndDate(endTime);
        return profitImputationInfo;
    }

    /**
     * 归集账户
     * 
     * @param profitImputationInfo
     * @return
     * @see
     */
    public boolean isAccountImputation(ProfitImputationInfo profitImputationInfo)
    {
        LOGGER.debug("[ProfitImputationServiceImpl: isAccountImputation(ProfitImputationInfo profitImputationInfo) ] [进入商户利润账户归集到系统中间利润账户:"
                     + profitImputationInfo.getProfitAccountId() + "]");
        try
        {
            String desc = "商户利润账户:[" + profitImputationInfo.getProfitAccountId()
                          + "] 归集到  系统中间利润账户账户 :["
                          + profitImputationInfo.getMiddleProfitAccountId() + "] 金额为:["
                          + profitImputationInfo.getImputationProfit() + "]";
            Long tiansactionNo = profitImputationInfo.getProfitImputationId();
            LOGGER.debug("[ProfitImputationServiceImpl:isAccountImputation] [商户利润账户归集到系统中间利润账户 :"
                         + profitImputationInfo.getProfitAccountId() + ","
                         + profitImputationInfo.getImputationProfit() + "]");

            SP sp = spService.getSP();
            // 系统信用户
            CCYAccount sysCreditAccount = (CCYAccount)identityAccountRoleService.getAccountNoCache(
                Constant.AccountType.SYSTEM_CREDIT, sp.getId(), IdentityType.SP.toString(),
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            if (profitImputationInfo.getImputationStatus().equals(
                Constant.ProfitImputation.WAIT_IMPUTATION))
            {

                profitImputationInfo = settlementCredit(profitImputationInfo, tiansactionNo,
                    sysCreditAccount);

                Long transactionId = null;
                if (profitImputationInfo.getImputationProfit().compareTo(BigDecimal.ZERO) > 0)
                {
                    transactionId = airtimeAccountingTransaction.transfer(
                        profitImputationInfo.getProfitAccountId(),
                        profitImputationInfo.getProfitAccountTypeId(),
                        profitImputationInfo.getMiddleProfitAccountId(),
                        profitImputationInfo.getMiddleProfitAccountTypeId(),
                        profitImputationInfo.getImputationProfit(), desc,
                        Constant.TransferType.TRANSFER_PROFIT_IMPUTATION, tiansactionNo);

                }

                if (null != transactionId)
                {
                    profitImputationInfo.setImputationStatus(Constant.ProfitImputation.IS_IMPUTATION);
                    profitImputationInfo.setImputationEndDate(new Date());
                    profitImputationInfo.setDescribe(profitImputationInfo.getDescribe() == null ? "" : profitImputationInfo.getDescribe()
                                                                                                       + "利润归集: "
                                                                                                       + profitImputationInfo.getImputationProfit());
                    profitImputationInfoService.saveProfitImputation(profitImputationInfo);
                }
                return true;
            }

        }
        catch (RpcException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationServiceImpl: isAccountImputation(ProfitImputationInfo:"
                         + profitImputationInfo.toString() + ") ] [商户利润账户归集到系统中间利润账户异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
        }
        return false;
    }

    private ProfitImputationInfo settlementCredit(ProfitImputationInfo profitImputationInfo,
                                                  Long tiansactionNo, CCYAccount sysCreditAccount)
    {
        LOGGER.debug("[ProfitImputationServiceImpl: settlementCredit(ProfitImputationInfo:"
                     + profitImputationInfo.toString() + " ,tiansactionNo:" + tiansactionNo
                     + ",sysCreditAccount:" + sysCreditAccount.toString() + ") ]");

        BigDecimal imputationAmount = profitImputationInfo.getImputationProfit();

        if (sysCreditAccount.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0)
        {
            BigDecimal settlementAmount = BigDecimal.ZERO;
            String creditDesc = "商户利润账户:[" + profitImputationInfo.getProfitAccountId()
                                + "] 平账到  系统信用账户 :[" + sysCreditAccount.getAccountId() + "] 金额为:[";

            BigDecimal sysBigDecimal = BigDecimalUtil.multiply(
                sysCreditAccount.getAvailableBalance(), new BigDecimal("-1"),
                DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            if (imputationAmount.compareTo(sysBigDecimal) > 0)
            {
                imputationAmount = BigDecimalUtil.sub(imputationAmount, sysBigDecimal,
                    DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
                creditDesc += sysBigDecimal + "]";
                airtimeAccountingTransaction.transfer(profitImputationInfo.getProfitAccountId(),
                    profitImputationInfo.getProfitAccountTypeId(),
                    sysCreditAccount.getAccountId(),
                    sysCreditAccount.getAccountType().getAccountTypeId(), sysBigDecimal,
                    creditDesc, Constant.TransferType.TRANSFER_DEBIT_SETTLEMENT, tiansactionNo);
                settlementAmount = imputationAmount;
            }
            else
            {
                creditDesc += imputationAmount + "]";
                airtimeAccountingTransaction.transfer(profitImputationInfo.getProfitAccountId(),
                    profitImputationInfo.getProfitAccountTypeId(),
                    sysCreditAccount.getAccountId(),
                    sysCreditAccount.getAccountType().getAccountTypeId(), imputationAmount,
                    creditDesc, Constant.TransferType.TRANSFER_DEBIT_SETTLEMENT, tiansactionNo);
                settlementAmount = imputationAmount;
                imputationAmount = BigDecimal.ZERO;
            }

            profitImputationInfo.setImputationProfit(settlementAmount);
            profitImputationInfo.setDescribe("已信用平账: [" + settlementAmount + "]  ");
        }
        profitImputationInfo = profitImputationInfoService.saveProfitImputation(profitImputationInfo);
        return profitImputationInfo;
    }

    /**
     * 开始时间
     * 
     * @return
     * @see
     */
    public static Date getBeginTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -4);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date)currentDate.getTime().clone();
    }

    /**
     * 结束时间
     * 
     * @return
     * @see
     */
    public static Date getEndTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -4);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return (Date)currentDate.getTime().clone();
    }

    ProfitImputationRecord saveProfitImputationRecord(ProfitImputationRecord profitImputationRecord,
                                                      String recordStatus, String recordDescribe)
    {

        profitImputationRecord.setRecordStatus(recordStatus);
        profitImputationRecord.setRecordBeginDateate(getBeginTime());
        profitImputationRecord.setRecordEndDateate(getEndTime());
        profitImputationRecord.setRecordUpdateDate(new Date());
        profitImputationRecord.setRecordDescribe(recordDescribe);
        profitImputationRecord = profitImputationRecordService.saveProfitImputationRecord(profitImputationRecord);
        return profitImputationRecord;
    }

    @Override
    public void reTaskProfitImputation()
    {
        LOGGER.debug("[ProfitImputationServiceImpl: taskProfitImputation] [进入每日重新利润归集定时任务]");
        try
        {
            // 找出没有归集成功的记录重新归集
            List<ProfitImputationRecord> profitImputationRecords = profitImputationRecordService.queryProfitImputationRecordList(Constant.RecordStatus.SUCCESS);
            for (ProfitImputationRecord profitImputationRecord : profitImputationRecords)
            {
                imputationProfit(profitImputationRecord.getRecordBeginDateate(),
                    profitImputationRecord.getRecordEndDateate());
                saveProfitImputationRecord(profitImputationRecord, Constant.RecordStatus.SUCCESS,
                    "归集成功");
            }
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationServiceImpl: reTaskProfitImputation() ] 异常: "
                         + ExceptionUtil.getStackTraceAsString(e) + " ]");
        }
    }

    @Override
    public ProfitImputationInfo reImputationProfit(Long profitImputationId)
    {
        LOGGER.debug("[ProfitImputationServiceImpl: reImputationProfit(profitImputationId:"
                     + profitImputationId + ")] [进入重新计算归集利润]");

        ProfitImputationInfo profitImputationInfo = new ProfitImputationInfo();
        try
        {
            profitImputationInfo = profitImputationInfoService.getProfitImputationInfoById(profitImputationId);

            IdentityAccountRole iar = identityAccountRoleService.queryIdentityAccountRoleByParames(
                profitImputationInfo.getProfitAccountId().longValue(),
                Constant.Account.ACCOUNT_RELATION_TYPE_USE);

            BigDecimal amtSum = orderPageQuery.getOrderAmtSumByIdentityId(iar.getIdentityId(),
                Constant.OrderStatus.SUCCESS, Constant.Delivery.DELIVERY_STATUS_SUCCESS,
                profitImputationInfo.getImputationBeginDate(),
                profitImputationInfo.getImputationEndDate());
            if (amtSum == null)
            {
                amtSum = new BigDecimal(0);
            }
            amtSum = amtSum.setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            profitImputationInfo.setImputationProfit(amtSum);
            profitImputationInfo = profitImputationInfoService.saveProfitImputation(profitImputationInfo);
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationServiceImpl: reImputationProfit() ] 异常: "
                         + ExceptionUtil.getStackTraceAsString(e) + " ]");
            String[] msgParams = new String[] {ExceptionUtil.getStackTraceAsString(e)};
            String[] viewParams = new String[] {};
            // throw new ApplicationException("businesss000016",msgParams,viewParams,new
            // Exception());
            ApplicationException ae = new ApplicationException("imputation0000003", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
            // TODO: handle exception
        }

        LOGGER.debug("[ProfitImputationServiceImpl: reImputationProfit(profitImputationId:"
                     + profitImputationId + ")] [结束重新计算归集利润]");
        return profitImputationInfo;
    }

}
