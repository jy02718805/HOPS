/**
 * @Title: CacheBeanPostProcessor.java
 * @Package com.yuecheng.hops.identity.config
 * @Description: 初始化证书参数 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年9月17日 上午9:20:13
 * @version V1.0
 */

package com.yuecheng.hops.identity.config;


import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.Common;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.security.RSAUtils;
import com.yuecheng.hops.common.utils.BeanUtils;


/**
 * @ClassName: CacheBeanPostProcessor
 * @Description: 初始化证书参数
 * @author 肖进
 * @date 2014年9月17日 上午9:20:13
 */

public class CacheBeanPostProcessor
{
    private static Logger logger = LoggerFactory.getLogger(CacheBeanPostProcessor.class);

    private String keyStoreType;// 格式

    private String keyStoreFile;// 密钥文件

    private String keyStoreName;// 密钥文件

    private String keyStorePassword;// 密码

    private String aliasName;// 别名

    private String aliasPassword;// 别名密码

    /**
     * getter method
     * 
     * @return the keyStoreName
     */

    public String getKeyStoreName()
    {
        return keyStoreName;
    }

    /**
     * setter method
     * 
     * @param keyStoreName
     *            the keyStoreName to set
     */

    public void setKeyStoreName(String keyStoreName)
    {
        this.keyStoreName = keyStoreName;
    }

    public String getKeyStoreType()
    {
        return keyStoreType;
    }

    public void setKeyStoreType(String keyStoreType)
    {
        this.keyStoreType = keyStoreType;
    }

    /**
     * getter method
     * 
     * @return the aliasName
     */

    public String getAliasName()
    {
        return aliasName;
    }

    /**
     * setter method
     * 
     * @param aliasName
     *            the aliasName to set
     */

    public void setAliasName(String aliasName)
    {
        this.aliasName = aliasName;
    }

    /**
     * getter method
     * 
     * @return the keyStoreFile
     */

    public String getKeyStoreFile()
    {
        return keyStoreFile;
    }

    /**
     * setter method
     * 
     * @param keyStoreFile
     *            the keyStoreFile to set
     */

    public void setKeyStoreFile(String keyStoreFile)
    {
        this.keyStoreFile = keyStoreFile;
    }

    /**
     * getter method
     * 
     * @return the keyStorePassword
     */

    public String getKeyStorePassword()
    {
        return keyStorePassword;
    }

    /**
     * setter method
     * 
     * @param keyStorePassword
     *            the keyStorePassword to set
     */

    public void setKeyStorePassword(String keyStorePassword)
    {
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * getter method
     * 
     * @return the aliasPassword
     */

    public String getAliasPassword()
    {
        return aliasPassword;
    }

    /**
     * setter method
     * 
     * @param aliasPassword
     *            the aliasPassword to set
     */

    public void setAliasPassword(String aliasPassword)
    {
        this.aliasPassword = aliasPassword;
    }

    /**
     * 初始化调用的方法
     * 
     * @Title: init
     * @Description: 初始化证书参数
     * @param 设定文件
     * @return void 返回类型
     * @throws
     */
    public void init()
    {
        logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~初始化加载~~~~~~~~~~~~~~~~~~~~begin~~~~~~~~");
        // 公钥key
    	getPublicKey();
        // 私钥key
    	getPrivateKey();
        logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~初始化加载~~~~~~~~~~~~~~~~~~~~end~~~~~~~~");
    }
    
    
    public void getPublicKey()
    {
        // 公钥key
        String publicKey = this.aliasName + Constant.RSACacheKey.RSA_PUBLICKEY;
        String publicKeyObject = this.aliasName + Constant.RSACacheKey.RSA_PUBLICKEY_OBJECT;
        try
        {
        	String publicKeystr = (String)HopsCacheUtil.get(Common.IDENTITY_CACHE, publicKey);
        	RSAPublicKey publicKeyobject = (RSAPublicKey)HopsCacheUtil.get(Common.IDENTITY_CACHE, publicKeyObject);
        	if(StringUtils.isBlank(publicKeystr)||BeanUtils.isNull(publicKeyobject))
        	{
	            KeyStore ks = KeyStore.getInstance(this.keyStoreType);
	            String keystorefilepath = getClass().getResource("/").getFile().toString()
	                                      + "keystone/" + this.keyStoreName;
	            FileInputStream fis = new FileInputStream(keystorefilepath);
	            ks.load(fis, this.keyStorePassword.toCharArray());
	
	            // /公钥
	            Certificate cert = ks.getCertificate(this.aliasName);
	
	            RSAPublicKey pubkey = (RSAPublicKey)cert.getPublicKey();
	
	            HopsCacheUtil.put(Common.IDENTITY_CACHE, publicKey, RSAUtils.getKeyString(pubkey));
	            HopsCacheUtil.put(Common.IDENTITY_CACHE, publicKeyObject, pubkey);
	            
	            logger.info("[" + this.keyStoreName + "通过pfx文件" + this.aliasName
	                    + "别名得到公钥字符串：public key = " + RSAUtils.getKeyString(pubkey) + "]");
	            logger.info("[" + this.keyStoreName + "通过pfx文件" + this.aliasName
                    + "别名得到公钥对象：public key Object = " + pubkey + "]");
        	}

        }
        catch (Exception e)
        {
        	logger.error("CacheBeanPostProcessor:[getPublicKey]["+e.getMessage()+"]");
        	HopsCacheUtil.remove(Common.IDENTITY_CACHE, publicKey);
        	HopsCacheUtil.remove(Common.IDENTITY_CACHE, publicKeyObject);
        }
    }
    
    
    public void getPrivateKey()
    {
        // 私钥key
        String privateKey = this.aliasName + Constant.RSACacheKey.RSA_PRIVATEKEY;
        String privateKeyObject = this.aliasName + Constant.RSACacheKey.RSA_PRIVATEKEY_OBJECT;
        try
        {
        	String privateKeystr = (String)HopsCacheUtil.get(Common.IDENTITY_CACHE, privateKey);
        	RSAPublicKey privateKeyobject = (RSAPublicKey)HopsCacheUtil.get(Common.IDENTITY_CACHE, privateKeyObject);
        	if(StringUtils.isBlank(privateKeystr)||BeanUtils.isNull(privateKeyobject))
        	{
	            KeyStore ks = KeyStore.getInstance(this.keyStoreType);
	            String keystorefilepath = getClass().getResource("/").getFile().toString()
	                                      + "keystone/" + this.keyStoreName;
	            FileInputStream fis = new FileInputStream(keystorefilepath);
	            ks.load(fis, this.keyStorePassword.toCharArray());
	
	            // 私钥
	            RSAPrivateKey prikey = (RSAPrivateKey)ks.getKey(this.aliasName,
	                this.aliasPassword.toCharArray());
	            HopsCacheUtil.put(Common.IDENTITY_CACHE, privateKey, RSAUtils.getKeyString(prikey));
	            HopsCacheUtil.put(Common.IDENTITY_CACHE, privateKeyObject, prikey);
	            
	            logger.info("[" + this.keyStoreName + "通过pfx文件" + this.aliasName
	                    + "别名得到私钥字符串：private key = " + RSAUtils.getKeyString(prikey) + "]");
	            logger.info("[" + this.keyStoreName + "通过pfx文件" + this.aliasName
	                    + "别名得到私钥对象：private key Object= " + prikey + "]");
        	}
        }
        catch (Exception e)
        {
        	logger.error("CacheBeanPostProcessor:[getPrivateKey]["+e.getMessage()+"]");
        	HopsCacheUtil.remove(Common.IDENTITY_CACHE, privateKey);
        	HopsCacheUtil.remove(Common.IDENTITY_CACHE, privateKeyObject);
        }
    }
}
