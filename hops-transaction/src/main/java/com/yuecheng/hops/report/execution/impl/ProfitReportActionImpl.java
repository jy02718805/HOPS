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
import com.yuecheng.hops.report.entity.ProfitReportInfo;
import com.yuecheng.hops.report.entity.ProfitReportRecord;
import com.yuecheng.hops.report.entity.bo.ProfitReportBo;
import com.yuecheng.hops.report.execution.ProfitReportAction;
import com.yuecheng.hops.report.service.ProfitReportRecordService;
import com.yuecheng.hops.report.service.ProfitReportService;
import com.yuecheng.hops.report.service.TransactionReportService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 利润报表统计
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ProfitReportActionImpl
 * @since
 */
@Service("profitReportAction")
public class ProfitReportActionImpl implements ProfitReportAction
{
    @Autowired
    protected MerchantService merchantService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private ProfitReportService profitReportsService;

    @Autowired
    private TransactionReportService transactionReportsService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private ProfitReportRecordService profitReportRecordService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitReportActionImpl.class);

    /**
     * 扫描，统计出利润报表的元数据
     */
    @Override
    public void censusProfitReportByAgent()
    {

        LOGGER.debug("[进入扫描利润报表元数据][ProfitReportActionImpl.censusProfitReportByAgent()]");
        ProfitReportRecord profitReportRecord = new ProfitReportRecord();

        try
        {
            profitReportRecord = profitReportRecordService.queryProfitReportRecord(
                MerchantType.AGENT.toString(), ReportTool.getBeginTime());
            if (BeanUtils.isNull(profitReportRecord))
            {
                profitReportRecord = new ProfitReportRecord();
                // 1.初始化利润报表统计记录
                profitReportRecord = saveProfitReportRecord(profitReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    MerchantType.AGENT.toString(), Constant.RecordStatus.INITIALIZATION, "初始化");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(profitReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(profitReportRecord.getReportStatus()))
            {
                profitReportRecord = profitReportRecordService.updateProfitReportRecord(
                    MerchantType.AGENT.toString(), Constant.RecordStatus.PENDING, "处理中",
                    ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(profitReportRecord))
                {
                    // throw new Exception("利润报表记录状态修改重复!");
                    // 2.统计出利润报表
                    List<ProfitReportInfo> profitReports = censusProfitReport(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.AGENT.toString());

                    // 保存利润报表
                    profitReportsService.saveProfitReports(profitReports);
                    profitReportRecord = saveProfitReportRecord(profitReportRecord,
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.AGENT.toString(), Constant.RecordStatus.SUCCESS, "保存成功");
                }

            }

        }
        catch (Exception e)
        {
            saveProfitReportRecord(profitReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), MerchantType.AGENT.toString(),
                Constant.RecordStatus.FAILURE, "该条统计数据保存到利润报表失败");
            throw new ApplicationContextException(
                "[扫描利润报表元数据报错]"
                    + "[ProfitReportByMerchantActionImpl.censusProfitReportByAgent()] "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug("[结束扫描利润报表元数据]"
                     + "[ProfitReportByMerchantActionImpl.censusProfitReportByAgent()]");
    }

    /**
     * 补全利润报表
     */
    public ProfitReportInfo getProfitReport(ProfitReportBo profitReportPo, Date beginTime,
                                            Date endTime)
    {
        LOGGER.debug("[开始利润报表元数据生成][ProfitReportActionImpl.saveProfit(profitAttribute:"
                     + profitReportPo.toString() + ")]");

        ProfitReportInfo profitReport = new ProfitReportInfo();
        try
        {

            BeanUtils.copyProperties(profitReport, profitReportPo);

            profitReport.setBeginTime(beginTime);
            profitReport.setEndTime(endTime);

            Merchant merchant = merchantService.queryMerchantById(Long.valueOf(profitReportPo.getMerchantId()));

            profitReport.setMerchantName(merchant.getMerchantName());
            String merchantType = merchant.getMerchantType().toString();
            profitReport.setMerchantType(merchantType);

            if (MerchantType.AGENT.toString().equals(merchantType))
            {
                profitReport.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_AGENT_ZH_CN);
            }
            else
            {
                profitReport.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_SUPPLY_ZH_CN);
            }
            CarrierInfo cainfo = carrierInfoService.findOne(profitReportPo.getCarrierNo());
            profitReport.setCarrierName(cainfo.getCarrierName());

            Province province = provinceService.findOne(profitReportPo.getProvince());
            profitReport.setProvinceName(province.getProvinceName());

            City city = cityService.findOne(profitReportPo.getCity());
            profitReport.setCityName(city.getCityName());
            profitReport.setParValue(profitReportPo.getParValue());
            LOGGER.debug("[利润报表元数据保存][ProfitReportActionImpl.saveProfit()][profitReportsService.saveProfitReport(profitReport:"
                         + profitReport.toString() + ")]");
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "利润报表元数据生成报错: [ProfitReportActionImpl :getUpdateProfitReport(profitReportInfo:"
                    + profitReportPo.toString() + ")]", e);
        }

        LOGGER.debug("[结束利润报表元数据生成]" + "[ProfitReportActionImpl.saveProfit(profitAttribute:["
                     + profitReportPo.toString() + "])]");
        return profitReport;
    }

    /**
     * 统计利润报表
     */
    public List<ProfitReportInfo> censusProfitReport(Date beginTime, Date endTime,
                                                     String merchatType)
    {

        List<ProfitReportInfo> profitReports = new ArrayList<ProfitReportInfo>();

        try
        {
            List<ProfitReportBo> profitReportBos = profitReportsService.censusProfitInfos(
                beginTime, endTime, merchatType);
            for (ProfitReportBo profitReportBo : profitReportBos)
            {

                ProfitReportInfo profitReport = getProfitReport(profitReportBo, beginTime, endTime);
                profitReports.add(profitReport);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "利润报表查询统计报错: [ProfitReportActionImpl :censusProfitReport(beginTime:" + beginTime
                    + ",endTime:" + endTime, e);

        }

        return profitReports;
    }

    public ProfitReportRecord saveProfitReportRecord(ProfitReportRecord profitReportRecord,
                                                     Date beginDate, Date endDate,
                                                     String merchantType, String status,
                                                     String describe)
    {
        profitReportRecord.setBeginDate(beginDate);
        profitReportRecord.setEndDate(endDate);
        profitReportRecord.setMerchantType(merchantType);
        profitReportRecord.setUpdateDate(new Date());
        profitReportRecord.setReportStatus(status);
        profitReportRecord.setReportDescribe(describe);
        profitReportRecord = profitReportRecordService.saveProfitReportRecord(profitReportRecord);
        return profitReportRecord;
    }

    @Override
    public void censusProfitReportBySupply()
    {
        LOGGER.debug("[进入扫描利润报表元数据][ProfitReportActionImpl.censusProfitReportBySupply()]");
        ProfitReportRecord profitReportRecord = new ProfitReportRecord();

        try
        {
            profitReportRecord = profitReportRecordService.queryProfitReportRecord(
                MerchantType.SUPPLY.toString(), ReportTool.getBeginTime());
            if (BeanUtils.isNull(profitReportRecord))
            {
                profitReportRecord = new ProfitReportRecord();
                // 1.初始化利润报表统计记录
                profitReportRecord = saveProfitReportRecord(profitReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    MerchantType.SUPPLY.toString(), Constant.RecordStatus.INITIALIZATION, "初始化");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(profitReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(profitReportRecord.getReportStatus()))
            {
                profitReportRecord = profitReportRecordService.updateProfitReportRecord(
                    MerchantType.SUPPLY.toString(), Constant.RecordStatus.PENDING, "处理中",
                    ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(profitReportRecord))
                {
                    // 2.统计出利润报表
                    List<ProfitReportInfo> profitReports = censusProfitReport(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.SUPPLY.toString());

                    // 保存利润报表
                    profitReportsService.saveProfitReports(profitReports);
                    profitReportRecord = saveProfitReportRecord(profitReportRecord,
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.SUPPLY.toString(), Constant.RecordStatus.SUCCESS, "保存成功");
                }

            }

        }
        catch (Exception e)
        {
            saveProfitReportRecord(profitReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), MerchantType.SUPPLY.toString(),
                Constant.RecordStatus.FAILURE, "该条统计数据保存到利润报表失败");
            throw new ApplicationContextException(
                "[扫描利润报表元数据报错]"
                    + "[ProfitReportByMerchantActionImpl.censusProfitReportBySupply()] "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug("[结束扫描利润报表元数据]"
                     + "[ProfitReportByMerchantActionImpl.censusProfitReportBySupply()]");

    }

    @Override
    public void censusProfitReportByAgentTest(String beginTime, String endTime)
    {

        LOGGER.debug("[进入扫描利润报表元数据][ProfitReportActionImpl.censusProfitReportByMerchant()]");
        ProfitReportRecord profitReportRecord = new ProfitReportRecord();
        Date beginTimeDate = null;
        Date endTimeDate = null;
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
            // 1.初始化利润报表统计记录
            profitReportRecord = saveProfitReportRecord(profitReportRecord, beginTimeDate,
                endTimeDate, MerchantType.AGENT.toString(), Constant.RecordStatus.INITIALIZATION,
                "初始化");

            // 2.统计出利润报表
            List<ProfitReportInfo> profitReports = censusProfitReport(beginTimeDate, endTimeDate,
                MerchantType.AGENT.toString());

            // 保存利润报表
            profitReportsService.saveProfitReports(profitReports);
            profitReportRecord = saveProfitReportRecord(profitReportRecord, beginTimeDate,
                endTimeDate, MerchantType.AGENT.toString(), Constant.RecordStatus.SUCCESS, "保存成功");
        }
        catch (Exception e)
        {
            LOGGER.error("[扫描利润报表元数据报错]"
                         + "[ProfitReportByMerchantActionImpl.censusProfitReportByMerchant()] "
                         + ExceptionUtil.getStackTraceAsString(e));
            saveProfitReportRecord(profitReportRecord, beginTimeDate, endTimeDate,
                MerchantType.AGENT.toString(), Constant.RecordStatus.FAILURE, "该条统计数据保存到利润报表失败");
        }

        LOGGER.debug("[结束扫描利润报表元数据]"
                     + "[ProfitReportByMerchantActionImpl.censusProfitReportByMerchant()]");
    }

}