package com.prashant.jobsite.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.jobsite.dto.JobPostDTO;
import com.prashant.jobsite.dto.LoginDTO;
import com.prashant.jobsite.dto.RecruiterDTO;
import com.prashant.jobsite.exception.CustomException;
import com.prashant.jobsite.model.JobPost;
import com.prashant.jobsite.model.RecAccount;
import com.prashant.jobsite.model.UserAccount;
import com.prashant.jobsite.repository.AuthRepository;
import com.prashant.jobsite.repository.JobPostRepository;
import com.prashant.jobsite.service.AuthService;

// (origins="http://localhost:4200", exposedHeaders="Authentication, Content-Type, Transfer-Encoding, Date")

@CrossOrigin
@RestController
@RequestMapping("/jobs")
public class JobPostController {

	
	
	  
	  @Autowired
	  private JobPostRepository jobsRepository;
	  
	  @Autowired
	  private ModelMapper modelMapper;
	  
	  @Autowired
	  private AuthenticationManager authenticationManager;
//	  
//	  @Autowired
//	  private JwtTokenProvider jwtTokenProvider;
		
	  private Logger logger = LoggerFactory.getLogger(JobPostController.class);
	  
	  boolean debug = logger.isDebugEnabled();

	  @PostMapping("job/{userName}")
	  public ResponseEntity<Object> postJob(@PathVariable("userName") String userName, @RequestBody JobPostDTO job) {
		  
		  try {
		  if(job.getTitle().equals("") || job.getDescription().equals("") 
				  || job.getCompany().equals("")
					|| job.getLocation().equals("")) {
			  throw new CustomException("Enter mandatory credentials", HttpStatus.BAD_REQUEST);
		  }
		  JobPost jobPost = modelMapper.map(job, JobPost.class);
		  jobPost.setUserName(userName);
		  JobPost savedJob = jobsRepository.save(jobPost);
	return ResponseEntity.ok().body(modelMapper.map(savedJob, JobPostDTO.class));	  
		  
		  }
		  
		  catch(CustomException ex) {
			return ResponseEntity.status(ex.getHttpStatus()).body(ex.getLocalizedMessage());  
		  }
		  catch(Exception ex) {
			  return ResponseEntity.unprocessableEntity().body(ex.getLocalizedMessage());
		  }
		  
		  
	  }
	  
	  @GetMapping("{userName}")
	  public ResponseEntity<Object> postJob(@PathVariable("userName") String userName) {
		  
		  try {
		 List<JobPostDTO> jobs = new ArrayList<>();
		  
			  List<JobPost> allJobs = jobsRepository.findByUserName(userName);
			  for(JobPost job : allJobs) {
				  jobs.add(modelMapper.map(job, JobPostDTO.class));
			  }
	return ResponseEntity.ok().body(jobs);	  
		  
		  }
		  
		  catch(Exception ex) {
			  return ResponseEntity.unprocessableEntity().body(ex.getLocalizedMessage());
		  }
		  
		  
	  }
	  	  
}