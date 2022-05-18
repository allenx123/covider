package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.FrequentlyVisited;

@Repository
public interface FrequentlyVisitedRepository extends CrudRepository<FrequentlyVisited, String> {
	
	@Query("SELECT fv FROM FrequentlyVisited fv WHERE fv.email = ?1")
	List<FrequentlyVisited> fetchFrequentlyVisited(String email);
	
	

}
