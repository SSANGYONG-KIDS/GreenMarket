package com.ssangyong.GreenMarket.repository;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.ItemCategoryEntity;

public interface ItemCategoryRepository extends CrudRepository<ItemCategoryEntity, Integer>{
	
	ItemCategoryEntity findByIcName(String icName);
}
