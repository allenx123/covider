package com.project.covider.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.covider.models.HealthReport;

@Repository
public interface HealthReportRepository extends CrudRepository<HealthReport, String> {

	@Query("SELECT hr FROM HealthReport hr WHERE hr.email = ?1")
	List<HealthReport> fetchHealthReport(String email);
	
}
