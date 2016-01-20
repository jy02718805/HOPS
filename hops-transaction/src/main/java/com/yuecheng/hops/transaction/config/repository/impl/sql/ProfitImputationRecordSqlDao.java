package com.yuecheng.hops.transaction.config.repository.impl.sql;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationRecord;
import com.yuecheng.hops.transaction.config.repository.ProfitImputationRecordDao;


@Service
public class ProfitImputationRecordSqlDao implements ProfitImputationRecordDao
{
    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public ProfitImputationRecord queryProfitImputationRecord(Date imputationDate)
    {
        String imputationDateStr = format.format(imputationDate);
        String sql = "select * from profit_imputation_record r where 1=1 and r.record_begin_date=to_date(:recordBeginDate,'yyyy-mm-dd')";
        Query query = em.createNativeQuery(sql, ProfitImputationRecord.class);
        query.setParameter("recordBeginDate", imputationDateStr);
        List<ProfitImputationRecord> profitImputationRecords = query.getResultList();
        if (profitImputationRecords.size() > 0)
        {
            return profitImputationRecords.get(0);
        }
        else
        {
            return null;
        }
    }

    public ProfitImputationRecord queryProfitImputationRecordByStatus(Date imputationDate)
    {
        String imputationDateStr = format.format(imputationDate);
        String sql = "select * from profit_imputation_record r where 1=1 and r.record_begin_date=to_date(:recordBeginDate,'yyyy-mm-dd') and r.record_status in (:recordStatus1,:recordStatus2) ";
        Query query = em.createNativeQuery(sql, ProfitImputationRecord.class);
        query.setParameter("recordBeginDate", imputationDateStr);
        query.setParameter("recordStatus1", Constant.RecordStatus.INITIALIZATION);
        query.setParameter("recordStatus2", Constant.RecordStatus.FAILURE);
        List<ProfitImputationRecord> profitImputationRecords = query.getResultList();
        if (profitImputationRecords.size() > 0)
        {
            return profitImputationRecords.get(0);
        }
        else
        {
            return null;
        }
    }

}
