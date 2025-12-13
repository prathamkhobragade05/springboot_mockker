package com.projects.mockker.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;    

import com.projects.mockker.service.OtpEmailService;
import com.projects.mockker.service.UserService;
import com.projects.mockker.model.CredentialModel;
import com.projects.mockker.model.UserModel;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	OtpEmailService otpEmailService;


	@PostMapping("/login")											//---------------login via password
	public Long login(@RequestBody CredentialModel credential ){
		Long userId=userService.loginPassword(credential.getEmail(),credential.getOtp_password());
		if(userId==null){
			return 0L;
		}
		return userId;
	}
	
	@PostMapping("/login/forgetpass")										//---------------login forget password
	public Boolean sendPassword(@RequestBody CredentialModel credential) {
		if(userService.isExistsEmail(credential.getEmail())) {
			UserModel user= userService.getUser(credential.getEmail());
			return otpEmailService.sendPasswordLogin(credential.getEmail(),user.getPassword());
		}else {
			return false;
		}
	}
	
	@PostMapping("/login/send-otp")											//---------------login send OTP
	public ResponseEntity<String> sendLoginOtp(@RequestBody CredentialModel credential){
		if(userService.isExistsEmail(credential.getEmail())) {
			String otp = otpEmailService.generateOtp(credential.getEmail());
			otpEmailService.sendEmailOtp(credential.getEmail(), otp);
			return ResponseEntity.ok("email sent to "+credential.getEmail());
		}else {
			return ResponseEntity.status(409).body("");
		}
	}
	
	@PostMapping("/login/verify-otp")											//---------------login verify OTP
	public Long verifyLoginOtp(@RequestBody CredentialModel credential) {
		return otpEmailService.verifyOtpLogin(credential.getEmail(),credential.getOtp_password());
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	@PostMapping("/register/send-otp")											//---------------register send OTP
	public ResponseEntity<String> sendRegisterOtp(@RequestBody CredentialModel credential){
		if(userService.isExistsEmail(credential.getEmail())) {
			return ResponseEntity.status(409).body("Email id already exsits");
		}
		String otp = otpEmailService.generateOtp(credential.getEmail());
		otpEmailService.sendEmailOtp(credential.getEmail(), otp);
		
		return ResponseEntity.ok("otp sent");
	}
	
	@PostMapping("register/verify-otp")											//---------------register verify OTP
	public ResponseEntity<String> verifyRegisterOtp(@RequestBody CredentialModel credential) {
		boolean isValid=otpEmailService.verifyOtpRegister(credential.getEmail(), credential.getOtp_password());
		return isValid? ResponseEntity.ok("Verified"):ResponseEntity.status(400).body("Invalid Otp");
	}
	
	@PostMapping("/register")											//---------------register
	public Long register(@RequestBody UserModel user){
		try {
			UserModel registeredUser = userService.registerUser(user);
			return registeredUser.getId();
		}
		catch(Exception e) {
			System.out.println(ResponseEntity.badRequest().body(e.getMessage()));
            return null;

		}
	}
	
}
