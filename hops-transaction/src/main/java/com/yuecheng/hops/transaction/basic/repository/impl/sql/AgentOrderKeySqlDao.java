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
import com.yuecheng.hops.transaction.basic.repository.AgentOrderKeyDao;


@Service
public class AgentOrderKeySqlDao implements AgentOrderKeyDao
{
    private static Logger logger = LoggerFactory.getLogger(AgentOrderKeySqlDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void saveKey(Long merchantId, String merchantOrderNo) throws Exception
    {
        try
        {
            String sql = "insert into agent_order_key (MERCHANT_ID, MERCHANT_ORDER_NO) values (?, ?)";
            em.createNativeQuery(sql).setParameter(1, merchantId).setParameter(2, merchantOrderNo).executeUpdate();
        }
        catch(PersistenceException e)//!!!收敛数据方面异常
        {
            if(e.getCause().getCause().toString().contains("ORA-00001")){
                logger.error("saveKey exception info[" + ExceptionUtil.throwException(e) + "]");
                throw new ApplicationException(Constant.ErrorCode.IS_EXIST);
            }
            else
            {
                logger.error("saveKey exception info[" + ExceptionUtil.throwException(e) + "]");
                throw new ApplicationException(Constant.ErrorCode.FAIL);
            }
        }
    }
}
