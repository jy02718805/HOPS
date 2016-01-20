package com.yuecheng.hops.product.entity.type.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Product_Type_Relation")
public class ProductTypeRelation implements Serializable {

	private static final long serialVersionUID = -6055932030053470209L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productTypeRelationIdSeq")
	@SequenceGenerator(name = "productTypeRelationIdSeq", sequenceName = "PRODUCT_TYPE_REL_ID_SEQ")
	@Column(name = "id")
	public Long id;

	@Column(name = "product_property_id")
	public Long productPropertyId;

	@Column(name = "type_id")
	public Long typeId;

	public Long getProductPropertyId() {
		return productPropertyId;
	}

	public void setProductPropertyId(Long productPropertyId) {
		this.productPropertyId = productPropertyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

}
