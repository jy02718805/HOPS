package com.yuecheng.hops.report.execution.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.report.entity.TransactionReportInfo;
import com.yuecheng.hops.report.entity.TransactionReportRecord;
import com.yuecheng.hops.report.entity.bo.TransactionReportBo;
import com.yuecheng.hops.report.execution.TransactionReportAction;
import com.yuecheng.hops.report.service.TransactionReportRecordService;
import com.yuecheng.hops.report.service.TransactionReportService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 交易量报表统计 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see TransactionReportActionImpl
 * @since
 */
@Service("transactionReportAction")
public class TransactionReportActionImpl implements TransactionReportAction
{

    @Autowired
    protected MerchantService merchantService;

    @Autowired
    protected SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private TransactionReportService transactionReportsService;

    @Autowired
    private ProductPageQuery productPageQuery;

    @Autowired
    private TransactionReportRecordService transactionReportRecordService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionReportActionImpl.class);

    @Override
    public void censusAgentTransactionReport()
    {
        LOGGER.debug("[进入统计商户交易量方法][TransactionReportByMerchantActionImpl.censusTransactionReportByAgentMerchant()]");
        // 统计下游各个产品已成交量
        // 过程。。
        TransactionReportRecord transactionReportRecord = new TransactionReportRecord();
        try
        {
            transactionReportRecord = transactionReportRecordService.queryTransactionReportRecord(
                MerchantType.AGENT.toString(), Constant.TransactionReportRecord.TYPE_TRANSACTION,
                ReportTool.getBeginTime());

            if (BeanUtils.isNull(transactionReportRecord))
            {
                transactionReportRecord = new TransactionReportRecord();
                // 1.保存统计记录
                transactionReportRecord = saveTransactionReportRecord(transactionReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    Constant.RecordStatus.INITIALIZATION, MerchantType.AGENT.toString(),
                    "初始化交易量报表记录");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(transactionReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(transactionReportRecord.getReportStatus()))
            {

                transactionReportRecord = transactionReportRecordService.updateTransactionReportRecord(
                    Constant.TransactionReportRecord.TYPE_TRANSACTION,
                    MerchantType.AGENT.toString(), Constant.RecordStatus.PENDING, "处理中",
                    ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(transactionReportRecord))
                {
                    // 2.统计出交易量报表记录
                    List<TransactionReportInfo> transactionReports = censusTransactionReports(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.AGENT.toString());
                    // 3保存交易量
                    if (Constant.RecordStatus.PENDING.equals(transactionReportRecord.getReportStatus()))
                    {
                        transactionReportsService.saveTransactionReports(transactionReports);
                    }
                    transactionReportRecord = saveTransactionReportRecord(transactionReportRecord,
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        Constant.RecordStatus.SUCCESS, MerchantType.AGENT.toString(),
                        "代理商交易报表保存成功");// 修改备注
                }

            }

        }
        catch (Exception e)
        {
            saveTransactionReportRecord(transactionReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), Constant.RecordStatus.FAILURE,
                MerchantType.AGENT.toString(), "代理商交易报表保存失败");
            throw new ApplicationContextException(
                "[统计商户交易量方法报错][TransactionReportByMerchantActionImpl.censusTransactionReportByAgentMerchant())]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug(" [结束统计商户交易量方法][TransactionReportByMerchantActionImpl.censusTransactionReportBySupplyMerchant()]");
    }

    @Override
    public void censusSupplyTransactionReport()
    {
        LOGGER.debug("[进入统计商户交易量方法][TransactionReportByMerchantActionImpl.censusSupplyTransactionReport()]");
        // 统计下游各个产品已成交量

        TransactionReportRecord transactionReportRecord = new TransactionReportRecord();
        try
        {
            transactionReportRecord = transactionReportRecordService.queryTransactionReportRecord(
                MerchantType.SUPPLY.toString(), Constant.TransactionReportRecord.TYPE_TRANSACTION,
                ReportTool.getBeginTime());

            if (BeanUtils.isNull(transactionReportRecord))
            {
                transactionReportRecord = new TransactionReportRecord();
                // 1.保存统计记录
                transactionReportRecord = saveTransactionReportRecord(transactionReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    Constant.RecordStatus.INITIALIZATION, MerchantType.SUPPLY.toString(),
                    "初始化交易量报表记录");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(transactionReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(transactionReportRecord.getReportStatus()))
            {

                transactionReportRecord = transactionReportRecordService.updateTransactionReportRecord(
                    Constant.TransactionReportRecord.TYPE_TRANSACTION,
                    MerchantType.SUPPLY.toString(), Constant.RecordStatus.PENDING, "处理中",
                    ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(transactionReportRecord))
                {
                    // 2.统计出交易量报表记录
                    List<TransactionReportInfo> transactionReports = censusTransactionReports(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.SUPPLY.toString());
                    // 3保存交易量
                    transactionReportsService.saveTransactionReports(transactionReports);
                    saveTransactionReportRecord(transactionReportRecord,
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        Constant.RecordStatus.SUCCESS, MerchantType.SUPPLY.toString(),
                        "供货商交易报表保存成功");
                }
            }

        }
        catch (Exception e)
        {
            saveTransactionReportRecord(transactionReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), Constant.RecordStatus.FAILURE,
                MerchantType.SUPPLY.toString(), "供货商交易报表保存失败");
            throw new ApplicationContextException(
                "[统计商户交易量方法报错][TransactionReportByMerchantActionImpl.censusSupplyTransactionReport())]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug(" [结束统计商户交易量方法][TransactionReportByMerchantActionImpl.censusSupplyTransactionReport()]");
    }

    /**
     * 获得补全的交易量报表
     * 
     * @param transactionAttribute
     * @return
     * @see
     */
    public TransactionReportInfo getTransactionReport(TransactionReportBo transactionReportBo,
                                                      Date beginTime, Date endTime)
    {
        LOGGER.debug("[开始交易量报表元数据生成]"
                     + "[TransactionReportActionImpl.getTransactionReport(transactionReportBo:"
                     + transactionReportBo.toString() + ")]");

        TransactionReportInfo transactionReport = new TransactionReportInfo();
        try
        {
            BeanUtils.copyProperties(transactionReport, transactionReportBo);
            transactionReport.setBeginTime(beginTime);
            transactionReport.setEndTime(endTime);

            Merchant merchant = merchantService.queryMerchantById(Long.valueOf(transactionReportBo.getMerchantId()));

            transactionReport.setMerchantName(merchant.getMerchantName());
            String merchantType = merchant.getMerchantType().toString();
            transactionReport.setMerchantType(merchantType);

            if (MerchantType.AGENT.toString().equals(merchantType))
            {
                transactionReport.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_AGENT_ZH_CN);
            }
            else
            {
                transactionReport.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_SUPPLY_ZH_CN);
                transactionReport.setTotalSalesFee(transactionReportBo.getCostFee());

            }
            CarrierInfo cainfo = carrierInfoService.findOne(transactionReportBo.getCarrierNo());
            transactionReport.setCarrierName(cainfo.getCarrierName());

            Province province = provinceService.findOne(transactionReportBo.getProvince());
            transactionReport.setProvinceName(province.getProvinceName());

            City city = cityService.findOne(transactionReportBo.getCity());
            transactionReport.setCityName(city.getCityName());
            // 状态
            transactionReport.setReportsStatusName(ReportTool.setOrderStatusList(Long.valueOf(transactionReportBo.getReportsStatus())));

        }
        catch (Exception e)
        {
            LOGGER.error("[交易量报表元数据生成报错: "
                         + ExceptionUtil.getStackTraceAsString(e)
                         + "][TransactionReportActionImpl.getTransactionReport(transactionAttribute:["
                         + transactionReportBo.toString() + "])]");
            throw new ApplicationContextException(
                "交易量报表元数据生成报错: [TransactionReportActionImpl :getTransactionReport(transactionReportBo:"
                    + transactionReportBo.toString() + ")]", e);
        }

        LOGGER.debug("[结束交易量报表元数据生成]"
                     + "[TransactionReportActionImpl.getTransactionReport(transactionAttribute:["
                     + transactionReportBo.toString() + "])]");
        return transactionReport;
    }

    public List<TransactionReportInfo> censusTransactionReports(Date beginTime, Date endTime,
                                                                String merchantType)
    {

        List<TransactionReportInfo> transactionReports = new ArrayList<TransactionReportInfo>();

        try
        {
            List<TransactionReportBo> transactionReportPos = transactionReportsService.censusTransactionInfos(
                beginTime, endTime, merchantType);
            for (TransactionReportBo transactionReportBo : transactionReportPos)
            {
                TransactionReportInfo transactionReport = getTransactionReport(
                    transactionReportBo, beginTime, endTime);
                transactionReports.add(transactionReport);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "交易量报表查询统计报错: [TransactionReportActionImpl :censusTransactionReports(beginTime:"
                    + beginTime + ",endTime:" + endTime + ",merchantType:" + merchantType, e);
        }

        return transactionReports;
    }

    public TransactionReportRecord saveTransactionReportRecord(TransactionReportRecord transactionReportRecord,
                                                               Date beginDate, Date endDate,
                                                               String status, String merchantType,
                                                               String describe)
    {
        transactionReportRecord.setBeginDate(beginDate);
        transactionReportRecord.setEndDate(endDate);
        transactionReportRecord.setUpdateDate(new Date());
        transactionReportRecord.setReportStatus(status);
        transactionReportRecord.setReportDescribe(describe);
        transactionReportRecord.setReportType(Constant.TransactionReportRecord.TYPE_TRANSACTION);
        transactionReportRecord.setMerchantType(merchantType);
        transactionReportRecord = transactionReportRecordService.saveTransactionReportRecord(transactionReportRecord);
        return transactionReportRecord;
    }

    public void censusAgentTransactionReportTest(String beginTime, String endTime)
    {

        LOGGER.debug("[进入统计商户交易量方法][TransactionReportByMerchantActionImpl.censusTransactionReportByAgentMerchant()]");
        // 统计下游各个产品已成交量
        // 过程。。
        Date beginTimeDate = null;
        Date endTimeDate = null;
        TransactionReportRecord transactionReportRecord = new TransactionReportRecord();
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (StringUtil.isBlank(beginTime) && StringUtil.isBlank(endTime))
            {
                beginTimeDate = ReportTool.getBeginTime();
                endTimeDate = ReportTool.getEndTime();
            }
            else
            {
                beginTimeDate = format.parse(beginTime);
                endTimeDate = format.parse(endTime);
            }
            // 1.保存统计记录
            transactionReportRecord = saveTransactionReportRecord(transactionReportRecord,
                beginTimeDate, endTimeDate, Constant.RecordStatus.INITIALIZATION,
                MerchantType.AGENT.toString(), "初始化交易量报表记录");
            // 2.统计出交易量报表记录
            List<TransactionReportInfo> transactionReports = censusTransactionReports(
                beginTimeDate, endTimeDate, MerchantType.AGENT.toString());
            // 3保存交易量
            transactionReportsService.saveTransactionReports(transactionReports);
            saveTransactionReportRecord(transactionReportRecord, beginTimeDate, endTimeDate,
                Constant.RecordStatus.SUCCESS, MerchantType.AGENT.toString(), "批量保存成功");// 修改备注
        }
        catch (Exception e)
        {
            LOGGER.error("[统计商户交易量方法报错][TransactionReportByMerchantActionImpl.censusAgentTransactionReportTest())]"
                         + ExceptionUtil.getStackTraceAsString(e));
            saveTransactionReportRecord(transactionReportRecord, beginTimeDate, endTimeDate,
                Constant.RecordStatus.FAILURE, MerchantType.AGENT.toString(), "批量保存到代理商交易报表失败");
        }

        LOGGER.debug(" [结束统计商户交易量方法][TransactionReportByMerchantActionImpl.censusAgentTransactionReportTest()]");
    }

    @Override
    public void censusSupplyTransactionReportTest(String beginTime, String endTime)
    {
        LOGGER.debug("[进入统计商户交易量方法][TransactionReportByMerchantActionImpl.censusTransactionReportByAgentMerchant()]");
        // 统计下游各个产品已成交量
        Date beginTimeDate = null;
        Date endTimeDate = null;
        TransactionReportRecord transactionReportRecord = new TransactionReportRecord();
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (StringUtil.isBlank(beginTime) && StringUtil.isBlank(endTime))
            {
                beginTimeDate = ReportTool.getBeginTime();
                endTimeDate = ReportTool.getEndTime();
            }
            else
            {
                beginTimeDate = format.parse(beginTime);
                endTimeDate = format.parse(endTime);

            }
            transactionReportRecord = saveTransactionReportRecord(transactionReportRecord,
                beginTimeDate, endTimeDate, Constant.RecordStatus.INITIALIZATION,
                MerchantType.SUPPLY.toString(), "初始化交易量报表记录");
            List<TransactionReportInfo> transactionReports = censusTransactionReports(
                beginTimeDate, endTimeDate, MerchantType.SUPPLY.toString());

            transactionReportsService.saveTransactionReports(transactionReports);
            saveTransactionReportRecord(transactionReportRecord, beginTimeDate, endTimeDate,
                Constant.RecordStatus.SUCCESS, MerchantType.SUPPLY.toString(), "供货商交易报表保存成功");
        }
        catch (Exception e)
        {
            LOGGER.error("[统计商户交易量方法报错][TransactionReportByMerchantActionImpl.censusSupplyTransactionReportTest())]"
                         + ExceptionUtil.getStackTraceAsString(e));
            saveTransactionReportRecord(transactionReportRecord, beginTimeDate, endTimeDate,
                Constant.RecordStatus.FAILURE, MerchantType.SUPPLY.toString(), "供货商交易报表保存失败");
        }

        LOGGER.debug(" [结束统计商户交易量方法][TransactionReportByMerchantActionImpl.censusSupplyTransactionReportTest()]");
    }

}