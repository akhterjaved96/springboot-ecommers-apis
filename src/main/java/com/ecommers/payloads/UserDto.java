package com.ecommers.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private Integer id;

    private String name;
    private String email;


    private String password;

    private String imageName;

    private String address;

    private String about;

    private Date date;

    private Set<RoleDto> roles = new HashSet<>();


    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}