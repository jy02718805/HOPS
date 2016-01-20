package com.yuecheng.hops.transaction.event;

import com.yuecheng.hops.common.event.HopsRequestEvent;

public class SynProductStatusEvent extends HopsRequestEvent
{
	private static final long serialVersionUID = -8488438749475991997L;
	private Long merchantId;
	private String merchantProdCode;
	private String status;

	public Long getMerchantId()
	{
		return merchantId;
	}

	public void setMerchantId(Long merchantId)
	{
		this.merchantId = merchantId;
	}

	public String getMerchantProdCode()
	{
		return merchantProdCode;
	}

	public void setMerchantProdCode(String merchantProdCode)
	{
		this.merchantProdCode = merchantProdCode;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public SynProductStatusEvent(Object source, Long merchantId, String merchantProdCode, String status)
	{
		super(source);
		this.merchantId = merchantId;
		this.merchantProdCode = merchantProdCode;
		this.status = status;
	}

}
