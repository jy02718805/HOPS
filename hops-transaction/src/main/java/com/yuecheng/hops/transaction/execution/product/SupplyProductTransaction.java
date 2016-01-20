package com.yuecheng.hops.transaction.execution.product;


import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.basic.entity.Order;


public interface SupplyProductTransaction
{
    public SupplyProductRelation getSupplyProductRelation(Order order);
}
