package com.example.hospital_project;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.transaction.Transactional;


public interface Appointment_repo extends JpaRepository<Appointment_model, Long>{

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="sp_appointment_add :patient_id,:appointment_date,:appointment_time,:department,:symptoms")
	void new_booking(
			@Param("patient_id") String patient_id,
			@Param("appointment_date") LocalDate  appointment_date,	
			@Param("appointment_time") String  appointment_time,
			@Param("department") String  department,	
			@Param("symptoms") String  symptoms
			
			);
	

	@Query(nativeQuery = true,value="select * from appointments")
	List<Appointment_model> all_appointments();
	
	@Query(nativeQuery = true,value="select * from appointments where id = :id")
	Appointment_model appointment_search(@Param("id") Long id);
	
	
	@Query(nativeQuery = true,value="select * from appointments where patient_id=:patient_id")
	List<Appointment_model> patient_appointment_search(@Param("patient_id") String patient_id);

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update appointments set status=:status where id = :id")
	void appointment_update(@RequestParam("status") String status,@Param("id") Long id);


}
