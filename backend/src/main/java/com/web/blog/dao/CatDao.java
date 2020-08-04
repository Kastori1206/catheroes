
package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.Cat;

public interface CatDao extends JpaRepository<Cat, String> {
    Optional<Cat> getCatByCatid(long catid);
    
    List<Cat> findAll();    

    
}
