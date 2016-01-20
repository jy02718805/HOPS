package com.yuecheng.hops.security.service.impl;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.Common;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.config.CacheBeanPostProcessor;
import com.yuecheng.hops.security.service.SecurityKeystoreService;

/**
 * @ClassName: SecurityKeystoreServiceImpl
 * @Description: TODO
 * @author 肖进
 * @date 2014年9月15日 下午2:20:25
 */
@Service("securitykeystoreservice")
public class SecurityKeystoreServiceImpl implements SecurityKeystoreService
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityKeystoreServiceImpl.class);
    
    @Autowired
    CacheBeanPostProcessor cacheBeanPostProcessor;

    @Override
    public HashMap<String, Object> getKeyObjectToMap(String constantname)
    {
        try
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            logger.debug("[SecurityKeystoreServiceImpl: getKeyObjectToMap()][通过系统名称："
                         + constantname + "取Map对象：Map<String, Object>对象:]");
            // 通过key得到公钥对象
            String publicKey = constantname + Constant.RSACacheKey.RSA_PUBLICKEY;
            String publicKeystr = (String)HopsCacheUtil.get(Common.IDENTITY_CACHE, publicKey);
            if(StringUtils.isBlank(publicKeystr))
            {
            	logger.debug("重新执行init方法，获取公钥");
            	cacheBeanPostProcessor.getPublicKey();
            	publicKeystr = (String)HopsCacheUtil.get(Common.IDENTITY_CACHE, publicKey);
            }
            logger.debug("[SecurityKeystoreServiceImpl: getKeyObjectToMap()][getKeyObjectToMap-------publicKeystr="
                         + publicKeystr + "]");
            map.put(Constant.RSACacheKey.RSA_PUBLICKEY, publicKeystr);

            // 通过key得到私钥对象
            String privateKey = constantname + Constant.RSACacheKey.RSA_PRIVATEKEY;
            String privateKeystr = (String)HopsCacheUtil.get(Common.IDENTITY_CACHE, privateKey);
            if(StringUtils.isBlank(publicKeystr))
            {
            	logger.debug("重新执行init方法，获取私钥");
            	cacheBeanPostProcessor.getPrivateKey();
            	publicKeystr = (String)HopsCacheUtil.get(Common.IDENTITY_CACHE, privateKey);
            }
            logger.debug("[SecurityKeystoreServiceImpl: getKeyObjectToMap()][getKeyObjectToMap-------privateKeystr="
                         + privateKeystr + "]");
            map.put(Constant.RSACacheKey.RSA_PRIVATEKEY, privateKeystr);
            return map;
        }
        catch (Exception e)
        {
            logger.error("[SecurityKeystoreServiceImpl: getKeyObjectToMap(根据别名获取公私钥Map对象失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"getKeyObjectToMap"};
            ApplicationException ae = new ApplicationException("identity001096", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public RSAPublicKey getRSAPublicKey(String constantname)
    {
        try
        {
            logger.debug("[SecurityKeystoreServiceImpl: getRSAPublicKey()][通过系统名称：" + constantname
                         + "取公钥对象：RSAPublicKey对象:]");
            // 通过key得到公钥对象
            String publicKeyName = constantname + Constant.RSACacheKey.RSA_PUBLICKEY_OBJECT;
            RSAPublicKey publicKey = (RSAPublicKey)HopsCacheUtil.get(Common.IDENTITY_CACHE,publicKeyName);
            if(BeanUtils.isNull(publicKey))
            {
            	logger.debug("重新执行init方法，获取公钥对象");
            	cacheBeanPostProcessor.getPublicKey();
            	publicKey = (RSAPublicKey)HopsCacheUtil.get(Common.IDENTITY_CACHE,publicKeyName);
            }
            logger.debug("[SecurityKeystoreServiceImpl: getRSAPublicKey()][getRSAPublicKey--------------publicKey="
                         + publicKey + "]");
            return publicKey;
        }
        catch (Exception e)
        {
            logger.error("[SecurityKeystoreServiceImpl: getRSAPublicKey(根据别名获取公钥对象失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"getRSAPublicKey"};
            ApplicationException ae = new ApplicationException("identity001094", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public RSAPrivateKey getRSAPrivateKey(String constantname)
    {
        try
        {
            logger.debug("[SecurityKeystoreServiceImpl: getRSAPrivateKey()][通过系统名称："
                         + constantname + "取私钥对象：RSAPrivateKey对象:]");

            // 通过key得到私钥对象
            String privateKeyName = constantname + Constant.RSACacheKey.RSA_PRIVATEKEY_OBJECT;
            RSAPrivateKey privateKey = (RSAPrivateKey)HopsCacheUtil.get(Common.IDENTITY_CACHE,privateKeyName);
            if(BeanUtils.isNull(privateKey))
            {
            	logger.debug("重新执行init方法，获取私钥对象");
            	cacheBeanPostProcessor.getPrivateKey();
            	privateKey = (RSAPrivateKey)HopsCacheUtil.get(Common.IDENTITY_CACHE,privateKeyName);
            }
            logger.debug("[SecurityKeystoreServiceImpl: getRSAPrivateKey()][getRSAPrivateKey--------------privateKey="
                         + privateKey + "]");
            return privateKey;
        }
        catch (Exception e)
        {
            logger.error("[SecurityKeystoreServiceImpl: getRSAPrivateKey(根据别名获取私钥对象失败)] [异常:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            String[] msgParams = new String[] {"getRSAPrivateKey"};
            ApplicationException ae = new ApplicationException("identity001095", msgParams);
            throw ExceptionUtil.throwException(ae);
        }

    }

}
