package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.repository.LoginRepository;
import com.ssangyong.GreenMarket.repository.MemberRepository;

@Service
public class MemberService implements UserDetailsService{

	@Autowired
	LoginRepository repo;
	@Autowired
	MemberRepository memberrepository;

	// 리스트 뿌리기
	public List<MemberEntity> memberList() {
		return (List<MemberEntity>) memberrepository.findAll();
	}

	
  	public MemberEntity selectById(String mId) {
  		return memberrepository.findById(mId).get();
  	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}


//  	
//	@Override
//	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//		UserDetails user = memberrepository.findById(userId)
//				.filter(u->u!=null).map(u->new SecurityUser(u)).get();
//		return user;
//	}
  	
  	
}