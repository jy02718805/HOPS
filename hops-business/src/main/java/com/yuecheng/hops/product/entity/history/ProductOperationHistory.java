package com.yuecheng.hops.product.entity.history;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "product_Operation_history")
public class ProductOperationHistory implements Serializable {

	private static final long serialVersionUID = -7565579431564647729L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productOperationHistoryIdSeq")
	@SequenceGenerator(name = "productOperationHistoryIdSeq", sequenceName = "PRODUCT_O_HISTORY_ID_SEQ")
	@Column(name = "id")
	private Long id;

	@Column(name = "operation_name", length = 50)
	private String operationName;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "carrier_name", length = 64)
	private String carrierName;
	
	@Column(name = "par_value")
	private BigDecimal parValue;
	
	@Column(name = "province", length = 10)
	private String province;
	
	@Column(name = "city", length = 10)
	private String city;
	
	@Column(name = "merchant_type", length = 20)
	private String merchantType;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "operation_flag")
	private String operationFlag;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	
	
}
