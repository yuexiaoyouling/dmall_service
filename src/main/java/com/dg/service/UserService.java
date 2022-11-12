package com.dg.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dg.dao.UserDao;
import com.dg.entity.Domain.User;
import com.dg.entity.Request.LoginReq;
import com.dg.entity.Request.RegisterReq;
import com.dg.entity.VO.UserVO;
import com.dg.exception.BusinessException;
import com.dg.exception.ExceptionCode;
import com.dg.utils.BeanHelper;
import com.dg.utils.JwtTokenUtil;
import com.dg.utils.SnowflakeIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author TheFool
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserDetailsService userDetailsService;

    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByUserName(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUserName, userName);
        User user = userDao.selectOne(wrapper);
        if (user == null) { throw new BusinessException(ExceptionCode.SYS_UNAUTHORIZED, "用户不存在"); }
        return user;
    }

    public UserVO login(LoginReq req) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUserName());
        if (!passwordEncoder.matches(req.getPassword(), userDetails.getPassword())) {
            throw new BusinessException(ExceptionCode.SYS_UNAUTHORIZED, "用户名密码错误");
        }
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        UserVO copy = BeanHelper.copy(getUserByUserName(req.getUserName()), UserVO.class);
        copy.setToken(token);
        return copy;
    }

    public void register(RegisterReq req) {
        if (req.getUserName() != null && StringUtils.isNotBlank(req.getUserName())) {
            userCheck(req.getUserName());

            req.setPassword(passwordEncoder.encode(req.getPassword()));
            SnowflakeIdUtil snowflakeIdUtil = new SnowflakeIdUtil(0, 0);
            User copy = BeanHelper.copy(req, User.class);
            copy.setUserId(snowflakeIdUtil.nextId());
            copy.setStatus("0");

            if (userDao.insert(copy) != 1) {
                throw new BusinessException(ExceptionCode.SYS_ERROR, "注册失败,用户插入失败");
            }
        }
    }

    public Boolean userCheck(String userName) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<User>()
                .eq(User::getUserName, userName);
        if (userDao.selectCount(query) != 0) {
            throw new BusinessException(ExceptionCode.PARAM_ERROR, "已存在该用户名");
        }
        return Boolean.TRUE;
    }

}
