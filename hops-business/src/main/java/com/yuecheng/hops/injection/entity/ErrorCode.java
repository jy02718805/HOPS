package com.yuecheng.hops.injection.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "error_code")
public class ErrorCode implements Serializable
{

    private static final long serialVersionUID = 4936858023430081583L;

    @Id
    @Column(name = "code")
    public String code;

    @Column(name = "msg")
    public String msg;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "ErrorCode [code=" + code + ", msg=" + msg + "]";
    }
}
