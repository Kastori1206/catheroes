package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.opsworkscm.model.ResourceNotFoundException;
import com.web.blog.dao.MemberDao;
import com.web.blog.model.Member;
import com.web.blog.utill.amazon.AmazonClient;
import com.web.blog.utill.jwt.JwtService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public List<Member> findAll() {
		return memberDao.findAll();
	}
	
	@Override
	public Member findMemberByMid(long mid) {
		Optional<Member> memberDb =  memberDao.findMemberByMid(mid);
		
		if(memberDb.isPresent()) {
			return memberDb.get();
		}else {
			throw new ResourceNotFoundException("Record not found with mid : " + mid);			
		}
	}

	@Override
	public Member updateMember(Member member) {
		Optional<Member> memberDb = memberDao.findMemberByMid(member.getMid());
		
		if(memberDb.isPresent()) {
			Member memberUpdate = memberDb.get();
			memberUpdate.setNickname(member.getNickname());
			memberUpdate.setPassword(member.getPassword());
			memberUpdate.setImage(member.getImage());				

			memberDao.save(memberUpdate);
			return memberUpdate;
		}else {
			throw new ResourceNotFoundException("Record not found with mid : "+member.getMid());
		}
		
	}

	@Override
	public void deleteByMid(long mid) {		
		Optional<Member> memberDb = memberDao.findMemberByMid(mid);
		if(memberDb.isPresent()) {
			memberDao.delete(memberDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with mid : " + mid);			
		}
	}

	@Override
	public Member saveMember(Member member) {
		return memberDao.save(member);
	}

	//구현해야함
	@Override
	public Member KakaoLogin(String email) {
		Optional<Member> memberDb = memberDao.findMemberByEmail(email);
		if(memberDb.isPresent()) {
			return memberDb.get();
		}
		return null;
	}

	@Override
	public Member isNickName(String nickname) {
		Optional<Member> memberDb = memberDao.findMemberByNickname(nickname);
		if(memberDb.isPresent()) {
			return memberDb.get();
		}else {			
			throw new ResourceNotFoundException("Record not found with nickname : "+nickname);
		}
	}

	@Override
	public String Login(String email, String password) {
		Optional<Member> memberDb = memberDao.findMemberByEmailAndPassword(email, password);
		if(memberDb.isPresent()) {			
			return jwtService.create("member", memberDb.get(), "member");
		}else {
			throw new ResourceNotFoundException("Record not equals email,password : "+email);
		}
	}

	@Override
	public void updateImageById(MultipartFile image,Long mid) throws Exception {
		Optional<Member> memberDb = memberDao.findMemberByMid(mid);
		if(memberDb.isPresent()) {
			try {
				String path = this.amazonClient.uploadFile(image, mid, "profile/user/");
				memberDao.updateImage(mid, path);						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else {
			throw new ResourceNotFoundException("Record not found with mid : " + mid);		
		}		
	}	
}
