package com.project.covider.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="healthreports")
public class HealthReport {
	
	@Id
	@Column(name="email")
	String email;
	
	@Column(name="infected")
	String infected;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInfected() {
		return infected;
	}

	public void setInfected(String infected) {
		this.infected = infected;
	}
	
	

	
}
