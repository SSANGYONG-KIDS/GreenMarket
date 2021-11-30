package com.ssangyong.GreenMarket.controller;

import java.security.Principal;
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

import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
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
	public void selectMyItem(Model model, HttpServletRequest request, Principal principal, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		List<ItemEntity> myitemlist = (List<ItemEntity>)itemService.selectMyList(member);

		model.addAttribute("myitemlist", myitemlist);
	}
	
	@GetMapping("/iteminsert")
	public void itemInsertForm(Model model, Principal principal, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		model.addAttribute("tstates",ItStateEnumType.values());
		model.addAttribute("categorys",ICategoryEnumType.values());
		ItemEntity item = ItemEntity.builder().member(member).build();
		model.addAttribute("item_id",itemService.insertItem(item).getIId());
	}
	
	
	@PostMapping("/iteminsert")
	public String itemRegisterPost(ItemEntity item, Integer iId, RedirectAttributes rttr, Authentication authentication) {
		item.setIId(iId);
		ItemEntity ins_item = itemService.updateItem(item);
		
		rttr.addFlashAttribute("resultMessage", ins_item==null?"입력실패":"입력성공");
		return "redirect:/item/myitemlist";
	}
	
	
	@GetMapping("/itemdelete")
	public String itemDelete(Integer iId, RedirectAttributes rttr ) {
		int ret = itemService.deleteItem(iId);
		rttr.addFlashAttribute("resultMessage", ret==0?"삭제실패":"삭제성공");
		return "redirect:/item/myitemlist";
	}
	
	@GetMapping("/itemupdate")
	public String itemUpdateForm(Model model, Integer iId, Principal principal, Authentication authentication) {
		
		model.addAttribute("item",itemService.selectById(iId));
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		return "/item/iteminsert";
	}
	
	@PostMapping("/itemupdate")
	public String itemUpdate(ItemEntity item, String mId, RedirectAttributes rttr, Authentication authentication, Integer page, Integer size, String itemName, ICategoryEnumType itemSort, String startDate, String endDate, Integer priceLimit) {
		item.setMember(loginService.selectById(mId));
		ItemEntity update_item = itemService.updateItem(item);	
		rttr.addFlashAttribute("resultMessage", update_item==null?"수정실패":"수정성공");
		
		//방법1...주소창에 안보이기 
		ItemPageVO pagevo = ItemPageVO.builder()
						.page(page).size(size).itemName(itemName).itemSort(itemSort).startDate(startDate).endDate(endDate).priceLimit(priceLimit)
						.build();
		rttr.addFlashAttribute("pagevo", pagevo);
		
		return "redirect:/item/myitemlist";
		
		//방법2...주소창에 보이기 
		//String param = "page=" + page + "&size=" + size + "&type="+type + "&keyword=" + keyword;
		//return "redirect:/item/itemlist?" + param;
			
	}
}
