package com.yuecheng.hops.injection.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Response_Code_Translation")
public class ResponseCodeTranslation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "interface_type")
	private String interfaceType;
	
	@Column(name = "error_code")
	private String errorCode;
	
	@Column(name = "coop_Order_Status")
	private String coopOrderStatus;
	
	@Column(name = "failed_code")
	private String failedCode;
	
	@Column(name = "msg")
	private String msg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCoopOrderStatus() {
		return coopOrderStatus;
	}

	public void setCoopOrderStatus(String coopOrderStatus) {
		this.coopOrderStatus = coopOrderStatus;
	}

	public String getFailedCode() {
		return failedCode;
	}

	public void setFailedCode(String failedCode) {
		this.failedCode = failedCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
