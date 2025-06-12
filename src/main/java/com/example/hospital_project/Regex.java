package com.example.hospital_project;

import java.util.regex.Pattern;

public class Regex {

	 private static final String PHONE_Regex = "^\\d{10}$";
	  
	 private static final String PASSWORD_Regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{8,}$";
	 
	 
	   public static boolean isValidPhone(String phone){
	       return Pattern.matches(PHONE_Regex, phone);
	   }
	   
	   public static boolean isValidPass(String pass){
	       return Pattern.matches(PASSWORD_Regex, pass);
	   }
}
