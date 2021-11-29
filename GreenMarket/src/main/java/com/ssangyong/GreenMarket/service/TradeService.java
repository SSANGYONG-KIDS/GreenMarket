package com.ssangyong.GreenMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
