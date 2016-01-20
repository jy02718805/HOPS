package com.yuecheng.hops.report.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.repository.ReportPropertyDao;
import com.yuecheng.hops.report.service.ReportPropertyService;


@Service("reportPropertyService")
public class ReportPropertyServiceImpl implements ReportPropertyService
{
    @Autowired
    ReportPropertyDao reportPropertyDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportPropertyServiceImpl.class);

    @Override
    public List<ReportProperty> queryReportPropertysByTypeId(Long reportTypeId)
    {
        LOGGER.debug(" [ReportPropertyServiceImpl: queryReportPropertysByTypeId(reportTypeId:"
                     + reportTypeId + ")]");
        List<ReportProperty> reportPropertyList = reportPropertyDao.queryAll(reportTypeId);
        return reportPropertyList;
    }

    @Override
    public ReportProperty saveReportProperty(ReportProperty reportProperty)
    {
        // TODO Auto-generated method stub
        ReportProperty rp = new ReportProperty();
        LOGGER.debug(" [ReportPropertyServiceImpl: saveReportProperty(ReportProperty :"
                     + reportProperty.toString() + ")]");
        try
        {
            rp = reportPropertyDao.save(reportProperty);
        }
        catch (Exception e)
        {
            LOGGER.error("[ReportPropertyServiceImpl: saveReportProperty(ReportProperty :"
                         + reportProperty.toString() + ")]" + ExceptionUtil.getStackTraceAsString(e));
            String[] msgParams = new String[] {"saveReportProperty"};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("report000002", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
        return rp;
    }

    @Override
    public void delReportProperty(ReportProperty reportProperty)
    {
        LOGGER.debug("[ReportPropertyServiceImpl: delReportProperty(ReportProperty:"
                     + reportProperty.toString() + ")]");
        reportPropertyDao.delete(reportProperty);
    }

}
