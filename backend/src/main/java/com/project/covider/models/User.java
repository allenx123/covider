package com.project.covider.models;

import java.util.ArrayList;

import javax.persistence.Column;

public class User {
	String email;
	
	String firstName;
	
	String lastName;
	
	String type;
	
	String password;
	
	ArrayList<TimeSlot> timeslots;
	
	
	public ArrayList<TimeSlot> getTimeslots() {
		return timeslots;
	}
	public void setTimeslots(ArrayList<TimeSlot> timeslots) {
		this.timeslots = timeslots;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
