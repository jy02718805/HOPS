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
import com.yuecheng.hops.report.entity.RefundReportInfo;
import com.yuecheng.hops.report.entity.RefundReportRecord;
import com.yuecheng.hops.report.entity.bo.RefundReportBo;
import com.yuecheng.hops.report.execution.RefundReportAction;
import com.yuecheng.hops.report.service.ProfitReportService;
import com.yuecheng.hops.report.service.RefundReportRecordService;
import com.yuecheng.hops.report.service.RefundReportService;
import com.yuecheng.hops.report.service.TransactionReportService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 退款报表统计
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see RefundReportActionImpl
 * @since
 */
@Service("refundReportAction")
public class RefundReportActionImpl implements RefundReportAction
{
    @Autowired
    protected MerchantService merchantService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private RefundReportService refundReportService;

    @Autowired
    private TransactionReportService transactionReportsService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private RefundReportRecordService RefundReportRecordService;

    @Autowired
    private ProfitReportService profitReportService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RefundReportActionImpl.class);

    /**
     * 扫描，统计出退款报表的元数据
     */
    @Override
    public void censusRefundReportByAgent()
    {

        LOGGER.debug("[进入扫描退款报表元数据][RefundReportActionImpl.censusRefundReportByAgent()]");
        RefundReportRecord RefundReportRecord = new RefundReportRecord();

        try
        {
            RefundReportRecord = RefundReportRecordService.queryRefundReportRecord(
                MerchantType.AGENT.toString(), ReportTool.getBeginTime());
            if (BeanUtils.isNull(RefundReportRecord))
            {
                RefundReportRecord = new RefundReportRecord();
                // 1.初始化退款报表统计记录
                RefundReportRecord = saveRefundReportRecord(RefundReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    MerchantType.AGENT.toString(), Constant.RecordStatus.INITIALIZATION, "初始化");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(RefundReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(RefundReportRecord.getReportStatus()))
            {
                RefundReportRecord = RefundReportRecordService.updateRefundReportRecord(
                    MerchantType.AGENT.toString(), Constant.RecordStatus.PENDING, "处理中",
                    ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(RefundReportRecord))
                {
                    // 2.统计出退款报表
                    List<RefundReportInfo> refundReportInfos = censusRefundReport(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.AGENT.toString());

                    // 保存退款报表
                    refundReportService.saveRefundReports(refundReportInfos);
                    RefundReportRecord = saveRefundReportRecord(RefundReportRecord,
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.AGENT.toString(), Constant.RecordStatus.SUCCESS, "保存成功");
                }

            }

        }
        catch (Exception e)
        {
            saveRefundReportRecord(RefundReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), MerchantType.AGENT.toString(),
                Constant.RecordStatus.FAILURE, "该条统计数据保存到退款报表失败");
            throw new ApplicationContextException(
                "[扫描退款报表元数据报错]"
                    + "[RefundReportByMerchantActionImpl.censusRefundReportByAgent()] "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug("[结束扫描退款报表元数据]"
                     + "[RefundReportByMerchantActionImpl.censusRefundReportByAgent()]");
    }

    /**
     * 补全退款报表
     */
    public RefundReportInfo getRefundReport(RefundReportBo refundReportBo, Date beginTime,
                                            Date endTime)
    {
        LOGGER.debug("[开始退款报表元数据生成][RefundReportActionImpl.saveRefund(RefundAttribute:"
                     + refundReportBo.toString() + ")]");

        RefundReportInfo refundReportInfo = new RefundReportInfo();
        try
        {

            BeanUtils.copyProperties(refundReportInfo, refundReportBo);

            refundReportInfo.setBeginTime(beginTime);
            refundReportInfo.setEndTime(endTime);
            refundReportInfo.setManualAuditDate(refundReportBo.getManualAuditDate());
            Merchant merchant = merchantService.queryMerchantById(Long.valueOf(refundReportBo.getMerchantId()));

            refundReportInfo.setMerchantName(merchant.getMerchantName());
            String merchantType = merchant.getMerchantType().toString();
            refundReportInfo.setMerchantType(merchantType);

            if (MerchantType.AGENT.toString().equals(merchantType))
            {
                refundReportInfo.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_AGENT_ZH_CN);
                refundReportInfo.setRefundFace(refundReportBo.getOrderSalesFee());
            }
            else
            {
                refundReportInfo.setMerchantTypeName(Constant.ReportType.MERCHANTTYPE_SUPPLY_ZH_CN);
                refundReportInfo.setRefundFace(refundReportBo.getCostFee());
            }
            CarrierInfo cainfo = carrierInfoService.findOne(refundReportBo.getCarrierNo());
            refundReportInfo.setCarrierName(cainfo.getCarrierName());

            Province province = provinceService.findOne(refundReportBo.getProvince());
            refundReportInfo.setProvinceName(province.getProvinceName());

            City city = cityService.findOne(refundReportBo.getCity());
            refundReportInfo.setCityName(city.getCityName());
            refundReportInfo.setParValue(refundReportBo.getParValue());
            LOGGER.debug("[退款报表元数据保存][RefundReportActionImpl.saveRefund()][RefundReportsService.saveRefundReport(RefundReport:"
                         + refundReportInfo.toString() + ")]");
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "退款报表元数据生成报错: [RefundReportActionImpl :getUpdateRefundReport(RefundReportInfo:"
                    + refundReportBo.toString() + ")]", e);
        }

        LOGGER.debug("[结束退款报表元数据生成]" + "[RefundReportActionImpl.saveRefund(RefundAttribute:["
                     + refundReportBo.toString() + "])]");
        return refundReportInfo;
    }

    /**
     * 统计退款报表
     */
    public List<RefundReportInfo> censusRefundReport(Date beginTime, Date endTime,
                                                     String merchatType)
    {

        List<RefundReportInfo> refundReports = new ArrayList<RefundReportInfo>();
        try
        {
            List<RefundReportBo> refundReportBos = refundReportService.censusRefundInfos(
                beginTime, endTime, merchatType);

            for (RefundReportBo refundReportBo : refundReportBos)
            {

                RefundReportInfo refundReportInfo = getRefundReport(refundReportBo, beginTime,
                    endTime);
                // if (format.format(refundReportBo.getManualAuditDate()).equals(
                // format.format(refundReportBo.getBeginTime())))
                // {
                // ProfitReportInfo profitReportInfo = new ProfitReportInfo();
                // BeanUtils.copyProperties(profitReportInfo, refundReportInfo);
                // profitReportInfo.setSuccessFace(refundReportInfo.getRefundFace());
                //
                // }
                refundReports.add(refundReportInfo);

            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "退款报表查询统计报错: [RefundReportActionImpl :censusRefundReport(beginTime:" + beginTime
                    + ",endTime:" + endTime + "merchatType:" + merchatType, e);

        }
        return refundReports;
    }

    public RefundReportRecord saveRefundReportRecord(RefundReportRecord RefundReportRecord,
                                                     Date beginDate, Date endDate,
                                                     String merchantType, String status,
                                                     String describe)
    {
        RefundReportRecord.setBeginDate(beginDate);
        RefundReportRecord.setEndDate(endDate);
        RefundReportRecord.setMerchantType(merchantType);
        RefundReportRecord.setUpdateDate(new Date());
        RefundReportRecord.setReportStatus(status);
        RefundReportRecord.setReportDescribe(describe);
        RefundReportRecord = RefundReportRecordService.saveRefundReportRecord(RefundReportRecord);
        return RefundReportRecord;
    }

    @Override
    public void censusRefundReportBySupply()
    {
        LOGGER.debug("[进入扫描退款报表元数据][RefundReportActionImpl.censusRefundReportBySupply()]");
        RefundReportRecord RefundReportRecord = new RefundReportRecord();

        try
        {
            RefundReportRecord = RefundReportRecordService.queryRefundReportRecord(
                MerchantType.SUPPLY.toString(), ReportTool.getBeginTime());
            if (BeanUtils.isNull(RefundReportRecord))
            {
                RefundReportRecord = new RefundReportRecord();
                // 1.初始化退款报表统计记录
                RefundReportRecord = saveRefundReportRecord(RefundReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    MerchantType.SUPPLY.toString(), Constant.RecordStatus.INITIALIZATION, "初始化");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(RefundReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(RefundReportRecord.getReportStatus()))
            {
                RefundReportRecord = RefundReportRecordService.updateRefundReportRecord(
                    MerchantType.SUPPLY.toString(), Constant.RecordStatus.PENDING, "处理中",
                    ReportTool.getBeginTime());
                if (BeanUtils.isNotNull(RefundReportRecord))
                {
                    // 2.统计出退款报表
                    List<RefundReportInfo> refundinfos = censusRefundReport(
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.SUPPLY.toString());

                    // 保存退款报表
                    refundReportService.saveRefundReports(refundinfos);
                    RefundReportRecord = saveRefundReportRecord(RefundReportRecord,
                        ReportTool.getBeginTime(), ReportTool.getEndTime(),
                        MerchantType.SUPPLY.toString(), Constant.RecordStatus.SUCCESS, "保存成功");
                }

            }

        }
        catch (Exception e)
        {
            saveRefundReportRecord(RefundReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), MerchantType.SUPPLY.toString(),
                Constant.RecordStatus.FAILURE, "该条统计数据保存到退款报表失败");
            throw new ApplicationContextException(
                "[扫描退款报表元数据报错]"
                    + "[RefundReportByMerchantActionImpl.censusRefundReportBySupply()] "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }

        LOGGER.debug("[结束扫描退款报表元数据]"
                     + "[RefundReportByMerchantActionImpl.censusRefundReportBySupply()]");

    }

    // censusRefundReportBySupply
    @Override
    public void censusRefundReportByAgentTest(String beginTime, String endTime)
    {

        LOGGER.debug("[进入扫描退款报表元数据][RefundReportActionImpl.censusRefundReportByMerchant()]");
        RefundReportRecord RefundReportRecord = new RefundReportRecord();
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
            // 1.初始化退款报表统计记录
            List<RefundReportInfo> refundinfos = censusRefundReport(beginTimeDate, endTimeDate,
                MerchantType.AGENT.toString());

            // 保存退款报表
            refundReportService.saveRefundReports(refundinfos);
            RefundReportRecord = saveRefundReportRecord(RefundReportRecord, beginTimeDate,
                endTimeDate, MerchantType.AGENT.toString(), Constant.RecordStatus.SUCCESS, "保存成功");
        }
        catch (Exception e)
        {
            LOGGER.error("[扫描退款报表元数据报错]"
                         + "[RefundReportByMerchantActionImpl.censusRefundReportByMerchant()] "
                         + ExceptionUtil.getStackTraceAsString(e));
            saveRefundReportRecord(RefundReportRecord, beginTimeDate, endTimeDate,
                MerchantType.AGENT.toString(), Constant.RecordStatus.FAILURE, "该条统计数据保存到退款报表失败");
        }

        LOGGER.debug("[结束扫描退款报表元数据]"
                     + "[RefundReportByMerchantActionImpl.censusRefundReportByMerchant()]");
    }

    @Override
    public void censusRefundReportBySupplyTest(String beginTime, String endTime)
    {

        LOGGER.debug("[进入扫描退款报表元数据][RefundReportActionImpl.censusRefundReportByMerchant()]");
        RefundReportRecord RefundReportRecord = new RefundReportRecord();
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
            // 1.初始化退款报表统计记录
            List<RefundReportInfo> refundinfos = censusRefundReport(beginTimeDate, endTimeDate,
                MerchantType.SUPPLY.toString());

            // 保存退款报表
            refundReportService.saveRefundReports(refundinfos);
            RefundReportRecord = saveRefundReportRecord(RefundReportRecord, beginTimeDate,
                endTimeDate, MerchantType.SUPPLY.toString(), Constant.RecordStatus.SUCCESS, "保存成功");
        }
        catch (Exception e)
        {
            LOGGER.error("[扫描退款报表元数据报错]"
                         + "[RefundReportByMerchantActionImpl.censusRefundReportByMerchant()] "
                         + ExceptionUtil.getStackTraceAsString(e));
            saveRefundReportRecord(RefundReportRecord, beginTimeDate, endTimeDate,
                MerchantType.SUPPLY.toString(), Constant.RecordStatus.FAILURE, "该条统计数据保存到退款报表失败");
        }

        LOGGER.debug("[结束扫描退款报表元数据]"
                     + "[RefundReportByMerchantActionImpl.censusRefundReportByMerchant()]");
    }

}