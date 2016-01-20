package com.yuecheng.hops.report.repository.impl.sql;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.report.entity.AccountReportRecord;
import com.yuecheng.hops.report.repository.AccountReportRecordDao;

@Service
public class AccountReportRecordSqlDao implements AccountReportRecordDao
{
    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public AccountReportRecord queryAccountReportRecord(Date recordDate)
    {
        String recordDateStr = format.format(recordDate);
        String sql = "select * from account_report_record r where 1=1 and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd')";
        Query query = em.createNativeQuery(sql, AccountReportRecord.class);
        query.setParameter("recordBeginDate", recordDateStr);
        List<AccountReportRecord> accountReportRecords = query.getResultList();
        if (accountReportRecords.size() > 0)
        {
            return accountReportRecords.get(0);
        }
        else
        {
            return null;
        }
    }

    public AccountReportRecord queryAccountReportRecordByStatus(Date recordDate)
    {
        String recordDateDateStr = format.format(recordDate);
        String sql = "select * from account_report_record r where 1=1 and r.begin_date=to_date(:recordBeginDate,'yyyy-mm-dd') and r.report_status in (:recordStatus1,:recordStatus2) ";
        Query query = em.createNativeQuery(sql, AccountReportRecord.class);
        query.setParameter("recordBeginDate", recordDateDateStr);
        query.setParameter("recordStatus1", Constant.RecordStatus.INITIALIZATION);
        query.setParameter("recordStatus2", Constant.RecordStatus.FAILURE);
        List<AccountReportRecord> accountReportRecords = query.getResultList();
        if (accountReportRecords.size() > 0)
        {
            return accountReportRecords.get(0);
        }
        else
        {
            return null;
        }
    }

}
