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
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.SupplyTransactionReportInfo;
import com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo;
import com.yuecheng.hops.report.entity.po.SupplyTransactionReportPo;
import com.yuecheng.hops.report.execution.MerchantTransactionReportAcion;
import com.yuecheng.hops.report.repository.SupplyTransactionReportDao;
import com.yuecheng.hops.report.repository.jpa.SupplyTransactionReportJpaDao;
import com.yuecheng.hops.report.service.SupplyTransactionReportService;


@Service("supplyTransactionReportService")
public class SupplyTransactionReportServiceImpl implements SupplyTransactionReportService
{
    @Autowired
    private SupplyTransactionReportJpaDao supplyTransactionReportJpaDao;

    @Autowired
    private SupplyTransactionReportDao supplyTransactionReportDao;

    @Autowired
    private RebateProductQueryManager rebateProductQueryManager;

    @Autowired
    private MerchantTransactionReportAcion merchantTransactionReportAcion;

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyTransactionReportServiceImpl.class);

    @Override
    public BigDecimal getSupplyReportTransaction(Long merchantId, Long rebateProductId,
                                                 Date beginDate, Date endDate)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[AgentTransactionReportsServiceImpl.getSupplyReportTransaction(merchantId:"
                     + merchantId + ",productId:" + rebateProductId + ")]");
        LOGGER.debug("[进入统计返佣区间统计交易量][AgentTransactionReportsServiceImpl.getAgentReports()]");
        BigDecimal parValueTotal = new BigDecimal(0);
        try
        {
            parValueTotal = supplyTransactionReportDao.getSumSupplyTransaction(merchantId,
                rebateProductId, beginDate, endDate);
        }
        catch (Exception e)
        {
            LOGGER.error("[AgentTransactionReportService: getSupplyReportTransaction(merchantId:"
                         + merchantId + ", rebateProductId:" + rebateProductId
                         + ",  beginDate,endDate][报错:" + ExceptionUtil.getStackTraceAsString(e) + "]");
        }

        return parValueTotal;
    }

    public List<SupplyTransactionReportInfo> getSupplyTransactionReports(Long merchantId,
                                                                         Long productId,
                                                                         Date beginDate,
                                                                         Date endDate)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyTransactionReport.BEGIN_TIME, new SearchFilter(
            EntityConstant.SupplyTransactionReport.BEGIN_TIME, Operator.GTE, beginDate));
        filters.put(EntityConstant.SupplyTransactionReport.BEGIN_TIME, new SearchFilter(
            EntityConstant.SupplyTransactionReport.END_TIME, Operator.LTE, endDate));
        filters.put(EntityConstant.SupplyTransactionReport.MERCHANT_ID, new SearchFilter(
            EntityConstant.SupplyTransactionReport.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.SupplyTransactionReport.PRODUCT_ID, new SearchFilter(
            EntityConstant.SupplyTransactionReport.PRODUCT_ID, Operator.EQ, productId));
        Specification<SupplyTransactionReportInfo> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyTransactionReportInfo.class);
        List<SupplyTransactionReportInfo> suppplyReports = supplyTransactionReportJpaDao.findAll(spec);
        return suppplyReports;
    }

    @Override
    public List<MerchantTransactionReportBo> censusMerchantTransactionInfo(Date beginTime,
                                                                           Date endTime)
    {
        // TODO Auto-generated method stub
        List<MerchantTransactionReportBo> mtaList = supplyTransactionReportDao.getMerchantTransactionInfo(
            beginTime, endTime);
        return mtaList;
    }

    @Override
    public SupplyTransactionReportInfo saveSupplyTransactionReport(SupplyTransactionReportInfo supplyTransactionReport)
    {
        // TODO Auto-generated method stub
        supplyTransactionReport = supplyTransactionReportJpaDao.save(supplyTransactionReport);
        return supplyTransactionReport;
    }

    @Override
    public List<SupplyTransactionReportInfo> saveSupplyTransactionReports(List<SupplyTransactionReportInfo> supplyTransactionReports)
    {
        // TODO Auto-generated method stub
        supplyTransactionReports = (List<SupplyTransactionReportInfo>)supplyTransactionReportJpaDao.save(supplyTransactionReports);
        return supplyTransactionReports;
    }

    @Override
    public void testReport(String begin, String end)
    {
        // TODO Auto-generated method stub
        merchantTransactionReportAcion.censusSupplyTransactionReportTest(begin, end);
    }

    @Override
    public YcPage<SupplyTransactionReportPo> querySupplyTransactionReports(Map<String, Object> searchParams,
                                                                           List<ReportProperty> rpList,
                                                                           String beginTime,
                                                                           String endTime,
                                                                           int pageNumber,
                                                                           int pageSize,
                                                                           String sortType)
    {
        YcPage<SupplyTransactionReportPo> ycPage = supplyTransactionReportDao.querySupplyTransactionReports(
            searchParams, rpList, beginTime, endTime, pageNumber, pageSize, sortType);
        return ycPage;
    }
}
