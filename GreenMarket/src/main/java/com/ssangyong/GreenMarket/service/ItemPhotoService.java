package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.ItemPhotoEntity;
import com.ssangyong.GreenMarket.repository.ItemPhotoRepository;

@Service
public class ItemPhotoService {
	private static ItemPhotoEntity ItemPhotoEntity = new ItemPhotoEntity(0); //0번으로 사진 없음 파일 넣기
	
	@Autowired
	ItemPhotoRepository itemPhotoRepo;
	
	// list조회
	public List<ItemPhotoEntity> selectAll() {
		return (List<ItemPhotoEntity>) itemPhotoRepo.findAll();
	}

	// 아이디로 찾기
	public ItemPhotoEntity selectById(Integer ipId) {
		return itemPhotoRepo.findById(ipId).orElse(ItemPhotoEntity);
	}

	// 삽입
	public ItemPhotoEntity insertItemPhoto(ItemPhotoEntity itemPhoto) {
		return itemPhotoRepo.save(itemPhoto);
	}

	// 수정
	public ItemPhotoEntity updateItemPhoto(ItemPhotoEntity itemPhoto) {
		return itemPhotoRepo.save(itemPhoto);
	}

	// 제거
	public int deleteItemPhoto(Integer ipId) {
		int ret = 0;
		try {
			itemPhotoRepo.deleteById(ipId);
			ret = 1;
		} catch (Exception ex) {
			}
		return ret;
	}
}
