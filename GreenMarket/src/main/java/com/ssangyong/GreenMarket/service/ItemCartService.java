package com.ssangyong.GreenMarket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.ItemCartEntity;
import com.ssangyong.GreenMarket.model.ItemCartEntityId;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.repository.ItemCartRepository;
import com.ssangyong.GreenMarket.repository.ItemRepository;


@Service
public class ItemCartService {
	@Autowired
	ItemCartRepository icRepository;
	
	// 조회
	public List<ItemCartEntity> selectList(Integer iId) {
		List<ItemCartEntity> alllist = (List<ItemCartEntity>)icRepository.findAll();
		List<ItemCartEntity> itemCarts = new ArrayList();
		for(ItemCartEntity ic : alllist) {
			if(ic.getIcId().getItem().getIId()==iId) {
				itemCarts.add(ic);
			}
		}
		return itemCarts;
	}
	
	public ItemCartEntity insertItemCart(ItemCartEntity itemCart) {
		return icRepository.save(itemCart);
	}

	public ItemCartEntity selectByItemCartEntityId(ItemCartEntityId itemCartId) {
		ItemCartEntity vo = null;
		try {
			vo = icRepository.findById(itemCartId).orElseThrow();
		}catch(Exception aa) {
			return null;
		}
		return vo;
	}
	
	// 제거
	public int deleteItemCart(ItemCartEntityId itemCartId) {
		int ret = 0;
		try {
			icRepository.deleteById(itemCartId);
			ret = 1;
		} catch (Exception ex) {
			}
		return ret;
	}
}
