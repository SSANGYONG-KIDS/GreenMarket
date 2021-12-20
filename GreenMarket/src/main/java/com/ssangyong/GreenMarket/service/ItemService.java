package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepo;
	
	// page
	public Page<ItemEntity> selectAll(ItemPageVO pvo) {
		Predicate p = itemRepo.makePredicate(pvo);

		// makePaging(방향, sort할 field)
		Pageable pageable = pvo.makePaging(0, "iId");
		Page<ItemEntity> result = itemRepo.findAll(p, pageable);
		return result;
	}
	
	// list조회
	public List<ItemEntity> selectAll() {
		return (List<ItemEntity>) itemRepo.findAll();
	}
	
	// 최근 순 조회
	/*
	public List<ItemEntity> selectRecently() {
		return (List<ItemEntity>) itemRepo.findAllOrderByiIdDesc();
	}
	*/
	
	// mylist 조회
	public List<ItemEntity> selectMyList(MemberEntity member) {
		return (List<ItemEntity>)itemRepo.findByMember(member);
	}

	// 아이디로 찾기
	public ItemEntity selectById(Integer iId) {
		return itemRepo.findById(iId).get();
	}

	// 삽입
	public ItemEntity insertItem(ItemEntity item) {
		return itemRepo.save(item);
	}

	// 수정
	public ItemEntity updateItem(ItemEntity item) {
		return itemRepo.save(item);
	}

	// 제거
	public int deleteItem(Integer iId) {
		int ret = 0;
		try {
			itemRepo.deleteById(iId);
			ret = 1;
		} catch (Exception ex) {
			}
		return ret;
	}

}
