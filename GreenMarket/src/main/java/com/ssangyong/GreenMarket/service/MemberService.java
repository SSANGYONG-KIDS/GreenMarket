package com.ssangyong.GreenMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  	
    // 회원 수정
    @Transactional
    public void updateMember(MemberEntity member) {
    	MemberEntity memberEntity = memberrepository.findById(member.getMId()).get(); //영속화
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	memberEntity.setMPw(encoder.encode(member.getMPw()));
    	memberEntity.setMEmail(member.getMEmail());
    	memberEntity.setMPhone(member.getMPhone());
    	memberEntity.setMAddress(member.getMAddress());
       //함수 종료시(service종료) 트랜잭션 종료 후 더티체킹=> 자동 업데이트. DB flush
    }
    
    @Transactional
    public void deleteById(String mId) {
    	memberrepository.deleteById(mId);
    	
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