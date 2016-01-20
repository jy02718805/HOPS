package com.yuecheng.hops.identity.service.sp.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.repository.SpDao;
import com.yuecheng.hops.identity.service.impl.IdentityServiceImpl;
import com.yuecheng.hops.identity.service.sp.SpService;


@Service("spService")
public class SpServiceImpl extends IdentityServiceImpl implements SpService
{

    @Autowired
    private SpDao spDao;

    private static Logger logger = LoggerFactory.getLogger(SpServiceImpl.class);

    @Override
    public SP getSP()
    {
        try
        {
            logger.debug("[SpServiceImpl:getSP()]");
            SP sp = spDao.getSpByBusiness(Constant.BusinessType.BUSINESS_AIRTIME);
            sp.setIdentityType(IdentityType.SP);
            logger.debug("[SpServiceImpl:getSP(" + (BeanUtils.isNotNull(sp) ? sp.toString() :"") + ")][返回信息]");
            return sp;
        }
        catch (Exception e)
        {
            logger.error("[SpServiceImpl:getSP(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getSP"};
            ApplicationException ae = new ApplicationException("identity001041", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
