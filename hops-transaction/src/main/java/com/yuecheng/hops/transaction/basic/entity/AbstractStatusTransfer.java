/*
 * 文件名：AbstractStatusTransfer.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;


@MappedSuperclass
public abstract class AbstractStatusTransfer implements Serializable
{

    private static final long serialVersionUID = 8325238264581363562L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StatusTransferIdSeq")
    @SequenceGenerator(name = "StatusTransferIdSeq", sequenceName = "STATUS_TRANSAFER_ID_SEQ")
    @Column(name = "id")
    public Long               id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

}
