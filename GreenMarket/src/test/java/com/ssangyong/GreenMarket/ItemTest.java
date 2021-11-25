package com.ssangyong.GreenMarket;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ssangyong.GreenMarket.model.IStateEnumType;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemEntityId;
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
		ItemEntityId itemEntityId = new ItemEntityId();
		itemEntityId.setMember(mRepo.findById("test").orElseThrow());
		ItemEntity item = ItemEntity.builder()
				.ieId(itemEntityId)
				.iTitle("비싼책")
				.iContent("재미없어요")
				.iPrice(1300)
				.iState(IStateEnumType.GOOD)
				.iTstate(ItStateEnumType.POSSIBLE)
				.itemCategory(icRepo.findByIcName("도서"))
				.build();
		
		iRepo.save(item);
	}
}
