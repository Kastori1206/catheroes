
package com.web.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.Member;


public interface MemberDao extends JpaRepository<Member, String> {
	Optional<Member> findMemberByEmail(String email);
	Optional<Member> findMemberByNickname(String nickname);
    Optional<Member> findMemberByMid(long mid);
    Optional<Member> findMemberByEmailAndPassword(String email, String password);
    
    @Transactional
    @Modifying
    @Query(value=" UPDATE user "
    		   + " SET image = :image "
    		   + " WHERE uid = :mid ", nativeQuery = true)
    void updateImage(@Param("mid")Long mid,@Param("image") String image) throws Exception;
}
