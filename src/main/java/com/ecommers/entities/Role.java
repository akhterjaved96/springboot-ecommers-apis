package com.ecommers.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Role {

    @Id
    private Integer id;

    private String name;

    @Override
    public boolean equals(Object o) {
        Role role1 = (Role) o;
        return this.getId().equals(role1.getId());
    }


}