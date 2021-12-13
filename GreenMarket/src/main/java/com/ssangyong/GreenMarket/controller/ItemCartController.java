package com.ssangyong.GreenMarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssangyong.GreenMarket.model.ItemCartEntity;
import com.ssangyong.GreenMarket.model.ItemCartEntityId;
import com.ssangyong.GreenMarket.service.ItemCartService;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.MemberService;

@RestController
@RequestMapping("/item/*")
public class ItemCartController {
	@Autowired
	ItemCartService icService;
	@Autowired
	ItemService iService;
	@Autowired
	MemberService mService;

	//특정 board의 댓글 입력=> 재조회
	@PostMapping("/updateGood/{mId}/{iId}")
	public ResponseEntity<Integer> addOrDeleteItemCart(@PathVariable String mId,@PathVariable int iId) {
		System.out.println("mId= "+mId);
		System.out.println("iId= "+iId);

		ItemCartEntityId icId = new ItemCartEntityId();
		icId.setItem(iService.selectById(iId));
		icId.setMember(mService.selectById(mId));
		ItemCartEntity itemCart = icService.selectByItemCartEntityId(icId);
		
		if( itemCart != null ) {
			//null이 아니면 delete
			icService.deleteItemCart(icId);
		}else {
			//null이면 insert
			itemCart= ItemCartEntity.builder().icId(icId).build();
			icService.insertItemCart(itemCart);		
		}
	
					
		return new ResponseEntity<>(icService.selectList(iId).size(), HttpStatus.CREATED);
		}
}
