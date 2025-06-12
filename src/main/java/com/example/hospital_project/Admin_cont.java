package com.example.hospital_project;

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
public class Admin_cont {

	@Autowired
	Admin_repo ar;
	
	@GetMapping("/")
	public String index() {
		
		return "index";
	}
	
	
	@GetMapping("/admin_register")
	public String admin_register(Model m) {
		Admin_model admin = new Admin_model();
		m.addAttribute("admin", admin);
		return "admin_register";
	}
	
	@PostMapping("/admin_register")
	public String admin_add(@Valid @ModelAttribute("admin") Admin_model admin,@RequestParam("cpass") 
	String cpass,
			BindingResult result,
			Model m) throws Exception{
		if(result.hasErrors()) {
			return "admin_register";
		}
		
		String password = admin.getPass();
		if(!Regex.isValidPass(password)) {
			m.addAttribute("err", "Password should contain atleast 8 characters with 1 upper case, 1 lower case , 1 number and 1 special character [!@#$%]");
			m.addAttribute("admin", admin);
			return "admin_register";
		}
		if(!cpass.equals(password)) {
			m.addAttribute("err", "Password and Conform Password should be same !!!");
			m.addAttribute("admin", admin);
			return "admin_register";
		}
		
		if(ar.admin_idCount(admin.getId()) > 0) {
			m.addAttribute("err", "Id : "+admin.getId() + " already exists, use a different ID");
			m.addAttribute("admin", admin);
			return "admin_register";
		}
		
			String epass = EN_DE.encrypt(admin.getPass());
			
			
		
		ar.admin_add(admin.getId(),admin.getName(), admin.getEmail(), epass);
		m.addAttribute("msg", "Admin Registered Sucessfully");
		
		
		
		
		return "admin_register";
	}
	
	@GetMapping("/admin_login")
	public String admin_login(Model m) {
		Admin_model admin = new Admin_model();
		m.addAttribute("admin", admin);
		return "admin_login";
	}
	
	@PostMapping("/admin_login")
	public String admin_login(@RequestParam("email") String email,@RequestParam("pass") String pass,
			Model m) throws Exception{
		
		Admin_model a = ar.admin_login(email);
		
		
		if(a != null) {
			String dpass = EN_DE.decrypt(a.getPass());
			if (dpass.equals(pass)) {
				m.addAttribute("name", a.getName());
				m.addAttribute("msg", "Admin login sucess !!");
				String id = a.getId();
				m.addAttribute("id", id);
				
				return "admin_dashboard";
			}
			else {
				m.addAttribute("err", "Invalid password !!");
				return "admin_login";
			}
		}
		else {
			m.addAttribute("err", "Enter a valid email !!");
			return "admin_login";
		}
		
	}
	
	@GetMapping("/admin_forgot_pass")
	public String admin_forgot() {
		return "admin_forgot";
	}
	
	@GetMapping("/verify_email")
	public String verify_email(@Param("email") String email,Model m) throws Exception{
		Admin_model admin = ar.admin_verify_email(email);
		if(admin != null) {
			String dpass = EN_DE.decrypt(admin.getPass());
			m.addAttribute("msg", "Email verified sucessfully");
			m.addAttribute("pass", dpass);
			
		}
		else {
			m.addAttribute("err", "Invalid mail Id !!!");
		}
		return "admin_forgot";
	}
	
	@GetMapping("/admin_changePass/{id}")
	public String admin_changePass(@PathVariable("id") String id,Model m) {
		Admin_model admin = ar.admin_search(id);
		
		
		m.addAttribute("id", id);
	return "admin_changePass";
	
	}
	
	@PostMapping("/admin_update_pass")
	public String admin_update_pass(
			@RequestParam("opass") String opass,@RequestParam("npass") String npass,
			@RequestParam("cpass") String cpass,@RequestParam("id") String id,
			Model m,RedirectAttributes re)
		throws Exception{
		Admin_model admin = ar.admin_search(id);
		String apass = EN_DE.decrypt(admin.getPass());

		if(!(opass.equals(apass)))
				{
			re.addFlashAttribute("err", opass+ " "+ apass +" The current password you entered is wrong");
			return "redirect:/admin_changePass/"+id;
		}	
		
		
		if(!npass.equals(cpass)){
			re.addFlashAttribute("err", "new password and conform password should be same");
			return "redirect:/admin_changePass/"+id;
		}	
			
		try {
			ar.admin_update_pass(EN_DE.encrypt(npass), id);
			re.addFlashAttribute("msg", "Password changed Sucessfully ");
			
		}catch(Exception e) {
			re.addFlashAttribute("err", "Update Failed: "+e.getMessage());
		}
		
		return "redirect:/admin_changePass/"+id;
	}
	
	@GetMapping("/admin_account/{id}")
	public String admin_account(@PathVariable("id") String id,Model m) throws Exception {
		Admin_model admin = ar.admin_search(id);
		String dpass = EN_DE.decrypt(admin.getPass());
		admin.setPass(dpass);
		m.addAttribute("id", id);

		m.addAttribute("admin", admin);
		return "admin_MyAccount";
	}
	
	@PostMapping("/edit_admin")
	public String editAdmin(@Valid @ModelAttribute("admin") Admin_model admin, 
			BindingResult result, RedirectAttributes m)  {
		try {
			
			ar.admin_edit(admin.getId(),admin.getName(),admin.getEmail());
			
			m.addFlashAttribute("msg", "Admin Updated Sucessfully !!!");
			
		}catch(Exception e) {
			m.addFlashAttribute("err", "error: "+e.getMessage());
		}
			
		
		return "redirect:/admin_account/"+admin.getId();
	}
	
	@GetMapping("/admin_dashboard/{id}")
	public String ad(@PathVariable("id") String id,Model m) {
		m.addAttribute("id", id);
	return "admin_dashboard";
	}
	
	@GetMapping("/all_admin/{id}")
	public String all_admin(@PathVariable("id") String id,Model m) {
		List<Admin_model> admins = ar.admin_fetch();
		m.addAttribute("id", id);
		m.addAttribute("admins", admins);
	return "all_admins";}
}
