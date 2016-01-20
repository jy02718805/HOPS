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
@Table(name = "interface_packet_type_conf")
public class InterfacePacketTypeConf implements Serializable
{
    private static final long serialVersionUID = -82331160452264714L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InterfacePacketTypeConfIdSeq")
    @SequenceGenerator(name = "InterfacePacketTypeConfIdSeq", sequenceName = "SEQ_INTERFACE_PACKET_TYPE_CONF")
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_id")
    private Long merchantId;// 商户ID

    @Column(name = "interface_type", length = 32)
    private String interfaceType;// 接口类型：下单成功、下单失败

    @Column(name = "packet_type", length = 32)
    private String packetType;// 数据包类型：pain/xml,pain/text

    @Column(name = "connection_module", length = 32)
    private String connectionModule;// 连接模型：request、response

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

    public String getPacketType()
    {
        return packetType;
    }

    public void setPacketType(String packetType)
    {
        this.packetType = packetType;
    }

    public String getConnectionModule()
    {
        return connectionModule;
    }

    public void setConnectionModule(String connectionModule)
    {
        this.connectionModule = connectionModule;
    }

    @Override
    public String toString()
    {
        return "InterfacePacketTypeConf [id=" + id + ", merchantId=" + merchantId
               + ", interfaceType=" + interfaceType + ", packetType=" + packetType
               + ", connectionModule=" + connectionModule + "]";
    }

}
