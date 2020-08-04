package com.web.blog.dao.user;

import com.web.blog.model.user.User;

public interface AuthService {
	void singUpUser(User user);
	
	User loginUser(String email, String password);
}
