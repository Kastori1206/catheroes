package com.web.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.web.blog.dao.CatDao;
import com.web.blog.dao.FollowDao;
import com.web.blog.model.Cat;
import com.web.blog.model.Follow;
import com.web.blog.model.Member;
import com.web.blog.model.request.CatRequest;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.model.response.CatInfoResponse;
import com.web.blog.service.CatService;
import com.web.blog.utill.amazon.AmazonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RequestMapping("/api/v1")
@RestController
public class CatController {

	@Autowired
	private CatService catService;
		
	@GetMapping("/cat")
	@ApiOperation(value = "고양이 전체 조회")
	public ResponseEntity<List<Cat>> getAllCatget(String location){
		return ResponseEntity.ok().body(catService.findCatByLocation(location));
	}
	
	@GetMapping("/cat/detail/{catid}")
	@ApiOperation(value = "고양이 상세정보")
	public ResponseEntity<Cat> getCatById(@PathVariable("catid") long catid) {
		return ResponseEntity.ok().body(catService.findCatByCatId(catid));
	}
	
	@PutMapping("/cat/update/{catid}")
	@ApiOperation(value = "고양이 상세정보 수정")
	public ResponseEntity<Cat> updateCat(@PathVariable long catid, @RequestParam("cat") CatRequest catRequest) {
		catRequest.getCat().setCatid(catid);
		return ResponseEntity.ok().body(catService.updateCat(catRequest));
	}

	@DeleteMapping("/cat/delete/{catid}")
	@ApiOperation(value = "고양이 삭제")
	public HttpStatus deleteCat(@PathVariable long catid){
		catService.deleteByCatId(catid);
		return HttpStatus.OK;
	}

	@PostMapping("/cat")
	@ApiOperation(value = "가입하기")
	public ResponseEntity<Cat> saveCat(CatRequest catRequest) {		
		return ResponseEntity.ok().body(catService.saveCat(catRequest));
	}	

	@GetMapping("/cat/follow/{catid}")
	@ApiOperation(value = "고양이 팔로우 정보")
	public ResponseEntity<List<Follow>> follow(@PathVariable("catid") int catid) {
		return ResponseEntity.ok().body(catService.findFollowByCatid(catid));
	}

	@GetMapping("/cat/search")
	@ApiOperation(value = "고양이 이름으로 검색")
	public ResponseEntity<List<Cat>> getCatByNickName(String nickname, String location) {
		return ResponseEntity.ok().body(catService.findCatByNickName(nickname, location));

	}

	@PostMapping("/cat/searchImage")
	@ApiOperation(value = "고양이 사진으로 검색")
	public ResponseEntity<List<Cat>> searchImage(String breed, String location) {
		return ResponseEntity.ok().body(catService.findCatByBreed(breed, location));
	}
}
