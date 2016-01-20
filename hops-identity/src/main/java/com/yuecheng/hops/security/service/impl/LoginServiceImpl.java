package com.yuecheng.hops.security.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.PrivilegeConstants;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.customer.CustomerService;
import com.yuecheng.hops.identity.service.operator.OperatorService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.LoginService;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityTypeService;


@Service("loginService")
public class LoginServiceImpl implements LoginService
{

    @Autowired
    SecurityCredentialService securityCredentialService;

    @Autowired
    SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OperatorService operatorService;

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    public LoginServiceImpl()
    {
        super();
    }

    /**
     * 检查登录密码是否正确
     * 
     * @param name
     * @param pwd
     * @param customer
     * @return
     */
    public String checkSecurityCredential(String name, String pwd, AbstractIdentity identity)
    {
        try
        {
            logger.debug("LoginServiceImpl:checkSecurityCredential(" + name + "," + pwd + ","
                         + (BeanUtils.isNotNull(identity) ? identity.toString() : "") + ")");
            SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.PASSWORD);
            SecurityCredential sc = securityCredentialService.querySecurityCredentialByParams(
                identity.getId(), identity.getIdentityType(),
                (BeanUtils.isNotNull(securityTyp) ? securityTyp.getSecurityTypeId() : null),
                Constant.SecurityCredentialStatus.ENABLE_STATUS);
            if (null != sc && null != sc.getSecurityId())
            {
                String md5Pwd = securityCredentialManagerService.getLoginEncryptKey(pwd,
                    identity.getId());
                if (sc.getSecurityValue().equals(md5Pwd))
                {
                    logger.debug("LoginServiceImpl:checkSecurityCredential(密码验证通过)[返回信息]");
                    return PrivilegeConstants.SUCCESS;
                }
                else
                {
                    String[] msgParams = new String[] {name, pwd};
                    ApplicationException e = new ApplicationException("identity000023", msgParams);
                    throw ExceptionUtil.throwException(e);
                }
            }
            else
            {
                String[] msgParams = new String[] {name, pwd};
                ApplicationException e = new ApplicationException("identity000023", msgParams);
                throw ExceptionUtil.throwException(e);
            }
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[LoginServiceImpl:checkSecurityProperty("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {identity.getId().toString(),
                identity.getIdentityType().toString()};
            ApplicationException ae = new ApplicationException("identity000030", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * Operator用户登录
     */
    @Override
    public AbstractIdentity logon(String name, String pwd, IdentityType identityType)
    {
        try
        {
            logger.debug("[LoginServiceImpl:login(" + name + "," + pwd + ")]");
            // 判断必须参数
            if (StringUtil.isNullOrEmpty(name) || StringUtil.isNullOrEmpty(pwd))
            {
                logger.error("[LoginServiceImpl:login(用户名或密码为空)]");
                String[] msgParams = new String[] {"logon"};
                ApplicationException ae = new ApplicationException("identity50001", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            AbstractIdentity identity = null;
            if (identityType.toString().equals(IdentityType.CUSTOMER.toString()))
            {
                identity = customerService.queryCustomerByCustomerName(name);
                identity.setIdentityType(IdentityType.CUSTOMER);
            }
            else
            {
                identity = operatorService.queryOperatorByOperatorName(name);
                identity.setIdentityType(IdentityType.OPERATOR);
            }

            String result = checkSecurityCredential(name, pwd, identity);
            if (result.equals(Constant.Common.SUCCESS))
            {
                logger.debug("[LoginServiceImpl:login("
                             + (BeanUtils.isNotNull(identity) ? identity.toString() : "")
                             + ")][返回信息]");
                return identity;
            }
            logger.error("[LoginServiceImpl:login(登录失败)]");
            String[] msgParams = new String[] {"logon"};
            ApplicationException ae = new ApplicationException("identity001004", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception ex)
        {
            logger.error("[LoginServiceImpl:login(" + ex.getMessage() + ")]");
            if (ex instanceof HopsException)
            {
                throw new ApplicationException(ex.getMessage());
            }
            else
            {
                String[] msgParams = new String[] {name, pwd};
                ApplicationException e = new ApplicationException("identity001004", msgParams);
                throw ExceptionUtil.throwException(e);
            }
        }
    }

    /**
     * Customer用户注册
     */
    @Override
    public AbstractIdentity saveRegistPassword(AbstractIdentity identity, String loginPwd,
                                               String payPwd, String updateUser)
    {
        try
        {
            if (BeanUtils.isNotNull(identity) && StringUtil.isNotBlank(loginPwd))
            {
                logger.debug("[LoginServiceImpl:regist(" + identity.toString() + ")]");
                // 保存登录密码
                securityCredentialManagerService.saveSecurityCredential(identity, loginPwd,
                    updateUser, Constant.SecurityCredential.LOGIN_MD5);
                if (StringUtil.isNotBlank(payPwd))
                {
                    // 保存支付密码
                    securityCredentialManagerService.saveSecurityCredential(identity, loginPwd,
                        updateUser, Constant.SecurityCredential.PAY_MD5);
                }
            }
            logger.debug("[LoginServiceImpl:regist("
                         + (BeanUtils.isNotNull(identity) ? identity.toString() : "") + ")][返回信息]");
            return identity;
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[LoginServiceImpl:regist(" + ExceptionUtil.getStackTraceAsString(e)
                         + ")]");
            if (e instanceof HopsException)
            {
                throw new ApplicationException(ExceptionUtil.getStackTraceAsString(e));
            }
            else
            {
                ApplicationException ae = new ApplicationException("identity000024");
                throw ExceptionUtil.throwException(ae);
            }
        }
    }

}