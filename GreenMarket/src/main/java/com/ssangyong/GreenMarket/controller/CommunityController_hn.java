//package com.ssangyong.GreenMarket.controller;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.servlet.support.RequestContextUtils;
//
//import com.ssangyong.GreenMarket.model.CommunityEntity;
//import com.ssangyong.GreenMarket.model.MemberEntity;
//import com.ssangyong.GreenMarket.model.PageMaker_hn;
//import com.ssangyong.GreenMarket.model.PageVO_hn;
//import com.ssangyong.GreenMarket.repository.CommunityRepository;
//import com.ssangyong.GreenMarket.service.CommunityService;
//
//import lombok.extern.java.Log;
//
//@Controller
//@RequestMapping("/community/*")
//public class CommunityController_hn {
//
//	@Autowired
//	CommunityService service;
//
//	//@Autowired
//	//private CommunityRepository repo;
//	
//	@GetMapping("/register")
//	public void CommunityPost() {
//		System.out.println("Community 컨트롤러의 CommunityPost()");
//		//return 안해줘도 ㄱㅊ
//	}
//	
//	
//	@GetMapping("/boardlist")
//	public void selectAll(Model model, HttpServletRequest request, PageVO_hn pagevo) {
//
//		Page<CommunityEntity> result = service.selectAll(pagevo);
//
//		model.addAttribute("boardResult", result);
//		model.addAttribute("pagevo", pagevo);
//		model.addAttribute("result", new PageMaker_hn<>(result));
//	}
//	
///*	
//	@GetMapping("/boardlist")
//	public void selectAll(@ModelAttribute("pageVO") PageVO vo, Model model) {
//		Pageable page = vo.makePageable(0, "cId");
//		Page<CommunityEntity> result = repo.findAll(repo.makePredicate(vo.getType(), vo.getKeyword()), page);
//		
//		log.info(""+page);
//		log.info(""+result);
//		log.info("total page number : "+result.getTotalPages());
//		model.addAttribute("pagevo", vo);
//		model.addAttribute("result", new PageMaker(result));
//	}
//*/	
//	
//	
//	@GetMapping("/boarddetail")
//	public void selectById(Model model, Integer cId, Principal principal, Authentication authentication, PageVO_hn pagevo ) {
//		model.addAttribute("board", service.selectById(cId));
//		model.addAttribute("pagevo", pagevo);
//		/*
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		UserVO user =  uservice.selectById(userDetails.getUsername());
//		model.addAttribute("user",user);
//		*/
//	}
//	
//	
//	// MemberService는 아직 pull 전이라서 없엉
//	@PostMapping("/register")
//	public String boardRegisterPost(CommunityEntity board, RedirectAttributes rttr, Authentication authentication) {
//		System.out.println("Community 컨트롤러의 boardRegisterPost()");
//		
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
////		MemberEntity user = MemberService.selectById(userDetails.getUsername()); //Community 엔티티로 바꾸고
////		board.setMember(user);  //글쓴이 배정
//		CommunityEntity ins_board = service.insertBoard(board); //DB에 넣는작업
//		
//		rttr.addFlashAttribute("resultMessage", ins_board==null?"입력실패":"입력성공"); //밑에 redirect페이지로 변수 들고가고 싶을때 쓰느 함수
//		return "redirect:/community/boardlist";
//	}
//	
//	
//	/*
//	@GetMapping("/community/delete")
//	public String boardDelete(Integer diaryNum, RedirectAttributes rttr ) {
//		int ret = service.deleteBoard(diaryNum);
//		rttr.addFlashAttribute("resultMessage", ret==0?"삭제실패":"삭제성공");
//		return "redirect:/dietdiaryboard/boardlist";
//	}
//	
//	@PostMapping("/community/update")
//	public String boardUpdate(DietDiaryBoardVO board, String userId, RedirectAttributes rttr, Authentication authentication, Integer page, Integer size, String type, String keyword) {
//		board.setUser(uservice.selectById(userId));
//		DietDiaryBoardVO update_board = service.updateBoard(board);	
//		rttr.addFlashAttribute("resultMessage", update_board==null?"수정실패":"수정성공");
//		
//		//방법1...주소창에 안보이기 
//		PageVO pagevo = PageVO.builder()
//						.page(page).size(size).type(type).keyword(keyword)
//						.build();
//		rttr.addFlashAttribute("pagevo", pagevo);
//		//방법2...주소창에 보이기 
//		String param = "page=" + page + "&size=" + size + "&type="+type + "&keyword=" + keyword;
//		return "redirect:/dietdiaryboard/boardlist?" + param;
//			
//	}
//	*/
//
//}
