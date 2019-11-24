package com.prashant.jobsite.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prashant.jobsite.common.CommonUtility;
import com.prashant.jobsite.controller.AuthController;
import com.prashant.jobsite.exception.CustomException;
import com.prashant.jobsite.model.RecAccount;
import com.prashant.jobsite.model.UserAccount;
import com.prashant.jobsite.repository.AuthRepository;
import com.prashant.jobsite.repository.RecRepository;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private RecRepository recRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	private UserLogRepository userLogRepository;
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	public UserAccount signin(String userName, String password) {
		logger.info("inside auth service signin method");
		UserAccount user;
		if(CommonUtility.isNullOrEmpty(userName) || CommonUtility.isNullOrEmpty(password))
			throw new CustomException("Empty username or password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		try {
			logger.info("repository method called");
			 user = authRepository.findByEmail(userName);

			 if(user == null) {
				 throw new CustomException("Username does not exist in system", HttpStatus.NOT_FOUND);
			 }
			 if(!(passwordEncoder.matches(password, user.getPassword())))
				 throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
//			 saveLog(user);	
		} catch (Exception e) {
			 throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		} finally {

		}
		return user;
	}
	
//	private void saveLog(UserAccount user) {
//		
//		UserLog log = new UserLog();
//		
//		log.setUserAccountId(user.getId());
//		log.setUserAccount(user);
//		log.setLastLoginDate(new Date());
//		log.setIpAddress(CommonUtility.getRemoteSystemIP());
//		
//		UserLog existingLog = userLogRepository.findByUserAccountId(user.getId()); 
//
//		if(existingLog != null) {
//			log.setId(existingLog.getId());
//			
//		}
//		
//		logger.info("saving log {}", log);
//		userLogRepository.save(log);
//	}


	public RecAccount recSignup(RecAccount recruiter) {
		logger.info("into signup service method");
		
		

			try {
				if(recruiter.getEmail().equals("") || recruiter.getPassword().equals("") || 
						recruiter.getCompany().equals("") || recruiter.getName().equals("")
						|| recruiter.getDesignation().equals(""))
					throw new CustomException("Enter mantadory credentials", HttpStatus.BAD_REQUEST);
				
				if (authRepository.existsByEmail(recruiter.getEmail())) {
					throw new CustomException("Login Id already exists in the system", HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
				UserAccount user = new UserAccount();
				user.setEmail(recruiter.getEmail());
				user.setPassword(passwordEncoder.encode(recruiter.getPassword()));
				logger.info("saving user");
				authRepository.save(user);
				recruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));
				logger.info("saving recruiter");
				return recRepository.save(recruiter);
				
			} catch (Exception ex) {
				throw new CustomException(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			
		
	}
	
//	public Boolean resetPassword(String userName, String password) {
//		boolean isPasswordChanged = false;
//		UserAccount user = authRepository.findByUserName(userName);
//		if (user == null) {
//			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
//		} else {
//			user.setPassword(passwordEncoder.encode(password));
//			authRepository.save(user);
//			isPasswordChanged = true;
//		}
//		return isPasswordChanged;
//	}
//
//	public Boolean changePassword(String userName, String existingPassword, String newPassword) {
//		boolean isPasswordChanged = false;
//		UserAccount user = authRepository.findByUserName(userName);
//		logger.info("Change Password Service call with user {}", user);
//		if (user == null) {
//			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
//		} else {
//			logger.info("Change Password Service call with user details {}", user.toString());
//			if(!(passwordEncoder.matches(existingPassword, user.getPassword())))
//				throw new CustomException("The value provided for current password is not correct", HttpStatus.UNPROCESSABLE_ENTITY);
//			user.setPassword(passwordEncoder.encode(newPassword));
//			try {
//				authRepository.save(user);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//				isPasswordChanged = true;
//		}
//		return isPasswordChanged;
//	}
//	
//	public Boolean isUserExists(String userName) {
//		if (authRepository.existsByUserName(userName)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

}
