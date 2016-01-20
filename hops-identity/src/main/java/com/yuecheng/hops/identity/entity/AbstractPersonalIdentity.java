package com.yuecheng.hops.identity.entity;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.yuecheng.hops.identity.entity.mirror.Person;


@MappedSuperclass
// @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPersonalIdentity extends AbstractIdentity implements PersonalRole
{
    private static final long serialVersionUID = -4795138726286739179L;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, updatable = true)
    private Person person;

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }
}
