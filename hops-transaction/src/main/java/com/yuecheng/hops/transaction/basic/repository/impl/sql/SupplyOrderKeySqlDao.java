package com.yuecheng.hops.transaction.basic.repository.impl.sql;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.basic.repository.SupplyOrderKeyDao;


@Service
public class SupplyOrderKeySqlDao implements SupplyOrderKeyDao
{
    private static Logger logger = LoggerFactory.getLogger(SupplyOrderKeySqlDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void saveKey(Long merchantId, Long deliveryId) throws Exception
    {
        try
        {
            String sql = "insert into supply_order_key (MERCHANT_ID, DELIVERY_ID) values (?, ?)";
            em.createNativeQuery(sql).setParameter(1, merchantId).setParameter(2, deliveryId).executeUpdate();
        }
        catch(PersistenceException e)
        {
            logger.error("saveKey exception info[" + ExceptionUtil.throwException(e) + "]");
            throw new ApplicationException(Constant.ErrorCode.IS_EXIST);
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void deleteKey(Long merchantId, Long deliveryId)
    {
        try
        {
            String sql = "delete supply_order_key where merchant_id=? and delivery_id =?";
            em.createNativeQuery(sql).setParameter(1, merchantId).setParameter(2, deliveryId).executeUpdate();
        }
        catch(PersistenceException e)
        {
            logger.error("deleteKey exception info[" + ExceptionUtil.throwException(e) + "]");
        }
    }
}
