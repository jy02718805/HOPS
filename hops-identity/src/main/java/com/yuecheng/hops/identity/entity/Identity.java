package com.yuecheng.hops.identity.entity;


import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MirrorRoleType;
import com.yuecheng.hops.identity.entity.identifier.Identifier;
import com.yuecheng.hops.identity.entity.identifier.IdentifierType;
import com.yuecheng.hops.identity.entity.relation.IdentityRelationship;
import com.yuecheng.hops.identity.entity.relation.IdentityRelationshipType;


/**
 * 统一定义id的entity基类. 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@MappedSuperclass
public interface Identity extends Serializable
{
    Long getId();

    void setId(Long id);

    MirrorRoleType getIdentityRoleType();

    void setIdentityRoleType(MirrorRoleType identityRoleType);

    IdentityType getIdentityType();

    void setIdentityType(IdentityType identityType);

    IdentityStatus getIdentityStatus();

    void setIdentityStatus(IdentityStatus identityStatus);

    Identifier getIdentifier(IdentifierType identifierType);

    void addIdentifier(Identifier identifier);

    IdentityRelationship getIdentityRelationship(IdentityRelationshipType identityRelationshipType);

    void setIdentityRelationship(IdentityRelationship identityRelationship);

}
