package com.yuecheng.hops.transaction.config.product.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.product.TmallTSC;
import com.yuecheng.hops.transaction.config.product.TmallTSCService;
import com.yuecheng.hops.transaction.config.repository.TmallTSCDao;


/**
 * 天猫产品服务层
 * 
 * @author Jinger 2014-03-11
 */
@Service("tmallTSCService")
public class TmallTSCServiceImpl implements TmallTSCService
{

    @Autowired
    private TmallTSCDao   tmallTSCDao;

    private static Logger logger = LoggerFactory.getLogger(TmallTSCServiceImpl.class);

    @Override
    public List<TmallTSC> getAll()
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[TmallTSCServiceImpl:getAll()]");
        }
        return tmallTSCDao.selectAll();
    }

    @Override
    public TmallTSC findOne(String tsc)
    {
        logger.info("[TmallTSCServiceImpl:findOne(" + tsc + ")]");
        TmallTSC tmallTSC = tmallTSCDao.findOne(tsc);
//        if(BeanUtils.isNotNull(tmallTSC)){
//            return tmallTSC;
//        }else{
//            throw new ApplicationException(Constant.ErrorCode.PRODUCT_DISABLED);
//        }
        return tmallTSC;
    }

}
