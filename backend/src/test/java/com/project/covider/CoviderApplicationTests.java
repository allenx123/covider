package com.project.covider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.covider.helpers.Utility;
import com.project.covider.models.Building;
import com.project.covider.models.CheckIn;
import com.project.covider.models.HealthReport;
import com.project.covider.models.Notified;
import com.project.covider.models.Profile;
import com.project.covider.repositories.BuildingRepository;
import com.project.covider.repositories.CheckInRepository;
import com.project.covider.repositories.HealthReportRepository;
import com.project.covider.repositories.NotifiedRepository;
import com.project.covider.repositories.ProfilesRepository;
import com.project.covider.repositories.TimeSlotRepository;

@SpringBootTest
class CoviderApplicationTests {
	
	@Autowired(required = true)
    DataSource dataSource;
	@Autowired(required = true)
	ProfilesRepository profileRepository;
	@Autowired(required = true)
	BuildingRepository buildingRepository;
	@Autowired(required = true)
	TimeSlotRepository timeSlotRepository;
	@Autowired(required = true)
	CheckInRepository checkInRepository;
	@Autowired(required = true)
	HealthReportRepository healthReportRepository;
	@Autowired(required = true)
	NotifiedRepository notifiedRepository;

	@Test  
	void testGetBuildings()   
	{  
		ArrayList<Building> buildingList = (ArrayList<Building>) buildingRepository.fetchBuildings();
		assertNotNull(buildingList);
	} 
	
	@Test  
	void testSignUp()   
	{  
		Profile input = new Profile();
		input.setEmail("test@usc.edu");
		input.setPassword("123456");
		input.setFirstName("John");
		input.setLastName("Doe");
		input.setType("Student");
		profileRepository.save(input);
		ArrayList<Profile> profileList = (ArrayList<Profile>) profileRepository.fetchProfileEmail("test@usc.edu");
		assertEquals(profileList.size(), 1);
		assertEquals(profileList.get(0).getEmail(), "test@usc.edu");
	} 
	
	@Test
	void testInvalidLogin()
	{
		
		ArrayList<Profile> profileList = (ArrayList<Profile>) profileRepository.fetchProfileLogin("test@usc.edu", "password");
		assertEquals(profileList.size(), 0);
		
		
	}
	
	
	@Test
	void testLogin()
	{
		Profile input = new Profile();
		input.setEmail("test@usc.edu");
		input.setPassword("123456");
		input.setFirstName("John");
		input.setLastName("Doe");
		input.setType("Student");
		profileRepository.save(input);
		ArrayList<Profile> profileList = (ArrayList<Profile>) profileRepository.fetchProfileLogin("test@usc.edu", "123456");
		assertEquals(profileList.size(), 1);

		
	}
	
	@Test
	void testInfectedHealthReport()
	{
		HealthReport hr = new HealthReport();
		hr.setEmail("test@usc.edu");
		hr.setInfected("yes");
		healthReportRepository.save(hr);
		ArrayList<HealthReport> healthReport = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport("test@usc.edu");
		assertEquals(healthReport.size(), 1);
		assertEquals(healthReport.get(0).getInfected(), "yes");

	}
	
	@Test
	void testNotInfectedHealthReport()
	{
		HealthReport hr = new HealthReport();
		hr.setEmail("test@usc.edu");
		hr.setInfected("no");
		healthReportRepository.save(hr);
		ArrayList<HealthReport> healthReport = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport("test@usc.edu");
		assertEquals(healthReport.size(), 1);
		assertEquals(healthReport.get(0).getInfected(), "no");

	}
	
	@Test
	void testOverrideHealthReport()
	{
		HealthReport hr = new HealthReport();
		hr.setEmail("test@usc.edu");
		hr.setInfected("no");
		healthReportRepository.save(hr);
		hr.setInfected("yes");
		healthReportRepository.save(hr);
		ArrayList<HealthReport> healthReport = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport("test@usc.edu");
		assertEquals(healthReport.size(), 1);
		assertEquals(healthReport.get(0).getInfected(), "yes");
	}
	
	
	
	@Test
	void testCheckIn() 
	{
		ArrayList<CheckIn> checkIns = (ArrayList<CheckIn>) checkInRepository.fetchCheckIn("test@usc.edu");
		assertNotNull(checkIns);
		int currLength = checkIns.size();
		CheckIn input = new CheckIn();
		input.setId(Utility.getUUID());
		input.setEmail("test@usc.edu");
		input.setBuilding("Leavey Library");
		checkInRepository.save(input);
		input.setId(Utility.getUUID());
		input.setEmail("test@usc.edu");
		input.setBuilding("Fertitta Hall");
		checkInRepository.save(input);
		checkIns = (ArrayList<CheckIn>) checkInRepository.fetchCheckIn("test@usc.edu");

		assertTrue(checkIns.size()-currLength==2);
		
	}
	
	
	
	@Test
	void testNotified()
	{
		Notified n = new Notified();
		n.setEmail("test@usc.edu");
		notifiedRepository.save(n);
		ArrayList<Notified> notifiedList = (ArrayList<Notified>) notifiedRepository.fetchNotified("test@usc.edu");
		assertEquals(notifiedList.size(),1);
	}
	
	@Test
	void testMultipleNotified()
	{
		Notified n = new Notified();
		n.setEmail("test@usc.edu");
		notifiedRepository.save(n);
		notifiedRepository.save(n);
		ArrayList<Notified> notifiedList = (ArrayList<Notified>) notifiedRepository.fetchNotified("test@usc.edu");
		assertEquals(notifiedList.size(),1);
	}
	
	
	
}
