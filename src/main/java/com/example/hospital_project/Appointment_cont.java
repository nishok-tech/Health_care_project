package com.example.hospital_project;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class Appointment_cont {

	@Autowired
	Appointment_repo ar;
	
	@Autowired
	Patient_repo pr;
	
	@GetMapping("/patient_new_appointment/{id}")
	public String new_appointment(@PathVariable("id") String id,Model m) {
		Appointment_model appointment = new Appointment_model();
		m.addAttribute("appointment",appointment);
		m.addAttribute("id", id);
		return "patient_new_appointment";
	}
	
	@PostMapping("/new_appointment")
	public String new_booking1(
			@Valid @ModelAttribute("app") Appointment_model app,
					BindingResult result,
					RedirectAttributes m
			) {
		
		if(result.hasErrors()) {
			return "patient_new_appointment";
		}
		ar.new_booking(app.getPatient_id(),app.getAppointment_date(),app.getAppointment_time(),app.getDepartment(),app.getSymptoms());
	
		m.addFlashAttribute("msg", "Appointment made successfully");
		return "redirect:/patient_new_appointment/"+app.getPatient_id();
	}
	
	@GetMapping("/patient_appointment_history/{id}")
	public String patient_appointment_history(@PathVariable("id") String id,
			Model m) {
		List<Appointment_model> appointments = ar.patient_appointment_search(id);
		m.addAttribute("appointments",appointments);
		m.addAttribute("patient_id", id);
		return "patient_appointment_history";
	}
	
	@GetMapping("/all_appointments/{id}")
	public String all_bookings(@PathVariable("id") String id,Model m) {
		List<Appointment_model> appointments = ar.all_appointments();
		m.addAttribute("a_id", id);
		m.addAttribute("appointments",appointments);
		return "all_appointments";
	}
	
	@GetMapping("/view_appointment/{id}/{a_id}")
	public String view_booking(@PathVariable("id") String id,
			@PathVariable("a_id") String a_id,Model m) throws Exception{
		
		Appointment_model Appointment = ar.appointment_search(Long.valueOf(id));
		Patient_model Patient = pr.patient_search(Long.valueOf(Appointment.getPatient_id()));
//		System.out.print(booking.getCust_id());
		m.addAttribute("Appointment",Appointment);
		m.addAttribute("Patient",Patient);
		m.addAttribute("a_id",a_id);
		return "view_appointment";
	}
	
	@PostMapping("/appointment_update/{bid}/{a_id}")
	public String booking_update(RedirectAttributes m,@PathVariable("a_id") String a_id,
			@RequestParam("status") String status,@PathVariable("bid") String bid) {
		try {
			ar.appointment_update(status,Long.valueOf(bid));
			m.addFlashAttribute("msg","Appointment updated sucessfully");
			
		}catch(Exception e) {
			m.addFlashAttribute("err","Error occured: "+e.getMessage());
		}
		
		return "redirect:/view_appointment/"+bid+"/"+a_id;
	}
}
