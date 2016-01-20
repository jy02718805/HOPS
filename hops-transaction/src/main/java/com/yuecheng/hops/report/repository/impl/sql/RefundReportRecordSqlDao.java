package com.yuecheng.hops.report.repository.impl.sql;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.RefundReportRecord;
import com.yuecheng.hops.report.repository.RefundReportRecordDao;


@Service
public class RefundReportRecordSqlDao implements RefundReportRecordDao
{
    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public RefundReportRecord queryRefundReportRecord(String merchantType, Date recordDate)
    {
        String recordDateStr = format.format(recordDate);
        String sql = "select * from refund_report_record r where 1=1 and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd') and r.merchant_type=:merchantType ";
        Query query = em.createNativeQuery(sql, RefundReportRecord.class);
        query.setParameter("recordBeginDate", recordDateStr);
        query.setParameter("merchantType", merchantType);
        List<RefundReportRecord> refundReportRecord = query.getResultList();
        if (refundReportRecord.size() > 0)
        {
            return refundReportRecord.get(0);
        }
        else
        {
            return null;
        }
    }

    public RefundReportRecord queryRefundReportRecordByStatus(String merchantType,
                                                              Date recordDate, String status1,
                                                              String status2)
    {
        String recordDateStr = format.format(recordDate);
        String sql = "select * from refund_report_record r where 1=1 and r.merchant_type=:merchantType and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd') and r.report_status in (:recordStatus1,:recordStatus2) ";
        Query query = em.createNativeQuery(sql, RefundReportRecord.class);
        query.setParameter("merchantType", merchantType);
        query.setParameter("recordBeginDate", recordDateStr);
        query.setParameter("recordStatus1", status1);
        query.setParameter("recordStatus2", status2);
        List<RefundReportRecord> refundReportRecords = query.getResultList();
        if (refundReportRecords.size() > 0)
        {
            return refundReportRecords.get(0);
        }
        else
        {
            return null;
        }
    }

}
