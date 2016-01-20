/*
 * 文件名：RebateTaskControl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年2月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rebate_task_control")
public class RebateTaskControl  implements Serializable{

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = 3179064577349257037L;
	
	@Id
    @Column(name = "rebate_control_id")
    private String rebateControlId;  //返佣任务控制表主键ID

    @Column(name = "rebate_date")
    private Date rebateDate;    //返佣任务时间

    @Column(name = "CREATE_DATE")
    private Date createDate;        //创建时间

    @Column(name = "update_DATE")
    private Date updateDate;        //更新时间
    
    @Column(name = "STATUS")
    private String status;          //状态
    
    @Column(name = "remark")
    private String remark;          //描述

	public String getRebateControlId() {
		return rebateControlId;
	}

	public void setRebateControlId(String rebateControlId) {
		this.rebateControlId = rebateControlId;
	}

	public Date getRebateDate() {
		return rebateDate;
	}

	public void setRebateDate(Date rebateDate) {
		this.rebateDate = rebateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "RebateTaskControl [rebateControlId=" + rebateControlId
				+ ", rebateDate=" + rebateDate + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", status=" + status
				+ ", remark=" + remark + "]";
	}
    
}
