package com.ssangyong.GreenMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.CommunityReplyEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.repository.CommunityReplyRepository;
import com.ssangyong.GreenMarket.repository.CommunityRepository;


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
		System.out.println("댓글 레포지토리 update or insert()");
		return repo.save(reply);
	}
	
	@Transactional
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
