
package com.web.blog.dao.article;

import java.util.List;

import com.web.blog.model.article.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, String> {
    List<Comment> findCommentByArticleid(long articleid);
	int deleteCommentByCommentid(long commentid);        
}
