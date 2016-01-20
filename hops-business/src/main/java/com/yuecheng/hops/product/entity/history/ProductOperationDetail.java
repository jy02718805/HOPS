package com.yuecheng.hops.product.entity.history;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "product_operation_detail")
public class ProductOperationDetail implements Serializable {

	private static final long serialVersionUID = -7565579431564647729L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productOperationDetailIdSeq")
	@SequenceGenerator(name = "productOperationDetailIdSeq", sequenceName = "PRODUCT_O_DETAIL_ID_SEQ")
	@Column(name = "id")
	private Long id;

	@Column(name = "product_operation_history_id")
	private Long productOperationHistoryId;
	
	@Column(name = "product_relation_id")
	private Long productRelationId;
	
	@Column(name = "product_relation_status", length = 64)
	private String productRelationStatus;
	
	@Column(name = "merchant_type")
	private String merchantType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductOperationHistoryId() {
		return productOperationHistoryId;
	}

	public void setProductOperationHistoryId(Long productOperationHistoryId) {
		this.productOperationHistoryId = productOperationHistoryId;
	}

	public Long getProductRelationId() {
		return productRelationId;
	}

	public void setProductRelationId(Long productRelationId) {
		this.productRelationId = productRelationId;
	}

	public String getProductRelationStatus() {
		return productRelationStatus;
	}

	public void setProductRelationStatus(String productRelationStatus) {
		this.productRelationStatus = productRelationStatus;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	
	
	
}
