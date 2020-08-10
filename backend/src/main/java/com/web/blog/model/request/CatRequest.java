package com.web.blog.model.request;

import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.Cat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatRequest{
	//고양이 정보
	// private Cat cat;
	private long catid;
	private String breed;
	private String attr;
	private String conditions;

	//고양이 사진
	// private MultipartFile file;    
	
}
