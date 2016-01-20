package com.yuecheng.hops.rebate.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.assist.RebateRuleAssist;
import com.yuecheng.hops.rebate.repository.RebateRuleDao;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;
import com.yuecheng.hops.rebate.service.RebateRuleQueryManager;
import com.yuecheng.hops.rebate.service.RebateRuleService;


@Service("rebateRuleService")
public class RebateRuleServiceImpl implements RebateRuleService
{

    @Autowired
    private RebateRuleDao rebateRuleDao;

    @Autowired
    private RebateProductQueryManager rebateProductService;

    @Autowired
    private MerchantStatusManagement merchantService;

    @Autowired
    private IdentityService identityService;
    
    @Autowired
    private RebateRuleQueryManager rebateRuleQueryManager;

    private static Logger logger = LoggerFactory.getLogger(RebateRuleServiceImpl.class);

    @Override
    @Transactional
    public RebateRuleAssist saveRebateRule(RebateRule rebateRule)
    {
        logger.debug("[RebateRuleServiceImpl:saveRebateRule(" + (BeanUtils.isNotNull(rebateRule) ? rebateRule.toString() :StringUtil.initString())
                                                                                                            + ")]");
        rebateRule= rebateRuleDao.save(rebateRule);
        RebateRuleAssist rebateRuleAssist=rebateRuleQueryManager.getAllName(rebateRule);
        logger.debug("[RebateRuleServiceImpl:saveRebateRule(" + (BeanUtils.isNotNull(rebateRuleAssist) ? rebateRuleAssist.toString() :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssist;
    }

    @Override
    @Transactional
    public void deleteRebateRule(Long rebateRuleId)
    {
        logger.debug("[RebateRuleServiceImpl:deleteRebateRule(" + rebateRuleId + ")]");
        RebateRuleAssist rebateRuleAssist = this.queryRebateRuleById(rebateRuleId);
        RebateRule rebateRule=rebateRuleAssist.getRebateRule();
        rebateRule.setStatus(Constant.RebateStatus.DEL_STATUS);
        rebateRuleDao.save(rebateRule);
    }

    @Override
    @Transactional
    public void updateRebateRule(String loginUser,Long rebateRuleId, String status)
    {
        logger.debug("[RebateRuleServiceImpl:updateRebateRule(" + rebateRuleId + "," + status
                        + ")]");
        RebateRuleAssist rebateRuleAssist = this.queryRebateRuleById(rebateRuleId);
        RebateRule rebateRule=rebateRuleAssist.getRebateRule();
        rebateRule.setStatus(status);
        rebateRule.setUpdateUser(loginUser);
        rebateRule.setUpdateDate(new Date());
        rebateRuleDao.save(rebateRule);
    }


    @Override
    public RebateRuleAssist queryRebateRuleById(Long rebateRuleId)
    {
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleById(" + rebateRuleId + ")]");
        RebateRule rebateRule = rebateRuleDao.findOne(rebateRuleId);
        RebateRuleAssist rebateRuleAssist = rebateRuleQueryManager.getAllName(rebateRule);
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleById(" + (BeanUtils.isNotNull(rebateRuleAssist) ? rebateRuleAssist.toString() :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssist;
    }

    @Override
    public List<RebateRuleAssist> getAllRebateRule()
    {
        logger.debug("[RebateRuleServiceImpl:getAllRebateRule()]");
        List<RebateRule> rebateRuleList = rebateRuleDao.selectAll();
        List<RebateRuleAssist> rebateRuleAssists = rebateRuleQueryManager.getAllName(rebateRuleList);
        logger.debug("[RebateRuleServiceImpl:getAllRebateRule(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssists;
    }

    @Override
    @Transactional
    public List<RebateRuleAssist> saveRebateRuleList(List<RebateRule> rebateRuleList)
    {
        try
        {
            logger.debug("[RebateRuleServiceImpl:saveRebateRuleList(" + (BeanUtils.isNotNull(rebateRuleList) ? Collections3.convertToString(rebateRuleList, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                                + ")]");
            List<RebateRuleAssist> backRRList = new ArrayList<RebateRuleAssist>();
            if (BeanUtils.isNotNull(rebateRuleList))
            {
                for (RebateRule rebateRule : rebateRuleList)
                {
                    rebateRule = rebateRuleDao.save(rebateRule);
                    RebateRuleAssist rebateRuleAssist=rebateRuleQueryManager.getAllName(rebateRule);
                    backRRList.add(rebateRuleAssist);
                }
            }
            logger.debug("[RebateRuleServiceImpl:saveRebateRuleList(" + (BeanUtils.isNotNull(backRRList) ? Collections3.convertToString(backRRList, Constant.Common.SEPARATOR) :StringUtil.initString())
                + ")][返回信息]");
            return backRRList;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRuleServiceImpl:saveRebateRuleList(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveRebateRuleList"};
            ApplicationException ae = new ApplicationException("transaction001018", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
