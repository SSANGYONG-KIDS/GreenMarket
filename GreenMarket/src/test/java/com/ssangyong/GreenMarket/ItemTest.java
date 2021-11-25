package com.ssangyong.GreenMarket;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ssangyong.GreenMarket.model.IStateEnumType;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemCategoryEntity;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.repository.ItemCategoryRepository;
import com.ssangyong.GreenMarket.repository.ItemRepository;
import com.ssangyong.GreenMarket.repository.MemberRepository;

@Commit
@SpringBootTest
public class ItemTest {
	
	@Autowired
	ItemRepository iRepo;
	
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ItemCategoryRepository icRepo;

	/**
	 * Item 샘플 데이터 생성하기
	 */
	@Transactional
	@Test
	public void insertItem() {
		
		
		ItemEntity item = ItemEntity.builder()
				.iTitle("비싼책")
				.iContent("재미없어요")
				.iPrice(1300)
				.iState(IStateEnumType.GOOD)
				.iTstate(ItStateEnumType.POSSIBLE)
				.itemCategory(icRepo.findByIcName("디지털기기"))
				.build();
		
		iRepo.save(item);
	}
}
