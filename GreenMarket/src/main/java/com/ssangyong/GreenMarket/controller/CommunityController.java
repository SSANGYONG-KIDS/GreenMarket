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
import com.ssangyong.GreenMarket.model.CommunityReplyEntity;
import com.ssangyong.GreenMarket.model.CommunityTagEntity;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.service.CommunityService;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.CommunityReplyService;


@Controller
public class CommunityController {

	@Autowired
	CommunityService service;

	@Autowired
	LoginService log_service;
	
	@Autowired
	CommunityReplyService reply_service;
	
	@GetMapping("/community/boardlist")
	public void selectAll(Model model, HttpServletRequest request, PageVO pagevo) {

		Page<CommunityEntity> result = service.selectAll(pagevo);

		model.addAttribute("boardResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMaker<>(result));
	}

	//내가 쓴 글 보기
	@GetMapping("/community/myBoardlist")
	public String myBoardlist(Model model, Principal principal, Authentication authentication, PageVO pagevo) {
		System.out.println("내가 쓴 글 보기 controller");
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity user =  log_service.selectById(userDetails.getUsername());
		
		Page<CommunityEntity> result = service.selectMyBoardlist(pagevo, user);
		
		model.addAttribute("boardResult",result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMaker<>(result));
		return "community/myBoardlist";	
	}
	
	//태그 포함된 게시글 보기
	@GetMapping("community/tagBoardlist/{ctId}")
	public String sameTagBoardlist(@PathVariable Integer ctId, Model model, PageVO pagevo) {
		System.out.println("controller-같은 태그 게시물보기() 실행");
		System.out.println("ctId: "+ctId);
			
		//같은 ctname을 가진 게시글을 받아오기
		List<CommunityTagEntity> tagList = service.selectByTagName(ctId);
		
		//model attribute 등록하기
		model.addAttribute("tagList",tagList);
		return "community/hashTagBoardlist";
	}
	
	
	//글 상세보기
	@GetMapping("/community/boarddetail")
	public String selectById(Model model, Integer cId, Principal principal, Authentication authentication, PageVO pagevo ) {
		model.addAttribute("board", service.selectById(cId));
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("cId",cId);
		model.addAttribute("tags", service.selectTags(cId));
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity user =  log_service.selectById(userDetails.getUsername());
		model.addAttribute("user",user);
		
		//조회수 +1
		service.updateViews(cId);
	
		return "community/boarddetail";
	}
	
	// 댓글 등록
	@RequestMapping(value="community/boarddetail", method= {RequestMethod.POST})
	@ResponseBody
	public void replySave(CommunityReplyEntity reply, Integer cId, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity user = log_service.selectById(userDetails.getUsername());
		reply.setMember(user);
		
		CommunityEntity board = service.selectById(cId);
		reply.setCommunity(board);
	
		//대댓글 해야하는데 ...일단 안하기
		reply.setCrDepth(0);
		reply.setCrOrder(0);
		reply.setCrTopno(0);
		
		reply_service.updateOrInsert(reply);
	}
	
	
	//글 등록 페이지로 이동
	@GetMapping("community/register")
	public String register() {
		return "community/register";
	}
	
	//글 등록완료
	@RequestMapping(value="community/register", method= {RequestMethod.POST})
	@ResponseBody
	public void boardRegisterPost(CommunityEntity board, @RequestParam(value="tagArr[]") List<String> tagArr, RedirectAttributes rttr, Authentication authentication) {
		System.out.println("controller-글 등록하기");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity user = log_service.selectById(userDetails.getUsername());
		
		service.insertBoard(board, user);
		service.insertTag(tagArr, board);
	}
	
	
	//글 수정 페이지로 이동
	@GetMapping("community/update")
	public String update(Model model, Integer cId) {

		model.addAttribute("board", service.selectById(cId));
		return "community/update";
	}
	
	
	//댓글 삭제하기
	@GetMapping("community/boarddetail/{cId}/{crId}")
	public String replyDelete(@PathVariable Integer cId, @PathVariable Integer crId) {
		int ret = reply_service.deleteReply(crId);
		
		return "redirect:/community/boarddetail?cId="+cId;
	}
	
	//글 삭제하기
	@RequestMapping(value="community/boarddetail", method= {RequestMethod.DELETE})
	@ResponseBody
	public void boardDelete(Integer cId, RedirectAttributes rttr ) {
		int ret = service.deleteBoard(cId);
		
		rttr.addFlashAttribute("resultMessage", ret==0?"삭제실패":"삭제성공");
	}
	
	
	//글 수정
	@RequestMapping(value="community/update", method= {RequestMethod.POST})
	@ResponseBody
	public void update(CommunityEntity board) {
		service.updateBoard(board);
	}
}