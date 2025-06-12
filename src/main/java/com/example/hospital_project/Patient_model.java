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
@Table(name="patient")
public class Patient_model {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	@NotBlank(message="Enter Customer Name")
	private String name;
	
	@NotBlank(message="Enter Mobile number")
	private String phone;
	
	@NotBlank(message="Enter Email id")
	private String email;
	
	@NotBlank(message="Enter Password")
	private String pass;
	
	@NotBlank(message="fill Gender")
	private String gender;
	
	@NotBlank(message="Enter address")
	private String address;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob")
	private  LocalDate dob;


	public Patient_model() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public LocalDate getDob() {
		return dob;
	}


	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	
}
