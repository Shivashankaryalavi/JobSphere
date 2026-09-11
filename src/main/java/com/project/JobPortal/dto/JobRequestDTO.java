package com.project.JobPortal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class JobRequestDTO {
	
	@NotBlank(message="Title cannot be blank")
	private String title;
	@NotBlank(message="Location cannot be blank")
	private String location;
	@Positive(message="Salary must be greater than 0")
	private double salary;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}

}
