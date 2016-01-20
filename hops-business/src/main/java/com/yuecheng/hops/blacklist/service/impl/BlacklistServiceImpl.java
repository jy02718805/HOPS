package com.yuecheng.hops.blacklist.service.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.blacklist.entity.Blacklist;
import com.yuecheng.hops.blacklist.repository.BlacklistDao;
import com.yuecheng.hops.blacklist.service.BlacklistService;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;


/**
 * 黑名单服务实现类
 * 
 * @author yao
 * @version 2015年5月28日
 * @see BlacklistServiceImpl
 * @since
 */
@Service("blacklistService")
public class BlacklistServiceImpl implements BlacklistService
{

    @Autowired
    private BlacklistDao blacklistDao;

    private static Logger logger = LoggerFactory.getLogger(BlacklistServiceImpl.class);

    @Override
    public Blacklist saveBlacklist(Blacklist blacklist)
    {
        logger.info("[BlacklistServiceImpl:saveBlacklist(" + blacklist != null ? blacklist.toString() : null
                                                                                                        + ")]");
        try
        {
            if (blacklist != null) blacklist = blacklistDao.save(blacklist);
        }
        catch (Exception e)
        {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        return blacklist;
    }

    @Override
    public void deleteBlacklist(Long id)
    {
        logger.info("[BlacklistServiceImpl:deleteBlacklist(" + id + ")]");
        blacklistDao.delete(id);
    }

    @Override
    public Blacklist findOne(Long blacklistId)
    {
        logger.info("[BlacklistServiceImpl:findOne(" + blacklistId + ")]");
        return blacklistDao.queryBlacklistById(blacklistId);
    }

    @Override
    public Blacklist findOne(String blacklistNo, String businessType)
    {
        logger.info("[BlacklistServiceImpl:findOne(" + blacklistNo + ","
                    + businessType + ")]");
        return blacklistDao.queryBlacklistByNoAndTpye(blacklistNo, businessType);
    }

    @Override
    public List<Blacklist> selectAll()
    {
        logger.info("[BlacklistServiceImpl:selectAll()]");
        return blacklistDao.selectAll();
    }

    @Override
    public YcPage<Blacklist> queryBlacklist(Map<String, Object> searchParams,
            int pageNumber, int pageSize, BSort bsort)
    {
        logger.info("[BlacklistServiceImpl:queryBlacklist(" + searchParams != null ? searchParams.toString() : null
                                                                                                               + ","
                                                                                                               + pageNumber
                                                                                                               + ","
                                                                                                               + pageSize
                                                                                                               + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? "id" : bsort.getCloumn();
        String orderDirect = bsort == null ? "DESC" : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect),
                orderCloumn);
        Page<Blacklist> page = PageUtil.queryPage(blacklistDao, filters,
                pageNumber, pageSize, sort, Blacklist.class);
        YcPage<Blacklist> ycPage = new YcPage<Blacklist>();
        ycPage.setList(page.getContent());
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setCountTotal((int)page.getTotalElements());
        return ycPage;
    }

}
