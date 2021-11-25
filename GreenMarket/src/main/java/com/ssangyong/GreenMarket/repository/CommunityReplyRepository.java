package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.CommunityReplyEntity;

public interface CommunityReplyRepository extends CrudRepository<CommunityReplyEntity, Integer> {

	public List<CommunityReplyEntity> findByCommunity(CommunityEntity community);
	
}
