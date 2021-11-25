package com.ssangyong.GreenMarket.ih;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ssangyong.GreenMarket.model.LocationEntity;
import com.ssangyong.GreenMarket.model.MemberAddress;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.repository.LocationRepository;
import com.ssangyong.GreenMarket.repository.MemberRepository;


@Commit
@SpringBootTest
public class MemberTest {
	@Autowired
	MemberRepository mrepo;
	
	@Autowired
	LocationRepository rrepo;
	
	@Transactional //commit 실행
	@Test
	public void insertUser() {
		
		LocationEntity location = LocationEntity.builder()
				.locId("000")
				.locName("서울")
				.build();
		rrepo.save(location);
		
		
		IntStream.range(1, 10).forEach(i -> {
		
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
