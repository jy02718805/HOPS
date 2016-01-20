package com.yuecheng.hops.blacklist.service;


import com.yuecheng.hops.blacklist.entity.Blacklist;


/**
 * 检查黑名单接口类
 * 
 * @author yao
 * @version 2015年5月26日
 * @see CheckBlacklistService
 * @since
 */
public interface CheckBlacklistService
{
    // 根据黑名单号码和业务类型进行检查
    public Blacklist checkNum(String blacklistNo, String businessType);
}
