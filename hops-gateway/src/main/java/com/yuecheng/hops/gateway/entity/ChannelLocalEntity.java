package com.yuecheng.hops.gateway.entity;

public class ChannelLocalEntity {
	private Long merchantId;
	private String interfaceType;
	private Long recordId;
	
	public ChannelLocalEntity(){
		
	}
	
	public ChannelLocalEntity(Long merchantId,String interfaceType,Long recordId){
		this.merchantId = merchantId;
		this.interfaceType = interfaceType;
		this.recordId = recordId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

}
