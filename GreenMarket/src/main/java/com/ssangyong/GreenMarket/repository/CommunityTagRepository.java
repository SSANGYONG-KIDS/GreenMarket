package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.CommunityReplyEntity;
import com.ssangyong.GreenMarket.model.CommunityTagEntity;

public interface CommunityTagRepository extends CrudRepository<CommunityTagEntity, Integer>{

	public List<CommunityTagEntity> findByCommunity_cId(@Param(value="cId") Integer cId);
	
	public List<CommunityTagEntity> findByCtName(String ctName);

}
