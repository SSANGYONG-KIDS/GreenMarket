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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.IStateEnumType;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.ItemPhotoEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.service.ItemPhotoService;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.LoginService;

@Controller
@RequestMapping("/item/*")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	@Autowired
	ItemPhotoService itemPhotoService;
	@Autowired
	LoginService loginService;
	
	
	@GetMapping("/itemlist")
	public void selectAll(Model model, HttpServletRequest request, ItemPageVO pagevo) {

		Page<ItemEntity> result = itemService.selectAll(pagevo);

		model.addAttribute("itemResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMaker<>(result));
	}
	
	@GetMapping("/itemdetail")
	public void selectById(Model model, Integer iId, Principal principal, Authentication authentication, ItemPageVO pagevo ) {
		ItemEntity item = itemService.selectById(iId);
		model.addAttribute("item", item);
		model.addAttribute("item_owner",item.getMember());
		model.addAttribute("item_photos",itemPhotoService.selectById(item.getIId()));
		model.addAttribute("pagevo", pagevo);
		System.out.println(pagevo);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
	}
	
	@GetMapping("/myitemlist")
	public void selectMyItemList(Model model, HttpServletRequest request, Principal principal, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		List<ItemEntity> myitemlist = (List<ItemEntity>)itemService.selectMyList(member);

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
		//item 정보 수정
		item.setMember(MemberEntity.builder().mId(writer).build());
		item.setICategory(ICategoryEnumType.valueOf(iCategory));
		item.setITstate(ItStateEnumType.valueOf(iTstate));
		item.setIRegdate(Timestamp.valueOf(LocalDateTime.now()));
		ItemEntity ins_item = itemService.updateItem(item);
		
		//등록된 새로운 사진들 ipid받아와서, photo객체에 iId설정해주기 
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
		
		//사용자가 지운 사진들 객체 테이블에서 제거하기!
		List<Integer> deletelist = new ArrayList();
		String[] delete_pid_str = delete_pids.split(",");
		for(int i=0; i<delete_pid_str.length; i++){
			deletelist.add(Integer.parseInt(delete_pid_str[i]));
		}
		for(Integer j: deletelist) {
			itemPhotoService.deleteItemPhoto(j);
		}
		
		rttr.addFlashAttribute("resultMessage", ins_item==null?"입력실패":"입력성공");
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
		
		rttr.addFlashAttribute("resultMessage", ins_item==null?"입력실패":"입력성공");
		return "redirect:/item/myitemlist";
	}
	
	
	@GetMapping("/itemdelete")
	public String itemDelete(Integer iId, RedirectAttributes rttr ) {
		int ret = itemService.deleteItem(iId);
		rttr.addFlashAttribute("resultMessage", ret==0?"삭제실패":"삭제성공");
		return "redirect:/item/myitemlist";
	}
	
}
