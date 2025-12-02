package com.projects.mockker.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.mockker.service.OtpEmailService;
import com.projects.mockker.service.QuestionService;
import com.projects.mockker.service.UserService;
import com.projects.mockker.model.UserModel;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	OtpEmailService otpEmailService;


	@PostMapping("/login")											//---------------login via password
	public Long login(@RequestBody UserModel user){
		return userService.login(user.getEmail(), user.getPassword());
	}
	
	@PostMapping("/login/forgetpass")
	public Boolean sendPassword(@RequestParam("email") String email) {
		if(userService.isExistsEmail(email)) {
			UserModel user= userService.getUser(email);
			return otpEmailService.sendPassword(email,user.getPassword());
		}else {
			return false;
		}
	}
	
	@PostMapping("/login/send-otp")											//---------------login send OTP
	public ResponseEntity<String> sendLoginOtp(@RequestParam("email") String email){
		if(userService.isExistsEmail(email)) {
			String otp = otpEmailService.generateOtp(email);
			otpEmailService.sendEmailOtp(email, otp);
			return ResponseEntity.ok("email sent to "+email);
		}else {
			return ResponseEntity.status(409).body("");
		}
	}
	
	@PostMapping("/login/verify-otp")											//---------------login verify OTP
	public ResponseEntity<String> verifyLoginOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
		boolean isValid=otpEmailService.verifyOtp(email, otp);
		return isValid? ResponseEntity.ok("Verified"):ResponseEntity.status(400).body("Invalid Otp");
	}
	
	@PostMapping("/register")											//---------------register
	public ResponseEntity<?> register(@RequestBody UserModel user){
		try {
			UserModel regsiteredUser = userService.registerUser(user);
			return ResponseEntity.ok(regsiteredUser);
		}
		catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

		}
	}
	
	@PostMapping("/register/send-otp")											//---------------register send OTP
	public ResponseEntity<String> sendRegisterOtp(@RequestParam("email") String email){
		if(userService.isExistsEmail(email)) {
			return ResponseEntity.status(409).body("Email id already exsits");
		}
		String otp = otpEmailService.generateOtp(email);
		otpEmailService.sendEmailOtp(email, otp);
		
		return ResponseEntity.ok("otp sent");
	}
	
	
	@PostMapping("register/verify-otp")											//---------------register verify OTP
	public ResponseEntity<String> verifyRegisterOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
		boolean isValid=otpEmailService.verifyOtp(email, otp);
		return isValid? ResponseEntity.ok("Verified"):ResponseEntity.status(400).body("Invalid Otp");
		
	}
	
	@GetMapping("")
	public Long userLogin(@RequestParam("email") String email) {
		return userService.userLogin(email);
	}
	
	
	
}
