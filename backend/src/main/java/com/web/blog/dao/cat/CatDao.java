
package com.web.blog.dao.cat;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.cat.Cat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatDao extends JpaRepository<Cat, String> {
    Optional<Cat> getCatByCatid(long catid);
    
    List<Cat> findAll();    

    
}
