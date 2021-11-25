package com.ssangyong.GreenMarket.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.MemberAddress;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.MemberRoleEnumType;
import com.ssangyong.GreenMarket.model.SecurityUser;
import com.ssangyong.GreenMarket.repository.MemberRepository;

@Service
public class LoginService implements UserDetailsService {
	
	@Autowired 
	private MemberRepository memberRepo;
	
	public MemberEntity selectById(String mId) {
  		return memberRepo.findById(mId).get();
  	}
	
	public void signup(MemberEntity member, MemberAddress memberAddress) {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 
		member.setMPw(encoder.encode(member.getMPw()));
		member.setMRole(MemberRoleEnumType.USER);
		member.setMAddress(memberAddress);
        
        memberRepo.save(member);
	}

	@Override
	public UserDetails loadUserByUsername(String mId) throws UsernameNotFoundException {
		  UserDetails user = null;
	  	  Optional<MemberEntity> obj =  memberRepo.findById(mId) ;
	  	  if(!(obj.isEmpty())) {
	  		  user = memberRepo.findById(mId)
	  	        	  .filter(u ->u!=null).map(u->new SecurityUser(u)).get();
	  	  }
	  	  
	      return user;
	}

}