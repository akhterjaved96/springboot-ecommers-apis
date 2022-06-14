package com.ecommers.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommers.entities.Role;


public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
