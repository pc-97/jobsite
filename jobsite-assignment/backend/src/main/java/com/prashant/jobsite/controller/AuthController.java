package com.prashant.jobsite.controller;

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

import com.prashant.jobsite.dto.LoginDTO;
import com.prashant.jobsite.dto.RecruiterDTO;
import com.prashant.jobsite.exception.CustomException;
import com.prashant.jobsite.model.RecAccount;
import com.prashant.jobsite.model.UserAccount;
import com.prashant.jobsite.repository.AuthRepository;
import com.prashant.jobsite.service.AuthService;

// (origins="http://localhost:4200", exposedHeaders="Authentication, Content-Type, Transfer-Encoding, Date")

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	
	
	  @Autowired
	  private AuthService authService;
	  @Autowired
	  private AuthRepository authRepository;
	  
	  @Autowired
	  private ModelMapper modelMapper;
	  
	  @Autowired
	  private AuthenticationManager authenticationManager;
//	  
//	  @Autowired
//	  private JwtTokenProvider jwtTokenProvider;
		
	  private Logger logger = LoggerFactory.getLogger(AuthController.class);
	  
	  boolean debug = logger.isDebugEnabled();

	  @PostMapping("/login")
	  public ResponseEntity<Object> login(@RequestBody LoginDTO user) {
		
		  logger.info("inside login api");
		  
		  UserAccount authenticatedUser = new UserAccount();
//		  HttpHeaders headers = new HttpHeaders();
		  
		  try {
//			  logger.info(".authenticate method called");
//			  logger.info("user is {}", user.getEmail());
//			  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//			  logger.info("out of .authenticate method");
//			  
			  authenticatedUser = authService.signin(user.getEmail(), user.getPassword());
			  authenticatedUser = authRepository.findByEmail(user.getEmail());
//			  logger.info("out of auth service signin method");
//			  headers.add("Authentication", generateToken(authenticatedUser.getUserName(), authenticatedUser.getRoles(), authenticatedUser.getUserType()));
			  authenticatedUser.setPassword("");
		  } catch (CustomException cuex) {
			  return ResponseEntity.status(cuex.getHttpStatus()).body(cuex.getLocalizedMessage());
		  } catch (AuthenticationException e) {
			  logger.error("Authentication failed for user {}", authenticatedUser.toString());
			  CustomException exp = new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
			  return ResponseEntity.unprocessableEntity().body(exp.getLocalizedMessage());
		  } catch (Exception ex) {
			  logger.error("Login failed for user {}", authenticatedUser.toString());
			  return ResponseEntity.unprocessableEntity().body(ex.getLocalizedMessage());
		  } 
		 logger.info("function ended");  
		  return ResponseEntity.ok().body(modelMapper.map(authenticatedUser, LoginDTO.class));
	  }
	  
	  @PostMapping("/recSignup")
	  public ResponseEntity<Object> recSignup(@RequestBody RecruiterDTO user) {
		  if(debug) {
			  logger.info("Signup API called with user details {}", user.toString());
		  }
		  logger.info("into the signup API");
//		  HttpHeaders headers = new HttpHeaders();
		  RecAccount newUser = new RecAccount();
		  try {
			  if(user.getPassword().length() < 6) {
				  throw new CustomException("Please enter password of minimum 6 characters", HttpStatus.BAD_REQUEST);
			  }
			  newUser = authService.recSignup(modelMapper.map(user, RecAccount.class));
			 newUser.setPassword(""); 
			  if(debug) {
				  logger.info("New user created in system with credentials {}", newUser.toString());
			  }
			  
//			  logger.info("Header Set {}", headers.toString());
		  } catch (CustomException cuex) {
			  return ResponseEntity.status(cuex.getHttpStatus()).body(cuex.getLocalizedMessage());
		  } catch (Exception ex) {
			  logger.error("Signup failed for user {}", newUser.toString());
//			  ex.printStackTrace();
			  return ResponseEntity.unprocessableEntity().body(ex.getLocalizedMessage());
		  }
		  return ResponseEntity.ok().body(modelMapper.map(newUser, RecruiterDTO.class));
	  }
	  
}
