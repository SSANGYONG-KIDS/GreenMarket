package com.ssangyong.GreenMarket.service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssangyong.GreenMarket.model.MemberAddress;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.MemberRoleEnumType;
import com.ssangyong.GreenMarket.model.SecurityUser;  
import com.ssangyong.GreenMarket.repository.LoginRepository;
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
       String api_key = "NCS49WNVQ5ZW0S3I";
       String api_secret = "NESLMKGUAVFRAG3QGST9KSZLO4RQVKGU";
       Message coolsms = new Message(api_key, api_secret);
       HashMap<String, String> params = new HashMap<String, String>();
       params.put("to", mPhone);    // 수신전화번호
       params.put("from", "010-5104-6324");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
       params.put("type", "SMS");
       params.put("text", "[ FitPle ] 휴대폰인증 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
       params.put("app_version", "test app 1.2"); // application name and version

       try {
           JSONObject obj = (JSONObject) coolsms.send(params);
       } catch (CoolsmsException e) {
           System.out.println(e.getMessage());
           System.out.println(e.getCode());
       }

   } 

   
   public boolean checkName(String mId) {
 	  return memberRepo.findById(mId).isPresent();
   }
   
   public boolean checkNickName(String mNickname) {
// 	  return memberRepo.findById(mNickname).isPresent();
	   return memberRepo.findBymNickname(mNickname).size() > 0 ? true : false;
   }
   
   
}
