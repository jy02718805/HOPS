/*
 * 文件名：Specification.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年10月15日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.common.query;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public interface Specification<T>
{
    boolean isSatisfiedBy(T t);

    Predicate toPredicate(Root<T> root, CriteriaBuilder cb);

    Class<T> getType();
}
