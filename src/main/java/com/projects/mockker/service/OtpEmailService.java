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
	@Value("${sendgrid.api.key}")
    private String sendGridApiKey;
	
	private Map<String,String> otpStorage=new HashMap<>();

	public String generateOtp(String email) {											//---------------generate OTP
		String otp =String.valueOf((int)(Math.random()*900000)+100000);
		otpStorage.put(email, otp);
		return otp;
	}
	
	@Async
	public void sendEmailOtp(String email, String otp) {											//---------------send OTP Email
		Email from = new Email("yourverifiedemail@example.com");
        String subject = "Mockker";
        Email to = new Email(toEmail);
		Content content = new Content("text/plain", "Your OTP is: " + otp);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

		try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
	}

	public boolean verifyOtp(String email, String otp) {											//---------------verify OTP
		return otp.equals(otpStorage.get(email))? true:false;
	}

	public Boolean sendPassword(String email,String Password) {
		Email from = new Email("yourverifiedemail@example.com");
        String subject = "Request For Password!";
        Email to = new Email(toEmail);
		Content content = new Content("Your password is: "+Password);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

		try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
			return true

        } catch (IOException ex) {
            throw new RuntimeException(ex);
			return false;
        }
		
	}
}
