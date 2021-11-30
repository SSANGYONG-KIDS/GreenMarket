package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.repository.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	TradeRepository tradeRepo;

	/**
	 * Trade 테이블에 거래 데이터 삽입하기
	 * @param trade
	 * @return TradeEntity
	 */
	public TradeEntity insertTrade(TradeEntity trade) {
		return tradeRepo.save(trade);
	}
	
	/**
	 * 자기가 구매자(Renter)이면서 거래 완료가 아닌 것 모두 들고하기
	 */
	public List<TradeEntity> listLiveTradeAboutRenter(MemberEntity member) {
		List<TradeEntity> list = tradeRepo.findAllByBuyMember(member);
		System.out.println(list);
		return null;
	}

}
