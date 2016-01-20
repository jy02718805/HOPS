package com.yuecheng.hops.identity.service.operator;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.IdentityService;


/**
 * 操作员服务类
 * 
 * @author xwj
 */
public interface OperatorService extends IdentityService
{
    /**
     * 根据个人信息Id查找操作员
     * 
     * @param personId
     *            个人信息ID
     * @return
     */
    public Operator queryOperatorByPersonId(Long personId);

    /**
     * 根据个人信息Name查找操作员
     * 
     * @param personId
     *            个人信息ID
     * @return
     */
    public Operator queryOperatorByOperatorName(String operatorName);

    /**
     * 编辑操作员的基本信息 基本信息包括：昵称、性别、出生年月、邮箱、手机号码、qq信息
     * 
     * @param operator
     * @return
     */
    public Operator editOperatorInfo(Operator operator);

    /**
     * 查询指定商户下的所有管理
     * 
     * @param merchantId
     * @return
     */
    public List<Operator> queryOperatorByMerchant(Long merchantId);

    /**
     * 根据条件获取操作员列表
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @return
     */
    public YcPage<Operator> queryOperator(Map<String, Object> searchParams, int pageNumber,
                                          int pageSize, BSort sort,IdentityType identityType);

    /**
     * 操作员注册
     * 
     * @param operator
     * @param loginPwd
     * @param payPwd
     * @param updateUser
     * @return
     */
    public Operator regist(Operator operator, String loginPwd, String payPwd, String updateUser);
}
