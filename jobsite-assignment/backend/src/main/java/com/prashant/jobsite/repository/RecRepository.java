package com.prashant.jobsite.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.jobsite.model.RecAccount;
import com.prashant.jobsite.model.UserAccount;




public interface RecRepository extends JpaRepository<RecAccount, Integer> {

	boolean existsByEmail(String email);

	  UserAccount findByEmail(String email);
	  
	  UserAccount findByEmailAndPassword(String email, String password);
	  
	//  boolean existsByEmail(String email);

	  @Transactional
	  void deleteByemail(String email);


}
