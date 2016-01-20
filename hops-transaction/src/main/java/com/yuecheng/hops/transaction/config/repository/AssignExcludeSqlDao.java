/*
 * 文件名：AssignExcludeSqlDao.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015-4-14
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.config.repository;

import java.util.List;
import java.util.Map;

import com.yuecheng.hops.transaction.config.entify.product.AssignExclude;


public interface AssignExcludeSqlDao
{
    List<AssignExclude> getAssignExcludeByParams(Map<String, Object> searchParams);
}
