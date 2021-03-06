package com.ssangyong.GreenMarket.service;

import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
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

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;


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
             user = memberRepo.findById(mId).filter(u ->u!=null).map(u->new SecurityUser(u)).get();
          }
          
         return user;
   }
   
   public void certifiedPhoneNumber(String mPhone, String cerNum) {
       String api_key = "NCSEE28TUBDDKYD3";
       String api_secret = "6LT6R7UENFIXUKJONYPKMTIGHJDD4NAK";
       Message coolsms = new Message(api_key, api_secret);
       HashMap<String, String> params = new HashMap<String, String>();
       params.put("to", mPhone);    // ??????????????????
       params.put("from", "010-8447-7807");    // ??????????????????. ?????????????????? ??????,?????? ?????? ?????? ????????? ?????? ???
       params.put("type", "SMS");
       params.put("text", "[ GREENMARKET ] ??????????????? ????????? : ???????????????" + "["+cerNum+"]" + "?????????.");
       params.put("app_version", "test app 1.2"); // application name and version

       try {
           JSONObject obj = (JSONObject) coolsms.send(params);
       } catch (CoolsmsException e) {
           System.out.println(e.getMessage());
           System.out.println(e.getCode());
       }

   } 
   
   public boolean checkmId(String mId) {
 	  return memberRepo.findById(mId).isPresent();
   }
   
   public boolean checkNickName(String mNickname) {
	  return memberRepo.findBymNickname(mNickname).size() > 0 ? true : false;
   }

   @Transactional
   public int FindPassword(String mId, String mEmail) {
	   
	   MemberEntity memberEntity = memberRepo.findById(mId).orElse(null);
	   
	   if (memberEntity == null) return 2;
	   
	   else if(memberEntity.getMEmail().equals(mEmail)) {
		   return 1;
	   }else {
		   return 0;
	   }
   }
   
}
