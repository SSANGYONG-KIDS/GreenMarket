package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MessageEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
	/**
	 * 한 거래에 대한 모든 메시지 가져오기
	 */
	@Query("SELECT m FROM MessageEntity m join m.trade t WHERE t.tId = ?1 ORDER BY m.msgRegdate ASC")
	public List<MessageEntity> findAllByTradeWithOrder(int tId);
}
