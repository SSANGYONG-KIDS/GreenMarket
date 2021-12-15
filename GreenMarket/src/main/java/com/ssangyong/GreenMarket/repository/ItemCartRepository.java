package com.ssangyong.GreenMarket.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.ItemCartEntity;
import com.ssangyong.GreenMarket.model.ItemCartEntityId;


public interface ItemCartRepository  extends CrudRepository<ItemCartEntity, ItemCartEntityId>, QuerydslPredicateExecutor<ItemCartEntity> {

}
