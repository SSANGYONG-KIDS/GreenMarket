package com.ssangyong.GreenMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.ReviewEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.service.MemberService;
import com.ssangyong.GreenMarket.service.ReviewService;
import com.ssangyong.GreenMarket.service.TradeService;

@RestController
@RequestMapping("/review/*") //reviews로 시작하는 것은 전부 받겠다.
public class ReviewRestController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	MemberService memberService;
	@Autowired
	TradeService tradeService;
	
	//댓글 상세보기 selectById
	@GetMapping("/{rId}")
	public ResponseEntity<ReviewEntity> viewReply(@PathVariable int rId) {
		return new ResponseEntity<>(reviewService.selectById(rId), HttpStatus.OK);		
	}

	//게시글의 모든 댓글 조회
	@GetMapping("/myreviews/{mId}")
	public ResponseEntity<List<ReviewEntity>> selectAll(@PathVariable String mId) {
		 
		return new ResponseEntity<>(reviewService.selectMyList(memberService.selectById(mId)), HttpStatus.OK);
	}
	
	//특정 board의 댓글 입력=> 재조회
	@PostMapping("/{tnum}")
	public ResponseEntity<ReviewEntity> addReview(@PathVariable int tnum, @RequestBody ReviewEntity review, Authentication authentication) {
		System.out.println("tnum= "+tnum);
		System.out.println(review);
		TradeEntity trade= tradeService.selectById(tnum);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member = memberService.selectById(userDetails.getUsername());
				
		review.setTrade(trade);
		review.setMember(member);
		review.setItem(trade.getItem());
				
		return new ResponseEntity<>(reviewService.insertReview(review), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{rId}")
	public ResponseEntity<List<ReviewEntity>> deleteByRid(@PathVariable int rId, Authentication authentication) {
		reviewService.deleteReview(rId);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member = memberService.selectById(userDetails.getUsername());
		return new ResponseEntity<>(reviewService.selectMyList(member), HttpStatus.OK);
	}

}
