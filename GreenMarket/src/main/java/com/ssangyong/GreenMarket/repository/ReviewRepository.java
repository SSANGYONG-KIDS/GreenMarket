package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.ReviewEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;


public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer>, QuerydslPredicateExecutor<ReviewEntity> {

	public List<ReviewEntity> findByMember(MemberEntity member);
	public List<ReviewEntity> findByItem(ItemEntity item);
	public ReviewEntity findByTrade(TradeEntity trade);
}
