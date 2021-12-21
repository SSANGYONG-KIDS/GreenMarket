package com.ssangyong.GreenMarket.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.MemberEntity;

@Service
public class MailService {
     
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String email, String password) {
        
    	// 수신 대상을 담을 ArrayList 생성
        ArrayList<String> toUserList = new ArrayList<>();
        
        // 수신 대상 추가
        toUserList.add(email);
        
    	// 수신 대상 개수
        int toUserSize = toUserList.size();
        
        // SimpleMailMessage (단순 텍스트 구성 메일 메시지 생성할 때 이용)
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        
        // 수신자 설정
        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));
        
        // 메일 제목
        simpleMessage.setSubject("그린마켓 임시 비멀번호를 알려드립니다!");
        
        // 메일 내용
        simpleMessage.setText(password+ " 이 비밀번호로 다시 로그인 해주세요!");
        
        // 메일 발송
        javaMailSender.send(simpleMessage);
    }
}