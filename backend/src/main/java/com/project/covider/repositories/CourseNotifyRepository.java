package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.CheckIn;
import com.project.covider.models.CourseNotify;

@Repository
public interface CourseNotifyRepository extends CrudRepository<CourseNotify, String> {
	
	@Query("SELECT c FROM CourseNotify c WHERE c.email = ?1")
	List<CourseNotify> fetchCourseNotify(String email);

}
