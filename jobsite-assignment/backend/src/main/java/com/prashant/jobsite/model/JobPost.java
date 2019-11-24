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
@Table(name="job_post")
@Data
public class JobPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(unique = false, nullable = false, updatable = true)
	private String title;
    
    @Column(nullable = false, updatable = true)
    private String description;
    
    @Column(unique = false, nullable = false, updatable = true)
    private Integer yearsOfExperience;
    
    @Column(unique = false, nullable = false, updatable = true)
    private String company;
    
    @Column(unique=false, nullable = false, updatable = true)
    private String location;
    
    @Column(unique=false, nullable = false, updatable = false)
    private String userName;
    
  }
