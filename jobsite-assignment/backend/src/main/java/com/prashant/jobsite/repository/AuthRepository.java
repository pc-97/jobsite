package com.prashant.jobsite.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.jobsite.model.UserAccount;




public interface AuthRepository extends JpaRepository<UserAccount, Integer> {

	boolean existsByEmail(String email);

	  UserAccount findByEmail(String email);
	  
	  UserAccount findByEmailAndPassword(String userName, String password);
	  
	//  boolean existsByEmail(String email);

	  @Transactional
	  void deleteByEmail(String email);


}
