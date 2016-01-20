package com.yuecheng.hops.identity.service.merchant;


import java.util.List;

import com.yuecheng.hops.identity.entity.merchant.Merchant;


public interface MerchantStatusManagement
{
    /**
     * 根据返佣状态获取商户列表
     * 
     * @param isRebate
     * @return
     */
    public List<Merchant> queryMerchantByIsRebate(boolean isRebate);

}