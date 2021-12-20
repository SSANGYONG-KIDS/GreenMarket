package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.ItemCartEntity;
import com.ssangyong.GreenMarket.model.ItemCartEntityId;


public interface ItemCartRepository  extends CrudRepository<ItemCartEntity, ItemCartEntityId>, QuerydslPredicateExecutor<ItemCartEntity> {
	@Query(value="SELECT it.i_id, it.i_content, it.i_price, it.i_regdate, it.i_state, it.i_title, it.i_tstate, it.m_id, it.i_category FROM itemcart ic left outer join item it ON (ic.i_id = it.i_id) WHERE ic.m_id=?1", nativeQuery = true)
	public List<Object[]> allFindByMId(String mId);
}
