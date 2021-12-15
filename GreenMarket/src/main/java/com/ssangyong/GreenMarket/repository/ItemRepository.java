package com.ssangyong.GreenMarket.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.QItemEntity;

public interface ItemRepository extends CrudRepository<ItemEntity, Integer>, QuerydslPredicateExecutor<ItemEntity> {
	
	public default Predicate makePredicate(ItemPageVO pvo) {
		BooleanBuilder builder = new BooleanBuilder();
		QItemEntity item = QItemEntity.itemEntity;
		builder.and(item.iId.gt(0)); //and iId>0
		if(pvo.getItemName()==null) return builder;
		
		builder.andAnyOf(item.iTitle.like("%" + pvo.getItemName() + "%"), item.iContent.like("%" + pvo.getItemName() + "%")); //물건 이름 검색
		if(pvo.getItemSort()!=null) {
			builder.and(item.iCategory.eq(pvo.getItemSort()));
		}
		builder.and(item.iTstate.eq(ItStateEnumType.POSSIBLE)); //거래 가능 상태
		if(pvo.getPriceLimit()!=0) {
			builder.and(item.iPrice.lt(pvo.getPriceLimit())); //최대 가능 금액
		}
		return builder;
	}
	
	public Page<ItemEntity> findByIContent(String content, Pageable page);
	
	public List<ItemEntity> findByMember(MemberEntity member);

	public Page<ItemEntity> findAll(Predicate p, Pageable pageable);
}
