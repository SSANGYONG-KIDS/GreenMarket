package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.ItemCartEntity;
import com.ssangyong.GreenMarket.model.ItemCartEntityId;


public interface ItemCartRepository  extends CrudRepository<ItemCartEntity, ItemCartEntityId>, QuerydslPredicateExecutor<ItemCartEntity> {
	@Query(value="SELECT it.* FROM itemcart ic left outer join item it ON (ic.i_id = it.i_id) WHERE ic.m_id=?1", nativeQuery = true)
	public List<Object[]> allFindByMId(String mId);
}
