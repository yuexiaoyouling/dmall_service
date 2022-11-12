package com.dg.entity.Request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * @Author TheFool
 */
@Data
public class RegisterReq {

    /**
     * 用户名称
     */
    @Getter
    @NotBlank(message = "登录用户名不可为空")
    private String userName;
    /**
     * 用户密码
     */
    @Getter
    @NotBlank(message = "登录密码不可为空")
    private String password;
    /**
     * 用户头像
     */
    private String userLogo;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 角色
     */
    private String role;
    /**
     * 备注
     */
    private String remark;
}
