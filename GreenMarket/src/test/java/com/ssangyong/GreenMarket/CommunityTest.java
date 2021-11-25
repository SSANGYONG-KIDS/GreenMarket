package com.ssangyong.GreenMarket;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.CommunityReplyEntity;
import com.ssangyong.GreenMarket.model.LocationEntity;
import com.ssangyong.GreenMarket.model.MemberAddress;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.repository.CommunityReplyRepository;
import com.ssangyong.GreenMarket.repository.CommunityRepository;
import com.ssangyong.GreenMarket.repository.LocationRepository;
import com.ssangyong.GreenMarket.repository.MemberRepository;


@Commit
@SpringBootTest
public class CommunityTest {
	@Autowired
	CommunityRepository crepo;
	
	@Autowired
	MemberRepository mrepo;
	
	@Autowired
	CommunityReplyRepository crrepo;
	
	@Transactional
	@Test
	public void insertCommunity2() {		
		IntStream.range(10, 15).forEach(i->{
			CommunityEntity community = CommunityEntity.builder()
					.cTitle("게시글"+i)
					.cViews(0)
					.cContent("내용"+i)
					.member(mrepo.getById("test5"))
					.build();
		
			crepo.save(community);
		});
	}	
	
	//@Transactional //commit 실행
	//@Test
	public void insertCommunity() {
		
		CommunityEntity community = CommunityEntity.builder()
				.cTitle("게시글")
				.cViews(0)
				.cContent("안녕하세요~~")
				.member(mrepo.getById("test2"))
				.build();
		
		crepo.save(community);
		
		/*
		IntStream.range(2, 10).forEach(i -> {
		
		MemberAddress ma = new MemberAddress();
		ma.setAddNum("08529");
		ma.setMemberAddress1("서울특별시 금천구 남부순환로112길 9-4");
		ma.setMemberAddress2("(가산동)");
		ma.setMemberAddress3("102호");
			MemberEntity member = MemberEntity.builder()
					.mId("test" + i)
					.mPw("qwer")
					.mName("name" + i)
					.mNickname("nickname"+ i)					
					.mEmail("email" + i + "@gmail.com")
					.mPhoto(i + ".jpg")
					.mPhone("010-1111-222"+i)
					.mIsdropped(0)
					.loc(rrepo.getById("000"))
					.mAddress(ma)
					.build();
			System.out.println("memberToString " + member);
			mrepo.save(member);
		
		});
		*/
	}
	
	//@Test
	public void insertUser2() {		
	IntStream.range(1, 4).forEach(i->{
		MemberEntity user = MemberEntity.builder()
		.mId("sample" + i)
		.mPw("qwer")
		.mName("name" + i)
		.mNickname("nickname" + i)
		.mEmail("email" + i + "@gmail.com")
		.mPhoto(i+".jpg")
		.mPhone("010-1111-2222" + i)
		.mIsdropped(0)
		.build();
	
	});
	}
}
