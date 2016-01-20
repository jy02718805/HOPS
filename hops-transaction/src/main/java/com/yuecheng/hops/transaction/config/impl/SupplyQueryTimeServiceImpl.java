package com.yuecheng.hops.transaction.config.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.transaction.config.SupplyQueryTimeService;
import com.yuecheng.hops.transaction.config.entify.query.SupplyQueryTactics;
import com.yuecheng.hops.transaction.config.repository.SupplyQueryTacticsDao;


@Service("supplyQueryTimeService")
public class SupplyQueryTimeServiceImpl implements SupplyQueryTimeService
{
    @Autowired
    private SupplyQueryTacticsDao supplyQueryTacticsDao;

    @Override
    public Date getQueryTime(Long merchantId, Date pastTime, Date now)
    {
        long date1 = pastTime.getTime();
        long date2 = now.getTime();
        long millisecond = date2 - date1;
        BigDecimal Second = new BigDecimal(millisecond).divide(new BigDecimal(1000));
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyQueryTactics.MERCHANT_ID, new SearchFilter(
            EntityConstant.SupplyQueryTactics.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.SupplyQueryTactics.TIME_DIFFERENCE_LOW, new SearchFilter(
            EntityConstant.SupplyQueryTactics.TIME_DIFFERENCE_LOW, Operator.LTE, Second));
        filters.put(EntityConstant.SupplyQueryTactics.TIME_DIFFERENCE_HIGH, new SearchFilter(
            EntityConstant.SupplyQueryTactics.TIME_DIFFERENCE_HIGH, Operator.GTE, Second));
        Specification<SupplyQueryTactics> spec_UpQueryTactics = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyQueryTactics.class);
        // 下单返回
        SupplyQueryTactics uqt = supplyQueryTacticsDao.findOne(spec_UpQueryTactics);
        Date resultDate = DateUtil.addTime(uqt.getIntervalUnit(), uqt.getIntervalTime().intValue());
        return resultDate;
    }
}
