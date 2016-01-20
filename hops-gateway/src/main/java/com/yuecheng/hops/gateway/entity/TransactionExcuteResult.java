package com.yuecheng.hops.gateway.entity;

import java.io.Serializable;
import java.util.Map;

public class TransactionExcuteResult implements Serializable {

	private static final long serialVersionUID = 8250657596360927761L;

	private String interfaceType;

	private Map<String, Object> responseFields;

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public Map<String, Object> getResponseFields() {
		return responseFields;
	}

	public void setResponseFields(Map<String, Object> responseFields) {
		this.responseFields = responseFields;
	}

}
