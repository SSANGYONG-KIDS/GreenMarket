package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.ReviewEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	// list조회
	public List<ReviewEntity> selectAll() {
		return (List<ReviewEntity>) reviewRepo.findAll();
	}
		
	// 한 사람의 review list 조회
	public List<ReviewEntity> selectMyList(MemberEntity member) {
		return (List<ReviewEntity>)reviewRepo.findByMember(member);
	}
	
	// 각 item의 review list 조회
	public List<ReviewEntity> selectItemReviewList(ItemEntity item) {
		return (List<ReviewEntity>)reviewRepo.findByItem(item);
	}
	
	// 거래아이디로 찾기
	public ReviewEntity selectByTrade(TradeEntity trade) {
		return reviewRepo.findByTrade(trade);
	}

	// 아이디로 찾기
	public ReviewEntity selectById(Integer rId) {
		return reviewRepo.findById(rId).get();
	}

	// 삽입
	public ReviewEntity insertReview(ReviewEntity review) {
		return reviewRepo.save(review);
	}

	// 수정
	public ReviewEntity updateReview(ReviewEntity review) {
		return reviewRepo.save(review);
	}

	// 제거
	public int deleteReview(Integer rId) {
		int ret = 0;
		try {
			reviewRepo.deleteById(rId);
			ret = 1;
		} catch (Exception ex) {
			}
		return ret;
	}
}
