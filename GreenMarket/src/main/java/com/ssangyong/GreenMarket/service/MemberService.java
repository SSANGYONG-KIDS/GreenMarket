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
//    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    	memberEntity.setMPw(encoder.encode(member.getMPw()));
    	memberEntity.setMEmail(member.getMEmail());
//    	memberEntity.setMPhone(member.getMPhone());
    	memberEntity.setMPhoto(member.getMPhoto());
    	memberEntity.setMAddress(member.getMAddress());
       //함수 종료시(service종료) 트랜잭션 종료 후 더티체킹=> 자동 업데이트. DB flush
    }
    
    // 비밀번호 수정
    @Transactional
    public void updateMemberPassword(MemberEntity member) {
    	MemberEntity memberEntity = memberrepository.findById(member.getMId()).get(); //영속화
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	memberEntity.setMPw(encoder.encode(member.getMPw()));
    }
    
    // 번호 수정
    @Transactional
    public void updateMemberPhone(MemberEntity member) {
    	MemberEntity memberEntity = memberrepository.findById(member.getMId()).get(); //영속화
    	memberEntity.setMPhone(member.getMPhone());
    }
    
    // 비밀번호 확인
    @Transactional
    public boolean checkCurrentPassword(String mId, String password) {   	
    	// 멤버 정보 가져오기
    	MemberEntity memberEntity = memberrepository.findById(mId).get();
    	
    	// 비교하기
    	return new BCryptPasswordEncoder().matches(password, memberEntity.getMPw());
    	
    }
    
    @Transactional
    public void deleteById(String mId) {
    	MemberEntity member = selectById(mId);
    	member.setMIsdropped(1);
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