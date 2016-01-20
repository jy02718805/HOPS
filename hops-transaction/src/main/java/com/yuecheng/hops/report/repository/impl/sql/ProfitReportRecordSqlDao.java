package com.yuecheng.hops.report.repository.impl.sql;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.report.entity.ProfitReportRecord;
import com.yuecheng.hops.report.repository.ProfitReportRecordDao;


@Service
public class ProfitReportRecordSqlDao implements ProfitReportRecordDao
{
    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public ProfitReportRecord queryProfitReportRecord(String merchantType, Date recordDate)
    {
        String recordDateStr = format.format(recordDate);
        String sql = "select * from profit_report_record r where 1=1 and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd') and r.merchant_type=:merchantType ";
        Query query = em.createNativeQuery(sql, ProfitReportRecord.class);
        query.setParameter("recordBeginDate", recordDateStr);
        query.setParameter("merchantType", merchantType);
        List<ProfitReportRecord> profitReportRecord = query.getResultList();
        if (profitReportRecord.size() > 0)
        {
            return profitReportRecord.get(0);
        }
        else
        {
            return null;
        }
    }

    public ProfitReportRecord queryProfitReportRecordByStatus(String merchantType,
                                                              Date recordDate)
    {
        String recordDateStr = format.format(recordDate);
        String sql = "select * from profit_report_record r where 1=1 and r.merchant_type=:merchantType and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd') and r.report_status in (:recordStatus1,:recordStatus2) ";
        Query query = em.createNativeQuery(sql, ProfitReportRecord.class);
        query.setParameter("merchantType", merchantType);
        query.setParameter("recordBeginDate", recordDateStr);
        query.setParameter("recordStatus1", Constant.RecordStatus.INITIALIZATION);
        query.setParameter("recordStatus2", Constant.RecordStatus.FAILURE);
        List<ProfitReportRecord> profitReportRecords = query.getResultList();
        if (profitReportRecords.size() > 0)
        {
            return profitReportRecords.get(0);
        }
        else
        {
            return null;
        }
    }

}
