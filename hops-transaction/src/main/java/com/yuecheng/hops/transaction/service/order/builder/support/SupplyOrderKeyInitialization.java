/*
 * 文件名：SupplyOrderKeyInitialization.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年7月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.order.builder.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.transaction.basic.repository.SupplyOrderKeyDao;

@Service("supplyOrderKeyInitialization")
public class SupplyOrderKeyInitialization
{
    private static Logger         logger = LoggerFactory.getLogger(SupplyOrderKeyInitialization.class);
    
    @Autowired
    private SupplyOrderKeyDao supplyOrderKeyDao;
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void saveSupplyOrderKey(Long merchantId, Long deliveryId) throws Exception{//!!!异常优化
        try
        {
            logger.debug("开始保存SupplyOrderKey!");
            supplyOrderKeyDao.saveKey(merchantId, deliveryId);
            logger.debug("保存SupplyOrderKey结束!");    
        }
        catch (ApplicationException e)
        {
            throw e;//!!!
        }
        catch (Exception e)
        {
            throw e;
        }
        
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void deleteSupplyOrderKey(Long merchantId, Long deliveryId){
        supplyOrderKeyDao.deleteKey(merchantId, deliveryId);
    }
}
