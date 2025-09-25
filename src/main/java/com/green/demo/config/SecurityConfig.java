package com.green.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.green.demo.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private AuthenticationConfiguration authenticationConfiguration;
	public SecurityConfig(
			AuthenticationConfiguration authenticationConfiguration) {
		this.authenticationConfiguration = authenticationConfiguration;
	}

	// 2. authenticationManager Bean 등록
	@Bean
	public  AuthenticationManager  authenticationManager (
		AuthenticationConfiguration  configuration	
			) throws Exception {		
		return configuration.getAuthenticationManager();
	}
	
	//  인가 작업설정
	@Bean
	public  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		  .csrf( (auth) -> auth.disable() )  // session  방식 활성화, jwt 비활성화'
		 
		  // form Login 기능 비활성화 
    	  //   .formLogin( Customizer.withDefaults() ); 
	      .formLogin( (auth)-> auth.disable() ) 
		 
	      .httpBasic( (auth)->auth.disable() )
	       
	      .authorizeHttpRequests( (auth) -> auth
	    	   .requestMatchers("/login", "/", "/join").permitAll()  
	    	   .requestMatchers("/admin").hasRole("ADMIN")
	    	   .anyRequest().authenticated() )
	      
	      // 1. 커스텀 로그인 필터 등록 LoginFilter 필터 추가
	      // Before, After, At(기존대체)	      
	      .addFilterAt( new LoginFilter(
	    		  authenticationManager( authenticationConfiguration ) ), 
	    	 UsernamePasswordAuthenticationFilter.class)     
	      
	      // 세션 설정 JWT 는 STATELESS , 중요 
	      .sessionManagement( (session) -> session
	    		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
    	  ;
		
		return   http.build();
		
	}
	
	// 암호화에 필요한 클래스
	@Bean
	public BCryptPasswordEncoder  bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
}













