package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.CommunityReplyEntity;
import com.ssangyong.GreenMarket.repository.CommunityReplyRepository;


@Service
public class CommunityReplyService {
	
	@Autowired
	CommunityReplyRepository repo;
	
	
	public List<CommunityReplyEntity> selectAll(CommunityEntity community) {
		return (List<CommunityReplyEntity>)repo.findByCommunity(community);
	}
	
	
	public CommunityReplyEntity selectAll (Integer crId) {
		return repo.findById(crId).get();
	}

	public CommunityReplyEntity updateOrInsert(CommunityReplyEntity reply) {
		return repo.save(reply);
	}
	
	public int deleteReply(Integer crId) {
		int ret=0;
		try {
		repo.deleteById(crId);
		ret=1;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return ret;
	}

	
}
