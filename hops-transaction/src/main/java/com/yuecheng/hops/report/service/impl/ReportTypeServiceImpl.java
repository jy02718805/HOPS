package com.yuecheng.hops.report.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.report.entity.ReportMetadata;
import com.yuecheng.hops.report.entity.ReportTerm;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.repository.ReportMetadataDao;
import com.yuecheng.hops.report.repository.ReportTermDao;
import com.yuecheng.hops.report.repository.ReportTypeDao;
import com.yuecheng.hops.report.service.ReportTypeService;


@Service("reportTypeService")
public class ReportTypeServiceImpl implements ReportTypeService
{
    @Autowired
    ReportTypeDao reportTypeDao;

    @Autowired
    ReportMetadataDao reportMetadataDao;

    @Autowired
    ReportTermDao reportTermDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportTypeServiceImpl.class);

    @Override
    public YcPage<ReportType> queryReportTypes(Map<String, Object> searchParams, int pageNumber,
                                               int pageSize, String sortType)
    {
        LOGGER.debug(" 开始 [进入查询报表类型方法][ReportTypeServiceImpl:queryReportTypes()");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Page<ReportType> page = PageUtil.queryPage(reportTypeDao, filters, pageNumber, pageSize,
            new Sort(Direction.DESC, sortType), ReportType.class);
        YcPage<ReportType> ycPage = new YcPage<ReportType>();
        ycPage.setCountTotal((int)page.getTotalElements());
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setList(page.getContent());

        LOGGER.debug(" 结束[查询报表类型方法][ReportTypeServiceImpl:queryReportTypes()");
        return ycPage;
    }

    @Override
    public ReportType saveReportType(ReportType reportType)
    {
        // TODO Auto-generated method stub
        ReportType rt = new ReportType();
        LOGGER.debug("[ReportTypeServiceImpl:saveReportType(ReportType :" + reportType.toString()
                     + ")]");
        try
        {
            rt = reportTypeDao.save(reportType);
        }
        catch (Exception e)
        {
            LOGGER.error("[ReportTypeServiceImpl:saveReportType(ReportType :"
                         + reportType.toString() + ")]" + ExceptionUtil.getStackTraceAsString(e));
            String[] msgParams = new String[] {"saveReportType"};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("report000001", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
        return rt;
    }

    @Override
    public ReportType getReportType(Long reportTypeId)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[ReportTypeServiceImpl:getReportType()");
        ReportType rt = reportTypeDao.findOne(reportTypeId);
        return rt;
    }

    @Override
    public List<ReportType> getReportTypeByType(String reportMetadataType)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[ReportTypeServiceImpl:getReportTypeByType()");
        List<ReportType> rtList = reportTypeDao.getReportTypeByType(reportMetadataType);
        return rtList;
    }

    @Override
    public List<ReportMetadata> getmetadataByType(String type)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[ReportTypeServiceImpl:getmetadataByType(type:" + type + ")");
        List<ReportMetadata> rmList = reportMetadataDao.getmetadataByType(type);
        return rmList;
    }

    @Override
    public List<ReportMetadata> getAllmetadataByType(String type)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[ReportTypeServiceImpl: getAllmetadataByType()");
        List<ReportMetadata> rmList = reportMetadataDao.getAllmetadataByType(type);
        return rmList;
    }

    @Override
    public ReportTerm saveReportTerms(ReportTerm rt)
    {
        LOGGER.debug("[ReportTypeServiceImpl: saveReportTerms(" + rt.toString() + ")]");
        ReportTerm reportTerm = null;
        // TODO Auto-generated method stub
        try
        {
            reportTerm = reportTermDao.save(rt);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LOGGER.error("[ReportTypeServiceImpl: saveReportTerms(" + rt.toString() + ")]"
                         + ExceptionUtil.getStackTraceAsString(e));

            String[] msgParams = new String[] {"saveReportTerms"};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("report000003", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }

        return reportTerm;
    }

    @Override
    public List<ReportTerm> getReportTermsByReportTypeId(Long reportType)
    {
        // TODO Auto-generated method stub getReportTermsByReportTypeId
        LOGGER.debug("[ReportTypeServiceImpl: getReportTermsByReportTypeId()");
        List<ReportTerm> rtList = new ArrayList<ReportTerm>();
        try
        {
            rtList = reportTermDao.getReportTermsByReportTypeId(reportType);
            for (ReportTerm trm : rtList)
            {
                ReportMetadata rm = reportMetadataDao.findOne(trm.getReportMetadataId());
                trm.setReportMetadata(rm);
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LOGGER.error("[ReportTypeServiceImpl: getReportTermsByReportTypeId()" + ExceptionUtil.getStackTraceAsString(e));
        }

        return rtList;
    }

    @Override
    public ReportMetadata getmetadataById(Long id)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[ReportTypeServiceImpl: getmetadataById()");
        ReportMetadata rm = reportMetadataDao.findOne(id);
        return rm;
    }

    @Override
    public ReportTerm getReportTermsByMetadataId(Long reportMetadata, Long reportTypeId)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[ReportTypeServiceImpl: getReportTermsByMetadataId()");
        ReportTerm rt = reportTermDao.getReportTermsByMetadataId(reportMetadata, reportTypeId);
        return rt;
    }

    @Override
    public boolean reportNameIsRepeat(String reportFileName, String reportMetadataType)
    {
        // TODO Auto-generated method stub
        List<ReportType> reportTypes = reportTypeDao.getReportTypeByReportFileName(reportFileName,
            reportMetadataType);
        return !Collections3.isEmpty(reportTypes);
    }
}
