package com.ssangyong.GreenMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.repository.CommunityRepository;

@Service
public class CommunityService {

	@Autowired
	CommunityRepository repo;

	// page
	public Page<CommunityEntity> selectAll(PageVO pvo) {
		Predicate p = repo.makePredicate(pvo.getType(), pvo.getKeyword());

		// makePaging(방향, sort할 field)
		Pageable pageable = pvo.makePaging(0, "cId");
		Page<CommunityEntity> result = repo.findAll(p, pageable);
		return result;
	}

	// list조회
	public List<CommunityEntity> selectAll() {
		return (List<CommunityEntity>) repo.findAll();
	}

	// 아이디로 찾기
	public CommunityEntity selectById(Integer cId) {
		return repo.findById(cId).get();
	}

	// 삽입
	public CommunityEntity insertBoard(CommunityEntity board) {
		return repo.save(board);
	}

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
