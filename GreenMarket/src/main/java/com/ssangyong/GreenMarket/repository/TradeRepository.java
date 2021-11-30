package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;

public interface TradeRepository extends CrudRepository<TradeEntity, Integer>{
	public List<TradeEntity> findAllByBuyMember(MemberEntity member);
}
