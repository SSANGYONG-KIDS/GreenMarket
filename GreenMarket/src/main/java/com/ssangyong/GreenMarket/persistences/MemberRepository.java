package com.ssangyong.GreenMarket.persistences;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MemberEntity;


public interface MemberRepository extends CrudRepository<MemberEntity, String> {


}
