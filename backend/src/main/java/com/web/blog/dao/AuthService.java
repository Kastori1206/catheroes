package com.web.blog.dao;

import com.web.blog.model.User;

public interface AuthService {
	void singUpUser(User user);
	
	User loginUser(String email, String password);
}
