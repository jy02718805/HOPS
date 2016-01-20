package com.yuecheng.hops.blacklist.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.blacklist.entity.Blacklist;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;


/**
 * 黑名单服务接口
 * 
 * @author yao
 * @version 2015年5月25日
 * @see BlacklistService
 * @since
 */
public interface BlacklistService
{
    public Blacklist saveBlacklist(Blacklist blacklist);

    public void deleteBlacklist(Long id);

    /*
     * //根据黑名单号码查找 public Blacklist findOne(String blacklistNo);
     */
    // 根据黑名单ID查找
    public Blacklist findOne(Long blacklistId);

    // 根据黑名单号码和业务类型查找
    public Blacklist findOne(String blacklistNo, String businessType);

    public List<Blacklist> selectAll();

    public YcPage<Blacklist> queryBlacklist(Map<String, Object> searchParams,
            int pageNumber, int pageSize, BSort bsort);

}
