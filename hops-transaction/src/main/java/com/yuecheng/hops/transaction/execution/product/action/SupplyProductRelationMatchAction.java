package com.yuecheng.hops.transaction.execution.product.action;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.numsection.service.CheckNumSectionService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.config.entify.product.MerchantProductLevel;
import com.yuecheng.hops.transaction.config.product.MerchantProductLevelService;
import com.yuecheng.hops.transaction.execution.product.MerchantProductMatcherTransaction;


@Service("supplyProductRelationMatchAction")
public class SupplyProductRelationMatchAction
{
    @Autowired
    private MerchantProductLevelService       merchantLevelService;

    @Autowired
    private CheckNumSectionService                 checkNumSectionService;

    @Autowired
    private MerchantProductMatcherTransaction merchantProductMatcherTransaction;

    @Autowired
    private ProductService                    productService;

    @Autowired
    private SupplyProductRelationService      supplyProductRelationService;

    public List<SupplyProductRelation> execute(Order order)
    {
        List<SupplyProductRelation> uprs = new ArrayList<SupplyProductRelation>();
        Random r = new Random();
        int radomNum = r.nextInt(100);
        MerchantProductLevel merchantLevel = merchantLevelService.findMerchantProductLevelByRadom(radomNum);

        NumSection numSection = checkNumSectionService.checkNum(order.getUserCode());

        List<AirtimeProduct> apList = null;
        if (order.getOrderStatus().compareTo(Constant.OrderStatus.SUCCESS_PART) == 0)
        {
            // 部分成功订单
            SupplyProductRelation upProduct = merchantProductMatcherTransaction.matchSupplyProduct(
                order.getMerchantId(), numSection,
                order.getProductFace().subtract(order.getOrderSuccessFee()),order.getBusinessType().intValue());
            if (upProduct != null)
            {
                // apList = productService.getParentProducts(upProduct.getProductId());
                apList = productService.getProductTree(numSection.getProvince().getProvinceId(),
                    order.getProductFace().subtract(order.getOrderSuccessFee()).toString(),
                    numSection.getCarrierInfo().getCarrierNo(), numSection.getCity().getCityId(),null);
            }
            else
            {
                return uprs;
            }
        }
        else
		{
			// 正常订单
			apList = productService.getProductTree(numSection.getProvince().getProvinceId(), order.getProductFace()
					.toString(), numSection.getCarrierInfo().getCarrierNo(), numSection.getCity().getCityId(), order
					.getBusinessType().intValue());
        }

        if (merchantLevel != null && CollectionUtils.isNotEmpty(apList))
        {
//            uprs = supplyProductRelationService.matchProduct(numSection, order.getProductFace(), order.getProductSaleDiscount(), merchantLevel.getMerchantLevel(), new BigDecimal(order.getExt4()));
            uprs = supplyProductRelationService.querySupplyProductByProductId(apList,
                merchantLevel.getMerchantLevel(), order.getProductSaleDiscount(), new BigDecimal(
                    order.getExt4()));
            uprs = BeanUtils.isNotNull(uprs)?uprs:new ArrayList<SupplyProductRelation>();
        }
        return uprs;
    }
}
