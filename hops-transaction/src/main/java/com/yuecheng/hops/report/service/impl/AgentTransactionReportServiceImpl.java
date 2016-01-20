package com.yuecheng.hops.report.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;
import com.yuecheng.hops.report.entity.AgentTransactionReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo;
import com.yuecheng.hops.report.entity.po.AgentTransactionReportPo;
import com.yuecheng.hops.report.execution.MerchantTransactionReportAcion;
import com.yuecheng.hops.report.repository.AgentTransactionReportDao;
import com.yuecheng.hops.report.repository.jpa.AgentTransactionReportJpaDao;
import com.yuecheng.hops.report.service.AgentTransactionReportService;


/**
 * 代理商交易量统计服务
 * 
 * @author Administrator
 * @version 2014年10月27日
 * @see AgentTransactionReportServiceImpl
 * @since
 */
@Service("agentTransactionReportService")
public class AgentTransactionReportServiceImpl implements AgentTransactionReportService
{

    @Autowired
    private AgentTransactionReportDao agentTransactionReportDao;

    @Autowired
    private AgentTransactionReportJpaDao agentTransactionReportJpaDao;

    @Autowired
    private MerchantTransactionReportAcion merchantTransactionReportAcion;

    @Autowired
    private RebateProductQueryManager rebateProductQueryManager;


    private static final Logger LOGGER = LoggerFactory.getLogger(AgentTransactionReportServiceImpl.class);

    @Override
    public BigDecimal getAgentReportTransaction(Long merchantId, Long rebateProductId,
                                                Date beginDate, Date endDate)
    {
        LOGGER.debug("[进入统计返佣区间统计交易量][AgentTransactionReportsServiceImpl.getAgentReports()]");
        BigDecimal parValueTotal = new BigDecimal(0);
        try
        {
            parValueTotal = agentTransactionReportDao.getSumAgentTransaction(merchantId,
                rebateProductId, beginDate, endDate);
        }
        catch (Exception e)
        {
            LOGGER.error("[AgentTransactionReportService: getAgentReportTransaction(merchantId:"
                         + merchantId + ", rebateProductId:" + rebateProductId
                         + ",  beginDate,endDate][报错:" + ExceptionUtil.getStackTraceAsString(e) + "]");
        }

        return parValueTotal;
    }

    @Override
    public List<AgentTransactionReportInfo> getAgentTransactionReports(Long merchantId,
                                                                       Long productId,
                                                                       Date beginDate, Date endDate)
    {
        LOGGER.debug("[AgentTransactionReportsServiceImpl.getAgentTransactionReports(merchantId:"
                     + merchantId + ",productId:" + productId + ",beginDate:" + beginDate
                     + ",endDate:" + endDate + ")]");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentTransactionReport.BEGIN_TIME, new SearchFilter(
            EntityConstant.AgentTransactionReport.BEGIN_TIME, Operator.GTE, beginDate));
        filters.put(EntityConstant.AgentTransactionReport.BEGIN_TIME, new SearchFilter(
            EntityConstant.AgentTransactionReport.END_TIME, Operator.LTE, endDate));
        filters.put(EntityConstant.AgentTransactionReport.MERCHANT_ID, new SearchFilter(
            EntityConstant.AgentTransactionReport.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.AgentTransactionReport.PRODUCT_ID, new SearchFilter(
            EntityConstant.AgentTransactionReport.PRODUCT_ID, Operator.EQ, productId));
        Specification<AgentTransactionReportInfo> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentTransactionReportInfo.class);
        List<AgentTransactionReportInfo> agentReports = agentTransactionReportJpaDao.findAll(spec);
        return agentReports;
    }

    @Override
    public List<MerchantTransactionReportBo> censusMerchantTransactionInfo(Date beginTime,
                                                                           Date endTime)
    {
        // TODO Auto-generated method stub
        List<MerchantTransactionReportBo> matList = agentTransactionReportDao.getMerchantTransactionInfos(
            beginTime, endTime);
        return matList;
    }

    @Override
    public AgentTransactionReportInfo saveAgentTransactionReport(AgentTransactionReportInfo agentTransactionReport)
    {
        // TODO Auto-generated method stub
        agentTransactionReport = agentTransactionReportJpaDao.save(agentTransactionReport);
        return agentTransactionReport;
    }

    @Override
    public List<AgentTransactionReportInfo> saveAgentTransactionReports(List<AgentTransactionReportInfo> agentTransactionReports)
    {
        // TODO Auto-generated method stub
        agentTransactionReports = (List<AgentTransactionReportInfo>)agentTransactionReportJpaDao.save(agentTransactionReports);
        return agentTransactionReports;
    }

    @Override
    public void testReport(String begin, String end)
    {
        // TODO Auto-generated method stub
        merchantTransactionReportAcion.censusAgentTransactionReportTest(begin, end);
    }

    @Override
    public YcPage<AgentTransactionReportPo> queryAgentTransactionReports(Map<String, Object> searchParams,
                                                                         List<ReportProperty> rpList,
                                                                         String beginTime,
                                                                         String endTime,
                                                                         int pageNumber,
                                                                         int pageSize,
                                                                         String sortType)
    {
        // TODO Auto-generated method stub
        YcPage<AgentTransactionReportPo> ycPage = agentTransactionReportDao.queryAgentTransactionReports(
            searchParams, rpList, beginTime, endTime, pageNumber, pageSize, sortType);
        return ycPage;
    }
}
