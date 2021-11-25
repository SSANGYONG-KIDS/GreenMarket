package com.ssangyong.GreenMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.MemberAddress;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.repository.MemberRepository;

@Service
public class LoginService{
	
	@Autowired 
	private MemberRepository memberRepo;
	
	public void signup(MemberEntity member, MemberAddress memberAddress) {
       
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return null;
//	}

}