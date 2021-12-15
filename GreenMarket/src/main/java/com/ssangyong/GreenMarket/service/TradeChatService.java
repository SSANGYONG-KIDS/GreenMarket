package com.ssangyong.GreenMarket.service;

import java.util.List;

import javax.transaction.Transactional;

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
	 * 메시지 읽음 처리하기
	 */
	@Transactional
	public void changeToReadState(int tId, String mIdOfReader) {
		List<MessageEntity> messages = getAllMessage(tId); // 메시지 가져오기
		for (MessageEntity message : messages) { // 모든 메시지 확인		
			if (!message.getMember().getMId().equals(mIdOfReader)) { // 상대방 메시지에 대해서
				message.setMsgIsread(1); // 읽은 여부를 읽음('1')으로 바꾸기
			}
		}
	}
	
	/**
	 * 안읽은 메시지 수 구하기
	 */
	public int getCntOfUnreadMsg(int tId, String mIdOfReader) {
		int cntUnread = 0; // 안읽은 수
		List<MessageEntity> messages = getAllMessage(tId); // 메시지 가져오기
		int messagesSize = messages.size();
		for (int i = 0; i < messagesSize; i++) { // 모든 메시지 확인	
			MessageEntity message = messages.get(messagesSize - 1 - i); // 최근 메시지부터 확인
			if (!message.getMember().getMId().equals(mIdOfReader)) { // 상대방 메시지에 대해서
				if (message.getMsgIsread() == 0) { // 안읽은 메시지에 대해서
					cntUnread++;
				} else if (message.getMsgIsread() == 0) { // 읽은 메시지에 대해서
					break; // 최근 순으로 확인하기 때문에 내가 읽은 상대방의 메시지 확인한 순간 더 확인할 필요가 없어진다.
				}
			}
		}
		
		return cntUnread;
	}
}
