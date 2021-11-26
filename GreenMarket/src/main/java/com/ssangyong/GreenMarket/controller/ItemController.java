package com.ssangyong.GreenMarket.controller;

import java.security.Principal;

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

import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.LoginService;

@Controller
@RequestMapping("/item/*")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	@Autowired
	LoginService loginService;
	
	
	@GetMapping("/itemlist")
	public void selectAll(Model model, HttpServletRequest request, PageVO pagevo) {

		Page<ItemEntity> result = itemService.selectAll(pagevo);

		model.addAttribute("itemResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMaker<>(result));
	}
	
	@GetMapping("/itemdetail")
	public void selectById(Model model, Integer iId, Principal principal, Authentication authentication, PageVO pagevo ) {
		ItemEntity item = itemService.selectById(iId);
		model.addAttribute("item", item);
		model.addAttribute("item_owner",item.getMember());
		model.addAttribute("pagevo", pagevo);
		
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
	}
	
	@PostMapping("/register")
	public String itemRegisterPost(ItemEntity item, RedirectAttributes rttr, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member = loginService.selectById(userDetails.getUsername());
		item.setMember(member); 
		ItemEntity ins_item = itemService.insertItem(item);
		
		rttr.addFlashAttribute("resultMessage", ins_item==null?"입력실패":"입력성공");
		return "redirect:/item/itemlist";
	}
	
	@GetMapping("/delete")
	public String itemDelete(Integer iId, RedirectAttributes rttr ) {
		int ret = itemService.deleteItem(iId);
		rttr.addFlashAttribute("resultMessage", ret==0?"삭제실패":"삭제성공");
		return "redirect:/item/itemlist";
	}
	
	@PostMapping("/update")
	public String itemUpdate(ItemEntity item, String mId, RedirectAttributes rttr, Authentication authentication, Integer page, Integer size, String type, String keyword) {
		item.setMember(loginService.selectById(mId));
		ItemEntity update_item = itemService.updateItem(item);	
		rttr.addFlashAttribute("resultMessage", update_item==null?"수정실패":"수정성공");
		
		//방법1...주소창에 안보이기 
		PageVO pagevo = PageVO.builder()
						.page(page).size(size).type(type).keyword(keyword)
						.build();
		rttr.addFlashAttribute("pagevo", pagevo);
		
		return "redirect:/item/itemlist";
		
		//방법2...주소창에 보이기 
		//String param = "page=" + page + "&size=" + size + "&type="+type + "&keyword=" + keyword;
		//return "redirect:/item/itemlist?" + param;
			
	}
}
