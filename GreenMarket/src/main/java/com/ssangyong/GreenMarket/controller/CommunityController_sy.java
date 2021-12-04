
//수연이 코드

package com.ssangyong.GreenMarket.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.service.CommunityService;


//@Controller
public class CommunityController_sy {

	@Autowired
	CommunityService service;

	@GetMapping("/community/boardlist")
	public void selectAll(Model model, HttpServletRequest request, PageVO pagevo) {

		Page<CommunityEntity> result = service.selectAll(pagevo);

		model.addAttribute("boardResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMaker<>(result));
	}

	
	
	@GetMapping("/community/boarddetail")
	public void selectById(Model model, Integer cId, Principal principal, Authentication authentication, PageVO pagevo ) {
		model.addAttribute("board", service.selectById(cId));
		model.addAttribute("pagevo", pagevo);
		/*
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		UserVO user =  uservice.selectById(userDetails.getUsername());
		model.addAttribute("user",user);
		*/
	}
	
	/*
	@PostMapping("/community/register")
	public String boardRegisterPost(DietDiaryBoardVO board, RedirectAttributes rttr, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		UserVO user = uservice.selectById(userDetails.getUsername());
		board.setUser(user); 
		DietDiaryBoardVO ins_board = service.insertBoard(board);
		
		rttr.addFlashAttribute("resultMessage", ins_board==null?"입력실패":"입력성공");
		return "redirect:/dietdiaryboard/boardlist";
	}

	@GetMapping("/community/delete")
	public String boardDelete(Integer diaryNum, RedirectAttributes rttr ) {
		int ret = service.deleteBoard(diaryNum);
		rttr.addFlashAttribute("resultMessage", ret==0?"삭제실패":"삭제성공");
		return "redirect:/dietdiaryboard/boardlist";
	}
	
	@PostMapping("/community/update")
	public String boardUpdate(DietDiaryBoardVO board, String userId, RedirectAttributes rttr, Authentication authentication, Integer page, Integer size, String type, String keyword) {
		board.setUser(uservice.selectById(userId));
		DietDiaryBoardVO update_board = service.updateBoard(board);	
		rttr.addFlashAttribute("resultMessage", update_board==null?"수정실패":"수정성공");
		
		//방법1...주소창에 안보이기 
		PageVO pagevo = PageVO.builder()
						.page(page).size(size).type(type).keyword(keyword)
						.build();
		rttr.addFlashAttribute("pagevo", pagevo);
		//방법2...주소창에 보이기 
		String param = "page=" + page + "&size=" + size + "&type="+type + "&keyword=" + keyword;
		return "redirect:/dietdiaryboard/boardlist?" + param;
			
	}
	*/

}
