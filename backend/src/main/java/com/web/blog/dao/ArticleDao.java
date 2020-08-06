
package com.web.blog.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.blog.model.Article;

public interface ArticleDao extends JpaRepository<Article, String> {
    List<Article> findArticleByCatidOrderByArticleidDesc(long catid);    
    List<Article> findArticleByUserid(long userid);    
    @Transactional
    @Query(value=" SELECT max(articleid) "
    		   + " FROM article ", nativeQuery = true)
    Long getMaxArticleId() ;
    @Transactional
	int deleteArticleByArticleid(long articleid);    
}
