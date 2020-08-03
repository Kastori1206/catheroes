
package com.web.blog.dao.user;

import java.util.Optional;

import com.web.blog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);
    User getUserByNickname(String nickname);
    Optional<User> getUserByUid(long uid);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    @Transactional
    int deleteUserByEmail(String email);
}
