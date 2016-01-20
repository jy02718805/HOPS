package com.yuecheng.hops.product.entity.history.assist;

import java.io.Serializable;

import com.yuecheng.hops.product.entity.history.ProductOperationRule;
import com.yuecheng.hops.product.entity.history.ProductOperationRuleBak;

public class ProductOperationRuleAssist  implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -3164136029351580225L;

	private ProductOperationRule productOperationRule;
	
    private ProductOperationRuleBak productOperationRuleBak;
	
	private String merchantName;

    public ProductOperationRule getProductOperationRule()
    {
        return productOperationRule;
    }

    public void setProductOperationRule(ProductOperationRule productOperationRule)
    {
        this.productOperationRule = productOperationRule;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public ProductOperationRuleBak getProductOperationRuleBak()
    {
        return productOperationRuleBak;
    }

    public void setProductOperationRuleBak(ProductOperationRuleBak productOperationRuleBak)
    {
        this.productOperationRuleBak = productOperationRuleBak;
    }
	
}
