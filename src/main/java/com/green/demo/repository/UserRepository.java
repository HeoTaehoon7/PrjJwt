package com.green.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.demo.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	//  해당 아이디(username)가 존재하는 지 체크 함수 
	Boolean existsByUsername(String username);

	// 해당 아이디(username)로 회원정보 검색한다
	UserEntity findByUsername(String username);

}
