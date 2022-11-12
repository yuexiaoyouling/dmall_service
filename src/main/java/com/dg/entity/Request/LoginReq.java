package com.dg.entity.Request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * @Author TheFool
 */
public class LoginReq {
    @Getter
    @NotBlank(message = "登录用户名不可为空")
    private String userName;

    @Getter
    @NotBlank(message = "登录密码不可为空")
    private String password;
}
