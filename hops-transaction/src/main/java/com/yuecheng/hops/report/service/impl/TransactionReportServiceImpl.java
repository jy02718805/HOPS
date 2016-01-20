package com.yuecheng.hops.report.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.TransactionReportInfo;
import com.yuecheng.hops.report.entity.bo.TransactionReportBo;
import com.yuecheng.hops.report.entity.po.TransactionReportPo;
import com.yuecheng.hops.report.execution.TransactionReportAction;
import com.yuecheng.hops.report.repository.TransactionReportDao;
import com.yuecheng.hops.report.repository.jpa.TransactionReportJpaDao;
import com.yuecheng.hops.report.service.TransactionReportService;


/**
 * 交易量统计服务
 * 
 * @author Administrator
 * @version 2014年10月27日
 * @see TransactionReportServiceImpl
 * @since
 */
@Service("transactionReportService")
public class TransactionReportServiceImpl implements TransactionReportService
{
    @Autowired
    private TransactionReportAction transactionReportAction;

    @Autowired
    private TransactionReportJpaDao transactionReportJpaDao;

    @Autowired
    private TransactionReportDao transactionReportDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionReportServiceImpl.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public TransactionReportInfo saveTransactionReport(TransactionReportInfo tr)
    {
        // TODO Auto-generated method stub
        TransactionReportInfo trr = transactionReportJpaDao.save(tr);
        return trr;
    }

    @Override
    public List<TransactionReportBo> censusTransactionInfos(Date beginTime, Date endTime,
                                                            String merchantType)
    {
        List<TransactionReportBo> transactionReportPos = transactionReportDao.getTransactionReportBos(
            beginTime, endTime, merchantType);
        return transactionReportPos;
    }

    @Override
    public List<TransactionReportInfo> saveTransactionReports(List<TransactionReportInfo> transactionReports)
    {
        transactionReports = (List<TransactionReportInfo>)transactionReportJpaDao.save(transactionReports);
        return transactionReports;
    }

    @Override
    public void testTransactionReport(String beginTime, String endTime)
    {
        // TODO Auto-generated method stub
        transactionReportAction.censusAgentTransactionReportTest(beginTime, endTime);

        transactionReportAction.censusSupplyTransactionReportTest(beginTime, endTime);;
    }

    @Override
    public YcPage<TransactionReportPo> queryTransactionReports(Map<String, Object> searchParams,
                                                               List<ReportProperty> rpList,
                                                               String beginTime, String endTime,
                                                               int pageNumber, int pageSize,
                                                               String sortType)
    {
        LOGGER.debug("begin [TransactionReportsService.queryTransactionReports()]");

        YcPage<TransactionReportPo> ycPage = transactionReportDao.queryTransactionReports(
            searchParams, rpList, beginTime, endTime, pageNumber, pageSize, sortType);
        LOGGER.debug("end [TransactionReportsService.queryTransactionReports()]");
        return ycPage;
    }
}
