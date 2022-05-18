package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.Building;

@Repository
public interface BuildingRepository extends CrudRepository<Building, String>{
	
	@Query("SELECT b FROM Building b")
	List<Building> fetchBuildings();

}
