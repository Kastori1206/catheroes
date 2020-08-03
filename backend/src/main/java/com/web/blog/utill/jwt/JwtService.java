package com.web.blog.utill.jwt;

import java.util.Map;

public interface JwtService {
    public <T> String create(String key, T data, String subject);
	Map<String, Object> get(String key);
	int getUserUid();
	boolean isUsable(String jwt);
}