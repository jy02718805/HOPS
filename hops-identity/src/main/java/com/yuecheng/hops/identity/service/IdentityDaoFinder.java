package com.yuecheng.hops.identity.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.identity.repository.CustomerDao;
import com.yuecheng.hops.identity.repository.IdentityDao;
import com.yuecheng.hops.identity.repository.MerchantDao;
import com.yuecheng.hops.identity.repository.OperatorDao;
import com.yuecheng.hops.identity.repository.SpDao;


@Component
public class IdentityDaoFinder
{
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private SpDao spDao;

    @Autowired
    private OperatorDao operatorDao;

    private static final Logger logger = LoggerFactory.getLogger(IdentityDaoFinder.class);

    @SuppressWarnings("rawtypes")
    public IdentityDao getByIdentityType(IdentityType identityType)
    {
        if (identityType != null)
        {
            logger.debug("[IdentityDaoFinder:getByIdentityType(" + identityType.toString() + ")]");
            String identityTypeStr = identityType.toString();
            if (identityTypeStr.equalsIgnoreCase(IdentityType.CUSTOMER.toString()))
            {
                return customerDao;
            }
            else if (identityTypeStr.equalsIgnoreCase(IdentityType.MERCHANT.toString()))
            {
                return merchantDao;
            }
            else if (identityTypeStr.equalsIgnoreCase(IdentityType.OPERATOR.toString()))
            {
                return operatorDao;
            }
            else if (identityTypeStr.equalsIgnoreCase(IdentityType.SP.toString()))
            {
                return spDao;
            }
            else
            {
                logger.debug("[IdentityDaoFinder:getByIdentityType(" + identityType.toString()
                             + ")][返回信息]");
                String[] msgParams = new String[] {identityType.toString()};
                String[] viewParams = new String[] {};
                ApplicationException e = new ApplicationException("identity001053", msgParams,
                    viewParams);
                throw ExceptionUtil.throwException(e);
            }
        }
        logger.debug("[IdentityDaoFinder:getByIdentityType(identityType为null)][返回信息]");
        String[] msgParams = new String[] {null};
        String[] viewParams = new String[] {};
        ApplicationException e = new ApplicationException("identity001053", msgParams, viewParams);
        throw ExceptionUtil.throwException(e);
    }
}
