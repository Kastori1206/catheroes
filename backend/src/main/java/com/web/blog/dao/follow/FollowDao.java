package com.web.blog.dao.follow;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.web.blog.model.follow.Follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowDao extends JpaRepository<Follow, String> {
    List<Follow> findFollowByCatid(long catid);    
    List<Follow> findFollowByUserid(long userid);    

    Optional<Follow> findFollowByCatidAndUserid(long catid, long userid);

    @Transactional
    int deleteFollowByCatidAndUserid(long catid, long userid);
}
