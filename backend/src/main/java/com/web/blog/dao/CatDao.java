
package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.Cat;

public interface CatDao extends JpaRepository<Cat, String> {
    Optional<Cat> getCatByCatid(long catid);
    
    List<Cat> findAll();    
    List<Cat> findCatByLocation(String location);
    List<Cat> findCatByLocationAndNicknameContaining(String location, String nickname);
    List<Cat> findCatByLocationAndBreed(String location, String breed);
    
    @Transactional
    @Query(value=" SELECT max(catid) "
    		   + " FROM cat ", nativeQuery = true)
    Long getMaxCatId() ;

}
