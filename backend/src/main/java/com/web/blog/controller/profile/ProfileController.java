package com.web.blog.controller.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.web.blog.dao.cat.CatDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.CatInfoResponse;
import com.web.blog.model.cat.Cat;
import com.web.blog.model.cat.RegistRequest;
import com.web.blog.model.user.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RequestMapping("/profile")
@RestController
public class ProfileController {

	@Autowired
	private CatDao catDao;

	// @Autowired
	// private JwtService jwtService;

//	@GetMapping("/detail/{catid}")
//	@ApiOperation(value = "고양이 상세정보")
//	public Object detail(@PathVariable("catid") int catid) {
//
//		Optional<Cat> catOpt = catDao.getCatByCatid(catid);
//
//		ResponseEntity response = null;
//
//		if (catOpt.isPresent()) {
//			final CatInfoResponse result = new CatInfoResponse();
//			result.status = true;
//			result.data = "success";
//			result.nickName = catOpt.get().getNickname();
//			result.age = catOpt.get().getAge();
//			result.breed = catOpt.get().getBreed();
//			result.location = catOpt.get().getLocation();
//			result.attr = catOpt.get().getAttr();
//			result.conditions = catOpt.get().getConditions();
//			result.lat = catOpt.get().getLat();
//			result.lng = catOpt.get().getLng();
//
//			System.out.println(result);
//
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//
//		return response;
//	}
//
//	@PostMapping("/catList")
//	@ApiOperation(value = "고양이 전체 조회")
//	public Object detail() {
//		// System.out.println(jwtService.getUserUid());
//
//		ResponseEntity response = null;
//		List<Cat> catOpt = catDao.findAll();
//		
//		if(!catOpt.isEmpty()){
//			final List<CatInfoResponse> results = new ArrayList<CatInfoResponse>();
//
//			for (Cat cat : catOpt) {
//				CatInfoResponse result = new CatInfoResponse();
//				result.status = true;
//				result.data = "success";
//				result.nickName = cat.getNickname();
//				result.age = cat.getAge();
//				result.breed = cat.getBreed();
//				result.location = cat.getLocation();
//				result.attr = cat.getAttr();
//				result.conditions = cat.getConditions();
//				result.lat = cat.getLat();
//				result.lng = cat.getLng();
//				result.catid = cat.getCatid();
//				results.add(result);				
//			} 
//				
//			response = new ResponseEntity<>(results, HttpStatus.OK);
//		}
//		else {
//        	response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        
//    	return response;
//	}
//	
//	@PostMapping("/regist")
//	@ApiOperation(value = "고양이 등록하기")
//	public Object signup(@Valid @RequestBody RegistRequest request) {
//		final BasicResponse result = new BasicResponse();
//		// 중복처리 필수
//		Cat cat = null;		
//		System.out.println(request);
//		// 등록된 고양이 처리 (나중에 함)
//		// cat = catDao.getCatByNickname(request.getNickname());
//		// if (user != null) {
//		// 	result.status = false;
//		// 	result.data = "nickname";
//		// 	return new ResponseEntity<>(result, HttpStatus.OK);
//		// }
//
//		// 회원가입단을 생성해 보세요.
//		cat = Cat.builder()
//				 .nickname(request.getNickname())
//				 .breed(request.getBreed())
//				 .image(request.getImage())
//				 .lat(request.getLat())
//				 .lng(request.getLng())
//				 .age(0)
//				 .attr("")
//				 .conditions("")
//				 .location("")
//				 .food("")
//				 .family("")
//				 .neutered("")	
//				 .hospital("")			 
//				 .build();
//		System.out.println(cat.toString());		 
//		catDao.save(cat);
//		result.status = true;
//		result.data = "success";
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//
//	@PostMapping("/follow/{catid}")
//	@ApiOperation(value = "고양이 상세정보")
//	public Object follow(@PathVariable("catid") int catid) {
//
//		Optional<Cat> catOpt = catDao.getCatByCatid(catid);
//
//		ResponseEntity response = null;
//
//		if (catOpt.isPresent()) {
//			final CatInfoResponse result = new CatInfoResponse();
//			result.status = true;
//			result.data = "success";
//			result.nickName = catOpt.get().getNickname();
//			result.age = catOpt.get().getAge();
//			result.breed = catOpt.get().getBreed();
//			result.location = catOpt.get().getLocation();
//			result.attr = catOpt.get().getAttr();
//			result.conditions = catOpt.get().getConditions();
//			result.lat = catOpt.get().getLat();
//			result.lng = catOpt.get().getLng();
//
//			System.out.println(result);
//
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//
//		return response;
//	}
}

