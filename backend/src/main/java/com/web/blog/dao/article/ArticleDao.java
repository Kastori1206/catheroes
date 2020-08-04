
package com.web.blog.dao.article;

import java.util.List;

import com.web.blog.model.article.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDao extends JpaRepository<Article, String> {
    List<Article> findArticleByCatid(long catid);    
    List<Article> findArticleByUserid(long userid);    
}
