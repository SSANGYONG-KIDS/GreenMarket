package com.ssangyong.GreenMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.repository.CommunityRepository;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository repo;

// 원래 코드!! (PageVO 기법)
	public Page<CommunityEntity> selectAll(PageVO pvo) {
		Predicate p = repo.makePredicate(pvo.getType(), pvo.getKeyword());

		// makePaging(방향, sort할 field)
		Pageable pageable = pvo.makePaging(0, "cId");
		Page<CommunityEntity> result = repo.findAll(p, pageable);
		return result;
	}
	
	
	
	// 원래 코드!!list조회
	public List<CommunityEntity> selectAll() {
		return (List<CommunityEntity>) repo.findAll();
	}


	
	// 아이디로 찾기
	public CommunityEntity selectById(Integer cId) {
		return repo.findById(cId).get();
	}

	// 삽입 (변경)
	@Transactional
	public void insertBoard(CommunityEntity board, MemberEntity user) {
		System.out.println("insert board() 호출");
		board.setCViews(0); //조회수 0
		board.setMember(user);
		repo.save(board);
	}
	
	//삽입 오리지날
//	public CommunityEntity insertBoard(CommunityEntity board) {
//		System.out.println("insert board() 호출");
//		return repo.save(board);
//	}

	// 수정
	public CommunityEntity updateBoard(CommunityEntity board) {
		return repo.save(board);
	}

	// 제거
	public int deleteBoard(Integer cId) {
		int ret = 0;
		try {
			repo.deleteById(cId);
			ret = 1;
		} catch (Exception ex) {
		}
		return ret;
	}
}
