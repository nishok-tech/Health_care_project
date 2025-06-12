package com.example.hospital_project;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import jakarta.transaction.Transactional;


public interface Patient_repo extends JpaRepository<Patient_model, Long> {
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="insert into patient(name,phone,email,pass,gender,dob,address) values(:name,:phone,:email,:pass,:gender,:dob,:address)")
	void patient_add(@Param("name") String name,@Param("phone") String phone
			,@Param("email") String email,@Param("pass") String pass,
			@Param("gender") String gender,@Param("dob") LocalDate dob,@Param("address") String address);
	
	@Query(nativeQuery = true,value="select count(*) from patient where email = :email or phone = :phone ")
	int patient_isUnique(@Param("phone") String phone
			,@Param("email") String email);
	
	
	
	@Query(nativeQuery = true,value="select * from patient where phone = :phone ")
	Patient_model patient_login(@Param("phone") String phone);
	
	@Query(nativeQuery = true,value="select * from patient where id = :id ")
	Patient_model patient_search(@Param("id") Long id);
	
	@Query(nativeQuery = true,value="select * from patient where email = :email ")
	Patient_model patient_verify_email(@Param("email") String email);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update patient set pass=:npass where id=:id")
	void patient_update_pass(@Param("npass") String npass,@Param("id") Long id);
	
	@Query(nativeQuery = true,value="select * from patient ")
	List<Patient_model> patient_fetch();
	
	
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update patient set gender=:gender,dob=:dob,address=:address,name=:name,phone=:phone,email=:email where id=:id")
	void patient_update(@Param("name") String name,@Param("phone") String phone
			,@Param("email") String email,@Param("id") Long id,
			@Param("gender") String gender,@Param("dob") LocalDate dob,@Param("address") String address);
	
}
