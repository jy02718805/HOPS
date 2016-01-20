package com.yuecheng.hops.report.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.RefundReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.RefundReportBo;
import com.yuecheng.hops.report.entity.po.RefundReportPo;
import com.yuecheng.hops.report.execution.RefundReportAction;
import com.yuecheng.hops.report.repository.RefundReportDao;
import com.yuecheng.hops.report.repository.jpa.RefundReportJpaDao;
import com.yuecheng.hops.report.service.RefundReportService;


/**
 * 利润报表
 * 
 * @author Administrator
 * @version 2014年10月24日
 * @see RefundReportServiceImpl
 * @since
 */
@Service("refundReportService")
public class RefundReportServiceImpl implements RefundReportService
{
    @Autowired
    private RefundReportDao refundReportDao;

    @Autowired
    private RefundReportJpaDao refundReportJpaDao;

    @Autowired
    private RefundReportAction refundReportAction;

    private static final Logger LOGGER = LoggerFactory.getLogger(RefundReportServiceImpl.class);

    @Override
    public RefundReportInfo saveRefundReport(RefundReportInfo refundReportInfo)
    {
        try
        {
            refundReportInfo = refundReportJpaDao.save(refundReportInfo);
        }
        catch (Exception e)
        {
            LOGGER.error("[保存利润报表报错][RefundReportsServiceImpl.saveRefundReport(RefundReport:"
                         + refundReportInfo.toString() + ")]");
            return null;
        }
        return refundReportInfo;
    }

    @Override
    public List<RefundReportBo> censusRefundInfos(Date beginTime, Date endTime, String merchatType)
    {
        List<RefundReportBo> refundReportPos = refundReportDao.getRefundReportBos(beginTime,
            endTime, merchatType);
        return refundReportPos;
    }

    @Override
    public List<RefundReportInfo> saveRefundReports(List<RefundReportInfo> refundReports)
    {
        refundReports = (List<RefundReportInfo>)refundReportJpaDao.save(refundReports);
        return refundReports;
    }

    @Override
    public void testReport(String begin, String end)
    {
        // TODO Auto-generated method stub
        refundReportAction.censusRefundReportByAgentTest(begin, end);
        refundReportAction.censusRefundReportBySupplyTest(begin, end);
    }

    @Override
    public YcPage<RefundReportPo> queryRefundReports(Map<String, Object> searchParams,
                                                     List<ReportProperty> rpList,
                                                     String beginTime, String endTime,
                                                     int pageNumber, int pageSize, String sortType)
    {
        // TODO Auto-generated method stub
        YcPage<RefundReportPo> ycPage = refundReportDao.queryRefundReports(searchParams, rpList,
            beginTime, endTime, pageNumber, pageSize, sortType);
        return ycPage;
    }

}