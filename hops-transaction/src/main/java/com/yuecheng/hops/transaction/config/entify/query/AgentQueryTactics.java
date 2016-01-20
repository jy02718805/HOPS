package com.yuecheng.hops.transaction.config.entify.query;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "down_query_tactics")
public class AgentQueryTactics implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DownQueryTacticsIdSeq")
    @SequenceGenerator(name = "DownQueryTacticsIdSeq", sequenceName = "DOWN_QUERY_TACTICS_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "count")
    private Long              count;                                    // 次数

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getCount()
    {
        return count;
    }

    public void setCount(Long count)
    {
        this.count = count;
    }
}
