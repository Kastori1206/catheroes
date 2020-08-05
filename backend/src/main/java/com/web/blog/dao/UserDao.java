
package com.web.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.User;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);
    User getUserByNickname(String nickname);
    Optional<User> getUserByUid(long uid);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    @Transactional
    int deleteUserByEmail(String email);
    
    @Transactional
    @Modifying
    @Query(value=" UPDATE user "
    		   + " SET image = :image "
    		   + " WHERE uid = :userId ", nativeQuery = true)
    void updateImage(@Param("userId")Long userId,@Param("image") String image) throws Exception;
}
