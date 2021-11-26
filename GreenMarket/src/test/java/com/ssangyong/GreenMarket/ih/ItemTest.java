package com.ssangyong.GreenMarket.ih;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.IStateEnumType;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.repository.ItemRepository;
import com.ssangyong.GreenMarket.repository.MemberRepository;

@Commit
@SpringBootTest
public class ItemTest {
	
	@Autowired
	ItemRepository iRepo;
	
	@Autowired
	MemberRepository mRepo;

	/**
	 * Item 샘플 데이터 생성하기
	 */
	@Transactional
	@Test
	public void insertItem() {
		ItemEntity item = ItemEntity.builder()
				.member(mRepo.findById("test1").orElseThrow())
				.iTitle("비싼책")
				.iContent("재미없어요")
				.iPrice(1300)
				.iState(IStateEnumType.GOOD)
				.iTstate(ItStateEnumType.POSSIBLE)
				.iCategory(ICategoryEnumType.BOOK_TICKET_RECORD)
				.build();
		
		iRepo.save(item);
		
		ItemEntity item2 = ItemEntity.builder()
				.iTitle("~~가방")
				.iContent("사고나서 거의 사용안해서 깨끗합니다")
				.iPrice(50000)
				.iState(IStateEnumType.GOOD)
				.iTstate(ItStateEnumType.POSSIBLE)
				.iCategory(ICategoryEnumType.WOMEN_MERCHANDISE)
				.build();
		
		iRepo.save(item2);
	}
}
