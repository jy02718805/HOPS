package com.yuecheng.hops.transaction.config.repository;


import java.util.Date;

import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationRecord;


public interface ProfitImputationRecordDao
{
    ProfitImputationRecord queryProfitImputationRecord(Date imputationDate);
    
    ProfitImputationRecord queryProfitImputationRecordByStatus(Date imputationDate);
}
