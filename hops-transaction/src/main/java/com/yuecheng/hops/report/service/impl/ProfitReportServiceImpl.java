package com.yuecheng.hops.report.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ProfitReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.ProfitReportBo;
import com.yuecheng.hops.report.entity.po.ProfitReportPo;
import com.yuecheng.hops.report.execution.ProfitReportAction;
import com.yuecheng.hops.report.repository.ProfitReportDao;
import com.yuecheng.hops.report.repository.jpa.ProfitReportJpaDao;
import com.yuecheng.hops.report.service.ProfitReportService;


/**
 * 利润报表
 * 
 * @author Administrator
 * @version 2014年10月24日
 * @see ProfitReportServiceImpl
 * @since
 */
@Service("profitReportService")
public class ProfitReportServiceImpl implements ProfitReportService
{
    @Autowired
    private ProfitReportJpaDao profitReportJpaDao;

    @Autowired
    private ProfitReportDao profitReportDao;
    
    @Autowired
    private ProfitReportAction profitReportAction;
    

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitReportServiceImpl.class);

    @Override
    public ProfitReportInfo saveProfitReport(ProfitReportInfo profitReport)
    {
        try
        {
            profitReport = profitReportJpaDao.save(profitReport);
        }
        catch (Exception e)
        {
            LOGGER.error("[保存利润报表报错][ProfitReportsServiceImpl.saveProfitReport(profitReport:"
                         + profitReport.toString() + ")]");
            return null;
        }
        return profitReport;
    }

    @Override
    public List<ProfitReportBo> censusProfitInfos(Date beginTime, Date endTime,String merchatType)
    {
        List<ProfitReportBo> profitReportPos = profitReportDao.getProfitReportBos(beginTime,
            endTime,merchatType);
        return profitReportPos;
    }

    @Override
    public List<ProfitReportInfo> saveProfitReports(List<ProfitReportInfo> profitReports)
    {
        profitReports = (List<ProfitReportInfo>)profitReportJpaDao.save(profitReports);
        return profitReports;
    }

    @Override
    public void testReport(String begin,String end)
    {
        // TODO Auto-generated method stub
        profitReportAction.censusProfitReportByAgentTest(begin, end);
    }

    @Override
    public YcPage<ProfitReportPo> queryProfitReports(Map<String, Object> searchParams,
                                                     List<ReportProperty> rpList,
                                                     String beginTime, String endTime,
                                                     int pageNumber, int pageSize, String sortType)
    {
        // TODO Auto-generated method stub
        @SuppressWarnings("unused")
        YcPage<ProfitReportPo> ycPage=profitReportDao.queryProfitReports(searchParams, rpList,beginTime,
            endTime, pageNumber, pageSize, sortType);
        return ycPage;
    }

    
    
}