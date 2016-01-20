package com.yuecheng.hops.product.entity.property;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Product_Property")
public class ProductProperty implements Serializable {

	private static final long serialVersionUID = 5351026716181807972L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productPropertyIdSeq")
	@SequenceGenerator(name = "productPropertyIdSeq", sequenceName = "PRODUCT_PROPERTY_ID_SEQ")
	@Column(name = "product_property_id")
	protected Long productPropertyId;

	@Column(name = "param_name")
	protected String paramName;
	
	@Column(name = "PARAM_ENGLISH_NAME")
	protected String paramEnglishName;

	@Column(name = "attribute", length = 64)
	protected String attribute;

	@Column(name = "min_length")
	protected Integer minLength;

	@Column(name = "max_length")
	protected Integer maxLength;

	@Column(name = "value", length = 400)
	protected String value;
	
	@Transient
	protected boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getProductPropertyId() {
		return productPropertyId;
	}

	public void setProductPropertyId(Long productPropertyId) {
		this.productPropertyId = productPropertyId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	public String getParamEnglishName() {
		return paramEnglishName;
	}

	public void setParamEnglishName(String paramEnglishName) {
		this.paramEnglishName = paramEnglishName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + (flag ? 1231 : 1237);
		result = prime * result
				+ ((maxLength == null) ? 0 : maxLength.hashCode());
		result = prime * result
				+ ((minLength == null) ? 0 : minLength.hashCode());
		result = prime
				* result
				+ ((paramEnglishName == null) ? 0 : paramEnglishName.hashCode());
		result = prime * result
				+ ((paramName == null) ? 0 : paramName.hashCode());
		result = prime
				* result
				+ ((productPropertyId == null) ? 0 : productPropertyId
						.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductProperty other = (ProductProperty) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (flag != other.flag)
			return false;
		if (maxLength == null) {
			if (other.maxLength != null)
				return false;
		} else if (!maxLength.equals(other.maxLength))
			return false;
		if (minLength == null) {
			if (other.minLength != null)
				return false;
		} else if (!minLength.equals(other.minLength))
			return false;
		if (paramEnglishName == null) {
			if (other.paramEnglishName != null)
				return false;
		} else if (!paramEnglishName.equals(other.paramEnglishName))
			return false;
		if (paramName == null) {
			if (other.paramName != null)
				return false;
		} else if (!paramName.equals(other.paramName))
			return false;
		if (productPropertyId == null) {
			if (other.productPropertyId != null)
				return false;
		} else if (!productPropertyId.equals(other.productPropertyId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
