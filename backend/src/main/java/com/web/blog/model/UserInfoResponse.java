package com.web.blog.model;

import io.swagger.annotations.ApiModelProperty;

public class UserInfoResponse extends BasicResponse{
    @ApiModelProperty(value = "nickName", position = 4)
    public String nickName;
    @ApiModelProperty(value = "email", position = 5)
    public String email;
    @ApiModelProperty(value = "uid", position = 6)
    public long uid;
    @ApiModelProperty(value = "password", position = 7)
    public String password;
}

