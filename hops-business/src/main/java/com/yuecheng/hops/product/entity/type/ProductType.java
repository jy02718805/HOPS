package com.yuecheng.hops.product.entity.type;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yuecheng.hops.product.entity.property.ProductProperty;

@Entity
@Table(name = "Product_Type")
public class ProductType implements Serializable {
	private static final long serialVersionUID = 2203310515976206724L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productTypeIdSeq")
	@SequenceGenerator(name = "productTypeIdSeq", sequenceName = "PRODUCT_TYPE_ID_SEQ")
	@Column(name = "type_id")
	protected Long typeId;

	@Column(name = "product_type_name", length = 64)
	protected String productTypeName;

	@Column(name = "product_type_status", length = 10)
	protected String productTypeStatus;
	
	@Column(name = "business_type", length = 1)
	protected int businessType;

	@Transient
	protected List<ProductProperty> propertyTypes;

	
	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}

	// protected PriceStrategy priceStrategy;
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductTypeStatus() {
		return productTypeStatus;
	}

	public void setProductTypeStatus(String productTypeStatus) {
		this.productTypeStatus = productTypeStatus;
	}

	public List<ProductProperty> getPropertyTypes() {
		return propertyTypes;
	}

	public void setPropertyTypes(List<ProductProperty> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}

}
