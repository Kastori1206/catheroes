package com.web.blog.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.opsworkscm.model.ResourceNotFoundException;
import com.web.blog.dao.CatDao;
import com.web.blog.dao.FollowDao;
import com.web.blog.dao.MemberDao;
import com.web.blog.model.Article;
import com.web.blog.model.Cat;
import com.web.blog.model.Comment;
import com.web.blog.model.Follow;
import com.web.blog.model.Member;
import com.web.blog.model.request.CatRequest;
import com.web.blog.utill.amazon.AmazonClient;

@Service
public class CatServiceImpl implements CatService {
	@Autowired
	private CatDao catDao;
	
	@Autowired
	private FollowDao followDao;
	
	@Autowired
	private AmazonClient amazonClient;
		
	@Override
	public List<Cat> findCatByLocation(String location) {		
		return catDao.findCatByLocation(location);
	}

	@Override
	public Cat findCatByCatId(long catid) {
		Optional<Cat> catDb = catDao.findCatByCatid(catid);
		
		if(catDb.isPresent()) {
			return catDb.get();
		}else {
			throw new ResourceNotFoundException("Record not found with catid : " + catid);
		}	
	}

	@Override
	public List<Cat> findCatByNickName(String nickname, String location) {
		return catDao.findCatByLocationAndNicknameContaining(location, nickname);
	}

	@Override
	public List<Cat> findCatByBreed(String breed, String location) {
		return catDao.findCatByLocationAndBreed(location, breed);
	}

	@Override
	public Cat saveCat(CatRequest catRequest) {
		
		Cat cat = new Cat();
		System.out.println(catRequest.toString());
		try {
			String path = this.amazonClient.uploadFile(catRequest.getFile(), catDao.getMaxCatId()+1, "profile/cat/");
			
			cat.setNickname(catRequest.getNickname());
			cat.setBreed(catRequest.getBreed());
			cat.setLocation(catRequest.getLocation());
			cat.setImage(path);
			cat.setLat(catRequest.getLat());
			cat.setLng(catRequest.getLng());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catDao.save(cat);
	}

	@Override
	public void deleteByCatId(long catid) {
		Optional<Cat> catDb = catDao.findCatByCatid(catid);

		if(catDb.isPresent()) {
			catDao.delete(catDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with catid : " + catid);			
		}
	}

	@Override
	public Cat updateCat(CatRequest catRequest) {
		Cat cat = catDao.findCatByCatid(catRequest.getCatid()).get();
		cat.setCatid(catRequest.getCatid());
		cat.setAttr(catRequest.getAttr());
		cat.setBreed(catRequest.getBreed());
		cat.setConditions(catRequest.getConditions());		
		return catDao.save(cat);
	}

	@Override
	public List<Follow> findFollowByCatid(long catid) {		
		return followDao.findByCatCatid(catid);
	}

}
