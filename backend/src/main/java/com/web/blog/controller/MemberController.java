

package com.web.blog.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.Member;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.model.response.OAuthTokenResponse;
import com.web.blog.service.MailService;
import com.web.blog.service.MemberService;
import com.web.blog.utill.jwt.JwtService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RequestMapping("api/v1")
@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private MailService mailService;
	
	@GetMapping("/member/")
	@ApiOperation(value = "?????? ?????? ??????")
	public ResponseEntity<List<Member>> getAllMember(){
		return ResponseEntity.ok().body(memberService.findAll());
	}
	
	@PostMapping("/member/info")
	@ApiOperation(value = "???????????? ?????? ??????")
	public ResponseEntity<Member> getMemberByToken(){
		return ResponseEntity.ok().body(memberService.findMemberByMid(jwtService.getMemberMid()));
	}

	@PostMapping("/member")
	@ApiOperation(value = "?????? ??????")
	public ResponseEntity<Member> saveMember(@RequestBody Member member){
		return ResponseEntity.ok().body(memberService.saveMember(member));
	}
	
	@PutMapping("/member")
	@ApiOperation(value = "?????? ??????")
	public ResponseEntity<Member> updateMember(@RequestParam Map<String,String> request) {
		ObjectMapper objectMapper = new ObjectMapper();	
		Member member = objectMapper.convertValue(request, Member.class);
		
		return ResponseEntity.ok().body(memberService.updateMember(member));
	}
	
	@DeleteMapping("/member/{mid}")
	@ApiOperation(value = "?????? ??????")
	public HttpStatus deleteMember(@PathVariable long mid) {
		memberService.deleteByMid(mid);
		return HttpStatus.OK;
	}

	@PostMapping("/member/{mid}")
	@ApiOperation(value = "??????????????? ?????? ??????")
	public ResponseEntity<Member> getMemberById(@PathVariable long mid){
		return ResponseEntity.ok().body(memberService.findMemberByMid(mid));
	}
	
	@PostMapping("/member/login")
	@ApiOperation(value = "?????????")
	public ResponseEntity<String> Login(@RequestParam("email") String email,@RequestParam("password") String password ){
		return ResponseEntity.ok().body(memberService.Login(email,password));
	}
	
	@PostMapping("/auth/kakao/Login")
	@ApiOperation(value = "????????? ?????????")
	public ResponseEntity<String> kakaoCallback(@RequestBody OAuthTokenResponse oauthToken) {// Data??? ??????????????? ????????????
		return ResponseEntity.ok().body(memberService.KakaoLogin(oauthToken));
	}

	@PostMapping("/member/image")
	@ApiOperation(value = "????????? ????????? ??????")
	public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile image, @RequestParam("mid") Long mid) throws Exception{// Data??? ??????????????? ????????????
		return ResponseEntity.ok().body(memberService.updateImageById(image, mid));
	}
	
	@PostMapping("/member/nickname/")
	@ApiOperation(value = "????????? ?????? ??????")
	public ResponseEntity<Boolean> isNickname(@RequestParam("nickname") String nickname){
		System.out.println(nickname);
		return ResponseEntity.ok().body(memberService.isNickName(nickname));
	}
	
	@PostMapping("/member/email/")
	@ApiOperation(value = "????????? ?????? ??????")
	public ResponseEntity<Boolean> isEmail(@RequestParam("email") String email){
		return ResponseEntity.ok().body(memberService.isEmail(email));
	}

	@PostMapping("/member/newsfeed")
	@ApiOperation(value = "????????? ????????????")
	public ResponseEntity<String> updateNewsfeed(@RequestParam("cid") Long cid, @RequestParam("mid") Long mid) throws Exception{// Data??? ??????????????? ????????????
		return ResponseEntity.ok().body(memberService.updateNewsfeedById(cid, mid));
	}
	
	@PutMapping("/member/clear")
	@ApiOperation(value = "???????????? ????????? ?????????")
	public ResponseEntity<Member> clearAlarm(@RequestParam Map<String,String> request) {
		ObjectMapper objectMapper = new ObjectMapper();	
		Member member = objectMapper.convertValue(request, Member.class);
		
		return ResponseEntity.ok().body(memberService.clearAlarm(member));
	}
	
	@GetMapping("/member/email")
	@ApiOperation(value = "????????? ?????? ??????")
	public ResponseEntity<String> createEmailCheck(@RequestParam String userEmail,@RequestParam String subject,@RequestParam String text){
		return ResponseEntity.ok().body(mailService.send(subject,userEmail,text));		
	}
}
