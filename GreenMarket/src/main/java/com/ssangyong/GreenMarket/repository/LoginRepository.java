package com.ssangyong.GreenMarket.repository;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MemberEntity;

public interface LoginRepository extends CrudRepository<MemberEntity, String> {

}
