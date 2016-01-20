package com.yuecheng.hops.product.entity.history.assist;

import java.io.Serializable;

import com.yuecheng.hops.product.entity.history.ProductOperationHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationHistoryBak;

public class ProductOperationHistoryAssist  implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -3164136029351580225L;

	private ProductOperationHistory ProductOperationHistory;

    private ProductOperationHistoryBak ProductOperationHistoryBak;
    
	private String noUpdateProductName;

    public ProductOperationHistory getProductOperationHistory()
    {
        return ProductOperationHistory;
    }

    public void setProductOperationHistory(ProductOperationHistory productOperationHistory)
    {
        ProductOperationHistory = productOperationHistory;
    }

    public String getNoUpdateProductName()
    {
        return noUpdateProductName;
    }

    public void setNoUpdateProductName(String noUpdateProductName)
    {
        this.noUpdateProductName = noUpdateProductName;
    }

    public ProductOperationHistoryBak getProductOperationHistoryBak()
    {
        return ProductOperationHistoryBak;
    }

    public void setProductOperationHistoryBak(ProductOperationHistoryBak productOperationHistoryBak)
    {
        ProductOperationHistoryBak = productOperationHistoryBak;
    }

	
}
