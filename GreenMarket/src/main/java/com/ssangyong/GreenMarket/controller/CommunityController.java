package com.ssangyong.GreenMarket.controller;

//수연이 코드


import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.service.CommunityService;
import com.ssangyong.GreenMarket.service.LoginService;


@Controller
public class CommunityController {

	@Autowired
	CommunityService service;

	@Autowired
	LoginService log_service;
	
	@GetMapping("/community/boardlist")
	public void selectAll(Model model, HttpServletRequest request, PageVO pagevo) {

		Page<CommunityEntity> result = service.selectAll(pagevo);

		model.addAttribute("boardResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMaker<>(result));
	}
	
	
	@GetMapping("/community/boarddetail")
	public String selectById(Model model, Integer cId, Principal principal, Authentication authentication, PageVO pagevo ) {
		System.out.println("게시글 상세보기 controller");
		model.addAttribute("board", service.selectById(cId));
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("cId",cId);
		return "community/boarddetail";
		/*
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		UserVO user =  uservice.selectById(userDetails.getUsername());
		model.addAttribute("user",user);
		*/
	}
	
	//글 등록 페이지로 이동
	@GetMapping("community/register")
	public String register() {
		System.out.println("controller-getmapping-register()");
		return "community/register";
	}
	
	//글 등록완료
	//@DeleteMapping("/community/register")
	@RequestMapping(value="community/register", method= {RequestMethod.POST})
	@ResponseBody
	public void boardRegisterPost(CommunityEntity board, RedirectAttributes rttr, Authentication authentication) {
		System.out.println("controller-글 등록하기");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity user = log_service.selectById(userDetails.getUsername());
		
		//MemberEntity user = log_service.selectById("test"); // 임시로 세션 아이디 대신 test 가져오기
		//System.out.println("board.getCTitle(): "+board.getCTitle());
		//System.out.println("board.getCContent(): "+board.getCContent());
		
		service.insertBoard(board, user);
		
		//MemberEntity user = service.selectById(userDetails.getUsername());
		/*
		board.setMember(user);
		//board.setUser(user); 
		CommunityEntity ins_board = service.insertBoard(board);
		//DietDiaryBoardVO ins_board = service.insertBoard(board);
		
		rttr.addFlashAttribute("resultMessage", ins_board==null?"입력실패":"입력성공");
		return "redirect:/community/boardlist";
		*/
	}
	
	
	//글 삭제하기
	@RequestMapping(value="community/boarddetail", method= {RequestMethod.DELETE})
	@ResponseBody
	public void boardDelete(Integer cId, RedirectAttributes rttr ) {
		System.out.println("controller-boardDelete() 실행");
		System.out.println("cId(data): "+cId);
		int ret = service.deleteBoard(cId);
		System.out.println("ret: "+ret);
		rttr.addFlashAttribute("resultMessage", ret==0?"삭제실패":"삭제성공");
	}
/*	
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
