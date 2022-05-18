package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, String>{
	
	@Query("SELECT c FROM Course c WHERE c.instructor = ?1 and c.course = ?2")
	List<Course> fetchCourse(String instructor, String course);
	

}
