//package com.web.blog.controller;
//
//import java.util.Optional;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.web.blog.dao.MemberDAO;
//import com.web.blog.model.Member;
//import com.web.blog.model.request.KakaoProfileRequest;
//import com.web.blog.model.request.UserSignupRequest;
//import com.web.blog.model.request.UserUpdateRequest;
//import com.web.blog.model.response.BasicResponse;
//import com.web.blog.model.response.LoginResponse;
//import com.web.blog.model.response.OAuthTokenResponse;
//import com.web.blog.model.response.UserInfoResponse;
//import com.web.blog.utill.jwt.JwtService;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
//		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
//		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
//		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
//
//@CrossOrigin(origins = { "http://localhost:3000" })
//@RestController
//public class AccountController {
//
//	@Autowired
//	private MemberDAO userDao;
//
//	@Autowired
//	private JwtService jwtService;
//
//	@PostMapping("/account/login")
//	@ApiOperation(value = "로그인")
//	public Object login(@RequestParam(required = true) final String email,
//			@RequestParam(required = true) final String password) {
//		System.out.println("------------------------------");
//		System.out.println(email);
//		System.out.println(password);
//
//		Optional<Member> userOpt = userDao.findUserByEmailAndPassword(email, password);
//
//		ResponseEntity response = null;
//
//		if (userOpt.isPresent()) {
//			// 회원 정보가 존재하면 토큰 생성
//			String token = jwtService.create("user", userOpt.get(), "user");
//			System.out.println(token);
//			final LoginResponse result = new LoginResponse();
//			result.status = true;
//			result.data = "success";
//			result.token = token;
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//		} else {
//			final LoginResponse result = new LoginResponse();
//			result.status = true;
//			result.data = "fail";
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//		}
//
//		return response;
//	}
//
//	@PostMapping("/account/delete")
//	@ApiOperation(value = "회원탈퇴")
//	public Object deleteUser(String email) {
//		System.out.println(email);
//		ResponseEntity response = null;
//
//		BasicResponse result = new BasicResponse();
//		if(userDao.deleteUserByEmail(email)>0) {
//			result.status = true;
//			result.data = "success";
//		}else {
//			result.status = false;
//			result.data = "fail";
//		}
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//
//		return response;
//	}
//
//	@PostMapping("/account/update")
//	@ApiOperation(value = "회원정보 수정")
//	public Object update(@Valid @RequestBody UserUpdateRequest request) {
//		
//		ResponseEntity response = null;
//		final UserInfoResponse result = new UserInfoResponse();
//		Member user = userDao.getUserByUid(request.getUid()).get();
//
//		// 회원정보 수정 부분 코드작성
//		user.setEmail(request.getEmail());
//		user.setNickname(request.getNickname());
//		user.setPassword(request.getPassword());
//		userDao.save(user);
//
//		result.status = true;
//		result.data = "success";
//		result.email = user.getEmail();
//		result.nickName = user.getNickname();
//		result.uid = user.getUid();
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//
//		return response;
//	}
//	
//	@PostMapping("/account/signup")
//	@ApiOperation(value = "가입하기")
//	public Object signup(@Valid @RequestBody UserSignupRequest request) {
//		final BasicResponse result = new BasicResponse();
//		// 이메일, 닉네임 중복처리 필수
//		Member user = null;
//		String email = null;
//		String nickname = null;
//		user = userDao.getUserByNickname(request.getNickname());
//		if (user != null) {
//			result.status = false;
//			result.data = "nickname";
//			return new ResponseEntity<>(result, HttpStatus.OK);
//		}
//		user = userDao.getUserByEmail(request.getEmail());
//		if (user != null) {
//			result.status = false;
//			result.data = "email";
//			return new ResponseEntity<>(result, HttpStatus.OK);
//		}
//
//		// 회원가입단을 생성해 보세요.
//		user = Member.builder().email(request.getEmail()).password(request.getPassword()).nickname(request.getNickname())
//				.build();
//		userDao.save(user);
//		result.status = true;
//		result.data = "success";
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//
//	@PostMapping("/account/info")
//	@ApiOperation(value = "사용자 정보보기")
//	public Object info() {
//		// System.out.println(jwtService.getUserUid());
//		int uid = jwtService.getUserUid();
//		System.out.println(uid);
//
//		Optional<Member> userOpt = userDao.getUserByUid(uid);
//
//		ResponseEntity response = null;
//
//		if (userOpt.isPresent()) {
//			final UserInfoResponse result = new UserInfoResponse();
//			result.status = true;
//			result.data = "success";
//			result.email = userOpt.get().getEmail();
//			result.nickName = userOpt.get().getNickname().split("_")[0];
//			result.uid = userOpt.get().getUid();
//			result.password = userOpt.get().getPassword();
//			result.image = userOpt.get().getImage();
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//
//		return response;
//	}
//
//	@PostMapping("/account/findByUid")
//	@ApiOperation(value = "uid로 유저정보 받아오기")
//	public Object findByUid(@RequestParam(required = true) final long uid) {
//		// System.out.println(jwtService.getUserUid());		
//		System.out.println(uid);
//		Optional<Member> userOpt = userDao.getUserByUid(uid);
//
//		ResponseEntity response = null;
//
//		if (userOpt.isPresent()) {
//			System.out.println( userOpt.get().getImage());
//			final UserInfoResponse result = new UserInfoResponse();
//			result.status = true;
//			result.data = "success";
//			result.email = userOpt.get().getEmail();
//			result.nickName = userOpt.get().getNickname().split("_")[0];
//			result.image = userOpt.get().getImage();
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//
//		return response;
//	}
//
//	@PostMapping("/auth/kakao/Login")
//	@ApiOperation(value = "카카오 로그인")
//	public Object kakaoCallback(@Valid @RequestBody OAuthTokenResponse oauthToken) {// Data를 리턴해주는 컨트롤러		
//		RestTemplate rt = new RestTemplate();
//		// HttpHeader 오브젝트 생성
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
//		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
//
//		// Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
//		ResponseEntity<String> KakaoResponse = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
//				kakaoProfileRequest, String.class);
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		KakaoProfileRequest kakaoProfile = null;
//		try {
//			kakaoProfile = objectMapper.readValue(KakaoResponse.getBody(), KakaoProfileRequest.class);
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
//		// User 오브젝트 : username, password, email
//		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
//		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//
//		System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
//		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
//
//		Member kakaoUser = Member.builder()
//				.nickname(kakaoProfile.getProperties().getNickname() + "_" + kakaoProfile.getId())
//				.email(kakaoProfile.getKakao_account().getEmail()).auth("kakao").image("defaule.jpg").build();
//
//		ResponseEntity response = null;
//
//		// 가입자 혹은 비가입자 체크 해서 처리
//		Member originUser = userDao.getUserByEmail(kakaoUser.getEmail());
//
//		if (originUser == null) {
//			System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
//			userDao.save(kakaoUser);
//			Member user = userDao.getUserByEmail(kakaoUser.getEmail());
//
//			String token = jwtService.create("user", user, "user");
//			System.out.println(token);
//			final LoginResponse result = new LoginResponse();
//			result.status = true;
//			result.data = "success";
//			result.token = token;
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//		} else {// 기존유저
//			if (originUser.getAuth() == null) {
//				final LoginResponse result = new LoginResponse();
//				result.status = false;
//				result.data = "기존유저입니다.";
//
//				response = new ResponseEntity<>(result, HttpStatus.OK);
//			} else {
//				System.out.println("자동로그인을 진행");
//				// 로그인 처리
//				Member user = userDao.getUserByEmail(kakaoUser.getEmail());
//
//				String token = jwtService.create("user", user, "user");
//				System.out.println(token);
//				final LoginResponse result = new LoginResponse();
//				result.status = true;
//				result.data = "success";
//				result.token = token;
//				response = new ResponseEntity<>(result, HttpStatus.OK);
//			}
//		}
//
//		return response;
//	}
//}