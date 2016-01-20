package com.yuecheng.hops.rebate.service.impl;


import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.rebate.entity.RebateTradingVolume;
import com.yuecheng.hops.rebate.repository.RebateTradingVolumeDao;
import com.yuecheng.hops.rebate.service.RebateRuleService;
import com.yuecheng.hops.rebate.service.RebateTradingVolumeService;


@Service("rebateTradingVolumeService")
public class RebateTradingVolumeServiceImpl implements RebateTradingVolumeService
{

    @Autowired
    private RebateTradingVolumeDao rebateTradingVolumeDao;

    @Autowired
    private RebateRuleService rebateRuleService;

    private static Logger logger = LoggerFactory.getLogger(RebateTradingVolumeServiceImpl.class);

    @Override
    public RebateTradingVolume saveRebateTradingVolume(RebateTradingVolume rebateTradingVolume)
    {

        logger.debug("[RebateTradingVolumeServiceImpl:saveRebateTradingVolume("
                    + (BeanUtils.isNotNull(rebateTradingVolume) ? rebateTradingVolume.toString() :StringUtil.initString())
                                                                                     + ")]");
        rebateTradingVolume= rebateTradingVolumeDao.save(rebateTradingVolume);
        logger.debug("[RebateTradingVolumeServiceImpl:saveRebateTradingVolume("
            + (BeanUtils.isNotNull(rebateTradingVolume) ? rebateTradingVolume.toString() :StringUtil.initString())
                                                                             + ")][返回信息]");
        return rebateTradingVolume;
    }

    @Override
    public void deleteRebateTradingVolume(Long rebateTradingVolumeId)
    {
        logger.debug("[RebateTradingVolumeServiceImpl:deleteRebateTradingVolume("
                        + rebateTradingVolumeId + ")]");
        rebateTradingVolumeDao.delete(rebateTradingVolumeId);
    }

    @Override
    public RebateTradingVolume queryRebateTradingVolumeById(Long rebateTradingVolumeId)
    {
        logger.debug("[RebateTradingVolumeServiceImpl:findOne(" + rebateTradingVolumeId + ")]");
        RebateTradingVolume rebateTradingVolume= rebateTradingVolumeDao.findOne(rebateTradingVolumeId);
        logger.debug("[RebateTradingVolumeServiceImpl:findOne("
            + (BeanUtils.isNotNull(rebateTradingVolume) ? rebateTradingVolume.toString() :StringUtil.initString())
                                                                             + ")][返回信息]");
        return rebateTradingVolume;
    }

    @Override
    public RebateTradingVolume queryRebateTradingVolumeByParams(Long rebateRuleID, Long tradingVolume)
    {
        logger.debug("[RebateTradingVolumeServiceImpl:getRebateTradingVolumeByParams("
                        + rebateRuleID + "," + tradingVolume + ")]");
        RebateTradingVolume rebateTradingVolume = rebateTradingVolumeDao.getRebateTradingVolumeByParams(rebateRuleID, tradingVolume);
        logger.debug("[RebateTradingVolumeServiceImpl:getRebateTradingVolumeByParams("
            + (BeanUtils.isNotNull(rebateTradingVolume) ? rebateTradingVolume.toString() :StringUtil.initString())
                                                                             + ")][返回信息]");
        return rebateTradingVolume;
    }

    @Override
    public List<RebateTradingVolume> queryRebateTradingVolumesByParams(Long rebateRuleID)
    {
        logger.debug("[RebateTradingVolumeServiceImpl:getRebateTradingVolumesByParams("
                        + rebateRuleID + ")]");
        List<RebateTradingVolume> rebateTradingVolumes = rebateTradingVolumeDao.getRebateTradingVolumeByParams(rebateRuleID);
        logger.debug("[RebateTradingVolumeServiceImpl:getRebateTradingVolumesByParams("
            + (BeanUtils.isNotNull(rebateTradingVolumes) ? Collections3.convertToString(rebateTradingVolumes, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                             + ")][返回信息]");
        return rebateTradingVolumes;
    }

    @Override
    @Transactional
    public void delRebateTradingVolumeByRuleId(Long rebateRuleId)
    {
        logger.debug("[RebateTradingVolumeServiceImpl:delRebateTradingVolumeByRuleId("
                        + rebateRuleId + ")]");
        List<RebateTradingVolume> list = queryRebateTradingVolumesByParams(rebateRuleId);
        for (RebateTradingVolume rebateTradingVolume : list)
        {
//            deleteRebateTradingVolume(rebateTradingVolume.getRebateTradingVolumeID());
        	rebateTradingVolumeDao.delete(rebateTradingVolume.getRebateTradingVolumeID());
        }
    }

    @Override
    @Transactional
    public void saveRebateTradingVolume(Long ruleId, String tradingVolumeStr)
    {
        try
        {
            logger.debug("[RebateTradingVolumeServiceImpl:saveRebateTradingVolume(" + ruleId
                            + "," + tradingVolumeStr + ")]");
            if (BeanUtils.isNotNull(tradingVolumeStr) && !tradingVolumeStr.isEmpty())
            {
                String[] tradingVolumelist = tradingVolumeStr.split("\\*");
                int size = tradingVolumelist.length;
                if (size > 0)
                {
                    this.delRebateTradingVolumeByRuleId(ruleId);
                }
                for (String tradingVolume : tradingVolumelist)
                {
                    String[] discount = tradingVolume.split(Constant.StringSplitUtil.DECODE);

                    RebateTradingVolume rebateTradingVolume = new RebateTradingVolume();
                    rebateTradingVolume.setRebateRuleId(ruleId);
                    rebateTradingVolume.setTradingVolumeLow(new Long(discount[0]));
                    rebateTradingVolume.setTradingVolumeHigh(new Long(discount[1]));
                    rebateTradingVolume.setDiscount(new BigDecimal(discount[2]));
//                    this.saveRebateTradingVolume(rebateTradingVolume);
                    rebateTradingVolumeDao.save(rebateTradingVolume);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("[RebateTradingVolumeServiceImpl:saveRebateTradingVolume("
                             + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveRebateTradingVolume"};
            ApplicationException ae = new ApplicationException("transaction001021", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
}
