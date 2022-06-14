package com.ecommers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommers.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
	
	@Query("select u from User u where u.name like :key")
	List<User> findByName(@Param("key") String name);

}
