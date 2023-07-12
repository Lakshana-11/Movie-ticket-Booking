package com.cts.movieusermanagement.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.movieusermanagement.model.Users;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Integer> {
	
	Users findByUserEmail(String userEmail);
	
}
