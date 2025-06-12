package com.example.hospital_project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="admin")
public class Admin_model {

	@Id
	@NotBlank(message="Enter Admin Id")
	private String id;
	
	@NotBlank(message="Enter Admin Name")
	private String name;
	
	@NotBlank(message="Enter Email id")
	private String email;
	
	@NotBlank(message="Enter Password")
	private String pass;

	public Admin_model() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
}
