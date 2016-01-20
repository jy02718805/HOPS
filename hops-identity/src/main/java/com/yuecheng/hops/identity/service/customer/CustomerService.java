package com.yuecheng.hops.identity.service.customer;


import com.yuecheng.hops.identity.entity.customer.Customer;
import com.yuecheng.hops.identity.service.IdentityService;


/**
 * Customer逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-24
 */
public interface CustomerService extends IdentityService
{
    /**
     * 根据个人基本信息ID查找用户
     * 
     * @param personId
     *            个人基本信息ID
     * @return
     */
    public Customer queryCustomerByPersonId(Long personId);

    /**
     * 根据用户名查找用户
     * 
     * @param personName
     *            用户名
     * @return
     */
    public Customer queryCustomerByCustomerName(String customerName);

    /**
     * 用户注册
     * 
     * @param customer
     * @param loginPwd
     * @param payPwd
     * @param updateUser
     * @return
     */
    public Customer regist(Customer customer, String loginPwd, String payPwd, String updateUser);
}
