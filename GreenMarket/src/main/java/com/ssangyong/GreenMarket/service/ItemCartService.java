package com.ssangyong.GreenMarket.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.IStateEnumType;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemCartEntity;
import com.ssangyong.GreenMarket.model.ItemCartEntityId;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPhotoEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.repository.ItemCartRepository;
import com.ssangyong.GreenMarket.repository.ItemPhotoRepository;


@Service
public class ItemCartService {
	@Autowired
	ItemCartRepository icRepository;
	@Autowired
	ItemPhotoRepository ipRepository;
	
	//한 사람이 좋아요 한 물품전체 조회
	public List<ItemEntity> selectItemList(String mId) {
		List<Object[]> objects= icRepository.allFindByMId(mId);
		
	    List<ItemEntity> itemlist= new ArrayList<>();
	      objects.forEach(arr->{
	    	  MemberEntity member = MemberEntity.builder().mId((String)arr[7]).build();
	    	  
	    	  ItemPhotoEntity photo = ipRepository.findById(Integer.parseInt(String.valueOf(arr[9]))).get();
	    			  
	    	  List<ItemPhotoEntity> photos = new ArrayList<>();
	    	  photos.add(photo);
	    	  
	    	  ItemEntity item = ItemEntity.builder()
	               .iId(Integer.parseInt(String.valueOf(arr[0])))
	               .iContent((String)arr[1])
	               .iPrice(Integer.parseInt(String.valueOf(arr[2])))
	               .iRegdate((Timestamp)arr[3])
	               .iState(IStateEnumType.valueOf((String)arr[4]))
	               .iTitle((String)arr[5])
	               .iTstate(ItStateEnumType.valueOf((String)arr[6]))
	               .member(member)
	               .iCategory(ICategoryEnumType.valueOf((String)arr[8]))
	               .photos(photos)
	               .build();
	    	 
	    	  itemlist.add(item);
	      });
	     
		return itemlist;
	}
	
	// 하나의 물품 좋아요 한 사람들을 조회
	public List<ItemCartEntity> selectList(Integer iId) {
		List<ItemCartEntity> alllist = (List<ItemCartEntity>)icRepository.findAll();
		List<ItemCartEntity> itemCarts = new ArrayList();
		for(ItemCartEntity ic : alllist) {
			if(ic.getIcId().getItem().getIId()==iId) {
				itemCarts.add(ic);
			}
		}
		return itemCarts;
	}
	
	public ItemCartEntity insertItemCart(ItemCartEntity itemCart) {
		return icRepository.save(itemCart);
	}

	public ItemCartEntity selectByItemCartEntityId(ItemCartEntityId itemCartId) {
		ItemCartEntity vo = null;
		try {
			vo = icRepository.findById(itemCartId).orElseThrow();
		}catch(Exception aa) {
			return null;
		}
		return vo;
	}
	
	// 제거
	public int deleteItemCart(ItemCartEntityId itemCartId) {
		int ret = 0;
		try {
			icRepository.deleteById(itemCartId);
			ret = 1;
		} catch (Exception ex) {
			}
		return ret;
	}
}
