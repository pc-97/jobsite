package com.prashant.jobsite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prashant.jobsite.model.UserAccount;
import com.prashant.jobsite.repository.AuthRepository;



@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private AuthRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	final UserAccount user = userRepository.findByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException("UserAccount '" + username + "' not found");
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(user.getPassword())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
