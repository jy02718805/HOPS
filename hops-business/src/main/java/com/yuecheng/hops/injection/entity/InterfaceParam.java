package com.yuecheng.hops.injection.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "interface_param")
public class InterfaceParam implements Serializable
{
    private static final long serialVersionUID = -1882068604517443210L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InterfaceParamIdSeq")
    @SequenceGenerator(name = "InterfaceParamIdSeq", sequenceName = "INTERFACE_PARAM_ID_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "interface_definition_id")
    private Long interfaceDefinitionId;// 接口定义ID

    @Column(name = "sequence")
    private Long sequence;// 排序

    @Column(name = "input_param_name", length = 20)
    private String inputParamName;// 输入参数名称

    @Column(name = "out_param_name", length = 20)
    private String outParamName;// 输出参数名称

    @Column(name = "data_type", length = 10)
    private String dataType;// 数据类型(字符串、日期)

    @Column(name = "param_type", length = 10)
    private String paramType;// 参数类型

    @Column(name = "encryption_function")
    private String encryptionFunction;// 加密类型

    @Column(name = "encryption_param_names", length = 256)
    private String encryptionParamNames;

    @Column(name = "connection_module", length = 10)
    private String connectionModule;// 連接方式

    @Column(name = "is_capital", length = 10)
    private String isCapital;// 大小写：0大写，1小写

    @Column(name = "in_body", length = 10)
    private String inBody;// 参数是否在报文中

    @Column(name = "format_type")
    private String formatType;// 金额、日期格式化定义

    @Column(name = "response_result")
    private String responseResult;// 返回成功失败标示 成功(success) 失败(fail)

    public String getResponseResult()
    {
        return responseResult;
    }

    public void setResponseResult(String responseResult)
    {
        this.responseResult = responseResult;
    }

    public String getFormatType()
    {
        return formatType;
    }

    public void setFormatType(String formatType)
    {
        this.formatType = formatType;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getInterfaceDefinitionId()
    {
        return interfaceDefinitionId;
    }

    public void setInterfaceDefinitionId(Long interfaceDefinitionId)
    {
        this.interfaceDefinitionId = interfaceDefinitionId;
    }

    public Long getSequence()
    {
        return sequence;
    }

    public void setSequence(Long sequence)
    {
        this.sequence = sequence;
    }

    public String getInputParamName()
    {
        return inputParamName;
    }

    public void setInputParamName(String inputParamName)
    {
        this.inputParamName = inputParamName;
    }

    public String getOutParamName()
    {
        return outParamName;
    }

    public void setOutParamName(String outParamName)
    {
        this.outParamName = outParamName;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getParamType()
    {
        return paramType;
    }

    public void setParamType(String paramType)
    {
        this.paramType = paramType;
    }

    public String getEncryptionFunction()
    {
        return encryptionFunction;
    }

    public void setEncryptionFunction(String encryptionFunction)
    {
        this.encryptionFunction = encryptionFunction;
    }

    public String getEncryptionParamNames()
    {
        return encryptionParamNames;
    }

    public void setEncryptionParamNames(String encryptionParamNames)
    {
        this.encryptionParamNames = encryptionParamNames;
    }

    public String getConnectionModule()
    {
        return connectionModule;
    }

    public void setConnectionModule(String connectionModule)
    {
        this.connectionModule = connectionModule;
    }

    public String getIsCapital()
    {
        return isCapital;
    }

    public void setIsCapital(String isCapital)
    {
        this.isCapital = isCapital;
    }

    public String getInBody()
    {
        return inBody;
    }

    public void setInBody(String inBody)
    {
        this.inBody = inBody;
    }

    @Override
    public String toString()
    {
        return "InterfaceParam [id=" + id + ", interfaceDefinitionId=" + interfaceDefinitionId
               + ", sequence=" + sequence + ", inputParamName=" + inputParamName
               + ", outParamName=" + outParamName + ", dataType=" + dataType + ", paramType="
               + paramType + ", encryptionFunction=" + encryptionFunction
               + ", encryptionParamNames=" + encryptionParamNames + ", connectionModule="
               + connectionModule + ", isCapital=" + isCapital + ", inBody=" + inBody + "]";
    }
}
