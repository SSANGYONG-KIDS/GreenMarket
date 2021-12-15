package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MemberEntity;

public interface MemberRepository extends CrudRepository<MemberEntity, String>{
	 List<MemberEntity> findBymNickname(String mNickname);
}
