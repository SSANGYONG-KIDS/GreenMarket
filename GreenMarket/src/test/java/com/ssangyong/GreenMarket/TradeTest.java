package com.ssangyong.GreenMarket;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.repository.ItemCategoryRepository;
import com.ssangyong.GreenMarket.repository.ItemRepository;
import com.ssangyong.GreenMarket.repository.MemberRepository;
import com.ssangyong.GreenMarket.repository.TradeRepository;

@Commit
@SpringBootTest
public class TradeTest {
	@Autowired
	ItemRepository iRepo;
	@Autowired
	MemberRepository mRepo;
	@Autowired
	ItemCategoryRepository icRepo;
	@Autowired
	TradeRepository tRepo;
	
	/**
	 * 거래테이블에 샘플 데이터 삽입하기
	 */
	@Transactional
	@Test
	public void insertTrade() {
		TradeEntity trade = TradeEntity.builder()
				.item(iRepo.findById(4).orElseThrow())
				.buyMember(mRepo.findById("test2").orElseThrow())
				.build();
		tRepo.save(trade);
	}
	
	/**
	 * 
	 */
	@Transactional
//	@Test
	public void showTrade() {
		TradeEntity trade = tRepo.findById(5).orElseThrow();
		System.out.println(trade);
		
		System.out.println("구매자: " + trade.getBuyMember());
		System.out.println("판매자: " + trade.getItem().getMember());
	}
}
