package com.ssangyong.GreenMarket.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ssangyong.GreenMarket.model.ItemCategoryEntity;

public interface ItemCategoryRepository extends CrudRepository<ItemCategoryEntity, Integer>{
	
	ItemCategoryEntity findByIcName(String icName);
}
