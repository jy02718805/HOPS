package com.yuecheng.hops.identity.service.customer;


import java.util.List;

import com.yuecheng.hops.identity.entity.mirror.Person;


/**
 * Person逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-24
 */
public interface PersonService
{
    /**
     * 保存个人信息
     * 
     * @param person
     *            个人信息
     * @return
     */
    public Person savePerson(Person person);

    /**
     * 根据个人信息ID删除个人信息
     * 
     * @param personId
     *            个人信息ID
     */
    public void deletePerson(Long personId);

    /**
     * 根据个人信息ID查找个人信息
     * 
     * @param personId
     *            个人信息ID
     * @return
     */
    public Person findOne(Long personId);

    /**
     * 获取所有个人信息列表
     * 
     * @return
     */
    public List<Person> selectAll();

}
