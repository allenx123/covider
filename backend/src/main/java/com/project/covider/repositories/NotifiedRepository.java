package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.Notified;

@Repository
public interface NotifiedRepository extends CrudRepository<Notified, String> {

	@Query("SELECT n FROM Notified n WHERE n.email = ?1")
	List<Notified> fetchNotified(String email);
	
}
