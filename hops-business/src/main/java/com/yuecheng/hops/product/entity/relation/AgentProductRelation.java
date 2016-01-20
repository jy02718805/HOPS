package com.yuecheng.hops.product.entity.relation;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Agent_Product_Relation")
public class AgentProductRelation implements ProductRelation,Serializable {

	private static final long serialVersionUID = 1785238022124442737L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agentProductRelationIdSeq")
	@SequenceGenerator(name = "agentProductRelationIdSeq", sequenceName = "AGENT_PRODUCT_ID_SEQ", allocationSize = 1)
	@Column(name = "id")
	public Long id;

	@Column(name = "identity_id")
	private Long identityId;

	@Column(name = "identity_type")
	private String identityType;

	@Column(name = "identity_name", length = 50)
	private String identityName;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "province", length = 10)
	private String province;// 省份

	@Column(name = "par_value", length = 10)
	private BigDecimal parValue;// 面值

	@Column(name = "carrier_name", length = 64)
	private String carrierName;// 运营商

	@Column(name = "city", length = 10)
	private String city;// 城市

	@Column(name = "discount", length = 10)
	private BigDecimal discount;

	@Column(name = "discount_weight", length = 10)
	private BigDecimal discountWeight;

	@Column(name = "quality", length = 10)
	private BigDecimal quality;

	@Column(name = "qualityWeight", length = 10)
	private BigDecimal qualityWeight;

	@Column(name = "price", length = 10)
	private BigDecimal price;

	@Column(name = "status", length = 2)
	private String status;

	@Column(name = "def_value")
	private Boolean defValue;// 选定 1,未选 2

	@Column(name = "is_root")
	private Boolean isRoot;// true是 false否
	
	@Column(name = "display_value")
	private BigDecimal displayValue;
	
	@Column(name = "business_type")
	private Integer businessType;
	
	@Column(name = "agent_prod_id")
	private String agentProdId;

	@Transient
	private Long pid;

	public Integer getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(Integer businessType)
	{
		this.businessType = businessType;
	}

	public String getAgentProdId()
	{
		return agentProdId;
	}

	public void setAgentProdId(String agentProdId)
	{
		this.agentProdId = agentProdId;
	}

	public BigDecimal getDisplayValue()
	{
		return displayValue;
	}

	public void setDisplayValue(BigDecimal displayValue)
	{
		this.displayValue = displayValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdentityId() {
		return identityId;
	}

	public void setIdentityId(Long identityId) {
		this.identityId = identityId;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Boolean isDefValue() {
		return defValue;
	}

	public void setDefValue(Boolean defValue) {
		this.defValue = defValue;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscountWeight() {
		return discountWeight;
	}

	public void setDiscountWeight(BigDecimal discountWeight) {
		this.discountWeight = discountWeight;
	}

	public BigDecimal getQuality() {
		return quality;
	}

	public void setQuality(BigDecimal quality) {
		this.quality = quality;
	}

	public BigDecimal getQualityWeight() {
		return qualityWeight;
	}

	public void setQualityWeight(BigDecimal qualityWeight) {
		this.qualityWeight = qualityWeight;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	public Boolean getDefValue() {
		return defValue;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "DownProductRelation [id=" + id + ", identityId=" + identityId
				+ ", identityType=" + identityType + ", identityName="
				+ identityName + ", productId=" + productId + ", productName="
				+ productName + ", province=" + province + ", parValue="
				+ parValue + ", carrierName=" + carrierName + ", city=" + city
				+ ", discount=" + discount + ", discountWeight="
				+ discountWeight + ", quality=" + quality + ", qualityWeight="
				+ qualityWeight + ", price=" + price + ", status=" + status
				+ ", defValue=" + defValue + ", isRoot=" + isRoot + "]";
	}

}
