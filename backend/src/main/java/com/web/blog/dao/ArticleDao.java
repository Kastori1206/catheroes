
package com.web.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.Article;

public interface ArticleDao extends JpaRepository<Article, String> {
    List<Article> findArticleByCatid(long catid);    
    List<Article> findArticleByUserid(long userid);    
}
