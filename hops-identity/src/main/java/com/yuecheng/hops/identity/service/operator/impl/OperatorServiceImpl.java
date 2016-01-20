package com.yuecheng.hops.identity.service.operator.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.mirror.Person;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.repository.OperatorDao;
import com.yuecheng.hops.identity.service.customer.PersonService;
import com.yuecheng.hops.identity.service.impl.IdentityServiceImpl;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.operator.OperatorService;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.security.service.LoginService;


@Service("operatorService")
public class OperatorServiceImpl extends IdentityServiceImpl implements OperatorService
{
    @Autowired
    OperatorDao operatorDao;

    @Autowired
    PersonService personService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    SpService spService;

    @Autowired
    LoginService loginService;

    static Logger logger = LoggerFactory.getLogger(OperatorServiceImpl.class);

    /**
     * 编辑操作员基本信息
     */
    @Override
    public Operator editOperatorInfo(Operator operator)
    {
        try
        {
            String[] msgParams = new String[] {"editOperatorInfo"};
            logger.debug("OperatorServiceImpl:editOperatorInfo(" + (BeanUtils.isNotNull(operator) ? operator.toString() :"")
                                                                                                            + ")");
            Long id = operator.getId();
            String birthday = operator.getPerson().getBirthday();
            Operator persist = operatorDao.findOne(id);
            if (BeanUtils.isNotNull(persist))
            {
                Person person = personService.findOne(persist.getPerson().getPersonId());
                person.setBirthday(birthday);
                Person personNew = personService.savePerson(person);
                if (BeanUtils.isNotNull(personNew))
                {
                    persist.setDisplayName(operator.getDisplayName());
                    persist.setLastUpdateDate(operator.getLastUpdateDate());
                    persist.setLastUpdateUser(operator.getLastUpdateUser());
                    persist = operatorDao.save(persist);
                    logger.debug("OperatorServiceImpl:editOperatorInfo(" + (BeanUtils.isNotNull(persist) ? persist.toString() :"")
                                                                                                                  + ")[返回信息]");
                    return persist;
                }
                else
                {
                    logger.error("[OperatorServiceImpl:editOperatorInfo()]");
                    ApplicationException ae = new ApplicationException("identity001047", msgParams);
                    throw ExceptionUtil.throwException(ae);
                }
            }
            else
            {
                logger.error("[OperatorServiceImpl:editOperatorInfo()]");
                ApplicationException ae = new ApplicationException("identity001046", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[OperatorServiceImpl:editOperatorInfo(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"editOperatorInfo"};
            ApplicationException ae = new ApplicationException("identity001029", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<Operator> queryOperatorByMerchant(Long merchantId)
    {
        logger.debug("OperatorServiceImpl:queryOperatorByMerchant(" + merchantId + ")");
        List<Operator> operatorList = operatorDao.getOperatorByMerchant(merchantId);
        logger.debug("OperatorServiceImpl:queryOperatorByMerchant(" + (BeanUtils.isNotNull(operatorList) ? Collections3.convertToString(
            operatorList, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
        return operatorList;

    }

    @Override
    public YcPage<Operator> queryOperator(Map<String, Object> searchParams, int pageNumber,
                                          int pageSize, BSort bsort,IdentityType identityType)
    {
        try{
            logger.debug("OperatorServiceImpl:queryOperator(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                                 + ")");
            Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
            String orderCloumn = bsort == null ? EntityConstant.Identity.IDENTITY_ID : bsort.getCloumn();
            String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
            Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
            if(BeanUtils.isNotNull(identityType))
            {
                filters.put(EntityConstant.Identity.OWNER_IDENTITY_TYPE, new SearchFilter(
                    EntityConstant.Identity.OWNER_IDENTITY_TYPE, org.springside.modules.persistence.SearchFilter.Operator.EQ, identityType));
            }
            YcPage<Operator> ycPage = PageUtil.queryYcPage(operatorDao, filters, pageNumber, pageSize,
                sort, Operator.class);
            List<Operator> list = queryOperatorName(ycPage.getList());
            logger.debug("OperatorServiceImpl:queryOperator(" + (BeanUtils.isNotNull(list) ? Collections3.convertToString(
                list, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
            return ycPage;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }catch(Exception e)
        {
            logger.error("[OperatorServiceImpl:queryOperator(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryOperator"};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("identity001111", msgParams,viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    public List<Operator> queryOperatorName(List<Operator> list)
    {
        try
        {
            logger.debug("OperatorServiceImpl:getOperatorName(" + (BeanUtils.isNotNull(list) ? list.toString() :"")
                                                                                                   + ")");
            for (Operator operator : list)
            {
                if (operator.getOwnerIdentityType().equals(IdentityType.SP))
                {
                    SP sp = spService.getSP();
                    operator.setOwnerIdentityName(sp.getSpName());
                }
                else if (operator.getOwnerIdentityType().equals(IdentityType.MERCHANT))
                {
                    Merchant merchant = merchantService.queryMerchantById(operator.getOwnerIdentityId());
                    operator.setOwnerIdentityName(merchant.getMerchantName());
                }
            }
            logger.debug("OperatorServiceImpl:getOperatorName(" + (BeanUtils.isNotNull(list) ? Collections3.convertToString(
                list, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
            return list;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[OperatorServiceImpl:getOperatorName(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getOperatorName"};
            ApplicationException ae = new ApplicationException("identity001033", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public Operator queryOperatorByPersonId(Long personId)
    {
        logger.debug("OperatorServiceImpl:queryOperatorByPersonId(" + personId + ")");
        Operator operator = operatorDao.getOperatorByPersonId(personId);
        logger.debug("OperatorServiceImpl:getOperatorName(" + (BeanUtils.isNotNull(operator) ? operator.toString() :"")
                                                                                                       + ")[返回信息]");
        return operator;
    }

    @Override
    public Operator queryOperatorByOperatorName(String operatorName)
    {
        try
        {
            logger.debug("[OperatorServiceImpl:queryOperatorByOperatorName(" + operatorName + ")]");
            Operator operator = operatorDao.getOperatorByOperatorName(operatorName);
            logger.debug("OperatorServiceImpl:queryOperatorByOperatorName(" + (BeanUtils.isNotNull(operator) ? operator.toString() :"")
                                                                                                                       + ")[返回信息]");
            return operator;
        }
        catch (Exception e)
        {
            logger.error("[OperatorServiceImpl:queryOperatorByOperatorName(" + ExceptionUtil.getStackTraceAsString(e)
                         + ")]");
            String[] msgParams = new String[] {"queryOperatorByOperatorName"};
            ApplicationException ae = new ApplicationException("identity50006", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public Operator regist(Operator operator, String loginPwd, String payPwd, String updateUser)
    {
        logger.debug("[OperatorServiceImpl:regist(" + (BeanUtils.isNotNull(operator) ? operator.toString() :"")
                                                                                               + ","
                                                                                               + loginPwd
                                                                                               + ","
                                                                                               + payPwd
                                                                                               + ","
                                                                                               + updateUser
                                                                                               + ")]");
        operator = (Operator)saveIdentity(operator, updateUser);
        // 注册密码保存（登录密码和支付密码）
        loginService.saveRegistPassword(operator, loginPwd, payPwd, updateUser);
        logger.debug("[OperatorServiceImpl:regist(" + (BeanUtils.isNotNull(operator) ? operator.toString() : "")
                                                                                               + ")][返回信息]");
        return operator;
    }
}
