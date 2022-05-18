package com.project.covider.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.covider.helpers.Utility;
import com.project.covider.models.Building;
import com.project.covider.models.BuildingList;
import com.project.covider.models.CheckIn;
import com.project.covider.models.Course;
import com.project.covider.models.CourseNotify;
import com.project.covider.models.FrequentlyVisited;
import com.project.covider.models.HealthReport;
import com.project.covider.models.Instructor;
import com.project.covider.models.LoginRequest;
import com.project.covider.models.Notified;
import com.project.covider.models.Profile;
import com.project.covider.models.ProfileWithStatus;
import com.project.covider.models.TimeSlot;
import com.project.covider.models.User;
import com.project.covider.repositories.BuildingRepository;
import com.project.covider.repositories.CheckInRepository;
import com.project.covider.repositories.CourseNotifyRepository;
import com.project.covider.repositories.CourseRepository;
import com.project.covider.repositories.FrequentlyVisitedRepository;
import com.project.covider.repositories.HealthReportRepository;
import com.project.covider.repositories.NotifiedRepository;
import com.project.covider.repositories.ProfilesRepository;
import com.project.covider.repositories.TimeSlotRepository;

@RestController
public class UIController {
	
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
	@Autowired(required = true)
	FrequentlyVisitedRepository frequentlyVisitedRepository;
	@Autowired(required = true)
	CourseRepository courseRepository;
	@Autowired(required = true)
	CourseNotifyRepository courseNotifyRepository;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/getprofile", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<User> getProfile(
			@RequestBody LoginRequest input) {  
		                    
		try {
			String email = input.getEmail();
			String password = input.getPassword();
			ArrayList<Profile> profileList = (ArrayList<Profile>) profileRepository.fetchProfileLogin(email, password);
			User user = new User();
			if (profileList.size()==0) {
				user.setType("invalid");
			}
			else {
				user.setEmail(email);
				user.setFirstName(profileList.get(0).getFirstName());
				user.setLastName(profileList.get(0).getLastName());
				user.setPassword(password);
				user.setType(profileList.get(0).getType());
				ArrayList<TimeSlot> timeslots = (ArrayList<TimeSlot>) timeSlotRepository.fetchSchedule(email);
				user.setTimeslots(timeslots);
				
			}
			
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			User user = new User();
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> signup(
			@RequestBody Profile input) {  
		                    
		try {
			String msg = "{ \"status\" : \"new\" }";
			String email = input.getEmail();
			ArrayList<Profile> profileList = (ArrayList<Profile>) profileRepository.fetchProfileEmail(email);
			if (profileList.size()!=0) {
				msg = "{ \"status\" : \"exists\" }";
			}
			else {
				profileRepository.save(input);
			}
			
			return new ResponseEntity<String>(msg, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/getbuildings", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<ArrayList<Building>> getBuildings() {  
		                    
		try {
			
			ArrayList<Building> buildingList = (ArrayList<Building>) buildingRepository.fetchBuildings();
			
			return new ResponseEntity<ArrayList<Building>>(buildingList, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			ArrayList<Building> buildingList = new ArrayList<Building>();
			return new ResponseEntity<ArrayList<Building>>(buildingList, HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/getschedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<ArrayList<TimeSlot>> getSchedule(
			@RequestBody User input) {  
		                    
		try {
			
			ArrayList<TimeSlot> schedule = (ArrayList<TimeSlot>) timeSlotRepository.fetchSchedule(input.getEmail());
			
			return new ResponseEntity<ArrayList<TimeSlot>>(schedule, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			ArrayList<TimeSlot> schedule = new ArrayList<TimeSlot>();
			return new ResponseEntity<ArrayList<TimeSlot>>(schedule, HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/setschedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> setSchedule(
			@RequestBody ArrayList<TimeSlot> input) {  
		                    
		try {
			for (int i=0; i<input.size(); i++) {
				TimeSlot ts = input.get(i);
				ts.setId(ts.getEmail()+ts.getCourse());
				timeSlotRepository.save(ts);
				Course c = new Course();
				c.setCourse(ts.getCourse());
				c.setInstructor(ts.getTeacher());
				c.setStatus("in person");
				c.setId(ts.getCourse()+ts.getTeacher());
				courseRepository.save(c);
			}
			return new ResponseEntity<String>("{}", HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/gethealthreport", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> gethealthReport(
			@RequestBody User input) {  
		                    
		try {
			ArrayList<HealthReport> reports = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport(input.getEmail());
			if (reports.size()>0 && reports.get(0).getInfected().equals("yes")) {
				return new ResponseEntity<String>("{ \"status\" : \"positive\" }", HttpStatus.OK);
			}
			
			if (reports.size()>0 && reports.get(0).getInfected().equals("no")) {
				return new ResponseEntity<String>("{ \"status\" : \"negative\" }", HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("{ \"status\" : \"undefined\" }", HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/healthreport", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> healthReport(
			@RequestBody HealthReport input) {  
		                    
		try {
			
			healthReportRepository.save(input);
			if (input.getInfected().equals("yes")) {
				Notified n = new Notified();
				n.setEmail(input.getEmail());
				notifiedRepository.save(n);
			}
			updateBuildings();
			return new ResponseEntity<String>("{}", HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/checkin", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> checkIn(
			@RequestBody CheckIn input) {  
		                    
		try {
			input.setId(Utility.getUUID());
			FrequentlyVisited fv = new FrequentlyVisited();
			fv.setBuilding(input.getBuilding());
			fv.setEmail(input.getEmail());
			fv.setId(input.getId());
			
			checkInRepository.save(input);
			frequentlyVisitedRepository.save(fv);
			
			updateBuildings();
			
			return new ResponseEntity<String>("{}", HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/notify", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> notify(
			@RequestBody User input) {  
		                    
		try {
			String email = input.getEmail();
			
			ArrayList<Notified> notifiedList = (ArrayList<Notified>) notifiedRepository.fetchNotified(email);
			if (notifiedList.size()>0) {
				return new ResponseEntity<String>("{ \"status\" : \"safe\" }", HttpStatus.OK);
			}
			
			//check if user checked into any building that an infected user also checked into
			ArrayList<CheckIn> checkIns = (ArrayList<CheckIn>) checkInRepository.fetchCheckIn(email);
			for (int i=0; i<checkIns.size(); i++) {
				String building = checkIns.get(i).getBuilding();
				ArrayList<CheckIn> temp = (ArrayList<CheckIn>) checkInRepository.fetchCheckInBuilding(building);
				for (int j=0; j<temp.size(); j++) {
					email = temp.get(j).getEmail();
					ArrayList<HealthReport> healthReport = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport(email);
					if (healthReport.size()>0) {
						if (healthReport.get(0).getInfected().equals("yes")) {
							Notified n = new Notified();
							n.setEmail(input.getEmail());
							notifiedRepository.save(n);
							return new ResponseEntity<String>("{ \"status\" : \"danger\" }", HttpStatus.OK);
						}
					}
				}
			}
			
			//check if user is in same class as an infected user
			email = input.getEmail();
			ArrayList<TimeSlot> timeslots = (ArrayList<TimeSlot>) timeSlotRepository.fetchSchedule(email);
			for (int i=0; i<timeslots.size(); i++) {
				String course = timeslots.get(i).getCourse();
				String instructor = timeslots.get(i).getTeacher();
				ArrayList<TimeSlot> sameClassList = (ArrayList<TimeSlot>) timeSlotRepository.fetchSchedule(instructor, course);
				for (int j=0; j<sameClassList.size(); j++) {
					email = sameClassList.get(i).getEmail();
					ArrayList<HealthReport> healthReport = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport(email);
					if (healthReport.size()>0) {
						if (healthReport.get(0).getInfected().equals("yes")) {
							Notified n = new Notified();
							n.setEmail(input.getEmail());
							notifiedRepository.save(n);
							return new ResponseEntity<String>("{ \"status\" : \"danger\" }", HttpStatus.OK);
						}
					}
				}
			}
			
			return new ResponseEntity<String>("{ \"status\" : \"safe\" }", HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/checkhealthreport", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> checkHealthReport(
			@RequestBody User input) {  
		                    
		try {
			String email = input.getEmail();
			ArrayList<HealthReport> healthReport = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport(email);
			if (healthReport.size()>0) {
				return new ResponseEntity<String>("{ \"status\" : \"exists\" }", HttpStatus.OK);
			}
			return new ResponseEntity<String>("{ \"status\" : \"new\" }", HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/getstudents", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<ArrayList<ProfileWithStatus>> getStudents(
			@RequestBody Instructor input) {  
		                    
		try {
			String instructor = input.getInstructor();
			String course = input.getCourse();
			ArrayList<TimeSlot> timeslots = (ArrayList<TimeSlot>) timeSlotRepository.fetchSchedule(instructor, course);
			ArrayList<ProfileWithStatus> students = new ArrayList<ProfileWithStatus>();
			for (int i=0; i<timeslots.size(); i++) {
				ArrayList<Profile> profile = (ArrayList<Profile>) profileRepository.fetchProfileEmail(timeslots.get(i).getEmail());
			    if (profile.size()>0 && profile.get(0).getType().equals("student")) {
			    	ProfileWithStatus pws = new ProfileWithStatus();
			    	pws.setEmail(profile.get(0).getEmail());
			    	pws.setFirstName(profile.get(0).getFirstName());
			    	pws.setLastName(profile.get(0).getLastName());
			    	pws.setPassword(profile.get(0).getPassword());
			    	pws.setType(profile.get(0).getType());
			    	ArrayList<HealthReport> hr = (ArrayList<HealthReport>) healthReportRepository.fetchHealthReport(profile.get(0).getEmail());
			    	if (hr.size()>0) {
			    		if (hr.get(0).getInfected().equals("yes")) {
			    			pws.setHealthstatus("Positive");
			    		}
			    		else {
			    			pws.setHealthstatus("Negative");
			    		}
			    		//pws.setHealthstatus(hr.get(0).)
			    	}
			    	else {
			    		pws.setHealthstatus("Undefined");
			    	}
			    	students.add(pws);
			    }
			}
			return new ResponseEntity<ArrayList<ProfileWithStatus>>(students, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			ArrayList<ProfileWithStatus> students = new ArrayList<ProfileWithStatus>();
			return new ResponseEntity<ArrayList<ProfileWithStatus>>(students, HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/setclassstatus", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> setClassStatus(
			@RequestBody Instructor input) {  
		                    
		try {
			String instructor = input.getInstructor();
			String course = input.getCourse();
			ArrayList<Course> courses = (ArrayList<Course>) courseRepository.fetchCourse(instructor, course);
			if (courses.size()>0) {
				String status = courses.get(0).getStatus();
				if (status.equals("online")) {
					status = "in person";
				}
				else status = "online";
				
				courses.get(0).setStatus(status);
				courseRepository.save(courses.get(0));
				
				ArrayList<TimeSlot> students = (ArrayList<TimeSlot>) timeSlotRepository.fetchSchedule(instructor, course);
				for (int i=0; i<students.size(); i++) {
					CourseNotify cn = new CourseNotify();
					cn.setCourse(course);
					cn.setTeacher(instructor);
					cn.setEmail(students.get(i).getEmail());
					cn.setStatus(status);
					cn.setId(students.get(i).getEmail()+course+instructor);
					courseNotifyRepository.save(cn);
				}
				
				
			}
			return new ResponseEntity<String>("{}", HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
    }
	
	
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/getclassstatus", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<Course> getClassStatus(
			@RequestBody Instructor input) {  
		                    
		try {
			String instructor = input.getInstructor();
			String course = input.getCourse();
			ArrayList<Course> courses = (ArrayList<Course>) courseRepository.fetchCourse(instructor, course);
			if (courses.size()>0) {
				return new ResponseEntity<Course>(courses.get(0), HttpStatus.OK);
				
			}
			Course c = new Course();
			return new ResponseEntity<Course>(c, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			Course c = new Course();
			return new ResponseEntity<Course>(c, HttpStatus.BAD_REQUEST);
		}
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/notifyclassstatus", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<ArrayList<CourseNotify>> notifyClassStatus(
			@RequestBody User input) {  
		                    
		try {
			String email = input.getEmail();
			ArrayList<CourseNotify> notifications = (ArrayList<CourseNotify>) courseNotifyRepository.fetchCourseNotify(email);
			
			for (int i=0; i<notifications.size(); i++) {
				courseNotifyRepository.delete(notifications.get(i));
			}
			
			return new ResponseEntity<ArrayList<CourseNotify>>(notifications, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			ArrayList<CourseNotify> notifications = new ArrayList<CourseNotify>();
			return new ResponseEntity<ArrayList<CourseNotify>>(notifications, HttpStatus.BAD_REQUEST);
		}
    }
	
	
	
	
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/frequentlyvisited", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<BuildingList> frequentlyVisited(
			@RequestBody User input) {  
		                    
		try {
			
			String email = input.getEmail();
			ArrayList<FrequentlyVisited> frequentlyVisitedList = (ArrayList<FrequentlyVisited>) frequentlyVisitedRepository.fetchFrequentlyVisited(email);
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for (int i=0; i<frequentlyVisitedList.size(); i++) {
				FrequentlyVisited fv = frequentlyVisitedList.get(i);
				if (map.containsKey(fv.getBuilding())) {
					map.put(fv.getBuilding(), map.get(fv.getBuilding())+1);
				}
				else {
					map.put(fv.getBuilding(), 1);
				}
			}
			ArrayList<Pair> buildingList = new ArrayList<Pair>();
			for (String building : map.keySet()) {
				buildingList.add(new Pair(building, map.get(building)));
			}
			
			Collections.sort(buildingList, new Sortbynum());
			ArrayList<String> list = new ArrayList<String>();
			if (buildingList.size()>=5) {
				for (int i=0; i<5; i++) {
					list.add(buildingList.get(i).building);
				}
			}
			else {
				for (int i=0; i<buildingList.size(); i++) {
					list.add(buildingList.get(i).building);
				}
			}
			
			BuildingList bl = new BuildingList();
			bl.setBuildingList(list);
			return new ResponseEntity<BuildingList>(bl, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			BuildingList bl = new BuildingList();
			return new ResponseEntity<BuildingList>(bl, HttpStatus.BAD_REQUEST);
		}
    }
	
	public class Pair {
		Pair(String building, int num) {
			this.building = building;
			this.num = num;
		}
		String building;
		int num;
	}
	class Sortbynum implements Comparator<Pair> {
		
	    public int compare(Pair a, Pair b)
	    {
	 
	        return b.num-a.num;
	    }
	}
	
	
	public void updateBuildings() {
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
		
	}
	
	

}
