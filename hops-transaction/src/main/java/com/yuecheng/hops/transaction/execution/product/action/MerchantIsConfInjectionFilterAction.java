package com.yuecheng.hops.transaction.execution.product.action;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;


@Service("merchantIsConfInjectionFilterAction")
public class MerchantIsConfInjectionFilterAction
{
    @Autowired
    private InterfaceService interfaceService;

    public List<SupplyProductRelation> filter(List<SupplyProductRelation> sortUprs)
    {
        List<SupplyProductRelation> deleteList = new ArrayList<SupplyProductRelation>();
        for (Iterator<SupplyProductRelation> iterator3 = sortUprs.iterator(); iterator3.hasNext();)
        {
            SupplyProductRelation upProductRelation = iterator3.next();
            Boolean checkSendOrder = interfaceService.checkInterfaceIsExist(upProductRelation.getIdentityId(), Constant.Interface.INTERFACE_TYPE_SEND_ORDER);
            Boolean checkQueryOrder = interfaceService.checkInterfaceIsExist(upProductRelation.getIdentityId(), Constant.Interface.INTERFACE_TYPE_QUERY_ORDER);
            if (!checkSendOrder || !checkQueryOrder)
            {
                deleteList.add(upProductRelation);
            }
        }
        sortUprs.removeAll(deleteList);
        return sortUprs;
    }
    
    public List<SupplyProductRelation> filterFlow(List<SupplyProductRelation> sortUprs)
    {
        List<SupplyProductRelation> deleteList = new ArrayList<SupplyProductRelation>();
        for (Iterator<SupplyProductRelation> iterator3 = sortUprs.iterator(); iterator3.hasNext();)
        {
            SupplyProductRelation upProductRelation = iterator3.next();
            Boolean checkSendOrderFlow = interfaceService.checkInterfaceIsExist(upProductRelation.getIdentityId(), Constant.Interface.INTERFACE_TYPE_SUPPLY_SEND_ORDER_FLOW);
            Boolean checkQueryOrderFlow = interfaceService.checkInterfaceIsExist(upProductRelation.getIdentityId(), Constant.Interface.INTERFACE_TYPE_SUPPLY_QUERY_ORDER_FLOW);
            if (!checkSendOrderFlow|| !checkQueryOrderFlow)
            {
                deleteList.add(upProductRelation);
            }
        }
        sortUprs.removeAll(deleteList);
        return sortUprs;
    }
}
