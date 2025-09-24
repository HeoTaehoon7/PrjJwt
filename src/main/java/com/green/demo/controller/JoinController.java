package com.green.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.demo.dto.JoinDTO;
import com.green.demo.service.JoinService;

@RestController
public class JoinController {
	
	// @Autowired  -> 생성자 주입방법으로 대체
	private  JoinService   joinService;
	// 생성자 주입 : @Autowired 대신 사용
	public   JoinController(JoinService  joinService) {
		this.joinService = joinService;
	}
	
	
	@PostMapping("/join")     // Insert
	public  String  addUser(JoinDTO  joinDTO) {
		
		joinService.addUser( joinDTO );
		return "ok";
		
	}
	
	
}
