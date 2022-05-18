package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.Profile;

@Repository
public interface ProfilesRepository extends CrudRepository<Profile, String>{

	@Query("SELECT p FROM Profile p WHERE p.email = ?1 and p.password = ?2")
	List<Profile> fetchProfileLogin(String email, String password);
	
	@Query("SELECT p FROM Profile p WHERE p.email = ?1")
	List<Profile> fetchProfileEmail(String email);
	
}
