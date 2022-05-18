package com.project.covider;

import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.covider.controllers.UIController;
import com.project.covider.models.Building;
import com.project.covider.models.CheckIn;
import com.project.covider.models.HealthReport;
import com.project.covider.repositories.BuildingRepository;
import com.project.covider.repositories.CheckInRepository;
import com.project.covider.repositories.HealthReportRepository;
import com.project.covider.repositories.NotifiedRepository;
import com.project.covider.repositories.ProfilesRepository;
import com.project.covider.repositories.TimeSlotRepository;

@SpringBootApplication
public class CoviderApplication implements CommandLineRunner {

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

	@Value("${updateDatabase}")
	boolean updateDatabase;
	@Value("${resetTables}")
	boolean resetTables;

	public static int day;

	public static void main(String[] args) {
		day = 0;
		SpringApplication.run(CoviderApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Calendar c = Calendar.getInstance();

		
		/*
		if (updateDatabase) {
			// updates building risk
			ArrayList<Building> buildings = (ArrayList<Building>) buildingRepository.findAll();
			for (int i = 0; i < buildings.size(); i++) {
				String building = buildings.get(i).getName();
				String latitute = buildings.get(i).getLatitude();
				String longitude = buildings.get(i).getLongitude();
				String risk = buildings.get(i).getRisk();
				Building updateBuilding = new Building();
				updateBuilding.setName(building);
				updateBuilding.setRisk(risk);
				updateBuilding.setLatitude(latitute);
				updateBuilding.setLongitude(longitude);
				ArrayList<CheckIn> checkIns = (ArrayList<CheckIn>) checkInRepository.fetchCheckInBuilding(building);
				for (int j = 0; j < checkIns.size(); j++) {
					String email = checkIns.get(j).getEmail();
					ArrayList<HealthReport> healthReport = (ArrayList<HealthReport>) healthReportRepository
							.fetchHealthReport(email);
					if (healthReport.size() > 0 && healthReport.get(0).getInfected().equals("yes")) {
						updateBuilding.setRisk("high");
						buildingRepository.save(updateBuilding);
						break;
					}
				}
			}
			System.exit(0);
		}
		*/
		
		
		if (resetTables) {
			// clear health reports, check ins, notified
			// reset building status to low
			healthReportRepository.deleteAll();
			checkInRepository.deleteAll();
			notifiedRepository.deleteAll();

			ArrayList<Building> buildings = (ArrayList<Building>) buildingRepository.findAll();
			for (int i = 0; i < buildings.size(); i++) {
				String building = buildings.get(i).getName();
				String latitute = buildings.get(i).getLatitude();
				String longitude = buildings.get(i).getLongitude();
				String risk = buildings.get(i).getRisk();
				Building updateBuilding = new Building();
				updateBuilding.setName(building);
				updateBuilding.setRisk("low");
				updateBuilding.setLatitude(latitute);
				updateBuilding.setLongitude(longitude);
				buildingRepository.save(updateBuilding);
			}
			
			System.exit(0);
		}

	}

}
