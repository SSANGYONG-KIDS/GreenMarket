package com.ssangyong.GreenMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.CommunityTagEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.repository.CommunityRepository;
import com.ssangyong.GreenMarket.repository.CommunityTagRepository;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository repo;
	@Autowired
	private CommunityTagRepository tag_repo;
	
	
// 원래 코드!! (PageVO 기법)
	public Page<CommunityEntity> selectAll(PageVO pvo) {
		Predicate p = repo.makePredicate(pvo.getType(), pvo.getKeyword());

		// makePaging(방향, sort할 field)
		Pageable pageable = pvo.makePaging(0, "cId");
		Page<CommunityEntity> result = repo.findAll(p, pageable);
		return result;
	}
		
	// 내가 쓴 글 보기
	public Page<CommunityEntity> selectMyBoardlist(PageVO pvo, MemberEntity user){
		Predicate p = repo.makePredicate(pvo.getType(), pvo.getKeyword());
		
		Pageable pageable = pvo.makePaging(0, "cId");
		Page<CommunityEntity> result = repo.findByMember(user, pageable);
		return result;
	}
	/*
	// 내가쓴 글 보기(원래)
	public Page<CommunityEntity> selectMyBoard(PageVO pvo, String mId){
		System.out.println("service-selectMyBoard()");
		Predicate p = repo.makePredicate(pvo.getType(), pvo.getKeyword());
	//	Predicate p = repo.makePredicate(null, null);
		String id = mId;
			
		// makePaging(방향, sort할 field)
		Pageable pageable = pvo.makePaging(0, "cId");
		System.out.println("#############################################################");
		Page<CommunityEntity> result = repo.findByMember_mId(mId, p, pageable);
		System.out.println("#############################################################");
		return result;
	}
	*/
	// 원래 코드!!list조회
	public List<CommunityEntity> selectAll() {
		return (List<CommunityEntity>) repo.findAll();
	}

	
	
	
	// 아이디로 찾기 (글 상세보기)
	public CommunityEntity selectById(Integer cId) {
		return repo.findById(cId).get();
	}

	// 태그 아이디로 찾기
	public List<CommunityTagEntity> selectTags(Integer cId) {
		return tag_repo.findByCommunity_cId(cId);
	}
	
	//같은 태그 게시글 띄우기
	public List<CommunityTagEntity> selectByTagName(Integer ctId) {
		System.out.println("service-select by tagName() 호출");
		CommunityTagEntity tagEntity = tag_repo.findById(ctId).get();
	
		String thisName = tagEntity.getCtName();
		
		return tag_repo.findByCtName(thisName);
		
	}
	
	// 삽입 (변경)
	@Transactional
	public void insertBoard(CommunityEntity board, MemberEntity user) {
		System.out.println("insert board() 호출");
		board.setCViews(0); //조회수 0
		board.setMember(user);
		repo.save(board);
	}
	
	// 태그 엔티티 삽입
	@Transactional
	public void insertTag(List<String> tagArr, CommunityEntity board) {
		System.out.println("service - insert tag()");
		for(String tagName : tagArr) {
			if(tagName.equals("default__")) {
				return;
			}
			CommunityTagEntity tag = new CommunityTagEntity();
			tag.setCommunity(board);
			tag.setCtName(tagName);
			tag_repo.save(tag);
		}
		
	}
	
	// 글 수정
	@Transactional
	public void updateBoard(CommunityEntity requestBoard) {
		System.out.println("service-updateBoard() 호출");
		CommunityEntity board = repo.findById(requestBoard.getCId()).get(); //영속화
		board.setCTitle(requestBoard.getCTitle());
		board.setCContent(requestBoard.getCContent());
		//함수 종료시(service종료) 트랜잭션 종료 후 더티체킹=> 자동 업데이트. DB flush
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
	
	// 게시글 조회수 +1
	@Transactional
	public void updateViews(Integer cId) {
		this.selectById(cId).setCViews(this.selectById(cId).getCViews()+1);
	}

	
}
