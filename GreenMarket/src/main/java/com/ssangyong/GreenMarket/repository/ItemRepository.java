package com.ssangyong.GreenMarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.QItemEntity;

public interface ItemRepository extends CrudRepository<ItemEntity, Integer>, QuerydslPredicateExecutor<ItemEntity> {
	
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QItemEntity item = QItemEntity.itemEntity;
		builder.and(item.iId.gt(0)); //and diaryNum>0
		if(type==null) return builder;
		switch (type) {
		case "title":
			builder.and(item.iTitle.like("%" + keyword + "%")); //and title like '%?%'
			break;
		case "content":
			builder.and(item.iContent.like("%" + keyword + "%")); //and content like '%?%'
			break;
		case "writer":
			builder.and(item.member.mNickname.like("%" + keyword + "%")); //and writer like '%?%'
			break;
		default:
			break;
		}
		return builder;
	}
	
	public Page<ItemEntity> findByIContent(String content, Pageable page);

	public Page<ItemEntity> findAll(Predicate p, Pageable pageable);
}
