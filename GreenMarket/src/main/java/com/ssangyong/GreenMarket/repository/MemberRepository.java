package com.ssangyong.GreenMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MemberEntity;

public interface MemberRepository extends CrudRepository<MemberEntity, String>, JpaRepository<MemberEntity, String>{
	
}
