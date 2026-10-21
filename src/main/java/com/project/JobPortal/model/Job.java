package com.project.JobPortal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Job {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String location;
	private double salary;
	
	@ManyToOne
	private User recruiter;
	
	public User getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(User recruiter) {
		this.recruiter = recruiter;
	}
	public Job(Integer id,String title,String location,double salary) {
		this.id=id;
		this.title=title;
		this.location=location;
		this.salary=salary;
	}
	public Job() {
	}

	public Integer getId() {
		return id;
	}

	
	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public double getSalary() {
		return salary;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}


}
