/*
 * 文件名：RebateTaskControlDao.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年2月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.rebate.entity.RebateTaskControl;

public interface RebateTaskControlDao extends PagingAndSortingRepository<RebateTaskControl, String>, JpaSpecificationExecutor<RebateTaskControl>{
	@Query("select rtc from RebateTaskControl rtc where rtc.rebateDate =:rebateDate")
    RebateTaskControl getRebateTaskControlByRebateDate(@Param("rebateDate") Date rebateDate);
}
