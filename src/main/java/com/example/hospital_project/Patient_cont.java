package com.example.hospital_project;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.validation.Valid;

@Controller
public class Patient_cont {

	@Autowired
	Patient_repo pr;
	
	
	
	@GetMapping("/patient_register")
	public String patient_register(Model m) {
		Patient_model patient = new Patient_model();
		m.addAttribute("patient", patient);
		return "patient_register";
	}
	
	@PostMapping("/patient_register")
	public String patient_add(@Valid @ModelAttribute("patient") Patient_model patient,
			BindingResult result,
			@RequestParam("cpass") 
	String cpass,
			
			Model m)
		throws Exception{
		if(result.hasErrors()) {
			return "patient_register";
		}
		
		String password = patient.getPass();
		
		if(!Regex.isValidPass(password)) {
			m.addAttribute("err", "Password should contain atleast 8 characters with 1 upper case, 1 lower case , 1 number and 1 special character [!@#$%]");
			return "patient_register";
		}
		if(!cpass.equals(password)) {
			m.addAttribute("err", "Password and Conform Password should be same !!!");
			return "patient_register";
		}
		
		if(pr.patient_isUnique(patient.getPhone(),patient.getEmail()) > 0) {
			m.addAttribute("err", "EMail or Mobile number already exists !!!");
			return "patient_register";
		}
		
		if(!Regex.isValidPhone(patient.getPhone())) {
			m.addAttribute("err", "Enter a 10 digit mobile number");
			return "patient_register";
		}
		
		
			String epass = EN_DE.encrypt(patient.getPass());
			
			
		try {
			pr.patient_add(patient.getName(), patient.getPhone(),patient.getEmail(), epass,patient.getGender(),patient.getDob(),patient.getAddress());
			m.addAttribute("msg", "Registered Sucessfully");
			
		}catch(Exception e) {
			m.addAttribute("err", "Registeration Failed"+e.getMessage());
		}
		
		
		
		
		
		return "patient_register";
	}
	
	@GetMapping("/patient_login")
	public String patient_login(Model m) {
		
		return "patient_login";
	}
	
	@PostMapping("/patient_login")
	public String admin_login(@RequestParam("phone") String phone,@RequestParam("pass") String pass,
			Model m) throws Exception{
		
		Patient_model patient = pr.patient_login(phone);
		
		
		if(patient != null) {
			String dpass = EN_DE.decrypt(patient.getPass());
			if (dpass.equals(pass)) {
				m.addAttribute("id", patient.getId());
				m.addAttribute("msg", "login sucess !!");
				return "patient_page";
			}
			else {
				m.addAttribute("err", "Invalid password !!");
				return "patient_login";
			}
		}
		else {
			m.addAttribute("err", "Enter a valid Mobile number !!");
			return "patient_login";
		}
		
	}
	
	@GetMapping("/patient_forgot_pass")
	public String patient_forgot() {
		return "patient_forgot";
	}
	
	@GetMapping("/patient_changePass/{id}")
	public String patient_changePass(@PathVariable("id") String id,Model m) {
		Patient_model patient = pr.patient_search(Long.valueOf(id));
		m.addAttribute("patient", patient);
		return "change_pass";
	}
	
	@GetMapping("/verify_patient")
	public String verify_cemail(@Param("email") String email,Model m) throws Exception{
		Patient_model admin = pr.patient_verify_email(email);
		if(admin != null) {
			String dpass = EN_DE.decrypt(admin.getPass());
			m.addAttribute("msg", "Email verified sucessfully");
			m.addAttribute("pass", dpass);
			
		}
		else {
			m.addAttribute("err", "Invalid mail Id !!!");
		}
		return "patient_forgot";
	}
	
	@PostMapping("/patient_update_pass")
	public String patient_update_pass(
			@RequestParam("opass") String opass,@RequestParam("npass") String npass,
			@RequestParam("cpass") String cpass,@RequestParam("id") String id,
			Model m,RedirectAttributes re) throws Exception
		{
		Patient_model cust = pr.patient_search(Long.valueOf(id));
		String custpass = EN_DE.decrypt(cust.getPass());

		
		
		if(!opass.equals(custpass)){
			re.addFlashAttribute("err", "The current password you entered is wrong");
			return "redirect:/customer_changePass/"+id;
		}	
		
		if(!Regex.isValidPass(npass)) {
			re.addFlashAttribute("err", "Password should contain atleast 8 characters with 1 upper case, 1 lower case , 1 number and 1 special character [!@#$%]");
			return "redirect:/customer_changePass/"+id;
		}
		
		if(!npass.equals(cpass)){
			re.addFlashAttribute("err", "new password and conform password should be same");
			return "redirect:/customer_changePass/"+id;
		}	
			
		try {
			pr.patient_update_pass(EN_DE.encrypt(npass),Long.valueOf(id));
			re.addFlashAttribute("msg", "Password changed Sucessfully ");
			
		}catch(Exception e) {
			re.addFlashAttribute("err", "Update Failed: "+e.getMessage());
		}
		
		return "redirect:/patient_changePass/"+id;
	}
	
	@GetMapping("/patient_profile/{id}")
	public String customer_profile(@PathVariable("id") String id,Model m) {
		Patient_model patient = pr.patient_search(Long.valueOf(id));
		m.addAttribute("patient", patient);
		return "patient_profile";
	}
	
	@PostMapping("/patient_update")
	public String patient_update(
			@Valid @ModelAttribute("patient") Patient_model patient,
			BindingResult result,
//			@RequestParam("phone") String phone,@RequestParam("email") String email,
//			@RequestParam("name") String name,@RequestParam("id") String id,
//			@RequestParam("dob") String dob,
//			@RequestParam("address") String addresss,
//			@RequestParam("") String ,
			Model m,RedirectAttributes re)
		throws Exception{
		
		
		if(!Regex.isValidPhone(patient.getPhone())){
			m.addAttribute("err", "Enter a 10 digit mobile number");
			return "patient_profile";
		}	
			
		try {
			pr.patient_update(patient.getName(), patient.getPhone(), patient.getEmail(),patient.getId(),patient.getGender(),patient.getDob(),patient.getAddress());
			re.addFlashAttribute("msg", "Updated Sucessfully done");
			
		}catch(Exception e) {
			re.addFlashAttribute("err", "Update Failed: "+e.getMessage());
		}
		
		return "redirect:/patient_profile/"+patient.getId();
	}
	
	@GetMapping("/all_patients/{id}")
	public String all_admin(@PathVariable("id") String id,Model m) {
		List<Patient_model> patients = pr.patient_fetch();
		m.addAttribute("id", id);
		m.addAttribute("patients", patients);
	return "all_patients";}
	
}
