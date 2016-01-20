package com.yuecheng.hops.transaction.execution.product.action;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant.IdentityStatus;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;


@Service("merchantStatusFilterAction")
public class MerchantStatusFilterAction
{
    @Autowired
    private MerchantService merchantService;

    public List<SupplyProductRelation> filter(List<SupplyProductRelation> sortUprs)
    {
        List<SupplyProductRelation> deleteList = new ArrayList<SupplyProductRelation>();
        for (Iterator<SupplyProductRelation> iterator3 = sortUprs.iterator(); iterator3.hasNext();)
        {
            SupplyProductRelation upProductRelation = iterator3.next();
            Merchant merchant = merchantService.queryMerchantById(upProductRelation.getIdentityId());
            if (IdentityStatus.CLOSE_STATUS.equalsIgnoreCase(merchant.getIdentityStatus().getStatus()))
            {
                deleteList.add(upProductRelation);
            }
        }
        for (SupplyProductRelation supplyProductRelation : deleteList)
        {
            sortUprs.remove(supplyProductRelation);
        }
        return sortUprs;
    }
}
