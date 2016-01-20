/*
 * 文件名：OrderSqlDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年11月24日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.basic.repository.impl.sql;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.repository.NotifyDao;


@Service
public class NotifySqlDao implements NotifyDao
{
    private static Logger logger = LoggerFactory.getLogger(NotifySqlDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Notify> getNotifyOrdersByStatus(Integer notifyStatus, Integer scannerNotifyNum)
    {
        try
        {
            String sql = "select * from yc_notify where create_time<sysdate and create_time>sysdate-3 and notify_status=:notifyStatus and notify_cntr <= limited_cntr and start_time <= sysdate and rownum<=:scannerNotifyNum";
            Query query = em.createNativeQuery(sql, Notify.class);
            query.setParameter("notifyStatus", notifyStatus);
            query.setParameter("scannerNotifyNum", scannerNotifyNum);
            List<Notify> notifyList = query.getResultList();
            return notifyList;
        }
        catch (Exception e)
        {
            logger.error("getNotifyOrdersByStatus exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public Notify findNotifyByOrderNo(Long orderNo)
    {
        try
        {
            String sql = "select * from yc_notify where order_no=:orderNo";
            Query query = em.createNativeQuery(sql, Notify.class);
            query.setParameter("orderNo", orderNo);
            List<Notify> notifys = query.getResultList();
            if (notifys.size() > 0)
            {
                return notifys.get(0);
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            logger.error("findNotifyByOrderNo exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }
}
