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
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.report.entity.AgentTransactionReportInfo;
import com.yuecheng.hops.report.entity.SupplyTransactionReportInfo;
import com.yuecheng.hops.report.entity.TransactionReportRecord;
import com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo;
import com.yuecheng.hops.report.execution.MerchantTransactionReportAcion;
import com.yuecheng.hops.report.service.AgentTransactionReportService;
import com.yuecheng.hops.report.service.SupplyTransactionReportService;
import com.yuecheng.hops.report.service.TransactionReportRecordService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 商户出报表基础功能
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see MerchantTransactionReportActionImpl
 * @since
 */
@Service("merchantTransactionReportAcion")
public class MerchantTransactionReportActionImpl implements MerchantTransactionReportAcion
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
    private AgentTransactionReportService agentTransactionReportsService;

    @Autowired
    private SupplyTransactionReportService supplyTransactionReportsService;

    @Autowired
    private ProductPageQuery productPageQuery;

    @Autowired
    private TransactionReportRecordService transactionReportRecordService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantTransactionReportActionImpl.class);

    @Override
    public void censusAgentTransactionReport()
    {
        LOGGER.debug("[进入统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant()]");

        // 统计下游各个产品已成交量
        TransactionReportRecord transactionReportRecord = new TransactionReportRecord();

        try
        {
            transactionReportRecord = transactionReportRecordService.queryTransactionReportRecord(
                MerchantType.AGENT.toString(), Constant.TransactionReportRecord.TYPE_AGENT,
                ReportTool.getBeginTime());

            if (BeanUtils.isNull(transactionReportRecord))
            {
                transactionReportRecord = new TransactionReportRecord();
                // 1.初始化代理交易量报表
                transactionReportRecord = saveTransactionReportControl(transactionReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    Constant.RecordStatus.INITIALIZATION, MerchantType.AGENT.toString(),
                    Constant.TransactionReportRecord.TYPE_AGENT, "初始化");
            }
            if (!Constant.RecordStatus.SUCCESS.equals(transactionReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(transactionReportRecord.getReportStatus()))
            {

                transactionReportRecord = transactionReportRecordService.updateTransactionReportRecord(
                    Constant.TransactionReportRecord.TYPE_AGENT, MerchantType.AGENT.toString(),
                    Constant.RecordStatus.PENDING, "处理中", ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(transactionReportRecord))
                { // 2.统计出代理交易量报表
                    @SuppressWarnings("unchecked")
                    List<AgentTransactionReportInfo> agentTransactionReports = (List<AgentTransactionReportInfo>)censusTransactionReports(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.AGENT.toString());

                    // 3.保存代理交易量报表
                    agentTransactionReportsService.saveAgentTransactionReports(agentTransactionReports);
                    transactionReportRecord = saveTransactionReportControl(
                        transactionReportRecord, ReportTool.getBeginTime(),
                        ReportTool.getEndTime(), Constant.RecordStatus.SUCCESS,
                        MerchantType.AGENT.toString(),
                        Constant.TransactionReportRecord.TYPE_AGENT, "代理商交易报表保存成功");
                }

            }

        }
        catch (Exception e)
        {
            saveTransactionReportControl(transactionReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), Constant.RecordStatus.FAILURE,
                MerchantType.AGENT.toString(), Constant.TransactionReportRecord.TYPE_AGENT,
                " 代理商交易报表保存失败");
            throw new ApplicationContextException(
                "[统计商户交易量方法报错][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant())]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug(" [结束统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportBySupplyMerchant()]");
    }

    @Override
    public void censusSupplyTransactionReport()
    {
        LOGGER.info("[进入统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant()]");
        TransactionReportRecord transactionReportRecord = new TransactionReportRecord();
        try
        {
            transactionReportRecord = transactionReportRecordService.queryTransactionReportRecord(
                MerchantType.SUPPLY.toString(), Constant.TransactionReportRecord.TYPE_SUPPLY,
                ReportTool.getBeginTime());

            if (BeanUtils.isNull(transactionReportRecord))
            {
                transactionReportRecord = new TransactionReportRecord();
                // 1.初始化代理交易量报表
                transactionReportRecord = saveTransactionReportControl(transactionReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    Constant.RecordStatus.INITIALIZATION, MerchantType.SUPPLY.toString(),
                    Constant.TransactionReportRecord.TYPE_SUPPLY, "初始化");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(transactionReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(transactionReportRecord.getReportStatus()))
            {

                transactionReportRecord = transactionReportRecordService.updateTransactionReportRecord(
                    Constant.TransactionReportRecord.TYPE_SUPPLY, MerchantType.SUPPLY.toString(),
                    Constant.RecordStatus.PENDING, "处理中", ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(transactionReportRecord))
                {
                    // 2.统计出供货交易量报表
                    @SuppressWarnings("unchecked")
                    List<SupplyTransactionReportInfo> supplyTransactionReports = (List<SupplyTransactionReportInfo>)censusTransactionReports(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.SUPPLY.toString());
                    // 3.保存供货交易量报表
                    supplyTransactionReportsService.saveSupplyTransactionReports(supplyTransactionReports);
                    transactionReportRecord = saveTransactionReportControl(
                        transactionReportRecord, ReportTool.getBeginTime(),
                        ReportTool.getEndTime(), Constant.RecordStatus.SUCCESS,
                        MerchantType.SUPPLY.toString(),
                        Constant.TransactionReportRecord.TYPE_SUPPLY, "供货商交易报表保存成功");
                }

            }

        }
        catch (Exception e)
        {
            saveTransactionReportControl(transactionReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), Constant.RecordStatus.FAILURE,
                MerchantType.AGENT.toString(), Constant.TransactionReportRecord.TYPE_SUPPLY,
                " 供货商交易报表保存失败");
            
            
            throw new ApplicationContextException(
                "[统计商户交易量方法报错][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant())]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug(" [结束统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportBySupplyMerchant()]");
    }

    public AgentTransactionReportInfo getAgentTransactionReport(MerchantTransactionReportBo merchantTransactionReportPo,
                                                                Date beginTime, Date endTime)
    {
        AgentTransactionReportInfo agentTransactionReport = new AgentTransactionReportInfo();
        try
        {
            BeanUtils.copyProperties(agentTransactionReport, merchantTransactionReportPo);
            agentTransactionReport.setBeginTime(beginTime);
            agentTransactionReport.setEndTime(endTime);

            AirtimeProduct airtimeProduct = productPageQuery.queryAirtimeProductById(merchantTransactionReportPo.getProductId());
            if (BeanUtils.isNull(airtimeProduct))
            {
                airtimeProduct = productPageQuery.queryAirtimeProductByDeleteId(merchantTransactionReportPo.getProductId());
            }

            Merchant merchant = merchantService.queryMerchantById(Long.valueOf(merchantTransactionReportPo.getMerchantId()));
            agentTransactionReport.setMerchantId(merchant.getId());
            agentTransactionReport.setMerchantName(merchant.getMerchantName());
            agentTransactionReport.setMerchantType(Constant.ReportType.MERCHANTTYPE_AGENT);
            agentTransactionReport.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_AGENT_ZH_CN);
            agentTransactionReport.setProductId(airtimeProduct.getProductId());
            agentTransactionReport.setParValue(airtimeProduct.getParValue());
            agentTransactionReport.setProductName(airtimeProduct.getProductName());

            if (StringUtil.isNotBlank(airtimeProduct.getCarrierName()))
            {
                CarrierInfo cainfo = carrierInfoService.findOne(airtimeProduct.getCarrierName());
                agentTransactionReport.setCarrierNo(cainfo.getCarrierNo());
                agentTransactionReport.setCarrierName(cainfo.getCarrierName());
            }

            if (StringUtil.isNotBlank(airtimeProduct.getProvince()))
            {
                Province province = provinceService.findOne(airtimeProduct.getProvince());
                agentTransactionReport.setProvince(airtimeProduct.getProvince());
                agentTransactionReport.setProvinceName(province.getProvinceName());
            }
            else
            {
                agentTransactionReport.setProvince("***");
            }

            if (StringUtil.isNotBlank(airtimeProduct.getCity()))
            {
                City city = cityService.findOne(airtimeProduct.getCity());
                agentTransactionReport.setCity(airtimeProduct.getCity());
                agentTransactionReport.setCityName(city.getCityName());
            }
            else
            {
                agentTransactionReport.setCity("****");
            }

            agentTransactionReport.setReportsStatus(merchantTransactionReportPo.getReportsStatus());
            agentTransactionReport.setReportsStatusName(ReportTool.setOrderStatusList(Long.valueOf(merchantTransactionReportPo.getReportsStatus())));

        }
        catch (Exception e)
        {
            LOGGER.error("[结束统计商户交易量方法][MerchantTransactionReportActionImpl.getSupplyTransactionReport()] 异常: "
                         + ExceptionUtil.getStackTraceAsString(e));

        }
        return agentTransactionReport;
    }

    public SupplyTransactionReportInfo getSupplyTransactionReport(MerchantTransactionReportBo merchantTransactionReportPo,
                                                                  Date beginTime, Date endTime)
    {
        SupplyTransactionReportInfo supplyTransactionReport = new SupplyTransactionReportInfo();
        try
        {
            BeanUtils.copyProperties(supplyTransactionReport, merchantTransactionReportPo);
            supplyTransactionReport.setBeginTime(beginTime);
            supplyTransactionReport.setEndTime(endTime);

            AirtimeProduct airtimeProduct = productPageQuery.queryAirtimeProductById(merchantTransactionReportPo.getProductId());
            if (BeanUtils.isNull(airtimeProduct))
            {
                airtimeProduct = productPageQuery.queryAirtimeProductByDeleteId(merchantTransactionReportPo.getProductId());
            }

            supplyTransactionReport.setTotalSalesFee(merchantTransactionReportPo.getCostFee());
            Merchant merchant = merchantService.queryMerchantById(Long.valueOf(merchantTransactionReportPo.getMerchantId()));
            supplyTransactionReport.setMerchantId(merchant.getId());
            supplyTransactionReport.setMerchantName(merchant.getMerchantName());
            supplyTransactionReport.setMerchantType(Constant.ReportType.MERCHANTTYPE_SUPPLY);
            supplyTransactionReport.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_SUPPLY_ZH_CN);
            supplyTransactionReport.setProductId(airtimeProduct.getProductId());
            supplyTransactionReport.setParValue(airtimeProduct.getParValue());
            supplyTransactionReport.setProductName(airtimeProduct.getProductName());

            if (StringUtil.isNotBlank(airtimeProduct.getCarrierName()))
            {
                CarrierInfo cainfo = carrierInfoService.findOne(airtimeProduct.getCarrierName());
                supplyTransactionReport.setCarrierNo(cainfo.getCarrierNo());
                supplyTransactionReport.setCarrierName(cainfo.getCarrierName());
            }

            if (StringUtil.isNotBlank(airtimeProduct.getProvince()))
            {
                Province province = provinceService.findOne(airtimeProduct.getProvince());
                supplyTransactionReport.setProvince(airtimeProduct.getProvince());
                supplyTransactionReport.setProvinceName(province.getProvinceName());
            }
            else
            {
                supplyTransactionReport.setProvince("***");
            }

            if (StringUtil.isNotBlank(airtimeProduct.getCity()))
            {
                City city = cityService.findOne(airtimeProduct.getCity());
                supplyTransactionReport.setCity(airtimeProduct.getCity());
                supplyTransactionReport.setCityName(city.getCityName());
            }
            else
            {
                supplyTransactionReport.setCity("****");
            }

            supplyTransactionReport.setTotalParValue(merchantTransactionReportPo.getTotalParValue());
            supplyTransactionReport.setReportsStatus(merchantTransactionReportPo.getReportsStatus());
            supplyTransactionReport.setReportsStatusName(ReportTool.setOrderStatusList(Long.valueOf(merchantTransactionReportPo.getReportsStatus())));

            supplyTransactionReport.setTransactionNum(merchantTransactionReportPo.getTransactionNum());
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "[结束统计商户交易量方法][MerchantTransactionReportActionImpl.getSupplyTransactionReport()] 异常: "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        return supplyTransactionReport;
    }

    public TransactionReportRecord saveTransactionReportControl(TransactionReportRecord transactionReportControl,
                                                                Date beginDate, Date endDate,
                                                                String status,
                                                                String merchantType, String type,
                                                                String describe)
    {
        transactionReportControl.setBeginDate(beginDate);
        transactionReportControl.setEndDate(endDate);
        transactionReportControl.setUpdateDate(new Date());
        transactionReportControl.setReportStatus(status);
        transactionReportControl.setMerchantType(merchantType);
        transactionReportControl.setReportDescribe(describe);
        transactionReportControl.setReportType(type);
        transactionReportControl = transactionReportRecordService.saveTransactionReportRecord(transactionReportControl);
        return transactionReportControl;
    }

    public List<?> censusTransactionReports(Date beginTime, Date endTime, String merchantType)
    {
        List<MerchantTransactionReportBo> merchantTransactionReportInfos = new ArrayList<MerchantTransactionReportBo>();
        if (MerchantType.AGENT.toString().equals(merchantType))
        {
            merchantTransactionReportInfos = agentTransactionReportsService.censusMerchantTransactionInfo(
                beginTime, endTime);
            List<AgentTransactionReportInfo> agentTransactionReports = new ArrayList<AgentTransactionReportInfo>();
            for (MerchantTransactionReportBo merchantTransactionReportInfo : merchantTransactionReportInfos)
            {
                AgentTransactionReportInfo agentTransactionReport = getAgentTransactionReport(
                    merchantTransactionReportInfo, beginTime, endTime);
                agentTransactionReports.add(agentTransactionReport);
            }
            return agentTransactionReports;
        }
        else
        {
            merchantTransactionReportInfos = supplyTransactionReportsService.censusMerchantTransactionInfo(
                beginTime, endTime);
            List<SupplyTransactionReportInfo> supplyTransactionReports = new ArrayList<SupplyTransactionReportInfo>();
            for (MerchantTransactionReportBo merchantTransactionReportInfo : merchantTransactionReportInfos)
            {
                SupplyTransactionReportInfo supplyTransactionReport = getSupplyTransactionReport(
                    merchantTransactionReportInfo, beginTime, endTime);
                supplyTransactionReports.add(supplyTransactionReport);
            }
            return supplyTransactionReports;
        }
    }

    @Override
    public void censusAgentTransactionReportTest(String beginTime, String endTime)
    {
        LOGGER.debug("[进入统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant()]");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 统计下游各个产品已成交量
        TransactionReportRecord transactionReportControl = new TransactionReportRecord();
        Date beginTimeDate = null;
        Date endTimeDate = null;
        try
        {
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
            // 1.初始化代理交易量报表
            transactionReportControl = saveTransactionReportControl(transactionReportControl,
                beginTimeDate, endTimeDate, Constant.RecordStatus.INITIALIZATION,
                MerchantType.AGENT.toString(), Constant.TransactionReportRecord.TYPE_AGENT, "初始化");

            @SuppressWarnings("unchecked")
            // 2.统计出代理交易量报表
            List<AgentTransactionReportInfo> agentTransactionReports = (List<AgentTransactionReportInfo>)censusTransactionReports(
                beginTimeDate, endTimeDate, MerchantType.AGENT.toString());

            // 保存代理交易量报表
            agentTransactionReportsService.saveAgentTransactionReports(agentTransactionReports);
            transactionReportControl = saveTransactionReportControl(transactionReportControl,
                beginTimeDate, endTimeDate, Constant.RecordStatus.SUCCESS,
                MerchantType.AGENT.toString(), Constant.TransactionReportRecord.TYPE_AGENT,
                "批量保存成功");
        }
        catch (Exception e)
        {
            saveTransactionReportControl(transactionReportControl, beginTimeDate, endTimeDate,
                Constant.RecordStatus.FAILURE, MerchantType.AGENT.toString(),
                Constant.TransactionReportRecord.TYPE_AGENT, " 批量保存失败");
            throw new ApplicationContextException(
                "[统计商户交易量方法报错][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant())]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug(" [结束统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportBySupplyMerchant()]");
    }

    @Override
    public void censusSupplyTransactionReportTest(String beginTime, String endTime)
    {
        LOGGER.info("[进入统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant()]");
        // 统计下游各个产品已成交量

        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date beginTimeDate = null;
            Date endTimeDate = null;
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
            TransactionReportRecord transactionReportControl = new TransactionReportRecord();
            transactionReportControl = saveTransactionReportControl(transactionReportControl,
                beginTimeDate, endTimeDate, Constant.RecordStatus.INITIALIZATION,
                MerchantType.SUPPLY.toString(), Constant.TransactionReportRecord.TYPE_SUPPLY,
                "无操作");
            @SuppressWarnings("unchecked")
            List<SupplyTransactionReportInfo> supplyTransactionReports = (List<SupplyTransactionReportInfo>)censusTransactionReports(
                beginTimeDate, endTimeDate, MerchantType.SUPPLY.toString());

            supplyTransactionReportsService.saveSupplyTransactionReports(supplyTransactionReports);
            transactionReportControl = saveTransactionReportControl(transactionReportControl,
                beginTimeDate, endTimeDate, Constant.RecordStatus.SUCCESS,
                MerchantType.SUPPLY.toString(), Constant.TransactionReportRecord.TYPE_SUPPLY,
                "批量保存成功");
        }
        catch (Exception e)
        {
            LOGGER.error("[统计商户交易量方法报错][MerchantTransactionReportActionImpl.censusTransactionReportByAgentMerchant())]"
                         + ExceptionUtil.getStackTraceAsString(e));
        }

        LOGGER.debug(" [结束统计商户交易量方法][MerchantTransactionReportActionImpl.censusTransactionReportBySupplyMerchant()]");
    }

}