package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPhotoEntity;

public interface ItemPhotoRepository extends CrudRepository<ItemPhotoEntity, Integer>{
	public List<ItemPhotoEntity> findByItem(ItemEntity item);

}
