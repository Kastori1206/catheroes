
package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.blog.model.Article;

public interface ArticleDao extends JpaRepository<Article, String> {
    Optional<Article> findArticleByArticleid(long articleid);
    
    List<Article> findByMemberMid(long mid);
    List<Article> findByCatCatid(long catid);
    @Transactional
    @Query(value=" SELECT max(articleid) "
    		   + " FROM article ", nativeQuery = true)
    Long getMaxArticleId() ;       
}
