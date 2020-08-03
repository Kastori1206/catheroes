package com.web.blog.model;

import io.swagger.annotations.ApiModelProperty;

public class LoginResponse extends BasicResponse {
	@ApiModelProperty(value = "token", position = 4)
    public String token;

}
