package com.ssangyong.GreenMarket;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ssangyong.GreenMarket.model.ItemCategoryEntity;
import com.ssangyong.GreenMarket.repository.ItemCategoryRepository;

@Commit
@SpringBootTest
public class ItemCategoryTest {
	@Autowired
	ItemCategoryRepository itemCategoryRepository;
	
	/**
	 * 아이템 카테고리 샘플 데이터 생성
	 */
	@Transactional
	public void insertItemCategory() {
		ItemCategoryEntity itemCategoryEntity = ItemCategoryEntity.builder()
				.icId(1)
				.icName("도서")
				.build();
		
		itemCategoryRepository.save(itemCategoryEntity);
	}
	
	/**
	 * 아이템 카테고리 테이블의 모든 데이터 리스트로 가져오기
	 */
	@Transactional
	@Test
	public void selectItemCategory() {
		List<ItemCategoryEntity> itemCategoryEntities = (List<ItemCategoryEntity>) itemCategoryRepository.findAll();
		for (ItemCategoryEntity itemCategoryEntity: itemCategoryEntities) {
			System.out.println(itemCategoryEntity);
		}
	}
	
	/**
	 * 아이템 카테고리 테이블에서 이름으로 엔터티 찾기
	 */
	public void findItemCategoryByName() {
//		itemCategoryRepository.find
	}
}
