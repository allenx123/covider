package com.project.covider.helpers;


import java.util.UUID;    
public class Utility {

	public static String getUUID() {
		UUID uuid = UUID.randomUUID(); 
		return uuid.toString();
	}
}
