package com.yuecheng.hops.identity.service.operator.impl;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.repository.OperatorDao;
import com.yuecheng.hops.identity.service.operator.OperatorPasswordManagement;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;


@Service("operatorPasswordManagement")
public class OperatorPasswordManagementImpl implements OperatorPasswordManagement
{

    private static final Logger logger = LoggerFactory.getLogger(OperatorPasswordManagementImpl.class);

    @Autowired
    private OperatorDao operatorDao;

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Override
    public Long resetPassword(Long identityId, String oldPassword, String newPassword,
                              String updateUserName)
    {
        try
        {
            String[] msgParams = new String[] {"resetPassword"};
            logger.debug("OperatorServiceImpl:resetPassword(" + identityId + "," + oldPassword
                         + "," + newPassword + ")");
            if (StringUtil.isNullOrEmpty(oldPassword) || StringUtil.isNullOrEmpty(newPassword))
            {
                logger.error("[OperatorServiceImpl:resetPassword()]");
                ApplicationException ae = new ApplicationException("identity001048", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            Operator operator = operatorDao.findOne(identityId);
            SecurityCredential lastest = securityCredentialManagerService.updateSecurityCredential(
                operator, oldPassword, newPassword, updateUserName);
            return lastest.getIdentityId();
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[OperatorServiceImpl:resetPassword("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"resetPassword"};
            ApplicationException ae = new ApplicationException("identity001030", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public Operator requestResetPasswordEmail(Long operatorId, String updateUser)
    {
        try
        {
            logger.debug("OperatorServiceImpl:requestResetPasswordEmail(" + operatorId + ")");
            Operator operator = operatorDao.findOne(operatorId);
            if (operator == null)
            {
                logger.error("[OperatorServiceImpl:requestResetPasswordEmail()]");
                String[] msgParams = new String[] {"requestResetPasswordEmail"};
                ApplicationException ae = new ApplicationException("identity001046", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            // 是否要对操作员的状态、秘钥的状态做检查？ 暂时不处理
            securityCredentialManagerService.resetSecurityCredential(operator, updateUser);
            logger.debug("OperatorServiceImpl:requestResetPasswordEmail("
                         + (BeanUtils.isNotNull(operator) ? operator.toString() : "") + ")[返回信息]");
            return operator;
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[OperatorServiceImpl:requestResetPasswordEmail("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"requestResetPasswordEmail"};
            ApplicationException ae = new ApplicationException("identity001031", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public boolean activeNewPassword(Operator operator, String token, Date date)
    {
        return false;
    }

}