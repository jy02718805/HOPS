package com.yuecheng.hops.security.service;


import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialType;

public interface SecurityCredentialManagerService
{

    /**
     * 根据登录密码和系统MD5Key进行组装加密
     * 
     * @param pwd
     *            登录密码源串
     * @return
     */

    public String getLoginEncryptKey(String pwd, Long identityId);

    /**
     * 根据js加密密码进行解密并组装加密
     * 
     * @param jsRsaKey
     *            js加密密码
     * @return
     */
    public String getEncryptKeyByJSRSAKey(String jsRsaKey, Long identityId);

    /**
     * 根据登录密码密文进行判断密码是否正确：
     * 
     * @return
     */

    /**
     * 根据时间期限对密匙进行终止
     * 
     * @return
     */
    public void stopSecurityCredential();

    /**
     * 解密该密钥ID对应的密钥获得该密钥明文
     * 
     * @param securityId
     * @return
     */
    public String decryptKeyBySecurityId(Long securityId);

    /**
     * 解密该密钥对象获得该密钥明文
     * 
     * @param securityId
     * @return
     */
    public String decryptKeyBySecurity(SecurityCredential securityCredential);

    /**
     * 公共加密方法
     * 
     * @param key
     *            待加密字符
     * @param encryptType
     *            加密类型
     * @return
     */
    public String encrypt(String key, String encryptType);

    /**
     * 公共解密方法
     * 
     * @param key
     *            待解密字符
     * @param encryptType
     *            解密类型
     * @return
     */
    public String decrypt(String key, String encryptType);

    public void checkSecurity(String SecurityCredentialType, String pwd)
        throws Exception;
    
    
    /**
     * 更新密匙状态信息
     * 
     * @param securityCredential
     *            密匙信息
     * @param status
     *            更新状态
     * @return
     */
    public SecurityCredential updateSecurityCredentialStatus(SecurityCredential securityCredential,
                                                             String status, String updateUser);

    
    /**
     * 更新密匙状态信息
     * 
     * @param securityCredentialId
     *            密匙Id
     * @param status
     *            更新状态
     * @return
     */
    public SecurityCredential updateSecurityCredentialStatus(Long securityCredentialId,
                                                             String status, String updateUser);
    /**
     * 修改密码
     * 
     * @param operator
     * @param oldPwd
     * @param newPwd
     * @param updateUser
     * @return
     */
    public SecurityCredential updateSecurityCredential(Operator operator, String oldPwd,
                                                       String newPwd, String updateUser);
    
    
    /**
     * 重置密码
     * 
     * @param operator
     * @param updateUser
     * @return
     */
    public SecurityCredential resetSecurityCredential(Operator operator, String updateUser);
    
    
    /**
     * 保存登录密码或支付密码
     * 
     * @param identity
     * @param pwd
     * @param updateUser
     * @param passwordType
     *            密码类型（LOGIN_MD5、PAY_MD5）
     * @return
     */
    public SecurityCredential saveSecurityCredential(AbstractIdentity identity, String pwd,
                                                     String updateUser, String passwordType);

    /**
     * 根据条件组装密钥信息并保存
     * 
     * @param identity
     * @param securityName
     * @param securityValue
     * @param updateUser
     * @param securityTyp
     * @return
     */
    public SecurityCredential saveSecurityCredential(AbstractIdentity identity,
                                                     String securityName, String securityValue,
                                                     String updateUser,
                                                     SecurityCredentialType securityTyp);
}
