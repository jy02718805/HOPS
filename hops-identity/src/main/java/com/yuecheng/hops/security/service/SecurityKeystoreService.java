package com.yuecheng.hops.security.service;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;


/**
 * @ClassName: SecurityKeystoreService
 * @Description: 访问Keystore
 * @author 肖进
 * @date 2014年9月15日 下午1:55:53
 */

public interface SecurityKeystoreService
{
    /**
     * 通过别名得到公钥对象
     * 
     * @param alias
     * @return
     */
    public RSAPublicKey getRSAPublicKey(String alias);

    /**
     * 通过别名得私钥对象：RSAPrivateKey对象
     * 
     * @param alias
     * @return
     */
    public RSAPrivateKey getRSAPrivateKey(String alias);

    /**
     * 通过系统名称取公私钥Map对象
     * 
     * @param constantname
     * @return
     */
    Map<String, Object> getKeyObjectToMap(String constantname);

}
