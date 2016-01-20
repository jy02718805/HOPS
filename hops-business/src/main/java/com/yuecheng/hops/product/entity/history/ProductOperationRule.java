package com.yuecheng.hops.product.entity.history;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "product_operation_rule")
public class ProductOperationRule  implements Serializable {
	
	private static final long serialVersionUID = -8769748225703744141L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productOperationRuleIdSeq")
	@SequenceGenerator(name = "productOperationRuleIdSeq", sequenceName = "PRODUCT_O_RULE_ID_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "history_id")
	private Long historyId;
	
	@Column(name = "merchant_id")
	private Long merchantId;

	@Column(name = "merchant_type", length = 32)
	private String merchantType;

	@Column(name = "create_Date")
    private Date createDate;
	
	@Column(name = "create_user", length = 32)
    private String createUser;

    @Column(name = "remark", length = 200)
    private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "ProductOperationRule [id=" + id + ", historyId=" + historyId
				+ ", merchantId=" + merchantId + ", merchantType="
				+ merchantType + ", createTime=" + createDate + ", createUser="
				+ createUser + ", remark=" + remark + "]";
	}

}
