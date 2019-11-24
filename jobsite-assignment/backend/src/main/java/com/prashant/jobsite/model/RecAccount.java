package com.prashant.jobsite.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name="REC_ACCOUNT")
@Data
public class RecAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(unique = true, nullable = false, updatable = false)
	@Email(message = "Email not valid")
	private String email;
    

    @Size(min=6, message = "Minimum password length: 6 characters")
    @Column(nullable = false, updatable = true)
    private String password;
    
    @Column(unique = false, nullable = false, updatable = true)
    private String name;
    
    @Column(unique = false, nullable = false, updatable = true)
    private String company;
    
    @Column(unique=false, nullable = false, updatable = true)
    private Long contactNumber;
    
    @Column(unique = false, nullable = false, updatable = true)
    private String designation;
  }
