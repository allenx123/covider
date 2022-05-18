package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.covider.models.TimeSlot;

@Repository
public interface TimeSlotRepository extends CrudRepository<TimeSlot, String>{
	
	@Query("SELECT ts FROM TimeSlot ts WHERE ts.email = ?1")
	List<TimeSlot> fetchSchedule(String email);
	
	@Query("SELECT ts FROM TimeSlot ts WHERE ts.teacher = ?1 and ts.course = ?2")
	List<TimeSlot> fetchSchedule(String teacher, String course);

}
