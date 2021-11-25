package com.ssangyong.GreenMarket.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.QCommunityEntity;

public interface CommunityRepository extends CrudRepository<CommunityEntity, Integer>, QuerydslPredicateExecutor<CommunityEntity> {

	
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QCommunityEntity board = QCommunityEntity.communityEntity;
		builder.and(board.cId.gt(0)); //and diaryNum>0
		if(type==null) return builder;
		switch (type) {
		case "title":
			builder.and(board.cTitle.like("%" + keyword + "%")); //and title like '%?%'
			break;
		case "content":
			builder.and(board.cContent.like("%" + keyword + "%")); //and content like '%?%'
			break;
		case "writer":
			builder.and(board.member.mNickname.like("%" + keyword + "%")); //and writer like '%?%'
			break;
		default:
			break;
		}
		return builder;
	}
	
	
	public Page<CommunityEntity> findByCContent(String content, Pageable page);

	public Page<CommunityEntity> findAll(Predicate p, Pageable pageable);
	
}
