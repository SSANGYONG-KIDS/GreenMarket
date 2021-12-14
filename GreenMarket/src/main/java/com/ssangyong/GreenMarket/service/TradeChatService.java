package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.config.ChatSocketHandler;
import com.ssangyong.GreenMarket.model.MessageEntity;
import com.ssangyong.GreenMarket.repository.MessageRepository;
import com.ssangyong.GreenMarket.repository.TradeRepository;

@Service
public class TradeChatService {
	
	@Autowired
	MessageRepository messageRepo;
	@Autowired
	TradeRepository tradeRepo;
	
	/**
	 * JSON 형식의 String -> JSONObject로 변환
	 */
	public JSONObject parseJsonToObject(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 메시지 테이블에 입력하기
	 * @param messageEntity
	 */
	public void insert(MessageEntity messageEntity) {
		messageRepo.save(messageEntity);
	}
	
	/**
	 * 한 거래에 대한 모든 메시지 가져오기
	 */
	public List<MessageEntity> getAllMessage(int tId) {
		return messageRepo.findAllByTradeWithOrder(tId);
	}
	
	/**
	 * TODO 테스트용. 현재 소켓 목록보기
	 */
}
