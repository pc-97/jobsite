/**
 * 
 */
package com.prashant.jobsite.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtility {
	
//	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private static String pattern = "yyyy-MM-dd";
	public static boolean isNullOrEmpty(String input) {
		boolean isEmpty = false;
		if(input == null || "".equals(input))
			isEmpty = true;
		return isEmpty;
	}
	
//	public static String getCurrentUser() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  return authentication.getName();
//	}
//
	
}
