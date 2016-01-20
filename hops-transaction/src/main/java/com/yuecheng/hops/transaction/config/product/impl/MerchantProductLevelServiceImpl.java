package com.yuecheng.hops.transaction.config.product.impl;


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

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.config.entify.product.MerchantProductLevel;
import com.yuecheng.hops.transaction.config.product.MerchantProductLevelService;
import com.yuecheng.hops.transaction.config.repository.MerchantProductLevelDao;


/**
 * 商户分组设置服务层
 * 
 * @author Jinger 2014-03-26
 */
@Service("merchantProductLevelService")
public class MerchantProductLevelServiceImpl implements MerchantProductLevelService
{
    private static Logger           logger = LoggerFactory.getLogger(MerchantProductLevelServiceImpl.class);

    @Autowired
    private MerchantProductLevelDao merchantProductLevelDao;

    @Override
    public List<MerchantProductLevel> getAllMerchantProductLevel()
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:getAllMerchantProductLevel()]");
        }
        List<MerchantProductLevel> merchantProductLevelAll = (List<MerchantProductLevel>)merchantProductLevelDao.findAll();
        return merchantProductLevelAll;
    }

    @Override
    @Transactional
    public MerchantProductLevel saveMerchantProductLevel(MerchantProductLevel merchantProductLevel)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:saveMerchantProductLevel("
                        + merchantProductLevel != null ? merchantProductLevel.toString() : null
                                                                                           + ")]");
        }
        if (merchantProductLevel != null)
        {
            merchantProductLevel = merchantProductLevelDao.save(merchantProductLevel);
        }
        return merchantProductLevel;
    }

    @Override
    @Transactional
    public void deleteMerchantProductLevel(Long merchantProductLevelId)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:deleteMerchantProductLevel("
                        + merchantProductLevelId + ")]");
        }
        if (merchantProductLevelId != null)
        {
            merchantProductLevelDao.delete(merchantProductLevelId);
        }
    }

    @Override
    @Transactional
    public MerchantProductLevel updateMerchantProductLevel(MerchantProductLevel merchantProductLevel)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:updateMerchantProductLevel("
                        + merchantProductLevel != null ? merchantProductLevel.toString() : null
                                                                                           + ")]");
        }
        if (merchantProductLevel != null)
        {
            merchantProductLevel = merchantProductLevelDao.save(merchantProductLevel);
        }
        return merchantProductLevel;
    }

    @Override
    public MerchantProductLevel getMerchantProductLevelById(Long merchantProductLevelId)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:getMerchantProductLevelById("
                        + merchantProductLevelId + ")]");
        }
        if (merchantProductLevelId != null)
        {
            return merchantProductLevelDao.findOne(merchantProductLevelId);
        }
        return null;
    }

    @Override
    public YcPage<MerchantProductLevel> queryMerchantProductLevel(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize,
                                                                  BSort bsort)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:queryMerchantProductLevel("
                        + searchParams != null ? searchParams.toString() : null + "," + pageNumber
                                                                           + "," + pageSize + ")]");
        }
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? "id" : bsort.getCloumn();
        String orderDirect = bsort == null ? "DESC" : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        Page<MerchantProductLevel> page = PageUtil.queryPage(merchantProductLevelDao, filters,
            pageNumber, pageSize, sort, MerchantProductLevel.class);
        YcPage<MerchantProductLevel> ycPage = new YcPage<MerchantProductLevel>();
        ycPage.setList(page.getContent());
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setCountTotal((int)page.getTotalElements());
        return ycPage;
    }

    @Override
    public MerchantProductLevel getMerchantProductLevelByLevel(Long merchantProductLevel)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:getMerchantProductLevelByLevel("
                        + merchantProductLevel + ")]");
        }
        return merchantProductLevelDao.queryMerchantProductLevelList(merchantProductLevel);
    }

    @Override
    public MerchantProductLevel findMerchantProductLevelByRadom(int radomNum)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantProductLevelServiceImpl:findMerchantProductLevelByRadom("
                        + radomNum + ")]");
        }
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantProductLevel.ORDER_PERCENTAGE_LOW, new SearchFilter(
            EntityConstant.MerchantProductLevel.ORDER_PERCENTAGE_LOW, Operator.LTE, radomNum));//
        filters.put(EntityConstant.MerchantProductLevel.ORDER_PERCENTAGE_HIGH, new SearchFilter(
            EntityConstant.MerchantProductLevel.ORDER_PERCENTAGE_HIGH, Operator.GT, radomNum));//
        Specification<MerchantProductLevel> spec_MerchantProductLevel = DynamicSpecifications.bySearchFilter(
            filters.values(), MerchantProductLevel.class);
        MerchantProductLevel merchantProductLevel = merchantProductLevelDao.findOne(spec_MerchantProductLevel);
        return merchantProductLevel;
    }
}
