package com.dg.rest;

import com.dg.entity.Domain.User;
import com.dg.entity.Request.LoginReq;
import com.dg.entity.Request.RegisterReq;
import com.dg.entity.VO.Result;
import com.dg.entity.VO.UserVO;
import com.dg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author TheFool
 */
@RestController
@RequestMapping(value = "v1/public/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result<UserVO> login(@Validated @RequestBody LoginReq req) {
        return Result.success(userService.login(req));
    }

    @PostMapping(value = "/register")
    public Result register(@Validated @RequestBody RegisterReq req) {
        userService.register(req);
        return Result.success();
    }
}
