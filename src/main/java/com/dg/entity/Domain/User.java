package com.dg.entity.Domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author TheFool
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User {

    private long userId;
    private String userName;
    private String password;
    private String role;
    private String sex;
    private String phone;
    private String email;
    private String userLogo;
    private String realName;
    private Timestamp birthday;
    private double balance;
    private Timestamp createTime;
    private String status;

}
