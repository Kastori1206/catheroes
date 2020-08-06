package com.web.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.dao.ArticleDao;
import com.web.blog.dao.CommentDao;
import com.web.blog.model.Article;
import com.web.blog.model.Comment;
import com.web.blog.model.request.CommentSaveRequest;
import com.web.blog.model.response.ArticleResponse;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.model.response.CommentResponse;
import com.web.blog.utill.amazon.AmazonClient;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@RequestMapping("/article")
@RestController
public class ArticleController {

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private CommentDao commentDao;
	
	private AmazonClient amazonClient;

	@Autowired
	ArticleController(AmazonClient amazonClient) {
	   this.amazonClient = amazonClient;
	}

	@PostMapping("/deleteArticle")
	@ApiOperation(value = "포스트 삭제하기")
	public Object deleteArticle(@RequestParam(required = true) final long articleid) {
		final BasicResponse result = new BasicResponse();
		Article article = null;		
//		System.out.println(articleid);
		
		if(articleDao.deleteArticleByArticleid(articleid)>0) {
			result.status = true;
			result.data = "success";
		} else {
			result.status = false;
			result.data = "fail";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/saveArticle")
	@ApiOperation(value = "포스트 등록하기")
	public Object signup(@RequestParam("image") MultipartFile image,
				   @RequestParam("userid") long userid,
				   @RequestParam("catid") long catid,
				   @RequestParam("title") String title,
				   @RequestParam("content") String content,         
				   @RequestParam("imgpath") String imgpath
		  ) throws IOException  {
	   final BasicResponse result = new BasicResponse();

	   Article article = null;      
	

	   // 생성 코드
	   article = Article.builder()
			  .userid(userid)
			  .catid(catid)
			  .image(imgpath)
			  .title(title)
			  .content(content)
			  .build();
	   
	   articleDao.save(article);
	   this.amazonClient.uploadFile(image, article.getArticleid(), imgpath);
	   result.status = true;
	   result.data = "success";

	   return new ResponseEntity<>(result, HttpStatus.OK);
 	}

	@PostMapping("/deleteComment")
	@ApiOperation(value = "댓글 삭제하기")
	public Object deleteComment(@RequestParam(required = true) final long commentid) {
		final BasicResponse result = new BasicResponse();
		Comment comment = null;		
		System.out.println(commentid);
		
		if(commentDao.deleteCommentByCommentid(commentid)>0) {
			result.status = true;
			result.data = "success";
		} else {
			result.status = false;
			result.data = "fail";
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/saveComment")
	@ApiOperation(value = "댓글 등록하기")
	public Object saveComment(@Valid @RequestBody CommentSaveRequest req) {
		final BasicResponse result = new BasicResponse();
		Comment comment = null;		
		System.out.println(req);

		comment = Comment.builder()
				 .articleid(req.getArticleid())
				 .comment(req.getComment())
				 .writer(req.getWriter())
				 .build();
		System.out.println(comment.toString());		 
		commentDao.save(comment);
		result.status = true;
		result.data = "success";

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/findByCatId")
	@ApiOperation(value = "catid에 해당하는 게시글 조회")
	public Object findByCatId(@RequestParam(required = true) final long catid) {
		System.out.println("------------------------------");

		List<Article> articleOpt = articleDao.findArticleByCatid(catid);

		ResponseEntity response = null;

		if (!articleOpt.isEmpty()) {	
			final List<ArticleResponse> results = new ArrayList<ArticleResponse>();

			for(Article article : articleOpt) {
				ArticleResponse result = new ArticleResponse();
				result.status = true;
				result.data = "success";
				result.articleid = article.getArticleid();
				result.userid = article.getUserid();
				result.catid = article.getCatid();
				result.title = article.getTitle();
				result.content = article.getContent();
				results.add(result);
			}

			response = new ResponseEntity<>(results, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return response;
	}
	
	@PostMapping("/findByUserId")
	@ApiOperation(value = "userid에 해당하는 게시글 조회")
	public Object findByUserId(@RequestParam(required = true) final long userid) {
		System.out.println("------------------------------");

		List<Article> articleOpt = articleDao.findArticleByUserid(userid);

		ResponseEntity response = null;

		if (!articleOpt.isEmpty()) {	
			final List<ArticleResponse> results = new ArrayList<ArticleResponse>();

			for(Article article : articleOpt) {
				ArticleResponse result = new ArticleResponse();
				result.status = true;
				result.data = "success";
				result.articleid = article.getArticleid();
				result.userid = article.getUserid();
				result.catid = article.getCatid();
				result.title = article.getTitle();
				result.content = article.getContent();
				results.add(result);
			}

			response = new ResponseEntity<>(results, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@PostMapping("/findCommentByArticleId")
	@ApiOperation(value = "userid에 해당하는 게시글 조회")
	public Object findCommentByArticleId(@RequestParam(required = true) final long articleid) {
		System.out.println("------------------------------");

		List<Comment> commentOpt = commentDao.findCommentByArticleid(articleid);

		ResponseEntity response = null;

		if (!commentOpt.isEmpty()) {	
			final List<CommentResponse> results = new ArrayList<CommentResponse>();

			for(Comment comment : commentOpt) {
				CommentResponse result = new CommentResponse();
				result.status = true;
				result.data = "success";
				result.commentid = comment.getCommentid();
				result.articleid = comment.getArticleid();
				result.comment = comment.getComment();
				result.writer = comment.getWriter();
				results.add(result);
			}

			response = new ResponseEntity<>(results, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return response;
	}
}