package com.logate.lacademy.web.search;

import java.io.Serializable;

import org.springframework.context.annotation.Description;

@Description(value = "Employee Search Object")
public class EmployeeSearch implements Serializable {

	private String firstName;
	private String lastName;
	private String jobDescription;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getJobDescription() {
		return jobDescription;
	}
	
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	@Override
	public String toString() {
		return "EmployeeSearch [firstName=" + firstName + ", lastName=" + lastName + ", jobDescription="
				+ jobDescription + "]";
	}
}
