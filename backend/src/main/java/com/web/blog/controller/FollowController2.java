//package com.web.blog.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import com.web.blog.dao.FollowDao;
//import com.web.blog.model.Follow;
//import com.web.blog.model.response.BasicResponse;
//import com.web.blog.model.response.FollowResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
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
//@RequestMapping("/follow")
//@RestController
//public class FollowController {
//
//	@Autowired
//	private FollowDao followDao;
//
//	@PostMapping("/findByCatId")
//	@ApiOperation(value = "팔로워 찾기")
//	public Object findByCatId(@RequestParam(required = true) final long catid) {
//		System.out.println("------------------------------");
//
//		List<Follow> followOpt = followDao.findFollowByCatid(catid);
//
//		ResponseEntity response = null;
//
//		if (!followOpt.isEmpty()) {
//			final List<FollowResponse> results = new ArrayList<FollowResponse>();
//
//			for (Follow follow : followOpt) {
//				FollowResponse result = new FollowResponse();
//				result.status = true;
//				result.data = "success";
//				result.fcid = follow.getFcid();
//				result.catid = follow.getCatid();
//				result.userid = follow.getUserid();
//				results.add(result);
//			}
//
//			response = new ResponseEntity<>(results, HttpStatus.OK);
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//
//		return response;
//	}
//
//	@PostMapping("/findCatByUserId")
//	@ApiOperation(value = "팔로우한 고양이 찾기")
//	public Object findCatByUserId(@RequestParam(required = true) final long userid) {
//		System.out.println("----------------------------");
//		List<Follow> followOpt = followDao.findFollowByUserid(userid);
//
//		ResponseEntity response = null;
//
//		if (!followOpt.isEmpty()) {
//			final List<FollowResponse> results = new ArrayList<FollowResponse>();
//
//			for (Follow follow : followOpt) {
//				FollowResponse result = new FollowResponse();
//				result.status = true;
//				result.data = "success";
//				result.fcid = follow.getFcid();
//				result.catid = follow.getCatid();
//				result.userid = follow.getUserid();
//				results.add(result);
//			}
//
//			response = new ResponseEntity<>(results, HttpStatus.OK);
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//
//		return response;
//	}
//
//	@PostMapping("/followCat")
//	@ApiOperation(value = "팔로우 추가/제거")
//	public Object followCat(@RequestParam(required = true) final long catid,
//			@RequestParam(required = true) final long userid) {
//		ResponseEntity response = null;
//
//		Optional<Follow> follow = null;
//		follow = followDao.findFollowByCatidAndUserid(catid, userid);
//
//		if (!follow.isPresent()) {
//			System.out.println("follow 없음");
//
//			Follow input = null;
//			input = Follow.builder().catid(catid).userid(userid).build();
//			followDao.save(input);
//
//			Boolean status = true;
//			response = new ResponseEntity<>(status, HttpStatus.OK);
//		} else {
//			System.out.println("follow 있음");
//
//			if (followDao.deleteFollowByCatidAndUserid(catid, userid) > 0) {
//				Boolean status = false;
//				response = new ResponseEntity<>(status, HttpStatus.OK);
//			}
//
//		}
//
//		return response;
//	}
//
//	@PostMapping("/followCheck")
//	@ApiOperation(value = "팔로우 체크")
//	public Object followCheck(@RequestParam(required = true) final long catid,
//			@RequestParam(required = true) final long userid) {
//		ResponseEntity response = null;
//
//		Optional<Follow> follow = null;
//		follow = followDao.findFollowByCatidAndUserid(catid, userid);
//
//		if (follow.isPresent()) {
//
//			Boolean status = true;
//			response = new ResponseEntity<>(status, HttpStatus.OK);
//		} else {
//			Boolean status = false;
//			response = new ResponseEntity<>(status, HttpStatus.OK);
//		}
//
//		return response;
//	}
//}