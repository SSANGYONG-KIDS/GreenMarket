package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.ReviewEntity;


public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer>{

	public List<ReviewEntity> findByMember(MemberEntity member);
}
