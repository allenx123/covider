package com.project.covider.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="checkins")
public class CheckIn {
	
	@Id
	@Column(name="id")
	String id;
	
	@Column(name="email")
	String email;
	
	@Column(name="building")
	String building;
	
	@Column(name="social_distance")
	boolean social_distance;
	
	@Column(name="wearing_masks")
	boolean wearing_masks;
	
	@Column(name="hand_sanitizer")
	boolean hand_sanitizer;
	
	@Column(name="ppe_provided")
	boolean ppe_provided;
	

	public boolean isSocial_distance() {
		return social_distance;
	}

	public void setSocial_distance(boolean social_distance) {
		this.social_distance = social_distance;
	}

	public boolean isWearing_masks() {
		return wearing_masks;
	}

	public void setWearing_masks(boolean wearing_masks) {
		this.wearing_masks = wearing_masks;
	}

	public boolean isHand_sanitizer() {
		return hand_sanitizer;
	}

	public void setHand_sanitizer(boolean hand_sanitizer) {
		this.hand_sanitizer = hand_sanitizer;
	}

	public boolean isPpe_provided() {
		return ppe_provided;
	}

	public void setPpe_provided(boolean ppe_provided) {
		this.ppe_provided = ppe_provided;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}
	
	
	
	

}
