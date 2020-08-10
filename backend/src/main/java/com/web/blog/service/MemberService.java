package com.web.blog.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.Member;
import com.web.blog.model.response.OAuthTokenResponse;

public interface MemberService {
	//회원 전체 조회
	List<Member> findAll();
	//회원 조회 - 회원번호
	Member findMemberByMid(long mid);
	//회원 수정
	Member updateMember(Member member);
	//회원 탈퇴
	void deleteByMid(long mid);
	//회원 가입
	Member saveMember(Member member);
	//카카오 로그인
	String KakaoLogin(OAuthTokenResponse oauthToken);
	//닉네임 중복검사
	Member isNickName(String nickname);
	//로그인
	String Login(String email, String password);
	//이미지 업데이트
	void updateImageById(MultipartFile image, Long mid) throws Exception;

}
