package com.yuecheng.hops.identity.service.customer.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.identity.entity.mirror.Person;
import com.yuecheng.hops.identity.repository.PersonDao;
import com.yuecheng.hops.identity.service.customer.PersonService;


/**
 * Person逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-24
 */

@Service("personService")
public class PersonServiceImpl implements PersonService
{
    @Autowired
    private PersonDao personDao;

    private static Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    /**
     * 添加Person信息
     */
    @Override
    public Person savePerson(Person person)
    {
        logger.debug("[PersonServiceImpl:savePerson(" + (BeanUtils.isNotNull(person)? person.toString() :"")
                                                                                             + ")]");
        if (BeanUtils.isNotNull(person))
        {
            person = personDao.save(person);
            logger.debug("[PersonServiceImpl:savePerson(" + (BeanUtils.isNotNull(person)? person.toString() :"")
                                                                                                 + ")][返回信息]");
        }
        return person;
    }

    /**
     * 删除Person信息
     */
    @Override
    public void deletePerson(Long personId)
    {
        logger.debug("[PersonServiceImpl:deletePerson(" + personId + ")]");
        if (personId != null)
        {
            personDao.delete(personId);
        }
    }

    /**
     * 查找Person信息
     */
    @Override
    public Person findOne(Long personId)
    {
        logger.debug("[PersonServiceImpl:findOne(" + personId + ")]");
        if (personId != null)
        {
            Person person = personDao.findOne(personId);
            logger.debug("[PersonServiceImpl:findOne(" + (BeanUtils.isNotNull(person)? person.toString() :"")
                                                                                              + ")][返回信息]");
            return person;
        }
        ApplicationException ae = new ApplicationException("identity50005");
        throw ExceptionUtil.throwException(ae);
    }

    /**
     * 查找Person信息列表
     */
    @Override
    public List<Person> selectAll()
    {
        logger.debug("[PersonServiceImpl:selectAll()]");
        List<Person> personList = personDao.selectAll();
        logger.debug("[PersonServiceImpl:selectAll(" + (BeanUtils.isNotNull(personList) ? Collections3.convertToString(
            personList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return personList;
    }

}
