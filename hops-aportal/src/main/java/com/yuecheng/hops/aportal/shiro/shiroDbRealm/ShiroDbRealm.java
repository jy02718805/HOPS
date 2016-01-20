// /*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements. See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership. The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied. See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
package com.yuecheng.hops.aportal.shiro.shiroDbRealm;


import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.operator.OperatorService;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.service.IdentityRoleQueryService;
import com.yuecheng.hops.privilege.service.RolePrivilegeQueryService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;


@Service("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm
{
    private OperatorService operatorService;

    private SecurityCredentialService securityCredentialService;

    private IdentityRoleQueryService identityRoleQueryService;

    private RolePrivilegeQueryService rolePrivilegeQueryService;

    private SecurityCredentialManagerService securityCredentialManagerService;

    public void setSecurityCredentialManagerService(SecurityCredentialManagerService securityCredentialManagerService)
    {
        this.securityCredentialManagerService = securityCredentialManagerService;
    }

    public void setOperatorService(OperatorService operatorService)
    {
        this.operatorService = operatorService;
    }

    public void setSecurityCredentialService(SecurityCredentialService securityCredentialService)
    {
        this.securityCredentialService = securityCredentialService;
    }

    public void setIdentityRoleQueryService(IdentityRoleQueryService identityRoleQueryService)
    {
        this.identityRoleQueryService = identityRoleQueryService;
    }

    public void setRolePrivilegeQueryService(RolePrivilegeQueryService rolePrivilegeQueryService)
    {
        this.rolePrivilegeQueryService = rolePrivilegeQueryService;
    }

    /**
     * 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
        throws AuthenticationException
    {
        Operator operator = (Operator)principals.getPrimaryPrincipal();
        if (operator != null)
        {
            List<Role> roleList = identityRoleQueryService.queryRoleByIdentity(operator.getId(),
                IdentityType.OPERATOR);
            if (roleList != null && roleList.size() != 0)
            {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                for (Role role : roleList)
                {
                    List<String> permissions = rolePrivilegeQueryService.queryPermissionsByRoleId(role.getRoleId());
                    info.addRole(role.getRoleType());
                    info.addStringPermissions(permissions);
                }
                return info;
            }
            else
            {
                throw new AuthenticationException("授权未通过，该用户没有角色！");
            }
        }
        else
        {
            throw new AuthenticationException("授权未通过，认证用户信息不存在！");
        }
    }

    /**
     * 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
        throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        String userName = token.getUsername();
        String pwd = null;
        if (!StringUtil.isNullOrEmpty(userName))
        {
            Operator operator = operatorService.queryOperatorByOperatorName(userName);
            if (operator != null)
            {
                if (Constant.IdentityStatus.CLOSE_STATUS.equals(operator.getIdentityStatus().getStatus()))
                {
                    throw new LockedAccountException("认证未通过，操作员状态未开启！");
                }
                SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialByParam(
                    operator.getId(), IdentityType.OPERATOR,
                    Constant.SecurityCredentialType.PASSWORD, null);
                if (Constant.SecurityCredentialStatus.DISABLE_STATUS.equals(securityCredential.getStatus()))
                {
                    throw new LockedAccountException("认证未通过，操作员密码被禁用！");
                }
                else if (Constant.SecurityCredentialStatus.DELETE_STATUS.equals(securityCredential.getStatus()))
                {
                    throw new LockedAccountException("认证未通过，操作员密码被删除！");
                }
                else if (Constant.SecurityCredentialStatus.EXPIRATION_STATUS.equals(securityCredential.getStatus()))
                {
                    throw new LockedAccountException("认证未通过，操作员密码已过期！");
                }
                if (null == token.getPassword())
                {
                    throw new AuthenticationException("认证未通过，密码为空！");
                }
                pwd = String.valueOf(token.getPassword());
                // 解密
//                pwd = securityCredentialManagerService.decrypt(pwd,
//                    Constant.EncryptType.ENCRYPT_TYPE_JSRSA);
                String loginPwd = securityCredential.getSecurityValue();
                String md5Password = securityCredentialManagerService.getEncryptKeyByJSRSAKey(pwd,
                    operator.getId());
                if (!md5Password.equals(loginPwd))
                {
                    throw new IncorrectCredentialsException("认证未通过，密码不正确！");
                }
                token.setPassword(md5Password.toCharArray());
                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    operator, loginPwd, getName());
                return simpleAuthenticationInfo;
            }
            else
            {
                throw new UnknownAccountException("认证未通过，操作员不存在!");
            }
        }
        else
        {
            throw new AuthenticationException("认证未通过，用户名为空！");
        }
    }

}
