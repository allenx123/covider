package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.CheckIn;

@Repository
public interface CheckInRepository extends CrudRepository<CheckIn, String>{
	
	
	@Query("SELECT c FROM CheckIn c WHERE c.email = ?1")
	List<CheckIn> fetchCheckIn(String email);
	
	@Query("SELECT c FROM CheckIn c WHERE c.building = ?1")
	List<CheckIn> fetchCheckInBuilding(String building);

}
