package com.yuecheng.hops.identity.service.customer.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.customer.Customer;
import com.yuecheng.hops.identity.repository.CustomerDao;
import com.yuecheng.hops.identity.service.customer.CustomerService;
import com.yuecheng.hops.identity.service.customer.PersonService;
import com.yuecheng.hops.identity.service.impl.IdentityServiceImpl;
import com.yuecheng.hops.security.service.LoginService;


/**
 * Customer逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-24
 */

@Service("customerService")
public class CustomerServiceImpl extends IdentityServiceImpl implements CustomerService
{
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PersonService personService;

    @Autowired
    private LoginService loginService;

    static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    /**
     * 根据personId获取customer
     */
    @Override
    public Customer queryCustomerByPersonId(Long personId)
    {
        logger.debug("[CustomerServiceImpl:queryCustomerByPersonId(" + personId + ")]");
        Customer customer = customerDao.getCustomerByPersonId(personId);
        logger.debug("[CustomerServiceImpl:queryCustomerByPersonId(" + (BeanUtils.isNotNull(customer)? customer.toString() :"")
                                                                                                                + ")][返回信息]");
        return customer;
    }

    /**
     * 根据用户名获取customer
     */
    @Override
    public Customer queryCustomerByCustomerName(String customerName)
    {
        try
        {
            logger.debug("[CustomerServiceImpl:queryCustomerByCustomerName(" + customerName + ")]");
            if (StringUtil.isNotBlank(customerName))
            {
                Customer customer = customerDao.queryCustomerByCustomerName(customerName);
                logger.debug("[CustomerServiceImpl:queryCustomerByCustomerName(" + (BeanUtils.isNotNull(customer)? customer.toString() :"")
                                                                                                                            + ")][返回信息]");
                return customer;
            }
            else
            {
                logger.debug("[CustomerServiceImpl:queryCustomerByCustomerName(用户名不能为空)][返回信息]");
                String[] msgParams = new String[] {customerName};
                ApplicationException e = new ApplicationException("identity001001", msgParams);
                throw ExceptionUtil.throwException(e);
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception ex)
        {
            logger.error("[CustomerServiceImpl:queryCustomerByCustomerName(" + ex.getMessage()
                         + ")]");
            ApplicationException e = new ApplicationException("identity001002", ex);
            throw ExceptionUtil.throwException(e);
        }

    }

    @Override
    public Customer regist(Customer customer, String loginPwd, String payPwd, String updateUser)
    {
        logger.debug("[CustomerServiceImpl:regist(" + customer != null ? customer.toString() :""
                                                                                               + ","
                                                                                               + loginPwd
                                                                                               + ","
                                                                                               + payPwd
                                                                                               + ","
                                                                                               + updateUser
                                                                                               + ")]");
        customer = (Customer)saveIdentity(customer, updateUser);
        // 注册密码保存（登录密码和支付密码）
        loginService.saveRegistPassword(customer, loginPwd, payPwd, updateUser);
        logger.debug("[CustomerServiceImpl:regist(" + (BeanUtils.isNotNull(customer)? customer.toString() :"")
                                                                                               + ")][返回信息]");
        return customer;
    }
}
