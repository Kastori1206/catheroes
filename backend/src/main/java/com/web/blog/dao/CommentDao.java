
package com.web.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.Comment;

public interface CommentDao extends JpaRepository<Comment, String> {
    List<Comment> findCommentByArticleid(long articleid);
	int deleteCommentByCommentid(long commentid);        
}
