package com.green.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.demo.dto.JoinDTO;
import com.green.demo.entity.UserEntity;
import com.green.demo.repository.UserRepository;

@Service
public class JoinService {
	
	private  BCryptPasswordEncoder  bCryptPasswordEncoder;
	private  UserRepository         userRepository;
	
	public JoinService
			UserRepository         userRepository,
			BCryptPasswordEncoder  bCryptPasswordEncoder
			) {
		this.userRepository        =  userRepository;
		this.bCryptPasswordEncoder =  bCryptPasswordEncoder;
	}

	public void addUser(JoinDTO joinDTO) {
		
		String   username  =  joinDTO.getUsername();
		String   password  =  joinDTO.getPassword();
		String   name      =  joinDTO.getName();
		
		// 회원정보가 있는지 검색한다
		Boolean  isExist   =  userRepository.existsByUsername(username);
		
		if( isExist )
			return;
		
		UserEntity  user = new UserEntity();
		user.setUsername(username);
		user.setPassword( bCryptPasswordEncoder.encode( password ) );
		user.setName(name);
		user.setRole("ROLE_ADMIN");
		
		userRepository.save(user);
		
	}
	
}
