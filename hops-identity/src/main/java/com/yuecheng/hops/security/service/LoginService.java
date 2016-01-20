package com.yuecheng.hops.security.service;


import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.identity.entity.AbstractIdentity;


public interface LoginService
{
    /**
     * 验证登录密码
     * 
     * @param name
     *            登录名
     * @param pwd
     *            登录密码
     * @param customer
     *            登录用户
     * @return
     */
    public String checkSecurityCredential(String name, String pwd, AbstractIdentity identity);

    /**
     * 用户登录
     * 
     * @param name
     *            登录名
     * @param pwd
     *            密码
     * @return
     */
    public AbstractIdentity logon(String name, String pwd, IdentityType identityType);

    /**
     * 用户注册
     * 
     * @param customer
     *            用户信息
     * @return
     */
    public AbstractIdentity saveRegistPassword(AbstractIdentity identity, String loginPwd,
                                               String payPwd, String updateUser);
}
