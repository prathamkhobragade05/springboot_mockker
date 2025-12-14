package com.projects.mockker.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.mockker.model.UserModel;
import com.projects.mockker.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	
	public boolean isExistsEmail(String email) {
		return userRepo.existsByEmail(email);
	}
	
	public Long loginPassword(String email,String password) {			//---------------login via password
		System.out.println(email+" "+password);
		UserModel user= userRepo.findByEmail(email);
		Long UserId=0l;
		if(user.getPassword().equals(password)) {
			UserId=user.getId();
		}
		return UserId;
	}
	
	public Long LoginOtp(String email) {			//---------------login via OTP
		UserModel user=userRepo.findByEmail(email);
		if(user==null) return null;
		Long userId=user.getId();
		return userId;
	}
	
	
	public UserModel registerUser(UserModel user) throws Exception {			//---------------register
		// TODO Auto-generated method stub
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new Exception(user.getEmail()+" already registered!");
		}
		userRepo.save(user);

		return userRepo.save(user);
	}

	public UserModel getUser(String email) {
		UserModel user =userRepo.findByEmail(email);
		return user;
	}
	
}
