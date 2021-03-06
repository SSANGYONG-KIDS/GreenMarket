package com.ssangyong.GreenMarket.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.IStateEnumType;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemCartEntityId;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.ItemPhotoEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.model.ReviewEntity;
import com.ssangyong.GreenMarket.service.ItemCartService;
import com.ssangyong.GreenMarket.service.ItemPhotoService;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.ReviewService;

@Controller
@RequestMapping("/item/*")
public class ItemController {
	@Autowired
	ItemService itemService;
	@Autowired
	ItemPhotoService itemPhotoService;
	@Autowired
	LoginService loginService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	ItemCartService icService;
	
	
	@GetMapping("/itemlist")
	public void selectAll(Model model, HttpServletRequest request, ItemPageVO pagevo) {

		Page<ItemEntity> result = itemService.selectAll(pagevo);

		for(ItemEntity item : result) {
			if(item.getIContent().length()>=10) {
				String sub = item.getIContent().substring(0, 10)+" ...";
				item.setIContent(sub);
			}
		}

	    model.addAttribute("itemSorts", ICategoryEnumType.values());
		model.addAttribute("itemResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMaker<>(result));
	}
	
	//???????????? ?????? ??????
	@GetMapping("/itemcart")
	public void selectMyItemCart(Model model, HttpServletRequest request, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		
		List<ItemEntity> myitemcart = icService.selectItemList(member.getMId());
		for(ItemEntity item : myitemcart) {
			if(item.getIContent().length()>=10) {
				String sub = item.getIContent().substring(0, 10)+" ...";
				item.setIContent(sub);
			}
		}
		
		model.addAttribute("myitemcart", myitemcart);
		model.addAttribute("member", member);
	}		

	
	@GetMapping("/itemdetail")
	public void selectById(Model model, Integer iId, Principal principal, Authentication authentication, ItemPageVO pagevo ) {
		ItemEntity item = itemService.selectById(iId);
		model.addAttribute("item", item);
		model.addAttribute("item_owner",item.getMember());
	
		List<ReviewEntity> item_review = reviewService.selectItemReviewList(item);
		model.addAttribute("item_review",item_review);
		//??????
		double total = 0;
		double avg = 0;
		for(ReviewEntity review: item_review) {
			total+=review.getRStar();
		}
		avg = total /  item_review.size();
		
		model.addAttribute("review_avg", Math.round(avg*100)/100.0); //????????? ??????????????????
		model.addAttribute("pagevo", pagevo);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		//?????? ??????
		model.addAttribute("like_num",icService.selectList(iId).size());
		ItemCartEntityId icId= new ItemCartEntityId();
		icId.setItem(item);
		icId.setMember(member);
		model.addAttribute("like_me",icService.selectByItemCartEntityId(icId)==null?0:1);
	}
	
	@GetMapping("/myitemlist")
	public void selectMyItemList(Model model, HttpServletRequest request, Principal principal, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		List<ItemEntity> myitemlist = (List<ItemEntity>)itemService.selectMyList(member);
		
		for(ItemEntity item : myitemlist) {
			if(item.getIContent().length()>=10) {
				String sub = item.getIContent().substring(0, 10)+" ...";
				item.setIContent(sub);
			}
		}

		model.addAttribute("myitemlist", myitemlist);
	}
	
	@GetMapping("/myitem")
	public void selectMyItem(Model model, Integer iId, HttpServletRequest request, Principal principal, Authentication authentication) throws JsonProcessingException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		ItemEntity item = itemService.selectById(iId);
		model.addAttribute("item", item);
		model.addAttribute("istates",IStateEnumType.values());
		model.addAttribute("tstates",ItStateEnumType.values());
		model.addAttribute("categorys",ICategoryEnumType.values());
		
		int[] ori_ipids= new int[item.getPhotos().size()];
		for(int i=0;i<item.getPhotos().size();i++) {
			ori_ipids[i]= item.getPhotos().get(i).getIpId();
		}
		model.addAttribute("ori_ipids", Arrays.toString(ori_ipids));
	}
	
	@PostMapping("/itemupdate")
	public String itemUpdatePost(ItemEntity item, String iIds, String delete_pids,String writer, String iTstate, String iCategory, RedirectAttributes rttr, Authentication authentication) {
		//item ?????? ??????
		item.setMember(MemberEntity.builder().mId(writer).build());
		item.setICategory(ICategoryEnumType.valueOf(iCategory));
		item.setITstate(ItStateEnumType.valueOf(iTstate));
		item.setIRegdate(Timestamp.valueOf(LocalDateTime.now()));
		ItemEntity ins_item = itemService.updateItem(item);
		
		//????????? ????????? ????????? ipid????????????, photo????????? iId??????????????? 
		List<Integer> iId_list = new ArrayList();
		String[] splitStr = iIds.split(",");
		for(int i=0; i<splitStr.length; i++){
			iId_list.add(Integer.parseInt(splitStr[i]));
		}
		for(Integer i: iId_list) {
			ItemPhotoEntity iphoto = itemPhotoService.selectById(i);
			iphoto.setItem(ins_item);
			itemPhotoService.updateItemPhoto(iphoto);
		}
		
		//???????????? ?????? ????????? ?????? ??????????????? ????????????!
		List<Integer> deletelist = new ArrayList();
		if(!delete_pids.equals("")) {
			String[] delete_pid_str = delete_pids.split(",");
			for(int i=0; i<delete_pid_str.length; i++){
				deletelist.add(Integer.parseInt(delete_pid_str[i]));
			}
			for(Integer j: deletelist) {
				itemPhotoService.deleteItemPhoto(j);
			}
		}
		rttr.addFlashAttribute("resultMessage", ins_item==null?"????????????":"????????????");
		return "redirect:/item/myitemlist";
	}
	
	@GetMapping("/insertform")
	public void itemInsertForm(Model model, Principal principal, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		model.addAttribute("istates",IStateEnumType.values());
		model.addAttribute("tstates",ItStateEnumType.values());
		model.addAttribute("categorys",ICategoryEnumType.values());
	}
	
	
	@PostMapping("/iteminsert")
	public String itemRegisterPost(ItemEntity item, String iIds, String writer, String iTstate, String iCategory, RedirectAttributes rttr, Authentication authentication) {
		item.setMember(MemberEntity.builder().mId(writer).build());
		item.setICategory(ICategoryEnumType.valueOf(iCategory));
		item.setITstate(ItStateEnumType.valueOf(iTstate));
		item.setIRegdate(Timestamp.valueOf(LocalDateTime.now()));

		ItemEntity ins_item = itemService.insertItem(item);
		
		List<Integer> iId_list = new ArrayList();
		String[] splitStr = iIds.split(",");
		for(int i=0; i<splitStr.length; i++){
			iId_list.add(Integer.parseInt(splitStr[i]));
		}
		
		for(Integer i: iId_list) {
			ItemPhotoEntity iphoto = itemPhotoService.selectById(i);
			iphoto.setItem(ins_item);
			itemPhotoService.updateItemPhoto(iphoto);
		}
		
		rttr.addFlashAttribute("resultMessage", ins_item==null?"????????????":"????????????");
		return "redirect:/item/myitemlist";
	}
	
	
	@DeleteMapping("/itemdelete/{iId}")
	@ResponseBody
	public String itemDelete(@PathVariable int iId) {
		int ret = itemService.deleteItem(iId);
		String resultMessage = ret==0?"????????????":"????????????";
		
		return resultMessage;
	}
	
}
