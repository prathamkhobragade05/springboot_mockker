package com.projects.mockker.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.projects.mockker.model.UserModel;

@Service
public class OtpEmailService {
	@Autowired
	private JavaMailSender mailSender;
	
	private Map<String,String> otpStorage=new HashMap<>();

	public String generateOtp(String email) {											//---------------generate OTP
		String otp =String.valueOf((int)(Math.random()*900000)+100000);
		otpStorage.put(email, otp);
		return otp;
	}
	
	@Async
	public void sendEmailOtp(String email, String otp) {											//---------------send OTP Email
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("OTP Verification");
		message.setText("Yout OTP is: "+otp);
		mailSender.send(message);
	}

	public boolean verifyOtp(String email, String otp) {											//---------------verify OTP
		return otp.equals(otpStorage.get(email))? true:false;
	}

	public Boolean sendPassword(String email,String Password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Request For Password!");
		message.setText("Your password is: "+Password);
		mailSender.send(message);
		return true;
		
	}
	
	

}
