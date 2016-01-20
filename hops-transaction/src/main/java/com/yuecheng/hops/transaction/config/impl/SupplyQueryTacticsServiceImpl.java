package com.yuecheng.hops.transaction.config.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.config.SupplyQueryTacticsService;
import com.yuecheng.hops.transaction.config.entify.query.SupplyQueryTactics;
import com.yuecheng.hops.transaction.config.repository.SupplyQueryTacticsDao;


@Service("supplyQueryTacticsService")
public class SupplyQueryTacticsServiceImpl implements SupplyQueryTacticsService
{
    @Autowired
    SupplyQueryTacticsDao       supplyQueryTacticsDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyQueryTacticsServiceImpl.class);

    SimpleDateFormat            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public YcPage<SupplyQueryTactics> querySupplyQueryTactics(Map<String, Object> searchParams,
                                                              int pageNumber, int pageSize,
                                                              String sortType)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsService:querySupplyQueryTactics()] ");
        }
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Page<SupplyQueryTactics> page = PageUtil.queryPage(supplyQueryTacticsDao, filters,
            pageNumber, pageSize, new Sort(Direction.DESC, sortType), SupplyQueryTactics.class);
        Long totalElement = page.getTotalElements();
        return new YcPage<SupplyQueryTactics>(page.getContent(), page.getTotalPages(),
            totalElement.intValue());
    }

    // 保存
    @Override
    @Transactional
    public SupplyQueryTactics saveSupplyQueryTactics(SupplyQueryTactics supplyqt)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsService:saveSupplyQueryTactics()] ");
        }
        return supplyQueryTacticsDao.save(supplyqt);
    }

    @Override
    @Transactional
    public void delSupplyQueryTactics(SupplyQueryTactics supplyqt)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsService:delSupplyQueryTactics()] ");
        }
        supplyQueryTacticsDao.delete(supplyqt);
    }

    @Override
    public SupplyQueryTactics getSupplyQueryTactics(Long supplyqtId)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsService:showSupplyQueryTactics()] ");
        }
        SupplyQueryTactics supplyqtShow = supplyQueryTacticsDao.findOne(supplyqtId);
        return supplyqtShow;
    }

    @Override
    public List<SupplyQueryTactics> querySupplyQueryTactics(SupplyQueryTactics supplyqt)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsService:querySupplyQueryTactics] ");
        }
        HashMap<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put("merchantId",
            new SearchFilter("merchantId", Operator.EQ, supplyqt.getMerchantId()));
        Specification<SupplyQueryTactics> spec_supplyqt = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyQueryTactics.class);
        List<SupplyQueryTactics> list = supplyQueryTacticsDao.findAll(spec_supplyqt);
        return list;
    }

    @Override
    public SupplyQueryTactics querySupplyQueryTacticsByMerchantId(Long merchantId)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("[" + format.format(new Date())
                        + "][SupplyQueryTacticsService:querySupplyQueryTacticsByMerchantId] ");
        }
        HashMap<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put("merchantId", new SearchFilter("merchantId", Operator.EQ, merchantId));
        Specification<SupplyQueryTactics> spec_supplyqt = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyQueryTactics.class);
        SupplyQueryTactics supplyQueryTactics = supplyQueryTacticsDao.findOne(spec_supplyqt);
        return supplyQueryTactics;
    }
}
