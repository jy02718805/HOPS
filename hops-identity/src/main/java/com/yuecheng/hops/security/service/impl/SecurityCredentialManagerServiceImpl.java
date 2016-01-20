package com.yuecheng.hops.security.service.impl;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.IdentityConstants;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.security.DesUtil;
import com.yuecheng.hops.common.security.MD5Util;
import com.yuecheng.hops.common.security.RSAUtils;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialStatusTransfer;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityCredentialStatusTransferService;
import com.yuecheng.hops.security.service.SecurityKeystoreService;
import com.yuecheng.hops.security.service.SecurityTypeService;


@Service("securityCredentialManagerService")
public class SecurityCredentialManagerServiceImpl implements SecurityCredentialManagerService
{
    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    private SecurityKeystoreService securityKeystoreService;

    @Autowired
    private SecurityCredentialStatusTransferService securityCredentialStatusTransferService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(SecurityCredentialManagerServiceImpl.class);

    @Override
    public String getLoginEncryptKey(String pwd, Long identityId)
    {
        // .....系统md5key1,2
        String loginPwd = StringUtil.initString();
        logger.debug("[SecurityCredentialManagerServiceImpl:getMD5SecurityCredential(" + pwd
                     + ")]");
        try
        {
            SecurityCredential securityCredential1 = securityCredentialService.getSecurityCredentialByName(Constant.SecurityCredential.SYSTEM_MD5_KEY_1);
            SecurityCredential securityCredential2 = securityCredentialService.getSecurityCredentialByName(Constant.SecurityCredential.SYSTEM_MD5_KEY_2);

            if (null != securityCredential1 && null != securityCredential2
                && StringUtil.isNotBlank(pwd))
            {
                String sk1 = decrypt(securityCredential1.getSecurityValue(),
                    Constant.EncryptType.ENCRYPT_TYPE_3DES);
                String sk2 = decrypt(securityCredential2.getSecurityValue(),
                    Constant.EncryptType.ENCRYPT_TYPE_3DES);
                loginPwd = sk1 + pwd + sk2 + identityId;
                loginPwd = MD5Util.getMD5Sign(loginPwd);
                return loginPwd;
            }
            else
            {
                logger.error("[SecurityCredentialManagerServiceImpl:getMD5SecurityCredential("
                             + pwd + ")] 报错:系统MD5Key为空或密码为空");
                String[] msgParams = new String[] {"getMD5SecurityCredential"};
                ApplicationException ae = new ApplicationException("identity001051", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialManagerServiceImpl:getMD5SecurityCredential(" + pwd
                         + ")] " + ExceptionUtil.getStackTraceAsString(e));
            String[] msgParams = new String[] {"getMD5SecurityCredential"};
            ApplicationException ae = new ApplicationException("identity001075", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 从缓存中取3des密钥
     * 
     * @return
     */
    private String getKey3Des()
    {
        logger.debug("[SecurityCredentialManagerServiceImpl:getKey3Des(进入缓存获取3DES密钥)]");
        String key3Des = (String)HopsCacheUtil.get(Constant.Common.IDENTITY_CACHE,
            Constant.SecurityCredential.KEY_3DES);
        if (StringUtil.isNullOrEmpty(key3Des))
        {
            SecurityCredential securityCredential = securityCredentialService.getSecurityCredentialByName(Constant.SecurityCredential.KEY_3DES);
            key3Des = decrypt(securityCredential.getSecurityValue(),
                Constant.EncryptType.ENCRYPT_TYPE_RSA);
            HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE,
                Constant.SecurityCredential.KEY_3DES, key3Des);
            logger.debug("[SecurityCredentialManagerServiceImpl:getKey3Des(缓存中不存在，重新在数据库查询，并放入缓存)]");
        }
        logger.debug("[SecurityCredentialManagerServiceImpl:getKey3Des(3DES密钥:" + key3Des + ")]");
        return key3Des;
    }

    @Override
    public void stopSecurityCredential()
    {
        logger.debug("[SecurityCredentialManagerServiceImpl:stopSecurityCredential(进入终止使用过期的密钥方法)]");
        List<SecurityCredential> securityCredentials = securityCredentialService.getSecurityCredentialListByStatus(Constant.SecurityCredentialStatus.ENABLE_STATUS);
        for (SecurityCredential securityCredential : securityCredentials)
        {
            Date validityDate = securityCredential.getValidityDate();
            Date currDate = new Date();
            if (validityDate.compareTo(currDate) < 0)
            {
                logger.debug("[SecurityCredentialManagerServiceImpl:stopSecurityCredential("
                             + securityCredential.getSecurityId() + ":已到期，修改状态为已过期)]");
                securityCredential.setStatus(Constant.SecurityCredentialStatus.EXPIRATION_STATUS);
                securityCredential.setUpdateDate(new Date());
                securityCredential.setUpdateUser("system");
                securityCredentialService.updateSecurityCredential(securityCredential);
            }
        }
    }

    @Override
    public String decryptKeyBySecurityId(Long securityId)
    {
        logger.debug("[SecurityCredentialManagerServiceImpl: decryptKeyBySecurityId(securityId:"
                     + securityId + ")] ");
        String result = StringUtil.initString();
        try
        {
            SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialById(securityId);
            String securityString = securityCredential.getSecurityValue();
            if (securityCredential.getSecurityType().getEncryptType().equalsIgnoreCase(
                Constant.EncryptType.ENCRYPT_TYPE_RSA))
            {
                result = decrypt(securityString, Constant.EncryptType.ENCRYPT_TYPE_RSA);
            }
            else if (securityCredential.getSecurityType().getEncryptType().equalsIgnoreCase(
                Constant.EncryptType.ENCRYPT_TYPE_3DES))
            {
                result = decrypt(securityString, Constant.EncryptType.ENCRYPT_TYPE_3DES);
            }
            logger.debug("[SecurityCredentialManagerServiceImpl: decryptKeyBySecurityId(" + result
                         + ")][返回信息]");
            return result;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialManagerServiceImpl: decryptKeyBySecurityId(securityId:"
                         + securityId + ")] " + ExceptionUtil.getStackTraceAsString(e));
            String[] msgParams = new String[] {"decryptKeyBySecurityId"};
            ApplicationException ae = new ApplicationException("identity001080", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public String decryptKeyBySecurity(SecurityCredential securityCredential)
    {
        logger.debug("[SecurityCredentialManagerServiceImpl: decryptKeyBySecurity(securityCredential (securityId:"
                     + securityCredential.getSecurityId() + "))] ");
        String result = StringUtil.initString();
        try
        {
            String securityString = securityCredential.getSecurityValue();
            if (securityCredential.getSecurityType().getEncryptType().equalsIgnoreCase(
                Constant.EncryptType.ENCRYPT_TYPE_RSA))
            {
                result = decrypt(securityString, Constant.EncryptType.ENCRYPT_TYPE_RSA);
            }
            else if (securityCredential.getSecurityType().getEncryptType().equalsIgnoreCase(
                Constant.EncryptType.ENCRYPT_TYPE_3DES))
            {
                result = decrypt(securityString, Constant.EncryptType.ENCRYPT_TYPE_3DES);
            }
            logger.debug("[SecurityCredentialManagerServiceImpl: decryptKeyBySecurity(" + result
                         + ")][返回信息]");
            return result;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialManagerServiceImpl: decryptKeyBySecurity(securityCredential:"
                         + securityCredential + ")] " + ExceptionUtil.getStackTraceAsString(e));
            String[] msgParams = new String[] {"decryptKeyBySecurity"};
            ApplicationException ae = new ApplicationException("identity001081", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public String encrypt(String key, String encryptType)
    {
        try
        {
            if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(encryptType))
            {
                String encryptValue = StringUtil.initString();
                if (Constant.EncryptType.ENCRYPT_TYPE_MD5.equals(encryptType))
                {
                    encryptValue = MD5Util.getMD5Sign(key);
                }
                else if (Constant.EncryptType.ENCRYPT_TYPE_3DES.equals(encryptType))
                {
                    String des3Key = getKey3Des();
                    encryptValue = DesUtil.encryptDes(des3Key, key);
                }
                else if (Constant.EncryptType.ENCRYPT_TYPE_RSA.equals(encryptType))
                {
                    RSAPublicKey publicKey = securityKeystoreService.getRSAPublicKey(Constant.SecurityCredential.RSA_KEY);
                    encryptValue = RSAUtils.encryptByPublicKey(key, publicKey);
                }
                else
                {
                    logger.error("[SecurityCredentialManagerServiceImpl: encrypt(key:" + key
                                 + ",encryptType:" + encryptType + ")] ");
                    String[] msgParams = new String[] {"encrypt"};
                    ApplicationException ae = new ApplicationException("identity001098", msgParams);
                    throw ExceptionUtil.throwException(ae);
                }
                logger.debug("[SecurityCredentialManagerServiceImpl: encrypt(" + encryptValue
                             + ")][返回信息]");
                return encryptValue;
            }
            logger.error("[SecurityCredentialManagerServiceImpl: encrypt(key:" + key
                         + ",encryptType:" + encryptType + ")] ");
            String[] msgParams = new String[] {"encrypt"};
            ApplicationException ae = new ApplicationException("identity001097", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialManagerServiceImpl: encrypt()]"
                         + ExceptionUtil.getStackTraceAsString(e));
            String[] msgParams = new String[] {"encrypt"};
            ApplicationException ae = new ApplicationException("identity001099", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public String decrypt(String key, String encryptType)
    {
        try
        {
            if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(encryptType))
            {
                String decryptValue = StringUtil.initString();
                if (Constant.EncryptType.ENCRYPT_TYPE_MD5.equals(encryptType))
                {
                    logger.error("[SecurityCredentialManagerServiceImpl: decrypt(encryptKey:"
                                 + key + ",encryptType:" + encryptType + ")] ");
                    String[] msgParams = new String[] {"decrypt"};
                    ApplicationException ae = new ApplicationException("identity001098", msgParams);
                    throw ExceptionUtil.throwException(ae);
                }
                else if (Constant.EncryptType.ENCRYPT_TYPE_3DES.equals(encryptType))
                {
                    String des3Key = getKey3Des();
                    decryptValue = DesUtil.decryptDes(des3Key, key);
                }
                else if (Constant.EncryptType.ENCRYPT_TYPE_RSA.equals(encryptType))
                {
                    RSAPrivateKey privateKey = (RSAPrivateKey)securityKeystoreService.getRSAPrivateKey(Constant.SecurityCredential.RSA_KEY);
                    decryptValue = RSAUtils.decryptByPrivateKey(key, privateKey);

                }
                else if (Constant.EncryptType.ENCRYPT_TYPE_JSRSA.equals(encryptType))
                {
                    RSAPrivateKey privateKey = (RSAPrivateKey)securityKeystoreService.getRSAPrivateKey(Constant.SecurityCredential.RSA_KEY);
                    decryptValue = RSAUtils.decryptByJs(privateKey, key);
                }
                else
                {
                    logger.error("[SecurityCredentialManagerServiceImpl: decrypt(encryptKey:"
                                 + key + ",encryptType:" + encryptType + ")] ");
                    String[] msgParams = new String[] {"decrypt"};
                    ApplicationException ae = new ApplicationException("identity001098", msgParams);
                    throw ExceptionUtil.throwException(ae);
                }
                logger.debug("[SecurityCredentialManagerServiceImpl: decrypt(" + key + ")][返回信息]");
                return decryptValue;
            }
            logger.error("[SecurityCredentialManagerServiceImpl: decrypt(encryptKey:" + key
                         + ",encryptType:" + encryptType + ")] ");
            String[] msgParams = new String[] {"decrypt"};
            ApplicationException ae = new ApplicationException("identity001097", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialManagerServiceImpl: decrypt()]"
                         + ExceptionUtil.getStackTraceAsString(e));
            String[] msgParams = new String[] {"decrypt"};
            ApplicationException ae = new ApplicationException("identity001099", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public String getEncryptKeyByJSRSAKey(String jsRsaKey, Long identityId)
    {
        logger.debug("[SecurityCredentialManagerServiceImpl:getEncryptKeyByJSRSAKey(" + jsRsaKey
                     + ")]");
        String key = this.decrypt(jsRsaKey, Constant.EncryptType.ENCRYPT_TYPE_JSRSA);
        String encryptKey = this.getLoginEncryptKey(key, identityId);
        logger.debug("[SecurityCredentialManagerServiceImpl:getEncryptKeyByJSRSAKey(" + encryptKey
                     + ")][返回信息]");
        return encryptKey;
    }

    @Override
    public void checkSecurity(String SecurityCredentialType, String pwd)
        throws Exception
    {
        // TODO Auto-generated method stub
        String loginPwd = decrypt(pwd, Constant.EncryptType.ENCRYPT_TYPE_JSRSA);
        SecurityCredentialType securityType = securityTypeService.querySecurityTypeByTypeName(SecurityCredentialType);
        boolean bl = false;
        
        
        
        if (Constant.SecurityRule.NEED.equals(securityType.getSecurityRule().getLetter()))
        {
            bl = StringUtil.isLetter(loginPwd);
            if (!bl)
            {
                String[] msgParams = new String[] {"checkSecurity"};
                String[] viewParams = new String[] {};
                ApplicationException ae = new ApplicationException("identity101211", msgParams);
                throw new Exception(ae.getMessage());
            }

            if (Constant.SecurityRule.NEED.equals(securityType.getSecurityRule().getIsUpperCase()))
            {
                bl = StringUtil.isUpperCase(loginPwd);
                if (!bl)
                {
                    String[] msgParams = new String[] {"checkSecurity"};
                    ApplicationException ae = new ApplicationException("identity101214", msgParams);
                    throw new Exception(ae.getMessage());
                }
            }
            else if (Constant.SecurityRule.NOTNEED.equals(securityType.getSecurityRule().getIsUpperCase()))
            {
                bl = StringUtil.isUpperCase(loginPwd);
                if (bl)
                {
                    String[] msgParams = new String[] {"checkSecurity"};
                    ApplicationException ae = new ApplicationException("identity101219", msgParams);
                    throw new Exception(ae.getMessage());
                }
            }

            if (Constant.SecurityRule.NEED.equals(securityType.getSecurityRule().getIsLowercase()))
            {
                bl = StringUtil.isLowerCase(loginPwd);
                if (!bl)
                {
                    String[] msgParams = new String[] {"checkSecurity"};
                    ApplicationException ae = new ApplicationException("identity101215", msgParams);
                    throw new Exception(ae.getMessage());
                }
            }
            else if (Constant.SecurityRule.NOTNEED.equals(securityType.getSecurityRule().getIsUpperCase()))
            {
                bl = StringUtil.isLowerCase(loginPwd);
                if (bl)
                {
                    String[] msgParams = new String[] {"checkSecurity"};
                    ApplicationException ae = new ApplicationException("identity101220", msgParams);
                    throw new Exception(ae.getMessage());
                }
            }
        }
        else if (Constant.SecurityRule.NOTNEED.equals(securityType.getSecurityRule().getLetter()))
        {
            bl = StringUtil.isLetter(loginPwd);
            if (bl)
            {
                String[] msgParams = new String[] {"checkSecurity"};
                ApplicationException ae = new ApplicationException("identity101216", msgParams);
                throw new Exception(ae.getMessage());
            }
        }
        if (Constant.SecurityRule.NEED.equals(securityType.getSecurityRule().getFigure()))
        {
            bl = StringUtil.isNumber(loginPwd);
            if (!bl)
            {
                String[] msgParams = new String[] {"checkSecurity"};
                ApplicationException ae = new ApplicationException("identity101212", msgParams);
                throw new Exception(ae.getMessage());
            }
        }
        else if (Constant.SecurityRule.NOTNEED.equals(securityType.getSecurityRule().getFigure()))
        {
            bl = StringUtil.isNumber(loginPwd);
            if (bl)
            {
                String[] msgParams = new String[] {"checkSecurity"};
                ApplicationException ae = new ApplicationException("identity101217", msgParams);
                throw new Exception(ae.getMessage());
            }
        }

        if (Constant.SecurityRule.NEED.equals(securityType.getSecurityRule().getSpecialCharacter()))
        {
            bl = StringUtil.isConSpeCharacters(loginPwd);
            if (!bl)
            {
                String[] msgParams = new String[] {"checkSecurity"};
                ApplicationException ae = new ApplicationException("identity101213", msgParams);
                throw new Exception(ae.getMessage());
            }
        }
        else if (Constant.SecurityRule.NOTNEED.equals(securityType.getSecurityRule().getSpecialCharacter()))
        {
            bl = StringUtil.isConSpeCharacters(loginPwd);
            if (bl)
            {
                String[] msgParams = new String[] {"checkSecurity"};
                ApplicationException ae = new ApplicationException("identity101218", msgParams);
                throw new Exception(ae.getMessage());
            }
        }
    }

    @Override
    public SecurityCredential resetSecurityCredential(com.yuecheng.hops.identity.entity.operator.Operator operator,
                                                      String updateUser)
    {
        try
        {
            logger.debug("[SecurityCredentialServiceImpl:resetSecurityCredential("
                         + (BeanUtils.isNotNull(operator) ? operator.toString() : "") + ","
                         + updateUser + ")]");
            // 生成新密码
            long milli = System.currentTimeMillis();
            String tempPassword = String.valueOf(milli).substring(0, 6);
            String escriptPassword = getLoginEncryptKey(tempPassword, operator.getId());
            // 修改秘钥状态
            SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.PASSWORD);
            SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialByParams(
                operator.getId(), IdentityType.OPERATOR,
                (BeanUtils.isNotNull(securityTyp) ? securityTyp.getSecurityTypeId() : null),
                Constant.SecurityCredentialStatus.ENABLE_STATUS);
            securityCredential.setStatus(IdentityConstants.SECURITY_CREDENTIAL_ACTIVE);
            securityCredential.setSecurityValue(escriptPassword);
            securityCredential.setUpdateDate(new Date());
            securityCredential.setUpdateUser(updateUser);
            SecurityCredential persistSecurityCredential = securityCredentialService.saveSecurityCredential(securityCredential);
            logger.debug("[SecurityCredentialServiceImpl:resetSecurityCredential("
                         + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                         + ")][返回信息]");
            return persistSecurityCredential;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialServiceImpl:resetSecurityCredential(充值密码失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"resetSecurityCredential"};
            ApplicationException ae = new ApplicationException("identity001084", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredential updateSecurityCredential(com.yuecheng.hops.identity.entity.operator.Operator operator,
                                                       String oldPwd, String newPwd,
                                                       String updateUser)
    {
        try
        {
            logger.debug("[SecurityCredentialServiceImpl:updateSecurityCredential("
                         + (BeanUtils.isNotNull(operator) ? operator.toString() : "") + ","
                         + oldPwd + "," + newPwd + "," + updateUser + ")]");
            oldPwd = decrypt(oldPwd, Constant.EncryptType.ENCRYPT_TYPE_JSRSA);
            newPwd = decrypt(newPwd, Constant.EncryptType.ENCRYPT_TYPE_JSRSA);

            SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.PASSWORD);
            // 修改秘钥状态
            SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialByParams(
                operator.getId(), IdentityType.OPERATOR,
                (BeanUtils.isNotNull(securityTyp) ? securityTyp.getSecurityTypeId() : null), null);
            String oldEscriptPassword = getLoginEncryptKey(oldPwd, operator.getId());
            if (!oldEscriptPassword.equals(securityCredential.getSecurityValue()))
            {
                String[] msgParams = new String[] {"updateSecurityCredential"};
                logger.error("[OperatorServiceImpl:updateSecurityCredential()]");
                ApplicationException ae = new ApplicationException("identity001049", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            String newEscriptPassword = getLoginEncryptKey(newPwd, operator.getId());
            securityCredential.setSecurityValue(newEscriptPassword);
            securityCredential.setUpdateUser(updateUser);
            securityCredential.setUpdateDate(new Date());
            SecurityCredential lastest = securityCredentialService.saveSecurityCredential(securityCredential);
            logger.debug("[SecurityCredentialServiceImpl:updateSecurityCredential("
                         + (BeanUtils.isNotNull(lastest) ? lastest.toString() : "") + ")][返回信息]");
            return lastest;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialServiceImpl:updateSecurityCredential(修改密码失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"updateSecurityCredential"};
            ApplicationException ae = new ApplicationException("identity001084", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredential saveSecurityCredential(AbstractIdentity identity, String pwd,
                                                     String updateUser, String passwordType)
    {
        try
        {
            logger.debug("SecurityCredentialServiceImpl:saveSecurityCredential("
                         + (BeanUtils.isNotNull(identity) ? identity.toString() : "") + ")");
            String tempPwd = StringUtil.initString();
            if (StringUtil.isNullOrEmpty(pwd))
            {
                // 设置注册登录默认密码
                tempPwd = Constant.SecurityCredential.DEFULT_PWD;
            }
            else
            {
                tempPwd = getEncryptKeyByJSRSAKey(pwd, identity.getId());
            }
            String securityName = identity.getId() + Constant.SecurityCredential.SECURITYNAME_PAY;
            if (Constant.SecurityCredential.LOGIN_MD5.equals(passwordType))
            {
                securityName = identity.getId() + Constant.SecurityCredential.SECURITYNAME_LONGIN;
            }
            // 密钥类型
            SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.PASSWORD);
            SecurityCredential securityCredential = saveSecurityCredential(identity, securityName,
                tempPwd, updateUser, securityTyp);
            logger.debug("SecurityCredentialServiceImpl:saveSecurityCredential("
                         + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                         + ")");
            return securityCredential;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialServiceImpl:saveSecurityCredential("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            ApplicationException ae = new ApplicationException("identity001005");
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredential saveSecurityCredential(AbstractIdentity identity,
                                                     String securityName, String securityValue,
                                                     String updateUser,
                                                     SecurityCredentialType securityTyp)
    {
        try
        {
            SecurityCredential securityCredential = new SecurityCredential();
            securityCredential.setSecurityName(securityName);
            securityCredential.setIdentityId(identity.getId());
            securityCredential.setIdentityType(identity.getIdentityType());
            securityCredential.setSecurityValue(securityValue);
            securityCredential.setStatus(Constant.CustomerStatus.OPEN_STATUS);
            securityCredential.setCreateUser(updateUser);
            securityCredential.setCreateDate(new Date());
            securityCredential.setUpdateUser(updateUser);
            securityCredential.setUpdateDate(new Date());
            securityCredential.setSecurityType(securityTyp);
            Date validityDate = sdf.parse(Constant.SecurityCredential.DEFULT_VALIDITY_DATE);
            if (0 != securityTyp.getValidity())
            {
                validityDate = DateUtil.addDate(Constant.DateUnit.TIME_UNIT_DAY,
                    securityTyp.getValidity().intValue(), securityCredential.getUpdateDate());
            }
            securityCredential.setValidityDate(validityDate);
            securityCredential = securityCredentialService.saveSecurityCredential(securityCredential);
            logger.error("[SecurityCredentialServiceImpl:saveSecurityCredential("
                         + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                         + ")][返回信息]");
            return securityCredential;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialServiceImpl:saveSecurityCredential("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            ApplicationException ae = new ApplicationException("identity001005");
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredential updateSecurityCredentialStatus(SecurityCredential securityCredential,
                                                             String status, String updateUser)
    {
        try
        {
            if (null == securityCredential || null == status)
            {
                logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus(修改密钥状态机信息失败，参数为空)]");
                ApplicationException ae = new ApplicationException("identity001107");
                throw ExceptionUtil.throwException(ae);
            }
            else
            {
                SecurityCredentialStatusTransfer securityCredentialStatusTransfer = securityCredentialStatusTransferService.querySecurityCredentialStatusTransferByParams(
                    securityCredential.getStatus(), status);
                if (null != securityCredentialStatusTransfer)
                {
                    securityCredential.setStatus(status);
                    securityCredential.setUpdateDate(new Date());
                    securityCredential.setUpdateUser(updateUser);
                    securityCredential = securityCredentialService.saveSecurityCredential(securityCredential);
                    logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus("
                                 + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                                 + ")][返回信息]");
                    return securityCredential;
                }
                else
                {
                    logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus(修改密钥状态机信息失败，状态不能进行转换)]");
                    ApplicationException ae = new ApplicationException("identity001108");
                    throw ExceptionUtil.throwException(ae);
                }
            }
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            ApplicationException ae = new ApplicationException("identity001109");
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public SecurityCredential updateSecurityCredentialStatus(Long securityCredentialId,
                                                             String status, String updateUser)
    {
        if (null == securityCredentialId || StringUtil.isBlank(status))
        {
            logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus(修改密钥状态机信息失败，参数为空)]");
            ApplicationException ae = new ApplicationException("identity001107");
            throw ExceptionUtil.throwException(ae);
        }
        else
        {
            SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialById(securityCredentialId);
            if (BeanUtils.isNotNull(securityCredential))
            {
                securityCredential = updateSecurityCredentialStatus(securityCredential, status,
                    updateUser);
                logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus("
                             + (BeanUtils.isNotNull(securityCredential) ? securityCredential.toString() : "")
                             + ")][返回信息]");
                return securityCredential;
            }
            else
            {
                logger.error("[SecurityCredentialServiceImpl:updateSecurityCredentialStatus(根据Id查询密钥信息失败，没有查到数据)]");
                ApplicationException ae = new ApplicationException("identity001110");
                throw ExceptionUtil.throwException(ae);
            }
        }
    }
}
