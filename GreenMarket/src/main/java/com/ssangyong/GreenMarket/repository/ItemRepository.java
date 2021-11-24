package com.ssangyong.GreenMarket.repository;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemEntityId;

public interface ItemRepository extends CrudRepository<ItemEntity, ItemEntityId>{

}
