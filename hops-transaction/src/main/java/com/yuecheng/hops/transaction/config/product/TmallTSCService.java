package com.yuecheng.hops.transaction.config.product;


import java.util.List;

import com.yuecheng.hops.transaction.config.entify.product.TmallTSC;


/**
 * 天猫产品服务层
 * 
 * @author Jinger 2014-03-11
 */
public interface TmallTSCService
{

    /**
     * 获取所有的天猫产品信息
     * 
     * @return
     */
    public List<TmallTSC> getAll();

    /**
     * 根据天猫产品ID获取产品信息
     * 
     * @param tsc
     * @return
     */
    public TmallTSC findOne(String tsc);
}
