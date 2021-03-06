package com.ssangyong.GreenMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.TStateEnumType;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.repository.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	TradeRepository tradeRepo;
	
	
	/**
	 * "YYYY-MM-DD" 형식의 String을 "YYYY년 MM월 DD일" 형식의 String으로 바꾸기
	 */
	public String convertFormToKorDate(String date) {
		String dateKor = date.replaceFirst("-", "년 ");
		dateKor = dateKor.replaceFirst("-", "월 ");
		dateKor = dateKor + "일";
		return dateKor;
	}

	/**
	 * Trade 테이블에 거래 데이터 삽입하기
	 * @param trade
	 * @return TradeEntity
	 */
	public TradeEntity insertTrade(TradeEntity trade) {
		return tradeRepo.save(trade);
	}
	
	/**
	 * 한 멤버가 구매(Rent)한 거래 내역 가져오기
	 */
	public List<TradeEntity> listTradeForRenter(MemberEntity member) {
		List<TradeEntity> list = tradeRepo.findAllByBuyMember(member);
		return list;
	}
	
	/**
	 * 한 멤버가 판매(Share)한 거래 내역 가져오기
	 */
	public List<TradeEntity> listTradeForSharer(MemberEntity member) {
		List<TradeEntity> list = tradeRepo.findByMIdOfItem(member.getMId());
		return list;
	}
	
	/**
	 * 아이디로 찾기
	 * @param tId
	 * @return
	 */
	public TradeEntity selectById(Integer tId) {
		return tradeRepo.findById(tId).get();
	}
	
	/**
	 * tState 변경하기
	 * @param tId
	 * @param tStateTitle
	 */
	@Transactional
	public void changeTState(int tId, String tStateValue) {
		TStateEnumType tState = TStateEnumType.valueOf(tStateValue);
		
		// trade 가져오기
		TradeEntity trade = tradeRepo.findById(tId).orElseThrow();
		
		// tState 변경하기
		if (tState != null) {
			trade.setTState(tState);
		}	
	}

}
