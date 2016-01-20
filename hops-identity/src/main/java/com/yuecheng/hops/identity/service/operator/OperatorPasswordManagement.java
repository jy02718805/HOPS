package com.yuecheng.hops.identity.service.operator;


import java.util.Date;

import com.yuecheng.hops.identity.entity.operator.Operator;


public interface OperatorPasswordManagement
{

    /**
     * 重新设定密码
     * 
     * @param identityId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public abstract Long resetPassword(Long identityId, String oldPassword, String newPassword,
                                       String updateUserName);

    /**
     * 通过Email充置密码请求
     * 
     * @param operator
     * @return
     */
    public abstract Operator requestResetPasswordEmail(Long operatorId, String UpdateUser);

    /**
     * 激活新密码
     * 
     * @param operator
     * @param token
     * @param date
     * @return
     */
    public abstract boolean activeNewPassword(Operator operator, String token, Date date);

}