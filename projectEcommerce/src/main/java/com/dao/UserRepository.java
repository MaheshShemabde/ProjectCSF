package com.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("Select u from User u where u.email=:email")
	User findUserByEmail(@Param("email") String email);

}
