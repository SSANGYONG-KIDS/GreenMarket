package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;

public interface TradeRepository extends CrudRepository<TradeEntity, Integer> {
	/**
	 * 한 구매자(Renter)에 대한 모든 trade 가져오기
	 * @param member
	 * @return
	 */
	public List<TradeEntity> findAllByBuyMember(MemberEntity member);
	
	/**
	 * 한 판매자(Sharer)에 대한 모든 trade 가져오기
	 */
	@Query("SELECT t FROM TradeEntity t join t.item i join i.member m WHERE m.mId = ?1")
	public List<TradeEntity> findByMIdOfItem(String m_id);
}
