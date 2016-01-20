package com.yuecheng.hops.report.repository.impl.sql;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.report.entity.TransactionReportRecord;
import com.yuecheng.hops.report.repository.TransactionReportRecordDao;


@Service
public class TransactionReportRecordSqlDao implements TransactionReportRecordDao
{
    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public TransactionReportRecord queryTransactionReportRecord(String merchantType,String reportType,
                                                                Date recordDate)
    {
        String recordDateStr = format.format(recordDate);
        String sql = "select * from transaction_report_record r where 1=1 and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd') and r.merchant_type=:merchantType and r.report_type=:reportType ";
        Query query = em.createNativeQuery(sql, TransactionReportRecord.class);
        query.setParameter("recordBeginDate", recordDateStr);
        query.setParameter("merchantType", merchantType);
        query.setParameter("reportType", reportType);
        List<TransactionReportRecord> transactionReportRecords = query.getResultList();
        if (transactionReportRecords.size() > 0)
        {
            return transactionReportRecords.get(0);
        }
        else
        {
            return null;
        }
    }

    public TransactionReportRecord queryTransactionReportRecordByStatus(String merchantType,String reportType,
                                                                        Date recordDate)
    {
        String recordDateStr = format.format(recordDate);
        String sql = "select * from transaction_report_record r where 1=1 and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd')  and r.merchant_type=:merchantType and r.report_type=:reportType and r.report_status in (:recordStatus1,:recordStatus2) ";
        Query query = em.createNativeQuery(sql, TransactionReportRecord.class);
        query.setParameter("recordBeginDate", recordDateStr);
        query.setParameter("merchantType", merchantType);
        query.setParameter("reportType", reportType);
        query.setParameter("recordStatus1", Constant.RecordStatus.INITIALIZATION);
        query.setParameter("recordStatus2", Constant.RecordStatus.FAILURE);
        List<TransactionReportRecord> transactionReportRecords = query.getResultList();
        if (transactionReportRecords.size() > 0)
        {
            return transactionReportRecords.get(0);
        }
        else
        {
            return null;
        }
    }

}
