package com.yuecheng.hops.transaction.execution.product.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.config.entify.product.AssignExclude;
import com.yuecheng.hops.transaction.config.product.AssignExcludeService;


@Service("assignExcludeFilterAction")
public class AssignExcludeFilterAction
{
    @Autowired
    private AssignExcludeService designExcludeService;

    /**
     * 过滤指定排除的商户
     * 
     * @param ycOrder
     * @param init_uprs
     * @return
     */
    public List<SupplyProductRelation> filter(Order ycOrder, List<SupplyProductRelation> init_uprs)
    {
//        //运营商
//        ycOrder.getExt1();
//        //省份
//        ycOrder.getExt2();
//        //城市
//        ycOrder.getExt3();
//        //面值
//        ycOrder.getExt4();
        Map<String,Object> searchParams = new HashMap<String,Object>();
        searchParams.put(EntityConstant.AssignExclude.CARRIER_NO, ycOrder.getExt1());
        searchParams.put(EntityConstant.AssignExclude.PROVICE_NO, ycOrder.getExt2());
        searchParams.put(EntityConstant.AssignExclude.CITY_NO, ycOrder.getExt3());
        searchParams.put(EntityConstant.AssignExclude.PAR_VALUE, ycOrder.getOrderFee().toString());
        searchParams.put(EntityConstant.AssignExclude.RULE_TYPE, Constant.AssignExclude.RULE_TYPE_ASSIGN);
        searchParams.put(EntityConstant.AssignExclude.MERCHANT_ID, ycOrder.getMerchantId().toString());
        searchParams.put(EntityConstant.AssignExclude.MERCHANT_TYPE,MerchantType.AGENT.toString());
        List<AssignExclude> designs = designExcludeService.getAllAssignExcludeRelation(searchParams);
        
        searchParams = new HashMap<String,Object>();
        searchParams.put(EntityConstant.AssignExclude.CARRIER_NO, ycOrder.getExt1());
        searchParams.put(EntityConstant.AssignExclude.PROVICE_NO, ycOrder.getExt2());
        searchParams.put(EntityConstant.AssignExclude.CITY_NO, ycOrder.getExt3());
        searchParams.put(EntityConstant.AssignExclude.PAR_VALUE, ycOrder.getOrderFee().toString());
        searchParams.put(EntityConstant.AssignExclude.RULE_TYPE, Constant.AssignExclude.RULE_TYPE_EXCLUDE);
        searchParams.put(EntityConstant.AssignExclude.MERCHANT_ID, ycOrder.getMerchantId().toString());
        searchParams.put(EntityConstant.AssignExclude.MERCHANT_TYPE,MerchantType.AGENT.toString());
        List<AssignExclude> excludes = designExcludeService.getAllAssignExcludeRelation(searchParams);
        //定义将排除供货商之后的供货商产品列表
        List<SupplyProductRelation> noExcludes = new ArrayList<SupplyProductRelation>();
        //定义将指定供货商的供货商产品列表
        List<SupplyProductRelation> result = new ArrayList<SupplyProductRelation>();
        //先将排除的供货商从全局供货商产品关系列表中移除
        for(SupplyProductRelation supplyProductRelation : init_uprs)
        {
            boolean flag=isInAssignExclude(excludes,supplyProductRelation);
            if(!flag)
            {
                noExcludes.add(supplyProductRelation);
            }
        }
        //判断是否有指定的供货商
        if(designs.size()>0)
        {
            //找到指定的且不排除的供货商
            for(SupplyProductRelation supplyProductRelation : noExcludes)
            {
                boolean flag=isInAssignExclude(designs,supplyProductRelation);
                if(flag)
                {
                    result.add(supplyProductRelation);
                }
            }
        }else{
            result=noExcludes;
        }
        return result;
    }
    
    public boolean isInAssignExclude(List<AssignExclude> excludes,SupplyProductRelation supplyProductRelation)
    {
        boolean flag=false;
        for (AssignExclude assignExclude : excludes)
        {
            if(supplyProductRelation.getProductId().equals(assignExclude.getProductNo())&&supplyProductRelation.getIdentityId().equals(assignExclude.getObjectMerchantId()))
            {
                flag=true;
            }
        }
        return flag;
    }
}
