package com.example.hospital_project;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name="appointments")
public class Appointment_model {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id")
	 private Long id;
	 
	
	 @Column(name = "patient_id")
	private String patient_id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "appointment_date")
	private  LocalDate appointment_date;	
 
   
    
    @Column(name = "appointment_time")
    private String appointment_time;
    
   
    
    @NotBlank(message="department is required")
    @Column(name = "department")
    private String department;
    
    @Column(name = "symptoms")
    private String symptoms;
   
   
    @Column(name = "status")
    private String status;


	public Appointment_model() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPatient_id() {
		return patient_id;
	}


	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}


	public LocalDate getAppointment_date() {
		return appointment_date;
	}


	public void setAppointment_date(LocalDate appointment_date) {
		this.appointment_date = appointment_date;
	}


	public String getAppointment_time() {
		return appointment_time;
	}


	public void setAppointment_time(String appointment_time) {
		this.appointment_time = appointment_time;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getSymptoms() {
		return symptoms;
	}


	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	

    
}
