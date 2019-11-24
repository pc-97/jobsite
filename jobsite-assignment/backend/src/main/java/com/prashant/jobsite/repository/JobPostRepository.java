package com.prashant.jobsite.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.jobsite.model.JobPost;




public interface JobPostRepository extends JpaRepository<JobPost, Integer> {

//	boolean existsByEmail(String email);

//	  UserAccount findByEmail(String email);
//	  
//	  UserAccount findByEmailAndPassword(String userName, String password);
//	  
//	//  boolean existsByEmail(String email);
//
//	  @Transactional
//	  void deleteByEmail(String email);
	
	List<JobPost> findByUserName(String userName);


}
