package com.green.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.green.demo.dto.CustomerUserDetails;
import com.green.demo.entity.UserEntity;
import com.green.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private  final  UserRepository   userRepository;
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		// db 에서  조회
		UserEntity  user = userRepository.findByUsername( username );
		
		if( user != null ) {
			//UserDetails에 담아서 return 하면 AutneticationManager 가 검증
			return new CustomerUserDetails( user );
		}
		
		return null;
	}
	
}







